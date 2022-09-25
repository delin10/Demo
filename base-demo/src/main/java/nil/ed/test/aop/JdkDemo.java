package nil.ed.test.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lidelin10
 * @date 2022/3/31 上午9:40
 */
public class JdkDemo {

    public static void main(String[] args) {
        TestInterfaceImpl obj = new TestInterfaceImpl();
        TestInterface testInterface = (TestInterface) Proxy.newProxyInstance(JdkDemo.class.getClassLoader(), new Class[] {TestInterface.class}, new JdkAop(obj));
        testInterface.test();
    }

    static class JdkAop implements InvocationHandler {

        private final Object target;

        public JdkAop(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Jdk Aop...");
            return method.invoke(target, args);
        }
    }

    interface TestInterface {
        void test();
    }

    static class TestInterfaceImpl implements TestInterface {

        @Override
        public void test() {
            if (Math.random() > 0.8D) {
                return;
            }
            System.out.println("ok");
            test();
        }

    }
}
