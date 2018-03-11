package com.example.liu.eparty.util;

import android.view.View;

import com.example.liu.eparty.base.BaseNodeViewBinder;
import com.example.liu.eparty.base.BaseNodeViewFactory;

/**
 * 自定义选择展示树工厂
 */

public class ShowNodeViewFactory extends BaseNodeViewFactory {

    private ShowNodeViewBinder.OnOperateListener mOnOperateListener;

    public ShowNodeViewFactory(ShowNodeViewBinder.OnOperateListener onOperateListener){
        this.mOnOperateListener = onOperateListener;
    }

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        return new ShowNodeViewBinder(view, level, mOnOperateListener);
    }
}
