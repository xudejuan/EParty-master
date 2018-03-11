package com.example.liu.eparty.activity.task;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.DateTimePicker;
import com.example.liu.eparty.util.DateUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AddPlanActivity extends BaseActivity {

    @BindView(R.id.add_plan_start_time)
    TextView startTime;
    @BindView(R.id.add_plan_end_time)
    TextView endTime;
    @BindView(R.id.add_plan_place)
    TextView place;
    @BindView(R.id.add_plan_content)
    TextView content;

    private int tastId;

    @Override
    protected String setTitle() {
        return "提交计划";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_plan;
    }

    @Override
    protected void init() {
        tastId = getIntent().getIntExtra("tastId", 0);
    }

    @OnClick(R.id.add_plan_start_time)
    public void startTime(View view) {
        DateTimePicker.choose(this, startTime);
    }

    @OnClick(R.id.add_plan_end_time)
    public void endTime(View view) {
        DateTimePicker.choose(this, endTime);
    }

    @OnClick(R.id.add_plan_submit)
    public void submit() {
        if (check()) {
            MyOkHttpUtil.post("")
                    .addParams("tastId", String.valueOf(tastId))
                    .addParams("submitUserId", String.valueOf(user.getUserId()))
                    .addParams("submitName", user.getUserName())
                    .addParams("taskName", content.getText().toString())
                    .addParams("doStartTime", startTime.getText().toString())
                    .addParams("doFinishTime", endTime.getText().toString())
                    .addParams("doWhere", place.getText().toString())
                    .build()
                    .execute(new OperateCallback(this, "正在提交...") {
                        @Override
                        public void onCompleted() {
                            finish();
                        }
                    });
        }
    }

    private boolean check() {
        if (!DateUtil.isDateTimeFormat(startTime.getText().toString())) {
            ToastUtil.show(this, "开始时间信息不完整");
            return false;
        }
        if (!DateUtil.isDateTimeFormat(endTime.getText().toString())) {
            ToastUtil.show(this, "结束时间信息不完整");
            return false;
        }
        if (TextUtils.isEmpty(place.getText().toString().trim())) {
            ToastUtil.show(this, "执行地点不能为空");
            return false;
        }
        if (TextUtils.isEmpty(content.getText().toString().trim())) {
            ToastUtil.show(this, "计划内容不能为空");
            return false;
        }
        return true;
    }
}
