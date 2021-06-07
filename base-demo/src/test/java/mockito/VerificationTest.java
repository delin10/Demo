package mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author lidelin.
 */
public class VerificationTest {

    @InjectMocks
    private TestInjectMock injectMock;

    @Mock
    private TestMock mock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void verifyTest() {
        injectMock.test();
        Mockito.verify(mock).test();
        Mockito.verify(mock).testNoInvoke();
    }

    public static class TestMock {

        public void test() {

        }

        public void testNoInvoke() {

        }

    }

    public static class TestInjectMock {

        TestMock mock;


        public void test() {
            mock.test();
        }

    }

}
