package com.eussi.ch08_bytecode_execution_engine;

/**
 * 方法静态解析演示
 * @author wangxueming
 */
public class StaticResolution {
    public static void sayHello() {
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}
