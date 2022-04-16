package com.eussi.ch10_compile_optimization;

/**
 * @author wangxueming
 * @create 2020-04-16 22:44
 * @description
 */
public class ConditionCompile {
    public static void main(String[] args) {
        if (true) {
            System.out.println("block 1");
        } else {
            System.out.println("block 2");
        }
    }
}
