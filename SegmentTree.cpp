class SegmentTreeMax{
    private:vector<int>seg;

    public:
        SegmentTreeMax(int n,int initial_value){
            seg.resize(4*n+1,initial_value);
        }

        void build(int ind,int low,int high,vector<int>&arr){
            if(low == high){
                seg[ind] = arr[low];
                return;
            }
            int mid = (low+high)/2;
            build(2*ind+1,low,mid,arr);
            build(2*ind+2,mid+1,high,arr);
            seg[ind] = max(seg[2*ind+1],seg[2*ind+2]);
        }

        int query(int ind,int low,int high,int l,int r){
            if(high < l or low > r){ // low...high..l..r or l...r...low...high
                return INT_MIN;
            }
            if(low >= l and high <= r){ //complete overlap
                return seg[ind];
            }

            //partial overlap
            int mid = (low+high)/2;
            int left = query(2*ind+1,low,mid,l,r);
            int right = query(2*ind+2,mid+1,high,l,r);
            return max(left,right);
        }

        void update(int ind,int low,int high,int i,int value){
            if(low == high){
                seg[ind] = value;
            }
            int mid = (low+high)/2;
            if(i<=mid) update(2*ind+1,low,mid,i,value);
            else update(2*ind+2,mid+1,high,i,value);
            seg[ind] = max(seg[2*ind+1],seg[2*ind+2]);
        }
};
