package com.eussi.handler;

import com.eussi.base.DataEvent;
import com.lmax.disruptor.EventHandler;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class KickAssEventHandler implements EventHandler<DataEvent> {
    @Override
    public void onEvent(DataEvent event, long sequence, boolean endOfBatch)
            throws Exception {
        System.out.println("kick your ass "+sequence+" times!!!!");
    }
}