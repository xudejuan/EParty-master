package com.example.liu.eparty.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseNodeViewBinder;
import com.example.liu.eparty.bean.TreeNode;

/**
 * 自定义选择展示树
 */

public class ShowNodeViewBinder extends BaseNodeViewBinder {

    private TextView textView;
    private TextView operate;
    private ImageView arrowImage;
    private ImageView treeImage;
    private int level;
    private OnOperateListener mOnOperateListener;

    public ShowNodeViewBinder(View itemView, int level, OnOperateListener onOperateListener) {
        super(itemView);
        this.mOnOperateListener = onOperateListener;
        textView = itemView.findViewById(R.id.node);
        arrowImage = itemView.findViewById(R.id.arrow_img);
        treeImage = itemView.findViewById(R.id.image_tree);
        operate = itemView.findViewById(R.id.item_level_operate);
        this.level = level;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_level_show;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
        arrowImage.setPadding(30 * level, 0, 0, 0);
        if (treeNode.hasChild()){
            treeImage.setImageResource(R.mipmap.folder);
        }else {
            treeImage.setImageResource(R.mipmap.file);
        }
        operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnOperateListener.showDialog(treeNode);
            }
        });
    }


    public interface OnOperateListener {
        void showDialog(TreeNode treeNode);
    }
}
