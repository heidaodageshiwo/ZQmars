package com.iekun.ef.model;

import com.alibaba.fastjson.JSONArray;

public class DataAnalysis {

    /**
     * 任务id
     * */
    private String id;

    /**
     * 任务类型
     * */
    private String type;

    /**
     * 任务名字
     * */
    private String name;

    /**
     * 创建人ID
     * */
    private Long creatorId;

    /**
     * 创建人姓名
     * */
    private String creatorName;

    /**
     *sparkID
     * */
    private String appId;

    /**
     *检索条件
     * */
    private String filter;

    /**
     *任务状态
     * */
    private String status;

    /**
     * 任务进度
     * */
    private float progress;

    /**
     * 创建日期
     * */
    private String createTime;

    /**
     * 完成日期
     * */
    private String endTime;

    /**
     * 备注
     * */
    private String remark;

    /**
     * 删除标记
     * */
    private int deleteFlag;

    /**
     * 时空信息
     * */
    private String parameter;

    private JSONArray spaceTimeItems;

    public JSONArray getSpaceTimeItems() {
        return spaceTimeItems;
    }

    public void setSpaceTimeItems(JSONArray spaceTimeItems) {
        this.spaceTimeItems = spaceTimeItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public DataAnalysis(String id, String type, String name, Long creatorId, String creatorName, String appId, String filter, String status, float progress, String createTime, String endTime, String remark, int deleteFlag, String parameter, JSONArray spaceTimeItems) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.appId = appId;
        this.filter = filter;
        this.status = status;
        this.progress = progress;
        this.createTime = createTime;
        this.endTime = endTime;
        this.remark = remark;
        this.deleteFlag = deleteFlag;
        this.parameter = parameter;
        this.spaceTimeItems = spaceTimeItems;
    }

    public DataAnalysis(){}
}
