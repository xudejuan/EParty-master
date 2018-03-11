package com.example.liu.eparty.bean;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2017/12/29:15:27
 * introduction：
 */

public class Info2<T> {

    private int status;
    private String message;
    private T data;

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


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
