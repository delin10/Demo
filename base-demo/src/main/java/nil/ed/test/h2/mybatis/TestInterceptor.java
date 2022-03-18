package nil.ed.test.h2.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * @author lidelin.
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class TestInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("intercept...");
        Object ret = invocation.proceed();
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        System.out.println(statement.getBoundSql(invocation.getArgs()[1]).getSql());
        return ret;
    }
}
