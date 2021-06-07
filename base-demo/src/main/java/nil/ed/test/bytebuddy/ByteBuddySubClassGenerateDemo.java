package nil.ed.test.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.Super;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * byte buddy 生成子类注意事项：
 * 1、访问级别为public
 * 2、bytebuddy会自动生成和父类匹配的构造函数，不需要自己额外进行定义！
 * 3、对于父类没有构造函数情况，bytebuddy无法为子类生成默认构造函数.
 */
public class ByteBuddySubClassGenerateDemo {

    public static void main(String[] args) throws Exception {
        fixedFieldDemo();
        // out: Hello Bar
        delegateDemo();
        defNewFieldAndMethod();
        defNewDefaultConstruct();
        advice();
        System.out.println("done");
    }

    private static void fixedFieldDemo() throws IllegalAccessException, InstantiationException {
        DynamicType.Unloaded<?> unloadedType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.isToString())
                .intercept(FixedValue.value("Hello World ByteBuddy!"))
                .make();
        Class<?> clz = unloadedType.load(ByteBuddySubClassGenerateDemo.class.getClassLoader())
                .getLoaded();
        System.out.println(clz.getCanonicalName());
        System.out.println(clz.newInstance().toString());
    }

    private static void delegateDemo() throws IllegalAccessException, InstantiationException {
        new ByteBuddy()
                .subclass(Foo.class)
                .method(ElementMatchers.named("sayFoo")
                        .and(ElementMatchers.isDeclaredBy(Foo.class)))
                .intercept(MethodDelegation.to(Bar.class))
                .make()
                .load(ByteBuddySubClassGenerateDemo.class.getClassLoader())
                .getLoaded()
                .newInstance()
                .sayFoo();
    }

    private static void defNewFieldAndMethod() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Object obj = new ByteBuddy()
                .subclass(Object.class)
                .name("NewDefClass")
                .defineMethod("custom", String.class, Modifier.PUBLIC)
                .intercept(MethodDelegation.to(Bar.class))
                .defineField("x", String.class, Modifier.PRIVATE | Modifier.FINAL | Modifier.STATIC)
                .value("______ok____")
                .make()
                .load(ByteBuddySubClassGenerateDemo.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded()
                .newInstance();
        Method method = obj.getClass().getDeclaredMethod("custom");
        method.invoke(obj);
        Field f = obj.getClass().getDeclaredField("x");
        f.setAccessible(true);
        System.out.println(f.get(obj));
    }

    private static void advice() throws IllegalAccessException, InstantiationException {
        TestMethodMonitor obj = new ByteBuddy()
                .subclass(TestMethodMonitor.class)
                .method(ElementMatchers.named("sayFoo"))
                .intercept(Advice.to(MethodMonitor.class).withExceptionHandler((MethodDescription instrumentedMethod, TypeDescription instrumentedType) -> {
                    System.out.println("Error");
                    return null;
                }))
                .make()
                .load(ByteBuddySubClassGenerateDemo.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded()
                .newInstance();
        System.out.println(obj.sayFoo("TEST"));
    }

    private static void defNewDefaultConstruct() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object obj = new ByteBuddy()
                .subclass(NonDefaultConstruct.class)
                .make()
                .load(ByteBuddySubClassGenerateDemo.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded()
                .getConstructor(String.class)
                .newInstance("ok");
    }

    public static class Foo  {
        public String sayFoo() {
            System.out.println("Hello Foo!");
            throw new IllegalStateException();
//            return "Foo";
        }
    }

    public static class Bar  {
        @RuntimeType
        public static String sayBar(@Super Object superObj) {
            System.out.println("Hello Bar!");
            return "Bar";
        }
    }

    public static class NonDefaultConstruct {
        public NonDefaultConstruct(String in) {
            System.out.println(in);
        }
    }

    public static class NoBody {
        public static void init() {

        }
    }

    public static class TestMethodMonitor  {
        public String sayFoo(String string) {
            System.out.println("Hello Foo!");
            System.out.println(string);
            return "Foo";
        }
    }


    public static class MethodMonitor {
        @Advice.OnMethodEnter(skipOn = TestMethodMonitor.class)
        public static Object before() {
            System.out.println("Method enter");
            return new Object();
        }

        // 抛出异常的情况无法处理
        @Advice.OnMethodExit(onThrowable = Exception.class)
        public static Object after(@Advice.Thrown Exception e,
                                 @Advice.Return Object returnValue,
                                 @Advice.AllArguments Object[] params,
                                 @Advice.Enter Object enterReturnValue) {

            System.err.println(e);
            System.out.println("Method exit");
            System.out.println("return value = " + returnValue);
            System.out.println("params value = " + Arrays.toString(params));
            System.out.println("enterReturnValue value = " + enterReturnValue);
            return "sdfghm";
        }
    }

}
