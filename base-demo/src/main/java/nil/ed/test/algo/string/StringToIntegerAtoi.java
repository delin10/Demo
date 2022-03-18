package nil.ed.test.algo.string;

/**
 * 字符串转换整数 (atoi).
 * https://leetcode-cn.com/problems/string-to-integer-atoi/.
 * @author lidelin.
 */
public class StringToIntegerAtoi {
    public static void main(String[] args) {
//        System.out.println(myAtoi("42"));
//        System.out.println(myAtoi("   -42"));
//        System.out.println(myAtoi("4193 with words"));
//        System.out.println(myAtoi("words and 987"));
//        System.out.println(myAtoi("-91283472332"));
        System.out.println(myAtoi("21474836460"));
    }

    static final String MAX = String.valueOf(Integer.MAX_VALUE);
    static final String MIN = String.valueOf(Integer.MIN_VALUE).replace("-", "");

    public static int myAtoi(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        boolean positive = true;
        int idx = 0;
        while (idx < s.length() && s.charAt(idx) == ' ') {
            idx++;
        }
        if (idx == s.length()) {
            return 0;
        }
        char first = s.charAt(idx);
        if (first == '-' || first == '+') {
            if (first == '-') {
                positive = false;
            }
            idx++;
        }

        while (idx < s.length() && s.charAt(idx) == '0') {
            idx++;
        }
        StringBuilder builder = new StringBuilder();
        while (idx < s.length() && Character.isDigit(s.charAt(idx))) {
            builder.append(s.charAt(idx++));
        }
        if (builder.length() == 0) {
            return 0;
        }

        boolean overflow = false;
        if (positive) {
            if (builder.length() == MAX.length()) {
                for (int i = 0; i < builder.length(); ++i) {
                    if (builder.charAt(i) > MAX.charAt(i)) {
                        overflow = true;
                    }
                }
            } else if (builder.length() > MAX.length()) {
                overflow = true;
            }
        } else {
            if (builder.length() == MIN.length()) {
                for (int i = 0; i < builder.length(); ++i) {
                    if (builder.charAt(i) > MIN.charAt(i)) {
                        overflow = true;
                    }
                }
            } else if (builder.length() > MAX.length()) {
                overflow = true;
            }
        }

        if (overflow) {
            if (positive) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        return positive ? Integer.parseInt(builder.toString()) : -Integer.parseInt(builder.toString());
    }
}
