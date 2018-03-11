package com.example.liu.eparty.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 组织树
 */

public class Tree implements Serializable{
    private int id;
    private String text;
    private String state;
    private Object powerId;
    private List<Tree> children;

    public Tree(int id, String text, List<Tree> children) {
        this.id = id;
        this.text = text;
        this.children = children;
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

    public Object getPowerId() {
        return powerId;
    }

    public void setPowerId(Object powerId) {
        this.powerId = powerId;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }
}
