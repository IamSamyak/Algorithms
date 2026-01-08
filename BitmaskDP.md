---

# **1. Algorithm Idea – Bitmask DP**

**Goal:**

* Solve problems where we want to consider **all subsets** of `n` elements and compute something (like max value, sum, etc.) efficiently.

**Key Idea:**

* Use a **bitmask** (binary number) to represent a subset of elements:

  * `n = 3` → elements `{0,1,2}`
  * Subsets:

    * `000` → empty set
    * `101` → elements `{0,2}`
    * `111` → all elements `{0,1,2}`

**Why DP?**

* Instead of recomputing for each subset, we store results in `dp[mask]` → the best value for subset represented by `mask`.
* **Transition:**

  * For each subset `mask`, try removing one element `i` → `prevMask`
  * Then: `dp[mask] = max(dp[mask], dp[prevMask] + value[i])`

**Complexity:**

* `O(n * 2^n)` → much faster than checking all permutations separately.

---

# **2. Code Explained Part by Part**

---

### **Step 1: Variables**

```java
static int n;        // number of elements
static int[] value;  // value[i] = value of element i
static int[] dp;     // dp[mask] = best value for subset mask
```

* `n` → total number of elements.
* `value[i]` → value of element `i` (for example, profit, weight, etc.).
* `dp[mask]` → stores the **best value for subset represented by mask**.

---

### **Step 2: Initialize dp**

```java
int totalMasks = 1 << n; // total subsets = 2^n
dp = new int[totalMasks];

// Base case: empty set
dp[0] = 0;
```

* `1 << n` → `2^n` → total number of subsets.
* `dp[0]` → value of **empty set** is 0.

✅ Example:

* If `n = 3` → masks go from `0` to `7` (binary `000` to `111`).

---

### **Step 3: Iterate over all masks**

```java
for (int mask = 1; mask < totalMasks; mask++) {
    dp[mask] = 0; // initialize
```

* Loop through **all subsets** (`mask = 1` to `2^n - 1`).
* Initialize `dp[mask]` to 0 (or `-INF` for minimization/maximization problems).

---

### **Step 4: Try adding each element to subset**

```java
for (int i = 0; i < n; i++) {
    if ((mask & (1 << i)) != 0) { // if i is in the subset
        int prevMask = mask ^ (1 << i); // remove i from mask
        dp[mask] = Math.max(dp[mask], dp[prevMask] + value[i]);
    }
}
```

* `(mask & (1 << i)) != 0` → **check if element `i` is in subset**

  * Example: `mask = 101` (elements 0 and 2)
  * `i=0` → `(101 & 001) != 0` → true
* `prevMask = mask ^ (1 << i)` → **remove element i** from current subset.
* `dp[mask] = max(dp[mask], dp[prevMask] + value[i])` → update the best value for this subset.

---

### **Step 5: Answer for full set**

```java
System.out.println("Maximum value of all elements: " + dp[totalMasks - 1]);
```

* `totalMasks - 1` → mask with **all elements included** (`111...1` in binary).
* `dp[totalMasks - 1]` → best value for **full set**.

---

# **3. Example**

Suppose `n = 3` and `value = {5, 10, 3}`

| mask | subset  | dp[mask] |
| ---- | ------- | -------- |
| 000  | {}      | 0        |
| 001  | {0}     | 5        |
| 010  | {1}     | 10       |
| 011  | {0,1}   | 15       |
| 100  | {2}     | 3        |
| 101  | {0,2}   | 8        |
| 110  | {1,2}   | 13       |
| 111  | {0,1,2} | 18       |

* `dp[mask]` is built **from smaller subsets** by adding one element at a time.

---

### **4. Why This is Efficient**

* Without DP: check all subsets → O(n * 2^n) anyway.
* With DP: **reuse results of smaller subsets**, no recomputation.
* Works for problems like:

  * Max subset sum
  * Traveling Salesman Problem (TSP)
  * Matching or assignment problems

---
