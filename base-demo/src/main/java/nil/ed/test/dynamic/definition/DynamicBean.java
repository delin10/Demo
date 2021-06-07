package nil.ed.test.dynamic.definition;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author lidelin.
 */
public class DynamicBean implements FactoryBean<Object> {

    private final BeanSource beanSource;

    public DynamicBean(BeanSource beanSource) {
        this.beanSource = beanSource;
    }

    @Override
    public Object getObject() throws Exception {
        return beanSource.getBean();
    }

    @Override
    public Class<?> getObjectType() {
        return beanSource.getBeanType();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
