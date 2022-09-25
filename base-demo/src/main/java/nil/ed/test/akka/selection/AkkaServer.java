package nil.ed.test.akka.selection;

import java.io.File;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * akka {
 *   actor {
 *     provider = remote
 *   }
 *   remote {
 *     enabled-transports = []
 *     netty.tcp {
 *       hostname = "127.0.0.1"
 *       port = 8998
 *     }
 *   }
 * }
 * @author lidelin10
 * @date 2022/3/28 下午7:32
 */
public class AkkaServer {
    public static void main(String[] args) {
        Config config = ConfigFactory.parseFileAnySyntax(new File(AkkaServer.class.getResource("/application.conf").getFile()));
        ActorSystem actorSystem = ActorSystem.create("xx", config);
        ActorRef actorRef = actorSystem.actorOf(Props.create(TestServerActor.class), "sender");
        actorRef.tell("ssss", actorRef);
        System.out.println(actorSystem.provider().addressString());
        System.out.println(actorRef.path());
        System.out.println(actorRef.toString());
    }
}
