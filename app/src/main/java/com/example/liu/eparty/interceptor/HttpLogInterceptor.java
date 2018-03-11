package com.example.liu.eparty.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/30:16:17
 * introduction：
 */

public class HttpLogInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //打印请求体
        Log.e("111", "======== request ========");
        Log.e("111", "method : " + request.method());
        Log.e("111", "url : " + request.url().toString());
        Response response = chain.proceed(request);
        Log.e("111", "======== response ========");
        Log.e("111", "code : " + response.code());
        MediaType mediaType = response.body().contentType();
        String content= response.body().string();
        //打印json数据
        Log.e("111", "json : " + content);
        //因为response.body().string();只能访问一次，访问后关闭response流
        //因此在拦截打印json数据后要重构一个以使得后面能接收到
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}
