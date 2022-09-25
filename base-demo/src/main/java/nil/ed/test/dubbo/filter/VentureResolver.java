package nil.ed.test.dubbo.filter;

import java.lang.reflect.Method;

/**
 * @author lidelin10
 * @date 2022/3/31 下午2:53
 */
public interface VentureResolver {

    /**
     * 解析国家参数.
     * @param targetMethod 目标方法.
     * @param arguments    入参列表.
     * @return 国家标识.
     */
    String resolve(Method targetMethod, Object[] arguments) throws Exception;

}
