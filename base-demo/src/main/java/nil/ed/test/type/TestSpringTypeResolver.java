package nil.ed.test.type;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author lidelin.
 */
public class TestSpringTypeResolver {

    static class Test {

    }

    public <T1 extends Test, T2 extends T1, T3> void test(List<?> arg1,
                                                          List<List<?>> arg2,
                                                          List<T1> arg3,
                                                          List<T2> arg4,
                                                          List<T3> arg5,
                                                          T3 arg6,
                                                          T1 arg7) {
    }

    public static void main(String[] args) {
        Method method = Stream.of(TestSpringTypeResolver.class.getMethods())
                .filter(m -> m.getName().equalsIgnoreCase("test"))
                .findFirst()
                .get();
        System.out.println("method: " + method.getName());
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            System.out.println("param " + parameters[i].getName() + ": " + parameters[i].getParameterizedType() + "...");
            resolveInternal(ResolvableType.forMethodParameter(method, i), 0);
            System.out.println("----------------------------------");
            System.out.println("----------------------------------");
            System.out.println("----------------------------------");
            System.out.println("----------------------------------");
        }
    }



    public static void resolveInternal(ResolvableType type, int level) {
        if (type == ResolvableType.NONE) {
            outPretty(level, "NONE");
            return;
        }
        outPretty(level, "raw Class ===> " + type.getRawClass());
        outPretty(level, "resolved ===> " + type.resolve());
        outPretty(level + 4, "component type recursive resolve");
        if (type.getComponentType() == ResolvableType.NONE) {
            outPretty(level + 8, "Non component Type. ");
        } else {
            resolveInternal(type.getComponentType(), level + 8);
        }
        outPretty(level + 4, "generic type recursive resolve");
        if (type.hasGenerics()) {
            for (ResolvableType resolvableType : type.getGenerics()) {
                resolveInternal(resolvableType, level + 8);
            }
        } else {
            outPretty(level + 8, "Non Generic Type. ");
        }
        for (int i = 2; i < 5; ++i) {
            outPretty(level + 4, "Nested[" + i + "] = " + type.getNested(i));
            if (type.getNested(i) != ResolvableType.NONE) {
                resolveInternal(type.getNested(i), level + 8);
            }
        }
    }

    public static void outPretty(int level, Object out) {
        String indent = IntStream.range(0, level).mapToObj(i -> "  ").collect(Collectors.joining());
        System.out.println(indent + out);
    }


}
