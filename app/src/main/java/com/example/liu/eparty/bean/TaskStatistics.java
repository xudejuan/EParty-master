package com.example.liu.eparty.bean;

import java.io.Serializable;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/26:9:59
 * introduction：
 */

public class TaskStatistics implements Serializable{

    private int finished;
    private int unfinished;
    private int finishedTimeout;

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getUnfinished() {
        return unfinished;
    }

    public void setUnfinished(int unfinished) {
        this.unfinished = unfinished;
    }

    public int getFinishedTimeout() {
        return finishedTimeout;
    }

    public void setFinishedTimeout(int finishedTimeout) {
        this.finishedTimeout = finishedTimeout;
    }
}
