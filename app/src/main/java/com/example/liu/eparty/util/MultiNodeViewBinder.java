package com.example.liu.eparty.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.bean.TreeNode;

/**
 * 自定义多选组织展示树
 */

public class MultiNodeViewBinder extends CheckableNodeViewBinder {

    private TextView textView;
    private ImageView imageView;
    private int level;
    private OnChooseListener onChooseListener;

    public MultiNodeViewBinder(View itemView, int level, OnChooseListener onChooseListener) {
        super(itemView);
        textView = itemView.findViewById(R.id.node);
        imageView = itemView.findViewById(R.id.arrow_img);
        this.level = level;
        this.onChooseListener = onChooseListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_level_multi;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
        imageView.setPadding(35 * level, 0, 0, 0);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        onChooseListener.onChoose(treeNode, selected);
    }

    public interface OnChooseListener{
        void onChoose(TreeNode treeNode, boolean selected);
    }
}
