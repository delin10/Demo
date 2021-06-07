package powermock;

/**
 * @author lidelin.
 */
public class SuppressInitClassDemo {

    public static Object o = new Object();

    static {
        System.out.println("static init");
    }

    public static void staticInvoke() {
        System.out.println("Static invoke!");
    }

}
