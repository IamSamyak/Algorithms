enum Operation {
    MIN {
        @Override
        int merge(int a, int b) {
            return Math.min(a, b);
        }

        @Override
        int identity() {
            return Integer.MAX_VALUE;
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
    },
    SUM {
        @Override
        int merge(int a, int b) {
            return a + b;
        }

        @Override
        int identity() {
            return 0;
        }
    };

    abstract int merge(int a, int b);
    abstract int identity();
}

class SegmentTree {
    int seg[];
    Operation operation;

    SegmentTree(int n, Operation operation) {
        seg = new int[4 * n + 1];
        this.operation = operation;
    }

    void build(int ind, int left, int right, int arr[]) {
        if (left == right) {
            seg[ind] = arr[left];
            return;
        }

        int mid = (left + right) / 2;

        // left subtree
        build(2 * ind + 1, left, mid, arr);

        // right subtree
        build(2 * ind + 2, mid + 1, right, arr);

        seg[ind] = operation.merge(
                seg[2 * ind + 1],
                seg[2 * ind + 2]
        );
    }

    int query(int ind, int left, int right, int leftQ, int rightQ) {
        // [left...right]...[leftQ...rightQ] or [leftQ...rightQ]...[left...right]
        // No overlap
        if (leftQ > right || rightQ < left)
            return operation.identity();

        // Complete overlap
        // [leftQ...left...right...rightQ]
        if (leftQ <= left && right <= rightQ)
            return seg[ind];

        // Partial overlap
        int mid = (left + right) / 2;

        int leftResult = query(2 * ind + 1, left, mid, leftQ, rightQ);
        int rightResult = query(2 * ind + 2, mid + 1, right, leftQ, rightQ);

        return operation.merge(leftResult, rightResult);
    }

    void pointUpdate(int ind, int left, int right, int indQ, int value) {
        if (left == right) {
            seg[ind] = value;
            return;
        }

        int mid = (left + right) / 2;

        if (indQ <= mid)
            pointUpdate(2 * ind + 1, left, mid, indQ, value);
        else
            pointUpdate(2 * ind + 2, mid + 1, right, indQ, value);

        seg[ind] = operation.merge(
                seg[2 * ind + 1],
                seg[2 * ind + 2]
        );
    }
}
