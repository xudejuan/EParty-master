package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Report;

import java.util.List;


/**
 * 查看任务adapter
 */

public class ReportAdapter extends BaseAdapter<Report> {

    public ReportAdapter(Context context, List<Report> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Report item) {
        ((TextView) holder.getView(R.id.item_report_name)).setText(item.getSubmitName());
        ((TextView) holder.getView(R.id.item_report_organization)).setText(item.getSubmitFinishName());
        TextView state = ((TextView) holder.getView(R.id.item_report_organization));
        if (item.getFinishReadOk().equals("null")){
            state.setText("未审核");
        }else if(item.getFinishReadOk().equals("未通过")){
            state.setText("未通过");
            state.setTextColor(context.getResources().getColor(R.color.red));
        }else if (item.getFinishReadOk().equals("已通过")){
            state.setText("已通过");
            state.setTextColor(context.getResources().getColor(R.color.green_deep));
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_report;
    }
}
