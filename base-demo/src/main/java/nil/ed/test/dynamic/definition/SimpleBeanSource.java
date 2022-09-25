package nil.ed.test.dynamic.definition;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.validation.DataBinder;

import java.lang.reflect.Method;

public class SimpleBeanSource implements BeanSource {

    protected Class<?> clazz;

    private final PropertyValues propertyValues = new MutablePropertyValues();

    protected String destroyMethodName;

    protected Method destroyMethod;

    public SimpleBeanSource(Class<?> clazz) {
        this(clazz, null);
    }

    public SimpleBeanSource(Class<?> clazz, String destroyMethodName) {
        this.clazz = clazz;
        this.destroyMethodName = destroyMethodName;
        if (StringUtils.isNotBlank(destroyMethodName)) {
            this.destroyMethod = MethodUtils.getMatchingAccessibleMethod(clazz, destroyMethodName);
            if (this.destroyMethod == null) {
                throw new IllegalArgumentException("Destroy method cannot be found! [ destroyMethodName = " + destroyMethodName + " ]");
            }
        }
    }

    @Override
    public Object getBean() {
        return instantiateClassAndBind(clazz, getPropertyValues());
    }

    @Override
    public Class<?> getBeanType() {
        return clazz;
    }

    @Override
    public void release() {
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    protected Object instantiateClassAndBind(Class<?> clazz, PropertyValues values) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class<?>[] {DisposableBean.class});
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new DisposableBeanInterceptor());
        Object instance = enhancer.create();
        PropertyValues propertyValues = getPropertyValues();
        if (propertyValues != null && !propertyValues.isEmpty()) {
            DataBinder binder = new DataBinder(instance);
            binder.setConversionService(DefaultConversionService.getSharedInstance());
            binder.bind(propertyValues);
        }
        return instance;
    }

    private class DisposableBeanInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

            if ("destroy".equals(method.getName())) {
                if (clazz.isAssignableFrom(DisposableBean.class) && (objects == null || objects.length == 0)) {
                    return null;
                }
                System.out.println(o);
                destroyMethod.invoke(getBean(), objects);
            }
            System.out.println(method);
            System.out.println(methodProxy);
            return methodProxy.invoke(o, objects);
        }
    }

    private static class Test implements DisposableBean, TestInt {
        @Override
        public void destroy() throws Exception {

        }
    }

    interface TestInt {
        default void test() {

        }
    }
    public static void main(String[] args) {
        Method method = MethodUtils.getMatchingAccessibleMethod(Test.class, "destroy");
        Method method1 = MethodUtils.getMatchingAccessibleMethod(Test.class, "test");
        System.out.println(method.getDeclaringClass());
        System.out.println(method1.getDeclaringClass());
    }
}
