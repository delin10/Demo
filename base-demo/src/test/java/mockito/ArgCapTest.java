package mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author lidelin.
 */
public class ArgCapTest {

    @Mock
    private ArgCapClass capClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCap() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        capClass.cap("21345y");
        /*
        1、Report Matcher.
        2、Intercept invocation.
        3、Capture Argument.
         */
        Mockito.verify(capClass).cap(captor.capture());
        System.out.println(captor.getValue());
    }

    public static class ArgCapClass {

        public void cap(String a) {

        }

    }

}
