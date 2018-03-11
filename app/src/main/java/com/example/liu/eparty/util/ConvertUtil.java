package com.example.liu.eparty.util;

import java.util.List;

/**
 * 转换工具类
 */

public class ConvertUtil {

    public static <T> String listToString(List<T> list){
        if (list != null) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                s.append(list.get(i)).append(",");
            }
            return s.toString();
        }
        return  null;
    }

    public static String deleteParentheses(String userName){
        for (int i = 0; i < userName.length(); i++) {
            if (userName.charAt(i) == '('){
                return userName.substring(0, i);
            }
        }
        return userName;
    }
}
