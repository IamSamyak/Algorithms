---

# 🚀 Disjoint Set Union (DSU) / Union-Find

## 🔹 Overview

* **Disjoint Set Union (DSU)**, also called **Union-Find**, is a data structure used to manage **disjoint (non-overlapping) sets**
* Supports two core operations efficiently:

  * **Find** → Identify which set an element belongs to
  * **Union** → Merge two sets
* Widely used in **graph algorithms** and **connectivity problems**

### 📌 Common Use Cases

* Kruskal’s **Minimum Spanning Tree (MST)**
* **Cycle detection** in undirected graphs
* **Dynamic connectivity** problems

---

## 🧱 Internal Data Structures

DSU maintains two arrays:

### 🔸 `parent[]`

* Stores the **parent (representative)** of each element
* If `parent[x] == x`, then `x` is the **root** of its set

### 🔸 `size[]`

* Stores the **size of the set** for which the element is the root
* Helps optimize union operations

> 💡 Each set is represented as a **tree**, with the root acting as the representative.

---

## ⚙️ Initialization

At the start:

* Each element is in its **own set**
* Every element is its **own parent**
* Each set has an initial **size of 1**

```java
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
}
```

### ✅ Explanation

* `parent[i] = i` → each element starts as its own component
* `size[i] = 1` → each set initially contains one element
* Indexing from `0` to `n` allows flexibility

---

## 🔍 Find Operation (Path Compression)

Finds the **ultimate parent (root)** of an element.

```java
int findParent(int x) {
    if (parent[x] != x) {
        parent[x] = findParent(parent[x]); // path compression
    }
    return parent[x];
}
```

### ✅ Explanation

* If the node is not its own parent, recursively find the root
* **Path compression** directly connects nodes to the root
* This flattens the tree and speeds up future operations

---

## 🔗 Union Operation (Union by Size)

Merges the sets containing two elements.

```java
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
```

### ✅ Explanation

* Find the root of both elements
* If roots are the same → already connected
* Otherwise:

  * Attach the **smaller tree under the larger tree**
  * Update the size of the new root

### ❓ Why Union by Size?

* Prevents deep trees
* Keeps the structure balanced
* Improves overall performance

---

## 🔎 Connectivity Check

Checks whether two elements belong to the same set.

```java
boolean isConnected(int x, int y) {
    return findParent(x) == findParent(y);
}
```

### ✅ Explanation

* If both elements share the same root → they are connected
* Very fast due to path compression

---

## ⏱️ Time Complexity

With **path compression + union by size**:

| Operation    | Time Complexity |
| ------------ | --------------- |
| Find         | O(α(n))         |
| Union        | O(α(n))         |
| Connectivity | O(α(n))         |

> 🔹 **α(n)** = Inverse Ackermann function
> Grows so slowly that it’s effectively **constant** in practice

---

## 🌟 Advantages

* Extremely efficient for large datasets
* Simple and reusable implementation
* Ideal for connectivity-based problems

---

## 📚 Common Applications

* Kruskal’s Minimum Spanning Tree (MST)
* Cycle detection in undirected graphs
* Network connectivity
* Grouping and clustering problems

---
