package nil.ed.test;

/**
 * @author lidelin.
 */
public class Loop {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        loop10w();
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void loop10w() {
        int j = 1;
        Object obj = new Object();
        for (int i = 0; i < 625_0000; ++i) {
            j = i;
            j = obj.hashCode();
            j = obj.hashCode();
            j = obj.hashCode();
            j = obj.hashCode();
        }
        System.out.println(j);
    }
}
