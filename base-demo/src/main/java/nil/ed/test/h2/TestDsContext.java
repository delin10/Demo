package nil.ed.test.h2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.sun.tools.javac.util.List;
import nil.ed.test.h2.mapper.OrderSearchDAO;
import nil.ed.test.h2.mapper.TestDAO;
import nil.ed.test.h2.mybatis.TestInterceptor;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * @author lidelin.
 */
public class TestDsContext {

    private static final String H2_MYSQL_MEM_URL = "jdbc:h2:mem:OG_REALTIME_ADB;CACHE_SIZE=1000;MODE=MYSQL;INIT=RUNSCRIPT FROM '%s'";

    private String basePackage;
    private String basePath;
    private SqlSessionFactory sqlSessionFactory;

    public void initDataSource(String basePackage, String scriptFile, String basePath) throws IOException {
        this.basePackage = basePackage;
        this.basePath = basePath;
        DataSource dataSource = dataSource(scriptFile);
        this.sqlSessionFactory = sqlSessionFactory(dataSource);
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new DsProxyHandler(sqlSessionFactory, clazz));
    }

    private DataSource dataSource(String scriptPath) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(String.format(H2_MYSQL_MEM_URL, scriptPath));
        return druidDataSource;
    }

    private SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws IOException {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        File f = new File(basePath);
        for (File sub : FileUtils.listFiles(f, new String[]{"xml"}, false)) {
            if (sub.isFile()) {
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(new FileInputStream(sub),
                        configuration, basePackage, configuration.getSqlFragments());
                xmlMapperBuilder.parse();
            }
        }
        configuration.addInterceptor(new TestInterceptor());
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(configuration);
    }

    public static void main(String[] args) throws IOException {
        TestDsContext testDsContext = new TestDsContext();
        testDsContext.initDataSource("nil.ed.test.h2.mapper", TestDsContext.class.getResource("/order.sql").getFile(), TestDsContext.class.getResource("/mappers/order").getFile());
//        TestDAO testDAO = testDsContext.getMapper(TestDAO.class);
//        testDAO.insert("1234567");
//        System.out.println(testDAO.getAll());
        OrderSearchDAO orderSearchDAO = testDsContext.getMapper(OrderSearchDAO.class);
        System.out.println(orderSearchDAO.search(new OrderSearchParam()));
        OrderSearchParam dbParam = new OrderSearchParam();
        OrderSearchParam.AbstractGeneratedCriteria criteria = dbParam.createAndCriteria();
        OrderSearchParam.AbstractGeneratedCriteria orderClassify = dbParam.createOrCriteria().keyIn(FieldConstants.ORDER_CLASSIFY, List.of(1, 2));
        dbParam.createOrCriteria().otherInfoMatch(FieldConstants.STORE_TYPE, "1")
                .otherInfoMatch(FieldConstants.PURCHASER_CLIENT_TYPE, 2)
                .addAndConditionFor(orderClassify);
        orderClassify.addOrConditionFor(criteria);
        System.out.println(orderSearchDAO.searchTradeOrderIds(dbParam));
    }
}