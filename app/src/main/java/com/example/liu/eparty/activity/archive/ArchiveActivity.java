package com.example.liu.eparty.activity.archive;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.ArchiveAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Task;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class ArchiveActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_issue_task)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_issue_task)
    RecyclerView recyclerView;

    private ArchiveAdapter archiveAdapter;

    @Override
    protected String setTitle() {
        return "任务归档";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_archive;
    }

    @Override
    protected void init() {
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
                if (archiveAdapter == null) {
                    archiveAdapter = new ArchiveAdapter(this, mDatas);
                    recyclerView.setAdapter(archiveAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                } else {
                    archiveAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                archiveAdapter.add(mDatas);
                recyclerView.scrollToPosition(archiveAdapter.getData().size());
            }
        }
    }
}
