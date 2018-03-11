package com.example.liu.eparty.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.liu.eparty.MyTreeView;
import com.example.liu.eparty.bean.Tree;
import com.example.liu.eparty.bean.TreeNode;

/**
 * 初始化树工具类（仅是少些重复代码）
 */

public class TreeUtil {

    public static void initShowTree(Tree tree, Activity activity, int containerId,
                                          ShowNodeViewBinder.OnOperateListener onOperateListener){
        ViewGroup viewGroup = (RelativeLayout) activity.findViewById(containerId);
        TreeNode root = TreeNode.root();
        TreeNode treeNode = new TreeNode(tree.getText());
        treeNode.setId(tree.getId());
        treeNode.setLevel(0);
        buildTree(treeNode, tree, 1);
        root.addChild(treeNode);
        MyTreeView treeView;
        treeView = new MyTreeView(root, activity, new ShowNodeViewFactory(onOperateListener));
        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(view);
    }

    public static void initMultiTree(Tree tree, Activity activity, int containerId,
                                           MultiNodeViewBinder.OnChooseListener onChooseListener){
        ViewGroup viewGroup = (RelativeLayout) activity.findViewById(containerId);
        TreeNode root = TreeNode.root();
        TreeNode treeNode = new TreeNode(tree.getText());
        treeNode.setId(tree.getId());
        treeNode.setLevel(0);
        buildTree(treeNode, tree, 1);
        root.addChild(treeNode);
        MyTreeView treeView;
        treeView = new MyTreeView(root, activity, new MultiNodeViewFactory(onChooseListener));
        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(view);
    }

    public static void initSingleTree(Tree tree, Activity activity, int containerId,
                                           SingleNodeViewBinder.OnChooseListener onChooseListener){
        ViewGroup viewGroup = (RelativeLayout) activity.findViewById(containerId);
        TreeNode root = TreeNode.root();
        TreeNode treeNode = new TreeNode(tree.getText());
        treeNode.setId(tree.getId());
        treeNode.setLevel(0);
        buildTree(treeNode, tree, 1);
        root.addChild(treeNode);
        MyTreeView treeView;
        treeView = new MyTreeView(root, activity, new SingleNodeViewFactory(onChooseListener));
        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(view);
    }

    private static void buildTree(TreeNode treeNode, Tree tree, int level) {
        if (tree.getChildren() != null) {
            for (int i = 0; i < tree.getChildren().size(); i++) {
                TreeNode node = new TreeNode(tree.getChildren().get(i).getText());
                node.setId(tree.getChildren().get(i).getId());
                node.setLevel(level);
                buildTree(node, tree.getChildren().get(i), level + 1);
                treeNode.addChild(node);
            }
        }
    }
}
