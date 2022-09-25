package nil.ed.test.algo.dp;

import java.util.Arrays;

/**
 * 地下城游戏.
 * https://leetcode-cn.com/problems/dungeon-game/
 * @author lidelin.
 */
public class BackgroundGame {

    public static void main(String[] args) {
        System.out.println(new BackgroundGame().calculateMinimumHP(new int[][]{{-2,-3,3},{-5,-10,1},{10,30,-5}}));
    }

    public int calculateMinimumHP(int[][] dungeon) {
        if (dungeon.length == 1) {
            return Arrays.stream(dungeon[0]).sum();
        }
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = dungeon[0][0];
        for (int i = 1; i < m; ++i) {
            dp[i][0] = dp[i - 1][0] + dungeon[i][0];
        }
        for (int j = 1; j < n; ++j) {
            dp[0][j] = dp[0][j - 1] + dungeon[0][j];
        }
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + dungeon[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

}
