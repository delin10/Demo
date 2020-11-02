package nil.ed.test.springboot.bean.conditional;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Configuration
public class ProxyConfig {

    // 被代理的类不能被注入？？？
    @Bean
    public ProxyFactoryBean testObject() throws ClassNotFoundException {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setSingleton(true);
        proxyFactoryBean.setTargetName("testObjectTarget");
        proxyFactoryBean.setProxyInterfaces(new Class[]{TestInter.class});
        proxyFactoryBean.setInterceptorNames("testInterceptor");
        return proxyFactoryBean;
    }



    public static class TestInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("start.." + invocation.getMethod().getName());
            Object ret = invocation.proceed();
            System.out.println("complete!");
            return ret;
        }
    }

    public interface TestInter {
        TestObject testReturnThisForProxyTest();
        TestObject testReturnOtherForProxyTest();
    }

    public static class TestObject implements TestInter {

        private TestObject otherTestObject;

        public TestObject() {
        }

        public TestObject(TestObject other) {
            this.otherTestObject = other;
        }

        @Override
        public TestObject testReturnThisForProxyTest() {
            return this;
        }

        @Override
        public TestObject testReturnOtherForProxyTest() {
            return otherTestObject;
        }

    }

}
