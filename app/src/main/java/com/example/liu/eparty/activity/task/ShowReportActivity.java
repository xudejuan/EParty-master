package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Report;
import com.example.liu.eparty.callback.FileDownloadCallback;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.ConstantUtil;
import com.example.liu.eparty.util.FileUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowReportActivity extends BaseActivity {

    @BindView(R.id.show_report_create_time)
    TextView createTime;
    @BindView(R.id.show_report_file_name)
    TextView fileName;
    @BindView(R.id.show_report_picture1)
    ImageView picture1;
    @BindView(R.id.show_report_picture2)
    ImageView picture2;
    @BindView(R.id.show_report_picture3)
    ImageView picture3;
    @BindView(R.id.show_report_video)
    ImageView video;
    @BindView(R.id.show_report_content)
    TextView content;
    @BindView(R.id.show_report_suggestion)
    TextView suggestion;
    @BindView(R.id.cl_show_report)
    TextView showCheck;
    @BindView(R.id.show_report_resubmit)
    TextView resubmit;

    private Report report;
    private int taskId;
    private String videoPath;
    private String fileUrl;
    private String[] pictureUrl;

    @Override
    protected String setTitle() {
        return "报告详情";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_report;
    }

    @Override
    protected void init() {
        taskId = getIntent().getIntExtra("taskId", 0);
        report = (Report) getIntent().getSerializableExtra("report");
        createTime.setText(report.getFinishTime());
        content.setText(report.getFinishOpinion());
        if (report.getFinishReadOk().equals("null")) {
            resubmit.setVisibility(View.VISIBLE);
        } else {
            if (report.getFinishReadOk().equals("1")) {
                showCheck.setVisibility(View.VISIBLE);
            } else if (report.getFinishReadOk().equals("0")) {
                resubmit.setVisibility(View.VISIBLE);
                showCheck.setVisibility(View.VISIBLE);
            }
        }
        List<String> url = report.getUrl();
        List<String> urlName = report.getUrlName();
        pictureUrl = new String[url.size() - 2];
        int j = 0;
        for (int i = 0; i < url.size(); i++) {
            if (url.get(i).endsWith(".mp4")) {
                showVideo(url.get(i), urlName.get(i));
            } else if (url.get(i).endsWith(".jpg") || url.get(i).endsWith(".png") || url.get(i).endsWith(".jpeg")) {
                pictureUrl[j++] = url.get(i);
                downloadPicture(url.get(i), j);
            } else {
                fileUrl = url.get(i);
                fileName.setText(urlName.get(i));
            }
        }
    }

    private void showVideo(String url, final String name) {
        url = url.substring(url.lastIndexOf("/") + 1, url.length());
        if (!FileUtil.isExist(this, "showReportVideo" + name)) {
            MyOkHttpUtil.get(ConstantUtil.FILE_URL + url)
                    .build()
                    .execute(new FileDownloadCallback(FileUtil.getSavePath(this),
                            "showReportVideo" + name, this) {
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
            videoPath = FileUtil.getSavePath(this) + "/" + "showReportVideo" + name;
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


    @OnClick(R.id.show_report_look_file)
    public void downloadFile() {
        fileUrl = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length());
        if (!FileUtil.isExist(this, "showReportFile" + report.getFinishId() + fileName.getText().toString())) {
            MyOkHttpUtil.post("")
                    .addParams("fileUrl", fileUrl)
                    .build()
                    .execute(new FileDownloadCallback(FileUtil.getSavePath(this),
                            "showReportFile" + report.getFinishId() + fileName.getText().toString(), this) {
                        @Override
                        public void onResponse(File response, int id) {
                            super.onResponse(response, id);
                            FileUtil.show(ShowReportActivity.this, response.getAbsolutePath());
                        }
                    });
        }else {
            FileUtil.show(ShowReportActivity.this, FileUtil.getSavePath(this)
                    + "/" + "showReportFile" + report.getFinishId() + fileName.getText().toString());
        }
    }

    @OnClick(R.id.show_report_picture1)
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

    @OnClick(R.id.show_report_picture2)
    public void previewPicture2() {
        if (picture2.getDrawable().getCurrent().getConstantState() ==
                getResources().getDrawable(R.mipmap.null_picture).getConstantState()) {
            Picasso.with(this).load(pictureUrl[1]).placeholder(R.mipmap.null_picture)
                    .error(R.mipmap.null_picture).into(picture1);
        } else {
            picture2.setDrawingCacheEnabled(true);
            Bitmap bitmap = picture2.getDrawingCache();
            startActivity(new Intent(this, PreviewPhotoActivity.class)
                    .putExtra("bitmap", bitmap));
            picture2.setDrawingCacheEnabled(false);
        }
    }

    @OnClick(R.id.show_report_picture3)
    public void previewPicture3() {
        if (picture3.getDrawable().getCurrent().getConstantState() ==
                getResources().getDrawable(R.mipmap.null_picture).getConstantState()) {
            Picasso.with(this).load(pictureUrl[2]).placeholder(R.mipmap.null_picture)
                    .error(R.mipmap.null_picture).into(picture1);
        } else {
            picture3.setDrawingCacheEnabled(true);
            Bitmap bitmap = picture3.getDrawingCache();
            startActivity(new Intent(this, PreviewPhotoActivity.class)
                    .putExtra("bitmap", bitmap));
            picture3.setDrawingCacheEnabled(false);
        }
    }

    @OnClick(R.id.show_report_video)
    public void previewVideo() {
        if (videoPath != null) {
            startActivity(new Intent(this, PreviewVideoActivity.class).putExtra("videoPath", videoPath));
        }
    }

    @OnClick(R.id.show_report_resubmit)
    public void resubmit(){
        MyOkHttpUtil.post("")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("taskId", String.valueOf(taskId))
                .build()
                .execute(new OperateCallback(this, "正在删除..."){
                    @Override
                    public void onCompleted() {
                        startActivity(new Intent(ShowReportActivity.this,
                                AddReportActivity.class).putExtra("taskId", taskId));
                    }
                });
    }
}
