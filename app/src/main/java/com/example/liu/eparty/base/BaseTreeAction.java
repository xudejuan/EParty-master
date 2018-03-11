package com.example.liu.eparty.base;


import com.example.liu.eparty.bean.TreeNode;

import java.util.List;

/**
 * 操作树封装（参考github上的treeView）
 */

public interface  BaseTreeAction {
    void expandAll();

    void expandNode(TreeNode treeNode);

    void expandLevel(int level);

    void collapseAll();

    void collapseNode(TreeNode treeNode);

    void collapseLevel(int level);

    void toggleNode(TreeNode treeNode);

    void deleteNode(TreeNode node);

    void addNode(TreeNode parent, TreeNode treeNode);

    List<TreeNode> getAllNodes();
}
