package com.hcyt.fileupload.utils;

import com.hcyt.fileupload.core.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 11:05
 */
@Component
public class MyUploadFilesUtils extends BaseController {

    //文件上传成功的标识
    private boolean flag = true;

    //日志文件上传成功的标识
    public boolean logFlag = true;

    //文件上传的数量
    public Integer fileCount = 0;

    private static Logger logger = LoggerFactory.getLogger(MyUploadFilesUtils.class);

    public void myUpload(String endpiont,String accessId,String accessKey,String bucket,String url, File srcFile,
                              File logFile,OssUpload ossUpload ) throws Exception {
        //得到日期
        String dataToString = DateTiemFormatUtils.createDataToString("yyyyMM");

        //将扫描的文件上传到oss服务器
        if(srcFile.isDirectory()){
            File[] srcFiles = srcFile.listFiles();
            for (File file : srcFiles) {
                if(file.isFile()){
                    //文件名称
                    String srcFileName = file.getName();
                    //得到文件后缀名
                    String fileSuffix = srcFileName.substring(srcFileName.lastIndexOf(".") + 1);
                    //oss上传路径
                    String ossUrl = null;
                    //判断文件类型，分别上传到不同的oss路径
                    if(fileSuffix.equalsIgnoreCase("jpg")|| fileSuffix.equalsIgnoreCase("png") ||
                            fileSuffix.equalsIgnoreCase("gif") || fileSuffix.equalsIgnoreCase("bmp") ||
                            fileSuffix.equalsIgnoreCase("jpeg")){
                        try {
                            ossUrl = ossUpload.upLoad2(endpiont, accessId, accessKey, bucket, photos+dataToString+"/", url, file);
                            fileCount++;
                        } catch (Exception e) {
                            flag = false;
                            logger.info(file.getName()+"文件上传发生异常！");
                            throw new Exception();
                        }
                    }else if(fileSuffix.equalsIgnoreCase("mp3")){
                        try {
                            ossUrl = ossUpload.upLoad2(endpiont, accessId, accessKey, bucket, audios+dataToString+"/", url, file);
                            fileCount++;
                        } catch (Exception e) {
                            flag = false;
                            logger.info(file.getName()+"文件上传发生异常！ ");
                            throw new Exception();
                        }
                    }else if(fileSuffix.equalsIgnoreCase("mp4") || fileSuffix.equalsIgnoreCase("avi") ||
                            fileSuffix.equalsIgnoreCase("flv")){
                        try {
                            ossUrl = ossUpload.upLoad2(endpiont, accessId, accessKey, bucket, videos+dataToString+"/", url, file);
                            fileCount++;
                        } catch (Exception e) {
                            flag = false;
                            logger.info(file.getName()+"文件上传发生异常！  ");
                            throw new Exception();
                        }
                    }
                    else {
                        try {
                            ossUrl = ossUpload.upLoad2(endpiont, accessId, accessKey, bucket, resources+dataToString+"/", url, file);
                            fileCount++;
                        } catch (Exception e) {
                            flag = false;
                            logger.info(file.getName()+"文件上传发生异常！   ");
                            throw new Exception();
                        }
                    }
                    if(ossUrl != null){
                        logger.info(ossUrl);
                        //生成日志文件
                        String content = "文件名：" + srcFileName + "\r\n" + "文件路径：" + ossUrl;
                        try{
                            FilesUtils.appendContentAndNewLine(logFile,content);
                            logger.info(srcFileName+"文件生成日志成功！");
                        }catch (Exception e){
                            logger.info(srcFileName+"文件生成日志失败！");
                            logFlag = false;
                            flag = false;
                            throw new Exception();
                        }
                    }
                }else if(file.isDirectory()){
                    myUpload(endpiont, accessId, accessKey, bucket, url,file,logFile,ossUpload);
                }
                //上传成功,删除上传的文件，可以防止重复打印日志
                if(flag){
                    file.delete();
                }
            }
        }
    }
}
