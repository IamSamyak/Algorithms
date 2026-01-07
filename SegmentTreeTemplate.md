---

# 🌲 Segment Tree (Generic – No Lazy Propagation)

## 📌 Overview

This is a **Segment Tree** implementation that supports:

* **Range Queries**

  * Minimum
  * Maximum
  * Sum
* **Point Updates** (update a single index)

The behavior is controlled using a clean `Operation` enum, making the tree **reusable and flexible**.

---

## 🎯 Problems It Solves

* Range Minimum Query (RMQ)
* Range Maximum Query
* Range Sum Query
* Dynamic arrays with frequent point updates

---

## 🧱 Core Data Structures

### 🔹 `seg[]`

* Segment tree array
* Each node stores aggregated information of a range

### 🔹 `Operation`

* Defines:

  * How two segments are merged
  * Identity value for no-overlap cases

---

## 🧩 Operation Enum

```java
enum Operation {
    MIN { ... },
    MAX { ... },
    SUM { ... }
}
```

### 🔸 `merge(a, b)`

Combines results from left and right subtrees:

| Operation | Merge Logic |
| --------- | ----------- |
| MIN       | `min(a, b)` |
| MAX       | `max(a, b)` |
| SUM       | `a + b`     |

---

### 🔸 `identity()`

Returned when there is **no overlap** during a query:

| Operation | Identity Value      |
| --------- | ------------------- |
| MIN       | `Integer.MAX_VALUE` |
| MAX       | `Integer.MIN_VALUE` |
| SUM       | `0`                 |

📌 **Why identity matters?**
It ensures correctness when merging partial results.

---

## 🏗 Segment Tree Construction

```java
SegmentTree(int arr[], Operation operation)
```

### What happens:

* Allocates segment tree array (`4n` size)
* Stores the selected operation
* Builds the tree recursively

⏱ **Time Complexity:** `O(n)`

---

## 🧠 Build Logic

```java
void build(int ind, int left, int right, int arr[])
```

### Explanation

* If `left == right` → leaf node
* Otherwise:

  * Split into left and right halves
  * Build recursively
  * Merge child results

---

## 🔍 Range Query

```java
int query(int leftQ, int rightQ)
```

### Query Cases

1. ❌ **No overlap**

   * Return identity

2. ✅ **Complete overlap**

   * Return `seg[ind]`

3. ⚠️ **Partial overlap**

   * Query both children
   * Merge results

⏱ **Time Complexity:** `O(log n)`

---

## ✏️ Point Update

```java
void pointUpdate(int indQ, int value)
```

### How it works

* Traverse to the leaf node for index `indQ`
* Update the value
* Recompute values on the path back up

⏱ **Time Complexity:** `O(log n)`

---

## 🧪 How to Use This Segment Tree

### ✅ Example Array

```java
int arr[] = {5, 2, 6, 3, 1, 7};
```

---

### 🔹 Range Sum Query

```java
SegmentTree stSum = new SegmentTree(arr, Operation.SUM);

System.out.println(stSum.query(1, 4)); // 2 + 6 + 3 + 1 = 12

stSum.pointUpdate(2, 10); // arr[2] = 10

System.out.println(stSum.query(1, 4)); // 2 + 10 + 3 + 1 = 16
```

---

### 🔹 Range Minimum Query

```java
SegmentTree stMin = new SegmentTree(arr, Operation.MIN);

System.out.println(stMin.query(0, 5)); // 1

stMin.pointUpdate(4, 8);

System.out.println(stMin.query(0, 5)); // 2
```

---

### 🔹 Range Maximum Query

```java
SegmentTree stMax = new SegmentTree(arr, Operation.MAX);

System.out.println(stMax.query(0, 5)); // 7

stMax.pointUpdate(1, 12);

System.out.println(stMax.query(0, 5)); // 12
```

---

## ⏱ Time & Space Complexity

| Operation    | Complexity |
| ------------ | ---------- |
| Build        | O(n)       |
| Range Query  | O(log n)   |
| Point Update | O(log n)   |
| Space        | O(4n)      |

---

## 🌟 Advantages

* Clean and reusable design
* Supports multiple query types with one implementation
* Faster than brute-force queries
* Easier to implement than lazy propagation

---

## ⚠️ Limitations

* No range updates
* Each update affects only one index
* For heavy range updates → **Lazy Segment Tree is better**

---

## 🧠 When to Use This?

✔ Use when:

* You need fast queries
* Updates are **point-based**
* Problem constraints are moderate to large

❌ Avoid when:

* Frequent range updates are required

---

## 🧩 One-Line Summary

> **Segment Trees break an array into intervals to answer queries and updates in logarithmic time.**

---
