package nil.ed.test.algo.dp;

/**
 * 环形子数组的最大和.
 * https://leetcode-cn.com/problems/maximum-sum-circular-subarray/
 * @author lidelin.
 */
public class MaxSubarraySumCircular {


    public static void main(String[] args) {
        MaxSubarraySumCircular inst = new MaxSubarraySumCircular();
        System.out.println(inst.maxSubarraySumCircular(new int[] {1,-2,3,-2}));
        System.out.println(inst.maxSubarraySumCircular(new int[] {5,-3,5}));
        System.out.println(inst.maxSubarraySumCircular(new int[] {3,-2,2,-3}));
        System.out.println(inst.maxSubarraySumCircular(new int[] {-2,4,-5,4,-5,9,4}));
    }

    public int maxSubarraySumCircular(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int findHeadContinuous = 0;

        int head = 0;
        int sum = 1;

        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            ans = Math.max(maxSubarraySumCircular(nums, i), ans);
        }

        for (int i = 1; i < nums.length; i++) {

        }
        return ans;
    }

    public int maxSubarraySumCircular(int[] nums, int start) {
        int ans = nums[start];
        int sum = nums[start];
        int ss = (start + 1) % nums.length;
        for (int i = 1; i < nums.length; i++) {
            sum = Math.max(nums[ss], sum + nums[ss]);
            ans = Math.max(ans, sum);
            ss = (ss + 1) % nums.length;
        }
        return ans;
    }

}
