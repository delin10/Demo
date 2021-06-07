package nil.ed.test.springcloud.feign.origin;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import nil.ed.test.comm.dto.TestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * @author lidelin.
 */
@Component
@Import(FeignClientsConfiguration.class)
public class MyFeignClient {

    @Autowired
    public MyFeignClient(Decoder decoder,
                         Encoder encoder,
                         Client client,
                         Contract contract) {
        TestClient testClient = Feign.builder()
                .client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(TestClient.class, "http://localhost:12001");
        TestDTO dto = testClient.get();
        System.out.println(dto);
    }

}
