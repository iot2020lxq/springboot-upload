package com.hcyt.fileupload.utils;


import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liuxiangqian
 * @version 2020/3/24 0024 - 14:44
 */
public class DateTiemFormatUtils {

    //创建一个日期，并将日期格式化为String类型
    public static String createDataToString(String dateStr){
        Date date = new Date();
        //格式化日期的类
        DateFormat dateFormat = new SimpleDateFormat(dateStr);
        //格式化日期
        String format = dateFormat.format(date);
        return format;
    }

}
