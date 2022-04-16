package com.eussi.ch06_class_structure.split_class.util;

import com.eussi.ch06_class_structure.split_class.Constants;

/**
 * @author wangxueming
 * @create 2020-03-21 1:05
 * @description
 */
public class ByteUtil {
    public static int getByteInt(byte[] bytes, int begin) {
        int a = bytes[begin]<<16;  //big-endian
        int b = bytes[begin+1];
        return a+b;
    }

    public static String getByteString(byte[] bytes, int begin, int len) {
        StringBuffer sb = new StringBuffer();
        int end = begin + len;
        for(int i=begin; i < end; i++) {
            byte b = bytes[i];
            char char1 = Constants.hexArr[b>>4 & 0x0F];
            char char2 = Constants.hexArr[b & 0x0F];
            sb.append(char1)
                    .append(char2)
                    .append(' ');
        }
        return sb.toString();
    }
}
