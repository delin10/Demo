package nil.ed.test.bytebuddy.annotation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;

/**
 * 1、[ OK ] 在类型上、方法上、变量上定义注解和属性值.
 * 2、[ OK ] 重写方法时继承注解或者抛弃注解.
 * @author lidelin.
 */
public class AnnotationDefineDemo {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface RuntimeDefinition {
        String value() default "";
    }

    public static class TestClass {

        private String field;

        public void stayWith() {

        }
    }

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        Class<?> generatedClass = new ByteBuddy()
                .subclass(TestClass.class)
                .annotateType(AnnotationDescription.Builder.ofType(TypeDescription.ForLoadedType.of(RuntimeDefinition.class)).define("value", "type").build())
                .method(ElementMatchers.named("stayWith"))
                .intercept(MethodCall.invokeSuper())
                .annotateMethod(AnnotationDescription.Builder.ofType(TypeDescription.ForLoadedType.of(RuntimeDefinition.class)).define("value", "method").build())
                .defineField("field", TypeDescription.ForLoadedType.of(String.class), Modifier.PRIVATE)
                .annotateField(AnnotationDescription.Builder.ofType(TypeDescription.ForLoadedType.of(RuntimeDefinition.class)).define("value", "field").build())
                .make()
                .load(AnnotationDefineDemo.class.getClassLoader())
                .getLoaded();
        System.out.println(generatedClass.getAnnotation(RuntimeDefinition.class).value());
        AnnotatedElement e = generatedClass.getDeclaredMethod("stayWith");
        System.out.println(e.getAnnotation(RuntimeDefinition.class).value());
        e = generatedClass.getDeclaredField("field");
        System.out.println(e.getAnnotation(RuntimeDefinition.class).value());

    }

}
