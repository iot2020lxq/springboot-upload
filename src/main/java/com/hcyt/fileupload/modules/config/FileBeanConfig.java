package com.hcyt.fileupload.modules.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.dc.pr.PRError;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author liuxiangqian
 * @version 2020/3/26 0026 - 11:19
 */
@Configuration
public class FileBeanConfig {

    @Value("${autoSacnFilePath}")
    private String autoSacnFilePath;

    @Value("${backupFilePath}")
    private String backupFilePath;

    @Value("${logFilePath}")
    private String logFilePath;

    @Value("${logFileName}")
    private String getLogFileName;

    /*
        扫描的文件
     */
    @Bean(name = "autoSacnFile")
    public File getAutoSacnFile(){
        return new File(autoSacnFilePath);
    }

    /*
        备份文件
     */
    @Bean(name = "backupFile")
    public File getBackupFile(){
        File backupFile = new File(backupFilePath);
        if(!backupFile.exists()){
            backupFile.mkdirs();
        }
        return backupFile;
    }

    /*
        日志文件
     */
    @Bean(name = "logFile")
    public File getLogFile(){
        File logFolder = new File(logFilePath);
        if(!logFolder.exists()){
            logFolder.mkdirs();
        }
        File logFile = new File(logFolder,getLogFileName);
        if(!logFile.exists()){
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logFile;
    }

}
