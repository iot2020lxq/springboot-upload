package com.hcyt.fileupload.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 8:47
 */
public class FilesUtils {

    private static Logger logger = LoggerFactory.getLogger(FilesUtils.class);

    /**
     * 递归：
     *  复制文件目录，可以实现多级目录复制
     */
    public static void copyFolder(File srcFolder,File desFolder){
        if (!desFolder.exists()){
            desFolder.mkdirs();
        }
        //判断源文件是否位目录
        if (srcFolder.isDirectory()){
            File newFolder = new File(desFolder,srcFolder.getName());
            if (!newFolder.exists()){
                newFolder.mkdir();
            }
            //获取源文件的子文件
            File[] files = srcFolder.listFiles();
            for (File file : files) {
                //复制文件
                copyFolder(file,newFolder);
            }
        }else if(srcFolder.isFile()){
//            File newFile = new File(desFile,srcFile.getName());
            copyFile(srcFolder,desFolder);
        }
    }

    /**
     * 一模一样的复制文件，对已存在文件进行覆盖
     * @param srcFile
     * @param desFile
     */
    public static void copyFile(File srcFile,File desFile){
        //源文件
        BufferedInputStream bufferedInputStream = null;
        //复制的新文件目录
        BufferedOutputStream bufferedOutputStream = null;
        File newFile = new File(desFile.getAbsolutePath()+"\\"+srcFile.getName());
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(srcFile));
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));

            //开始复制
            int len = bufferedInputStream.available();
            byte[] bytes = new byte[len];
            bufferedInputStream.read(bytes);
            bufferedOutputStream.write(bytes,0,len);
            bufferedOutputStream.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if (bufferedInputStream != null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成文件，并对文件的内容追加，追加一次换行一次
     */
    public static void appendContentAndNewLine(File file,String content) throws Exception {
        if (file.isDirectory()){
         return ;
        }
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.info("日志文件目录不存在，请重新启动系统或手动创建"+file.getAbsolutePath()+"！");
                throw new Exception();
            }
        }
        if (file.isFile()){
            FileOutputStream fileOutputStream = null;
            OutputStreamWriter outputStreamWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fileOutputStream = new FileOutputStream(file, true);
                outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(content);
                bufferedWriter.flush();
                bufferedWriter.newLine();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedWriter != null){
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStreamWriter != null){
                    try {
                        outputStreamWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileOutputStream != null){
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
