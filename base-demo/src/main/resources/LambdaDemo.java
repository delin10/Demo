package nil.ed.test.java;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author lidelin.
 */
public class LambdaDemo {
    static Consumer<String> consumer = System.out::println;
    static Consumer<String> consumer2 = t -> {
        if (t != null) {
            System.out.println(t);
        }
    };
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        consumer.accept("test");
        System.out.println(Arrays.toString(consumer.getClass().getInterfaces()));
        System.out.println(consumer.getClass().getSuperclass());
        System.out.println(Arrays.toString(consumer.getClass().getDeclaredFields()));
        System.out.println(consumer.getClass().getCanonicalName());
        consumer2.accept("2");
        System.out.println(Arrays.toString(LambdaDemo.class.getDeclaredClasses()));
        System.out.println(LambdaDemo.class.getEnclosingClass());
        System.out.println(Arrays.toString(LambdaDemo.class.getDeclaredFields()));
        System.out.println(Arrays.toString(LambdaDemo.class.getDeclaredMethods()));

        Consumer<String> consumerLocal = new LambdaDemo()::test;
        consumerLocal.accept("test");
    }

    public void test(String x) {
        System.out.println();
    }

    static void test() {
        consumer.accept("test");
        consumer2.accept("2");
    }

}
