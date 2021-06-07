package nil.ed.test.springboot.aop;

import org.springframework.aop.support.DefaultIntroductionAdvisor;

public class TestIntroductionAdvisor extends DefaultIntroductionAdvisor {
    public TestIntroductionAdvisor() {
        super(new TestIntroductionAdvice(), Lockable.class);
    }
}