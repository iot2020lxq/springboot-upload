package com.hcyt.fileupload.Test;

import com.hcyt.fileupload.utils.DateTiemFormatUtils;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 14:56
 */
public class test {

    public static void main(String[] args) {
        String dataToString = DateTiemFormatUtils.createDataToString("yyyy/MM");
        System.out.println(dataToString);
    }
}
