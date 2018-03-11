package com.example.liu.eparty.bean;

import java.util.List;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2017/12/29:15:27
 * introduction：
 */

public class Info<T> {

    private int status;
    private String message;
    private int total;
    private List<T> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
