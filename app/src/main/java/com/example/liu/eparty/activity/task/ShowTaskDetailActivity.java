package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Task;
import com.example.liu.eparty.callback.FileDownloadCallback;
import com.example.liu.eparty.util.FileUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class ShowTaskDetailActivity extends BaseActivity {

    @BindView(R.id.show_task_detail_title)
    TextView title;
    @BindView(R.id.show_task_detail_start_time)
    TextView startTime;
    @BindView(R.id.show_task_detail_end_time)
    TextView endTime;
    @BindView(R.id.show_task_detail_individual)
    TextView individual;
    @BindView(R.id.show_task_detail_organization)
    TextView organization;
    @BindView(R.id.show_task_detail_attachment_name)
    TextView fileName;
    @BindView(R.id.show_task_detail_content)
    TextView content;
    @BindView(R.id.show_task_detail_plan_state)
    TextView planState;
    @BindView(R.id.show_task_detail_submit_plan)
    TextView submitPlanState;
    @BindView(R.id.show_task_detail_report_state)
    TextView reportState;
    @BindView(R.id.show_task_detail_submit_report)
    TextView submitReportState;

    private Task task;
    private String fileUrl;
    private Intent planIntent;
    private Intent reportIntent;

    @Override
    protected String setTitle() {
        return "任务详情";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_task_detail;
    }

    @Override
    protected void init() {
        task = (Task) getIntent().getSerializableExtra("task");
        title.setText(task.getTitle());
        startTime.setText(task.getStartTime());
        endTime.setText(task.getEndTime());
        individual.setText(task.getIssuer());
        organization.setText(task.getIssuerOrganization());
        fileName.setText(task.getAttachmentName());
        fileUrl = task.getAttachmentUrl();
        content.setText(task.getContent());
        setPlanState();
        setReportState();
    }

    private void setPlanState() {
        switch (task.getPlanState()){
            case "未提交":
                planState.setText("未提交");
                submitPlanState.setText("提交计划");
                planIntent = new Intent(this, AddPlanActivity.class)
                        .putExtra("taskId", task.getTaskId());
                break;
            case "已提交":
                planState.setText("已提交");
                submitPlanState.setText("计划详情");
                planIntent = new Intent(this, ShowPlanActivity.class)
                        .putExtra("taskId", task.getTaskId())
                        .putExtra("plan", task.getPlanState());
                break;
            case "未通过":
                planState.setText("未通过");
                submitPlanState.setText("计划详情");
                planIntent = new Intent(this, ShowPlanActivity.class)
                        .putExtra("taskId", task.getTaskId())
                        .putExtra("plan", task.getPlanState());
                break;
            case "已通过":
                planState.setText("已通过");
                submitPlanState.setText("计划详情");
                planIntent = new Intent(this, ShowPlanActivity.class)
                        .putExtra("taskId", task.getTaskId())
                        .putExtra("plan", task.getPlanState());
                break;
        }
    }

    private void setReportState() {
        switch (task.getReportState()){
            case "未提交":
                reportState.setText("未提交");
                submitReportState.setText("提交报告");
                reportIntent = new Intent(this, AddReportActivity.class)
                        .putExtra("taskId", task.getTaskId());
                break;
            case "已提交":
                reportState.setText("已提交");
                submitReportState.setText("报告详情");
                reportIntent = new Intent(this, ShowReportActivity.class)
                        .putExtra("taskId", task.getTaskId())
                        .putExtra("report", task.getReportState());
                break;
            case "未通过":
                reportState.setText("未通过");
                submitReportState.setText("报告详情");
                reportIntent = new Intent(this, ShowReportActivity.class)
                        .putExtra("taskId", task.getTaskId())
                        .putExtra("report", task.getReportState());
                break;
            case "已通过":
                reportState.setText("已通过");
                submitReportState.setText("报告详情");
                reportIntent = new Intent(this, ShowReportActivity.class)
                        .putExtra("taskId", task.getTaskId())
                        .putExtra("report", task.getReportState());
                break;
        }
    }

    @OnClick(R.id.show_task_detail_attachment_detail)
    public void showFile(){
        if (!FileUtil.isExist(this, "showTaskDetail" + task.getTaskId() + fileName.getText().toString())) {
            MyOkHttpUtil.post("")
                    .addParams("fileUrl", fileUrl)
                    .build()
                    .execute(new FileDownloadCallback(FileUtil.getSavePath(this),
                            fileName.getText().toString() + task.getTaskId(), this) {
                        @Override
                        public void onResponse(File response, int id) {
                            super.onResponse(response, id);
                            FileUtil.show(ShowTaskDetailActivity.this, response.getAbsolutePath());
                        }
                    });
        }else {
            FileUtil.show(ShowTaskDetailActivity.this, FileUtil.getSavePath(this)
                    + "/" + "showTaskDetail" + task.getTaskId() + fileName.getText().toString());
        }
    }

    @OnClick(R.id.show_task_detail_content_detail)
    public void showContent(){
        new MaterialDialog.Builder(this)
                .title("任务内容")
                .content(content.getText().toString())
                .show();
    }

    @OnClick(R.id.show_task_detail_submit_plan)
    public void showPlan(){
        startActivity(planIntent);
    }

    @OnClick(R.id.show_task_detail_submit_report)
    public void showReport(){
        startActivity(reportIntent);
    }
}
