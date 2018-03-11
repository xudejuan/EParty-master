package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.TaskAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Task;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class IssueTaskActivity extends BaseActivity {

    @BindView(R.id.tabLayout_issue_task)
    TabLayout tabLayout;
    @BindView(R.id.refreshLayout_issue_task)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_issue_task)
    RecyclerView recyclerView;

    private TaskAdapter taskAdapter;
    private int position = 0;

    @Override
    protected String setTitle() {
        return "已下达的任务";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_issue_task;
    }

    @Override
    protected void init() {
        initTabLayout();
        initRefreshLayout();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("近期任务"));
        tabLayout.addTab(tabLayout.newTab().setText("历史任务"));
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
                .addParams("treeId", String.valueOf(user.getTreeId()))
                .addParams("history", String.valueOf(position))
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
                    taskAdapter.setType(2);
                    recyclerView.setAdapter(taskAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(IssueTaskActivity.this,
                                    IssueTaskDetailActivity.class).putExtra("taskId", mDatas.get(position).getTaskId()));
                        }
                    }, new RecyclerViewClickListener.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(View view, int position) {
                            showDialog(mDatas.get(position));
                        }
                    }));
                } else {
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

    private void showDialog(final Task task) {
        List<String> items = new ArrayList<>();
        if (position == 0) {
            items.add("删除该任务");
            items.add("转为历史任务");
        } else {
            items.add("删除该任务");
        }
        new MaterialDialog.Builder(this)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            deleteTask(task);
                        } else if (position == 1) {
                            turnToHistoryTask(task);
                        }
                    }
                }).show();
    }

    private void deleteTask(final Task task) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认删除任务?")
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            delete(task);
                        }
                    }
                }).show();
    }

    private void delete(Task task) {
        MyOkHttpUtil.post("")
                .addParams("taskId", String.valueOf(task.getTaskId()))
                .build()
                .execute(new OperateCallback(this, "正在删除...") {
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }

    private void turnToHistoryTask(final Task task) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认转为历史任务?")
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            turnToHistory(task);
                        }
                    }
                }).show();
    }

    private void turnToHistory(Task task) {
        MyOkHttpUtil.post("")
                .addParams("taskId", String.valueOf(task.getTaskId()))
                .build()
                .execute(new OperateCallback(this, "正在转移...") {
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }

    @OnClick(R.id.issue_task_issue)
    public void addTask() {
        List<String> items = new ArrayList<>();
        items.add("组织任务");
        items.add("私人任务");
        new MaterialDialog.Builder(this)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            startActivity(new Intent(IssueTaskActivity.this, AddOrganizationTaskActivity.class));
                        } else if (position == 1) {
                            startActivity(new Intent(IssueTaskActivity.this, AddIndividualTaskActivity.class));
                        }
                    }
                }).show();
    }
}
