class DSU {
    int[] parent, size;

    DSU(int n) {
        parent = new int[n + 1];
        size = new int[n + 1];
        Arrays.fill(size, 1);
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
    }

    int findParent(int x) {
        if (parent[x] != x) {
            parent[x] = findParent(parent[x]); // path compression
        }
        return parent[x];
    }

    boolean union(int x, int y) {
        int px = findParent(x);
        int py = findParent(y);
        if (px == py) return false;

        if (size[px] >= size[py]) {
            parent[py] = px;
            size[px] += size[py];
        } else {
            parent[px] = py;
            size[py] += size[px];
        }
        return true;
    }

    boolean isConnected(int x, int y) {
        return findParent(x) == findParent(y);
    }
}
