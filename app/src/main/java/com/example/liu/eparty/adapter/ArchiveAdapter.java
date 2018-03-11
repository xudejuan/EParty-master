package com.example.liu.eparty.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Task;
import com.example.liu.eparty.util.MyOkHttpUtil;

import java.util.List;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/3/5:21:34
 * introduction：
 */

public class ArchiveAdapter extends BaseAdapter<Task>{

    public ArchiveAdapter(Context context, List<Task> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, final Task item) {
        ((TextView) holder.getView(R.id.item_archive_task_title)).setText(item.getTitle());
        holder.getView(R.id.item_archive_export).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOkHttpUtil.get("PerReceiveTest")
                        .addParams("taskId", String.valueOf(item.getTaskId()))
                        .build();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_archive;
    }
}
