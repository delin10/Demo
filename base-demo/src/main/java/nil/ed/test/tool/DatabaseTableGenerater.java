package nil.ed.test.tool;

import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * @author lidelin.
 */
public class DatabaseTableGenerater {

    public static void main(String[] args) {
        PropertyNamingStrategy.SnakeCaseStrategy snakeCaseStrategy = new PropertyNamingStrategy.SnakeCaseStrategy();
        Class<?>[] alz = new Class[] { };

        for (Class<?> clz : alz) {
            Field[] fss = clz.getDeclaredFields();
            StringBuilder builder = new StringBuilder();
            builder.append("create table ").append(snakeCaseStrategy.translate(clz.getSimpleName().substring(0, clz.getSimpleName().length() - 2))).append('(');
            builder.append("\n");
            for (Field f : fss) {
                builder.append(snakeCaseStrategy.translate(f.getName())).append(" ").append(f.getType().getSimpleName());
                if (f.getName().equals("id")) {
                    builder.append(" primary key");
                }
                builder.append(",\n");
            }
            builder.delete(builder.length() - 1, builder.length());
            builder.append("\n);");
            System.out.println(builder);
        }
    }

}
