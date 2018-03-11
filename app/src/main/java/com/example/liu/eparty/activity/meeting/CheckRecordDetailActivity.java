package com.example.liu.eparty.activity.meeting;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.bean.Record;
import com.example.liu.eparty.callback.FileDownloadCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.FileUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckRecordDetailActivity extends BaseActivity {

    @BindView(R.id.check_record_detail_file_name)
    TextView file;
    @BindView(R.id.check_record_detail_picture)
    ImageView picture;

    private String fileUrl;
    private String fileName;
    private int meetingId;

    @Override
    protected String setTitle() {
        return "会议记录";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_check_record_detail;
    }

    @Override
    protected void init() {
        Record record = (Record) getIntent().getSerializableExtra("record");
        meetingId = ((Meeting) ACache.get(this, "meeting").getAsObject("meeting")).getId();
        fileUrl = record.getFile();
        fileName = FileUtil.getFileName(fileUrl);
        file.setText(fileName);
        String pictureUrl = record.getImg();
        Picasso.with(this).load(pictureUrl).placeholder(R.mipmap.null_picture)
                .error(R.mipmap.null_picture).into(picture);
    }

    @OnClick(R.id.check_record_detail_file)
    public void downloadFile(){
        if (!FileUtil.isExist(this, "checkRecordDetailFile" + meetingId + fileName)) {
            MyOkHttpUtil.post("")
                    .addParams("fileUrl", fileUrl)
                    .build()
                    .execute(new FileDownloadCallback(FileUtil.getSavePath(this),
                            "checkRecordDetailFile" + meetingId + fileName, this) {
                        @Override
                        public void onResponse(File response, int id) {
                            super.onResponse(response, id);
                            FileUtil.show(CheckRecordDetailActivity.this, response.getAbsolutePath());
                        }
                    });
        }else {
            FileUtil.show(CheckRecordDetailActivity.this, FileUtil.getSavePath(this)
                    + "/" + "checkRecordDetailFile" + meetingId + fileName);
        }
    }
}
