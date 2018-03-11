package com.example.liu.eparty.bean;

import java.io.Serializable;

public class Plan implements Serializable {

    /**
     * submitId : 39
     * submitUserId : 652
     * submitTime : 2017-12-23 15:49
     * doStartTime : 2017-11-29 16:49:00
     * doFinishTime : 2017-11-28 17:49:00
     * taskName : 换个方法
     * doWhere : 比较
     * submitName : 2333
     * receiveTreeId : null
     * submitUser : null
     * receiverUser : null
     * finishCodition : null
     * tastTitle : 很尴尬
     * url : null
     * urlname : null
     * tast : {"tastId":172,"tastContent":"就好好","urlName":"20170918175117.doc","testUrl":"C:/ZHDJ/upload/file/2017122315450920170918175117.doc","tastTitle":"很尴尬","selfType":1,"sentId":4,"treeName":null,"receiveId":652,"creatTime":"2017-12-23 15:45:09.0","checkId":null,"startTime":"2017-11-14 16:43:00","finishTime":"2017-11-29 17:43:00","tastType":"611b1d15","receiveUnit":null,"readok":null,"node":null,"userNode":null,"submit":null,"finishOk":null,"realFinishTime":null,"creatTastTime":null}
     * receiveUserId : 4
     * readOk : 1
     * submitTreeId : 1
     * checkStatus : null
     * checkOpinion : null
     * checkName : null
     * tastId : 172
     * submitType : 1
     * submitParentTreeId : 0
     */

    private int submitId;
    private int submitUserId;
    private String submitTime;
    private String doStartTime;
    private String doFinishTime;
    private String taskName;
    private String doWhere;
    private String submitName;
    private int receiveTreeId;
    private String submitUser;
    private String receiverUser;
    private String submitTreeName;
    private int finishCodition;
    private String tastTitle;
    private String url;
    private String urlname;
    private TastBean tast;
    private int receiveUserId;
    private String readOk;
    private int submitTreeId;
    private Object checkStatus;
    private String checkOpinion;
    private String checkName;
    private int tastId;
    private int submitType;
    private int submitParentTreeId;

    public int getSubmitId() {
        return submitId;
    }

    public void setSubmitId(int submitId) {
        this.submitId = submitId;
    }

