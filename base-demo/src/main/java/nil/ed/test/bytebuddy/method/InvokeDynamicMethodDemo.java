package nil.ed.test.bytebuddy.method;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.StubMethod;
import net.bytebuddy.matcher.ElementMatchers;

public class InvokeDynamicMethodDemo {

    public static void main(String...args) throws IllegalAccessException, InstantiationException {
        Object maked = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(StubMethod.INSTANCE)
                .make()
                .load(StubMethodDemo.class.getClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(maked.toString());
    }

}
