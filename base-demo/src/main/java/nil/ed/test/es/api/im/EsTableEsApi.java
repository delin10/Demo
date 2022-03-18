package nil.ed.test.es.api.im;

import com.alibaba.fastjson.JSON;
import nil.ed.test.es.api.ApiRegister;
import nil.ed.test.es.condition.EsTableQueryCondition;
import nil.ed.test.es.service.EsTableElasticSearchService;
import spark.Spark;

/**
 * @author lidelin.
 */
public class EsTableEsApi implements ApiRegister {
    @Override
    public void register() {
        try {
            EsTableElasticSearchService esTableElasticSearchService = new EsTableElasticSearchService();
            listRecordApi(esTableElasticSearchService);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void listRecordApi(EsTableElasticSearchService esTableElasticSearchService ) {
        Spark.get("/es_table/es/list", (res, response) -> {
            EsTableQueryCondition condition = JSON.parseObject(res.body(), EsTableQueryCondition.class);
            response.header("Content-Type", "application/json;charset=utf-8");
            return esTableElasticSearchService.getList(condition);
        });
    }
}
