package nil.ed.test.algo.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 无重复字符的最长子串.
 * 优化中等.
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/.
 * @author lidelin.
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("bbbbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));
        System.out.println(lengthOfLongestSubstring(""));
        System.out.println(lengthOfLongestSubstring( " "));
        System.out.println(lengthOfLongestSubstring( "dvdf"));
    }


    public static int lengthOfLongestSubstring(String s) {
        // O(N)
        // 通过滑动窗口进行优化.
        Set<Character> set = new HashSet<>(s.length(), 1);
        int ans = 0;
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i != 0) {
                set.remove(s.charAt(i -1));
            }

            while (right < s.length() && set.add(s.charAt(right))) {
                right++;
            }
            ans = Math.max(ans, set.size());
        }
        return ans;
    }

    public static int lengthOfLongestSubstringV3(String s) {
        // 优化点：减少hash函数进行优化
        // 不重复
        // 最长子串
        Map<Character, Integer> charMap = new HashMap<>(s.length(), 1);

        int ans = 0;
        int cnt = 0;
        int start = 0;
        int end = 0;
        // n * n
        while (end < s.length()){
            Integer idx = charMap.get(s.charAt(end));
            if (idx == null) {
                charMap.put(s.charAt(end), end);
                cnt++;
                // ++ 要放到这里.
                end++;
            } else {
                ans = Math.max(ans, cnt);
                cnt = 0;
                start = idx + 1;
                end = start;
                charMap.clear();
            }
        }
        // 当因为i < s.length()退出循环
        return Math.max(cnt, ans);
    }

    public static int lengthOfLongestSubstringV2(String s) {
        // start 规划start的位置进行优化
        // 不重复
        // 最长子串
        Map<Character, Integer> charMap = new HashMap<>(s.length(), 1);

        int ans = 0;
        int cnt = 0;
        int start = 0;
        int end = 0;
        // n * n
        while (end < s.length()){
            if (!charMap.containsKey(s.charAt(end))) {
                charMap.put(s.charAt(end), end);
                cnt++;
                // ++ 要放到这里.
                end++;
            } else {
                ans = Math.max(ans, cnt);
                cnt = 0;
                start = charMap.get(s.charAt(end)) + 1;
                end = start;
                charMap.clear();
            }
        }
        // 当因为i < s.length()退出循环
        return Math.max(cnt, ans);
    }

    public static int lengthOfLongestSubstringV1(String s) {
        // 不重复
        // 最长子串
        Set<Character> set = new HashSet<>(s.length(), 1);

        int ans = 0;
        int cnt = 0;
        int start = 0;
        int end = 0;
        // n * n
        while (end < s.length()){
            if (set.add(s.charAt(end))) {
                cnt++;
                // ++ 要放到这里.
                end++;
            } else {
                set.clear();
                ans = Math.max(ans, cnt);
                cnt = 0;
                start++;
                end = start;
            }
        }
        // 当因为i < s.length()退出循环
        return Math.max(cnt, ans);
    }
}
