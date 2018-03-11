package com.example.liu.eparty.bean;

import java.io.Serializable;
/**
 * 记录
 */

public class Record implements Serializable {

    /**
     * file : http://localhost:8080/zhdj/upload/files/meeting/李婷2016年5月11日.docx
     * img : http://localhost:8080/zhdj/upload/files/meeting/1.1.1首页@3x.png
     * userName : null
     * uploadtime : null
     * uploadTime : null
     */

    private String file;
    private String img;
    private String userName;
    private String uploadTime;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}