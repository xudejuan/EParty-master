package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Member;

import java.util.List;

public class FlowMemberAdapter extends BaseAdapter<Member>{

    public FlowMemberAdapter(Context context, List mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Member item) {
        ((TextView) holder.getView(R.id.item_flow_member_name)).setText(item.getName());
        ((TextView) holder.getView(R.id.item_flow_member_phone)).setText(item.getPhone());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_flow_member;
    }
}
