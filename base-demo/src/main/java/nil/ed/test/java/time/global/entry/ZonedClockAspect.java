package nil.ed.test.java.time.global.entry;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author lidelin10
 * @date 2022/3/31 下午5:08
 */
@Order(2)
@Aspect
@Component
public class ZonedClockAspect {
        private static final Logger LOGGER = LoggerFactory.getLogger(ZonedClockAspect.class);

        /**
         * 自定义日志输出字段
         */
        private static final String MEMBER_ID = "MEMBER_ID";
        private static final String VENTURE = "VENTURE";
        private static final String BIZ_CODE = "BIZ_CODE";

        @Pointcut("")
        public void annotationPointcut() {

        }

}
