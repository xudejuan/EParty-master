package com.example.liu.eparty.activity.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.activity.login.ResetPasswordActivity;
import com.example.liu.eparty.adapter.MemberAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Member;
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

public class MemberActivity extends BaseActivity {

    @BindView(R.id.tabLayout_member)
    TabLayout tabLayout;
    @BindView(R.id.refreshLayout_member)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_member)
    RecyclerView recyclerView;
    @BindView(R.id.member_add)
    Button addMember;

    private MemberAdapter memberAdapter;
    private int position = 0;
    private int treeId;

    @Override
    protected String setTitle() {
        return "组织成员";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_member;
    }

    @Override
    protected void init() {
        treeId = getIntent().getIntExtra("treeId", 0);
        if (treeId == 0){
            treeId = user.getTreeId();
        }
        initTabLayout();
        initRefreshLayout();
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("当前成员"));
        tabLayout.addTab(tabLayout.newTab().setText("历史人员"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                changeVisible();
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

    private void changeVisible() {
        if (position == 0){
            addMember.setVisibility(View.VISIBLE);
        }else {
            addMember.setVisibility(View.GONE);
        }
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
        if (position == 0){
            MyOkHttpUtil.get("User/SelectByPage")
                    .addParams("loginTreeId", String.valueOf(user.getTreeId()))
                    .addParams("treeId", String.valueOf(treeId))
                    .addParams("position", String.valueOf(page))
                    .build()
                    .execute(new DataCallback<Member>(this) {
                        @Override
                        public void showData(List<Member> mDatas) {
                            initRecyclerView(mDatas);
                        }
                    });
        }else {
            MyOkHttpUtil.get("User/SelectByPage")
                    .addParams("loginTreeId", String.valueOf(user.getTreeId()))
                    .addParams("treeId", String.valueOf(treeId))
                    .addParams("history", String.valueOf(position))
                    .addParams("position", String.valueOf(page))
                    .build()
                    .execute(new DataCallback<Member>(this) {
                        @Override
                        public void showData(List<Member> mDatas) {
                            initRecyclerView(mDatas);
                        }
                    });
        }
    }

    private void initRecyclerView(final List<Member> mDatas) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (memberAdapter == null) {
                    memberAdapter = new MemberAdapter(this, mDatas, "show");
                    recyclerView.setAdapter(memberAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(View view, int position) {
                            showChooseDialog(mDatas.get(position));
                        }
                    }));
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

    private void showChooseDialog(final Member member){
        if (position == 0) {
            List<String> items = new ArrayList<>();
            items.add("修改信息");
            items.add("修改密码");
            items.add("转为历史成员");
            items.add("删除该成员");
            new MaterialDialog.Builder(this)
                    .items(items)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            switch (position) {
                                case 0:
                                    startActivity(new Intent(MemberActivity.this, AddMemberActivity.class)
                                            .putExtra("member", member));
                                    break;
                                case 1:
                                    startActivity(new Intent(MemberActivity.this, ResetPasswordActivity.class)
                                            .putExtra("userId", member.getMemberId()));
                                    break;
                                case 2:
                                    turnToHistoryMember(member.getMemberId());
                                    break;
                                case 3:
                                    deleteMember(member.getMemberId());
                                    break;
                            }
                        }
                    }).show();
        }else {
            List<String> items = new ArrayList<>();
            items.add("恢复为当前成员");
            items.add("删除该成员");
            new MaterialDialog.Builder(this)
                    .items(items)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            switch (position) {
                                case 0:
                                    returnToCurrentMember(member.getMemberId());
                                    break;
                                case 1:
                                    deleteMember(member.getMemberId());
                                    break;
                            }
                        }
                    }).show();
        }
    }

    private void returnToCurrentMember(final int memberId) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认恢复为当前成员?")
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            returnToCurrent(memberId);
                        }
                    }
                }).show();
    }

    private void returnToCurrent(int memberId){
        MyOkHttpUtil.post("MakeHistory")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("history", String.valueOf(0))
                .addParams("memberId", String.valueOf(memberId))
                .build()
                .execute(new OperateCallback(this, "正在转移...") {
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }

    private void turnToHistoryMember(final int memberId) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认转为历史成员?")
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            turnToHistory(memberId);
                        }
                    }
                }).show();
    }

    private void turnToHistory(int memberId){
        MyOkHttpUtil.post("MakeHistory")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("history", String.valueOf(1))
                .addParams("memberId", String.valueOf(memberId))
                .build()
                .execute(new OperateCallback(this, "正在转移...") {
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }

    private void deleteMember(final int memberId) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认删除成员?")
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            delete(memberId);
                        }
                    }
                }).show();
    }

    private void delete(int memberId) {
        MyOkHttpUtil.post("DeleteUser")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("memberId", String.valueOf(memberId))
                .build()
                .execute(new OperateCallback(this, "正在删除...") {
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }

    @OnClick(R.id.member_add)
    public void addMember(){
        startActivity(new Intent(this, AddMemberActivity.class));
    }
}
