package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Place;

import java.util.List;

/**
 * 评论adapter
 */

public class PlaceAdapter extends BaseAdapter<Place> {

    public PlaceAdapter(Context context, List<Place> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Place item) {
        if (item.getName() != null) {
            ((TextView) holder.getView(R.id.item_place_name)).setText(item.getName());
        }
        if (item.getLocation() != null) {
            ((TextView) holder.getView(R.id.item_place_location)).setText(item.getLocation());
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_place;
    }
}