package nil.ed.test.dubbo.filter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.stream.IntStream;

import lombok.Data;
import nil.ed.test.dubbo.api.TestInter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

/**
 * @author lidelin10
 * @date 2022/3/31 上午11:05
 */
@Activate(group = "provider")
public class TestFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        return invoker.invoke(invocation);
    }

    public static String doResolvedVenture( Invocation invocation) {
        String venture = null;
        try {
            if (invocation.getArguments() != null && invocation.getArguments().length > 0) {
                Object obj = invocation.getArguments()[0];
                Field f = null;
                if (obj != null) {
                    f = FieldUtils.getDeclaredField(obj.getClass(), "venture", true);
                }

                // case 1：入参类型为String
                if (f == null) {
                    String[] parts = invocation.getTargetServiceUniqueName().split(":");
                    String interfaceClass = parts[0];
                    Class<?> clazz = Class.forName(interfaceClass);
                    Method method = ClassUtils.getPublicMethod(clazz, invocation.getMethodName(), invocation.getParameterTypes());
                    Parameter[] parameters = method.getParameters();
                    if (parameters != null && parameters.length > 0) {
                        venture = IntStream.range(0, parameters.length)
                                .filter(i -> "venture".equals(parameters[i].getName()))
                                .boxed()
                                .findFirst()
                                .map(i -> invocation.getArguments()[i])
                                .filter(arg -> String.class.isAssignableFrom(arg.getClass()))
                                .map(String.class::cast)
                                .orElse(null);
                    }
                // case 2：入参类型为Object，且包含venture字段.
                } else if (String.class.isAssignableFrom(f.getType())) {
                    venture = (String) FieldUtils.readField(f, obj, true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // todo log
        }
        return venture;
    }

    public static void main(String[] args) {
        Model model = new Model();
        model.setVenture("et");
        Class<?> clazz = TestInter.class;
        String methodName = "test";
        Class<?>[] sig = {Model.class};
        Object[] argses = {model};
        Invocation invocation = new Invocation() {
            @Override
            public String getTargetServiceUniqueName() {
                return clazz.getCanonicalName() + ":1.0.0";
            }

            @Override
            public String getMethodName() {
                return methodName;
            }

            @Override
            public String getServiceName() {
                return null;
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return sig;
            }

            @Override
            public Object[] getArguments() {
                return argses;
            }

            @Override
            public Map<String, String> getAttachments() {
                return null;
            }

            @Override
            public Map<String, Object> getObjectAttachments() {
                return null;
            }

            @Override
            public void setAttachment(String key, String value) {

            }

            @Override
            public void setAttachment(String key, Object value) {

            }

            @Override
            public void setObjectAttachment(String key, Object value) {

            }

            @Override
            public void setAttachmentIfAbsent(String key, String value) {

            }

            @Override
            public void setAttachmentIfAbsent(String key, Object value) {

            }

            @Override
            public void setObjectAttachmentIfAbsent(String key, Object value) {

            }

            @Override
            public String getAttachment(String key) {
                return null;
            }

            @Override
            public Object getObjectAttachment(String key) {
                return null;
            }

            @Override
            public String getAttachment(String key, String defaultValue) {
                return null;
            }

            @Override
            public Object getObjectAttachment(String key, Object defaultValue) {
                return null;
            }

            @Override
            public Invoker<?> getInvoker() {
                return null;
            }

            @Override
            public Object put(Object key, Object value) {
                return null;
            }

            @Override
            public Object get(Object key) {
                return null;
            }

            @Override
            public Map<Object, Object> getAttributes() {
                return null;
            }
        };
        System.out.println(doResolvedVenture(invocation));
    }

    @Data
    public static class Model {
        private String venture;
    }

}
