package com.iekun.ef.model;

public class Upgrade {
    /**
    * 设备版本升级ID
    */
    private Long id;

    private VersionLib versionLib;
    /**
    * 设备版本ID
    */
    private Long versionId;

    /**
    * 站点名称
    */
    private String siteName;

    /**
    * 设备序列号
    */
    private String deviceSn;

    /*升级后设备版本 */
    private String  version;
    
    /**
    * 升级前设备总版本
    */
    private String oldVersion;

    /**
    * 升级前设备FPGA版本
    */
    private String oldFpgaVersion;

    /**
    * 升级前设备BBU版本
    */
    private String oldBbuVersion;

    /**
    * 升级前设备SW版本
    */
    private String oldSwVersion;

    /**
    * 计划升级时间点
    */
    private String scheduleTime;

    /**
    * 升级成功时间点
    */
    private String successTime;

    /**
    * 尝试次数
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
    
    /**
     * 设备版本路径
     */
    private String fileUrl;
    
    /**
     * 设备版本校验和
     */
    private String checkSum;
    
    
    /**
    * 状态标志：0- OPEN；1- FREEZE；2- REOPEN；3-CLOSE
    */
    private Byte statusFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
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

    public String getOldVersion() {
        return oldVersion;
    }

    public void setOldVersion(String oldVersion) {
        this.oldVersion = oldVersion;
    }

    public String getOldFpgaVersion() {
        return oldFpgaVersion;
    }

    public void setOldFpgaVersion(String oldFpgaVersion) {
        this.oldFpgaVersion = oldFpgaVersion;
    }

    public String getOldBbuVersion() {
        return oldBbuVersion;
    }

    public void setOldBbuVersion(String oldBbuVersion) {
        this.oldBbuVersion = oldBbuVersion;
    }

    public String getOldSwVersion() {
        return oldSwVersion;
    }

    public void setOldSwVersion(String oldSwVersion) {
        this.oldSwVersion = oldSwVersion;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
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

	public VersionLib getVersionLib() {
		return versionLib;
	}

	public void setVersionLib(VersionLib versionLib) {
		this.versionLib = versionLib;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
}