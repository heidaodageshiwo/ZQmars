package com.iekun.ef.model;

/**
 * Created by feilong.cai on 2017/1/1.
 */

public class EmailTemplate {
    /**
     * 邮件模板ID
     */
    private Long id;

    /**
     * 触发事件类型：0- 黑名单，1-特殊归属地，2-设备告警，3-找回密码
     */
    private Integer eventType;

    /**
     * 当前模板是否启用标记：0-停用，1-启用
     */
    private  Integer active;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件模板
     */
    private String contentTpl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String  createTime;

    /**
     * 更新时间
     */
    private String  updateTime;

    /**
     * 删除标记: 0-  正常，  1-  删除
     */
    private Integer deleteFlag;


    public EmailTemplate(){
    }

    public EmailTemplate(Integer eventType, Integer active, String subject, String contentTpl, String remark, String createTime) {
        this.eventType = eventType;
        this.active = active;
        this.subject = subject;
        this.contentTpl = contentTpl;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = createTime;
        this.deleteFlag =0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContentTpl() {
        return contentTpl;
    }

    public void setContentTpl(String contentTpl) {
        this.contentTpl = contentTpl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
