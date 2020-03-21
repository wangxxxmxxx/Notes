package com.eussi.ch06_class_structure.split_class.util;

import com.eussi.ch06_class_structure.split_class.Constants;

/**
 * @author wangxueming
 * @create 2020-03-21 0:55
 * @description
 */
public class PrintUtil {

    public static void printBytesHex(byte[] bytes) {
        //print head
        println("———— FILE to HEX ————\nOFFSET    0  1  2  3  4  5  6  7   8  9  A  B  C  D  E  F");
        int rowcount = 0;//record how many byte print in row

        StringBuffer sb = null;
        for(int i=0; i<bytes.length; i++) {
            byte b = bytes[i];
            if(rowcount==0) {
                System.out.print(int2Others(i, 4, Constants.hexArr, true) + " ");
                sb = new StringBuffer(" ");
            }

            int index1 = b>>4 & 0x0f;
            int index2 = b & 0x0f;

            if(b>=32 && b <= 126) //if visible char
                sb.append((char)b);
            else
                sb.append('.');
            System.out.print("" + Constants.hexArr[index1] + Constants.hexArr[index2] + " ");
            if(rowcount==7) { //seprator
                System.out.print(" ");
                sb.append(' ');
            }

            rowcount ++;

            //complete space
            if(i==bytes.length-1) { //last byte
                //Completion of byte
                for(int j = bytes.length%16; j<16; j++) {
                    System.out.print("   ");//two chars and spaces
                    if(j==8)
                        System.out.print(" ");//if in middle, add one more space
                }
                println(sb.toString());
            }

            if(rowcount==16) {
                rowcount = 0;
                println(sb.toString());
                sb = null;
            }
        }
    }

    private static String int2Others(int i, int shift, char[] mapArr, boolean prZero) {

        char[] buf = new char[32];
        int charPos = 32;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            buf[--charPos] = mapArr[i & mask];
            i >>>= shift;//Unsigned shift to the right
        } while (i != 0);

        if(prZero) {
            int m = 32 - 32/shift;
            while(charPos>m) {
                buf[--charPos] = mapArr[0];//add zero
            }
        }
        return new String(buf, charPos, (32 - charPos));
    }

    public static void print(String s, Object... objs) {
        println("\n---\n");
    }

    public static void printSep() {
        println("\n---\n");
    }

    public static void println(String s, Object ... paddings) {
        printNoBreak(s, paddings);
        System.out.println();
    }

    /**
     * 支持占位符的打印 {}
     * @param s
     * @param paddings
     */
    public static void printNoBreak(String s, Object ... paddings) {
        for(Object obj: paddings) {
            String padding = obj.toString();
            if(s.indexOf("{}")!=-1) {
                s = s.replaceFirst("\\{\\}", padding);
            }
        }

        System.out.print(s);
    }
}
