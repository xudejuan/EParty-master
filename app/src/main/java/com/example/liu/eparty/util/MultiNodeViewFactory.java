package com.example.liu.eparty.util;

import android.view.View;

import com.example.liu.eparty.base.BaseNodeViewBinder;
import com.example.liu.eparty.base.BaseNodeViewFactory;


public class MultiNodeViewFactory extends BaseNodeViewFactory {

    private MultiNodeViewBinder.OnChooseListener onChooseListener;

    public MultiNodeViewFactory(MultiNodeViewBinder.OnChooseListener onChooseListener){
        this.onChooseListener = onChooseListener;
    }

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        return new MultiNodeViewBinder(view, level, onChooseListener);
    }
}
