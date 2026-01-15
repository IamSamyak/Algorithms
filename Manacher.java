/**
 * Manacher's Algorithm
 * Finds the longest palindromic substring in O(n) time.
 */
public class Manacher {

    /**
     * Returns the longest palindromic substring of the given string.
     */
    public static String longestPalindrome(String s) {

        // Handle edge cases
        if (s == null || s.length() == 0) {
            return "";
        }

        /*
         * Preprocess the string to handle even and odd length palindromes
         * uniformly.
         *
         * Example:
         *   s = "abba"
         *   transformed = "^#a#b#b#a#$"
         */
        char[] t = preprocess(s);
        int n = t.length;

        // p[i] stores the radius of the palindrome centered at i
        int[] p = new int[n];

        // center: center of the rightmost palindrome
        // right: right boundary of the rightmost palindrome
        int center = 0, right = 0;

        /*
         * Iterate over the transformed string (excluding sentinels)
         */
        for (int i = 1; i < n - 1; i++) {

            // Mirror position of i around the current center
            int mirror = 2 * center - i;

            /*
             * If i is within the right boundary,
             * initialize p[i] using the mirror palindrome
             */
            if (i < right) {
                p[i] = Math.min(right - i, p[mirror]);
            }

            /*
             * Try to expand palindrome centered at i
             * Compare characters equidistant from i
             */
            while (t[i + (1 + p[i])] == t[i - (1 + p[i])]) {
                p[i]++;
            }

            /*
             * If palindrome centered at i expands beyond right,
             * update center and right boundary
             */
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }

        /*
         * Find the longest palindrome by scanning p[]
         */
        int maxLen = 0;
        int centerIndex = 0;

        for (int i = 1; i < n - 1; i++) {
            if (p[i] > maxLen) {
                maxLen = p[i];
                centerIndex = i;
            }
        }

        /*
         * Convert the center and length back to original string indices
         *
         * start index formula:
         * (centerIndex - maxLen) / 2
         */
        int start = (centerIndex - maxLen) / 2;

        return s.substring(start, start + maxLen);
    }

    /**
     * Preprocesses the input string by inserting separators
     * and boundary markers.
     *
     * Example:
     *   "aba" → "^#a#b#a#$"
     */
    private static char[] preprocess(String s) {

        // Length = 2 * s.length + 3 (for ^, #, $)
        char[] t = new char[s.length() * 2 + 3];

        // Starting sentinel to avoid bounds checking
        t[0] = '^';

        int idx = 1;

        // Insert '#' between characters
        for (int i = 0; i < s.length(); i++) {
            t[idx++] = '#';
            t[idx++] = s.charAt(i);
        }

        // Ending separator and sentinel
        t[idx++] = '#';
        t[idx] = '$';

        return t;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad")); // Output: "bab" or "aba"
        System.out.println(longestPalindrome("cbbd"));  // Output: "bb"
    }
}
