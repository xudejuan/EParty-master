package com.example.liu.eparty.activity.meeting;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.activity.task.ChooseMemberActivity;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.ConvertUtil;
import com.example.liu.eparty.util.DateUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddMeetingActivity extends BaseActivity {

    @BindView(R.id.add_meeting_title)
    EditText title;
    @BindView(R.id.add_meeting_meeting_start_time)
    TextView meetingStartTime;
    @BindView(R.id.add_meeting_meeting_end_time)
    TextView meetingEndTime;
    @BindView(R.id.add_meeting_sign_in_start_time)
    TextView signInStartTime;
    @BindView(R.id.add_meeting_sign_in_end_time)
    TextView signInEndTime;
    @BindView(R.id.add_meeting_member_name)
    TextView member;
    @BindView(R.id.add_meeting_area)
    TextView area;
    @BindView(R.id.add_meeting_detail_address)
    TextView address;
    @BindView(R.id.add_meeting_content)
    EditText content;

    private List<Integer> memberIds = new ArrayList<>();
    private List<String> memberNames = new ArrayList<>();
    private double longitude;
    private double latitude;

    @Override
    protected String setTitle() {
        return "新增会议";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_meeting;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.add_meeting_member)
    public void chooseMember(){
        startActivityForResult(new Intent(this, ChooseMemberActivity.class), 0);
    }

    @OnClick(R.id.add_meeting_choose_area)
    public void chooseArea(){
        startActivityForResult(new Intent(this, ChoosePlaceActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK){
            memberIds = data.getIntegerArrayListExtra("memberIds");
            memberNames = data.getStringArrayListExtra("memberNames");
            member.setText(ConvertUtil.listToString(memberNames));
        }else if (requestCode == 1 && resultCode == RESULT_OK){
            longitude = data.getDoubleExtra("longitude", 0);
            latitude = data.getDoubleExtra("latitude", 0);
            area.setText(data.getStringExtra("area"));
        }
    }

    @OnClick(R.id.add_meeting_issue)
    public void issue(){
        if (check()){
            MyOkHttpUtil.post("")
                    .addParams("userId", String.valueOf(user.getUserId()))
                    .addParams("title", title.getText().toString())
                    .addParams("meetingStartTime", meetingStartTime.getText().toString())
                    .addParams("meetingEndTime", meetingEndTime.getText().toString())
                    .addParams("signInStartTime", signInStartTime.getText().toString())
                    .addParams("signInEndTime", signInEndTime.getText().toString())
                    .addParams("member", member.getText().toString())
                    .addParams("area", area.getText().toString())
                    .addParams("longitude", String.valueOf(longitude))
                    .addParams("latitude", String.valueOf(latitude))
                    .addParams("address", address.getText().toString())
                    .addParams("content", content.getText().toString())
                    .build()
                    .execute(new OperateCallback(this, "正在下达..."){
                        @Override
                        public void onCompleted() {
                            finish();
                        }
                    });
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(title.getText().toString())){
            ToastUtil.show(this, "标题不能为空");
            return false;
        }
        if (!DateUtil.isDateTimeFormat(meetingStartTime.getText().toString())){
            ToastUtil.show(this, "会议开始时间不完整");
            return false;
        }
        if (!DateUtil.isDateTimeFormat(meetingEndTime.getText().toString())){
            ToastUtil.show(this, "会议结束时间不完整");
            return false;
        }
        if (!DateUtil.isDateTimeFormat(signInStartTime.getText().toString())){
            ToastUtil.show(this, "签到开始时间不完整");
            return false;
        }
        if (!DateUtil.isDateTimeFormat(signInEndTime.getText().toString())){
            ToastUtil.show(this, "签到结束时间不完整");
            return false;
        }
        if (TextUtils.isEmpty(member.getText().toString())){
            ToastUtil.show(this, "参与人员不能为空");
            return false;
        }
        if (TextUtils.isEmpty(area.getText().toString())){
            ToastUtil.show(this, "所在地区不能为空");
            return false;
        }
        if (TextUtils.isEmpty(address.getText().toString())){
            ToastUtil.show(this, "详细地址不能为空");
            return false;
        }
        if (TextUtils.isEmpty(content.getText().toString())){
            ToastUtil.show(this, "会议内容不能为空");
            return false;
        }
        return true;
    }
}
