package nil.ed.anno;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author delin10
 * @since 2020/7/8
 **/
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalHandler.class)
public @interface MyCondition {
}
