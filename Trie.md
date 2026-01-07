---
# Trie (Prefix Tree) – Java Implementation

## Overview

A **Trie** (also called a **Prefix Tree**) is a tree-based data structure used to efficiently store and retrieve strings, especially useful for:

- Prefix-based searching
- Autocomplete systems
- Dictionary word lookup
- Spell checkers

Each node represents a character, and paths from the root represent words.

---

## Node Structure

Each node contains:
- An array of references to child nodes (`links[26]`) for lowercase English letters
- A boolean flag to mark the end of a word

```java
class Node {
    Node[] links = new Node[26];
    boolean flag;

    public Node() {}

    boolean containsKey(char ch) {
        return links[ch - 'a'] != null;
    }

    void put(char ch, Node node) {
        links[ch - 'a'] = node;
    }

    Node get(char ch) {
        return links[ch - 'a'];
    }

    void setEnd() {
        flag = true;
    }

    boolean isEnd() {
        return flag;
    }
}
````

### Explanation

* `links[26]` stores references for characters `'a'` to `'z'`
* `flag` marks whether a word ends at this node
* Helper methods make insertion and search clean and readable

---

## Trie Class

The `Trie` class manages the root node and provides operations to insert and search words.

```java
public class Trie {

    private static Node root;

    // Initialize your data structure here
    Trie() {
        root = new Node();
    }
```

---

## Insert Operation

Inserts a word into the trie character by character.

```java
    public static void insert(String word) {
        Node node = root;
        for (char ch : word.toCharArray()) {
            if (!node.containsKey(ch)) {
                node.put(ch, new Node());
            }
            node = node.get(ch);
        }
        node.setEnd();
    }
```

### Explanation

* Start from the root
* For each character:

  * Create a new node if it doesn’t exist
  * Move to the next node
* Mark the final node as the end of a word

---

## Search Operation

Checks whether a complete word exists in the trie.

```java
    public static boolean search(String word) {
        Node node = root;
        for (char ch : word.toCharArray()) {
            if (!node.containsKey(ch)) return false;
            node = node.get(ch);
        }
        return node.isEnd();
    }
```

### Explanation

* Traverse the trie using each character
* If any character is missing, return `false`
* Return `true` only if the final node marks the end of a word

---

## Prefix Search (Starts With)

Checks whether any word starts with the given prefix.

```java
    public static boolean startsWith(String prefix) {
        Node node = root;
        for (char ch : prefix.toCharArray()) {
            if (!node.containsKey(ch)) return false;
            node = node.get(ch);
        }
        return true;
    }
}
```

### Explanation

* Similar to search
* Does **not** require the final node to be an end-of-word
* Returns `true` if the prefix path exists

---

## Time Complexity

| Operation  | Time Complexity |
| ---------- | --------------- |
| Insert     | O(L)            |
| Search     | O(L)            |
| StartsWith | O(L)            |

Where **L** is the length of the word or prefix.

---

## Advantages

* Fast prefix-based lookups
* Efficient for large dictionaries
* No hashing collisions
* Ideal for autocomplete and search engines

---

## Common Applications

* Autocomplete systems
* Dictionary implementations
* Spell checkers
* Search engines
* IP routing and word games
