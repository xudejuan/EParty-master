package com.example.liu.eparty.activity.task;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.HandleAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Warning;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShowWarningDetailActivity extends BaseActivity {

    @BindView(R.id.tabLayout_show_warning_detail)
    TabLayout tabLayout;
    @BindView(R.id.refreshLayout_show_warning_detail)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_show_warning_detail)
    RecyclerView recyclerView;

    private HandleAdapter handleAdapter;
    private int position = 0;

    @Override
    protected String setTitle() {
        return "预警处理详情";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_warning_detail;
    }

    @Override
    protected void init() {
        initTabLayout();
        initRefreshLayout();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("未处理"));
        tabLayout.addTab(tabLayout.newTab().setText("已处理"));
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
//        MyOkHttpUtil.get("")
//                .addParams("userId", String.valueOf(user.getUserId()))
//                .addParams("treeId", String.valueOf(user.getTreeId()))
//                .addParams("state", String.valueOf(position))
//                .addParams("page", String.valueOf(page))
//                .build()
//                .execute(new DataCallback<Warning>(this){
//                    @Override
//                    public void showData(List<Warning> mDatas) {
//                        initRecyclerView(mDatas);
//                    }
//                });
        List<Warning> warnings = new ArrayList<>();
        warnings.add(new Warning("张三", 0, "惠东县"));
        warnings.add(new Warning("李四", 0, "惠东县"));
        warnings.add(new Warning("王五", 0, "惠东县"));
        initRecyclerView(warnings);
    }

    private void initRecyclerView(final List<Warning> mDatas) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (handleAdapter == null) {
                    handleAdapter = new HandleAdapter(this, mDatas);
                    recyclerView.setAdapter(handleAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                } else {
                    handleAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                handleAdapter.add(mDatas);
                recyclerView.scrollToPosition(handleAdapter.getData().size());
            }
        }
    }
}
