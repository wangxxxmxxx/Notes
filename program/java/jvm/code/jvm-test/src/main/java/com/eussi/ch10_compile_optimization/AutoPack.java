package com.eussi.ch10_compile_optimization;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author wangxueming
 */
public class AutoPack {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    public static void test1() {
        List<Integer> list= Arrays.asList(1,2,3,4);
        //如果在JDK 1.7中,还有另外一颗语法糖(在本章完稿之后,此语法糖随着Project Coin一起被划分到JDK 1.8中了,在JDK 1.7里不会包括
        //能让上面这句代码进一步简写成List＜Integer＞list=[1,2,3,4];
        int sum=0;
        for(int i : list){
            sum+=i;
        }
        System.out.println(sum);
    }

    public static void test2() {
        List list=Arrays.asList(new Integer[]{
                Integer.valueOf(1),
        Integer.valueOf(2),
        Integer.valueOf(3),
        Integer.valueOf(4)});
        int sum=0;
        for(Iterator localIterator = list.iterator(); localIterator.hasNext();){
            int i=((Integer)localIterator.next()).intValue();
            sum+=i;
        }
        System.out.println(sum);
    }

    public static void test3() {
        Integer a=1;
        Integer b=2;
        Integer c=3;
        Integer d=3;
        Integer e=321;
        Integer f=321;
        Long g=3L;
        System.out.println(c==d);
        System.out.println(e==f);
        System.out.println(c==(a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g==(a+b));
        System.out.println(g.equals(a+b));
    }
}
