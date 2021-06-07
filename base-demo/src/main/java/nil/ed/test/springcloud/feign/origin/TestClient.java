package nil.ed.test.springcloud.feign.origin;

import nil.ed.test.comm.dto.TestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lidelin.
 */
@FeignClient(name = "boot1")
public interface TestClient {

    /**
     * test get.
     * @return get.
     */
    @GetMapping("/boot1/hello")
    TestDTO get();

}
