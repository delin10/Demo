//package nil.ed.test.h2.mybatis;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.TypeHandler;
//
//public class TestTypeHandler implements TypeHandler<Object> {
//    /**
//     * 设置参数.
//     * @param ps            预编译语句.
//     * @param i             预编译语句参数位置，从1开始.
//     * @param parameter     参数值.
//     * @param jdbcType      jdbc类型.
//     * @throws SQLException SQL异常.
//     */
//    @Override
//    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
//        ps.setString(i, JSON.toJSONString(parameter));
//    }
//
//    @Override
//    public Object getResult(ResultSet rs, String columnName) throws SQLException {
//        String val = rs.getString(columnName);
//        if (StringUtils.isBlank(val)) {
//            return null;
//        }
//
//    }
//
//    @Override
//    public Enum<?> getResult(ResultSet rs, int columnIndex) throws SQLException {
//        return null;
//    }
//
//    @Override
//    public Enum<?> getResult(CallableStatement cs, int columnIndex) throws SQLException {
//        return null;
//    }
//}
