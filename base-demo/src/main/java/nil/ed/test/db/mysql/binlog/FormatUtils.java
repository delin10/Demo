package nil.ed.test.db.mysql.binlog;

/**
 * @author lidelin.
 */
public class FormatUtils {
    public static class Line {
        public static void formatKv(Object name, Object value) {
            System.out.printf("%s: %s%n", name, value);
        }
    }
}
