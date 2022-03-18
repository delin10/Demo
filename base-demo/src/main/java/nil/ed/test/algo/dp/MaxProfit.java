package nil.ed.test.algo.dp;

/**
 * @author lidelin.
 */
public class MaxProfit {

    public static void main(String[] args) {
        System.out.println(new MaxProfit().maxProfit(new int[] {1,2,3,0,2}));
        System.out.println(new MaxProfit().maxProfit(new int[] {1}));
    }

    public int maxProfit(int[] prices) {
        if (prices.length == 1) {
            return 0;
        }
        int[] profits = new int[prices.length];
        profits[0] = 0;
        for (int i = 1; i < prices.length; i++) {
            profits[i] = prices[i] - prices[i - 1];
        }
        int[] dp = new int[prices.length];
        int[] dp2 = new int[prices.length];
        dp[0] = 0;
        dp[1] = Math.max(dp[0], profits[1]);
        dp2[0] = 0;

        for (int i = 2; i < prices.length; i++) {
            dp[i] = Math.max(dp[i - 2] + profits[2], dp[i - 1]);
        }
        return dp[prices.length - 1];
    }
}
