---

# 🔍 LPS Array Construction (KMP Algorithm)

## 📌 What is the LPS Array?

**LPS (Longest Prefix Suffix)** array is a core part of the **KMP string matching algorithm**.
[▶️ KMP LPS Explained (YouTube)](https://www.youtube.com/watch?v=Gr-eKRuWIQc&t=670s)

For each index `i` in the string:

* `lps[i]` stores the **length of the longest proper prefix**
* which is also a **suffix** for the substring `s[0…i]`

### ❗ Proper Prefix

* A prefix that is **not equal to the whole string**
* Example:

  * String: `"abab"`
  * Proper prefixes: `"a"`, `"ab"`, `"aba"`
  * Proper suffixes: `"b"`, `"ab"`, `"bab"`

---

## 🧠 Why LPS is Needed?

* Prevents **rechecking characters**
* Enables KMP to skip unnecessary comparisons
* Ensures **linear time complexity O(n)**

---

## 🧪 Example

For the string:

```
s = "ababaca"
```

The LPS array will be:

```
lps = [0, 0, 1, 2, 3, 0, 1]
```

---

## 🧩 Code Implementation

```java
static int[] constructLps(String s){
    int n = s.length();
    int lps[] = new int[n];

    for(int i = 1; i < n; i++){
        int prev_idx = lps[i - 1];

        while(prev_idx > 0 && s.charAt(i) != s.charAt(prev_idx)){
            prev_idx = lps[prev_idx - 1];
        }

        lps[i] = prev_idx + (s.charAt(i) == s.charAt(prev_idx) ? 1 : 0);
    }
    return lps;
}
```

---

## 🪜 Step-by-Step Explanation

### 🔹 Step 1: Initialization

* `lps[0] = 0`

  * A single character has **no proper prefix or suffix**
* Start iterating from `i = 1`

---

### 🔹 Step 2: Previous LPS Value

```java
int prev_idx = lps[i - 1];
```

* Represents the length of the longest prefix-suffix till the **previous index**
* Acts as a **pointer** to compare characters

---

### 🔹 Step 3: Handle Mismatch (Fallback Logic)

```java
while(prev_idx > 0 && s.charAt(i) != s.charAt(prev_idx)){
    prev_idx = lps[prev_idx - 1];
}
```

#### 🔸 Why this works:

* If characters don’t match:

  * We don’t restart from scratch
  * We **jump to the next best candidate prefix**
* This avoids redundant comparisons

📌 **Key Insight from the Video**

> “We reuse previously computed LPS values instead of recomparing characters.”

---

### 🔹 Step 4: Match Case

```java
lps[i] = prev_idx + (s.charAt(i) == s.charAt(prev_idx) ? 1 : 0);
```

* If characters match:

  * Extend the prefix by `1`
* Else:

  * LPS remains `0`

---

## 🔁 Visual Intuition

For `"abab"` at index `3`:

```
Prefix: ab
Suffix: ab
```

➡️ `lps[3] = 2`

If mismatch occurs:

* Fall back using earlier LPS values
* Never move `i` backward

---

## ⏱️ Time & Space Complexity

| Metric | Complexity |
| ------ | ---------- |
| Time   | **O(n)**   |
| Space  | **O(n)**   |

* Each character is processed at most twice
* Efficient due to fallback via LPS values

---

## 🎯 Key Takeaways

* LPS captures **overlapping patterns**
* Helps avoid unnecessary comparisons
* Backbone of **KMP string matching**
* Smart reuse of previously solved subproblems

---

## 🧠 One-Line Summary

> **LPS tells us how much of the pattern we can reuse when a mismatch happens.**

---

## 🧪 Dry Run: LPS Construction for `"aabaaab"`

### 📌 String Details

```
Index:  0 1 2 3 4 5 6
Char :  a a b a a a b
```

Initialize:

```
lps[0] = 0
```

---

## 🪜 Step-by-Step Execution

### 🔹 i = 1 (`'a'`)

* `prev_idx = lps[0] = 0`
* Compare `s[1]` with `s[0]` → `'a' == 'a'` ✅
* `lps[1] = 0 + 1 = 1`

```
lps = [0, 1, _, _, _, _, _]
```

---

### 🔹 i = 2 (`'b'`)

* `prev_idx = lps[1] = 1`
* Compare `s[2]` with `s[1]` → `'b' != 'a'` ❌
* Fallback: `prev_idx = lps[0] = 0`
* Compare `s[2]` with `s[0]` → `'b' != 'a'` ❌
* No match → `lps[2] = 0`

```
lps = [0, 1, 0, _, _, _, _]
```

---

### 🔹 i = 3 (`'a'`)

* `prev_idx = lps[2] = 0`
* Compare `s[3]` with `s[0]` → `'a' == 'a'` ✅
* `lps[3] = 1`

```
lps = [0, 1, 0, 1, _, _, _]
```

---

### 🔹 i = 4 (`'a'`)

* `prev_idx = lps[3] = 1`
* Compare `s[4]` with `s[1]` → `'a' == 'a'` ✅
* `lps[4] = 2`

```
lps = [0, 1, 0, 1, 2, _, _]
```

---

### 🔹 i = 5 (`'a'`) 🔥 *Tricky Step*

* `prev_idx = lps[4] = 2`
* Compare `s[5]` with `s[2]` → `'a' != 'b'` ❌
* Fallback: `prev_idx = lps[1] = 1`
* Compare `s[5]` with `s[1]` → `'a' == 'a'` ✅
* `lps[5] = 2`

```
lps = [0, 1, 0, 1, 2, 2, _]
```

📌 **Key Insight**

> We don’t restart from index `0` — we reuse previous LPS values.

---

### 🔹 i = 6 (`'b'`)

* `prev_idx = lps[5] = 2`
* Compare `s[6]` with `s[2]` → `'b' == 'b'` ✅
* `lps[6] = 3`

```
lps = [0, 1, 0, 1, 2, 2, 3]
```

---

## ✅ Final LPS Array

```
[0, 1, 0, 1, 2, 2, 3]
```

---

## 🧠 What This Means

* Longest prefix which is also a suffix for:

  * `"aabaaab"` → `"aab"` (length `3`)
* Pattern reuse becomes **very efficient** during KMP search

---

## 🎯 Key Observations

* `i = 5` demonstrates **why fallback is powerful**
* No character is compared more than twice
* Algorithm never moves `i` backward

---

## 🧩 Mental Model (Interview Gold)

> “When a mismatch happens, we don’t throw away all progress — we jump to the next best prefix using LPS.”

---
