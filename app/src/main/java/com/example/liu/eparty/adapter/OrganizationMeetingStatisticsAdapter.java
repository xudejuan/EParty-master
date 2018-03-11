package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Meeting;

import java.util.List;

/**
 * 评论adapter
 */

public class OrganizationMeetingStatisticsAdapter extends BaseAdapter<Meeting> {

    public OrganizationMeetingStatisticsAdapter(Context context, List<Meeting> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Meeting item) {
        ((TextView) holder.getView(R.id.item_is_meeting_title)).setText(item.getMeetingTitle());
        ((TextView) holder.getView(R.id.item_is_meeting_time)).setText(item.getAStartTime());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_organization_meeting_statistics;
    }
}