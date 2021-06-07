package nil.ed.test.dynamic.definition;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

@Slf4j
public class RefreshableSingletonBeanSource extends SimpleBeanSource {

    private transient volatile Object bean;

    public RefreshableSingletonBeanSource(Class<?> clazz) {
        super(clazz);
    }

    public RefreshableSingletonBeanSource(Class<?> clazz, String destroyMethodName) {
        super(clazz, destroyMethodName);
    }

    @Override
    public Object getBean() {
        if (bean == null) {
            return bean = super.getBean();
        }
        return bean;
    }

    @Override
    public void release() {
        doRelease(bean);
    }

    public void refresh() {
        Object oldBean = bean;
        bean = super.getBean();
        if (oldBean != null) {
            doRelease(oldBean);
        }
    }

    private void doRelease(Object target) {
        if (target != null) {
            if (!clazz.isAssignableFrom(target.getClass())) {
                throw new IllegalArgumentException("Incompatible class: expected = "
                        + clazz.getCanonicalName() + ", actual = " + target.getClass().getCanonicalName());
            }
            try {
                if (!destroyMethod.isAccessible()) {
                    destroyMethod.setAccessible(true);
                }
                destroyMethod.invoke(target);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("Failed to invoke destroy method! [ destroyMethodName = {}, bean = {} ]", destroyMethodName, bean, e);
            }
        }
    }
}
