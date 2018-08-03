package com.iekun.ef.model;

public class UeInfo {
    /**
    * Ue信息ID
    */
    private Long id;

    /**
    * 站点序列号
    */
    private String siteSn;

    /**
     * 站点名称
     */
     private String siteName;
     
    /**
    * 子设备序列号
    */
    private String deviceSn;
    
    /**
     * 设备信息
    */
    
    private Device device;
    
    /**
     * 子设备名
     */
    private String deviceName;

    /**
    * IMSI
    */
    private String imsi;

    /**
    * IMEI
    */
    private String imei;

    /**
    * TMSI
    */
    private String stmsi;

    /**
    * MAC地址
    */
    private String mac;

    /**
    * 位置更新类型
    */
    private String latype;

    /**
    * 是否目标手机：0 --- NORMAL；1--- 黑名单手机；2--- 特殊归属地手机
    */
    private Byte indication;

    /**
    * 是否实时上报:00-  不实时上报;01-  实时上报
    */
    private Integer realtime;

    /**
    * 基站侧记录的上号时间
    */
    private String captureTime;

    private Long rarta;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 归属地地名
    */
    private String cityName;

   
    private String cityCode;
    
    /**
    * 运营商
    */
    private String band;

    /**
    * 运营商
    */
    private String operator;

    /**
    * 号码段
    */
    
    public UeInfo()
    {
    	super();
    }
    
    public UeInfo(String captureTime, String deviceSn, String imei, String imsi, String latype, String stmsi, Long rarta)
    {
    	super();
    	this.captureTime = captureTime;
    	this.deviceSn = deviceSn;
    	this.imei = imei;
    	this.imsi = imsi;
    	this.latype = latype;
    	this.stmsi = stmsi;
    	this.rarta = rarta;
    }

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

    public String getStmsi() {
        return stmsi;
    }

    public void setStmsi(String stmsi) {
        this.stmsi = stmsi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLatype() {
        return latype;
    }

    public void setLatype(String latype) {
        this.latype = latype;
    }

    public Byte getIndication() {
        return indication;
    }

    public void setIndication(Byte indication) {
        this.indication = indication;
    }

    public Integer getRealtime() {
        return realtime;
    }

    public void setRealtime(Integer realtime) {
        this.realtime = realtime;
    }

    public String getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    public Long getRarta() {
        return rarta;
    }

    public void setRarta(Long rarta) {
        this.rarta = rarta;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSiteSn() {
		return siteSn;
	}

	public void setSiteSn(String siteSn) {
		this.siteSn = siteSn;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}