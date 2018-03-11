package com.example.liu.eparty.base;

import android.view.View;

/**
 * 树节点工厂封装（参考github上的treeView）
 */

public abstract class BaseNodeViewFactory {
    public abstract BaseNodeViewBinder getNodeViewBinder(View view, int level);
}
