package com.hcyt.fileupload.modules.mapper;

import com.hcyt.fileupload.modules.entity.UploadUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 9:38
 */
@Mapper
@Repository
public interface UploadUserMapper {

    @Select("select password from user_table_upload limit 1")
    public UploadUser queryUploadPassword();

}
