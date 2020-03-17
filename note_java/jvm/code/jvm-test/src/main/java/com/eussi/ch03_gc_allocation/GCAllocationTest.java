package com.eussi.ch03_gc_allocation;

/**
 * @author wangxueming
 */
public class GCAllocationTest {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception{
//        testAllocation();
//        testPretenureSizeThreshold();
//        testTenuringThreshold();
//        testTenuringThreshold2();
//        testTenuringThreshold3();
        testHandlePromotion();
    }

    /**
     * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2,allocation3,allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];//发生一次minor GC
    }

    /**
     * VM Args: -verbose:gc -Xms40M -Xmx40M -Xmn20M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];//直接进入老年代
    }

    /**
     * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     */
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2,allocation3;
        allocation1 = new byte[_1MB / 4];
        //什么时候进入老年代取决于-XX:MaxTenuringThreshold设置
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * VM Args: -verbose:gc -Xms40M -Xmx40M -Xmn20M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
     */
    public static void testTenuringThreshold2() {
        byte[] allocation1, allocation2,allocation3;
        allocation1 = new byte[_1MB / 3];
        allocation2 = new byte[8 * _1MB];
        allocation3 = new byte[8 * _1MB];
        allocation3 = null;
        allocation3 = new byte[8 * _1MB];
    }

    /**
     * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
     */
    public static void testTenuringThreshold3() {
        byte[] allocation1, allocation2,allocation3,allocation4;
        allocation1 = new byte[_1MB / 4];
        //allocation1+allocation2大于survivor空间一半
        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

    /**
     * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:-HandlePromotionFailure
     */
    public static void testHandlePromotion() {
        byte[] allocation1, allocation2,allocation3,allocation4,allocation5,allocation6,allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }

}
