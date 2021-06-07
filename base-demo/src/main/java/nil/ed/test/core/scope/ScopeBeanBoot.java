package nil.ed.test.core.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author lidelin.
 */
@SpringBootApplication(scanBasePackages = "nil.ed.test.core.scope")
public class ScopeBeanBoot {
    public static void main(String[] args) {
        ApplicationContext application = SpringApplication.run(ScopeBeanBoot.class, args);
        Object obj1 = application.getBean("scopedBean");
        Object obj2 = application.getBean("scopedBean");
        Object obj3 = application.getBean("scopeComponent");
        Object obj4 = application.getBean("scopeComponent");
        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj3);
        System.out.println(obj4);
    }
}
