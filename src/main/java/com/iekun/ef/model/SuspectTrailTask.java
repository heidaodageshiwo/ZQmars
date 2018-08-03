package com.iekun.ef.model;

import java.util.ArrayList;

/**
 * Created by feilong.cai  on 2016/11/25.
 */

public class SuspectTrailTask extends Task {

    public final static Integer taskType = 1;

    private String targetIMSI;

    private String startTime;

    private String endTime;

    private ArrayList otherIMSI;

    public SuspectTrailTask(  String name, String parameter, Long creatorId,
                            String createTime, String targetIMSI, ArrayList otherIMSI, String startTime, String endTime) {
        super( taskType, name, parameter, creatorId, createTime);
        this.targetIMSI = targetIMSI;
        this.startTime = startTime;
        this.endTime = endTime;
        this.otherIMSI = otherIMSI;
    }

    public SuspectTrailTask(Long id, String name, String parameter, String result,
                            String resultExt, Integer isFinish, float nowValue, String remark,
                            String createTime, String finishTime, Long creatorId,
                            Integer deleteFlag, String targetIMSI, ArrayList otherIMSI, String startTime, String endTime) {
        super(id, taskType, name, parameter, result, resultExt, isFinish, nowValue,
                remark, createTime, finishTime, creatorId, deleteFlag);
        this.targetIMSI = targetIMSI;
        this.startTime = startTime;
        this.endTime = endTime;
        this.otherIMSI = otherIMSI;
    }

    public String getTargetIMSI() {
        return targetIMSI;
    }

    public void setTargetIMSI(String targetIMSI) {
        this.targetIMSI = targetIMSI;
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

    public ArrayList getOtherIMSI() {
        return otherIMSI;
    }

    public void setOtherIMSI( ArrayList otherIMSI) {
        this.otherIMSI = otherIMSI;
    }
}
