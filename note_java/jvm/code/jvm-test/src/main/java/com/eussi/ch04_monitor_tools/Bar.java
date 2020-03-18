package com.eussi.ch04_monitor_tools;

/**
 * VM Args: -XX:+PrintAssembly -XX:+UnlockDiagnosticVMOptions -Xcomp -XX:CompileCommand=dontinline,*Bar.sum -XX:CompileCommand=compileonly,*Bar.sum
 *
 * @author wangxueming
 */
public class Bar {
    int a = 1;
    static int b = 2;

    public int sum(int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        new Bar().sum(3);
    }
}