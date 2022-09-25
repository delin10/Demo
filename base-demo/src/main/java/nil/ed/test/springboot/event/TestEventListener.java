package nil.ed.test.springboot.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author lidelin10
 * @date 2022/6/27 上午11:00
 */
@Component
public class TestEventListener {

    @EventListener(classes = TestEvent.class)
    public void onEvent(TestEvent event) {
        System.out.println(event);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(TestEventListener.class);
        annotationConfigApplicationContext.refresh();
        annotationConfigApplicationContext.publishEvent(new TestEvent(annotationConfigApplicationContext, "TEST"));
        annotationConfigApplicationContext.publishEvent(new BTestEvent(annotationConfigApplicationContext, "TEST"));
    }

}
