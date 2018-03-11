package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.TaskAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Task;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class ShowTaskActivity extends BaseActivity {

    @BindView(R.id.tabLayout_show_task)
    TabLayout tabLayout;
    @BindView(R.id.refreshLayout_show_task)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_show_task)
    RecyclerView recyclerView;

    private TaskAdapter taskAdapter;
    private int position = 0;

    @Override
    protected String setTitle() {
        return "查看任务";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_task;
    }

    @Override
    protected void init() {
        initTabLayout();
        initRefreshLayout();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("组织任务"));
        tabLayout.addTab(tabLayout.newTab().setText("私人任务"));
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
        MyOkHttpUtil.get("PerReceiveTest")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("selfType", String.valueOf(position))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new DataCallback<Task>(this) {
                    @Override
                    public void showData(List<Task> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(final List<Task> mDatas) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (taskAdapter == null) {
                    taskAdapter = new TaskAdapter(this, mDatas);
                    taskAdapter.setType(position);
                    recyclerView.setAdapter(taskAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(ShowTaskActivity.this,
                                    ShowTaskDetailActivity.class).putExtra("task", mDatas.get(position)));
                        }
                    }));
                } else {
                    taskAdapter.setType(position);
                    taskAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                taskAdapter.add(mDatas);
                recyclerView.scrollToPosition(taskAdapter.getData().size());
            }
        }
    }
}
