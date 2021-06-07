package nil.ed.test.springboot.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class TestIntroductionAdvice extends DelegatingIntroductionInterceptor implements Lockable, TestBeanInterface {
    private boolean locked;

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean locked() {
        return this.locked;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println(invocation.getMethod().getName());
        if (locked() && invocation.getMethod().getName().indexOf("set") == 0) {
            throw new Exception("er");
        }
        return super.invoke(invocation);
    }

    @Override
    public void test() {
        System.out.println("qwertyuiop");
    }

    @Override
    public void setTest() {
        System.out.println("1234567890-=");
    }
}
