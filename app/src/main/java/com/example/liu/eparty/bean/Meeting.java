package com.example.liu.eparty.bean;

import java.io.Serializable;

/**
 * 会议
 */

public class Meeting implements Serializable {

    private int id;
    private int orgId;
    private int identity;
    private String orgName;
    private int orgAdminId;
    private String meetingTitle;
    private String meetingContent;
    private String aStartTime;
    private String aEndTime;
    private String aSignStartTime;
    private String aSignEndTime;
    private String signCode;
    private String signCodeUrl;
    private int arriveNumber;
    private String address;
    private Object image;
    private Object document;
    private int type;
    private Object status;

    public Meeting(String meetingTitle, String meetingContent, String aStartTime, String aEndTime, String aSignStartTime, String aSignEndTime, int arriveNumber, String address) {
        this.meetingTitle = meetingTitle;
        this.meetingContent = meetingContent;
        this.aStartTime = aStartTime;
        this.aEndTime = aEndTime;
        this.aSignStartTime = aSignStartTime;
        this.aSignEndTime = aSignEndTime;
        this.arriveNumber = arriveNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getOrgAdminId() {
        return orgAdminId;
    }

    public void setOrgAdminId(int orgAdminId) {
        this.orgAdminId = orgAdminId;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingContent() {
        return meetingContent;
    }

    public void setMeetingContent(String meetingContent) {
        this.meetingContent = meetingContent;
    }

    public String getAStartTime() {
        return aStartTime;
    }

    public void setAStartTime(String aStartTime) {
        this.aStartTime = aStartTime;
    }

    public String getAEndTime() {
        return aEndTime;
    }

    public void setAEndTime(String aEndTime) {
        this.aEndTime = aEndTime;
    }

    public String getASignStartTime() {
        return aSignStartTime;
    }

    public void setASignStartTime(String aSignStartTime) {
        this.aSignStartTime = aSignStartTime;
    }

    public String getASignEndTime() {
        return aSignEndTime;
    }

    public void setASignEndTime(String aSignEndTime) {
        this.aSignEndTime = aSignEndTime;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getSignCodeUrl() {
        return signCodeUrl;
    }

    public void setSignCodeUrl(String signCodeUrl) {
        this.signCodeUrl = signCodeUrl;
    }

    public int getArriveNumber() {
        return arriveNumber;
    }

    public void setArriveNumber(int arriveNumber) {
        this.arriveNumber = arriveNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getDocument() {
        return document;
    }

    public void setDocument(Object document) {
        this.document = document;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }
}