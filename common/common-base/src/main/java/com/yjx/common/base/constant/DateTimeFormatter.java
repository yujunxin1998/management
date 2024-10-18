package com.yjx.common.base.constant;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 时间戳解析常量
 * @author yjxbz
 */
public class DateTimeFormatter {

    /**　时间戳　*/
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    {
        DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    /**　日期　*/
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");



}
