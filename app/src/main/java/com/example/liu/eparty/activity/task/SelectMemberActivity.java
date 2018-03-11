package com.example.liu.eparty.activity.task;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.MemberAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Member;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectMemberActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_select_member)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_select_member)
    RecyclerView recyclerView;

    private MemberAdapter memberAdapter;
    private int treeId;

    @Override
    protected String setTitle() {
        return "筛选成员";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_select_member;
    }

    @Override
    protected void init() {
        treeId = getIntent().getIntExtra("treeId", 0);
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
        MyOkHttpUtil.get("")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("treeId", String.valueOf(treeId))
                .addParams("position", String.valueOf(page))
                .build()
                .execute(new DataCallback<Member>(this) {
                    @Override
                    public void showData(List<Member> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(final List<Member> mDatas) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (memberAdapter == null) {
                    memberAdapter = new MemberAdapter(this, mDatas, "choose");
                    recyclerView.setAdapter(memberAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                } else {
                    memberAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                memberAdapter.add(mDatas);
                recyclerView.scrollToPosition(memberAdapter.getData().size());
            }
        }
    }

    @OnClick(R.id.select_member_all_select)
    public void allSelected(){
        memberAdapter.allSelected();
    }

    @OnClick(R.id.select_member_query)
    public void query(){
        memberAdapter.query();
        finish();
    }
}
