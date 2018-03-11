package com.example.liu.eparty.activity.statistics;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.TaskStatistics;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.YearMonthPicker;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskOrganizationStatisticsActivity extends BaseActivity {

    @BindView(R.id.organization_statistics_pieChart)
    PieChart pieChart;
    @BindView(R.id.tv_organization_statistics_start_time)
    TextView startTime;
    @BindView(R.id.tv_organization_statistics_end_time)
    TextView endTime;
    @BindView(R.id.tv_organization_statistics_organization)
    TextView organizationName;

    private int organizationId;

    @Override
    protected String setTitle() {
        return "组织统计";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_task_organization_statistics;
    }

    @Override
    protected void init() {
        noData();
    }

    @OnClick(R.id.ll_organization_statistics_start_time)
    public void chooseStartTime(View view){
        YearMonthPicker yearMonthPicker = new YearMonthPicker();
        yearMonthPicker.choose(this, startTime, new YearMonthPicker.OnConfirmListener() {
            @Override
            public void onConfirm() {
                check();
            }
        });
        yearMonthPicker.show(view);
    }

    @OnClick(R.id.ll_organization_statistics_end_time)
    public void chooseEndTime(View view){
        YearMonthPicker yearMonthPicker = new YearMonthPicker();
        yearMonthPicker.choose(this, endTime, new YearMonthPicker.OnConfirmListener() {
            @Override
            public void onConfirm() {
                check();
            }
        });
        yearMonthPicker.show(view);
    }

    @OnClick(R.id.ll_organization_statistics_organization)
    public void chooseOrganization(){
        startActivityForResult(new Intent(this, SingleChooseOrganizationActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK){
            organizationName.setText(data.getStringExtra("organizationName"));
            organizationId = data.getIntExtra("organizationId", 0);
            check();
        }
    }

    private void check() {
        if (!startTime.getText().toString().equals("请选择")
                && !endTime.getText().toString().equals("请选择")
                && !organizationName.getText().toString().equals("请选择")){
            requestData();
        }
    }

    private void requestData(){
        MyOkHttpUtil.get("")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("startTime", startTime.getText().toString())
                .addParams("endTime", endTime.getText().toString())
                .addParams("organization", String.valueOf(organizationId))
                .build()
                .execute(new DataCallback<TaskStatistics>(this){
                    @Override
                    public void showData(List<TaskStatistics> mDatas) {
                        initPieChart(mDatas.get(0));
                    }
                });
    }

    private void initPieChart(TaskStatistics data) {
        if (data != null) {
            pieChart.getDescription().setEnabled(false);
            pieChart.setCenterText((data.getFinished() + data.getUnfinished() + data.getFinishedTimeout()) + "个");
            pieChart.setCenterTextColor(Color.BLACK);
            pieChart.setCenterTextSize(20);
            pieChart.setBackgroundColor(Color.WHITE);
            pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            pieChart.setEntryLabelColor(Color.WHITE);
            pieChart.setEntryLabelTextSize(12f);
            ArrayList<PieEntry> pieEntryList = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            if (data.getFinished() != 0){
                colors.add(Color.parseColor("#F70044"));
                PieEntry pieEntry1 = new PieEntry(data.getFinished(), "已完成");
                pieEntryList.add(pieEntry1);
            }
            if (data.getUnfinished() != 0){
                colors.add(Color.parseColor("#F6D600"));
                PieEntry pieEntry2 = new PieEntry(data.getUnfinished(), "未完成");
                pieEntryList.add(pieEntry2);

            }
            if (data.getFinishedTimeout() != 0){
                colors.add(Color.parseColor("#11CD86"));
                PieEntry pieEntry3 = new PieEntry(data.getFinishedTimeout(), "超时完成");
                pieEntryList.add(pieEntry3);
            }
            PieDataSet pieDataSet = new PieDataSet(pieEntryList, null);
            pieDataSet.setSliceSpace(3f);
            pieDataSet.setSelectionShift(10f);
            pieDataSet.setFormSize(20f);
            pieDataSet.setValueTextSize(20f);
            pieDataSet.setColors(colors);
            pieDataSet.setValueFormatter(new DefaultValueFormatter(0));
            PieData pieData = new PieData(pieDataSet);
            pieData.setDrawValues(true);
            pieData.setValueTextColor(Color.BLUE);
            pieData.setValueTextSize(20f);
            pieChart.setData(pieData);
            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    startActivity(new Intent(TaskOrganizationStatisticsActivity.this,
                            OrganizationTaskStatisticsActivity.class)
                            .putExtra("taskState", ((PieEntry) e).getLabel())
                            .putExtra("startTime", startTime.getText().toString())
                            .putExtra("endTime", endTime.getText().toString())
                            .putExtra("organizationId", organizationId));
                }

                @Override
                public void onNothingSelected() {

                }
            });
            pieChart.invalidate();
        } else {
            noData();
        }
    }

    private void noData() {
        pieChart.setNoDataText("无数据");
        pieChart.setNoDataTextColor(Color.BLACK);
    }
}
