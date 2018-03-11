package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Plan;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowPlanActivity extends BaseActivity {

    @BindView(R.id.show_plan_start_time)
    TextView startTime;
    @BindView(R.id.show_plan_end_time)
    TextView endTime;
    @BindView(R.id.show_plan_create_time)
    TextView createTime;
    @BindView(R.id.show_plan_place)
    TextView place;
    @BindView(R.id.show_plan_content)
    TextView content;
    @BindView(R.id.show_plan_suggestion)
    TextView suggestion;
    @BindView(R.id.show_plan_resubmit)
    Button resubmit;
    @BindView(R.id.cl_show_plan)
    ConstraintLayout showCheck;

    private int taskId;

    @Override
    protected String setTitle() {
        return "计划详情";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_plan;
    }

    @Override
    protected void init() {
        taskId = getIntent().getIntExtra("taskId", 0);
        Plan plan = (Plan) getIntent().getSerializableExtra("plan");
        startTime.setText(plan.getDoStartTime());
        endTime.setText(plan.getDoFinishTime());
        createTime.setText(plan.getSubmitTime());
        place.setText(plan.getDoWhere());
        content.setText(plan.getTaskName());
        plan.setCheckStatus("已通过");
        if (plan.getCheckStatus() == null){
            resubmit.setVisibility(View.VISIBLE);
        }else if (plan.getCheckStatus().toString().equals("已通过")){
            suggestion.setText(plan.getCheckOpinion());
            showCheck.setVisibility(View.VISIBLE);
        }else if (plan.getCheckStatus().toString().equals("未通过")){
            suggestion.setText(plan.getCheckOpinion());
            resubmit.setVisibility(View.VISIBLE);
            showCheck.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.show_plan_content_detail)
    public void showContent(){
        new MaterialDialog.Builder(this)
                .title("计划内容")
                .content(content.getText().toString())
                .show();
    }

    @OnClick(R.id.show_plan_resubmit)
    public void resubmit(){
        MyOkHttpUtil.post("")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("taskId", String.valueOf(taskId))
                .build()
                .execute(new OperateCallback(this, "正在删除..."){
                    @Override
                    public void onCompleted() {
                        startActivity(new Intent(ShowPlanActivity.this,
                                AddPlanActivity.class).putExtra("taskId", taskId));
                    }
                });
    }
}
