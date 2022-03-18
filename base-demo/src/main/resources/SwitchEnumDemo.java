package nil.ed.test.java;

/**
 * @author lidelin.
 */
public class SwitchEnumDemo {

    private enum TestSwitch {A, B, C, D}

    private static void test(TestSwitch testSwitch) {
        switch (testSwitch) {
            case A:
                System.out.println("A");
                break;
            case B:
                System.out.println("B");
                break;
            case C:
                System.out.println("C");
                break;
            case D:
                System.out.println("D");
                break;
            default:
                System.out.println("Default");
        }
    }

}
