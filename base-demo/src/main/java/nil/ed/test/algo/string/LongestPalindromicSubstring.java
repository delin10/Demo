package nil.ed.test.algo.string;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 最长回文子串.
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 * @author lidelin.
 */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        LongestPalindromicSubstring inst = new LongestPalindromicSubstring();
//        System.out.println(inst.longestPalindrome(""));
//        System.out.println(inst.longestPalindrome("babad"));
//        System.out.println(inst.longestPalindrome("cbbd"));
//        System.out.println(inst.longestPalindrome("a"));
//        System.out.println(inst.longestPalindrome("babaddtattarrattatddetartrateedredividerb"));
//        System.out.println(inst.longestPalindrome("adam"));
        System.out.println(inst.longestPalindrome("zudfweormatjycujjirzjpyrmaxurectxrtqedmmgergwdvjmjtstdhcihacqnothgttgqfywcpgnuvwglvfiuxteopoyizgehkwuvvkqxbnufkcbodlhdmbqyghkojrgokpwdhtdrwmvdegwycecrgjvuexlguayzcammupgeskrvpthrmwqaqsdcgycdupykppiyhwzwcplivjnnvwhqkkxildtyjltklcokcrgqnnwzzeuqioyahqpuskkpbxhvzvqyhlegmoviogzwuiqahiouhnecjwysmtarjjdjqdrkljawzasriouuiqkcwwqsxifbndjmyprdozhwaoibpqrthpcjphgsfbeqrqqoqiqqdicvybzxhklehzzapbvcyleljawowluqgxxwlrymzojshlwkmzwpixgfjljkmwdtjeabgyrpbqyyykmoaqdambpkyyvukalbrzoyoufjqeftniddsfqnilxlplselqatdgjziphvrbokofvuerpsvqmzakbyzxtxvyanvjpfyvyiivqusfrsufjanmfibgrkwtiuoykiavpbqeyfsuteuxxjiyxvlvgmehycdvxdorpepmsinvmyzeqeiikajopqedyopirmhymozernxzaueljjrhcsofwyddkpnvcvzixdjknikyhzmstvbducjcoyoeoaqruuewclzqqqxzpgykrkygxnmlsrjudoaejxkipkgmcoqtxhelvsizgdwdyjwuumazxfstoaxeqqxoqezakdqjwpkrbldpcbbxexquqrznavcrprnydufsidakvrpuzgfisdxreldbqfizngtrilnbqboxwmwienlkmmiuifrvytukcqcpeqdwwucymgvyrektsnfijdcdoawbcwkkjkqwzffnuqituihjaklvthulmcjrhqcyzvekzqlxgddjoir"));
        System.out.println(inst.longestPalindrome("anugnxshgonmqydttcvmtsoaprxnhpmpovdolbidqiyqubirkvhwppcdyeouvgedccipsvnobrccbndzjdbgxkzdbcjsjjovnhpnbkurxqfupiprpbiwqdnwaqvjbqoaqzkqgdxkfczdkznqxvupdmnyiidqpnbvgjraszbvvztpapxmomnghfaywkzlrupvjpcvascgvstqmvuveiiixjmdofdwyvhgkydrnfuojhzulhobyhtsxmcovwmamjwljioevhafdlpjpmqstguqhrhvsdvinphejfbdvrvabthpyyphyqharjvzriosrdnwmaxtgriivdqlmugtagvsoylqfwhjpmjxcysfujdvcqovxabjdbvyvembfpahvyoybdhweikcgnzrdqlzusgoobysfmlzifwjzlazuepimhbgkrfimmemhayxeqxynewcnynmgyjcwrpqnayvxoebgyjusppfpsfeonfwnbsdonucaipoafavmlrrlplnnbsaghbawooabsjndqnvruuwvllpvvhuepmqtprgktnwxmflmmbifbbsfthbeafseqrgwnwjxkkcqgbucwusjdipxuekanzwimuizqynaxrvicyzjhulqjshtsqswehnozehmbsdmacciflcgsrlyhjukpvosptmsjfteoimtewkrivdllqiotvtrubgkfcacvgqzxjmhmmqlikrtfrurltgtcreafcgisjpvasiwmhcofqkcteudgjoqqmtucnwcocsoiqtfuoazxdayricnmwcg"));
    }

    static int counter = 0;

    public String longestPalindrome(String s) {
        // 就是对称的子串
        // 双指针递归？不断对比前后的字符
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.DAYS, new LinkedBlockingQueue<>());
        try {
            List<Future<String>> ls = new LinkedList<>();
            for (int i = 0; i < s.length(); i++) {
                int finalInt = i;
                Future<String> ret = executor.submit(() -> recursiveCalculation(s, finalInt, s.length()));
                ls.add(ret);
            }

            return ls.stream().map(e -> {
                try {
                    return e.get();
                } catch (Exception ignored) {
                    return null;
                }
            }).max(Comparator.comparing(String::length)).orElse("");
        } finally {
            executor.shutdown();
        }

    }

    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final Map<String, Boolean> palindromicCache = new ConcurrentHashMap<>();

    public String recursiveCalculation(String s, int start, int end) {
        String key = String.format("%s:%s", start, end);
        Boolean cached = palindromicCache.get(key);
        if ((cached != null && cached) || (cached == null && isPalindromic(s, start, end))) {
            palindromicCache.put(key, true);
            return s.substring(start, end);
        }
        palindromicCache.put(key, false);
        if (start >= end) {
            return "";
        }

//        String key1 = String.format("%s:%s", start + 1, end);
//        String ret1 = cache.get(key1);
//        if (ret1 == null) {
//            String ret = recursiveCalculation(s, start + 1, end);
//            cache.put(key1, ret);
//        }
        String key2 = String.format("%s:%s", start, end - 1);
        String ret2 = cache.get(key2);
        if (ret2 == null) {
            String ret = recursiveCalculation(s, start, end - 1);
            cache.put(key2, ret);
        }
//        ret1 = cache.get(key1);
        ret2 = cache.get(key2);
//        return ret1.length() > ret2.length() ? ret1 : ret2;
        return ret2;
    }

    public boolean isPalindromic(String s, int start, int end) {
        // end exclusively.
        // start inclusively.

        if (start >= end) {
            return false;
        }

        for (int i = start, j = end - 1; i < j;) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

}
