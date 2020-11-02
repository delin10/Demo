package nil.ed.test.dubbo.provider;

import nil.ed.test.dubbo.api.CallMe;

public class CallMeImpl implements CallMe  {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
