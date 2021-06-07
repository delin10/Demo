package nil.ed.test.agent;

import java.lang.instrument.Instrumentation;

public class TestPremain {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println(inst);
    }

}
