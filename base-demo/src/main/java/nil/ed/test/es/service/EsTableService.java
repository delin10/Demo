package nil.ed.test.es.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import lombok.Setter;
import nil.ed.test.es.condition.EsTableQueryCondition;
import nil.ed.test.es.dao.EsTableMapper;
import nil.ed.test.es.model.EsTable;
import nil.ed.test.h2.DsProxyHandler;
import nil.ed.test.h2.TestDsContext;
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
public class EsTableService {

    @Getter
    @Setter
    private DataSource dataSource;
    @Getter
    @Setter
    private SqlSessionFactory sqlSessionFactory;
    @Getter
    @Setter
    private EsTableMapper esTableMapper;
    @Getter
    @Setter
    private EsTableElasticSearchService esTableElasticSearchService;

    public EsTableService() throws IOException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE");
        druidDataSource.setUsername("root");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactory(druidDataSource);
        this.dataSource = druidDataSource;
        this.sqlSessionFactory = sqlSessionFactory;
        this.esTableMapper = getMapper(EsTableMapper.class);
        this.esTableElasticSearchService = new EsTableElasticSearchService();
    }

    public void insert(EsTable table) {
        esTableMapper.insert(table);
    }

    public void update(EsTable table) {
        esTableMapper.insert(table);
        try {
            this.esTableElasticSearchService.update(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<EsTable> getList(EsTableQueryCondition condition) {
        return esTableMapper.getList(condition);
    }


    @SuppressWarnings("unchecked")
    private  <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new DsProxyHandler(sqlSessionFactory, clazz));
    }


    private SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws IOException {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        File f = new File(TestDsContext.class.getResource("/mappers/es").getFile());
        for (File sub : FileUtils.listFiles(f, new String[]{"xml"}, false)) {
            if (sub.isFile()) {
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(new FileInputStream(sub),
                        configuration, "nil.ed.test.es.dao", configuration.getSqlFragments());
                xmlMapperBuilder.parse();
            }
        }
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(configuration);
    }
}
