package nil.ed.test.algo.path;

import java.util.Arrays;

/**
 * 球会落何处.
 * https://leetcode-cn.com/problems/where-will-the-ball-fall/
 * @author lidelin.
 */
public class FindBall {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new FindBall().findBall(new int[][]{{1, 1, 1, -1, -1}, {1, 1, 1, -1, -1}, {-1, -1, -1, 1, 1}, {1, 1, 1, 1, -1}, {-1, -1, -1, -1, -1}})));
        System.out.println(Arrays.toString(new FindBall().findBall(new int[][]{{-1}})));
        System.out.println(Arrays.toString(new FindBall().findBall(new int[][]{{1,1,1,1,1,1},{-1,-1,-1,-1,-1,-1},{1,1,1,1,1,1},{-1,-1,-1,-1,-1,-1}})));
    }

    public int[] findBall(int[][] grid) {
        int entries = grid[0].length;
        int[] ans = new int[entries];
        for (int i = 0; i < entries; i++) {
            ans[i] = findBall(i, grid);
        }
        return ans;
    }

    public int findBall(int j, int [][] grid) {
        for (int i = 0; i < grid.length; i++) {
            if (!isOpen(i, j, grid)) {
                return -1;
            }
            j = grid[i][j] == 1 ? j + 1 : j - 1;
        }
        return j;
    }

    public boolean isOpen(int i, int j, int[][] grid) {
        if (grid[i][j] == 1) {
            return j + 1 < grid[i].length && grid[i][j + 1] == 1;
        } else {
            return j - 1 >= 0 && grid[i][j - 1] == -1;
        }
    }

}
