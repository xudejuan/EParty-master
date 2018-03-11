package com.example.liu.eparty.util;

import android.view.View;

import com.example.liu.eparty.base.BaseNodeViewBinder;
import com.example.liu.eparty.bean.TreeNode;


/**
 * （参考github上的treeView）
 */

public abstract class CheckableNodeViewBinder extends BaseNodeViewBinder {

    public CheckableNodeViewBinder(View itemView) {
        super(itemView);
    }

    /**
     * Get the checkable view id. MUST BE A CheckBox CLASS！
     *
     * @return
     */
    public abstract int getCheckableViewId();

    /**
     * Do something when a node select or deselect（only triggered by clicked）
     *
     * @param treeNode
     * @param selected
     */
    public void onNodeSelectedChanged(TreeNode treeNode, boolean selected) {
        /*empty*/
    }
}