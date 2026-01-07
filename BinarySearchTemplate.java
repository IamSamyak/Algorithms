import java.util.function.Predicate;

public class BinarySearchTemplate {

    /**
     * Finds the first index where the custom condition is true.
     * Monotone condition: TTTFFFF (true, then false)
     */
    public static int binarySearch(int n, Predicate<Integer> condition) {
        int left = 0;
        int right = n - 1;
        int ans = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (condition.test(mid)) { // <-- custom condition
                ans = mid;            // potential answer
                right = mid - 1;      // search left side
            } else {
                left = mid + 1;       // search right side
            }
        }

        return ans;
    }

    /**
     * Finds the last index where the custom condition is true.
     * Monotone condition: FFFTTT (false, then true)
     */
    public static int binarySearchLast(int n, Predicate<Integer> condition) {
        int left = 0;
        int right = n - 1;
        int ans = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (condition.test(mid)) { // <-- custom condition
                ans = mid;
                left = mid + 1;       // search right side
            } else {
                right = mid - 1;      // search left side
            }
        }

        return ans;
    }
}
