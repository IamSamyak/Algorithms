---

## **Overview**

This Java class `BinarySearchTemplate` provides **flexible binary search methods** where you can define a **custom condition** instead of directly comparing array elements to a target.

It has **two methods**:

1. `binarySearch`: Finds the **first index** where a condition is true (`TTTFFFF` pattern).
2. `binarySearchLast`: Finds the **last index** where a condition is true (`FFFTTT` pattern).

The key idea is that the **condition is monotone**—once it switches from `true → false` (or `false → true`), it does not switch back. This allows binary search to work efficiently.

---

## **1. Method: binarySearch**

```java
public static int binarySearch(int n, Predicate<Integer> condition)
```

### **Parameters:**

* `n` → the size of the "search space" (like an array length or the range of numbers).
* `condition` → a **Predicate** (Java functional interface that returns boolean). You can define your own condition here.

### **Logic:**

1. Initialize search range:

```java
int left = 0;
int right = n - 1;
int ans = -1; // default if no index satisfies condition
```

2. While the search space is valid:

```java
int mid = left + (right - left) / 2; // avoid overflow
```

3. Check your **custom condition**:

```java
if (condition.test(mid)) {
    ans = mid;      // this could be the first index
    right = mid - 1; // search left side to see if an earlier index also satisfies
} else {
    left = mid + 1;  // search right side
}
```

4. Return `ans` after the loop finishes.

---

### **Pattern Required:** `TTTFFFF`

* This means that for indices:

  ```
  0 1 2 3 4 5 6
  T T T F F F F
  ```
* You want the **first `true` index** (index `3` in this case).

---

## **2. Method: binarySearchLast**

```java
public static int binarySearchLast(int n, Predicate<Integer> condition)
```

### **Logic Difference:**

* This is for **finding the last index** where the condition is true.
* Monotone condition: `FFFTTT`

```java
if (condition.test(mid)) {
    ans = mid;       // potential last index
    left = mid + 1;  // search right side
} else {
    right = mid - 1; // search left side
}
```

* The difference from the first method is that we now **move `left` to mid + 1** when the condition is true (instead of moving `right`) because we are looking for the **last true**.

---

## **3. How to Use These Methods**

### **Example 1: Find the first number ≥ target in a sorted array**

```java
int[] arr = {1, 3, 5, 7, 9};
int target = 4;

int index = BinarySearchTemplate.binarySearch(arr.length, i -> arr[i] >= target);

System.out.println(index); // Output: 2 (arr[2] = 5 ≥ 4)
```

* **Condition:** `arr[i] >= target`
* Works because the array is sorted → `TTTFFFF` pattern.

---

### **Example 2: Find the last number ≤ target in a sorted array**

```java
int[] arr = {1, 3, 5, 7, 9};
int target = 6;

int index = BinarySearchTemplate.binarySearchLast(arr.length, i -> arr[i] <= target);

System.out.println(index); // Output: 2 (arr[2] = 5 ≤ 6)
```

* **Condition:** `arr[i] <= target` → `FFFTTT` pattern
* Returns the **last index** satisfying the condition.

---

### **Example 3: Custom problem (first index where square ≥ n)**

```java
int n = 30;
int index = BinarySearchTemplate.binarySearch(n, i -> i * i >= n);

System.out.println(index); // Output: 6 (6*6=36 ≥ 30)
```

* This shows that **binary search is not limited to arrays**, it can be used for any integer search space.

---

## **4. Applications**

This flexible binary search is used extensively in **competitive programming**, **algorithmic problems**, and even in **real-world systems**.

### **Common Applications:**

1. **Searching in sorted arrays**

   * Find first/last occurrence of a number.
   * Count elements satisfying a condition.

2. **Mathematical problems**

   * Find the square root or cube root of a number using integer search.
   * Find smallest/largest number satisfying an inequality.

3. **Optimization problems**

   * Find minimum or maximum feasible value.
   * Example: "Minimum capacity to ship packages in D days."

4. **Monotone function problems**

   * If a function `f(x)` is monotone, you can use binary search to find the first x where `f(x)` satisfies a condition.

5. **Games or scheduling**

   * Find earliest/largest time satisfying constraints.

---

## **5. Key Advantages**

1. **Reusable & flexible**

   * The `Predicate<Integer>` lets you define any condition; no need to rewrite binary search logic.

2. **Handles large search spaces**

   * Works for arrays or abstract ranges like `[0, 1e9]`.

3. **Time complexity**

   * Always **O(log n)**, very efficient.

---

### ✅ **Summary Table**

| Method             | Pattern   | Finds      | Example Condition  |
| ------------------ | --------- | ---------- | ------------------ |
| `binarySearch`     | `TTTFFFF` | First true | `arr[i] >= target` |
| `binarySearchLast` | `FFFTTT`  | Last true  | `arr[i] <= target` |

---
