package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.view.View;
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

public class IssueTaskDetailActivity extends BaseActivity {

    @BindView(R.id.issue_task_detail_title)
    TextView title;
    @BindView(R.id.issue_task_detail_start_time)
    TextView startTime;
    @BindView(R.id.issue_task_detail_end_time)
    TextView endTime;
    @BindView(R.id.issue_task_detail_attachment_name)
    TextView fileName;
    @BindView(R.id.issue_task_detail_content)
    TextView content;
    @BindView(R.id.issue_task_detail_plan_state)
    TextView planState;
    @BindView(R.id.issue_task_detail_check_plan)
    TextView checkPlanState;
    @BindView(R.id.issue_task_detail_report_state)
    TextView reportState;
    @BindView(R.id.issue_task_detail_check_report)
    TextView checkReportState;

    private Task task;
    private String fileUrl;

    @Override
    protected String setTitle() {
        return "已下达的任务";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_issue_task_detail;
    }

    @Override
    protected void init() {
        task = (Task) getIntent().getSerializableExtra("task");
        title.setText(task.getTitle());
        startTime.setText(task.getStartTime());
        endTime.setText(task.getEndTime());
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
                checkPlanState.setVisibility(View.GONE);
                break;
            case "已提交":
                planState.setText("已提交");
                checkPlanState.setText("计划详情");
                break;
            case "未通过":
                planState.setText("未通过");
                checkPlanState.setText("计划详情");
                break;
            case "已通过":
                planState.setText("已通过");
                checkPlanState.setText("计划详情");
                break;
        }
    }

    private void setReportState() {
        switch (task.getReportState()){
            case "未提交":
                reportState.setText("未提交");
                checkReportState.setVisibility(View.GONE);
                break;
            case "已提交":
                reportState.setText("已提交");
                checkReportState.setText("报告详情");
                break;
            case "未通过":
                reportState.setText("未通过");
                checkReportState.setText("报告详情");
                break;
            case "已通过":
                reportState.setText("已通过");
                checkReportState.setText("报告详情");
                break;
        }
    }

    @OnClick(R.id.issue_task_detail_attachment_detail)
    public void showFile() {
        if (!FileUtil.isExist(this, "issueTaskDetail" + task.getTaskId() + fileName.getText().toString())) {
            MyOkHttpUtil.post("")
                    .addParams("fileUrl", fileUrl)
                    .build()
                    .execute(new FileDownloadCallback(FileUtil.getSavePath(this),
                            fileName.getText().toString(), this) {
                        @Override
                        public void onResponse(File response, int id) {
                            super.onResponse(response, id);
                            FileUtil.show(IssueTaskDetailActivity.this, response.getAbsolutePath());
                        }
                    });
        } else {
            FileUtil.show(IssueTaskDetailActivity.this, FileUtil.getSavePath(this)
                    + "/" + "issueTaskDetail" + task.getTaskId() + fileName.getText().toString());
        }
    }

    @OnClick(R.id.issue_task_detail_content_detail)
    public void showContent() {
        new MaterialDialog.Builder(this)
                .title("任务内容")
                .content(content.getText().toString())
                .show();
    }

    @OnClick(R.id.issue_task_detail_check_plan)
    public void showPlan() {
        startActivity(new Intent(this, CheckPlanActivity.class)
                .putExtra("taskId", String.valueOf(task.getTaskId()))
                .putExtra("recipientId", String.valueOf(task.getRecipientId())));
    }

    @OnClick(R.id.issue_task_detail_check_report)
    public void showReport() {
        startActivity(new Intent(this, CheckReportActivity.class)
                .putExtra("taskId", String.valueOf(task.getTaskId()))
                .putExtra("recipientId", String.valueOf(task.getRecipientId())));
    }


}
