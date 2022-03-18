package nil.ed.test.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author lidelin.
 */
public class TryCatchDemo {

    public static void main(String[] args) {
        try {
            test();
        } catch (SQLException | IOException e) {

        }
    }

    public static void test() throws SQLException, FileNotFoundException {

    }
}
