package com.iekun.ef.model;

public class Role {
    /**
    * 角色ID
    */
    private Long id;

    /**
    * 角色名称
    */
    private String name;

    /**
    * 是否为默认：0-否；1-是
    */
    private Boolean defaultFlag;

    /**
    * 备注
    */
    private String remark;

    /**
    * 删除标志：0-未删除；1-已删除
    */
    private Boolean deleteFlag;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 最后更新时间
    */
    private String updateTime;

    /**
    * 创建人ID
    */
    private Long creatorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}