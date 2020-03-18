package com.eussi.ch04_monitor_tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试内存监控
 * VM Args: -Xms100m -Xmx100m -XX:+UseSerialGC
 *
 * @author wangxueming
 */
public class MemoryMonitor {
    /**
     * 内存占位符对象，一个OOMObject大约占64KB
     */
    static class OOMbject {
        public byte[] placeholder = new byte[64 * 1024];
    }
    public static void fillHeap(int num) throws InterruptedException {
        List<OOMbject> list = new ArrayList<OOMbject>();
        for(int i=0; i<num; i++) {
            //稍作延时，另监视曲线的变化更加明显
            Thread.sleep(100);
            list.add(new OOMbject());
        }
        System.gc();
        Thread.sleep(60000);//等待一段时间，观察平稳阶段
    }

    public static void main(String[] args) throws Exception{
        fillHeap(1000);
    }
}
