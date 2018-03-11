package com.example.liu.eparty.activity.meeting;

import android.widget.ImageView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

public class QrcodeActivity extends BaseActivity {

    @BindView(R.id.sign_in_qrcode)
    ImageView imageView;

    private Meeting meeting;

    @Override
    protected String setTitle() {
        return "二维码";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void init() {
        meeting = (Meeting) ACache.get(this, "meeting").getAsObject("meeting");
    }

    @Override
    protected void requestData(int page) {
        MyOkHttpUtil.post("")
                .addParams("meetingId", String.valueOf(meeting.getId()))
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("treeId", String.valueOf(user.getTreeId()))
                .build()
                .execute(new DataCallback<String>(this){
                    @Override
                    public void showData(List<String> mDatas) {
                        initQrcode(mDatas.get(0));
                    }
                });
    }

    private void initQrcode(String url) {
        Picasso.with(this).load(url).placeholder(R.mipmap.null_picture).
                error(R.mipmap.null_picture).into(imageView);
    }
}
