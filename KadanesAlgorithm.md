---

## 1. What is the Problem?

You are given an **array of integers** (both positive and negative).

Example:

```java
{-2, 1, -3, 4, -1, 2, 1, -5, 4}
```

Your task is to find the **maximum sum of a contiguous subarray**.

👉 **Contiguous** means the elements must be next to each other.

### Example Subarrays:

* `{4, -1, 2, 1}` → sum = **6** ✅
* `{1, -3, 4}` → sum = 2
* `{4}` → sum = 4

The **maximum** sum among all subarrays is **6**.

---

## 2. What is Kadane’s Algorithm?

**Kadane’s Algorithm** is an efficient way to find the maximum subarray sum.

### Key Idea (Beginner Friendly):

At every number, you have **two choices**:

1. **Continue** the previous subarray (add the current number)
2. **Start a new subarray** from the current number

You choose whichever gives the **larger sum**.

### Why use Kadane’s Algorithm?

* Brute force solution: O(n²) ❌ (slow)
* Kadane’s Algorithm: **O(n)** ✅ (fast and efficient)

---

## 3. Understanding the Code

### Code:

```java
public class KadanesAlgorithm {
```

This defines a **class** named `KadanesAlgorithm`.

---

### Method to Find Maximum Subarray Sum

```java
public static int maxSubArraySum(int[] arr) {
```

* This method takes an **integer array** as input
* It returns an **integer** (maximum subarray sum)

---

### Step 1: Initialization

```java
int currentSum = arr[0];
int maxSum = arr[0];
```

📌 Why initialize with the first element?

* The array may contain **only negative numbers**
* Starting from `0` would give a wrong answer

🔹 `currentSum`
→ Stores the sum of the **current subarray**

🔹 `maxSum`
→ Stores the **maximum sum found so far**

---

### Step 2: Loop Through the Array

```java
for (int i = 1; i < arr.length; i++) {
```

* Start from index `1` because index `0` is already used
* Loop through the rest of the array

---

### Step 3: Decide Whether to Extend or Restart

```java
currentSum = Math.max(arr[i], currentSum + arr[i]);
```

👉 This is the **heart of Kadane’s Algorithm**.

It means:

* Either **start a new subarray** with `arr[i]`
* Or **continue the existing subarray** by adding `arr[i]`

Example:

* `currentSum = -2`
* `arr[i] = 1`

Compare:

* `1` (start new)
* `-2 + 1 = -1` (continue)

Choose **1** → start a new subarray

---

### Step 4: Update Maximum Sum

```java
maxSum = Math.max(maxSum, currentSum);
```

* If the current subarray sum is greater than the previous maximum
* Update `maxSum`

---

### Step 5: Return the Result

```java
return maxSum;
```

This returns the **maximum subarray sum**.

---

## 4. Main Method (Testing the Code)

```java
public static void main(String[] args) {
```

This is where the program starts executing.

---

### Input Array

```java
int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
```

---

### Calling the Method

```java
int result = maxSubArraySum(array);
```

* Calls the method
* Stores the returned value in `result`

---

### Output

```java
System.out.println("Maximum Subarray Sum is: " + result);
```

📌 Output:

```
Maximum Subarray Sum is: 6
```

---

## 5. Simple Step-by-Step Example

| Element | currentSum | maxSum |
| ------- | ---------- | ------ |
| -2      | -2         | -2     |
| 1       | 1          | 1      |
| -3      | -2         | 1      |
| 4       | 4          | 4      |
| -1      | 3          | 4      |
| 2       | 5          | 5      |
| 1       | 6          | 6      |
| -5      | 1          | 6      |
| 4       | 5          | 6      |

✅ Final Answer = **6**

---

## 6. Summary (For Beginners)

* Kadane’s Algorithm finds the **maximum subarray sum**
* It works by **deciding at each step**:

  * Continue the subarray
  * OR start a new one
* Time Complexity: **O(n)**
* Space Complexity: **O(1)**

---
