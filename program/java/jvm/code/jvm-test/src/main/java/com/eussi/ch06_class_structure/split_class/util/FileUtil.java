package com.eussi.ch06_class_structure.split_class.util;

import java.io.*;

/**
 * @author wangxueming
 * @create 2020-03-21 0:54
 * @description
 */
public class FileUtil {

    public static byte[] class2Bytes(String classFilePath) {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = new FileInputStream(new File(classFilePath));
            bos = new ByteArrayOutputStream();
            int len = -1;
            while ((len = is.read()) != -1) {
                bos.write(len);
            }
            return bos.toByteArray();
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file, file path:" + classFilePath);
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("read error, file path:" + classFilePath);
            System.exit(-1);
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (bos != null)
                    bos.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
