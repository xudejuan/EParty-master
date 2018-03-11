package com.example.liu.eparty.base;

import java.util.List;

/**
 * 展示数据或错误信息
 */

public interface BaseView<T> {
    void showData(int requestId, List<T> mDatas);
}
