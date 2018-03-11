package com.example.liu.eparty.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Warning;

import java.util.List;


/**
 * 会议adapter
 */

public class HandleAdapter extends BaseAdapter<Warning> {

    public HandleAdapter(Context context, List<Warning> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, final Warning item) {
        ((TextView) holder.getView(R.id.item_handle_name)).setText(item.getWarnOrgName());
        ((TextView) holder.getView(R.id.item_handle_organization)).setText(item.getOrgName());
        ImageView icon = (ImageView) holder.getView(R.id.item_handle_icon);
        TextView way = (TextView) holder.getView(R.id.item_handle_way);
        if (item.getHandleStatus() == 0){
            icon.setImageResource(R.mipmap.unhandled);
            way.setVisibility(View.GONE);
        }else {
            icon.setImageResource(R.mipmap.handled);
            way.setVisibility(View.VISIBLE);
            way.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(item.getHandleWay());
                }
            });
        }
    }

    private void showDialog(String handleWay) {
        new MaterialDialog.Builder(context)
                .title("预警处理方式")
                .content(handleWay)
                .show();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_handle;
    }
}
