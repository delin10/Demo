package nil.ed.test.tool;

import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

public class IntersectDemo {

    public static void main(String[] args) {
        System.out.println(CollectionUtils.intersection(splitToSet("45468404903241,45468400271337,45749504649875,45468203971337,45468203649875,45749504049875,45468203071337,45468202771337,45748900271337,45748501703241,45748302569640,45467294469640,45467070469640,45467069969640,45467067969640,45467285469640,45467242069640,45746727609587,45744918569640,45744918169640,45464401745045,45464401445045,45463800845045,45743301845045,"), splitToSet("45748302569640,45467072274252,45467070469640,45467069969640,45467067969640,45467285469640,45467206339179,45467206039179,45467205749875,45746771049875,45745712345045,45744757303241,45744942945045,45744942009863,45744754845045,45744754545045,45744753909587,45744750245045,45744749267924,45744934109863,45744748009863,45744919609863,45744919345045,45744918569640,45744918169640,45744729145045,45744702303241,45744901203241,45464424145045,45464650045045,45464649445045,45744108771337,45744108071337,45464203374252")));
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        AtomicLong lastTs = new AtomicLong(0);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            long last = lastTs.getAndSet(System.currentTimeMillis());
            try {
                if (last != 0) {
                    System.out.println("scheduleAtFixedRate Check " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - last)));
                }
                System.out.println("scheduleAtFixedRate");
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {

            }
        }, 0, 2, TimeUnit.SECONDS);

        AtomicLong lastTs2 = new AtomicLong(0);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            long last = lastTs2.getAndSet(System.currentTimeMillis());
            try {
                if (last != 0) {
                    System.out.println("scheduleWithFixedDelay Check " + (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - last)));
                }
                System.out.println("scheduleWithFixedDelay  ");
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {

            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public static Set<String> splitToSet(String text) {
        return Sets.newHashSet(text.split("\\s*,\\s*"));
    }
}
