package com.iekun.ef.model;

public class Outbox {
    private Long id;

    /**
    * 通知人员名称
    */
    private String notifyName;

    /**
    * 通知人员手机号
    */
    private String notifyPhone;

    /**
    * 通知人员的email
    */
    private String notifyEmail;

    /**
    * 通知类型:0- sms; 1- email
    */
    private Byte notifyType;

    /**
    * 触发事件类型：0-  黑名单;1-  特殊区域;2-  设备告警
    */
    private Byte eventType;

    /**
    * 状态:0- 待处理;1- 成功;2- 失败;
    */
    private Byte status;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 发送时间
    */
    private String lastSendTime;

    /**
    * 删除标记: 0-  正常，  1-  删除
    */
    private Byte deleteFlag;

    /**
    * 内容
    */
    private String content;

    /**
     * 邮件主题
     */
    private String emailSubject;
    
    public Outbox()
    {
    	super();
    }

    public Outbox(String notifyName, String notifyPhone, String smsMsg)
    {
    	super();
    	this.content = smsMsg;
    	this.notifyName = notifyName;
    	this.notifyPhone = notifyPhone;
    	
    }

    public Outbox( String notifyName, String notifyEmail, Byte notifyType, Byte eventType, Byte status, String createTime, String content, String emailSubject) {
        this.notifyName = notifyName;
        this.notifyEmail = notifyEmail;
        this.notifyType = notifyType;
        this.eventType = eventType;
        this.status = status;
        this.createTime = createTime;
        this.content = content;
        this.emailSubject = emailSubject;
        this.deleteFlag = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotifyName() {
        return notifyName;
    }

    public void setNotifyName(String notifyName) {
        this.notifyName = notifyName;
    }

    public String getNotifyPhone() {
        return notifyPhone;
    }

    public void setNotifyPhone(String notifyPhone) {
        this.notifyPhone = notifyPhone;
    }

    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public Byte getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Byte notifyType) {
        this.notifyType = notifyType;
    }

    public Byte getEventType() {
        return eventType;
    }

    public void setEventType(Byte eventType) {
        this.eventType = eventType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(String lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public Byte getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Byte deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }
}