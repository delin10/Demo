package nil.ed.test.algo.dp;

/**
 * 斐波那契数列.
 * https://leetcode-cn.com/problems/fibonacci-number/
 * @author lidelin.
 */
public class Fib {

    public static void main(String[] args) {
        System.out.println(new Fib().fib(0));
        System.out.println(new Fib().fib(1));
        System.out.println(new Fib().fib(2));
        System.out.println(new Fib().fib(3));
        System.out.println(new Fib().fib(4));
        System.out.println(new Fib().fib(5));
    }

    public int fib(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i < n + 1; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

}
