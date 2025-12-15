class SegmentTreeLazy{
    int seg[];
    int lazy[];
    
    SegmentTreeLazy(int n){
        seg = new int[4*n + 1];
        lazy = new int[4*n + 1];
    }
    
    void build(int ind, int left, int right, int arr[]){
        if(left == right){
            seg[ind] = arr[left];
            return;
        }
        int mid = (left + right) / 2;
        build(2 * ind + 1, left, mid, arr);
        build(2 * ind + 2, mid + 1, right, arr);
        seg[ind] = seg[2 * ind + 1] + seg[2 * ind + 2];
    }
    
    void rangeUpdate(int ind, int left, int right, int leftQ, int rightQ, int value){
        // Update previous remaining updates if any
        // propogate the updates downwards
        if(lazy[ind] != 0){
            seg[ind] += ((right - left + 1) * lazy[ind]);
            if(left != right){
                lazy[2 * ind + 1] += lazy[ind];
                lazy[2 * ind + 2] += lazy[ind];
            }
            lazy[ind] = 0;
        }
        
        // [leftQ...rightQ]...[left...right] or [left...right]...[leftQ...rightQ]
        // No overlap
        if(rightQ < left || right < leftQ) return;
        
        // [leftQ...left...right...rightQ]
        // Complete overlap
        if(leftQ <= left && right <= rightQ){
            seg[ind] += (right - left + 1) * value;
            if(left != right){
                lazy[2 * ind + 1] += value;
                lazy[2 * ind + 2] += value;
            }
            return;
        }
        
        // Partial overlap
        int mid = (left + right) / 2;
        // left subtree
        rangeUpdate(2 * ind + 1, left, mid, leftQ, rightQ, value);
        
        //right subtree
        rangeUpdate(2 * ind + 2, mid + 1, right, leftQ, rightQ, value);
        
        seg[ind] = seg[2 * ind + 1] + seg[2 * ind + 2];
    }
    
    int rangeQuery(int ind, int left, int right, int leftQ, int rightQ){
        if(lazy[ind] != 0){
            seg[ind] += (right - left + 1) * lazy[ind];
            if(left != right){
                lazy[2 * ind + 1] += lazy[ind];
                lazy[2 * ind + 2] += lazy[ind];
            }
            lazy[ind] = 0;
        }
        
        // [leftQ...rightQ]...[left...right] or [left...right]...[leftQ...rightQ]
        // No overlap
        if(rightQ < left || right < leftQ) return 0;
        
        // [leftQ...left...right...rightQ]
        // Complete overlap
        if(leftQ <= left && right <= rightQ) return seg[ind];
        
        //Partial overlap
        int mid = (left + right) / 2;
        int leftResult = rangeQuery(2 * ind + 1, left, mid, leftQ, rightQ);
        int rightResult = rangeQuery(2 * ind + 2, mid + 1, right, leftQ, rightQ);

        return leftResult + rightResult;
    }
}
