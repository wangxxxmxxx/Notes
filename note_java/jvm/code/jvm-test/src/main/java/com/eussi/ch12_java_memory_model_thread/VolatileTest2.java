package com.eussi.ch12_java_memory_model_thread;

/**
 * volatile变量
 *
 * @author wangxueming
 */
public class VolatileTest2 {
    volatile boolean shutdownRequested;

    public void shutdown() {
        shutdownRequested = true;
    }

    public void doWork() {
        while (!shutdownRequested) {
            //do stuff
        }
    }
}