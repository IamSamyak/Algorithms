---

# 🌲 Segment Tree with Lazy Propagation (Generic)

## 📌 Overview

This implementation is a **Lazy Segment Tree** that supports:

* **Range Update** (add a value to all elements in a range)
* **Range Query**

  * Sum
  * Minimum
  * Maximum

It is **generic** thanks to the `Operation` enum, allowing you to switch behavior without rewriting the tree logic.

---

## 🎯 Problems It Solves

* Range Sum Query with range increment
* Range Minimum Query with range increment
* Range Maximum Query with range increment
* Efficient handling of **large ranges + many updates**

---

## 🧱 Core Components

### 🔹 `seg[]`

* Stores the segment tree values
* Each node represents a range

### 🔹 `lazy[]`

* Stores **pending updates**
* Used to delay propagation until needed

### 🔹 `Operation`

* Defines how nodes are merged
* Defines identity value
* Defines how lazy updates affect a node

---

## 🧩 Operation Enum (Key Abstraction)

```java
enum Operation {
    SUM { ... },
    MIN { ... },
    MAX { ... }
}
```

Each operation defines:

### 🔸 `merge(a, b)`

* Combines results from left and right children

| Operation | merge logic |
| --------- | ----------- |
| SUM       | `a + b`     |
| MIN       | `min(a, b)` |
| MAX       | `max(a, b)` |

---

### 🔸 `identity()`

* Neutral value for queries with **no overlap**

| Operation | Identity |
| --------- | -------- |
| SUM       | `0`      |
| MIN       | `+∞`     |
| MAX       | `-∞`     |

---

### 🔸 `applyLazy(value, lazy, length)`

* Applies a lazy update to a node

| Operation | Lazy Effect             |
| --------- | ----------------------- |
| SUM       | `value + lazy * length` |
| MIN       | `value + lazy`          |
| MAX       | `value + lazy`          |

📌 **Why difference?**

* For `SUM`, range length matters
* For `MIN` / `MAX`, only value shifts

---

## 🏗 Segment Tree Construction

```java
SegmentTreeLazy(int arr[], Operation operation)
```

### What happens:

* Allocates `seg[]` and `lazy[]`
* Builds tree in `O(n)`
* Stores chosen operation (SUM / MIN / MAX)

---

## 🧠 Build Logic

```java
void build(int ind, int left, int right, int arr[])
```

### Explanation

* Leaf node → store `arr[left]`
* Internal node → merge left & right children
* Recursively builds the tree

⏱ **Time Complexity:** `O(n)`

---

## ⏳ Lazy Propagation (pushDown)

```java
void pushDown(int ind, int left, int right)
```

### What it does:

* Applies pending lazy value to current node
* Pushes lazy value to children (if not leaf)
* Clears current lazy value

📌 **Key Idea**

> “Don’t update children immediately — delay until needed.”

---

## ✏️ Range Update (Add Value)

```java
void rangeUpdate(int leftQ, int rightQ, int value)
```

### Update Scenarios

1. ❌ **No overlap** → return
2. ✅ **Complete overlap**

   * Store update in `lazy`
   * Apply immediately to node
3. ⚠️ **Partial overlap**

   * Push lazy
   * Recurse to children
   * Merge results

⏱ **Time Complexity:** `O(log n)`

---

## 🔍 Range Query

```java
int rangeQuery(int leftQ, int rightQ)
```

### Query Scenarios

1. ❌ **No overlap** → return identity
2. ✅ **Complete overlap** → return node value
3. ⚠️ **Partial overlap**

   * Push lazy
   * Query children
   * Merge results

⏱ **Time Complexity:** `O(log n)`

---

## 🧪 How to Use This Segment Tree

### ✅ Example 1: Range Sum Query

```java
int arr[] = {1, 3, 5, 7, 9, 11};

SegmentTreeLazy st = new SegmentTreeLazy(arr, Operation.SUM);

// Query sum from index 1 to 4
System.out.println(st.rangeQuery(1, 4)); // 3 + 5 + 7 + 9 = 24

// Add 10 to range [2, 4]
st.rangeUpdate(2, 4, 10);

// Query again
System.out.println(st.rangeQuery(1, 4)); // 3 + 15 + 17 + 19 = 54
```

---

### ✅ Example 2: Range Minimum Query

```java
SegmentTreeLazy stMin = new SegmentTreeLazy(arr, Operation.MIN);

System.out.println(stMin.rangeQuery(0, 5)); // min value

stMin.rangeUpdate(1, 3, 5);

System.out.println(stMin.rangeQuery(0, 5));
```

---

### ✅ Example 3: Range Maximum Query

```java
SegmentTreeLazy stMax = new SegmentTreeLazy(arr, Operation.MAX);

System.out.println(stMax.rangeQuery(0, 5)); // max value

stMax.rangeUpdate(0, 2, 4);

System.out.println(stMax.rangeQuery(0, 5));
```

---

## ⏱ Time & Space Complexity

| Operation    | Complexity |
| ------------ | ---------- |
| Build        | O(n)       |
| Range Update | O(log n)   |
| Range Query  | O(log n)   |
| Space        | O(4n)      |

---

## 🌟 Advantages

* Highly flexible (SUM / MIN / MAX)
* Efficient for large ranges
* Clean abstraction using `enum`
* Interview & CP friendly

---

## ⚠️ Important Notes

* This implementation supports **range add updates**
* Not suitable for:

  * Range assignment (`set`)
  * Non-additive updates (without modification)

---

## 🧠 One-Line Summary

> **Lazy propagation allows us to postpone updates and still answer queries in logarithmic time.**

---
