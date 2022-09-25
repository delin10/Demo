package nil.ed.test.springboot.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author lidelin10
 * @date 2022/6/27 上午11:00
 */
public class BTestEvent extends ApplicationEvent {

    private final String message;

    public BTestEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
