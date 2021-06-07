package powermock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "powermock.SuppressInitClassDemo")
@SuppressStaticInitializationFor("powermock.SuppressInitClassDemo")
public class PowerMockSuppressTest {

    @Before
    public void init() {
        PowerMockito.mockStatic(SuppressInitClassDemo.class);
        System.out.println(SuppressInitClassDemo.o);
        SuppressInitClassDemo.staticInvoke();
    }

    @Test
    public void test() {
        System.out.println("test");
    }
}
