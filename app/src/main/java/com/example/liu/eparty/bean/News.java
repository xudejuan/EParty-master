package com.example.liu.eparty.bean;

import java.io.Serializable;

public class News implements Serializable {
    /**
     * newId : 1
     * newTitle : 发啊
     * newHref : https://item.btime.com/37vinbof5jk958b6558gvbbht82?from=gjl
     * newsDate : 2017-11-22 20:52
     * imgs : C:/ZZDJ/news/image/news/20171122205215.jpg
     */

    private int newId;
    private String newTitle;
    private String newHref;
    private String newsDate;
    private String imgs;

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewHref() {
        return newHref;
    }

    public void setNewHref(String newHref) {
        this.newHref = newHref;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}