package nil.ed.test.core.fuckspringboot;

import nil.ed.test.core.TestImportNonConfigurationAnno;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lidelin.
 */
public class AnnotationConfigCtxTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(TestImportNonConfigurationAnno.class);
        ctx.refresh();
        Object obj = ctx.getBean("definedInNonConfiguration");
        System.out.println(obj);
    }
}
