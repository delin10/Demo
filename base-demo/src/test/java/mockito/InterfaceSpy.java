package mockito;

import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author lidelin.
 */
public class InterfaceSpy {

    @Spy
    private TestInterface testInterface;

    @Test
    public void test() {
        MockitoAnnotations.initMocks(this);
        testInterface.test();
    }


    public interface TestInterface {

        void test();

    }

}
