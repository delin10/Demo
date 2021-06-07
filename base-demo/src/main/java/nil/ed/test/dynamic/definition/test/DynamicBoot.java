package nil.ed.test.dynamic.definition.test;

import nil.ed.test.dynamic.definition.RefreshableSingletonBeanSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "nil.ed.test.dynamic.definition.test")
public class DynamicBoot {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DynamicBoot.class, args);
        RefreshableSingletonBeanSource refreshableSingletonBeanSource = context.getBean(RefreshableSingletonBeanSource.class);
        Object obj1 = context.getBean("dynamicBean0");
        Object obj2 = context.getBean("dynamicBean0");

        Object obj3 = context.getBean("dynamicBean1");
        Object obj4 = context.getBean("dynamicBean1");
        refreshableSingletonBeanSource.refresh();
        Object obj5 = context.getBean("dynamicBean1");
        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj3);
        System.out.println(obj4);
        System.out.println(obj5);
    }
}
