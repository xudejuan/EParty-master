package com.example.liu.eparty.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/8:22:03
 * introduction：
 */

public class Task implements Serializable{

    private int taskId;
    private String taskType;
    private String title;
    private String createTime;
    private String startTime;
    private String endTime;
    private String issuer;
    private String issuerOrganization;
    private String recipient;
    private int recipientId;
    private String recipientOrganization;
    private String content;
    private String attachmentName;
    private String attachmentUrl;
    private List<String> membersName;
    private List<String> organizationsName;
    private String planState;
    private String reportState;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuerOrganization() {
        return issuerOrganization;
    }

    public void setIssuerOrganization(String issuerOrganization) {
        this.issuerOrganization = issuerOrganization;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipientOrganization() {
        return recipientOrganization;
    }

    public void setRecipientOrganization(String recipientOrganization) {
        this.recipientOrganization = recipientOrganization;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public List<String> getMembersName() {
        return membersName;
    }

    public void setMembersName(List<String> membersName) {
        this.membersName = membersName;
    }

    public List<String> getOrganizationsName() {
        return organizationsName;
    }

    public void setOrganizationsName(List<String> organizationsName) {
        this.organizationsName = organizationsName;
    }

    public String getPlanState() {
        return planState;
    }

    public void setPlanState(String planState) {
        this.planState = planState;
    }

    public String getReportState() {
        return reportState;
    }

    public void setReportState(String reportState) {
        this.reportState = reportState;
    }
}
