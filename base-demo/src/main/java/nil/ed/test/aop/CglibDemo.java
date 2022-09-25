package nil.ed.test.aop;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

/**
 * @author lidelin10
 * @date 2022/3/31 上午9:51
 */
public class CglibDemo {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestInterfaceImpl.class);
        enhancer.setCallback(new CglibAop(new TestInterfaceImpl()));
        TestInterfaceImpl instance = (TestInterfaceImpl) enhancer.create();
        instance.test();
    }
    static class CglibAop implements InvocationHandler {

        private final Object target;

        public CglibAop(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Cglib Aop...");
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
