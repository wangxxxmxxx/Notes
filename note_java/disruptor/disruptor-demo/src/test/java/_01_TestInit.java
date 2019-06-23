import com.eussi.base.DataEvent;
import com.eussi.publish.DataEventFactory;
import com.lmax.disruptor.RingBuffer;

/**
 * Created by wangxueming on 2019/6/20.
 */
public class _01_TestInit {

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

}
