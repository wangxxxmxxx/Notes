package com.eussi.ch06_class_structure.split_class;

import com.eussi.ch06_class_structure.split_class.util.ByteUtil;
import com.eussi.ch06_class_structure.split_class.util.FileUtil;
import static com.eussi.ch06_class_structure.split_class.util.PrintUtil.*;

/**
 * 打印类文件十六进制
 *
 * @description
 */
public class Split {

    public static void main(String[] args) {
        //check args
        if (args.length == 0 || args[0].length() == 0) {
            println("Need a classname arg! like 'package.classname'");
            System.exit(-1);
        }
        println(args[0]);

        run(args[0]);


    }

    private static void run(String arg) {
        //get class file
        String classPath = arg.replaceAll("\\.", "/") + ".class";
        String testClassFile = (Split.class.getResource("/").getFile() + classPath).replaceAll("\\\\", "/");
        println("Class file path: " + testClassFile);

        printSep();

        //read binary file
        byte[] classBytes = FileUtil.class2Bytes(testClassFile);

        printBytesHex(classBytes);

        printSep();

        //index from zero
        println("Magic(0~4): " + ByteUtil.getByteString(classBytes, 0,4));//0~4 magic
        printSep();

        println("Major Version(6~7): " + ByteUtil.getByteInt(classBytes, 6));//7~8 major version
        printSep();

        println("Minor Version(4~5): " + ByteUtil.getByteInt(classBytes, 4));//5~6 major version
        printSep();

        println("constant_pool_count(8~9): " + ByteUtil.getByteInt(classBytes, 8));//5~6 major version
        printSep();
    }

}
