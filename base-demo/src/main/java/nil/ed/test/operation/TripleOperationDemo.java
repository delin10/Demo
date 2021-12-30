package nil.ed.test.operation;

/**
 * @author lidelin.
 */
public class TripleOperationDemo {
    public static void main(String[] args) {
        int t = 0;
        int x = 0;
        System.out.println(args.length != 0 ? (t = 100) : (x = 10));
        // t = 0, x = 10
        System.out.printf("t = %d, x = %d", t, x);
    }
}
