package mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author lidelin.
 */
public class SpyTest2 {

    @Spy
    private TestLogic logic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        // NPE
        Mockito.when(logic.needMock(Mockito.any())).thenReturn(0);
        // 正常
        Mockito.doReturn(0).when(logic).needMock(Mockito.any());
    }

    public static class TestLogic {
        public int needMock(String a) {
            return a.length();
        }
    }
}
