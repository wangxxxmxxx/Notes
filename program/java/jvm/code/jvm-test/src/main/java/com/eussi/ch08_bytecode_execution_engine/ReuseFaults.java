package com.eussi.ch08_bytecode_execution_engine;

/**
 * 局部变量表Slot复用对垃圾收集的影响之一
 *
 * @author wangxueming
 */
public class ReuseFaults {
    public static void main(String[] args) {
//        byte[]placeholder=new byte[64*1024*1024];
//        System.gc();

//        {
//            byte[]placeholder=new byte[64*1024*1024];
//        }
//        System.gc();

        {
            byte[]placeholder=new byte[64*1024*1024];
        }
        int a = 0;
        System.gc();
    }
}
