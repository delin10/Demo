package nil.ed.test.type;

import java.lang.reflect.Method;

/**
 * @author lidelin.
 */
public class TestBridge {

    private static class ParentClass {

        protected void test() {

        }

    }

    private static class SubClass extends ParentClass {
        public void test() {
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        Method[] methods = SubClass.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            System.out.println(method.isBridge());
        }
    }

}
