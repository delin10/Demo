package nil.ed.test.java.time.global;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author lidelin10
 * @date 2022/3/29 下午3:21
 */
public class SessionZonedClock {

    private ZoneId zoneId;

    public ZonedDateTime getNow() {
        return ZonedDateTime.now(zoneId);
    }

    public ZonedDateTime parse(Long timeMillis) {
        return null;
    }

    public ZonedDateTime toZonedDateTime(long millis, ZoneId zoneId) {
        return null;
    }

    public ZonedDateTime toZonedDateTime(long millis, String venture) {
        return null;
    }


}
