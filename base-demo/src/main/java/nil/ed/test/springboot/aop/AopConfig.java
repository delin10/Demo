package nil.ed.test.springboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lidelin.
 */
@Configuration
@Aspect
public class AopConfig {

    @Pointcut("execution(public void insert(..))")
    public void pointcut() {
        //@annotation(nil.ed.test.springboot.aop.AopConfig.Anno)
    }

    @Before("pointcut()")
    public void before(JoinPoint jp) {
        System.out.println("before");
    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around");
        pjp.proceed();
    }

    @After("pointcut()")
    public void after(JoinPoint jp) {
        System.out.println("before");
    }

//    @Bean
//    public ProxyFactoryBean proxyFactoryBean() throws ClassNotFoundException {
//        ProxyFactoryBean bean = new ProxyFactoryBean();
//        bean.setProxyTargetClass(false);
//        bean.setProxyInterfaces(new Class[]{TestBeanInterface.class});
//        bean.setInterceptorNames("simpleMethodInterceptor");
//        bean.addAdvisor(new TestIntroductionAdvisor());
//        return bean;
//    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Anno {

    }

}
