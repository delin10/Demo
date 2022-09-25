package nil.ed.test.bytebuddy.method;

import com.sun.xml.internal.fastinfoset.algorithm.UUIDEncodingAlgorithm;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.redisson.liveobject.resolver.UUIDGenerator;

import java.util.UUID;

public class StaticMethodStubDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Class.forName("nil.ed.test.bytebuddy.method.StaticMethodStubDemo$Test");
            } catch (Exception e) {

            }
            System.out.println(Test.test());
            System.out.println(Test.class.getClassLoader().getClass());
        });
        t.setContextClassLoader(new TestClassLoader());
        t.start();
        t.join();
    }

    public static class TestClassLoader extends ClassLoader {


        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            System.out.println(name);
            if ("nil.ed.test.bytebuddy.method.StaticMethodStubDemo$Test".equals(name)) {
                return new ByteBuddy()
                        .redefine(Test.class)
                        .method(ElementMatchers.hasMethodName("test").and(ElementMatchers.isStatic()))
                        .intercept(FixedValue.value("intercept"))
                        .make()
                        .load(StaticMethodStubDemo.class.getClassLoader())
                        .getLoaded();
            }
            return super.loadClass(name);
        }
    }

    public static class Test {
        public static String test() {
            return "ok";
        }
    }
}
