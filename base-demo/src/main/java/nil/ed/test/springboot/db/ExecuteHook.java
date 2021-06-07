package nil.ed.test.springboot.db;

import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
@NoArgsConstructor
public class ExecuteHook {
    private String dataSource;
    private String metricType;
    private long slowQueryMillis;
    private Set<String> ignorableExceptions;
    private static final ThreadLocal<Long> startTimeLocal = new ThreadLocal<>();

    public ExecuteHook(String dataSource, String metricType, long slowQueryMillis) {
        this.dataSource = dataSource;
        this.metricType = metricType;
        this.slowQueryMillis = slowQueryMillis;
    }

    public ExecuteHook(String dataSource, String metricType, long slowQueryMillis, Set<String> ignorableExceptions) {
        this(dataSource, metricType, slowQueryMillis);
        this.ignorableExceptions = ignorableExceptions;
    }

    public void preProcess() {
        startTimeLocal.set(System.currentTimeMillis());
    }

    public void postProcess(Throwable throwable, Method method, Object[] args) {
        try {
            Long startTime = startTimeLocal.get();
            if (startTime == null) {
                return;
            }

            long cost = System.currentTimeMillis() - startTime;
            // 监控方法

            if (slowQueryMillis > 0 && cost > slowQueryMillis) {
                log.warn("dataSource={},metricType={},cost={},method={},args={}", dataSource, metricType, cost, method, Lists.newArrayList(args));
                //监控慢查询
            }

            if (throwable != null && (ignorableExceptions == null || !ignorableExceptions.contains(throwable.getClass().getName()))) {
                log.error("dataSource={},metricType={},execute exception,method={},args={}", dataSource, metricType, method, Lists.newArrayList(args), throwable);
                // 监控查询异常
            }
            startTimeLocal.remove();
        } catch (Exception e) {
            log.error("post process exception,dataSource={},metricType={}", dataSource, metricType, e);
        }
    }

}
