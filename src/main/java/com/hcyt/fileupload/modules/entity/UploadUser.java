package com.hcyt.fileupload.modules.entity;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 9:36
 */
public class UploadUser {

    private String password; //上传文件时的密码字段

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
