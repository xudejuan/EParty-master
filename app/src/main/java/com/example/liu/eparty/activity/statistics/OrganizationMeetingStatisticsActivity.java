package com.example.liu.eparty.activity.statistics;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.OrganizationMeetingStatisticsAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class OrganizationMeetingStatisticsActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_organization_statistics_meeting)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_organization_statistics_meeting)
    RecyclerView recyclerView;

    private OrganizationMeetingStatisticsAdapter adapter;
    private int meetingType;
    private String startTime;
    private String endTime;
    private int organizationId;

    @Override
    protected String setTitle() {
        return "组织统计";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_organization_meeting_statistics;
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
        organizationId = getIntent().getIntExtra("organizationId", 0);
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
                .addParams("organizationId", String.valueOf(organizationId))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new DataCallback<Meeting>(this){
                    @Override
                    public void showData(List<Meeting> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(final List<Meeting> meetings) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (adapter == null) {
                    adapter = new OrganizationMeetingStatisticsAdapter(this, meetings);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(OrganizationMeetingStatisticsActivity.this,
                                    OrganizationParticipateStatisticsActivity.class)
                                    .putExtra("meetingId", meetings.get(position).getId()));
                        }
                    }));
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
