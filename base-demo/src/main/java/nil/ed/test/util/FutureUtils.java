package nil.ed.test.util;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
public class FutureUtils {

    public static <O, T> FutureExecution makeFutureLoad(List<O> origin, Function<O, T> taskBuilder, Consumer<T> syncConsumer, Executor executor) {
        List<CompletableFuture<T>> fs = origin.stream()
                .map(o -> CompletableFuture.supplyAsync((() -> taskBuilder.apply(o)), executor))
                .collect(Collectors.toList());
        return new FutureExecution() {
            @Override
            public void exec() throws ExecutionException, InterruptedException {
                if (chain != null) {
                    chain.exec();
                }
                CompletableFuture.allOf(fs.toArray(new CompletableFuture[0])).get();
                fs.stream().map(f -> f.getNow(null)).forEach(syncConsumer);
            }

            @Override
            public void exec(long timeout, TimeUnit unit) throws TimeoutException, ExecutionException, InterruptedException {
                if (chain != null) {
                    chain.exec(timeout, unit);
                }
                CompletableFuture.allOf(fs.toArray(new CompletableFuture[0])).get(timeout, unit);
                fs.stream().map(f -> f.getNow(null)).forEach(syncConsumer);
            }
        };
    }

    public static <T> T blockingGet(Future<T> future) {
        try {
            return future.get();
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * 执行实体类.
     */
    public static abstract class FutureExecution {

        protected volatile FutureExecution chain;

        public <O, T> FutureExecution chain(List<O> origin, Function<O, T> taskBuilder, Consumer<T> syncConsumer, Executor executor) {
            FutureExecution execution = makeFutureLoad(origin, taskBuilder, syncConsumer, executor);
            execution.chain = this;
            return execution;
        }

        /**
         * 阻塞至执行完成.
         * @throws ExecutionException    执行出现错误.
         * @throws InterruptedException  线程中断.
         */
        public abstract void exec() throws ExecutionException, InterruptedException;

        /**
         * 阻塞至执行完成.
         * @param timeout   超时时间单位.
         * @param unit      超时单位.
         * @throws ExecutionException    执行出现错误.
         * @throws InterruptedException  线程中断.
         * @throws TimeoutException      超时.
         */
        public abstract void exec(long timeout, TimeUnit unit) throws TimeoutException, ExecutionException, InterruptedException;
    }

}
