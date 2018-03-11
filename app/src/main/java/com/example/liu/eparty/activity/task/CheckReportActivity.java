package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Report;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.callback.FileDownloadCallback;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.ConstantUtil;
import com.example.liu.eparty.util.FileUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckReportActivity extends BaseActivity {

    @BindView(R.id.check_report_detail_create_time)
    TextView time;
    @BindView(R.id.check_report_detail_file_name)
    TextView fileName;
    @BindView(R.id.check_report_detail_picture1)
    ImageView picture1;
    @BindView(R.id.check_report_detail_picture2)
    ImageView picture2;
    @BindView(R.id.check_report_detail_picture3)
    ImageView picture3;
    @BindView(R.id.check_report_detail_video)
    ImageView video;
    @BindView(R.id.check_report_detail_content)
    TextView content;
    @BindView(R.id.check_report_detail_suggestion)
    TextView suggestion;
    @BindView(R.id.ll_check_report)
    LinearLayout showPass;
    @BindView(R.id.cl_check_report)
    ConstraintLayout showCheck;

    private String taskId;
    private String recipientId;
    private Report report;
    private String videoPath;
    private String fileUrl;
    private String[] pictureUrl;

    @Override
    protected String setTitle() {
        return "报告详情";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_check_report;
    }

    @Override
    protected void init() {
        taskId = getIntent().getStringExtra("taskId");
        recipientId = getIntent().getStringExtra("recipientId");
    }

    @Override
    protected void requestData(int page) {
        MyOkHttpUtil.get("")
                .addParams("taskId", taskId)
                .addParams("recipientId", recipientId)
                .build()
                .execute(new DataCallback<Report>(this){
                    @Override
                    public void showData(List<Report> mDatas) {
                        initReport(mDatas.get(0));
                    }
                });
    }

    private void initReport(Report report){
        this.report = report;
        time.setText(report.getFinishTime());
        content.setText(report.getFinishOpinion());
        if (!report.getFinishReadOk().equals("null")) {
            showPass.setVisibility(View.VISIBLE);
        }else {
            showCheck.setVisibility(View.VISIBLE);
        }
        List<String> url = report.getUrl();
        List<String> urlName = report.getUrlName();
        if (url != null && url.size() > 0) {
            pictureUrl = new String[url.size() - 2];
            int j = 0;
            for (int i = 0; i < url.size(); i++) {
                if (url.get(i).endsWith(".mp4")) {
                    showVideo(url.get(i), urlName.get(i));
                } else if (url.get(i).endsWith(".jpg") || url.get(i).endsWith(".png")) {
                    pictureUrl[j++] = url.get(i);
                    downloadPicture(url.get(i), j);
                } else {
                    fileUrl = url.get(i);
                    fileName.setText(urlName.get(i));
                }
            }
        }
    }

    private void showVideo(String url, final String name) {
        url = url.substring(url.lastIndexOf("/") + 1, url.length());
        if (!FileUtil.isExist(this, "checkReportDetailVideo" + name)) {
            MyOkHttpUtil.get(ConstantUtil.FILE_URL + url)
                    .build()
                    .execute(new FileDownloadCallback(FileUtil.getSavePath(this),
                            "checkReportDetailVideo" + name, this) {
                        @Override
                        public void onResponse(File response, int id) {
                            super.onResponse(response, id);
                            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                            videoPath = response.getAbsolutePath();
                            retriever.setDataSource(videoPath);
                            video.setImageBitmap(retriever.getFrameAtTime());
                        }
                    });
        }else {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            videoPath = FileUtil.getSavePath(this) + "/" + "checkReportDetailVideo" + name;
            retriever.setDataSource(videoPath);
            video.setImageBitmap(retriever.getFrameAtTime());
        }
    }

    private void downloadPicture(String url, final int i) {
        String s = ConstantUtil.FILE_URL + url.substring(url.lastIndexOf("/") + 1, url.length());
        switch (i) {
            case 1:
                Picasso.with(this).load(s).placeholder(R.mipmap.null_picture)
                        .error(R.mipmap.null_picture).into(picture1);
                picture2.setVisibility(View.GONE);
                picture3.setVisibility(View.GONE);
                break;
            case 2:
                Picasso.with(this).load(s).placeholder(R.mipmap.null_picture)
                        .error(R.mipmap.null_picture).into(picture2);
                picture2.setVisibility(View.VISIBLE);
                picture3.setVisibility(View.GONE);
                break;
            case 3:
                Picasso.with(this).load(s).placeholder(R.mipmap.null_picture)
                        .error(R.mipmap.null_picture).into(picture3);
                picture2.setVisibility(View.VISIBLE);
                picture3.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.check_report_detail_look_file)
    public void downloadFile() {
        fileUrl = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
        if (!FileUtil.isExist(this, "checkReportDetailFile" + report.getFinishId() + fileName.getText().toString())) {
            MyOkHttpUtil.post("")
                    .addParams("fileUrl", fileUrl)
                    .build()
                    .execute(new FileDownloadCallback(FileUtil.getSavePath(this),
                            "checkReportDetailFile" + report.getFinishId() + fileName.getText().toString(), this) {
                        @Override
                        public void onResponse(File response, int id) {
                            super.onResponse(response, id);
                            FileUtil.show(CheckReportActivity.this, response.getAbsolutePath());
                        }
                    });
        }else {
            FileUtil.show(CheckReportActivity.this, FileUtil.getSavePath(this)
                    + "/" + "checkReportDetailFile" + report.getFinishId() + fileName.getText().toString());
        }
    }

    @OnClick({R.id.check_report_detail_pass, R.id.check_report_detail_unpass})
    public void check(final View view) {
        new MaterialDialog.Builder(this)
                .title("批改建议")
                .widgetColor(Color.RED)
                .input("请填写批改建议", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE){
                            if (dialog.getInputEditText() != null){
                                if (view.getId() == R.id.check_plan_detail_pass) {
                                    pass(dialog.getInputEditText().getText().toString());
                                }else if (view.getId() == R.id.check_plan_detail_unpass){
                                    unpass(dialog.getInputEditText().getText().toString());
                                }
                            }else {
                                ToastUtil.show(CheckReportActivity.this, "批改建议不能为空不能为空");
                            }
                        }
                        dialog.dismiss();
                    }
                });
    }

    public void unpass(String suggestion){
        MyOkHttpUtil.post("")
                .addParams("receiveUserId", String.valueOf(user.getUserId()))
                .addParams("readOk", "0")
                .addParams("checkOpinion", suggestion)
                .build()
                .execute(new OperateCallback(this, "正在提交..."){
                    @Override
                    public void onCompleted() {
                        setResult(RESULT_OK);
                        finish();
                    }
                });

    }

    private void pass(String suggestion){
        MyOkHttpUtil.post("")
                .addParams("receiveUserId", String.valueOf(user.getUserId()))
                .addParams("readOk", "1")
                .addParams("checkOpinion", suggestion)
                .build()
                .execute(new OperateCallback(this, "正在提交..."){
                    @Override
                    public void onCompleted() {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    @OnClick(R.id.check_report_detail_picture1)
    public void previewPicture1() {
        if (picture1.getDrawable().getCurrent().getConstantState() ==
                getResources().getDrawable(R.mipmap.null_picture).getConstantState()) {
            Picasso.with(this).load(pictureUrl[0]).placeholder(R.mipmap.null_picture)
                    .error(R.mipmap.null_picture).into(picture1);
        } else {
            picture1.setDrawingCacheEnabled(true);
            Bitmap bitmap = picture1.getDrawingCache();
            startActivity(new Intent(this, PreviewPhotoActivity.class)
                    .putExtra("bitmap", bitmap));
            picture1.setDrawingCacheEnabled(false);
        }
    }

    @OnClick(R.id.check_report_detail_picture2)
    public void previewPicture2() {
        if (picture2.getDrawable().getCurrent().getConstantState() ==
                getResources().getDrawable(R.mipmap.null_picture).getConstantState()) {
            Picasso.with(this).load(pictureUrl[1]).placeholder(R.mipmap.null_picture)
                    .error(R.mipmap.null_picture).into(picture2);
        } else {
            picture2.setDrawingCacheEnabled(true);
            Bitmap bitmap = picture2.getDrawingCache();
            startActivity(new Intent(this, PreviewPhotoActivity.class)
                    .putExtra("bitmap", bitmap));
            picture2.setDrawingCacheEnabled(false);
        }
    }

    @OnClick(R.id.check_report_detail_picture3)
    public void previewPicture3() {
        if (picture3.getDrawable().getCurrent().getConstantState() ==
                getResources().getDrawable(R.mipmap.null_picture).getConstantState()) {
            Picasso.with(this).load(pictureUrl[2]).placeholder(R.mipmap.null_picture)
                    .error(R.mipmap.null_picture).into(picture3);
        } else {
            picture3.setDrawingCacheEnabled(true);
            Bitmap bitmap = picture3.getDrawingCache();
            startActivity(new Intent(this, PreviewPhotoActivity.class)
                    .putExtra("bitmap", bitmap));
            picture3.setDrawingCacheEnabled(false);
        }
    }

    @OnClick(R.id.check_report_detail_video)
    public void previewVideo1() {
        startActivity(new Intent(this, PreviewVideoActivity.class)
                .putExtra("videoPath", videoPath));
    }

    @OnClick(R.id.check_report_detail_content_detail)
    public void showContent() {
        new MaterialDialog.Builder(this)
                .title("报告内容")
                .content(content.getText().toString())
                .show();
    }
}
