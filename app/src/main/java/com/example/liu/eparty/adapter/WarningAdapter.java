package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Warning;

import java.util.List;


/**
 * 会议adapter
 */

public class WarningAdapter extends BaseAdapter<Warning> {

    public WarningAdapter(Context context, List<Warning> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Warning item) {
        ((TextView) holder.getView(R.id.item_warning_warning_times)).setText("第" + item.getTimes() + "次预警");
        ((TextView) holder.getView(R.id.item_warning_time)).setText(item.getCreateTime());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_warning;
    }
}
