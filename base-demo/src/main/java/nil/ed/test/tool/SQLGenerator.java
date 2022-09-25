package nil.ed.test.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.IOUtils;

/**
 * @author lidelin.
 */
public class SQLGenerator {
    public static void main(String[] args) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 800; ++i) {
            stringBuilder.append("insert into `t_test`(session_id, sub_id) values");
            for (int j = 1000; j < 2000; ++j) {
                stringBuilder.append("(" + (1000 + i) + "," + Long.valueOf(i + String.valueOf(j)) + "),");
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            stringBuilder.append(";\n");
        }
        IOUtils.write(stringBuilder.toString(), Files.newOutputStream(Paths.get("/Users/lidelin/delin/Data/SQL", "test.sql"),StandardOpenOption.CREATE));
    }
}
