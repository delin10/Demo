package nil.ed.test.algo.string;

/**
 * Z 字形变换.
 *
 * https://leetcode-cn.com/problems/zigzag-conversion/.
 * @author lidelin.
 */
public class ZigZagConversion {

    public static void main(String[] args) {
        // PAHN APLSIIG YIR
        // PAHN PAAHHNN YIR
        // PAHN APLSIIG YIR
        System.out.println(convert("PAYPALISHIRING", 3));
        // PIN ALSIGYAH RPI
        // PIN ALSIGYAH RPI
        System.out.println(convert("PAYPALISHIRING", 4));
    }

    public static String convert(String s, int numRows) {
        if (s.length() == 0) {
            return "";
        }

        if (numRows < 2) {
            return s;
        }
        StringBuilder builder = new StringBuilder(s.length());
        // 第一层的计算
        int idx = 0;
        int counter = 0;
        while (true) {
            idx = counter * (2 * (numRows - 2) + 2);
            if (idx >= s.length()) {
                break;
            }
            builder.append(s.charAt(idx));
            counter++;
        }

        // 中间层的计算
        int level = 1;
        while (level <  numRows - 1) {
            counter = 0;
            idx = level;
            while (true) {
                idx = counter * (2 * (numRows - 2) + 2) + level;
                if (idx >= s.length()) {
                    break;
                }
                builder.append(s.charAt(idx));
                int idx2 = idx + (numRows - level - 2) * 2 + 2;
                if (idx2 < s.length()) {
                    builder.append(s.charAt(idx2));
                } else {
                    break;
                }
                counter++;
            }
            level++;
        }

        // 最后一层的计算
        counter = 0;
        while (true) {
            idx = counter * (2 * (numRows - 2) + 2) + numRows - 1;
            if (idx >= s.length()) {
                break;
            }
            builder.append(s.charAt(idx));
            counter++;
        }
        return builder.toString();
    }



}
