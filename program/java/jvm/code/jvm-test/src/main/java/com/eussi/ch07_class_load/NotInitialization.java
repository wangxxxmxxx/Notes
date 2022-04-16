package com.eussi.ch07_class_load;

/**
 * 非主动使用类字段演示
 *
 * @author wangxueming
 */
public class NotInitialization {
    public static void main(String[] args) {
//        System.out.println(SubClass.value);

        //通过数组定义来引用类，不会触发此类的初始化
//        SubClass[] sca = new SubClass[10];

        System.out.println(ConstClass.HELLOWORLD);

    }
}
