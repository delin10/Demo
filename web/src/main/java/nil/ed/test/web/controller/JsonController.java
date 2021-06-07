package nil.ed.test.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author lidelin.
 */
@RestController
@RequestMapping("/json")
public class JsonController {

    @GetMapping("/get")
    public Object getJsoList() {
        return Arrays.asList(1, 2, 3, 4);
    }

}
