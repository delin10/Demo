package nil.ed.test.java.time.global;

import java.time.ZoneId;
import java.util.Map;

/**
 * @author lidelin10
 * @date 2022/3/29 下午3:28
 */
public class UnionClock {

    static Map<String, ZoneId> ventureZoneProvider;

    static Map<ZoneId, SessionZonedClock> zonedClockCache;

    static ThreadLocal<SessionZonedClock> sessionZonedClock;

    static SessionZonedClock sysZonedClock;

    }
