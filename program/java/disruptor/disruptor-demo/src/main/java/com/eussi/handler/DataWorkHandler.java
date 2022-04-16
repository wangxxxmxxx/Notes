package com.eussi.handler;

import com.eussi.base.DataEvent;
import com.lmax.disruptor.WorkHandler;

/**
 * Created by wangxueming on 2019/6/23.
 */
public class DataWorkHandler implements WorkHandler<DataEvent> {

    private String name;

    public DataWorkHandler(String name) {
        this.name = name;
    }
    @Override
    public void onEvent(DataEvent event) throws Exception {
        System.out.println("WorkHandler["+name+"]处理事件"+event.getData());
    }
}