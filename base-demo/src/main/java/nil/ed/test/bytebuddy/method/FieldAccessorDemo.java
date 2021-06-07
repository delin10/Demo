package nil.ed.test.bytebuddy.method;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.not;

public class FieldAccessorDemo {

    /**
     * 必须是public.
     * Exception in thread "main" java.lang.IllegalAccessError: class nil.ed.test.bytebuddy.method.FieldAccessorDemo$UserType$ByteBuddy$ToXuUbRs cannot access its superclass nil.ed.test.bytebuddy.method.FieldAccessorDemo$UserType.
     */
    public static class UserType {
        public String doSomething() { return null; }
    }

    public interface Interceptor {
        String doSomethingElse();
    }

    public interface InterceptionAccessor {
        Interceptor getInterceptor();
        void setInterceptor(Interceptor interceptor);
    }

    public interface InstanceCreator {
        Object makeInstance();
    }

    public static class HelloWorldInterceptor implements Interceptor {
        @Override
        public String doSomethingElse() {
            return "Hello World!";
        }
    }

    public static void main(String...args) throws IllegalAccessException, InstantiationException {
        new FieldAccessorDemo().test();
    }

    public void test() throws IllegalAccessException, InstantiationException {
        Class<? extends UserType> dynamicUserType = new ByteBuddy()
                .subclass(UserType.class)
                .method(not(isDeclaredBy(Object.class)))
                .intercept(MethodDelegation.toField("interceptor"))
                .defineField("interceptor", Interceptor.class, Visibility.PRIVATE)
                .implement(InterceptionAccessor.class).intercept(FieldAccessor.ofBeanProperty())
                .make()
                .load(FieldAccessorDemo.class.getClassLoader())
                .getLoaded();

        InstanceCreator factory = new ByteBuddy()
                .subclass(InstanceCreator.class)
                .method(not(isDeclaredBy(Object.class)))
                .intercept(MethodDelegation.toConstructor(dynamicUserType))
                .make()
                .load(dynamicUserType.getClassLoader())
                .getLoaded().newInstance();

        UserType userType = (UserType) factory.makeInstance();
        ((InterceptionAccessor) userType).setInterceptor(new HelloWorldInterceptor());
    }

}
