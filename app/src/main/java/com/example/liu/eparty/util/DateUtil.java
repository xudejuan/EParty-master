package com.example.liu.eparty.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 日期工具类
 */

public class DateUtil {

    private static SimpleDateFormat simpleDateFormat =
            (SimpleDateFormat) SimpleDateFormat.getInstance();

    public static int getSecond() {
        simpleDateFormat.applyPattern("ss");
        return Integer.valueOf(simpleDateFormat.format(new Date()));
    }

    public static int getMinute() {
        simpleDateFormat.applyPattern("mm");
        return Integer.valueOf(simpleDateFormat.format(new Date()));
    }

    public static int getHour() {
        simpleDateFormat.applyPattern("hh");
        return Integer.valueOf(simpleDateFormat.format(new Date()));
    }

    public static int getDay() {
        simpleDateFormat.applyPattern("dd");
        return Integer.valueOf(simpleDateFormat.format(new Date()));
    }

    public static int getMonth() {
        simpleDateFormat.applyPattern("MM");
        return Integer.valueOf(simpleDateFormat.format(new Date()));
    }

    public static int getYear() {
        simpleDateFormat.applyPattern("yyyy");
        return Integer.valueOf(simpleDateFormat.format(new Date()));
    }

    public static String getTime() {
        return getYear() + "-" + getMonth() + "-" + getDay() + " " + getHour() + ":" + getMinute();
    }

    public static String getTimeWithSecond() {
        return getYear() + "-" + getMonth() + "-" + getDay() + " " + getHour() + ":" + getMinute() + ":" + getSecond();
    }

    public static String getTimeWithoutSecond(String time) {
        return time.substring(0, time.length() - 3);
    }

    /*
    *  2017-11-11 11:11:11 (length=19)
    */
    public static boolean isDateTimeFormat(String string) {
        return string.length() == 19;
    }
}
