package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Record;

import java.util.List;

/**
 * 记录adapter
 */

public class RecordAdapter extends BaseAdapter<Record> {

    public RecordAdapter(Context context, List<Record> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Record item) {
        ((TextView) holder.getView(R.id.item_record_username)).setText(item.getUserName());
        ((TextView) holder.getView(R.id.item_record_time)).setText(item.getUploadTime());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_record;
    }
}
