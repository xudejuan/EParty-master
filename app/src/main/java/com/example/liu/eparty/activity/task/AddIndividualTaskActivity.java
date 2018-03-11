package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.ConvertUtil;
import com.example.liu.eparty.util.DateTimePicker;
import com.example.liu.eparty.util.DateUtil;
import com.example.liu.eparty.util.FileUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddIndividualTaskActivity extends BaseActivity {

    @BindView(R.id.add_task_individual_title)
    EditText title;
    @BindView(R.id.add_task_individual_start_time)
    TextView startTime;
    @BindView(R.id.add_task_individual_end_time)
    TextView endTime;
    @BindView(R.id.add_task_individual_individual)
    TextView individual;
    @BindView(R.id.add_task_individual_attachment_name)
    TextView attachmentName;
    @BindView(R.id.add_task_individual_content)
    EditText content;

    private MaterialDialog materialDialog;
    private List<String> path;
    private String filePath;
    private ArrayList<Integer> memberIds = new ArrayList<>();

    @Override
    protected String setTitle() {
        return "下达私人任务";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_organization_task;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.add_task_individual_start_time)
    public void chooseStartTime() {
        DateTimePicker.choose(this, startTime);
    }

    @OnClick(R.id.add_task_individual_end_time)
    public void chooseEndTime() {
        DateTimePicker.choose(this, endTime);
    }

    @OnClick(R.id.add_task_individual_individual)
    public void chooseIndividual() {
        Intent intent = new Intent(this, ChooseMemberActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            memberIds = data.getIntegerArrayListExtra("memberIds");
            ArrayList<String> memberNames = data.getStringArrayListExtra("memberNames");
            if (memberNames.size() == 0) {
                individual.setText("请选择");
            } else {
                individual.setText(ConvertUtil.listToString(memberNames));
            }
            ACache.get(this, "sparseArray").clear();
        }
    }

    @OnClick(R.id.add_task_individual_attachment)
    public void chooseAttachment(){
        materialDialog = new MaterialDialog.Builder(this)
                .title("搜索文件")
                .widgetColor(getResources().getColor(R.color.blue_deep))
                .content("正在搜索本地文件，请稍候")
                .cancelable(false)
                .progress(true, 0, false)
                .show();
        new Thread() {
            @Override
            public void run() {
                path = FileUtil.getSpecificTypeOfFile(AddIndividualTaskActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        materialDialog.dismiss();
                        showFile();
                    }
                });
            }
        }.start();
    }

    private void showFile() {
        final List<String> fileName = new ArrayList<>();
        if (path != null) {
            for (String s : path) {
                fileName.add(FileUtil.getFileName(s));
            }
            new MaterialDialog.Builder(this)
                    .title("请选择文件")
                    .items(fileName)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            dialog.dismiss();
                            filePath = path.get(position);
                            attachmentName.setText(fileName.get(position));
                            attachmentName.setEnabled(true);
                        }
                    }).show();
        } else {
            new MaterialDialog.Builder(this)
                    .title("错误")
                    .content("没有找到任何文件")
                    .show();
        }
    }

    @OnClick(R.id.add_task_individual_issue)
    public void issue(){
        if (check()){
            MyOkHttpUtil.post("SentTast")
                    .addFile("Mrequest", FileUtil.getFileName(filePath), new File(filePath))
                    .addParams("tastTitle", title.getText().toString())
                    .addParams("startTime", startTime.getText().toString())
                    .addParams("finishTime", endTime.getText().toString())
                    .addParams("ids", ConvertUtil.listToString(memberIds))
                    .addParams("tastContent", content.getText().toString())
                    .build()
                    .execute(new OperateCallback(this, "正在下达..."){
                        @Override
                        public void onCompleted() {
                            finish();
                        }
                    });
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(title.getText().toString().trim())) {
            ToastUtil.show(this, "任务标题不能为空");
            return false;
        }
        if (!DateUtil.isDateTimeFormat(startTime.getText().toString())) {
            ToastUtil.show(this, "开始时间信息不完整");
            return false;
        }
        if (!DateUtil.isDateTimeFormat(endTime.getText().toString())) {
            ToastUtil.show(this, "结束时间信息不完整");
            return false;
        }
        if (individual.getText().toString().equals(getString(R.string.choose))) {
            ToastUtil.show(this, "最少选择一个人");
            return false;
        }
        if (TextUtils.isEmpty(content.getText().toString().trim())) {
            ToastUtil.show(this, "任务内容不能为空");
            return false;
        }
        return true;
    }
}
