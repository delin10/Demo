package nil.ed.test.es.dao;

import java.util.List;

import nil.ed.test.es.condition.EsTableQueryCondition;
import nil.ed.test.es.model.EsTable;

/**
 * @author easywork.
 */
public interface EsTableMapper {

    /**
     * 插入记录.
     * @param esTable 记录.
     * @return 影响行数.
     */
    long insert(EsTable esTable);

    /**
     * 更新记录.
     * @param esTable 记录.
     * @return 影响行数.
     */
    long update(EsTable esTable);

    List<EsTable> getList(EsTableQueryCondition condition);

}
