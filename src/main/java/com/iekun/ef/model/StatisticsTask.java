package com.iekun.ef.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by feilong.cai on 2016/11/25.
 */
public class StatisticsTask  extends Task {

    public final static Integer taskType = 0;

    private Integer statisticType;

    private Integer statisticUnit;

    private JSONArray statisticItems;

    private  String  startTime;

    private  String  endTime;

    public StatisticsTask(String name, String parameter, Long creatorId, String createTime, Integer statisticType,
                          Integer statisticUnit, JSONArray statisticItems, String startTime, String endTime) {
        super(taskType, name, parameter, creatorId, createTime);
        this.statisticType = statisticType;
        this.statisticUnit = statisticUnit;
        this.statisticItems = statisticItems;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public StatisticsTask(Long id, String name, String parameter, String result,
                          String resultExt, Integer isFinish, float nowValue,
                          String remark, String createTime, String finishTime, Long creatorId,
                          Integer deleteFlag, Integer statisticType,
                          Integer statisticUnit, JSONArray statisticItems, String startTime, String endTime) {
        super(id, taskType, name, parameter, result, resultExt, isFinish, nowValue, remark,
                createTime, finishTime, creatorId, deleteFlag);
        this.statisticType = statisticType;
        this.statisticUnit = statisticUnit;
        this.statisticItems = statisticItems;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Integer getTaskType() {
        return taskType;
    }

    public Integer getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(Integer statisticType) {
        this.statisticType = statisticType;
    }

    public Integer getStatisticUnit() {
        return statisticUnit;
    }

    public void setStatisticUnit(Integer statisticUnit) {
        this.statisticUnit = statisticUnit;
    }

    public JSONArray getStatisticItems() {
        return statisticItems;
    }

    public void setStatisticItems( JSONArray statisticItems) {
        this.statisticItems = statisticItems;
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
