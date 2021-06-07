package nil.ed.test.dynamic.definition;

/**
 * @author lidelin.
 */
public interface BeanSource {

    /**
     * 获取Bean.
     * @return bean.
     */
    Object getBean();

    /**
     * 获取bean类型.
     * @return bean类型.
     */
    Class<?> getBeanType();

    /**
     * 释放资源.
     */
    void release();
}
