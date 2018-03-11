package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Plan;

import java.util.List;


/**
 * 查看计划adapter
 */

public class PlanAdapter extends BaseAdapter<Plan> {

    public PlanAdapter(Context context, List<Plan> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Plan item) {
        ((TextView) holder.getView(R.id.item_plan_name)).setText(item.getSubmitName());
        ((TextView) holder.getView(R.id.item_plan_organization)).setText(item.getSubmitTreeName());
        TextView state = ((TextView) holder.getView(R.id.item_plan_state));
        if (item.getCheckStatus() == null){
            state.setText("未审核");
        }else if(item.getCheckStatus().toString().equals("未通过")){
            state.setText("未通过");
            state.setTextColor(context.getResources().getColor(R.color.red));
        }else if (item.getCheckStatus().toString().equals("已通过")){
            state.setText("已通过");
            state.setTextColor(context.getResources().getColor(R.color.green_deep));
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_plan;
    }
}
