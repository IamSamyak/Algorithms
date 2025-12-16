enum Operation {
    SUM {
        @Override
        int merge(int a, int b) {
            return a + b;
        }

        @Override
        int identity() {
            return 0;
        }

        @Override
        int applyLazy(int value, int lazy, int length) {
            return value + lazy * length;
        }
    },
    MIN {
        @Override
        int merge(int a, int b) {
            return Math.min(a, b);
        }

        @Override
        int identity() {
            return Integer.MAX_VALUE;
        }

        @Override
        int applyLazy(int value, int lazy, int length) {
            return value + lazy;
        }
    },
    MAX {
        @Override
        int merge(int a, int b) {
            return Math.max(a, b);
        }

        @Override
        int identity() {
            return Integer.MIN_VALUE;
        }

        @Override
        int applyLazy(int value, int lazy, int length) {
            return value + lazy;
        }
    };

    abstract int merge(int a, int b);
    abstract int identity();
    abstract int applyLazy(int value, int lazy, int length);
}

class SegmentTreeLazy {
    int seg[];
    int lazy[];
    Operation operation;
    int n;

    // Constructor directly takes arr[]
    SegmentTreeLazy(int arr[], Operation operation) {
        this.n = arr.length;
        seg = new int[4 * n + 1];
        lazy = new int[4 * n + 1];
        this.operation = operation;
        build(0, 0, n - 1, arr);
    }

    void build(int ind, int left, int right, int arr[]) {
        if (left == right) {
            seg[ind] = arr[left];
            return;
        }

        int mid = (left + right) / 2;

        build(2 * ind + 1, left, mid, arr);
        build(2 * ind + 2, mid + 1, right, arr);

        seg[ind] = operation.merge(
                seg[2 * ind + 1],
                seg[2 * ind + 2]
        );
    }

    void pushDown(int ind, int left, int right) {
        if (lazy[ind] != 0) {
            seg[ind] = operation.applyLazy(
                    seg[ind],
                    lazy[ind],
                    right - left + 1
            );

            if (left != right) {
                lazy[2 * ind + 1] += lazy[ind];
                lazy[2 * ind + 2] += lazy[ind];
            }
            lazy[ind] = 0;
        }
    }

    void rangeUpdate(int ind, int left, int right, int leftQ, int rightQ, int value) {
        // Update previous remaining updates if any
        // propogate the updates downwards
        pushDown(ind, left, right);

        // [leftQ...rightQ]...[left...right] or [left...right]...[leftQ...rightQ]
        // No overlap
        if (rightQ < left || right < leftQ) return;

        // [leftQ...left...right...rightQ]
        // Complete overlap
        if (leftQ <= left && right <= rightQ) {
            lazy[ind] += value;
            pushDown(ind, left, right);
            return;
        }

        // Partial overlap
        int mid = (left + right) / 2;

        // left subtree
        rangeUpdate(2 * ind + 1, left, mid, leftQ, rightQ, value);

        // right subtree
        rangeUpdate(2 * ind + 2, mid + 1, right, leftQ, rightQ, value);

        seg[ind] = operation.merge(
                seg[2 * ind + 1],
                seg[2 * ind + 2]
        );
    }

    int rangeQuery(int ind, int left, int right, int leftQ, int rightQ) {
        pushDown(ind, left, right);

        // [leftQ...rightQ]...[left...right] or [left...right]...[leftQ...rightQ]
        // No overlap
        if (rightQ < left || right < leftQ)
            return operation.identity();

        // [leftQ...left...right...rightQ]
        // Complete overlap
        if (leftQ <= left && right <= rightQ)
            return seg[ind];

        // Partial overlap
        int mid = (left + right) / 2;

        int leftResult = rangeQuery(2 * ind + 1, left, mid, leftQ, rightQ);
        int rightResult = rangeQuery(2 * ind + 2, mid + 1, right, leftQ, rightQ);

        return operation.merge(leftResult, rightResult);
    }

    // User-friendly range update
    void rangeUpdate(int leftQ, int rightQ, int value) {
        if (leftQ < 0) leftQ = 0;
        if (rightQ >= n) rightQ = n - 1;
        if (leftQ > rightQ) return;

        rangeUpdate(0, 0, n - 1, leftQ, rightQ, value);
    }

    // User-friendly range query
    int rangeQuery(int leftQ, int rightQ) {
        if (leftQ < 0) leftQ = 0;
        if (rightQ >= n) rightQ = n - 1;
        if (leftQ > rightQ) return operation.identity();

        return rangeQuery(0, 0, n - 1, leftQ, rightQ);
    }
}
