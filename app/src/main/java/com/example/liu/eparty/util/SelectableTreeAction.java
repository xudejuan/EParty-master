package com.example.liu.eparty.util;


import com.example.liu.eparty.base.BaseTreeAction;
import com.example.liu.eparty.bean.TreeNode;

import java.util.List;

/**
 * （参考github上的treeView）
 */

public interface SelectableTreeAction extends BaseTreeAction {
    void selectNode(TreeNode treeNode);

    void deselectNode(TreeNode treeNode);

    void selectAll();

    void deselectAll();

    List<TreeNode> getSelectedNodes();
}
