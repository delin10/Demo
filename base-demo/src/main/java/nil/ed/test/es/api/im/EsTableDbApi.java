package nil.ed.test.es.api.im;

import com.alibaba.fastjson.JSON;
import nil.ed.test.es.api.ApiRegister;
import nil.ed.test.es.condition.EsTableQueryCondition;
import nil.ed.test.es.model.EsTable;
import nil.ed.test.es.service.EsTableService;
import spark.Spark;

/**
 * @author lidelin.
 */
public class EsTableDbApi implements ApiRegister {
    @Override
    public void register() {
        try {
            EsTableService esTableService = new EsTableService();
            addRecordApi(esTableService);
            updateRecordApi(esTableService);
            listRecordApi(esTableService);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void addRecordApi(EsTableService esTableService ) {
        Spark.post("/es_table/db/add", (res, response) -> {
            EsTable esTable = JSON.parseObject(res.body(), EsTable.class);
            esTableService.insert(esTable);
            System.out.println(esTable);
            return "ok";
        });
    }

    public void updateRecordApi(EsTableService esTableService ) {
        Spark.post("/es_table/db/update", (res, response) -> {
            EsTable esTable = JSON.parseObject(res.body(), EsTable.class);
            esTableService.update(esTable);
            return "ok";
        });
    }

    public void listRecordApi(EsTableService esTableService ) {
        Spark.get("/es_table/db/list", (res, response) -> {
            EsTableQueryCondition condition = JSON.parseObject(res.body(), EsTableQueryCondition.class);
            response.header("Content-Type", "application/json;charset=utf-8");
            return JSON.toJSONString(esTableService.getList(condition));
        });
    }
}
