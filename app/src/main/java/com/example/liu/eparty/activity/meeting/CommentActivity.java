package com.example.liu.eparty.activity.meeting;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.CommentAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Comment;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.example.liu.eparty.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_comment)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_comment)
    RecyclerView recyclerView;

    private int position = 0;
    private Meeting meeting;
    private CommentAdapter commentAdapter;

    @Override
    protected String setTitle() {
        return "评论";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void init() {
        meeting = (Meeting) ACache.get(this, "meeting").getAsObject("meeting");
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
        MyOkHttpUtil.get("meeting/getComment")
                .addParams("meetingId", String.valueOf(meeting.getId()))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new DataCallback<Comment>(this){
                    @Override
                    public void showData(List<Comment> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    private void initRecyclerView(List<Comment> comments) {
        switch (SmartRefreshLayoutUtil.state) {
            case SmartRefreshLayoutUtil.REFRESH: {
                if (commentAdapter == null) {
                    commentAdapter = new CommentAdapter(this, comments);
                    recyclerView.setAdapter(commentAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                }else {
                    commentAdapter.refreshData(comments);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                commentAdapter.add(comments);
                recyclerView.scrollToPosition(commentAdapter.getData().size());
            }
        }
    }

    @OnClick(R.id.comment)
    public void comment() {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_comment, null);
        //初始化控件
        final EditText comment = inflate.findViewById(R.id.comment);
        inflate.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment.getText().length() == 0) {
                    ToastUtil.show(CommentActivity.this, "评论内容不能为空");
                } else {
                    commit(comment.getText().toString());
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setGravity(Gravity.BOTTOM);
        //重新设置padding值，使match_parent有效
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialog.show();
        //激活键盘
        comment.post(new Runnable() {
            @Override
            public void run() {
                comment.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(comment, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    public void commit(String comment) {
        OkHttpUtils.post()
                .url("")
                .addParams("", "")
                .build()
                .execute(new OperateCallback(this, "正在发表..."));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ACache.get(this, "meeting").clear();
    }
}
