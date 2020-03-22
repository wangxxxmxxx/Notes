package com.eussi.ch07_class_load;

/**
 * 被动使用类字段演示三：
 *      常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义
 *      的常量的类，因此不会触发定义的类的初始化
 *
 * @author wangxueming
 */
public class ConstClass {
    static{
        System.out.println("ConstClass init!");
    }

    public static final String HELLOWORLD = "HelloWorld";
}
