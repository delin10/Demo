package nil.ed.test.dubbo.filter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * @author lidelin10
 * @date 2022/3/31 下午2:55
 */
public class NamedVentureResolver implements VentureResolver {

    private static final String VENTURE_PARAM_NAME = "venture";

    @Override
    public String resolve(Method targetMethod, Object[] arguments) throws Exception {
        if (arguments == null || arguments.length == 0) {
            return null;
        }
        String venture = null;
        int index = resolveVentureParamIndex(targetMethod.getParameters());
        if (index > 0) {
            venture = resolveVentureObject(arguments[index], targetMethod.getParameters()[index]);
        }
        return venture;
    }

    public int resolveVentureParamIndex(Parameter[] parameters) {
        // 1. 解析注解，获取候选下标
        Optional<Integer> candidateVentureParamOpt = IntStream.range(0, parameters.length)
                .filter(i -> parameters[i].isAnnotationPresent(VentureMark.class))
                .boxed()
                .findFirst();
        if (!candidateVentureParamOpt.isPresent()) {
            // 2. 解析名称
            candidateVentureParamOpt = IntStream.range(0, parameters.length)
                    .filter(i -> VENTURE_PARAM_NAME.equals(parameters[i].getName()) && String.class.equals(parameters[i].getType()))
                    .boxed()
                    .findFirst();
        }
        return candidateVentureParamOpt.orElse(-1);
    }

    public String resolveVentureObject(Object candidate, Parameter parameter) throws Exception {
        if (candidate == null) {
            return null;
        }
        // 1. 直接解析String入参数.
        if (candidate instanceof String) {
            return VENTURE_PARAM_NAME.equals(parameter.getName()) ? (String) candidate : null;
        } else {
            // 2. 解析入参数对象
            // 2.1 解析携带VentureMark注解的成员
            Optional<Field> fieldOpt = FieldUtils.getFieldsListWithAnnotation(candidate.getClass(), VentureMark.class)
                    .stream().filter(f -> String.class.isAssignableFrom(f.getClass())).findFirst();
            // 2.2 解析携带名称为venture的成员
            Field candidateField = fieldOpt.orElse(FieldUtils.getDeclaredField(candidate.getClass(), "venture", true));
            if (candidateField == null || !String.class.equals(candidateField.getType())) {
                return null;
            }
            return (String) candidateField.get(candidate);
        }

    }
}
