package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.WarningAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Warning;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.DateUtil;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowWarningActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_show_warning)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_show_warning)
    RecyclerView recyclerView;

    private WarningAdapter warningAdapter;
    private String taskId;

    @Override
    protected String setTitle() {
        return "预警情况";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_warning;
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
//        MyOkHttpUtil.get("")
//                .addParams("userId", String.valueOf(user.getUserId()))
//                .addParams("treeId", String.valueOf(user.getTreeId()))
//                .build()
//                .execute(new DataCallback<Warning>(this){
//                    @Override
//                    public void showData(List<Warning> mDatas) {
//                        initRecyclerView(mDatas);
//                    }
//                });
        List<Warning> warnings = new ArrayList<>();
        warnings.add(new Warning("2018-02-11 18:18:00", "1"));
        warnings.add(new Warning("2018-02-11 18:18:00", "2"));
        warnings.add(new Warning("2018-02-11 18:18:00", "3"));
        initRecyclerView(warnings);
    }

    private void initRecyclerView(final List<Warning> mDatas) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (warningAdapter == null) {
                    warningAdapter = new WarningAdapter(this, mDatas);
                    recyclerView.setAdapter(warningAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(ShowWarningActivity.this,
                                    ShowWarningDetailActivity.class).putExtra("warning", mDatas.get(position)));
                        }
                    }));
                } else {
                    warningAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                warningAdapter.add(mDatas);
                recyclerView.scrollToPosition(warningAdapter.getData().size());
            }
        }
    }

    @OnClick(R.id.show_warning_issue)
    public void issue(){
        new MaterialDialog.Builder(this)
                .title("预警标题")
                .widgetColor(getResources().getColor(R.color.blue_deep))
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入预警标题", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        warning(dialog.getInputEditText().getText().toString());
                    }
                })
                .show();
    }

    private void warning(String warning) {
        MyOkHttpUtil.post("")
                .addParams("taskId", taskId)
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("warning", warning)
                .addParams("warningTime", DateUtil.getTimeWithSecond())
                .build()
                .execute(new OperateCallback(this, "下达中..."));
    }
}
