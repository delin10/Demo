package nil.ed.test.algo.dp;

/**
 * 跳跃游戏.
 * Time Exceed Version.
 * https://leetcode-cn.com/problems/jump-game-ii/
 * @author lidelin.
 */
public class Jump {

    public static void main(String[] args) {
        System.out.println(new Jump().jump(new int[] {2,3,1,1,4}));
        System.out.println(new Jump().jump(new int[] {2,3,0,1,4}));
        System.out.println(new Jump().jump(new int[] {2,1}));
    }

    public int jump(int[] nums) {
        return jump(nums, 0, 0);
    }

    public int jump(int[] nums, int target, int depth) {
        if (target == nums.length - 1) {
            return depth;
        }

        if (target >= nums.length) {
            return Integer.MAX_VALUE;
        }

        int maxStep = nums[target];
        if (maxStep == 0) {
            return Integer.MAX_VALUE;
        }
        int maxDepth = Integer.MAX_VALUE;
        for (int i = maxStep; i > 0; --i) {
            maxDepth = Math.min(maxDepth, jump(nums, target + i, depth + 1));
        }
        return maxDepth;
    }
}
