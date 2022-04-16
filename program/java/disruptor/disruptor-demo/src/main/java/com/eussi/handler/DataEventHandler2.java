package com.eussi.handler;

import com.eussi.base.DataEvent;
import com.lmax.disruptor.EventHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class DataEventHandler2 implements EventHandler<DataEvent> {
    @Override
    public void onEvent(DataEvent event, long sequence, boolean endOfBatch)
            throws Exception {
        //注意这里小睡眠了一下!!
//        TimeUnit.SECONDS.sleep(3);
        System.out.println("handle event's data:" + event.getData() +"isEndOfBatch:"+endOfBatch);
    }
}