package nil.ed.test.core.onlybean;

import nil.ed.test.core.TestImportNonConfigurationAnno;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * @author lidelin.
 */
public class BeanAnnoTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(BeanAnnoConfig.class);
        ctx.refresh();
        Object obj1 = ctx.getBean("beanName1");
        Object obj2 = ctx.getBean("beanName2");
        Assert.isTrue(obj1 == obj2, "expect Equals");

        Object dupBean = ctx.getBean("dupBean");
        Object dupBean2 = ctx.getBean("dupBean2");
        Assert.isTrue(dupBean == dupBean2, "expect Equals");
    }
}
