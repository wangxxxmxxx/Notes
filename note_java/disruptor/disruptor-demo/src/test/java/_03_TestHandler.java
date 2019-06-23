import com.eussi.base.DataEvent;
import com.eussi.handler.DataEventHandler;
import com.eussi.handler.DataEventHandler2;
import com.eussi.handler.DataWorkHandler;
import com.eussi.handler.KickAssEventHandler;
import com.eussi.publish.DataEventFactory;
import com.eussi.publish.DataEventTranslatorWithIdAndValue;
import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkerPool;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class _03_TestHandler {

    /**
     * 处理事件, 发布者快于处理者
     */
    @org.junit.Test
    public void test() {
        //创建一个RingBuffer，注意容量是4。
        RingBuffer<DataEvent> ringBuffer =
                RingBuffer.createSingleProducer(new DataEventFactory(), 4);

        //创建一个事件处理器。
        BatchEventProcessor<DataEvent> batchEventProcessor =
            /*
             * 注意参数：数据提供者就是RingBuffer、序列栅栏也来自RingBuffer
             * EventHandler使用自定义的。
             */
                new BatchEventProcessor<DataEvent>(ringBuffer,
                        ringBuffer.newBarrier(), new DataEventHandler());

        //将事件处理器本身的序列设置为ringBuffer的追踪序列。
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());

        //启动事件处理器。
        new Thread(batchEventProcessor).start();

        //往RingBuffer上发布事件
        for(int i=0;i<10;i++){
            ringBuffer.publishEvent(new DataEventTranslatorWithIdAndValue(), i, i+"s");
            System.out.println("发布事件["+i+"]");
        }

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理事件, 发布者慢于处理者
     */
    @org.junit.Test
    public void test2() {
        //创建一个RingBuffer，注意容量是4。
        RingBuffer<DataEvent> ringBuffer =
                RingBuffer.createSingleProducer(new DataEventFactory(), 4);

        //创建一个事件处理器。
        BatchEventProcessor<DataEvent> batchEventProcessor =
            /*
             * 注意参数：数据提供者就是RingBuffer、序列栅栏也来自RingBuffer
             * EventHandler使用自定义的。
             */
                new BatchEventProcessor<DataEvent>(ringBuffer,
                        ringBuffer.newBarrier(), new DataEventHandler2());

        //将事件处理器本身的序列设置为ringBuffer的追踪序列。
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());

        //启动事件处理器。
        new Thread(batchEventProcessor).start();

        //往RingBuffer上发布事件
        for(int i=0;i<10;i++){
            ringBuffer.publishEvent(new DataEventTranslatorWithIdAndValue(), i, i+"s");
            System.out.println("发布事件["+i+"]");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理事件, 多个处理者
     */
    @org.junit.Test
    public void test3() {
        //创建一个RingBuffer，注意容量是4。
        RingBuffer<DataEvent> ringBuffer =
                RingBuffer.createSingleProducer(new DataEventFactory(), 4);

        //创建一个事件处理器。
        BatchEventProcessor<DataEvent> batchEventProcessor =
            /*
             * 注意参数：数据提供者就是RingBuffer、序列栅栏也来自RingBuffer
             * EventHandler使用自定义的。
             */
                new BatchEventProcessor<DataEvent>(ringBuffer,
                        ringBuffer.newBarrier(), new DataEventHandler());

        //创建一个事件处理器。
        BatchEventProcessor<DataEvent> batchEventProcessor2 =
            /*
             * 注意参数：数据提供者就是RingBuffer、序列栅栏也来自RingBuffer
             * EventHandler使用自定义的。
             */
                new BatchEventProcessor<DataEvent>(ringBuffer,
                        ringBuffer.newBarrier(), new KickAssEventHandler());

        //将事件处理器本身的序列设置为ringBuffer的追踪序列。
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
        ringBuffer.addGatingSequences(batchEventProcessor2.getSequence());

        //启动事件处理器。
        new Thread(batchEventProcessor).start();
        new Thread(batchEventProcessor2).start();

        //往RingBuffer上发布事件
        for(int i=0;i<10;i++){
            ringBuffer.publishEvent(new DataEventTranslatorWithIdAndValue(), i, i+"s");
            System.out.println("发布事件["+i+"]");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理事件, 多个处理者
     */
    @org.junit.Test
    public void test4() {
        //创建一个RingBuffer，注意容量是4。
        RingBuffer<DataEvent> ringBuffer =
                RingBuffer.createSingleProducer(new DataEventFactory(), 4);

        //创建3个WorkHandler
        DataWorkHandler handler1 = new DataWorkHandler("1");
        DataWorkHandler handler2 = new DataWorkHandler("2");
        DataWorkHandler handler3 = new DataWorkHandler("3");

        WorkerPool<DataEvent> workerPool =
                new WorkerPool<DataEvent>(ringBuffer,
                        ringBuffer.newBarrier(),
                        new IgnoreExceptionHandler(),
                        handler1,
                        handler2,
                        handler3);

        //将WorkPool的工作序列集设置为ringBuffer的追踪序列。
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        //创建一个线程池用于执行Workhandler。
        Executor executor = Executors.newFixedThreadPool(4);
        //启动WorkPool。
        workerPool.start(executor);

        //往RingBuffer上发布事件
        for(int i=0;i<10;i++){
            ringBuffer.publishEvent(new DataEventTranslatorWithIdAndValue(), i, i+"s");
            System.out.println("发布事件["+i+"]");
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 等待策略
     * BlockingWaitStrategy
     *      阻塞等待。当要求节省CPU资源，而不要求高吞吐量和低延迟的时候使用这个策略。
     * BusySpinWaitStrategy
     *      自旋等待。这种策略会利用CPU资源来避免系统调用带来的延迟抖动，当线程可以绑定到指定CPU(核)的时候可以使用这个策略。
     * LiteBlockingWaitStrategy
     *      相比BlockingWaitStrategy，LiteBlockingWaitStrategy的实现方法也是阻塞等待，但它会减少一些不必要的唤醒。从源码的注释
     *      上看，这个策略在基准性能测试上是会表现出一些性能提升，但是作者还不能完全证明程序的正确性。
     * SleepingWaitStrategy
     *      先自旋，不行再临时让出调度(yield)，不行再短暂的阻塞等待。对于既想取得高性能，由不想太浪费CPU资源的场景，这个策略是一种
     *      比较好的折中方案。使用这个方案可能会出现延迟波动。
     * TimeoutBlockingWaitStrategy
     *      阻塞给定的时间，超过时间的话会抛出超时异常。
     * YieldingWaitStrategy
     *      先自旋(100次)，不行再临时让出调度(yield)。和SleepingWaitStrategy一样也是一种高性能与CPU资源之间取舍的折中方案，但这
     *      个策略不会带来显著的延迟抖动。
     * PhasedBackoffWaitStrategy
     *      先自旋(10000次)，不行再临时让出调度(yield)，不行再使用其他的策略进行等待。可以根据具体场景自行设置自旋时间、yield时间
     *      和备用等待策略。
     */

}
