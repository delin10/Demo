package nil.ed.test.algo.dp;

/**
 * 最佳观光组合.
 * https://leetcode-cn.com/problems/best-sightseeing-pair/.
 * @author lidelin.
 */
public class MaxScoreSightseeingPair {

    public static void main(String[] args) {
        System.out.println(new MaxScoreSightseeingPair().maxScoreSightseeingPair(new int[] {8,1,5,2,6}));
        System.out.println(new MaxScoreSightseeingPair().maxScoreSightseeingPair(new int[] {1, 2}));
    }

    public int maxScoreSightseeingPair(int[] values) {
        int ans = Integer.MIN_VALUE;
        int maxCal = values[0];
        for (int i = 1; i < values.length; i++) {
            ans = Math.max(ans, values[i] - i + maxCal);
            maxCal = Math.max(maxCal, values[i] + i);
        }
        return ans;
    }
}
