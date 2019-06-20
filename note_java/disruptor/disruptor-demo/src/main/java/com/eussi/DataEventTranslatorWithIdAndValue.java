package com.eussi;

import com.lmax.disruptor.EventTranslatorTwoArg;

/**
 * Created by wangxueming on 2019/6/20.
 */
public class DataEventTranslatorWithIdAndValue implements EventTranslatorTwoArg<DataEvent, Integer, String> {
    @Override
    public void translateTo(DataEvent event, long sequence, Integer id,
                            String value) {
        Data data = new Data(id, value);
        event.setData(data);
    }
}