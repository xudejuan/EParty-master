package com.example.liu.eparty.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.youth.picker.PickerView;
import com.youth.picker.entity.PickerData;
import com.youth.picker.listener.OnPickerClickListener;

import java.util.HashMap;
import java.util.Map;

public class YearMonthPicker {

    private PickerView pickerView;

    public void choose(final Activity activity, final TextView textView, final OnConfirmListener confirmListener) {
        PickerData data=new PickerData();
        data.setPickerTitleName("选择日期");
        Map<String, String[]> map = new HashMap<>();
        int currentYear = DateUtil.getYear();
        String[] years = new String[currentYear - 2018 +1];
        String[] months = new String[]{"1月","2月","3月","4月","5月","6月","7月", "8月","9月","10月","11月","12月"};
        for (int i=2018;i<=currentYear;i++){
            years[i-2018] = "" + i + "年";
            map.put(years[i-2018], months);
        }
        data.setFirstDatas(years);
        data.setSecondDatas(map);
        //设置弹出框的高度
        data.setHeight(1000);
        data.setInitSelectText("年份","月份");
        //初始化选择器
        pickerView = new PickerView(activity, data);

        //选择器点击回调
        pickerView.setOnPickerClickListener(new OnPickerClickListener() {
            //选择列表时触发的事件（手动关闭）
            @Override
            public void OnPickerClick(PickerData pickerData) {

            }
            //点击确定按钮触发的事件（自动关闭）
            @Override
            public void OnPickerConfirmClick(PickerData pickerData) {
                if (pickerData.getFirstText().equals("年份")){
                    textView.setText("请选择");
                    return ;
                }
                if (pickerData.getSecondText().equals("")){
                    textView.setText("请选择");
                    return;
                }
                String date = pickerData.getFirstText() + pickerData.getSecondText();
                textView.setText(date);
                confirmListener.onConfirm();
            }
        });
    }

    public void show(View view){
        if (!pickerView.isShowing()) {
            pickerView.show(view);
        }
    }

    public interface OnConfirmListener{
        void onConfirm();
    }
}
