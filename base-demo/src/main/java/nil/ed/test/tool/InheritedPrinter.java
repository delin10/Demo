package nil.ed.test.tool;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.validation.DataBinder;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lidelin.
 */
public class InheritedPrinter {

    public static void main(String[] args) {
        DataBinder dataBinder = new DataBinder(new Object());
        dataBinder.bind(new MutablePropertyValues());
        Class<?> clazz = ServletWebServerApplicationContext.class;
        int level = 0;
        outPretty(level, clazz.getSimpleName());
        traverse(clazz, level + 1);
    }

    private static void traverse(Class<?> clazz, int level) {
        if (clazz == null || Object.class.equals(clazz)) {
            return;
        }
        Class<?> superClass = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        if (superClass != null && superClass != Object.class) {
            outPretty(level, superClass.getSimpleName());
            traverse(superClass, level + 1);
        }
        if (interfaces.length != 0) {
            for (Class<?> clz : interfaces) {
                outPretty(level, clz.getSimpleName());
                traverse(clz, level + 1);
            }
        }
    }

    public static void outPretty(int level, Object out) {
        String indent = IntStream.range(0, level).mapToObj(i -> "  ").collect(Collectors.joining());
        System.out.println(indent + level + ":"+ out);
    }
}
