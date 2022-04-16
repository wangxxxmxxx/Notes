package com.eussi.ch07_class_load;

/**
 * 非法向前引用变量
 * @author wangxueming
 */
public class Test {
    static {
        i = 0;                  //给变量赋值可以正常编译通过
//        System.out.println(i);  //这句编译器提示“Illegal forwawrd reference"
    }
    static int i = 1;
}
