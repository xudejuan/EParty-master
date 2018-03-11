package com.example.liu.eparty.activity.statistics;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.TaskAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Task;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class OrganizationTaskStatisticsActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_organization_statistics_task)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_organization_statistics_task)
    RecyclerView recyclerView;

    private TaskAdapter adapter;
    private int taskState;
    private String startTime;
    private String endTime;
    private int organizationId;

    @Override
    protected String setTitle() {
        return "组织统计";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_organization_task_statistics;
    }

    @Override
    protected void init() {
        initParams();
        initRefreshLayout();
    }

    private void initParams() {
        switch (getIntent().getStringExtra("taskState")){
            case "已完成":
                taskState = 1;
                break;
            case "未完成":
                taskState = 2;
                break;
            case "超时完成":
                taskState = 3;
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
                .addParams("taskState", String.valueOf(taskState))
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .addParams("organizationId", String.valueOf(organizationId))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new DataCallback<Task>(this){
                    @Override
                    public void showData(List<Task> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(List<Task> meetings) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (adapter == null) {
                    adapter = new TaskAdapter(this, meetings);
                    adapter.setType(3);
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
