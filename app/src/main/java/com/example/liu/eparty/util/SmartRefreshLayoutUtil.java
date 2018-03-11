package com.example.liu.eparty.util;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/*
 * 下拉刷新，上拉加载工具类（仅仅是少写重复打码）
 */
public class SmartRefreshLayoutUtil {

    public static final int REFRESH = 1;
    public static final int LOAD_MORE = 2;
    public static int state = REFRESH;
    private static int currentPage = 1;
    private static int totalPage;

    private static OnRequestDataListener onRequestDataListener;

    public static void init(SmartRefreshLayout smartRefreshLayout){
        currentPage = 1;
        state = REFRESH;
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
                refreshlayout.finishRefresh();
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage <= totalPage) {
                    loadMoreData();
                }
                refreshlayout.finishLoadmore();
            }
        });
    }

    public static void setTotalPage(int totalPage) {
        SmartRefreshLayoutUtil.totalPage = totalPage;
    }

    public static void setOnRequestDataListener(OnRequestDataListener onRequestDataListener) {
        SmartRefreshLayoutUtil.onRequestDataListener = onRequestDataListener;
    }

    private static void refreshData() {
        currentPage = 1;
        state = REFRESH;
        onRequestDataListener.requestDatas(currentPage);
    }

    private static void loadMoreData() {
        currentPage++;
        state = LOAD_MORE;
        onRequestDataListener.requestDatas(currentPage);
    }

    public interface OnRequestDataListener{
        void requestDatas(int page);
    }
}
