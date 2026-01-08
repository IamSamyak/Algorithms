public class KadanesAlgorithm {

    // Method to find the maximum subarray sum
    public static int maxSubArraySum(int[] arr) {

        // Initialize both values with the first element
        int currentSum = arr[0];
        int maxSum = arr[0];

        // Start from the second element
        for (int i = 1; i < arr.length; i++) {

            // Either extend the existing subarray or start a new one
            currentSum = Math.max(arr[i], currentSum + arr[i]);

            // Update maximum sum found so far
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    // Main method to test the algorithm
    public static void main(String[] args) {

        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        int result = maxSubArraySum(array);

        System.out.println("Maximum Subarray Sum is: " + result);
    }
}
