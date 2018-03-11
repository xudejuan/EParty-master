package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Task;

import java.util.List;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/7:17:58
 * introduction：
 */

public class RecipientAdapter extends BaseAdapter<Task> {

    public RecipientAdapter(Context context, List<Task> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, final Task item) {
        ((TextView) holder.getView(R.id.item_issue_task_recipient_name)).setText(item.getRecipient());
        ((TextView) holder.getView(R.id.item_issue_task_recipient_organization)).setText(item.getRecipientOrganization());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_recipient;
    }
}
