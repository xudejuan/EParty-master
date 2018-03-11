package com.example.liu.eparty.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.liu.eparty.MyApplication;
import com.example.liu.eparty.R;
import com.example.liu.eparty.activity.archive.ArchiveActivity;
import com.example.liu.eparty.activity.login.LoginActivity;
import com.example.liu.eparty.activity.meeting.MeetingActivity;
import com.example.liu.eparty.activity.mine.MineActivity;
import com.example.liu.eparty.activity.news.NewsActivity;
import com.example.liu.eparty.activity.organization.OrganizationActivity;
import com.example.liu.eparty.activity.statistics.StatisticsActivity;
import com.example.liu.eparty.activity.task.TaskActivity;
import com.example.liu.eparty.bean.News;
import com.example.liu.eparty.bean.Update;
import com.example.liu.eparty.bean.Warning;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.FileUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.PermissionUtil;
import com.example.liu.eparty.util.ThrowableUtil;
import com.example.liu.eparty.util.ToastUtil;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MainActivity extends AutoLayoutActivity
        implements PermissionUtil.OnRequestPermissionsResultCallbacks {

    @BindView(R.id.slider_layout_main)
    SliderLayout sliderLayout;

    private String appVersion;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        checkUpdate();
        needToLogin();
        PermissionUtil.requestPermissions(this, 0,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA);
    }

    private void needToLogin() {
        if (MyApplication.getInstance().getToken().equals("")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            requestNews();
        }
    }

    private void requestNews() {
        MyOkHttpUtil.get("News/listUI")
                .addParams("", "")
                .build()
                .execute(new DataCallback<News>(this) {
                    @Override
                    public void showData(List<News> mDatas) {
                        initSlider(mDatas);
                    }
                });
    }

    private void initSlider(final List<News> newsList) {
        for (int i = 0; i < newsList.size(); i++) {
            final News news = newsList.get(i);
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(news.getNewTitle());
            textSliderView.image(news.getImgs());
            textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    startActivity(new Intent(MainActivity.this, NewsActivity.class)
                            .putExtra("newsLink", news.getNewHref()));
                }
            });
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setDuration(3000);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
    }

    @OnClick(R.id.frameLayout_organization)
    public void organization() {
        startActivity(new Intent(this, OrganizationActivity.class));
    }

    @OnClick(R.id.frameLayout_task)
    public void task() {
        startActivity(new Intent(this, TaskActivity.class));
    }

    @OnClick(R.id.frameLayout_meeting)
    public void meeting() {
        startActivity(new Intent(this, MeetingActivity.class));
    }

    @OnClick(R.id.frameLayout_statistics)
    public void statistics() {
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    @OnClick(R.id.frameLayout_archive)
    public void archive() {
        startActivity(new Intent(this, ArchiveActivity.class));
    }

    @OnClick(R.id.frameLayout_mine)
    public void mine() {
        startActivity(new Intent(this, MineActivity.class));
    }

    public void checkUpdate() {
        initAppVersion();
        check();
    }

    private void initAppVersion() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appVersion = packageInfo != null ? packageInfo.versionName : "";
    }

    public void check() {
        MyOkHttpUtil.get("")
                .addParams("appVersion", appVersion)
                .build()
                .execute(new DataCallback<Update>(this) {
                    @Override
                    public void showData(List<Update> mDatas) {
                        Update update = mDatas.get(0);
                        if (!update.getAppVersion().equals(appVersion)) {
                            showUpdateDialog(update.getAppName(), update.getAppSize(),
                                    update.getAppVersion(), update.getUpdateInfo(),
                                    update.getUpdateUrl());
                        } else {
                            startPull();
                        }
                    }
                });
    }

    //强制更新
    private void showUpdateDialog(String appName, String appSize, String appVersion,
                                  String updateInfo, final String updateUrl) {
        new MaterialDialog.Builder(this)
                .title("更新")
                .content(appName + appVersion + "新特性" + "\n" + updateInfo + "更新包大小：" + appSize + "MB")
                .cancelable(false)
                .positiveText("立即更新")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        download(updateUrl);
                    }
                }).show();
    }

    private void download(String updateUrl) {
        final MaterialDialog updateDialog = new MaterialDialog.Builder(this)
                .title("更新")
                .content("正在下载更新包，请稍候")
                .widgetColor(Color.RED)
                .progress(true, 100, false)
                .cancelable(false)
                .show();
        OkHttpUtils
                .get()
                .url(updateUrl)
                .build()
                .execute(new FileCallBack(FileUtil.getSavePath(this) + "/",
                        System.currentTimeMillis() + FileUtil.getFileName(updateUrl)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        updateDialog.dismiss();
                        ToastUtil.show(MainActivity.this, ThrowableUtil.getThrowableMessage(e));
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        updateDialog.dismiss();
                        installApk(response);
                    }
                });
    }

    public void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    //轮询检查是否有新的预警
    private void startPull() {
        final int userId = MyApplication.getInstance().getUser().getUserId();
        final int treeId = MyApplication.getInstance().getUser().getTreeId();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MyOkHttpUtil.get("")
                        .addParams("userId", String.valueOf(userId))
                        .addParams("treeId", String.valueOf(treeId))
                        .build()
                        .execute(new DataCallback<Warning>(MainActivity.this) {
                            @Override
                            public void showData(List<Warning> mDatas) {
                                showWarningDialog(mDatas.get(0));
                            }
                        });
            }
        }, 0, 60 * 1000);
    }

    private void showWarningDialog(final Warning warning) {
        new MaterialDialog.Builder(this)
                .title("第" + warning.getTimes() + "次预警")
                .content("任务：" + warning.getTaskName())
                .input("请输入处理方式", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                    }
                })
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (dialog.getInputEditText().getText().toString().trim().length() == 0) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        }
                        handle(warning.getId(), dialog.getInputEditText().getText().toString());
                    }
                })
                .show();
    }

    private void handle(int warningId, String way) {
        MyOkHttpUtil.post("")
                .addParams("userId", String.valueOf(MyApplication.getInstance().getUser().getUserId()))
                .addParams("warningId", String.valueOf(warningId))
                .addParams("handleWay", way)
                .build()
                .execute(new OperateCallback(this, "提交中...") {
                    @Override
                    public void onCompleted() {
                        timer.cancel();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms, boolean isAllGranted) {
        if (!isAllGranted) {
            ToastUtil.show(this, "必须同意所有权限才能正常使用");
            finish();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms, boolean isAllDenied) {
        if (isAllDenied) {
            ToastUtil.show(this, "必须同意所有权限才能正常使用");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        sliderLayout.stopAutoCycle();
    }
}
