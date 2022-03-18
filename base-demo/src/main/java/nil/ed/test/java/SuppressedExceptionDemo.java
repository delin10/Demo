package nil.ed.test.java;

/**
 * @author lidelin.
 */
public class SuppressedExceptionDemo {

    public static void main(String[] args) {
        RuntimeException e = new RuntimeException("TEST");
        e.addSuppressed(new RuntimeException());
        e.printStackTrace();
    }

}
