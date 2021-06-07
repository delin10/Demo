package nil.ed.test.agent.boot;

import java.util.concurrent.TimeUnit;

/**
 * boot parameter:
 * -javaagent:/Users/admin/delin/private-workspace/Demo/agent-boot/src/main/resources/lib/agent-jar-1.0-SNAPSHOT.jar
 * -javaagent:jarpath[=options]
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello Agent!");
        System.out.println("Hello Agent!");
        System.out.println("Hello Agent!");
        TimeUnit.SECONDS.sleep(10);
    }

}
