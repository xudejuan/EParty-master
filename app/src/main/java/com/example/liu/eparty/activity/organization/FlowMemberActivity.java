package com.example.liu.eparty.activity.organization;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.FlowMemberAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Member;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class FlowMemberActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_flow_member)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_flow_member)
    RecyclerView recyclerView;

    private FlowMemberAdapter flowMemberAdapter;

    @Override
    protected String setTitle() {
        return "流动党员";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_flow_member;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void requestData(int page) {
        MyOkHttpUtil.post("")
                .addParams("", "")
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new DataCallback<Member>(this){
                    @Override
                    public void showData(List<Member> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(final List<Member> mDatas) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (flowMemberAdapter == null) {
                    flowMemberAdapter = new FlowMemberAdapter(this, mDatas);
                    recyclerView.setAdapter(flowMemberAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(View view, int position) {
                            showDialog(mDatas.get(position));
                        }
                    }));
                }else {
                    flowMemberAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                flowMemberAdapter.add(mDatas);
                recyclerView.scrollToPosition(flowMemberAdapter.getData().size());
            }
        }
    }

    private void showDialog(final Member member){
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认删除该流动党员?")
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            delete(member);
                        }
                    }
                }).show();
    }

    private void delete(Member member){
        MyOkHttpUtil.post("")
                .addParams("taskId", String.valueOf(member.getMemberId()))
                .build()
                .execute(new OperateCallback(this, "正在删除...") {
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }
}
