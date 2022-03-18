package nil.ed.test.es.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author lidelin.
 */
public class DsProxyHandler implements InvocationHandler {

    private final SqlSessionFactory sqlSessionFactory;

    private final Class<?> mapperClass;

    public DsProxyHandler(SqlSessionFactory sqlSessionFactory, Class<?> mapperClass) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.mapperClass = mapperClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return method.invoke(session.getMapper(mapperClass), args);
        }
    }
}
