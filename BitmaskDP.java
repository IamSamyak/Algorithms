public class BitmaskDP {

    static int n;              // number of elements
    static int[] value;        // value[i] = value of element i
    static int[] dp;           // dp[mask] = best value for subset mask

    // Initialize dp and compute the answer
    public static void solve() {
        int totalMasks = 1 << n; // total subsets = 2^n
        dp = new int[totalMasks];

        // Base case: empty set
        dp[0] = 0;

        // Iterate over all masks
        for (int mask = 1; mask < totalMasks; mask++) {

            dp[mask] = 0; // or -INF depending on problem

            // Try adding an element i to subset
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) { // if i is in the subset
                    int prevMask = mask ^ (1 << i); // remove i from mask
                    dp[mask] = Math.max(dp[mask], dp[prevMask] + value[i]);
                }
            }
        }

        // Answer is the value for the full set
        System.out.println("Maximum value of all elements: " + dp[totalMasks - 1]);
    }
}
