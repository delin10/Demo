package nil.ed.test.type;

import org.springframework.core.ResolvableType;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * @author lidelin.
 */
public class TestType {

    public static class TestClass<L, M, N> {

    }

    public static class Test extends TestClass<Integer, Long, List<Double>> {

    }

    public class TestInnerClass<T> {
        public TestInnerClass(T x, T c, @RequestParam Integer a) {

        }

        public class InnerInnerClass {

        }
    }

    public Object method(Integer a, Integer b, Integer c) {
        return null;
    }

    public <T> Object method(T a) {
        return null;
    }

    public  <X extends Integer> void test(List<? super Test> ls, X a) {

    }

    public  <X extends Integer> void test2(List<? super Test> ls, X a) {

    }

    public static void main(String[] args) throws NoSuchMethodException {
        System.out.println(Test.class.getGenericSuperclass());
        Method m = TestType.class.getMethod("method", Integer.class, Integer.class, Integer.class);
        Method m2 = TestType.class.getMethod("method", Object.class);
        System.out.println(Arrays.toString(m.getGenericParameterTypes()));
        System.out.println(m.getGenericReturnType());
        System.out.println(Arrays.toString(m2.getGenericParameterTypes()));
        System.out.println(m2.getGenericReturnType());
        System.out.println(Arrays.toString(TestInnerClass.class.getDeclaredConstructors()));
        Constructor<?> constructor = TestInnerClass.class.getDeclaredConstructors()[0];
        System.out.println(constructor.getParameterCount());
        System.out.println(constructor + ": " + Arrays.toString(constructor.getGenericParameterTypes()));
        System.out.println(constructor + ": " + ((TypeVariable)constructor.getGenericParameterTypes()[0]).getBounds()[0]);

        System.out.println(Arrays.toString(TestInnerClass.InnerInnerClass.class.getDeclaredConstructors()));
        constructor = TestInnerClass.class.getDeclaredConstructors()[0];
        System.out.println(constructor.getParameterCount());
        System.out.println(Arrays.toString(constructor.getGenericParameterTypes()));

        System.out.println(Arrays.toString(constructor.getParameters()[0].getAnnotations()));
        System.out.println(UseAnnotation.COMPLETE);
        System.out.println(UseAnnotation.class.getDeclaredConstructors()[0]
                .getParameterTypes()[0].getAnnotations().length);
        Method testMethod = TestType.class.getMethod("test", List.class, Integer.class);
        System.out.println(testMethod.getParameters()[0].getType());
        System.out.println(testMethod.getParameters()[0].getType());
        System.out.println(testMethod.getParameters()[0].getParameterizedType());
        System.out.println(((ParameterizedType) testMethod.getParameters()[0].getParameterizedType()).getRawType());
        System.out.println("Bounds " + testMethod.getParameters()[0].getType().getTypeParameters()[0]);
        System.out.println(testMethod.getParameters()[0].getType().getTypeName());
        System.out.println(Arrays.toString(testMethod.getParameters()[0].getType().getTypeParameters()));
        System.out.println(testMethod.getParameters()[1].getType());
        System.out.println(testMethod.getParameters()[1].getParameterizedType());
        System.out.println(testMethod.getParameters()[1].getType().getTypeName());
        System.out.println(Arrays.toString(testMethod.getParameters()[1].getType().getTypeParameters()));

        Type[] types = testMethod.getGenericParameterTypes();
        for (Type type : types) {
            if (type instanceof WildcardType) {
                System.out.println("Wildcard Type: " + type);
                System.out.println("Wildcard Type: " + Arrays.toString(((WildcardType) type).getLowerBounds()));
                System.out.println("Wildcard Type: " + Arrays.toString(((WildcardType) type).getUpperBounds()));
            } if (type instanceof ParameterizedType) {
                Type[] paramedTypes = ((ParameterizedType) type).getActualTypeArguments();
                System.out.println(Arrays.toString(paramedTypes));
                if (paramedTypes[0] instanceof WildcardType) {
                    System.out.println("Paramed wildcard: " + Arrays.toString(((WildcardType)paramedTypes[0]).getUpperBounds()));
                    System.out.println("Paramed wildcard: " + Arrays.toString(((WildcardType)paramedTypes[0]).getLowerBounds()));
                }
            } else {
                System.out.println(type.getClass());
            }
        }
        ResolvableType resolvableType0 = ResolvableType.forMethodParameter(testMethod, 0);
        System.out.println(resolvableType0);
        System.out.println(Arrays.toString(resolvableType0.getGenerics()));

    }

    public enum UseAnnotation {
        COMPLETE(" finished ");

        private final String mString;

        UseAnnotation(@Nonnull String aString) {
            mString = aString;
        }

        public @Nonnull String getString() {
            return mString;
        }
    }
}
