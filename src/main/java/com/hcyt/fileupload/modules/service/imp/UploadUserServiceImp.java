package com.hcyt.fileupload.modules.service.imp;

import com.hcyt.fileupload.modules.entity.UploadUser;
import com.hcyt.fileupload.modules.mapper.UploadUserMapper;
import com.hcyt.fileupload.modules.service.UploadUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 9:35
 */
@Service
public class UploadUserServiceImp implements UploadUserService {

    @Autowired
    private UploadUserMapper userMapper;

    /*
        查询上传时的密码
     */
    @Override
    public UploadUser queryUploadPassword(){
        UploadUser user = userMapper.queryUploadPassword();
        return user;
    }
}
