package com.example.liu.eparty.activity.meeting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.ConstantUtil;
import com.example.liu.eparty.util.FileUtil;
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

public class UploadRecordActivity extends BaseActivity {

    @BindView(R.id.upload_record_picture)
    ImageView picture;
    @BindView(R.id.upload_record_file_name)
    TextView file;

    private Meeting meeting;
    private List<String> filePaths;
    private String picturePath;
    private String filePath;
    private MaterialDialog materialDialog;

    @Override
    protected String setTitle() {
        return "上传记录";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_upload_record;
    }

    @Override
    protected void init() {
        meeting = (Meeting) ACache.get(this, "meeting").getAsObject("meeting");
    }

    @OnClick(R.id.upload_record_submit)
    public void upload() {
        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        File file = new File(filePath);
        postFormBuilder.addFile("files", FileUtil.getFileName(filePath), file);
        file = new File(picturePath);
        postFormBuilder.addFile("imgs", FileUtil.getFileName(picturePath), file);
        Map<String, String> params = new HashMap<>();
        params.put("identify", ConstantUtil.IDENTIFY);
        params.put("token", token);
        params.put("meetingId", String.valueOf(meeting.getId()));
        params.put("userId", String.valueOf(user.getUserId()));
        postFormBuilder
                .url("warning/ReferFinish")
                .params(params)
                .build()
                .execute(new OperateCallback(this, "正在提交..."));
    }

    @OnClick(R.id.upload_record_picture)
    public void uploadPicture() {
        FilePickerBuilder.getInstance().setMaxCount(1)
                .setActivityTheme(R.style.MyTheme)
                .pickPhoto(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
            List<String> paths = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA);
            picturePath = paths.get(0);
            picture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    @OnClick(R.id.upload_record_file)
    public void uploadFile() {
        showWait();
    }

    private void showWait() {
        materialDialog = new MaterialDialog.Builder(this)
                .widgetColor(getResources().getColor(R.color.blue_deep))
                .title("搜索文件")
                .content("正在搜索本地文件，请稍候")
                .cancelable(false)
                .progress(true, 0, false)
                .show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
        findFile();
    }

    private void findFile() {
        new Thread(){
            @Override
            public void run() {
                filePaths = FileUtil.getSpecificTypeOfFile(UploadRecordActivity.this);
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
}
