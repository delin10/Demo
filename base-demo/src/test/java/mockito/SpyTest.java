package mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class SpyTest {

    @Spy
    private SpyTestClass spyTestClass;

    @Mock
    private SpyTestClass spyTestClass2;

    @InjectMocks
    @Spy
    private SpyInjectTestClass spyInjectTestClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {

        System.out.println(spyTestClass);
    }

    public static class SpyTestClass {

        public void test() {

        }

    }

    public static class SpyInjectTestClass {

        private SpyTestClass spyTestClass;

        public void test() {
            spyTestClass.test();
        }

    }

}
