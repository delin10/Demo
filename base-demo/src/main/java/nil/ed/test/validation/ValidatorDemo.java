package nil.ed.test.validation;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import javax.annotation.processing.Processor;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.groups.Default;

/**
 * @author lidelin.
 */
public class ValidatorDemo {

    public static void main(String[] args) {
        TestBean bean = new TestBean();
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        ServiceLoader<Processor> processors = ServiceLoader.load(Processor.class);
        Iterator<Processor> processorIterator = processors.iterator();
        while (processorIterator.hasNext()) {
            System.out.println(processorIterator.next());
        }
        System.out.println(ValidatorDemo.class.getClassLoader().getResource("META-INF/services/jakarta.validation.spi.ValidationProvider"));
        Set<ConstraintViolation<TestBean>> constraintViolations =  validator.validate(bean, Default.class);
        System.out.println(constraintViolations);

    }

    private static class TestBean {

        @Min(value = 1, message = "adfrgthnjhe")
        private int a;

    }

}
