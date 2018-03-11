package com.example.liu.eparty.activity.statistics;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.MeetingStatistics;
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

public class MeetingIndividualStatisticsActivity extends BaseActivity {

    @BindView(R.id.individual_statistics_pieChart)
    PieChart pieChart;
    @BindView(R.id.tv_individual_statistics_start_time)
    TextView startTime;
    @BindView(R.id.tv_individual_statistics_end_time)
    TextView endTime;
    @BindView(R.id.tv_individual_statistics_participate)
    TextView participate;

    @Override
    protected String setTitle() {
        return "个人统计";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_individual_statistics;
    }

    @Override
    protected void init() {
        noData();
    }

    @OnClick(R.id.ll_individual_statistics_start_time)
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

    @OnClick(R.id.ll_individual_statistics_end_time)
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

    @OnClick(R.id.ll_individual_statistics_participate)
    public void chooseParticipateState(){
        final List<String> states = new ArrayList<>();
        states.add("未参与");
        states.add("已参与");
        new MaterialDialog.Builder(this)
                .items(states)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        participate.setText(states.get(position));
                        dialog.dismiss();
                        check();
                    }
                })
                .show();
    }

    public void check() {
        if (!startTime.getText().toString().equals("请选择")
                && !endTime.getText().toString().equals("请选择")
                && !participate.getText().toString().equals("请选择")){
            requestData();
        }
    }

    private void requestData(){
        int state;
        if (participate.getText().toString().equals("未参与")){
            state = 0;
        }else {
            state = 1;
        }
        MyOkHttpUtil.get("")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("startTime", startTime.getText().toString())
                .addParams("endTime", endTime.getText().toString())
                .addParams("state", String.valueOf(state))
                .build()
                .execute(new DataCallback<MeetingStatistics>(this){
                    @Override
                    public void showData(List<MeetingStatistics> mDatas) {
                        initPieChart(mDatas.get(0));
                    }
                });
    }

    private void initPieChart(MeetingStatistics data) {
        if (data != null) {
            pieChart.setVisibility(View.VISIBLE);
            pieChart.getDescription().setEnabled(false);
            pieChart.setCenterText((data.getType1() + data.getType2() + data.getType3() + data.getType4()) + "次");
            pieChart.setCenterTextColor(Color.BLACK);
            pieChart.setCenterTextSize(20);
            pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            pieChart.setEntryLabelColor(Color.WHITE);
            pieChart.setEntryLabelTextSize(12f);
            ArrayList<PieEntry> pieEntryList = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            if (data.getType1() != 0){
                colors.add(Color.parseColor("#F70044"));
                PieEntry pieEntry1 = new PieEntry(data.getType1(), "党员大会");
                pieEntryList.add(pieEntry1);
            }
            if (data.getType2() != 0){
                colors.add(Color.parseColor("#F6D600"));
                PieEntry pieEntry2 = new PieEntry(data.getType2(), "党代会");
                pieEntryList.add(pieEntry2);

            }
            if (data.getType3() != 0){
                colors.add(Color.parseColor("#11CD86"));
                PieEntry pieEntry3 = new PieEntry(data.getType3(), "民主生活会");
                pieEntryList.add(pieEntry3);
            }
            if (data.getType4() != 0){
                colors.add(Color.parseColor("#066FA5"));
                PieEntry pieEntry4 = new PieEntry(data.getType3(), "党课");
                pieEntryList.add(pieEntry4);
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
                    startActivity(new Intent(MeetingIndividualStatisticsActivity.this,
                            IndividualMeetingStatisticsActivity.class)
                            .putExtra("type", ((PieEntry) e).getLabel())
                            .putExtra("startTime", startTime.getText().toString())
                            .putExtra("endTime", endTime.getText().toString())
                            .putExtra("participate", participate.getText().toString()));
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
