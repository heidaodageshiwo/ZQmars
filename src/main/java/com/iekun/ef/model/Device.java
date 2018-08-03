package com.iekun.ef.model;

public class Device {
    /**
    * 子设备ID
    */
    private Long id;

    private Site site;
   
    /**
    * 站点ID
    */
    private Long siteId;

    /**
    * 设备序列号
    */
    private String sn;

    /**
    * 设备名称
    */
    private String name;

    /**
    * 设备类型：01- cdma；02- gsm；03- wcdma；04- td；05- tdd；06- fdd
    */
    private Integer type;

    /**
    * 设备工作频段
    */
    private String band;

    /**
    * 运营商：01- 移动 02- 电信 03- 联通
    */
    private String operator;

    /**
    * 生产厂家
    */
    private String manufacturer;

    /**
    * 说明
    */
    private String remark;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
    
    /**
    * 删除标记: 0-  正常，  1-  删除
    */
    private int deleteFlag;
    
    /*设备最近告警短信发送时间 */
    private String dvLatestAlarmSmsSendTime;
    
    /*设备最近告警邮件发送时间*/
    private String dvLatestAlarmEmailSendTime;
    
    public Device(){
    	super();
    }
    
    public Device(String sn, String name,  Integer type,  String band,  String operator, String manufacturer, String remark, String belongTo)
    {
    	super();
		this.sn = sn;
		this.name = name;
		this.type = type;
		this.band = band;
		this.operator = operator;
		this.manufacturer = manufacturer;
		this.remark = remark;
		this.siteId = Long.parseLong(belongTo);
    }
    
    
    public Device(long id, String name,  Integer type,  String band,  String operator, String manufacturer, String remark, String belongTo)
    {
    	super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.band = band;
		this.operator = operator;
		this.manufacturer = manufacturer;
		this.remark = remark;
		this.siteId = Long.parseLong(belongTo);
    }
    
    public Device(long id, int deleteFlag){
    	super();
    	this.id = id;
    	this.deleteFlag = deleteFlag;
      	this.name = null;
		this.type = null;
		this.band = null;
		this.operator = null;
		this.manufacturer = null;
		this.remark = null;
		this.siteId = null;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
   public Site getSite() {
			return site;
		}
	
	public void setSite(Site site) {
		this.site = site;
	}

	public String getDvLatestAlarmSmsSendTime() {
		return dvLatestAlarmSmsSendTime;
	}

	public void setDvLatestAlarmSmsSendTime(String dvLatestAlarmSmsSendTime) {
		this.dvLatestAlarmSmsSendTime = dvLatestAlarmSmsSendTime;
	}

	public String getDvLatestAlarmEmailSendTime() {
		return dvLatestAlarmEmailSendTime;
	}

	public void setDvLatestAlarmEmailSendTime(String dvLatestAlarmEmailSendTime) {
		this.dvLatestAlarmEmailSendTime = dvLatestAlarmEmailSendTime;
	}

	
}