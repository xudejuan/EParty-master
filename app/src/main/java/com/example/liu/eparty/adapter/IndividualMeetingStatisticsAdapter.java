package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.IndividualMeetingStatistics;

import java.util.List;

/**
 * 评论adapter
 */

public class IndividualMeetingStatisticsAdapter extends BaseAdapter<IndividualMeetingStatistics> {

    public IndividualMeetingStatisticsAdapter(Context context, List<IndividualMeetingStatistics> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, IndividualMeetingStatistics item) {
        ((TextView) holder.getView(R.id.item_is_meeting_title)).setText(item.getTitle());
        ((TextView) holder.getView(R.id.item_is_meeting_time)).setText(item.getTime());
        TextView state = ((TextView) holder.getView(R.id.item_is_meeting_sign_in_state));
        switch (item.getSignInState()){
            case 0:
                state.setText("未参与");
                state.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case 1:
                state.setText("迟到");
                state.setTextColor(context.getResources().getColor(R.color.yellow));
                break;
            case 2:
                state.setText("准时到达");
                state.setTextColor(context.getResources().getColor(R.color.green_deep));
                break;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_individual_meeting_statistics;
    }
}