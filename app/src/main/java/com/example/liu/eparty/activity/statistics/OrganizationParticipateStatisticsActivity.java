package com.example.liu.eparty.activity.statistics;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.OrganizationParticipateStatisticsAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.OrganizationParticipateStatistics;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class OrganizationParticipateStatisticsActivity extends BaseActivity {

    @BindView(R.id.tabLayout_op_statistics)
    TabLayout tabLayout;
    @BindView(R.id.refreshLayout_op_statistics)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_op_statistics)
    RecyclerView recyclerView;

    private OrganizationParticipateStatisticsAdapter adapter;
    private int position = 0;
    private int meetingId;

    @Override
    protected String setTitle() {
        return "组织统计";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_organization_participate_statistics;
    }

    @Override
    protected void init() {
        meetingId = getIntent().getIntExtra("meetingId", 0);
        initTabLayout();
        initRefreshLayout();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("未参与"));
        tabLayout.addTab(tabLayout.newTab().setText("已参与"));
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
                .addParams("meetingId", String.valueOf(meetingId))
                .addParams("type", String.valueOf(position))
                .addParams("position", String.valueOf(page))
                .build()
                .execute(new DataCallback<OrganizationParticipateStatistics>(this) {
                    @Override
                    public void showData(List<OrganizationParticipateStatistics> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(final List<OrganizationParticipateStatistics> mDatas) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (adapter == null) {
                    adapter = new OrganizationParticipateStatisticsAdapter(this, mDatas, position);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                } else {
                    adapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                adapter.add(mDatas);
                recyclerView.scrollToPosition(adapter.getData().size());
            }
        }
    }
}
