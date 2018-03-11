package com.example.liu.eparty.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.OrganizationParticipateStatistics;

import java.util.List;

/**
 * 评论adapter
 */

public class OrganizationParticipateStatisticsAdapter extends BaseAdapter<OrganizationParticipateStatistics> {

    private int type;

    public OrganizationParticipateStatisticsAdapter(Context context, List<OrganizationParticipateStatistics> mData, int type) {
        super(context, mData);
        this.type = type;
    }

    @Override
    protected void setViewData(BaseViewHolder holder, OrganizationParticipateStatistics item) {
        ((TextView) holder.getView(R.id.item_op_statistics_name)).setText(item.getName());
        ((TextView) holder.getView(R.id.item_op_statistics_phone)).setText(item.getPhone());
        TextView state = (TextView) holder.getView(R.id.item_op_statistics_state);
        if (type == 0){
            state.setVisibility(View.VISIBLE);
        }else {
            if (item.getSignInState().equals("1")){
                state.setText("迟到");
                state.setTextColor(context.getResources().getColor(R.color.yellow));
            }else {
                state.setText("准时到达");
                state.setTextColor(context.getResources().getColor(R.color.green_deep));
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_organization_participate_statistics;
    }
}