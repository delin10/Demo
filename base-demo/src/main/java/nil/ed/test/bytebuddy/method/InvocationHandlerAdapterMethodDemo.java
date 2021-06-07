package nil.ed.test.bytebuddy.method;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InvocationHandlerAdapterMethodDemo {

    public static class TestInt {

        private final String msg;

        public TestInt(String msg) {
            this.msg = msg;
        }

        public void sayHi() {
            System.out.println(msg);
        }
    }

    public static class TestInvHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("proxy = " + proxy);
            System.out.println("method = " + method);
            System.out.println("args = " + Arrays.toString(args));
            return null;
        }
    }

    public static void main(String...args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        TestInt maked = (TestInt) new ByteBuddy()
                .subclass(TestInt.class, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
                .method(ElementMatchers.named("sayHi"))
                .intercept(InvocationHandlerAdapter.of(new TestInvHandler()))
                .make()
                .load(InvocationHandlerAdapterMethodDemo.class.getClassLoader())
                .getLoaded()
                .getConstructors()[0].newInstance("mamam");
        maked.sayHi();
    }
}
