package com.hcyt.fileupload.modules.controller;

import com.hcyt.fileupload.core.BaseController;
import com.hcyt.fileupload.json.AjaxJson;
import com.hcyt.fileupload.modules.entity.UploadUser;
import com.hcyt.fileupload.modules.service.imp.UploadUserServiceImp;
import com.hcyt.fileupload.utils.FilesUtils;
import com.hcyt.fileupload.utils.MyUploadFilesUtils;
import com.hcyt.fileupload.utils.OssUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Scanner;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 9:40
 */
@Controller
public class UploadController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadUserServiceImp userService;

    @Autowired
    MyUploadFilesUtils myUploadFilesUtils;

    @Autowired
    private OssUpload ossUpload;

    @Resource(name = "autoSacnFile")
    private File srcFile;

    @Resource(name = "backupFile")
    private File desFile;

    @Resource(name = "logFile")
    private File logFile;

    @RequestMapping("/fileUpload")
    @ResponseBody
    public AjaxJson ScanFilesUpload(){

        //初始化上传文件数量
        myUploadFilesUtils.fileCount = 0;

        AjaxJson ajaxJson = new AjaxJson();

        //得到上传文件的密码
        UploadUser user = userService.queryUploadPassword();

        String password = user.getPassword();

        while (true){
            long start = 0;
            long end = 0;
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入密码：");
            String str = sc.next();
            if(str.equals(password)){
                start = System.currentTimeMillis();
                logger.info("密码正确，开始扫描文件！");
                logger.info("扫描路径为："+srcFile.getAbsolutePath());
                if(!srcFile.exists()){
                    srcFile.mkdirs();
                }

                File[] srcFiles = srcFile.listFiles();
                //判断是否为空目录
                if(srcFiles.length == 0){
                    logger.info("上传的目录为空目录，退出系统！");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("上传的目录为空目录！");
                    break;
                }

                //开始备份文件
                try {
                    logger.info("文件开始备份，备份目录为："+desFile.getAbsolutePath());
                    FilesUtils.copyFolder(srcFile,desFile);
                    logger.info("文件备份成功！");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("文件备份过程发生错误！");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("文件备份过程发生错误！");
                    break;
                }

                //将扫描的文件上传到oss服务器
                try {
                    logger.info("文件开始上传......");
                    myUploadFilesUtils.myUpload(endpiont, accessId, accessKey, bucket, cdnUrl, srcFile, logFile, ossUpload);
                    end = System.currentTimeMillis();
                    long hours = (end - start)/(1000*60*60);
                    long minute = ((end - start)%(1000*60*60))/(1000*60);
                    long second = (((end - start)%(1000*60*60))%(1000*60))/(1000);
                    logger.info("文件上传完成！");
                    ajaxJson.setSuccess(true);
                    ajaxJson.setMsg("文件上传完成！"+"一共上传了"+myUploadFilesUtils.fileCount+"个文件，"+"耗时"+hours+"时"+minute+"分"+second+"秒！");
                    logger.info("一共上传了"+myUploadFilesUtils.fileCount+"个文件，"+"耗时"+hours+"时"+minute+"分"+second+"秒！");
                    break;
                } catch (Exception e) {
                    //日志内容生成失败
                    if(!myUploadFilesUtils.logFlag){
                        logger.info("发生错误，退出系统！");
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("部分日志内容生成失败！");
                    }else{
                        //文件上传失败
                        logger.info("文件上传过程发生错误！尝试重新启动系统或者手动上传！");
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("文件上传过程发生错误！尝试重新启动系统或者手动上传！");
                    }
                    break;
                }
            }else {
                logger.info("密码错误，退出系统！");
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("密码错误，退出系统！");
                break;
            }
        }
        return ajaxJson;
    }

}
