package com.iekun.ef.model;

public class DeviceLicense {
    /**
    * 设备授权ID
    */
    private Long id;

    /**
    * 站点名称
    */
    private String siteName;

    /**
    * 设备序列号
    */
    private String deviceSn;

    /**
    * 上传用户的用户名
    */
    private String uploadUser;

    /**
    * 上传的时间
    */
    private String uploadTime;

    /**
    * 计划下载的时间（暂时不用）
    */
    private String scheduleTime;

    /**
    * 保存路径
    */
    private String fileUrl;

    /**
    * 尝试次数（暂时不用）
    */
    private Byte retrties;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 更新时间
    */
    private String updateTime;
    
    /* * 
     * 升级之前license 过期时间 
     * */
    private String bExpireTime;
    
    
    /* * 
     * 升级之后license 过期时间 
     * */
    private String aExpireTime;
    
    
    /* * 
     * MD5校验和
     * */
    private String checkSum;
    

    /**
    * 授权状态标志:00-  未授权;01-  已授权
    */
    private Byte statusFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Byte getRetrties() {
        return retrties;
    }

    public void setRetrties(Byte retrties) {
        this.retrties = retrties;
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

    public Byte getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Byte statusFlag) {
        this.statusFlag = statusFlag;
    }

	public String getbExpireTime() {
		return bExpireTime;
	}

	public void setbExpireTime(String bExpireTime) {
		this.bExpireTime = bExpireTime;
	}

	public String getaExpireTime() {
		return aExpireTime;
	}

	public void setaExpireTime(String aExpireTime) {
		this.aExpireTime = aExpireTime;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
}