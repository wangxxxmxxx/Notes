import com.eussi.base.DataEvent;
import com.eussi.handler.DataEventHandler;
import com.eussi.handler.KickAssEventHandler;
import com.eussi.publish.DataEventFactory;
import com.eussi.publish.DataEventTranslator;
import com.eussi.publish.DataEventTranslatorWithIdAndValue;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangxueming on 2019/6/20.
 */
public class _05_TestDisruptorFrame {

    /**
     * ringbuffer初始化测试
     */
    @org.junit.Test
    public void test() {
        //创建一个执行器(线程池)。
        Executor executor = Executors.newFixedThreadPool(4);

        //创建一个Disruptor。
        Disruptor<DataEvent> disruptor =
                new Disruptor<DataEvent>(new DataEventFactory(), 4, executor);

        //创建两个事件处理器。
        DataEventHandler handler1 = new DataEventHandler();
        KickAssEventHandler handler2 = new KickAssEventHandler();
        //同一个事件，先用handler1处理再用handler2处理。
        disruptor.handleEventsWith(handler1).then(handler2);

        //启动Disruptor。
        disruptor.start();

        //发布10个事件。
        for(int i=0;i<10;i++){
            disruptor.publishEvent(new DataEventTranslatorWithIdAndValue(), 1, "Test data !");
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

}
