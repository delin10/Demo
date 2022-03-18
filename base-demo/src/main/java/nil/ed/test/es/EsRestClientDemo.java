package nil.ed.test.es;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

/**
 * @author lidelin.
 */
public class EsRestClientDemo {

    public static void main(String[] args) throws IOException {
        try (RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build()) {
            Response response = restClient.performRequest("GET", "/twitter/1/2");
            String content = String.join("\n", IOUtils.readLines(response.getEntity().getContent()));
            System.out.println(content);
        }
    }

}
