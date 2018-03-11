package com.example.liu.eparty.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseAdapter;
import com.example.liu.eparty.bean.Comment;

import java.util.List;

/**
 * 评论adapter
 */

public class CommentAdapter extends BaseAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> mData) {
        super(context, mData);
    }

    @Override
    protected void setViewData(BaseViewHolder holder, Comment item) {
        ((TextView) holder.getView(R.id.item_comment_username)).setText(item.getCommentator());
        ((TextView) holder.getView(R.id.item_comment_content)).setText(item.getCommentcontent());
        ((TextView) holder.getView(R.id.item_comment_time)).setText(item.getCommenttime());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.item_comment;
    }
}