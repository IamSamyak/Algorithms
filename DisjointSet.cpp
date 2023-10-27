class DisjointSet{
    public:
        vector<int>parent,size;
        int mx;
        DisjointSet(int n){
            parent.resize(n+1);
            size.resize(n+1,1);
            mx=1;
            for(int i=0;i<=n;i++) parent[i]=i;
        }

        int findUltimateParent(int node){
            if(node == parent[node]){
                return node;
            }
            return parent[node] = findUltimateParent(parent[node]);
        }

        void unionBySize(int u,int v){
            int ultimate_parent_of_u = findUltimateParent(u);
            int ultimate_parent_of_v = findUltimateParent(v);
            if(ultimate_parent_of_u == ultimate_parent_of_v) return;

            if(size[ultimate_parent_of_u] > size[ultimate_parent_of_v]){
                swap(ultimate_parent_of_u,ultimate_parent_of_v);
            }
            size[ultimate_parent_of_v]+=size[ultimate_parent_of_u];
            parent[ultimate_parent_of_u]=ultimate_parent_of_v;
            mx = max(mx,size[ultimate_parent_of_v]);
        }
};
