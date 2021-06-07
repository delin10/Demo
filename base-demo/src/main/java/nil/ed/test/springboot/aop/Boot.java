package nil.ed.test.springboot.aop;

import org.aopalliance.aop.Advice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;

/**
 * @author delin10
 **/
@EnableAspectJAutoProxy(proxyTargetClass = false)
@SpringBootApplication
public class Boot {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Boot.class);
        TestBeanInterface bean = (TestBeanInterface) context.getBean("testBean");
        bean.test();
        TestBeanInterface targetBean = (TestBeanInterface) context.getBean("proxyFactoryBean");
        targetBean.test();
        Lockable lockable = (Lockable) targetBean;
        targetBean.setTest();
        System.out.println(Arrays.toString(targetBean.getClass().getInterfaces()));
        System.out.println(Arrays.toString(targetBean.getClass().getClasses()));
    }
}
