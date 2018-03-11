package com.example.liu.eparty.activity.statistics;

import android.content.Intent;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;

import butterknife.OnClick;

public class StatisticsActivity extends BaseActivity {

    @Override
    protected String setTitle() {
        return "统计";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_statistics;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.rl_statistics_individual)
    public void showIndividualStatistics(){
        startActivity(new Intent(this, MeetingIndividualStatisticsActivity.class));
    }

    @OnClick(R.id.rl_statistics_organization1)
    public void showMeetingOrganizationStatistics(){
//        if (user.getStattus().equals("管理员")) {
            startActivity(new Intent(this, MeetingOrganizationStatisticsActivity.class));
//        }else {
//            ToastUtil.show(this, "无权查看");
//        }
    }

    @OnClick(R.id.rl_statistics_organization2)
    public void showTaskOrganizationStatistics(){
//        if (user.getStattus().equals("管理员")) {
            startActivity(new Intent(this, TaskOrganizationStatisticsActivity.class));
//        }else {
//            ToastUtil.show(this, "无权查看");
//        }
    }
}
