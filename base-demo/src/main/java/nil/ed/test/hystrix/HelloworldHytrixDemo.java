package nil.ed.test.hystrix;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @author lidelin.
 */
public class HelloworldHytrixDemo extends HystrixCommand<String> {


    private final String name;

    public HelloworldHytrixDemo(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(1500)
                )
        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(1).withMaxQueueSize(10).withMaximumSize(2).withQueueSizeRejectionThreshold(299)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("OK");
        return "Hello world! " + name;
    }

    @Override
    protected String getFallback() {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Fall back");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "************Fallback";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            System.out.println("Start--" + i);
            HelloworldHytrixDemo helloworldHytrixDemo = new HelloworldHytrixDemo("delin");
            long startTime = System.currentTimeMillis();
            Future<String> future = helloworldHytrixDemo.queue();
            // 会有大于1s的出现，说明是使用了CallerRunsPolicy
            System.out.println("Queued Consume: " + (System.currentTimeMillis() - startTime) + "ms");
            if (helloworldHytrixDemo.isCircuitBreakerOpen()) {
                System.out.println(">>>>>>>>>>>>" + helloworldHytrixDemo.isCircuitBreakerOpen());
            } else {
                System.out.println("<<<<<<<<<<<<" + helloworldHytrixDemo.isCircuitBreakerOpen());
            }
        }
        TimeUnit.SECONDS.sleep(200);
    }
}
