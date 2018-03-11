package com.example.liu.eparty.activity.task;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Plan;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckPlanActivity extends BaseActivity {

    @BindView(R.id.check_plan_detail_start_time)
    TextView startTime;
    @BindView(R.id.check_plan_detail_end_time)
    TextView endTime;
    @BindView(R.id.check_plan_detail_create_time)
    TextView createTime;
    @BindView(R.id.check_plan_detail_place)
    TextView place;
    @BindView(R.id.check_plan_detail_content)
    TextView content;
    @BindView(R.id.check_plan_detail_suggestion)
    TextView suggestion;
    @BindView(R.id.ll_check_plan_detail)
    LinearLayout showPass;
    @BindView(R.id.cl_check_plan_detail)
    ConstraintLayout showCheck;

    private String taskId;
    private String recipientId;

    @Override
    protected String setTitle() {
        return "计划详情";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_check_plan;
    }

    @Override
    protected void init() {
        taskId = getIntent().getStringExtra("taskId");
        recipientId = getIntent().getStringExtra("recipientId");
    }

    @Override
    protected void requestData(int page) {
        MyOkHttpUtil.get("")
                .addParams("taskId", taskId)
                .addParams("recipientId", recipientId)
                .build()
                .execute(new DataCallback<Plan>(this){
                    @Override
                    public void showData(List<Plan> mDatas) {
                        initPlan(mDatas.get(0));
                    }
                });
    }

    private void initPlan(Plan plan) {
        startTime.setText(plan.getDoStartTime());
        endTime.setText(plan.getDoFinishTime());
        place.setText(plan.getDoWhere());
        content.setText(plan.getTaskName());
        if (plan.getCheckStatus() == null){
            showPass.setVisibility(View.VISIBLE);
        }else {
            showCheck.setVisibility(View.VISIBLE);
            suggestion.setText(plan.getCheckOpinion());
        }
    }

    @OnClick(R.id.check_plan_detail_content_detail)
    public void showContent(){
        new MaterialDialog.Builder(this)
                .title("计划内容")
                .content(content.getText().toString())
                .show();
    }

    @OnClick({R.id.check_plan_detail_pass, R.id.check_plan_detail_unpass})
    public void check(final View view){
        new MaterialDialog.Builder(this)
                .title("批改建议")
                .widgetColor(getResources().getColor(R.color.blue_deep))
                .input("请填写批改建议", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                    }
                })
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE){
                            if (dialog.getInputEditText() != null){
                                if (view.getId() == R.id.check_plan_detail_pass) {
                                    pass(dialog.getInputEditText().getText().toString());
                                }else if (view.getId() == R.id.check_plan_detail_unpass){
                                    unpass(dialog.getInputEditText().getText().toString());
                                }
                            }else {
                                ToastUtil.show(CheckPlanActivity.this, "批改建议不能为空不能为空");
                            }
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    public void unpass(String suggestion){
        MyOkHttpUtil.post("")
                .addParams("receiveUserId", String.valueOf(user.getUserId()))
                .addParams("readOk", "0")
                .addParams("checkOpinion", suggestion)
                .build()
                .execute(new OperateCallback(this, "正在提交..."){
                    @Override
                    public void onCompleted() {
                        setResult(RESULT_OK);
                        finish();
                    }
                });

    }

    private void pass(String suggestion){
        MyOkHttpUtil.post("")
                .addParams("receiveUserId", String.valueOf(user.getUserId()))
                .addParams("readOk", "1")
                .addParams("checkOpinion", suggestion)
                .build()
                .execute(new OperateCallback(this, "正在提交..."){
                    @Override
                    public void onCompleted() {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }
}
