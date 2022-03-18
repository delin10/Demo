package nil.ed.test.java;

import java.lang.reflect.Field;

/**
 * @author lidelin.
 */
public class SwitchEnumDemo {

    private enum TestSwitch {A, B, C, D}

    public static void main(String[] args) throws ClassNotFoundException {
        for (Field f : TestSwitch.class.getDeclaredFields()) {
            System.out.println(f.getName());
        }
        for (Field f : Class.forName("nil.ed.test.java.SwitchEnumDemo$1").getDeclaredFields()) {
            System.out.println(f.getName());
        }
        test(TestSwitch.A);
    }

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
