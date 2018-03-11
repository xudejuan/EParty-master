package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.BitmapUtil;
import com.example.liu.eparty.util.ConstantUtil;
import com.example.liu.eparty.util.FileUtil;
import com.example.liu.eparty.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;


public class AddReportActivity extends BaseActivity {

    @BindView(R.id.add_report_file_name)
    TextView file;
    @BindView(R.id.add_report_picture1)
    ImageView imageView1;
    @BindView(R.id.add_report_picture2)
    ImageView imageView2;
    @BindView(R.id.add_report_picture3)
    ImageView imageView3;
    @BindView(R.id.add_report_video)
    ImageView video;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.add_report_content)
    EditText content;

    private int taskId;
    private MaterialDialog materialDialog;
    private List<String> filePaths;
    private String filePath;
    private List<String> picturePaths;
    private String videoPath;
    private int state;

    @Override
    protected String setTitle() {
        return "提交报告";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_report;
    }

    @Override
    protected void init() {
//        taskId = getIntent().getIntExtra("taskId", 0);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);
        play.setVisibility(View.GONE);
    }

    @OnClick(R.id.add_report_file)
    public void chooseFile() {
        showWait();
    }

    private void showWait() {
        materialDialog = new MaterialDialog.Builder(this)
                .title("搜索文件")
                .content("正在搜索本地文件，请稍候")
                .cancelable(false)
                .widgetColor(getResources().getColor(R.color.blue_deep))
                .progress(true, 0, false)
                .show();
        findFile();
    }

    private void findFile() {
        new Thread() {
            @Override
            public void run() {
                filePaths = FileUtil.getSpecificTypeOfFile(AddReportActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        materialDialog.dismiss();
                        showFile();
                    }
                });
            }
        }.start();
    }

    private void showFile() {
        final List<String> fileName = new ArrayList<>();
        if (filePaths != null) {
            for (String s : filePaths) {
                fileName.add(FileUtil.getFileName(s));
            }
            new MaterialDialog.Builder(this)
                    .title("请选择文件")
                    .widgetColor(getResources().getColor(R.color.blue_deep))
                    .items(fileName)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            dialog.dismiss();
                            filePath = filePaths.get(position);
                            file.setText(fileName.get(position));
                        }
                    }).show();
        } else {
            new MaterialDialog.Builder(this)
                    .title("错误")
                    .content("没有找到任何文件")
                    .show();
        }
    }

    @OnClick({R.id.add_report_picture1, R.id.add_report_picture2, R.id.add_report_picture3})
    public void choosePicture() {
        state = 0;
        FilePickerBuilder.getInstance().setMaxCount(3)
                .setActivityTheme(R.style.MyTheme)
                .enableImagePicker(true)
                .enableVideoPicker(false)
                .pickPhoto(this);
    }

    @OnClick(R.id.add_report_video)
    public void chooseVideo() {
        state = 1;
        FilePickerBuilder.getInstance().setMaxCount(1)
                .setActivityTheme(R.style.MyTheme)
                .enableImagePicker(false)
                .enableVideoPicker(true)
                .pickPhoto(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            List<String> paths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
            if (state == 0) {
                switch (paths.size()) {
                    case 0:
                        imageView1.setImageResource(R.mipmap.add_picture);
                        imageView2.setVisibility(View.GONE);
                        imageView3.setVisibility(View.GONE);
                    case 1:
                        imageView1.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFd(paths.get(0), 60, 60));
                        imageView2.setVisibility(View.VISIBLE);
                        imageView2.setImageResource(R.mipmap.add_picture);
                        imageView3.setVisibility(View.GONE);
                        break;
                    case 2:
                        imageView1.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFd(paths.get(0), 60, 60));
                        imageView2.setVisibility(View.VISIBLE);
                        imageView2.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFd(paths.get(1), 60, 60));
                        imageView3.setVisibility(View.VISIBLE);
                        imageView3.setImageResource(R.mipmap.add_picture);
                        break;
                    case 3:
                        imageView1.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFd(paths.get(0), 60, 60));
                        imageView2.setVisibility(View.VISIBLE);
                        imageView2.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFd(paths.get(1), 60, 60));
                        imageView3.setVisibility(View.VISIBLE);
                        imageView3.setImageBitmap(BitmapUtil.decodeSampledBitmapFromFd(paths.get(2), 60, 60));
                        break;
                }
                picturePaths = paths;
            } else {
                videoPath = paths.get(0);
                //用视频的第一帧作为缩略图
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                try {
                    retriever.setDataSource(videoPath);
                    video.setImageBitmap(retriever.getFrameAtTime());
                    play.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    ToastUtil.show(this, "文件无效");
                }
            }
        }
    }

    @OnClick(R.id.add_report_submit)
    public void submit() {
        if (check()) {
            PostFormBuilder postFormBuilder = OkHttpUtils.post();
            File fileFile = new File(filePath);
            postFormBuilder.addFile("files", FileUtil.getFileName(filePath), fileFile);
            File videoFile = new File(videoPath);
            postFormBuilder.addFile("files", FileUtil.getFileName(videoPath), videoFile);
            for (int i = 0; i < picturePaths.size(); i++) {
                if (!TextUtils.isEmpty(picturePaths.get(i))) {
                    File pictureFile = new File(picturePaths.get(i));
                    postFormBuilder.addFile("files", FileUtil.getFileName(picturePaths.get(i)), pictureFile);
                }
            }
            Map<String, String> params = new HashMap<>();
            params.put("identify", ConstantUtil.IDENTIFY);
            params.put("token", token);
            params.put("tastId", String.valueOf(taskId));
            params.put("userId", String.valueOf(user.getUserId()));
            params.put("finishContent", content.getText().toString());
            postFormBuilder
                    .url(ConstantUtil.BASE_URL + "warning/ReferFinish")
                    .params(params)
                    .build()
                    .execute(new OperateCallback(this, "正在提交..."));
        }
    }

    private boolean check(){
        if (TextUtils.isEmpty(content.getText().toString().trim())){
            ToastUtil.show(this, "报告内容不能为空");
            return false;
        }
        if (TextUtils.isEmpty(file.getText().toString().trim())){
            ToastUtil.show(this, "请选择文件");
            return false;
        }
        if (TextUtils.isEmpty(picturePaths.get(0))){
            ToastUtil.show(this, "请选择至少一张图片");
            return false;
        }
        if (TextUtils.isEmpty(videoPath)){
            ToastUtil.show(this, "请选择视频");
            return false;
        }
        return true;
    }
}
