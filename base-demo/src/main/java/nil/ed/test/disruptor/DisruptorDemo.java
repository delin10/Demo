package nil.ed.test.disruptor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import nil.ed.test.util.ThreadUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

/**
 * @author lidelin.
 */
public class DisruptorDemo {
    public static void main(String[] args) throws Exception
    {
        // 队列中的元素
        class Element {

            private int value;

            public int get(){
                return value;
            }

            public void set(int value){
                this.value= value;
            }

        }

        // 生产者的线程工厂
        ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("simpleThread-%d").build();
        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<Element> factory = new EventFactory<Element>() {
            @Override
            public Element newInstance() {
                return new Element();
            }
        };

        // 处理Event的handler
        EventHandler<Element> handler = new EventHandler<Element>(){
            @Override
            public void onEvent(Element element, long sequence, boolean endOfBatch)
            {
                System.out.printf("1-Element: %s, Thread: %s%n", element.get(), Thread.currentThread().getName());
//                ThreadUtils.sleepQuietly(ThreadLocalRandom.current().nextInt(100), TimeUnit.SECONDS);
                ThreadUtils.sleepQuietly(100, TimeUnit.SECONDS);
            }
        };

        EventHandler<Element> handler2 = new EventHandler<Element>(){
            @Override
            public void onEvent(Element element, long sequence, boolean endOfBatch)
            {
                System.out.printf("2-Element: %s, Thread: %s%n", element.get(), Thread.currentThread().getName());
//                ThreadUtils.sleepQuietly(ThreadLocalRandom.current().nextInt(100), TimeUnit.SECONDS);
                ThreadUtils.sleepQuietly(100, TimeUnit.SECONDS);
            }
        };

        // 阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

        // 指定RingBuffer的大小
        int bufferSize = 2;

        // 创建disruptor，采用单生产者模式
        Disruptor<Element> disruptor = new Disruptor(factory, bufferSize, threadFactory, ProducerType.SINGLE, strategy);

        // 设置EventHandler
        disruptor.handleEventsWith(handler, handler2);

        // 启动disruptor的线程
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        for (int l = 0; true; l++)
        {
            // 获取下一个可用位置的下标
            long sequence = ringBuffer.next();
            System.out.println("Next Sequence: " + sequence);
            try
            {
                // 返回可用位置的元素
                Element event = ringBuffer.get(sequence);
                System.out.println("Get Element: " + event);
                // 设置该位置元素的值
                event.set(l);
            }
            finally
            {
                ringBuffer.publish(sequence);
            }
            Thread.sleep(10);
        }
    }
}
