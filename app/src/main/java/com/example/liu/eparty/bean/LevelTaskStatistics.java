package com.example.liu.eparty.bean;

import java.util.List;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/26:10:36
 * introduction：
 */

public class LevelTaskStatistics {

    private String taskTitle;
    private String taskCreateTime;
    private List<String> organization;

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(String taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public void setOrganization(List<String> organization) {
        this.organization = organization;
    }

    public List<String> getOrganization() {
        return organization;
    }
}
