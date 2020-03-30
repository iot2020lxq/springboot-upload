package com.hcyt.fileupload.globalconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author liuxiangqian
 * @version 2020/3/23 0023 - 12:51
 */
@Component
public class MyCommandRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(MyCommandRunner.class);

    @Value("${spring.auto.openUrl}")
    private boolean isOpen;

    @Value("${spring.web.uploadRequestUrl}")
    private String loginUrl;

    @Value("${spring.web.start}")
    private String start;

    @Override
    public void run(String... args) throws Exception {
        if(isOpen){
            String cmd = start +" "+ loginUrl;
            Runtime run = Runtime.getRuntime();
            try{
                run.exec(cmd);
                logger.debug("启动浏览器打开项目成功");
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
}
