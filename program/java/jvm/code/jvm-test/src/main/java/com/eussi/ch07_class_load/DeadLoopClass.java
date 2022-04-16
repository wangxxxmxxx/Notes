package com.eussi.ch07_class_load;

/**
 * @author wangxueming
 */
public class DeadLoopClass {
    static {
        /*
        如果不加上if语句，编译器将提示 Initializer must be able to complete normally
         */
        if(true) {
            System.out.println(Thread.currentThread() + " init DeadLoopClsss");
            while(true) {
            }
        }
    }
}
class RunDead {
    public static void main(String[] args) {
        final Runnable script = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + " run over");
            }
        };
        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
