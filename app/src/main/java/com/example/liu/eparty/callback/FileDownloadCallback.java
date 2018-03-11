package com.example.liu.eparty.callback;

import android.content.Context;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.liu.eparty.R;
import com.example.liu.eparty.util.ToastUtil;
import com.google.gson.JsonParseException;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.json.JSONException;

import java.io.File;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import okhttp3.Call;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/24:21:09
 * introduction：
 */

public class FileDownloadCallback extends FileCallBack{

    private MaterialDialog dialog;
    private NumberProgressBar numberProgressBar;
    private Context context;

    public FileDownloadCallback(String destFileDir, String destFileName, Context context) {
        super(destFileDir, destFileName);
        this.context = context;
        initDialog();
    }

    private void initDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.number_progress, null);
        dialog = new MaterialDialog.Builder(context)
                .title("下载中...")
                .customView(view, false)
                .cancelable(false)
                .show();
        numberProgressBar = view.findViewById(R.id.number_progress_bar);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
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
    public void onResponse(File response, int id) {
        dialog.dismiss();
    }

    @Override
    public void inProgress(float progress, long total, int id) {
        super.inProgress(progress, total, id);
        numberProgressBar.setProgress(Float.valueOf(progress).intValue());
    }
}
