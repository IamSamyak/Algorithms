---

## **1. Overview of Binary Lifting and LCA**

### **Problem Context**

* You have a **tree** with `n` nodes (nodes are 0-indexed) and weighted edges.
* You want to answer queries like:

  * Distance between two nodes
  * Or some combination of distances (like in your `minimumWeight` function)
* To do this efficiently, you use **LCA** and **binary lifting**.

---

### **Lowest Common Ancestor (LCA)**

* **Definition:** For two nodes `u` and `v`, `LCA(u, v)` is the deepest node that is an ancestor of both `u` and `v`.
* Why important?

  * Once you know the LCA, you can compute distances in **O(1)** per query using prefix sums of edge weights along the path.

---

### **Binary Lifting**

* **Idea:** Precompute `up[node][i]` = `2^i`-th ancestor of `node`.
* Then, you can jump up the tree in **O(log n)** steps to find LCA.
* This is much faster than walking up the tree one step at a time.

---

## **2. DFS Function**

```java
void dfs(int node, int parent, int currDepth, int currWeight,
         int depths[], int cummulativeWeight[], List<List<int []>> adj, int up[][])
{
    depths[node] = currDepth;                     // Store depth of node
    cummulativeWeight[node] = currWeight;        // Store distance from root to node
    up[node][0] = parent;                        // 2^0 = 1 step ancestor

    for(int nbr[] : adj.get(node)){
        int nbrNode = nbr[0], nbrWt = nbr[1];
        if(nbrNode != parent){
            dfs(nbrNode, node, currDepth + 1, currWeight + nbrWt,
                depths, cummulativeWeight, adj, up);
        }
    }
}
```

✅ **Explanation:**

* Recursively traverses the tree.
* Fills:

  * `depths[node]`: Depth of each node from root
  * `cummulativeWeight[node]`: Distance from root
  * `up[node][0]`: Immediate parent
* **adj** is the adjacency list, each element is `[neighbor, weight]`.

---

## **3. Preprocessing Binary Lifting Table**

```java
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
```

✅ **Explanation:**

* Fills `up[node][p]` for all powers of 2.

* Logic:

  * `up[node][p]` = `2^p` ancestor of `node`
  * `= 2^(p-1)` ancestor of `2^(p-1)` ancestor
  * So: `up[node][p] = up[ up[node][p-1] ][p-1]`

* After this step, for any node, you can jump **2^i steps up in O(1)**.

---

## **4. Lifting a Node**

```java
int lift(int u, int diff, int up[][]){
    while(diff > 0){
        int jump = 31 - Integer.numberOfLeadingZeros(diff);
        u = up[u][jump];
        diff -= (1 << jump);
    }
    return u;
}
```

✅ **Explanation:**

* Lifts `u` up by `diff` levels using binary lifting.
* Uses **largest power of 2 ≤ diff**:

  * `jump = 31 - Integer.numberOfLeadingZeros(diff)`
  * This is the **floor(log2(diff))**
* Reduce `diff` step by step until node reaches the desired depth.

---

## **5. Finding LCA**

```java
int lca(int u, int v, int maxPower, int depths[], int up[][]){
    if(depths[u] > depths[v]){
        int temp = u; u = v; v = temp;   // Ensure v is deeper
    }

    v = lift(v, depths[v] - depths[u], up); // Lift v to same depth as u

    if(u == v) return u;

    for(int i = maxPower; i >= 0; i--){
        if(up[u][i] != -1 && up[u][i] != up[v][i]){
            u = up[u][i];
            v = up[v][i];
        }
    }
    return up[u][0]; // LCA found
}
```

✅ **Explanation:**

1. Make sure both nodes are at the same depth.
2. Lift the deeper node to match the shallower node.
3. Jump both nodes up in powers of 2 until their ancestors match.
4. Return their common parent.

* Complexity: **O(log n)**

---

## **6. Distance Between Nodes**

```java
int dist(int src, int dest, int maxPower, int depths[], int cummulativeWeigth[], int up[][]){
    int common = lca(src, dest, maxPower, depths, up);
    return cummulativeWeigth[src] + cummulativeWeigth[dest] - 2 * cummulativeWeigth[common];
}
```

✅ **Explanation:**

* Using **prefix sums** from root:

  ```
  distance(src, dest) = distance(root -> src) + distance(root -> dest) - 2 * distance(root -> LCA)
  ```
* Very efficient after preprocessing.

---

## **7. `minimumWeight` Function**

```java
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
```

✅ **Explanation:**

1. Convert edges into an adjacency list.
2. Compute `maxPower = log2(n)` for binary lifting table size.
3. Run DFS to fill depth, cumulative weight, and 1-step ancestors.
4. Preprocess binary lifting table.
5. For each query `[s1, s2, dest]`, calculate the **minimum path weight** using:

   ```
   total = (dist(s1, dest) + dist(s2, dest) + dist(s1, s2)) / 2
   ```

   * This formula comes from the **shortest path between three nodes in a tree** using distances to LCA.

---

## **8. Complexity Analysis**

| Step                   | Complexity |
| ---------------------- | ---------- |
| DFS                    | O(n)       |
| Preprocessing (`up`)   | O(n log n) |
| LCA queries (`lift`)   | O(log n)   |
| Distance queries       | O(log n)   |
| Total query processing | O(q log n) |

* Very efficient for **large trees** and **many queries**.

---

## ✅ **Summary**

* **DFS:** Sets up depth, cumulative weights, and immediate parents.
* **Binary Lifting:** Precomputes `2^i`-th ancestors for fast jumps.
* **LCA:** Finds lowest common ancestor in `O(log n)`.
* **Distance:** Uses LCA and cumulative weights to get path length.
* **Minimum Weight Query:** Combines distances between three nodes efficiently.

---
