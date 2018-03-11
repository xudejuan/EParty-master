package com.example.liu.eparty.activity.meeting;

import android.content.Intent;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.activity.mine.MemberActivity;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.util.ACache;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowMeetingDetailActivity extends BaseActivity {

    @BindView(R.id.show_meeting_detail_title)
    TextView title;
    @BindView(R.id.show_meeting_detail_start_time)
    TextView startTime;
    @BindView(R.id.show_meeting_detail_end_time)
    TextView endTime;
    @BindView(R.id.show_meeting_detail_sign_in_start_time)
    TextView signInStartTime;
    @BindView(R.id.show_meeting_detail_sign_in_end_time)
    TextView signInEndTime;
    @BindView(R.id.show_meeting_detail_count)
    TextView count;
    @BindView(R.id.show_meeting_detail_address)
    TextView address;
    @BindView(R.id.show_meeting_detail_content)
    TextView content;

    @Override
    protected String setTitle() {
        return "会议详情";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_meeting_detail;
    }

    @Override
    protected void init() {
        Meeting meeting = (Meeting) ACache.get(this, "meeting").getAsObject("meeting");
        title.setText(meeting.getMeetingTitle());
        startTime.setText(meeting.getAStartTime());
        endTime.setText(meeting.getAEndTime());
        signInStartTime.setText(meeting.getASignStartTime());
        signInEndTime.setText(meeting.getASignEndTime());
        count.setText(String.valueOf(meeting.getArriveNumber()));
        address.setText(meeting.getAddress());
        content.setText(meeting.getMeetingContent());
    }

    @OnClick(R.id.show_meeting_detail_participate)
    public void showParticipate(){
        startActivity(new Intent(this, MemberActivity.class));
    }

    @OnClick(R.id.show_meeting_detail_look_address)
    public void showAddress(){
        new MaterialDialog.Builder(this)
                .title("会议地址")
                .content(address.getText().toString())
                .show();
    }

    @OnClick(R.id.show_meeting_detail_look_content)
    public void showContent(){
        new MaterialDialog.Builder(this)
                .title("大会内容")
                .content(content.getText().toString())
                .show();
    }
}
