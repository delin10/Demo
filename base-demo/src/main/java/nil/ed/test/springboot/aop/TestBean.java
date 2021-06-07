package nil.ed.test.springboot.aop;

import org.springframework.stereotype.Component;

/**
 * @author lidelin.
 */
@Component
public class TestBean implements TestBeanInterface {

    @Override
    @AopConfig.Anno
    public void test() {
        System.out.println("test");
    }

    @Override
    public void setTest() {

    }

}
