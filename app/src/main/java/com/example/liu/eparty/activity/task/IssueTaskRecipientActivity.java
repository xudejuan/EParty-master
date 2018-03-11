package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.RecipientAdapter;
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
import butterknife.OnClick;

public class IssueTaskRecipientActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_issue_task_recipient)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_issue_task_recipient)
    RecyclerView recyclerView;

    private RecipientAdapter recipientAdapter;
    private String taskId;

    @Override
    protected String setTitle() {
        return "任务列表";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_issue_task_recipient;
    }

    @Override
    protected void init() {
        taskId = getIntent().getStringExtra("taskId");
        initRefreshLayout();
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
                .addParams("taskId", String.valueOf(taskId))
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
                if (recipientAdapter == null) {
                    recipientAdapter = new RecipientAdapter(this, mDatas);
                    recyclerView.setAdapter(recipientAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(IssueTaskRecipientActivity.this,
                                    IssueTaskDetailActivity.class).putExtra("task", mDatas.get(position)));
                        }
                    }));
                } else {
                    recipientAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                recipientAdapter.add(mDatas);
                recyclerView.scrollToPosition(recipientAdapter.getData().size());
            }
        }
    }

    @OnClick(R.id.issue_task_recipient_issue_warning)
    public void showWarning(){
        startActivity(new Intent(this, ShowWarningActivity.class)
                .putExtra("taskId", taskId));
    }
}
