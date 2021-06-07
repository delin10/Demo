package powermock;

/**
 * @author lidelin.
 */
public class StaticMethodProvider {

    static {
        System.out.println("static init");
    }

    public static void provide() {
        System.out.println("ok");
    }

}
