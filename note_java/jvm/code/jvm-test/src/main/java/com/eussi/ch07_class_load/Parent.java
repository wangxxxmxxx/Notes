package com.eussi.ch07_class_load;

/**
 * <clinit>()方法执行顺序
 * @author wangxueming
 */
public  class Parent {
    public static int A = 1;
    static {
        A = 2;
    }
}
class Sub extends Parent {
    public static int B = A;

    public static void main(String[] args) {
        System.out.println(Sub.B);
    }
}
