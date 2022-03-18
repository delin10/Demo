package nil.ed.test.es.api;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import nil.ed.test.es.api.im.EsTableDbApi;
import nil.ed.test.es.api.im.EsTableEsApi;
import nil.ed.test.es.service.canal.CanalService;
import spark.Spark;

/**
 * @author lidelin.
 */
public class EsTableApi {
    public static void main(String[] args) throws IOException {
        CanalService.start();
        Spark.port(12001);
        List<ApiRegister> registerList = new LinkedList<>();
        Collections.addAll(registerList, new EsTableDbApi(), new EsTableEsApi());
        registerList.forEach(ApiRegister::register);
    }

}
