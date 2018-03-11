package com.example.liu.eparty.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.bean.TreeNode;

/**
 * 自定义单选组织展示树
 */

public class SingleNodeViewBinder extends CheckableNodeViewBinder {

    private TextView textView;
    private TextView choose;
    private ImageView imageView;
    private int level;
    private OnChooseListener onChooseListener;

    public SingleNodeViewBinder(View itemView, int level, OnChooseListener onChooseListener) {
        super(itemView);
        textView = itemView.findViewById(R.id.node);
        choose = itemView.findViewById(R.id.item_level_single_choose);
        imageView = itemView.findViewById(R.id.arrow_img);
        this.level = level;
        this.onChooseListener = onChooseListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_level_single;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
        imageView.setPadding(35 * level, 0, 0, 0);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChooseListener.onChoose(treeNode);
            }
        });
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    public interface OnChooseListener{
        void onChoose(TreeNode treeNode);
    }
}
