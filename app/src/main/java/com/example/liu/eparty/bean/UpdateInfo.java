package com.example.liu.eparty.bean;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2017/12/28:20:53
 * introduction：
 */

public class UpdateInfo {

    /**
     * data : {"appId":1,"appName":"智慧党建","appSize":"3.03","appVersion":"1.0","updateUrl":"http://192.168.1.106:8080/zhdj/News/listUI/pic/app-debug.apk","updateInfo":"信息"}
     * state : 1
     * message : 成功
     */

    private Update data;
    private int state;
    private String message;

    public Update getData() {
        return data;
    }

    public void setData(Update data) {
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
