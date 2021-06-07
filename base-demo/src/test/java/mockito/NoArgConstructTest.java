package mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author lidelin.
 */
public class NoArgConstructTest {

    @Mock
    private NoArgConstructClass mock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        System.out.println(mock.getClass());
    }


    public static class NoArgConstructClass {
        {
            System.out.println("init");
        }
        public NoArgConstructClass(String text) {
            System.out.println(text);
        }
    }

}
