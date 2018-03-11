package com.example.liu.eparty.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.liu.eparty.R;


/**
 *  日期时间选择器
 */

public class DateTimePicker {

    public static void choose(Context context, final TextView textView){
        int year = DateUtil.getYear();
        int month = DateUtil.getMonth();
        int day = DateUtil.getDay();
        int hour = DateUtil.getHour();
        int minute = DateUtil.getMinute();
        final TimePickerDialog chooseMonth = new TimePickerDialog(context, R.style.DateTimeTheme,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                /*
                *  时间标准化
                */
                if (i < 10){
                    textView.append("0" +i);
                }else {
                    textView.append(""+i);
                }
                if (i1 < 10){
                    textView.append(":0" + i1);
                }else {
                    textView.append(":" + i1);
                }
                textView.append(":00");
            }
        }, hour, minute, true);
        new DatePickerDialog(context, R.style.DateTimeTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                /*
                *  日期标准化
                */

                textView.setText("" + i);
                if (i1 < 10){
                    textView.append("-0" + i1);
                }else {
                    textView.append("-" + i1);
                }
                if (i2 < 10){
                    textView.append("-0" + i2 + " ");
                }else {
                    textView.append("-" + i2 + " ");
                }
                chooseMonth.show();
            }
        }, year, month - 1, day).show();
    }
}
