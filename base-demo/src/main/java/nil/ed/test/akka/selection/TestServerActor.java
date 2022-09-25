package nil.ed.test.akka.selection;

import akka.actor.UntypedAbstractActor;

/**
 * @author lidelin10
 * @date 2022/3/28 下午7:38
 */
public class TestServerActor extends UntypedAbstractActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        System.out.println(message);
    }

}
