package com.eussi;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wangxueming on 2019/6/20.
 */
public class DataEventFactory implements EventFactory<DataEvent> {
    @Override
    public DataEvent newInstance() {
        return new DataEvent();
    }
}