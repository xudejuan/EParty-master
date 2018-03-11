package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Task;

import java.util.List;


/**
 * 查看任务adapter
 */

public class TaskAdapter extends BaseAdapter<Task> {

    private int type;//0为组织任务，1为私人任务，2为下达任务，3为任务统计

    public TaskAdapter(Context context, List<Task> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Task item) {
        ((TextView) holder.getView(R.id.item_task_title)).setText(item.getTitle());
        ((TextView) holder.getView(R.id.item_task_time)).setText(item.getCreateTime());
        ImageView icon = (ImageView) holder.getView(R.id.item_task_icon);
        switch (type){
            case 0:
                icon.setImageResource(R.mipmap.individual_task);
                break;
            case 1:
                icon.setImageResource(R.mipmap.organization_task);
                break;
            case 2:
                icon.setImageResource(R.mipmap.issue_task);
                break;
            case 3:
                icon.setImageResource(R.mipmap.task2);
                break;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_task;
    }

    public void setType(int type){
        this.type = type;
        notifyDataSetChanged();
    }
}
