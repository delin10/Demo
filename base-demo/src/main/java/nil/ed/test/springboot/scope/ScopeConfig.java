package nil.ed.test.springboot.scope;

import org.springframework.aop.scope.ScopedProxyFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * @author lidein.
 */
@Configuration
public class ScopeConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public TestClass testClass() {
        return new TestClass();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public TestClassScopeProxyMode testClassScopeProxyMode() {
        return new TestClassScopeProxyMode();
    }

    @Bean
    public ScopedProxyFactoryBean scopedProxyFactoryBeanForTestClass() {
        ScopedProxyFactoryBean bean = new ScopedProxyFactoryBean();
        bean.setTargetBeanName("testClass");
        bean.setProxyTargetClass(true);
        return bean;
    }

    @Bean
    public TestClassSingle testClassSingle() {
        return new TestClassSingle();
    }

    @Bean
    public TestClassDependSingle testClassDependSingle() {
        return new TestClassDependSingle(testClassSingle());
    }

}
