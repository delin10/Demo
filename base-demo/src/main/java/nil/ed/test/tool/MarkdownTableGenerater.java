package nil.ed.test.tool;

import java.lang.reflect.Field;

/**
 * @author lidelin.
 */
public class MarkdownTableGenerater {

    public static void main(String[] args) {
        Class<?> modelClazz = Void.class;
        Field[] fs = modelClazz.getDeclaredFields();
        for (Field f : fs) {
            System.out.println("|" + f.getName() + "|" + f.getType().getSimpleName() + "|      |");
        }
    }

}
