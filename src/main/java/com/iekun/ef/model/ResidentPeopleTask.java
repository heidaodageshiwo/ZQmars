package com.iekun.ef.model;

/**
 * Created by sword.yu  on 2016/12/21.
 */
public class ResidentPeopleTask extends Task  {

    public final static Integer taskType = 3;

    private Long areaId;

    private String areaName;

    private String startTime;

    private String endTime;

    public ResidentPeopleTask(  String name, String parameter, Long creatorId,
                            String createTime, Long areaId,  String areaName, String startTime, String endTime) {
        super( taskType, name, parameter, creatorId, createTime);
        this.areaId = areaId;
        this.areaName = areaName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ResidentPeopleTask(Long id, String name, String parameter, String result,
                          String resultExt, Integer isFinish, float nowValue, String remark,
                          String createTime, String finishTime, Long creatorId,
                          Integer deleteFlag,  Long areaId,  String areaName, String startTime, String endTime) {
        super(id, taskType, name, parameter, result, resultExt, isFinish, nowValue,
                remark, createTime, finishTime, creatorId, deleteFlag);
        this.areaId = areaId;
        this.areaName = areaName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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
}
