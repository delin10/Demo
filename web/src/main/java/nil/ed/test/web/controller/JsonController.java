package nil.ed.test.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author lidelin.
 */
@RestController
@RequestMapping("/json")
public class JsonController {

    private TransactionalService transactionalService;

    @Resource
    public void setTransactionalService(TransactionalService transactionalService) {
        try {
            transactionalService.insert(1);
            Arrays.stream(transactionalService.getClass().getDeclaredMethods()).forEach(e -> {
                System.out.println(Modifier.isPublic(e.getModifiers()) ? "public" : "private");
                System.out.println(e.getName());
                System.out.println(e.getDeclaringClass().getSimpleName());
                System.out.println();
            });
            System.out.println(transactionalService.getClass().getDeclaredMethod("insert", int[].class).getAnnotation(RequestMapping.class));
            System.out.println(transactionalService.getClass().getDeclaredMethod("insert", int[].class).getParameters()[0].getAnnotation(RequestMapping.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.transactionalService = transactionalService;
    }

    @GetMapping("/get")
    public Object getJsoList() {
        return Arrays.asList(1, 2, 3, 4);
    }

}
