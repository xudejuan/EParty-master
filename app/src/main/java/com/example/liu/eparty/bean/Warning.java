package com.example.liu.eparty.bean;

import java.io.Serializable;

/**
 * 预警
 */

public class Warning implements Serializable {

    /**
     * id : 20
     * taskId : 3
     * orgAdminId : 1
     * warnOrgId : 1
     * warnOrgName : null
     * orgId : 2
     * orgName : null
     * title : 爽肤水
     * deadLine : 2017-11-13 18:47:30
     * handleWay : null
     * result : null
     */

    private int id;
    private int taskId;
    private String taskName;
    private int orgAdminId;
    private int warnOrgId;
    private String warnOrgName;
    private int orgId;
    private int handleStatus;
    private String orgName;
    private String createTime;
    private String title;
    private String times;
    private String deadLine;
    private String handleWay;
    private Object result;

    public Warning(String warnOrgName, int handleStatus, String orgName) {
        this.warnOrgName = warnOrgName;
        this.handleStatus = handleStatus;
        this.orgName = orgName;
    }

    public Warning(String createTime, String times) {
        this.createTime = createTime;
        this.times = times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getOrgAdminId() {
        return orgAdminId;
    }

    public void setOrgAdminId(int orgAdminId) {
        this.orgAdminId = orgAdminId;
    }

    public int getWarnOrgId() {
        return warnOrgId;
    }

    public void setWarnOrgId(int warnOrgId) {
        this.warnOrgId = warnOrgId;
    }

    public int getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getWarnOrgName() {
        return warnOrgName;
    }

    public void setWarnOrgName(String warnOrgName) {
        this.warnOrgName = warnOrgName;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getHandleWay() {
        return handleWay;
    }

    public void setHandleWay(String handleWay) {
        this.handleWay = handleWay;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}