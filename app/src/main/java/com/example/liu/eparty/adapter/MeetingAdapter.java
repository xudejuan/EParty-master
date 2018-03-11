package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Meeting;

import java.util.List;


/**
 * 会议adapter
 */

public class MeetingAdapter extends BaseAdapter<Meeting> {

    public MeetingAdapter(Context context, List<Meeting> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Meeting item) {
        ((TextView) holder.getView(R.id.item_meeting_title)).setText(item.getMeetingTitle());
        ((TextView) holder.getView(R.id.item_meeting_time)).setText(item.getAStartTime());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_meeting;
    }
}
