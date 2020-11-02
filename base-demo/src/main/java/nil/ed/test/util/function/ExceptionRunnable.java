package nil.ed.test.util.function;

/**
 * @author lidelin.
 */
@FunctionalInterface
public interface ExceptionRunnable {

    /**
     * 执行方法.
     *
     * @throws Exception 可能抛出的异常.
     */
    void run() throws Exception;

}
