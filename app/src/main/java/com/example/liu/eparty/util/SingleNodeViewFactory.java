package com.example.liu.eparty.util;

import android.view.View;

import com.example.liu.eparty.base.BaseNodeViewBinder;
import com.example.liu.eparty.base.BaseNodeViewFactory;


public class SingleNodeViewFactory extends BaseNodeViewFactory {

    private SingleNodeViewBinder.OnChooseListener onChooseListener;

    public SingleNodeViewFactory(SingleNodeViewBinder.OnChooseListener onChooseListener){
        this.onChooseListener = onChooseListener;
    }

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        return new SingleNodeViewBinder(view, level, onChooseListener);
    }
}
