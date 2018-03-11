package com.example.liu.eparty.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liu.eparty.R;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/24:10:56
 * introduction：
 */

public class TipDialogUtil {

    private Dialog dialog;

    public void showLoading(Context context, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = v.findViewById(R.id.dialog_loading_view);// 加载布局
        TextView textView = v.findViewById(R.id.tipTextView);
        textView.setText(message);
        dialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        dialog.setCancelable(false); // 是否可以按“返回键”消失
        dialog.setCanceledOnTouchOutside(false); //点击加载框以外的区域
        dialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
        dialog.show();
    }

    public void dismiss(){
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
