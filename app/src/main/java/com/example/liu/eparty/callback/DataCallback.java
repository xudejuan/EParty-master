package com.example.liu.eparty.callback;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;

import com.example.liu.eparty.MyApplication;
import com.example.liu.eparty.activity.login.LoginActivity;
import com.example.liu.eparty.bean.Info;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.example.liu.eparty.util.TipDialogUtil;
import com.example.liu.eparty.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class DataCallback<T> extends Callback<Info<T>> {

    private TipDialogUtil tipDialogUtil;
    private Context context;

    public DataCallback(Context context){
        this.context = context;
        tipDialogUtil = new TipDialogUtil();
        tipDialogUtil.showLoading(context, "加载中...");
    }

    @Override
    public Info<T> parseNetworkResponse(Response response, int id) throws Exception {
        String json = response.body().string();
        return new Gson().fromJson(json, new TypeToken<Info<T>>(){}.getType());
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        tipDialogUtil.dismiss();
        if (e instanceof ConnectException) {
            ToastUtil.show(context, "HTTP错误");
        } else if ( e instanceof UnknownHostException) {
            ToastUtil.show(context, "网络连接错误");
        } else if (e instanceof InterruptedIOException) {
            ToastUtil.show(context, "网络连接超时");
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ToastUtil.show(context, "解析错误");
        } else {
            ToastUtil.show(context, "未知错误");
        }
    }

    @Override
    public void onResponse(Info<T> response, int id) {
        tipDialogUtil.dismiss();
        if (response.getStatus() == 1){
            SmartRefreshLayoutUtil.setTotalPage(response.getTotal() / 10 + 1);
            showData(response.getData());
        }else if (response.getStatus() == -10){
            ToastUtil.show(MyApplication.getInstance(), "登录失效，请重新登录");
            context.startActivity(new Intent(context, LoginActivity.class));
        }else {
            ToastUtil.show(MyApplication.getInstance(), response.getMessage());
        }
    }

    public void showData(List<T> mDatas){}
}