package nil.ed.test.springboot.scope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author delin10
 **/
@SpringBootApplication
public class ScopeBoot {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ScopeBoot.class);
        TestClass obj1 = ctx.getBean("testClass", TestClass.class);
        TestClass obj2 = ctx.getBean("scopedProxyFactoryBeanForTestClass", TestClass.class);
        TestClass obj21 = ctx.getBean("scopedProxyFactoryBeanForTestClass", TestClass.class);
        TestClass2 obj3 = ctx.getBean(TestClass2.class);
        TestClassSingle obj4 = ctx.getBean(TestClassSingle.class);
        TestClassScopeProxyMode obj5 = ctx.getBean(TestClassScopeProxyMode.class);
        TestClassScopeProxyMode obj6 = ctx.getBean(TestClassScopeProxyMode.class);
        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj21);
        System.out.println(obj3);
        System.out.println(obj4);
        System.out.println(obj5);
        System.out.println(obj6);
    }
}
