package com.example.liu.eparty.util;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;


/**
 * 异常信息工具类
 */

public class ThrowableUtil {

    public static String getThrowableMessage(Throwable e){
        if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            return "连接错误";
        } else if (e instanceof InterruptedIOException) {
            return "连接超时";
        } else {
            return "未知错误";
        }
    }
}
