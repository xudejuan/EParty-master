package com.example.liu.eparty.bean;

import java.io.Serializable;
import java.util.List;

public class Report implements Serializable {

    /**
     * finishId : 3
     * finishTime : 2017-12-27 19:40
     * submitFinishName : 2017-12-27 19:40
     * url : ["C:/ZHDJ/upload/file/20171227194043JPEG_1513514348366.jpg","C:/ZHDJ/upload/file/20171227194043wx_camera_1513733130364.jpg","C:/ZHDJ/upload/file/20171227194043wx_camera_1513733130364.jpg","C:/ZHDJ/upload/file/20171227194043wx_camera_1514302087007.mp4","C:/ZHDJ/upload/file/20171227194043第8章_EL、JSTL和Ajax技术.ppt"]
     * urlName : ["JPEG_1513514348366.jpg","wx_camera_1513733130364.jpg","wx_camera_1513733130364.jpg","wx_camera_1514302087007.mp4","第8章_EL、JSTL和Ajax技术.ppt"]
     * tastId : 185
     * finishUrlName : JPEG_1513514348366.jpg,wx_camera_1513733130364.jpg,wx_camera_1513733130364.jpg,wx_camera_1514302087007.mp4,第8章_EL、JSTL和Ajax技术.ppt,null
     * submitId : 55
     * finishReadOk : null
     * userId : 55
     * checkFinishTime : 2017-12-27 19:40
     * finishOpinion : 1
     * submitName : 刘俊年
     * tastTitle : 西门吹雪
     */

    private int finishId;
    private String finishTime;
    private String submitFinishName;
    private int tastId;
    private String finishUrlName;
    private int submitId;
    private String finishReadOk;
    private int userId;
    private String checkFinishTime;
    private String finishOpinion;
    private String submitName;
    private String tastTitle;
    private List<String> url;
    private List<String> urlName;

    public int getFinishId() {
        return finishId;
    }

    public void setFinishId(int finishId) {
        this.finishId = finishId;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getSubmitFinishName() {
        return submitFinishName;
    }

    public void setSubmitFinishName(String submitFinishName) {
        this.submitFinishName = submitFinishName;
    }

    public int getTastId() {
        return tastId;
    }

    public void setTastId(int tastId) {
        this.tastId = tastId;
    }

    public String getFinishUrlName() {
        return finishUrlName;
    }

    public void setFinishUrlName(String finishUrlName) {
        this.finishUrlName = finishUrlName;
    }

    public int getSubmitId() {
        return submitId;
    }

    public void setSubmitId(int submitId) {
        this.submitId = submitId;
    }

    public String getFinishReadOk() {
        return finishReadOk;
    }

    public void setFinishReadOk(String finishReadOk) {
        this.finishReadOk = finishReadOk;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCheckFinishTime() {
        return checkFinishTime;
    }

    public void setCheckFinishTime(String checkFinishTime) {
        this.checkFinishTime = checkFinishTime;
    }

    public String getFinishOpinion() {
        return finishOpinion;
    }

    public void setFinishOpinion(String finishOpinion) {
        this.finishOpinion = finishOpinion;
    }

    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    public String getTastTitle() {
        return tastTitle;
    }

    public void setTastTitle(String tastTitle) {
        this.tastTitle = tastTitle;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public List<String> getUrlName() {
        return urlName;
    }

    public void setUrlName(List<String> urlName) {
        this.urlName = urlName;
    }
}