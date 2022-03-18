package nil.ed.test.es.service;

import java.util.Collections;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import nil.ed.test.es.condition.EsTableQueryCondition;
import nil.ed.test.es.model.EsTable;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

/**
 * @author lidelin.
 */
public class EsTableElasticSearchService {

    private final RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();

    public void insert(EsTable esTable) throws Exception {
        Response response = restClient.performRequest("PUT", String.format("/test/es_table/%s", esTable.getId()), Collections.emptyMap(), new StringEntity(JSON.toJSONString(esTable)), new BasicHeader("Content-Type", "application/json;charset=utf-8"));
        String content = String.join("\n", IOUtils.readLines(response.getEntity().getContent()));
        System.out.println(content);
    }

    public void insert(Map<String, String> json) throws Exception {
        Response response = restClient.performRequest("PUT", String.format("/test/es_table/%s", json.get("id")), Collections.emptyMap(), new StringEntity(JSON.toJSONString(json)), new BasicHeader("Content-Type", "application/json;charset=utf-8"));
        String content = String.join("\n", IOUtils.readLines(response.getEntity().getContent()));
        System.out.println(content);
    }


    public void update(EsTable esTable) throws Exception {
        Response response = restClient.performRequest("PUT", String.format("/test/es_table/%s/_update", esTable.getId()), Collections.emptyMap(), new StringEntity(JSON.toJSONString(new UpdateDTO<>(esTable))), new BasicHeader("Content-Type", "application/json;charset=utf-8"));
        String content = String.join("\n", IOUtils.readLines(response.getEntity().getContent()));
        System.out.println(content);
    }

    public String getList(EsTableQueryCondition condition) throws Exception {
        SearchParam searchParam = new SearchParam();
        searchParam.setFrom(condition.getStart());
        searchParam.setSize(condition.getPageSize());
        Response response = restClient.performRequest("GET", "/test/es_table/_search", Collections.emptyMap(), new StringEntity(JSON.toJSONString(searchParam)), new BasicHeader("Content-Type", "application/json;charset=utf-8"));
        return String.join("\n", IOUtils.readLines(response.getEntity().getContent()));
    }

    public static class UpdateDTO<T> {
        private final T doc;

        public UpdateDTO(T doc) {
            this.doc = doc;
        }
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class SearchParam {

        private int from;
        private int size;

    }

}
