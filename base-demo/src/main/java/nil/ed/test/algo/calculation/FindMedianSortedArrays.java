package nil.ed.test.algo.calculation;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 寻找两个正序数组的中位数.
 * 困难.
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/.
 * @author lidelin.
 */
public class FindMedianSortedArrays {
    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(split("[1,3]"), split("[2]")));
        System.out.println(findMedianSortedArrays(split("[1,2]"), split("[3,4]")));
        System.out.println(findMedianSortedArrays(split("[0,0]"), split("[0,0]")));
        System.out.println(findMedianSortedArrays(split("[]"), split("[1]")));
        System.out.println(findMedianSortedArrays(split("[2]"), split("[]")));
        System.out.println(findMedianSortedArrays(split("[]"), split("[2,3]")));
    }

    public static int[] split(String arrStr) {
        return Stream.of(arrStr.replaceAll("[\\[\\]]", "").split("\\s*,\\s*")).filter(StringUtils::isNotBlank).mapToInt(NumberUtils::toInt).toArray();
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 1的处理
        // 除法向下取整的处理
        int i = 0;
        int totalSize = nums1.length + nums2.length;
        int j = totalSize - 1;
        int mink1 = 0;
        int mink2 = 0;

        int maxk1 = nums1.length - 1;
        int maxk2 = nums2.length - 1;

        boolean minOne = false;
        boolean maxOne = false;
        while (i <= j) {
            if (mink1 == nums1.length || (mink2 < nums2.length && nums1[mink1] > nums2[mink2])) {
                mink2++;
                minOne = false;
            } else {
                mink1++;
                minOne = true;
            }

            if (maxk2 < 0 || (maxk1 >= 0 && nums1[maxk1] > nums2[maxk2])) {
                maxk1--;
                maxOne = true;
            } else {
                maxk2--;
                maxOne = false;
            }
            ++i;
            --j;
        }

        if ((totalSize & 0x000000001) == 1) {
            return minOne ? nums1[mink1 - 1] : nums2[mink2 - 1];
        }
        double ans = minOne ? nums1[mink1 - 1] : nums2[mink2 - 1];
        ans = (ans +  (maxOne ? nums1[maxk1 + 1] : nums2[maxk2 + 1])) / 2.0;
        return ans;
    }

}
