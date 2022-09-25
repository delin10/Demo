package nil.ed.test.akka.selection;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.AwaitPermission$;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * @author lidelin10
 * @date 2022/3/28 下午7:32
 */
public class AkkaClient {
    public static void main(String[] args) throws TimeoutException, InterruptedException {
        Config config = ConfigFactory.parseFileAnySyntax(new File(AkkaClient.class.getResource("/application2.conf").getFile()));
        ActorSystem actorSystem = ActorSystem.create("12345", config);
        final String path = "akka://xx@127.0.0.1:8997/user/sender";
        final ActorSelection lookup = actorSystem.actorSelection(path);
        Future<Object> obj = Patterns.ask(lookup, "123456", 1000);
        System.out.println(obj);
        System.out.println(obj.result(Duration.apply(2, TimeUnit.SECONDS), AwaitPermission$.MODULE$));
    }
}
