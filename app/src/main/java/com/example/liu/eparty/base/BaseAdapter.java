package com.example.liu.eparty.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Iterator;
import java.util.List;

/**
 * Adapter封装
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected Context context;
    protected List<T> mData;

    public BaseAdapter(){}

    public BaseAdapter(Context context, List<T> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(setLayoutId(), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        setViewData(holder, mData.get(position));
    }

    /*
    * 绑定item中的weight
    */
    protected abstract void setViewData(BaseViewHolder holder, T item);

    protected abstract int setLayoutId();

    /*
    * 获取数据
    */
    public List<T> getData() {
        return mData;
    }

    /*
    * 追加数据
    */
    public void add(List<T> list) {
        mData.addAll(list);
        notifyItemRangeChanged(0, list.size());
    }

    /*
    * 更新列表数据
    */
    public void refreshData(List<T> list) {
        if (list != null && list.size() > 0) {
            clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                mData.add(i, list.get(i));
                notifyItemInserted(i);
            }
        }else {
            clear();
        }
    }

    /*
    * 清除列表数据
    */
    @SuppressWarnings("unchecked")
    private void clear() {
        for (Iterator it = mData.iterator(); it.hasNext(); ) {
            T t = (T) it.next();
            int position = mData.indexOf(t);
            it.remove();
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected static class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(View itemView) {
            super(itemView);
        }

        public View getView(int id) {
            return itemView.findViewById(id);
        }
    }
}
