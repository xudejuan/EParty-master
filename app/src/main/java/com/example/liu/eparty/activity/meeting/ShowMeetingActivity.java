package com.example.liu.eparty.activity.meeting;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.MeetingAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class ShowMeetingActivity extends BaseActivity {

    @BindView(R.id.tabLayout_show_meeting)
    TabLayout tabLayout;
    @BindView(R.id.refreshLayout_show_meeting)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_show_meeting)
    RecyclerView recyclerView;

    private MeetingAdapter meetingAdapter;
    private int position = 0;

    @Override
    protected String setTitle() {
        return "会议";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_meeting;
    }

    @Override
    protected void init() {
        initTabLayout();
        initRefreshLayout();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("党员大会"));
        tabLayout.addTab(tabLayout.newTab().setText("党代会"));
        tabLayout.addTab(tabLayout.newTab().setText("民主生活会"));
        tabLayout.addTab(tabLayout.newTab().setText("党课"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                initRefreshLayout();
                requestData(1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        MyOkHttpUtil.get("")
                .addParams("type", String.valueOf(position))
                .addParams("position", String.valueOf(page))
                .build()
                .execute(new DataCallback<Meeting>(this) {
                    @Override
                    public void showData(List<Meeting> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(final List<Meeting> mDatas) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (meetingAdapter == null) {
                    meetingAdapter = new MeetingAdapter(this, mDatas);
                    recyclerView.setAdapter(meetingAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            ACache.get(ShowMeetingActivity.this, "meeting").put("meeting", mDatas.get(position));
                            startActivity(new Intent(ShowMeetingActivity.this, MeetingOperateActivity.class));
                        }
                    }));
                } else {
                    meetingAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                meetingAdapter.add(mDatas);
                recyclerView.scrollToPosition(meetingAdapter.getData().size());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ACache.get(this, "meeting").clear();
    }
}
