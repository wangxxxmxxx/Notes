package com.eussi;

import com.lmax.disruptor.EventTranslator;

/**
 * Created by wangxueming on 2019/6/20.
 */
public class DataEventTranslator implements EventTranslator<DataEvent> {
    private Data data;

    public DataEventTranslator(Data data) {
        this.data = data;
    }

    @Override
    public void translateTo(DataEvent event, long sequence) {
        event.setData(data);
    }
}