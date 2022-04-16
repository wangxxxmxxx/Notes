package com.eussi.ch11_runtime_optimization;


public class Demo {
    private B b;
    private int y,z,sum;
    static class B {
        int value;

        final int get() {
            return value;
        }
    }

    public void foo() {
        y = b.get();
        //……do stuff……
        z = b.get();
        sum = y + z;
    }
}
