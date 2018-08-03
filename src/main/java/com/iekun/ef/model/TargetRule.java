package com.iekun.ef.model;

public class TargetRule {
    /**
    * 主键ID
    */
    private Long id;

    /**
    * 目标名称
    */
    private String name;
    
    private String smsTime;
    
    private String latestDeviceSn;

    /**
    * IMSI
    */
    private String imsi;

    /**
    * IMEI
    */
    private String imei;

    /**
    * MAC地址
    */
    private String mac;

    /**
    * 目标类型：0-黑名单；1-白名单
    */
    private Integer targetType;

    /**
    * 说明
    */
    private String remark;

    /**
    * 删除标记: 0-  正常，  1-  删除
    */
    private Integer deleteFlag;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;

    /**
    * 创建人ID
    */
    private String creatorId;

    /**
    * RECIVER 格式（json格式组织）：
    */
    private String receiver;

    /**
     * 身份号码
     * */
    private String idCard;

    /**
     * 手机号
     * */
    private String phone;

    public TargetRule(Long id, String name, String smsTime, String latestDeviceSn, String imsi, String imei, String mac,
                      Integer targetType, String remark, Integer deleteFlag, String createTime, String updateTime,
                      String creatorId, String receiver, String idCard, String phone) {
        this.id = id;
        this.name = name;
        this.smsTime = smsTime;
        this.latestDeviceSn = latestDeviceSn;
        this.imsi = imsi;
        this.imei = imei;
        this.mac = mac;
        this.targetType = targetType;
        this.remark = remark;
        this.deleteFlag = deleteFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.creatorId = creatorId;
        this.receiver = receiver;
        this.idCard = idCard;
        this.phone = phone;
    }

    public String getIdCard() {

        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TargetRule()
    {
    	super();
    }

    public TargetRule(String name, String imsi, String remark, String receivers)
    {
    	super();
    	this.name = name;
    	this.imsi = imsi;
    	this.remark = remark;
    	this.receiver = receivers;
    	this.deleteFlag = 0;
    }
    
    public TargetRule( long targetId, String currentSmsTime, String latestDeviceSn)
    {
    	super();
    	this.id = targetId;
    	this.smsTime = currentSmsTime;
    	this.latestDeviceSn = latestDeviceSn;
    }

   
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

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    public String getSmsTime() {
		return smsTime;
	}

	public void setSmsTime(String smsTime) {
		this.smsTime = smsTime;
	}

	public String getLatestDeviceSn() {
		return latestDeviceSn;
	}

	public void setLatestDeviceSn(String latestDeviceSn) {
		this.latestDeviceSn = latestDeviceSn;
	}
}