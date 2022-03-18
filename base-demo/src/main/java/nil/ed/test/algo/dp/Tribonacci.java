package nil.ed.test.algo.dp;

/**
 * 太波那契数列.
 * https://leetcode-cn.com/problems/n-th-tribonacci-number/.
 * @author lidelin.
 */
public class Tribonacci {

    public static void main(String[] args) {
        System.out.println(new Tribonacci().tribonacci(0));
        System.out.println(new Tribonacci().tribonacci(1));
        System.out.println(new Tribonacci().tribonacci(2));
        System.out.println(new Tribonacci().tribonacci(3));
        System.out.println(new Tribonacci().tribonacci(4));
        System.out.println(new Tribonacci().tribonacci(5));
        System.out.println(new Tribonacci().tribonacci(25));
    }

    public int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }
        return dp[n];
    }

}
