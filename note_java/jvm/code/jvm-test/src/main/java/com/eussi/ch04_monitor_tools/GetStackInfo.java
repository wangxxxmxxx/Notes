package com.eussi.ch04_monitor_tools;

import java.util.Map;

/**
 * 功能类似于jstack命令
 *
 * @author wangxueming
 *
 */
public class GetStackInfo {
    public static void main(String[] args) {
        for(Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
            Thread thread = stackTrace.getKey();
            StackTraceElement[] stack = stackTrace.getValue();
//            if(thread.equals(Thread.currentThread()))
//                continue;
            System.out.println("\n线程：" + thread.getName() + "\n");
            for(StackTraceElement element : stack) {
                System.out.println("\t" + element + "\n");
            }
        }
        System.gc();
    }
}
