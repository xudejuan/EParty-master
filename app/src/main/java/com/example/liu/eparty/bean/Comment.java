package com.example.liu.eparty.bean;

/**
 * 评论
 */

public class Comment {
    private int id;
    private int meetingId;
    private String commentator;
    private String meetingTitle;
    private String commentContent;
    private String commenttime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeetingid() {
        return meetingId;
    }

    public void setMeetingid(int meetingid) {
        this.meetingId = meetingid;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public String getMeetingtitle() {
        return meetingTitle;
    }

    public void setMeetingtitle(String meetingtitle) {
        this.meetingTitle = meetingtitle;
    }

    public String getCommentcontent() {
        return commentContent;
    }

    public void setCommentcontent(String commentcontent) {
        this.commentContent = commentcontent;
    }

    public String getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(String commenttime) {
        this.commenttime = commenttime;
    }
}