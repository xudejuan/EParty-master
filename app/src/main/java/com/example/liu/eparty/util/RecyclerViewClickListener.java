package com.example.liu.eparty.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector mGestureDetector;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongCLickListener;

    //内部接口，定义点击方法
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //内部接口，定义长按方法
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public RecyclerViewClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener){
        mClickListener = listener;
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener(){
                    //单击事件
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(childView != null && mClickListener != null){
                            mClickListener.onItemClick(childView,recyclerView.getChildLayoutPosition(childView));
                            return true;
                        }
                        return false;
                    }
                });
    }

    public RecyclerViewClickListener(Context context, final RecyclerView recyclerView, OnItemLongClickListener listener){
        mLongCLickListener = listener;
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener(){
                    //长按事件
                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(childView != null && mLongCLickListener != null){
                            mLongCLickListener.onItemLongClick(childView,recyclerView.getChildLayoutPosition(childView));
                        }
                    }
                });
    }

    public RecyclerViewClickListener(Context context, final RecyclerView recyclerView,
                                     final OnItemClickListener onItemClickListener,
                                     final OnItemLongClickListener onItemLongClickListener){
        mClickListener = onItemClickListener;
        mLongCLickListener = onItemLongClickListener;
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener(){
                    //单击事件
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(childView != null && mClickListener != null){
                            mClickListener.onItemClick(childView,recyclerView.getChildLayoutPosition(childView));
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        if(childView != null && mLongCLickListener != null){
                            mLongCLickListener.onItemLongClick(childView,recyclerView.getChildLayoutPosition(childView));
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        //把事件交给GestureDetector处理
        return mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}