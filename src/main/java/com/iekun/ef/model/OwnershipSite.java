package com.iekun.ef.model;

public class OwnershipSite {

    /**
     * 主键ID
     * */
    private String id;

    /**
     * 分组名字
     * */
    private String name;

    /**
     * 地区信息
     * */
    private String  ownership;

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 站点信息
     * */

    private String site;

    /**
     * 预警接收人信息
     * */
    private String warning;

    /**
     * 创建时间
     * */
    private String createTime;

    /**
     * 开始时间
     * */
    private String startTime;

    /**
     * 到期时间
     * */
    private String OverdueTime;

    /**
     * 备注
     * */
    private String remark;

    /**
     * 创建人ID
     * */
    private long creatorId;

    /**
     * 创建人姓名
     * */
    private String creatorName;

    public OwnershipSite(String id, String name, String ownership, String site, String warning, String createTime, String startTime, String overdueTime, String remark, long creatorId, String creatorName) {
        this.id = id;
        this.name = name;
        this.ownership = ownership;
        this.site = site;
        this.warning = warning;
        this.createTime = createTime;
        this.startTime = startTime;
        OverdueTime = overdueTime;
        this.remark = remark;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
    }

    public String getCreatorName() {

        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOverdueTime() {
        return OverdueTime;
    }

    public void setOverdueTime(String overdueTime) {
        OverdueTime = overdueTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public OwnershipSite(String id, String name, String ownership, String site, String warning, String createTime, String startTime, String overdueTime, String remark, long creatorId) {
        this.id = id;
        this.name = name;
        this.ownership = ownership;
        this.site = site;
        this.warning = warning;
        this.createTime = createTime;
        this.startTime = startTime;
        OverdueTime = overdueTime;
        this.remark = remark;
        this.creatorId = creatorId;
    }

    public OwnershipSite(){}
}
