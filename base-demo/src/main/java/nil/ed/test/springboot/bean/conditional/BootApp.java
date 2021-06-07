package nil.ed.test.springboot.bean.conditional;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;

/**
 * @author delin10
 * @since 2020/7/7
 **/

@SpringBootApplication
public class BootApp implements BeanPostProcessor,ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception{
//        System.setProperty("spring.banner.image.location", "/aa.jpg");
        System.setProperty("spring.banner.location", "/spring.txt");
        SpringApplication.run(BootApp.class);
        ProxyConfig.TestInter testInter = ((ProxyConfig.TestInter) applicationContext.getBean("testObject"));
        System.out.println(testInter.getClass());
        System.out.println(Proxy.newProxyInstance(ProxyConfig.TestInter.class.getClassLoader(), new Class[]{ProxyConfig.TestInter.class}, ((proxy, method, args1) -> {return  null;})));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        BootApp.applicationContext = applicationContext;
    }
}
