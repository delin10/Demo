package nil.ed.test.tool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lidelin.
 */
public class SQLGenerator {

    public static void main(String[] args) {

        long[] sessionIds = new long[] {2000L, 2001L};

//        StringBuilder builder = new StringBuilder();
//        builder.append("insert into t_test(`sub_distribution_id`, `session_id`) values ");
//        for (long sessionId : sessionIds) {
//            long num = ThreadLocalRandom.current().nextLong(4000, 20000);
//            for (int i = 0; i < num; ++i) {
//                builder.append("(").append(sessionId).append(String.format("%05d", i)).append(",").append(sessionId).append("),\n");
//            }
//        }
//        builder.delete(builder.length() - 2, builder.length()).append(";");
//        System.out.println(builder.toString());
        List<String> str = new LinkedList<>();
        int start = 0;
        int end = 500;
        long sid = 2000;
        for (int i = start; i < end; ++i) {
            str.add(String.format("%d%05d", sid, i));
        }
        System.out.println(String.join(",", str));

    }

}
