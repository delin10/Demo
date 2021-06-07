package mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author lidelin.
 */
public class WithDepTest {

    @Spy
    @InjectMocks
    private WithDepService withDepService;

    @Mock
    private IOtherService iOtherService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMethod1() {
        // 进行接口对象方法的Mock
        Mockito.doAnswer(inv -> null).when(iOtherService).service();
        withDepService.method1();
    }

    @Test
    public void testMethod0() {
        // 因为依赖method1，而method1在上面已经进行了单测，所以直接进行具体场景的mock
        Mockito.doAnswer(inv -> null).when(withDepService).method1();
        // method0还是进行的实际的方法调用
        // 但是涉及到method1的调用都会被拦截
        withDepService.method0();
    }

    public static class WithDepService {

        private IOtherService service;

        public void method0() {
            method1();
        }

        public void method1() {
            // do something.
            service.service();
        }

    }

    public interface IOtherService {
        void service();
    }
}
