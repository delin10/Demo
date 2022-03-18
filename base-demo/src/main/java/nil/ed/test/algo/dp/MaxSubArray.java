package nil.ed.test.algo.dp;

/**
 * 最大子数组.
 * https://leetcode-cn.com/problems/maximum-subarray/
 * @author lidelin.
 */
public class MaxSubArray {

    public static void main(String[] args) {
        MaxSubArray maxSubArray = new MaxSubArray();
        System.out.println(maxSubArray.maxSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4}));
        System.out.println(maxSubArray.maxSubArray(new int[] {1}));
        System.out.println(maxSubArray.maxSubArray(new int[] {5,4,-1,7,8}));
    }

    public int maxSubArray(int[] nums) {
        int ans = nums[0];
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum = Math.max(nums[i], sum + nums[i]);
            ans = Math.max(ans, sum);
        }
        return ans;
    }

}
