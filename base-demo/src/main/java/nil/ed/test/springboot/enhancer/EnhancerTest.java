package nil.ed.test.springboot.enhancer;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @author lidelin.
 */
public class EnhancerTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallbackFilter(new TestFilter());
        enhancer.setCallback(new TestCallback());
        enhancer.setSuperclass(TestClass.class);
        TestClass testClass = (TestClass)  enhancer.create();
        testClass.testA();
    }

    public static class TestClass {

        public void testA() {

        }
    }

    public static class TestCallback implements InvocationHandler {
        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            System.out.println("intercept");
            return null;
        }
    }



    public static class TestFilter implements CallbackFilter {

        @Override
        public int accept(Method method) {
            return 0;
        }
    }
}
