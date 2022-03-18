package nil.ed.test.algo.backward;

/**
 * 交错字符串.
 * 回溯法实现
 * https://leetcode-cn.com/problems/interleaving-string/
 * @author lidelin.
 */
public class IsInterleave {

    public static void main(String[] args) {
        String s1, s2, s3;
        s1 = "aabcc";
        s2 = "dbbca";
        s3 = "aadbbcbcac";
        IsInterleave inst = new IsInterleave();
        System.out.println(inst.isInterleave(s1, s2, s3));

        s1 = "aabcc";
        s2 = "dbbca";
        s3 = "aadbbbaccc";
        System.out.println(inst.isInterleave(s1, s2, s3));
        s1 = "";
        s2 = "";
        s3 = "";
        System.out.println(inst.isInterleave(s1, s2, s3));

        s1 = "a";
        s2 = "b";
        s3 = "a";
        System.out.println(inst.isInterleave(s1, s2, s3));

        s1 = "abababababababababababababababababababababababababababababababababababababababababababababababababbb";
        s2 = "babababababababababababababababababababababababababababababababababababababababababababababababaaaba";
        s3 = "abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababbb";
        System.out.println(inst.isInterleave(s1, s2, s3));
    }

    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        return isInterleave(s3, 0, new String[] {s1, s2}, new int[2], 0, 1) || isInterleave(s3, 0, new String[] {s1, s2}, new int[2], 1, 1);
    }

    public boolean isInterleave(String s3, int idx3, String[] ss, int[] idx, int now, int level) {
        for (int i = idx[now]; i < ss[now].length() && idx3 < s3.length(); i++, idx3++) {
            System.out.printf("now=%s, idx3=%s, level = %s%n", now, idx3, level);
            if (ss[now].charAt(i) == s3.charAt(idx3)) {
                int[] idx2 = new int[2];
                idx2[now] = i + 1;
                idx2[(now + 1) % 2] = idx[(now + 1) % 2];
                if (!isInterleave(s3, idx3 + 1, ss, idx2, (now + 1) % 2, level + 1)) {
                    continue;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
        if (idx3 == s3.length()) {
            return true;
        }

        return false;
    }
}
