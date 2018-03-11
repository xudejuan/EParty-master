package com.example.liu.eparty.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Member;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.SerializableSparseArray;

import java.util.List;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/7:17:58
 * introduction：
 */

public class MemberAdapter extends BaseAdapter<Member> {

    private SerializableSparseArray<String> sparseArray;
    private String type;

    public MemberAdapter(Context context, List<Member> mData, String type) {
        super(context, mData);
        this.type = type;
        //noinspection unchecked
        sparseArray = (SerializableSparseArray<String>) ACache.get(context, "sparseArray")
                .getAsObject("sparseArray");
    }

    @Override
    protected void setViewData(BaseViewHolder holder, final Member item) {
        ((TextView) holder.getView(R.id.item_member_name)).setText(item.getName());
        ((TextView) holder.getView(R.id.item_member_phone)).setText(item.getPhone());
        ImageView icon = (ImageView) holder.getView(R.id.item_member_identity);
        switch (item.getIdentity()) {
            case "书记":
                icon.setImageResource(R.mipmap.secretary);
                break;
            case "流动党员管理员":
                icon.setImageResource(R.mipmap.flow_administrator);
                break;
            case "管理员":
                icon.setImageResource(R.mipmap.public_administrator);
                break;
            case "普通党员":
                icon.setImageResource(R.mipmap.public_member);
                break;
        }
        CheckBox checkBox = (CheckBox) holder.getView(R.id.item_member_select);
        if (type.equals("show")) {
            checkBox.setVisibility(View.GONE);
        } else {
            if (sparseArray.get(item.getMemberId()) != null) {
                checkBox.setChecked(true);
            }
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sparseArray.put(item.getMemberId(), item.getName());
                    } else {
                        sparseArray.delete(item.getMemberId());
                    }
                }
            });
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_member;
    }

    public void allSelected() {
        for (Member member : mData) {
            if (sparseArray.get(member.getMemberId()) == null) {
                sparseArray.put(member.getMemberId(), member.getName());
            }
        }
        notifyDataSetChanged();
    }

    public void query(){
        ACache.get(context, "sparseArray").put("sparseArray", sparseArray);
    }
}
