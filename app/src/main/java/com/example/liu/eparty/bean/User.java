package com.example.liu.eparty.bean;

import java.io.Serializable;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/5:21:16
 * introduction：
 */

public class User implements Serializable{

    private int powerId;
    private int treeId;
    private String stattus;
    private String treeName;
    private String userName;
    private int userId;
    private String tels;
    private String token;

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public String getStattus() {
        return stattus;
    }

    public void setStattus(String stattus) {
        this.stattus = stattus;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTels() {
        return tels;
    }

    public void setTels(String tels) {
        this.tels = tels;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
