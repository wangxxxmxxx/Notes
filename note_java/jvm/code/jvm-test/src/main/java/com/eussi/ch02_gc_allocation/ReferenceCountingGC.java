package com.eussi.ch02_gc_allocation;

/**
 * 执行后，objA和objB会不会被GC呢？
 *
 * VM args -XX:+PrintGCDetails
 *
 * @author wangxueming
 *
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;
    /**
     * 这个成员属性唯一意义就是占点内存，以便能在GC日志中看清楚是否被回收过
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        //假设在这行发生GC,objA和objB能否被回收？
        System.gc();
    }

}
