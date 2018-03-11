package com.example.liu.eparty.util;

import com.example.liu.eparty.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/7:13:58
 * introduction：
 */

public class MyOkHttpUtil {

    public static PostFormBuilder post(String url){
        return OkHttpUtils.post()
                .url(ConstantUtil.BASE_URL + url)
                .addParams("identify", "mTerminal")
                .addParams("token", MyApplication.getInstance().getToken());
    }

    public static GetBuilder get(String url){
        return OkHttpUtils.get()
                .url(ConstantUtil.BASE_URL + url)
                .addParams("identify", "mTerminal")
                .addParams("token", MyApplication.getInstance().getToken());
    }
}
