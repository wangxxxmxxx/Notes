package com.imooc.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

/**
 * Hadoop HDFS Java API 操作
 */
public class HDFSApp {

    private static final String HDFS_PATH = "hdfs://192.168.198.201:8020";
    private static final String USER = "wangxm";

    FileSystem fileSystem = null;
    Configuration configuration = null;

    @Before
    public void setUp() throws Exception {
        System.out.println("Hhdfs Api setUp");
        configuration = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, USER);
    }

    @After
    public void tearDown() throws Exception {
        configuration = null;
        fileSystem = null;
        System.out.println("hdfs Api tearDown");
    }

    /**
     * 创建HDFS目录
     */
    @Test
    public void mkdir() throws Exception {
        fileSystem.mkdirs(new Path("/hdfsapi/test"));
    }

    /**
     * 创建文件
     */
    @Test
    public void create() throws Exception {
        FSDataOutputStream output = fileSystem.create(new Path("/hdfsapi/test/a.txt"));
        output.write("hello hadoop".getBytes());
        output.flush();
        output.close();
    }

    /**
     * 查看HDFS文件的内容
     */
    @Test
    public void cat() throws Exception {
        FSDataInputStream in = fileSystem.open(new Path("/hdfsapi/test/a.txt"));
        IOUtils.copyBytes(in, System.out, 1024);
        System.out.println();
        in.close();
    }


    /**
     * 重命名
     */
    @Test
    public void rename() throws Exception {
        Path oldPath = new Path("/hdfsapi/test/a.txt");
        Path newPath = new Path("/hdfsapi/test/b.txt");
        fileSystem.rename(oldPath, newPath);
    }

    /**
     * 上传文件到HDFS
     *
     * @throws Exception
     */
    @Test
    public void copyFromLocalFile() throws Exception {
        Path localPath = new Path("D:\\appPkg\\hadoop-2.6.0-cdh5.7.0.tar.gz");
        Path hdfsPath = new Path("/hdfsapi/test/hadoop-2.6.0-cdh5.7.0.tar.gz");
        fileSystem.copyFromLocalFile(localPath, hdfsPath);
    }

    /**
     * 上传文件到HDFS
     */
    @Test
    public void copyFromLocalFileWithProgress() throws Exception {
        InputStream in = new BufferedInputStream(
                new FileInputStream(
                        new File("D:\\appPkg\\hadoop-2.6.0-cdh5.7.0.tar.gz")));
        FSDataOutputStream output = fileSystem.create(new Path("/hdfsapi/test/_hadoop-2.6.0-cdh5.7.0.tar.gz"),
                new Progressable() {
                    public void progress() {
                        System.out.print(".");  //带进度提醒信息
                    }
                });


        IOUtils.copyBytes(in, output, 4096);
    }


    /**
     * 下载HDFS文件
     *
     * java.lang.NullPointerException
     * 	at java.lang.ProcessBuilder.start(ProcessBuilder.java:1012)
     * 	at org.apache.hadoop.util.Shell.runCommand(Shell.java:505)
     */
//    @Test
//    public void copyToLocalFile() throws Exception {
//        Path localPath = new Path("./test.txt");
//        Path hdfsPath = new Path("/hdfsapi/test/b.txt");
//        fileSystem.copyToLocalFile(hdfsPath, localPath);
//    }

    /**
     * 查看某个目录下的所有文件
     */
    @Test
    public void listFiles() throws Exception {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));

        for(FileStatus fileStatus : fileStatuses) {
            String isDir = fileStatus.isDirectory() ? "文件夹" : "文件";
            short replication = fileStatus.getReplication();
            long len = fileStatus.getLen();
            String path = fileStatus.getPath().toString();
            System.out.println(isDir + "\t" + replication + "\t" + len + "\t" + path);
        }

    }

    /**
     * 删除
     */
    @Test
    public void delete() throws Exception{
        boolean succ = fileSystem.delete(new Path("/hdfsapi"), true); // 根路径执行失败，应该不允许删除根路径
        System.out.println(succ);
    }




}
