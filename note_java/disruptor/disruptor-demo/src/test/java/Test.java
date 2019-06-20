import com.eussi.*;
import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.CountDownLatch;

/**
 * Created by wangxueming on 2019/6/20.
 */
public class Test {

    /**
     * ringbuffer初始化测试
     */
    @org.junit.Test
    public void test() {
        RingBuffer<DataEvent> ringBuffer =
                RingBuffer.createSingleProducer(new DataEventFactory(), 1024);
        DataEvent dataEvent = ringBuffer.get(0);
        System.out.println("Event = " + dataEvent);
        System.out.println("Data = " + dataEvent.getData());
    }

    /**
     * ringbuffer发布事件
     */
    @org.junit.Test
    public void test2() {
        RingBuffer<DataEvent> ringBuffer =
                RingBuffer.createSingleProducer(new DataEventFactory(), 1024);

        //发布事件!!!
        Data data = new Data(1, "Test data ! ");
        ringBuffer.publishEvent(new DataEventTranslator(data));

        DataEvent dataEvent0 = ringBuffer.get(0);
        System.out.println("Event = " + dataEvent0);
        System.out.println("Data = " + dataEvent0.getData());
        DataEvent dataEvent1 = ringBuffer.get(1);
        System.out.println("Event = " + dataEvent1);
        System.out.println("Data = " + dataEvent1.getData());
    }

    /**
     * ringbuffer发布事件2
     */
    @org.junit.Test
    public void test3() {
        RingBuffer<DataEvent> ringBuffer =
                RingBuffer.createSingleProducer(new DataEventFactory(), 1024);

        //发布事件!!!
        ringBuffer.publishEvent(new DataEventTranslatorWithIdAndValue(), 1, "Test data !");

        DataEvent dataEvent0 = ringBuffer.get(0);
        System.out.println("Event = " + dataEvent0);
        System.out.println("Data = " + dataEvent0.getData());
        DataEvent dataEvent1 = ringBuffer.get(1);
        System.out.println("Event = " + dataEvent1);
        System.out.println("Data = " + dataEvent1.getData());
    }

    /**
     * ringbuffer发布事件3
     */
    @org.junit.Test
    public void test4() {
        RingBuffer<DataEvent> ringBuffer = RingBuffer.createSingleProducer(new DataEventFactory(), 1024);
        long sequence = ringBuffer.next();
        try{
            DataEvent event = ringBuffer.get(sequence);
            Data data = new Data(1, "Test data !");
            event.setData(data);
        }finally{
            ringBuffer.publish(sequence);
        }
        DataEvent dataEvent0 = ringBuffer.get(0);
        System.out.println("Event = " + dataEvent0);
        System.out.println("Data = " + dataEvent0.getData());
        DataEvent dataEvent1 = ringBuffer.get(1);
        System.out.println("Event = " + dataEvent1);
        System.out.println("Data = " + dataEvent1.getData());
    }

    /**
     * ringbuffer多线程发布事件
     */
    @org.junit.Test
    public void test5() {
        final RingBuffer<DataEvent> ringBuffer = RingBuffer.createMultiProducer(new DataEventFactory(), 1024);
        final CountDownLatch latch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            final int index = i;
            //开启多个线程发布事件。
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long sequence = ringBuffer.next();
                    try{
                        DataEvent event = ringBuffer.get(sequence);
                        Data data = new Data(index, index+"s");
                        event.setData(data);
                    }finally{
                        ringBuffer.publish(sequence);
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            latch.await();
            //最后观察下发布的时间。
            for(int i=0;i<11;i++){
                DataEvent event = ringBuffer.get(i);
                System.out.println(event.getData());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * ringbuffer多线程环境使用单线程生产者发布事件
     */
    @org.junit.Test
    public void test6() {
        final RingBuffer<DataEvent> ringBuffer =
                //这里是单线程模式!!!
                RingBuffer.createSingleProducer(new DataEventFactory(), 1024);
        final CountDownLatch latch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            final int index = i;
            //开启多个线程发布事件。
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long sequence = ringBuffer.next();
                    try{
                        DataEvent event = ringBuffer.get(sequence);
                        Data data = new Data(index, index+"s");
                        event.setData(data);
                    }finally{
                        ringBuffer.publish(sequence);
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            latch.await();
            //最后观察下发布的时间。
            for(int i=0;i<11;i++){
                DataEvent event = ringBuffer.get(i);
                System.out.println(event.getData());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
