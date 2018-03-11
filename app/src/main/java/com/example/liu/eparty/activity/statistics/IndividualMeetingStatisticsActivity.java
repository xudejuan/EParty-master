package com.example.liu.eparty.activity.statistics;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.IndividualMeetingStatisticsAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.IndividualMeetingStatistics;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class IndividualMeetingStatisticsActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_individual_statistics_meeting)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_individual_statistics_meeting)
    RecyclerView recyclerView;

    private IndividualMeetingStatisticsAdapter adapter;
    private int meetingType;
    private String startTime;
    private String endTime;
    private int state;

    @Override
    protected String setTitle() {
        return "个人统计";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_individual_meeting_statistics;
    }

    @Override
    protected void init() {
        initParams();
        initRefreshLayout();
    }

    private void initParams() {
        switch (getIntent().getStringExtra("type")){
            case "党员大会":
                meetingType = 1;
                break;
            case "党代会":
                meetingType = 2;
                break;
            case "民主生活会":
                meetingType = 3;
                break;
            case "党课":
                meetingType = 4;
                break;
        }
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        String participate = getIntent().getStringExtra("participate");
        if (participate.equals("未参与")){
            state = 0;
        }else {
            state = 1;
        }
    }

    private void initRefreshLayout() {
        SmartRefreshLayoutUtil.init(smartRefreshLayout);
        SmartRefreshLayoutUtil.setOnRequestDataListener(new SmartRefreshLayoutUtil.OnRequestDataListener() {
            @Override
            public void requestDatas(int page) {
                requestData(page);
            }
        });
    }

    @Override
    protected void requestData(int page) {
        MyOkHttpUtil.get("meeting/getComment")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("meetingType", String.valueOf(meetingType))
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .addParams("state", String.valueOf(state))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new DataCallback<IndividualMeetingStatistics>(this){
                    @Override
                    public void showData(List<IndividualMeetingStatistics> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(List<IndividualMeetingStatistics> meetings) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (adapter == null) {
                    adapter = new IndividualMeetingStatisticsAdapter(this, meetings);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                }else {
                    adapter.refreshData(meetings);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                adapter.add(meetings);
                recyclerView.scrollToPosition(adapter.getData().size());
            }
        }
    }
}
