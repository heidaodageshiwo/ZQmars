package com.iekun.ef.model;

public class SysLog {
    /**
    * 操作ID
    */
    private Long id;

    /**
    * 操作类型：00- 设备状态日志;01- 用户登录登出日志;02- 设备操作日志;03- 通知日志;04- 系统日志
    */
    private Byte type;

    /**
    * 用户ID
    */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;
    
    /**
    * 操作码
    */
    private String operCode;

    /**
    * 操作名
    */
    private String operName;

    /**
    * 操作时间
    */
    private String operTime;

    /**
    * 操作结果：-1-处理中；0-成功；1-失败
    */
    private Byte operStatus;

    /**
    * 站点名称
    */
    private String siteName;

    /**
    * 子设备编号
    */
    private String deviceSn;

    /**
    * 会话ID
    */
   
    private Long sessionId;
    
    
  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public Byte getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(Byte operStatus) {
        this.operStatus = operStatus;
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

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}