package nil.ed.test.tool;

import java.util.stream.IntStream;

public class IdentifyNumCalculator {

    private static final String MAPPING = "10X98765432";

    private static final int ID_LEN = 18;

    private static final int[] WEIGHT = new int[] {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

    public static String verifyAndUpdate(String id) {
        if (id.length() != ID_LEN) {
            return "";
        }
        int modResult = IntStream.range(0, ID_LEN)
                .map(i -> (id.charAt(i) - '0') * WEIGHT[i])
                .sum() % 11;
        if (id.charAt(ID_LEN - 1) != MAPPING.charAt(modResult)) {
            return id.substring(0, ID_LEN - 1) + MAPPING.charAt(modResult);
        }
        return id;
    }

    public static void main(String[] args) {
        // 11010519491231002X
        System.out.println(verifyAndUpdate("110105194912310020"));
    }

}
