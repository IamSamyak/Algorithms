public class BinarySearchTemplate {

    /**
     * Finds the first index where condition(mid) is true.
     * Monotone condition: TTTFFFF (true, then false)
     * Example: find first element >= target in a sorted array.
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int ans = -1; // default if not found

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] >= target) { // condition: change as needed
                ans = mid;           // potential answer
                right = mid - 1;     // search left side for first occurrence
            } else {
                left = mid + 1;      // search right side
            }
        }

        return ans;
    }

    /**
     * Finds the last index where condition(mid) is true.
     * Monotone condition: FFFTTT (false, then true)
     * Example: find last element <= target in a sorted array.
     */
    public static int binarySearchLast(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        int ans = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] <= target) {  // condition: change as needed
                ans = mid;            // potential answer
                left = mid + 1;       // search right side for last occurrence
            } else {
                right = mid - 1;      // search left side
            }
        }

        return ans;
    }
}
