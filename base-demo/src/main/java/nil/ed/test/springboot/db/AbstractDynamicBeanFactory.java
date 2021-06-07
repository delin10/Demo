package nil.ed.test.springboot.db;

import com.google.common.base.Splitter;
import lombok.Data;
import nil.ed.test.springboot.db.config.ConfigSource;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Data
public abstract class AbstractDynamicBeanFactory implements BeanDefinitionRegistryPostProcessor {

    private String configSources;
    private String keyPrefixes = "";
    private String keySeparator = ".";
    private String beanName;
    private String initMethod;
    private String destroyMethod;
    private String currentOriginBeanName;
    private BeanDefinitionRegistry beanDefinitionRegistry;
    private ConfigurableListableBeanFactory beanFactory;
    private DelegatedDataSource delegatedDataSource;
    private ConfigSource configSource;

    private static final Splitter COMMA_SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();
    public static final String SLOW_PROPERTY_KEY = "slowQueryMillis";

    public void load() {
        String oldOriginBeanName = currentOriginBeanName;
        registerBeanDefinition(beanDefinitionRegistry);
        delegatedDataSource.setOriginalBeanName(currentOriginBeanName);
        delegatedDataSource.initDataSource();
        // auto close old datasource
        beanDefinitionRegistry.removeBeanDefinition(oldOriginBeanName);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registerBeanDefinition(registry);
        this.beanDefinitionRegistry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getDelegatedClass());
        delegatedDataSource = new DelegatedDataSource();
        delegatedDataSource.setHook(getExecuteHook());
        delegatedDataSource.setOriginalBeanName(currentOriginBeanName);
        delegatedDataSource.setBeanFactory(beanFactory);
        enhancer.setCallback(delegatedDataSource);
        beanFactory.registerSingleton(beanName, createDelegate(enhancer));
        this.beanFactory = beanFactory;
//        DataSourceReader.registerListener(this.getClass(), "load", this, getDataSources());
    }

    protected Object createDelegate(Enhancer enhancer) {
        return enhancer.create();
    }

    protected abstract AbstractBeanDefinition getBeanDefinition();

    protected abstract Class<?> getDelegatedClass();

    protected abstract ExecuteHook getExecuteHook();

    protected Properties readProperties() {
        List<String> dataSourceList = getDataSources();
        List<String> keyPrefixList = getKeyPrefixList(keyPrefixes);
//        return DataSourceReader.read(dataSourceList, keyPrefixList);
        return null;
    }

    protected int getSlowQueryMillis() {
        Properties properties = readProperties();
        String slowValueString = properties.getProperty(SLOW_PROPERTY_KEY);
        return NumberUtils.toInt(slowValueString);
    }

    private List<String> getKeyPrefixList(String keyPrefixes) {
        return COMMA_SPLITTER.splitToList(keyPrefixes).stream().map(prefix -> prefix + keySeparator).collect(
                Collectors.toList());
    }

    private List<String> getDataSources() {
        return COMMA_SPLITTER.splitToList(configSources);
    }

    private void registerBeanDefinition(BeanDefinitionRegistry registry) {
        AbstractBeanDefinition beanDefinition = getBeanDefinition();
        beanDefinition.setInitMethodName(initMethod);
        beanDefinition.setDestroyMethodName(destroyMethod);
        String originBeanName = createOriginBeanName();
        registry.registerBeanDefinition(originBeanName, beanDefinition);
        currentOriginBeanName = originBeanName;
    }

    private String createOriginBeanName() {
        String candidate = beanName + "$Origin" + RandomUtils.nextInt(1, 100);
        if (StringUtils.equals(candidate, currentOriginBeanName)) {
            return createOriginBeanName();
        }
        return candidate;
    }

    public static class DelegatedDataSource implements MethodInterceptor {

        private volatile String originalBeanName;
        private ConfigurableListableBeanFactory beanFactory;
        private ExecuteHook hook;
        private volatile Object dataSource;

        public void setOriginalBeanName(String originalBeanName) {
            this.originalBeanName = originalBeanName;
        }

        public void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

        public void setHook(ExecuteHook hook) {
            this.hook = hook;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            if (dataSource == null) {
                initDataSource();
            }
            boolean hasHook = hook != null;
            if (hasHook) {
                hook.preProcess();
            }
            Throwable methodException = null;
            try {
                return method.invoke(dataSource, args);
            } catch (Throwable t) {
                methodException = t.getCause();
                throw methodException;
            } finally {
                if (hasHook) {
                    hook.postProcess(methodException, method, args);
                }
            }
        }

        private void initDataSource() {
            dataSource = beanFactory.getBean(originalBeanName);
        }
    }
}