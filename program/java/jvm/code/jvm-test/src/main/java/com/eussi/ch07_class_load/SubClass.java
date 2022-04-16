package com.eussi.ch07_class_load;

/**
 * 被动使用类字段演示一：
 *      通过子类引用父类的静态字段，不会导致子类初始化
 *
 * @author wangxueming
 */
public class SubClass extends SuperClass {
    static{
        System.out.println("SubClass init!");
    }
}