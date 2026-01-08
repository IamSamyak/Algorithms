---

## **1. The Algorithm (Sieve of Eratosthenes)**

**Goal:** Find all prime numbers from `0` to `n`.

**Steps:**

1. **Assume all numbers are prime** initially.
2. **Mark 0 and 1 as not prime**, because by definition they are not primes.
3. **Loop through numbers starting from 2 up to √n**:

   * If the number is still marked prime, it is indeed prime.
   * Mark all multiples of that number as not prime (they can’t be prime).
4. **After finishing the loop**, all numbers still marked as prime are indeed prime.

**Key Points:**

* **Start marking multiples at i × i** because smaller multiples were already handled by smaller primes.
* **Stop outer loop at √n** because any composite number larger than √n will already have a smaller factor.

---

### **Step 1: Create the array and assume all numbers are prime**

```java
boolean[] isPrime = new boolean[n + 1];
Arrays.fill(isPrime, true);
```

* We create a boolean array `isPrime` of size `n + 1` (to include `n`).
* We fill it with `true` because **we initially assume every number is prime**.

---

### **Step 2: Mark 0 and 1 as not prime**

```java
if (n >= 0) isPrime[0] = false;
if (n >= 1) isPrime[1] = false;
```

* By definition, 0 and 1 are **not prime**, so we set them to `false`.
* The `if` checks ensure we don’t get an error if `n` is very small (like 0 or 1).

---

### **Step 3: Outer loop – check numbers from 2 up to √n**

```java
for (int i = 2; i * i <= n; i++) {
```

* `i` is the **current number we are checking**.
* **Why `i * i <= n` instead of `i <= n`?**

  * Any number larger than √n will have already been marked as a multiple of a smaller prime.
  * Saves unnecessary checks, making the algorithm faster.

---

### **Step 4: Check if the number is still prime**

```java
if (isPrime[i]) {
```

* If `isPrime[i]` is `true`, then `i` is prime.
* Only **prime numbers** need to mark multiples because multiples of non-primes are already marked.

---

### **Step 5: Inner loop – mark multiples as not prime**

```java
for (int j = i * i; j <= n; j += i) {
    isPrime[j] = false;
}
```

* **Start at `i * i`** because smaller multiples of `i` (like `i*2, i*3, …`) were already marked by smaller primes.
* `j += i` → increment by `i` each time to mark all multiples.
* `isPrime[j] = false;` → mark this number as **not prime**.

---

### **Step 6: Return the array**

```java
return isPrime;
```

* After all loops, `isPrime[i]` is `true` if `i` is prime and `false` if it is not.

---

✅ **Summary with Example (n = 10):**

| i | isPrime before  | Multiples marked | isPrime after           |
| - | --------------- | ---------------- | ----------------------- |
| 2 | all true        | 4,6,8,10         | 0,1=false, 2,3,5,7=true |
| 3 | 2,3,5,7 true    | 9                | 9=false                 |
| 4 | 4 already false | skip             | no change               |

* Result: `2, 3, 5, 7` are prime.

---
