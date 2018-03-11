package com.example.liu.eparty;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.liu.eparty.bean.User;
import com.example.liu.eparty.interceptor.HttpLogInterceptor;
import com.example.liu.eparty.util.ACache;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 自定义application类
 */

public class MyApplication extends Application {

    private String token;
    private User user;

    private static MyApplication mInstance;

    public static MyApplication getInstance(){
        return mInstance;
    }

    //刷新控件的头部底部
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                return new MaterialHeader(mInstance).setColorSchemeColors(mInstance.getResources().getColor(R.color.blue_deep));
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new BallPulseFooter(mInstance).setAnimatingColor(mInstance.getResources().getColor(R.color.blue_deep))
                        .setNormalColor(mInstance.getResources().getColor(R.color.blue_deep));
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initAutoLayout();
        initOkHttp();
        initUserAndToken();
    }

    private void initAutoLayout() {
        AutoLayoutConifg.getInstance().useDeviceSize();
    }

    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HttpLogInterceptor())
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void initUserAndToken() {
        this.user = (User) ACache.get(mInstance, "user").getAsObject("user");
        this.token = ACache.get(mInstance, "token").getAsString("token");
        if (token == null){
            token = "";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        ACache.get(mInstance, "user").put("user", user);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        ACache.get(mInstance, "token").put("token", token);
    }
}
