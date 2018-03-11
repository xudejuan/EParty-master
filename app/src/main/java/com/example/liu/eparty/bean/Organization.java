package com.example.liu.eparty.bean;

import java.io.Serializable;

/**
 * 组织
 */

public class Organization implements Serializable{
    private int id;
    private String text;
    private String state;
    private int powerId;

    public Organization(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
