---

## 1️⃣ Problem Recap

Given a string `s`, find the **longest palindromic substring**.

### Naive solutions

* Check every substring → **O(n³)**
* Expand around center → **O(n²)**

👉 **Manacher’s Algorithm solves it in O(n)**.

---

## 2️⃣ Key Idea of Manacher’s Algorithm

Manacher’s Algorithm:

* Uses **symmetry of palindromes**
* Avoids recomputing palindromic checks
* Expands palindromes only when necessary

The trick is to:

1. **Preprocess the string**
2. **Reuse palindrome information using mirror positions**
3. **Maintain the rightmost palindrome found so far**

---

## 3️⃣ Step 1: Preprocessing the String

### Why preprocessing is needed

Palindromes can be:

* Odd length → `"aba"`
* Even length → `"abba"`

To handle both uniformly, we **insert separators (`#`)** between characters.

### Example

Original string:

```
abba
```

Transformed string:

```
^#a#b#b#a#$
```

### Purpose of special characters

* `#` → removes distinction between even and odd length palindromes
* `^` and `$` → sentinels to avoid bounds checking

Now **every palindrome is odd-length**.

---

## 4️⃣ Step 2: Palindrome Radius Array (`p[]`)

```java
int[] p = new int[n];
```

### What `p[i]` means

`p[i]` = radius of palindrome centered at index `i`
(number of characters matched on **each side**)

Example:

```
# a # b # b # a #
      ↑
      i
p[i] = 4  → palindrome length = 2 * 4 + 1
```

---

## 5️⃣ Step 3: `center` and `right`

```java
int center = 0, right = 0;
```

### Meaning

* `center` → center of the **rightmost palindrome found so far**
* `right` → **right boundary** of that palindrome

So the known palindrome spans:

```
[center - p[center], center + p[center]]
```

---

## 6️⃣ Step 4: The Mirror Concept (MOST IMPORTANT)

### Mirror formula

```java
int mirror = 2 * center - i;
```

### Why this works

Palindromes are symmetric.

If:

```
i is d positions to the right of center
```

Then:

```
mirror is d positions to the left of center
```

Mathematically:

```
mirror = center - (i - center) = 2 * center - i
```

---

### Reusing palindrome information

If `i` lies **inside the current right boundary**:

```java
if (i < right) {
    p[i] = Math.min(right - i, p[mirror]);
}
```

Why `min()`?

* The mirrored palindrome might extend beyond `right`
* We can only trust palindrome info **inside the known boundary**

This optimization is what makes the algorithm **O(n)**.

---

## 7️⃣ Step 5: Expanding Around Center

```java
while (t[i + (1 + p[i])] == t[i - (1 + p[i])]) {
    p[i]++;
}
```

Only expands **when necessary**, after mirror reuse.

---

## 8️⃣ Step 6: Updating `center` and `right`

```java
if (i + p[i] > right) {
    center = i;
    right = i + p[i];
}
```

If a palindrome centered at `i` extends farther right,
we update our reference palindrome.

---

## 9️⃣ Step 7: Finding the Longest Palindrome

```java
int maxLen = 0;
int centerIndex = 0;
```

Scan `p[]` to find the largest radius.

---

## 🔁 Converting Back to Original String

```java
int start = (centerIndex - maxLen) / 2;
```

Why this formula?

* Each original character maps to **two positions** in the transformed string
* Division by 2 restores original indices

Final answer:

```java
s.substring(start, start + maxLen);
```

---

## 🔟 Time and Space Complexity

| Aspect | Complexity |
| ------ | ---------- |
| Time   | **O(n)**   |
| Space  | **O(n)**   |

Each character is expanded **at most once**.

---

## 🧠 One-Line Intuition (Interview Ready)

> **Manacher’s algorithm finds the longest palindrome in linear time by reusing symmetric palindrome information around a center.**

---
