package nil.ed.test.bytebuddy.method;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.ExceptionMethod;
import net.bytebuddy.matcher.ElementMatchers;

public class ExpMethodDemo {

    public interface TestInt {
        void sayHi();
    }

    public static void main(String...args) throws IllegalAccessException, InstantiationException {
        Object maked = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(ExceptionMethod.throwing(UnsupportedOperationException.class))
                .make()
                .load(ExpMethodDemo.class.getClassLoader())
                .getLoaded()
                .newInstance();
        try {
            System.out.println(maked.toString());
        } catch (UnsupportedOperationException uo) {
            uo.printStackTrace();
        }

        maked = new ByteBuddy()
                .subclass(TestInt.class)
                .method(ElementMatchers.named("sayHi"))
                .intercept(ExceptionMethod.throwing(UnsupportedOperationException.class))
                .make()
                .load(ExpMethodDemo.class.getClassLoader())
                .getLoaded()
                .newInstance();
        ((TestInt) maked).sayHi();
    }
}
