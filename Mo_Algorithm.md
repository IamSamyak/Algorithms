---

# 🧮 Square Root Decomposition (Range Sum Query)

## 📌 Overview

**Square Root Decomposition** is a technique used to efficiently answer **range queries** on an array while supporting **updates**.

It works by:

* Dividing the array into blocks of size ≈ `√n`
* Precomputing information (here: **sum**) for each block
* Using block sums to speed up queries

---

## 🎯 Problem Supported

* **Range Sum Query**: sum of elements from index `left` to `right`
* **Point Update**: update a single index value

---

## 🧱 Core Idea

* Let `n` = size of array
* Choose block size = `√n`
* Number of blocks ≈ `√n`
* Each block stores the **sum** of its elements

This reduces:

* Query time from **O(n)** → **O(√n)**
* Update time from **O(n)** → **O(1)**

---

## 🗂 Data Structures Used

### 🔸 Original Array

```java
int arr[];
```

### 🔸 Block Array

```java
int blocks[];
```

* `blocks[i]` stores the **sum of elements** in block `i`

---

## ⚙️ Block Construction

```java
static int[] computeBlocks(int arr[]){
    int n = arr.length;
    int sqrt = (int) Math.sqrt(n);

    int blocks[] = new int[sqrt + 1];
    int blockIdx = -1;

    for(int i = 0; i < n; i++){
        if(i % sqrt == 0) blockIdx++;
        blocks[blockIdx] += arr[i];
    }
    return blocks;
}
```

### ✅ Explanation

* Block size = `√n`
* When `i % sqrt == 0`, move to next block
* Add each element to its corresponding block sum

---

## 🔍 Range Query (Sum from `left` to `right`)

```java
static int query(int blocks[], int arr[], int left, int right, int sqrt){
    int result = 0;

    // Left partial block
    while(left != 0 && left < right && left % sqrt != 0){
        result += arr[left];
        left++;
    }

    // Complete blocks
    while(left + sqrt <= right){
        result += blocks[left / sqrt];
        left += sqrt;
    }

    // Right partial block
    while(left <= right){
        result += arr[left];
        left++;
    }

    return result;
}
```

---

## 🧠 Query Breakdown

### 🔹 1. Left Partial Block

* Add elements **one by one**
* Stop when `left` aligns with block boundary

### 🔹 2. Middle Complete Blocks

* Add **entire block sums**
* Jump by block size (`sqrt`)
* This is where optimization happens

### 🔹 3. Right Partial Block

* Add remaining elements individually

---

## ✏️ Point Update

```java
static void update(int blocks[], int arr[], int i, int val, int sqrt){
    int blockIdx = i / sqrt;
    blocks[blockIdx] += (val - arr[i]);
    arr[i] = val;
}
```

### ✅ Explanation

* Identify block containing index `i`
* Update block sum by difference
* Update original array value

⏱ **Time Complexity:** `O(1)`

---

## 🧪 Example Dry Run

### Input Array

```
arr = [1, 3, 5, 2, 7, 6, 3, 1, 4, 8]
n = 10
sqrt = 3
```

### Blocks Created

```
Block 0 → [1, 3, 5] = 9
Block 1 → [2, 7, 6] = 15
Block 2 → [3, 1, 4] = 8
Block 3 → [8] = 8

blocks = [9, 15, 8, 8]
```

---

### Query: `sum(2, 7)`

```
Elements = [5, 2, 7, 6, 3, 1]
Sum = 24
```

✔ Computed efficiently using blocks

---

## ⏱ Time Complexity

| Operation    | Complexity |
| ------------ | ---------- |
| Build Blocks | O(n)       |
| Range Query  | O(√n)      |
| Point Update | O(1)       |
| Space        | O(√n)      |

---

## 🌟 Advantages

* Faster than brute force
* Simple to implement
* Ideal for **static range queries + updates**

---

## ⚠️ Limitations

* Slower than Segment Tree for heavy updates
* Best when:

  * Moderate constraints
  * Offline or limited updates

---

## 🧠 When to Use √ Decomposition?

✔ When you need:

* Fast range queries
* Simple logic
* Less code than Segment Trees

---

## 🧩 One-Line Summary

> **Square Root Decomposition trades memory for speed by precomputing block results.**

---
