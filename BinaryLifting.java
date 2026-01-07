class BinaryLifting {
    void dfs(int node, int parent, int currDepth, int currWeight, int depths[], int cummulativeWeight[], List<List<int []>> adj, int up[][]){
        depths[node] = currDepth;
        cummulativeWeight[node] = currWeight;
        up[node][0] = parent;

        for(int nbr[] : adj.get(node)){
            int nbrNode = nbr[0], nbrWt = nbr[1];
            if(nbrNode != parent){
                dfs(nbrNode, node, currDepth + 1, currWeight + nbrWt, depths, cummulativeWeight, adj, up);
            }
        }
    }

    void preprocess(int n, int maxPower, int up[][]){
        for(int p = 1; p <= maxPower; p++){
            for(int node = 0; node < n; node++){
                int parent = up[node][p - 1];
                if(parent != -1){
                    up[node][p] = up[parent][p - 1];
                }
            }
        }
    }

    int lift(int u, int diff, int up[][]){
        while(diff > 0){
            int jump = 31 - Integer.numberOfLeadingZeros(diff);
            u = up[u][jump];
            diff -= (1 << jump);
        }
        return u;
    }

    int lca(int u, int v, int maxPower, int depths[], int up[][]){
        if(depths[u] > depths[v]){
            int temp = u;
            u = v;
            v = temp;
        }

        v = lift(v, depths[v] - depths[u], up);

        if(u == v) return u;

        for(int i = maxPower; i >= 0; i--){
            if(up[u][i] != -1 && up[u][i] != up[v][i]){
                u = up[u][i];
                v = up[v][i];
            }
        }
        return up[u][0];
    }

    int dist(int src, int dest, int maxPower, int depths[], int cummulativeWeigth[], int up[][]){
        int common = lca(src, dest, maxPower, depths, up);
        return cummulativeWeigth[src] + cummulativeWeigth[dest] - 2 * cummulativeWeigth[common];
    }

    public int[] minimumWeight(int[][] edges, int[][] queries) {
        int n = edges.length + 1;
        List<List<int []>> adj = new ArrayList<>();

        for(int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for(int edge[] : edges){
            int u = edge[0], v = edge[1], wt = edge[2];
            adj.get(u).add(new int[]{v, wt});
            adj.get(v).add(new int[]{u, wt});
        }

        int maxPower = 32 - Integer.numberOfLeadingZeros(n);

        int depths[] = new int[n];
        int cummulativeWeight[] = new int[n];

        int up[][] = new int[n][maxPower + 1];
        for(int i = 0; i < n; i++) Arrays.fill(up[i], -1);

        dfs(0, -1, 0, 0, depths, cummulativeWeight, adj, up);
        preprocess(n, maxPower, up);

        int result[] = new int[queries.length];
        for(int i = 0; i < queries.length; i++){
            int s1 = queries[i][0], s2 = queries[i][1], dest = queries[i][2];
            int total = (dist(s1, dest, maxPower, depths, cummulativeWeight, up)
                        + dist(s2, dest, maxPower, depths, cummulativeWeight, up)
                        + dist(s1, s2, maxPower, depths, cummulativeWeight, up)) / 2;
            result[i] = total;
        }
        return result;
    }
}
