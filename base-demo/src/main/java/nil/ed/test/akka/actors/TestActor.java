package nil.ed.test.akka.actors;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.remote.RemoteActorRefProvider;

/**
 * @author lidelin.
 */
public class TestActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        System.out.println(getContext().getSelf().toString());
        System.out.println(getContext().provider().addressString());
        System.out.println(getContext().provider().getDefaultAddress().toString());
        return receiveBuilder().matchAny(System.out::println).build();
    }

    public static void main(String[] args) throws InterruptedException {
        ActorSystem actorSystem = ActorSystem.create();
        ActorRef actorRef = actorSystem.actorOf(Props.create(TestActor.class), "sender");
        actorRef.tell("hahahahha", actorRef);
        TimeUnit.SECONDS.sleep(1000);
        actorSystem.terminate();

    }
}