    public int getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(int submitUserId) {
        this.submitUserId = submitUserId;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getDoStartTime() {
        return doStartTime;
    }

    public void setDoStartTime(String doStartTime) {
        this.doStartTime = doStartTime;
    }

    public String getDoFinishTime() {
        return doFinishTime;
    }

    public void setDoFinishTime(String doFinishTime) {
        this.doFinishTime = doFinishTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDoWhere() {
        return doWhere;
    }

    public void setDoWhere(String doWhere) {
        this.doWhere = doWhere;
    }

    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    public int getReceiveTreeId() {
        return receiveTreeId;
    }

    public void setReceiveTreeId(int receiveTreeId) {
        this.receiveTreeId = receiveTreeId;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public String getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(String receiverUser) {
        this.receiverUser = receiverUser;
    }

    public String getSubmitTreeName() {
        return submitTreeName;
    }

    public void setSubmitTreeName(String submitTreeName) {
        this.submitTreeName = submitTreeName;
    }

    public int getFinishCodition() {
        return finishCodition;
    }

    public void setFinishCodition(int finishCodition) {
        this.finishCodition = finishCodition;
    }

    public String getTastTitle() {
        return tastTitle;
    }

    public void setTastTitle(String tastTitle) {
        this.tastTitle = tastTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public TastBean getTast() {
        return tast;
    }

    public void setTast(TastBean tast) {
        this.tast = tast;
    }

    public int getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(int receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReadOk() {
        return readOk;
    }

    public void setReadOk(String readOk) {
        this.readOk = readOk;
    }

    public int getSubmitTreeId() {
        return submitTreeId;
    }

    public void setSubmitTreeId(int submitTreeId) {
        this.submitTreeId = submitTreeId;
    }

    public Object getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Object checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public int getTastId() {
        return tastId;
    }

    public void setTastId(int tastId) {
        this.tastId = tastId;
    }

    public int getSubmitType() {
        return submitType;
    }

    public void setSubmitType(int submitType) {
        this.submitType = submitType;
    }

    public int getSubmitParentTreeId() {
        return submitParentTreeId;
    }

    public void setSubmitParentTreeId(int submitParentTreeId) {
        this.submitParentTreeId = submitParentTreeId;
    }

    public static class TastBean implements Serializable{
        /**
         * tastId : 172
         * tastContent : 就好好
         * urlName : 20170918175117.doc
         * testUrl : C:/ZHDJ/upload/file/2017122315450920170918175117.doc
         * tastTitle : 很尴尬
         * selfType : 1
         * sentId : 4
         * treeName : null
         * receiveId : 652
         * creatTime : 2017-12-23 15:45:09.0
         * checkId : null
         * startTime : 2017-11-14 16:43:00
         * finishTime : 2017-11-29 17:43:00
         * tastType : 611b1d15
         * receiveUnit : null
         * readok : null
         * node : null
         * userNode : null
         * submit : null
         * finishOk : null
         * realFinishTime : null
         * creatTastTime : null
         */

        private int tastId;
        private String tastContent;
        private String urlName;
        private String testUrl;
        private String tastTitle;
        private int selfType;
        private int sentId;
        private Object treeName;
        private int receiveId;
        private String creatTime;
        private Object checkId;
        private String startTime;
        private String finishTime;
        private String tastType;
        private Object receiveUnit;
        private Object readok;
        private Object node;
        private Object userNode;
        private Object submit;
        private Object finishOk;
        private Object realFinishTime;
        private Object creatTastTime;

        public int getTastId() {
            return tastId;
        }

        public void setTastId(int tastId) {
            this.tastId = tastId;
        }

        public String getTastContent() {
            return tastContent;
        }

        public void setTastContent(String tastContent) {
            this.tastContent = tastContent;
        }

        public String getUrlName() {
            return urlName;
        }

        public void setUrlName(String urlName) {
            this.urlName = urlName;
        }

        public String getTestUrl() {
            return testUrl;
        }

        public void setTestUrl(String testUrl) {
            this.testUrl = testUrl;
        }

        public String getTastTitle() {
            return tastTitle;
        }

        public void setTastTitle(String tastTitle) {
            this.tastTitle = tastTitle;
        }

        public int getSelfType() {
            return selfType;
        }

        public void setSelfType(int selfType) {
            this.selfType = selfType;
        }

        public int getSentId() {
            return sentId;
        }

        public void setSentId(int sentId) {
            this.sentId = sentId;
        }

        public Object getTreeName() {
            return treeName;
        }

        public void setTreeName(Object treeName) {
            this.treeName = treeName;
        }

        public int getReceiveId() {
            return receiveId;
        }

        public void setReceiveId(int receiveId) {
            this.receiveId = receiveId;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public Object getCheckId() {
            return checkId;
        }

        public void setCheckId(Object checkId) {
            this.checkId = checkId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getTastType() {
            return tastType;
        }

        public void setTastType(String tastType) {
            this.tastType = tastType;
        }

        public Object getReceiveUnit() {
            return receiveUnit;
        }

        public void setReceiveUnit(Object receiveUnit) {
            this.receiveUnit = receiveUnit;
        }

        public Object getReadok() {
            return readok;
        }

        public void setReadok(Object readok) {
            this.readok = readok;
        }

        public Object getNode() {
            return node;
        }

        public void setNode(Object node) {
            this.node = node;
        }

        public Object getUserNode() {
            return userNode;
        }

        public void setUserNode(Object userNode) {
            this.userNode = userNode;
        }

        public Object getSubmit() {
            return submit;
        }

        public void setSubmit(Object submit) {
            this.submit = submit;
        }

        public Object getFinishOk() {
            return finishOk;
        }

        public void setFinishOk(Object finishOk) {
            this.finishOk = finishOk;
        }

        public Object getRealFinishTime() {
            return realFinishTime;
        }

        public void setRealFinishTime(Object realFinishTime) {
            this.realFinishTime = realFinishTime;
        }

        public Object getCreatTastTime() {
            return creatTastTime;
        }

        public void setCreatTastTime(Object creatTastTime) {
            this.creatTastTime = creatTastTime;
        }
    }
}