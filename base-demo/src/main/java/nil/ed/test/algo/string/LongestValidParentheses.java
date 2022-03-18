package nil.ed.test.algo.string;

/**
 * 32. 最长有效括号.
 * https://leetcode-cn.com/problems/longest-valid-parentheses/
 * @author lidelin.
 */
public class LongestValidParentheses {

    public static void main(String[] args) {
        System.out.println(new LongestValidParentheses().longestValidParentheses("(()"));
        System.out.println(new LongestValidParentheses().longestValidParentheses(")()())"));
        System.out.println(new LongestValidParentheses().longestValidParentheses("("));
        System.out.println(new LongestValidParentheses().longestValidParentheses(""));
        System.out.println(new LongestValidParentheses().longestValidParentheses(")("));
        System.out.println(new LongestValidParentheses().longestValidParentheses(")(()(()(((())(((((()()))((((()()(()()())())())()))()()()())(())()()(((()))))()((()))(((())()((()()())((())))(())))())((()())()()((()((())))))((()(((((()((()))(()()(())))((()))()))())"));
    }

    public int longestValidParentheses(String s) {
        if (s.length() < 2) {
            return 0;
        }

        int ans = 0;
        int tmpStart = 0;
        int start = 0;
        for (int i = 0; i < s.length(); ++i) {
            int left = 0;
            int right = 0;
            tmpStart = 0;
            for (int j = i; j < s.length(); ++j) {
                if (s.charAt(j) == '(') {
                    if (i == j) {
                        tmpStart = j;
                        left = 1;
                    } else {
                        left = left + 1;
                    }
                } else {
                    if (i == j) {
                        right = 1;
                    } else {
                        right = right + 1;
                    }
                    if (right > left) {
                        break;
                    }
                }
                if (left == right) {
                    if (j - i + 1 > ans) {
                        start = tmpStart;
                    }
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        if (ans != 0) {
            System.out.println(s.substring(start, start + ans));
        }
        return ans;
    }

}
