package nil.ed.test.web.controller;

import nil.ed.test.comm.dto.TestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidelin.
 */
@RestController
@RequestMapping("/boot1")
public class HelloController {

    @GetMapping("/hello")
    public Object test() {
        return new TestDTO().setId(1).setName("test");
    }

}
