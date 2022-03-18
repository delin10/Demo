package nil.ed.test.java;

/**
 * @author lidelin.
 */
public class IfElseDemo {

    static void test(String val) {
        if ("xxx".equals(val)) {
            System.out.println("xxx");
        } else if ("xx".equals(val)) {
            System.out.println("xx");
        } else {
            System.out.println("ok");
        }

        if (val != null) {
            System.out.println("not null");
        }
    }

}
