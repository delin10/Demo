package nil.ed.test.bytebuddy.annotation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.attribute.AnnotationRetention;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;

/**
 * 1、[ OK ] 在类型上、方法上、变量上定义注解和属性值.
 * 2、[ OK ] 重写方法时继承注解或者抛弃注解.
 * @author lidelin.
 */
public class AnnotationOverrrideDemo {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface RuntimeDefinition {
        String value() default "";
    }

    public static class TestClass {

        private String field;

        @RuntimeDefinition
        public void stayWith() {
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        Class<?> generatedClass = new ByteBuddy()
                .with(AnnotationRetention.DISABLED)
                .subclass(TestClass.class)
                .method(ElementMatchers.named("stayWith"))
                .intercept(MethodCall.invokeSuper())
                .make()
                .load(AnnotationOverrrideDemo.class.getClassLoader())
                .getLoaded();
        AnnotatedElement e = generatedClass.getDeclaredMethod("stayWith");
        System.out.println(e.getAnnotation(RuntimeDefinition.class));

    }

}
