package nil.ed.test.tool;

import sun.misc.Contended;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lidelin.
 */
@Contended
public class FormatTool {

    public static void main(String[] args) {
        List<List<String>> lines = new LinkedList<>();

        String[][] test = {{"1", "2"}, {"234567890-", "1234567890"}};
        for (String[] row : test) {
            for (String col : row) {
                System.out.printf("%-20s", col);
            }
            System.out.printf("%n");
        }
    }

}
