package com.example.liu.eparty;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.example.liu.eparty.adapter.TreeViewAdapter;
import com.example.liu.eparty.animator.TreeItemAnimator;
import com.example.liu.eparty.base.BaseNodeViewFactory;
import com.example.liu.eparty.bean.TreeNode;
import com.example.liu.eparty.util.SelectableTreeAction;
import com.example.liu.eparty.util.TreeHelper;

import java.util.List;

/**
 * （参考github上的treeView）
 */

public class MyTreeView implements SelectableTreeAction {
    private TreeNode root;

    private Context context;

    private BaseNodeViewFactory baseNodeViewFactory;

    private RecyclerView rootView;

    private TreeViewAdapter adapter;

    private boolean itemSelectable = true;

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.itemAnimator = itemAnimator;
        if (rootView != null && itemAnimator != null) {
            rootView.setItemAnimator(itemAnimator);
        }
    }

    private RecyclerView.ItemAnimator itemAnimator;

    public MyTreeView(@NonNull TreeNode root, @NonNull Context context, @NonNull BaseNodeViewFactory baseNodeViewFactory) {
        this.root = root;
        this.context = context;
        this.baseNodeViewFactory = baseNodeViewFactory;
        if (baseNodeViewFactory == null) {
            throw new IllegalArgumentException("You must assign a BaseNodeViewFactory!");
        }
    }

    public View getView() {
        if (rootView == null) {
            this.rootView = buildRootView();
        }

        return rootView;
    }

    @NonNull
    private RecyclerView buildRootView() {
        RecyclerView recyclerView = new RecyclerView(context);
        /**
         * disable multi touch event to prevent terrible data set error when calculate list.
         */
        recyclerView.setMotionEventSplittingEnabled(false);

        recyclerView.setItemAnimator(itemAnimator != null ? itemAnimator : new TreeItemAnimator());
        SimpleItemAnimator itemAnimator = (SimpleItemAnimator) recyclerView.getItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TreeViewAdapter(context, root, baseNodeViewFactory);
        adapter.setTreeView(this);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void expandAll() {
        if (root == null) {
            return;
        }
        TreeHelper.expandAll(root);

        refreshTreeView();
    }


    public void refreshTreeView() {
        if (rootView != null) {
            ((TreeViewAdapter) rootView.getAdapter()).refreshView();
        }
    }

    @Override
    public void expandNode(TreeNode treeNode) {
        adapter.expandNode(treeNode);
    }

    @Override
    public void expandLevel(int level) {
        TreeHelper.expandLevel(root, level);

        refreshTreeView();
    }

    @Override
    public void collapseAll() {
        if (root == null) {
            return;
        }
        TreeHelper.collapseAll(root);

        refreshTreeView();
    }

    @Override
    public void collapseNode(TreeNode treeNode) {
        adapter.collapseNode(treeNode);
    }

    @Override
    public void collapseLevel(int level) {
        TreeHelper.collapseLevel(root, level);

        refreshTreeView();
    }

    @Override
    public void toggleNode(TreeNode treeNode) {
        if (treeNode.isExpanded()) {
            collapseNode(treeNode);
        } else {
            expandNode(treeNode);
        }
    }

    @Override
    public void deleteNode(TreeNode node) {
        adapter.deleteNode(node);
    }

    @Override
    public void addNode(TreeNode parent, TreeNode treeNode) {
        parent.addChild(treeNode);

        refreshTreeView();
    }

    @Override
    public List<TreeNode> getAllNodes() {
        return TreeHelper.getAllNodes(root);
    }

    @Override
    public void selectNode(TreeNode treeNode) {
        if (treeNode != null) {
            adapter.selectNode(true, treeNode);
        }
    }

    @Override
    public void deselectNode(TreeNode treeNode) {
        if (treeNode != null) {
            adapter.selectNode(false, treeNode);
        }
    }

    @Override
    public void selectAll() {
        TreeHelper.selectNodeAndChild(root, true);

        refreshTreeView();
    }

    @Override
    public void deselectAll() {
        TreeHelper.selectNodeAndChild(root, false);

        refreshTreeView();
    }

    @Override
    public List<TreeNode> getSelectedNodes() {
        return TreeHelper.getSelectedNodes(root);
    }

    public boolean isItemSelectable() {
        return itemSelectable;
    }

    public void setItemSelectable(boolean itemSelectable) {
        this.itemSelectable = itemSelectable;
    }

}
