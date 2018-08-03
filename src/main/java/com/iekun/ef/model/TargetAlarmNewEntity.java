package com.iekun.ef.model;

public class TargetAlarmNewEntity {
    /**
     * Ue信息ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 分组所属用户ID
     */
    private String groupUserid;

    /**
     * 分组ID
     */
    private String groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点编号
     */
    private String siteSn;

    /**
     * 设备序列号
     */
    private String deviceSn;

    /**
     *
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
    private Boolean realtime;

    /**
     * 基站侧记录的上号时间
     */
    private String captureTime;

    /**
     *
     */
    private Long rarta;

    /**
     * 归属地地名（省+市）
     */
    private String cityName;

    /**
     *
     */
    private String cityCode;

    /**
     * 频段
     */
    private String band;

    /**
     * 运营商
     */
    private String operator;

    /**
     * 预警是否已读字段： 0 - 未确认；1-已确认；
     */
    private Boolean isRead;

    /**
     * 0未扫描解析、1已扫描解析
     */
    private Boolean isParse;

    /**
     * 已发短信：0未发、1已发
     */
    private Boolean isSms;

    /**
     * 目标名称
     */
    private String targetName;

    /**
     * 目标身份证号
     */
    private String targetId;

    /**
     * 目标手机号
     */
    private String targetPhone;

    /**
     * Ue信息ID
     * @return ID Ue信息ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Ue信息ID
     * @param id Ue信息ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 分组所属用户ID
     * @return GROUP_USERID 分组所属用户ID
     */
    public String getGroupUserid() {
        return groupUserid;
    }

    /**
     * 分组所属用户ID
     * @param groupUserid 分组所属用户ID
     */
    public void setGroupUserid(String groupUserid) {
        this.groupUserid = groupUserid;
    }

    /**
     * 分组ID
     * @return GROUP_ID 分组ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 分组ID
     * @param groupId 分组ID
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 分组名称
     * @return GROUP_NAME 分组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 分组名称
     * @param groupName 分组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 站点名称
     * @return SITE_NAME 站点名称
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * 站点名称
     * @param siteName 站点名称
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * 站点编号
     * @return SITE_SN 站点编号
     */
    public String getSiteSn() {
        return siteSn;
    }

    /**
     * 站点编号
     * @param siteSn 站点编号
     */
    public void setSiteSn(String siteSn) {
        this.siteSn = siteSn;
    }

    /**
     * 设备序列号
     * @return DEVICE_SN 设备序列号
     */
    public String getDeviceSn() {
        return deviceSn;
    }

    /**
     * 设备序列号
     * @param deviceSn 设备序列号
     */
    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    /**
     *
     * @return DEVICE_NAME
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     *
     * @param deviceName
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * IMSI
     * @return IMSI IMSI
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * IMSI
     * @param imsi IMSI
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * IMEI
     * @return IMEI IMEI
     */
    public String getImei() {
        return imei;
    }

    /**
     * IMEI
     * @param imei IMEI
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * TMSI
     * @return STMSI TMSI
     */
    public String getStmsi() {
        return stmsi;
    }

    /**
     * TMSI
     * @param stmsi TMSI
     */
    public void setStmsi(String stmsi) {
        this.stmsi = stmsi;
    }

    /**
     * MAC地址
     * @return MAC MAC地址
     */
    public String getMac() {
        return mac;
    }

    /**
     * MAC地址
     * @param mac MAC地址
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 位置更新类型
     * @return LATYPE 位置更新类型
     */
    public String getLatype() {
        return latype;
    }

    /**
     * 位置更新类型
     * @param latype 位置更新类型
     */
    public void setLatype(String latype) {
        this.latype = latype;
    }

    /**
     * 是否目标手机：0 --- NORMAL；1--- 黑名单手机；2--- 特殊归属地手机
     * @return INDICATION 是否目标手机：0 --- NORMAL；1--- 黑名单手机；2--- 特殊归属地手机
     */
    public Byte getIndication() {
        return indication;
    }

    /**
     * 是否目标手机：0 --- NORMAL；1--- 黑名单手机；2--- 特殊归属地手机
     * @param indication 是否目标手机：0 --- NORMAL；1--- 黑名单手机；2--- 特殊归属地手机
     */
    public void setIndication(Byte indication) {
        this.indication = indication;
    }

    /**
     * 是否实时上报:00-  不实时上报;01-  实时上报
     * @return REALTIME 是否实时上报:00-  不实时上报;01-  实时上报
     */
    public Boolean getRealtime() {
        return realtime;
    }

    /**
     * 是否实时上报:00-  不实时上报;01-  实时上报
     * @param realtime 是否实时上报:00-  不实时上报;01-  实时上报
     */
    public void setRealtime(Boolean realtime) {
        this.realtime = realtime;
    }

    /**
     * 基站侧记录的上号时间
     * @return CAPTURE_TIME 基站侧记录的上号时间
     */
    public String getCaptureTime() {
        return captureTime;
    }

    /**
     * 基站侧记录的上号时间
     * @param captureTime 基站侧记录的上号时间
     */
    public void setCaptureTime(String captureTime) {
        this.captureTime = captureTime;
    }

    /**
     *
     * @return RARTA
     */
    public Long getRarta() {
        return rarta;
    }

    /**
     *
     * @param rarta
     */
    public void setRarta(Long rarta) {
        this.rarta = rarta;
    }

    /**
     * 归属地地名（省+市）
     * @return CITY_NAME 归属地地名（省+市）
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 归属地地名（省+市）
     * @param cityName 归属地地名（省+市）
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     *
     * @return CITY_CODE
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     *
     * @param cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 频段
     * @return BAND 频段
     */
    public String getBand() {
        return band;
    }

    /**
     * 频段
     * @param band 频段
     */
    public void setBand(String band) {
        this.band = band;
    }

    /**
     * 运营商
     * @return OPERATOR 运营商
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 运营商
     * @param operator 运营商
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 预警是否已读字段： 0 - 未确认；1-已确认；
     * @return IS_READ 预警是否已读字段： 0 - 未确认；1-已确认；
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * 预警是否已读字段： 0 - 未确认；1-已确认；
     * @param isRead 预警是否已读字段： 0 - 未确认；1-已确认；
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * 0未扫描解析、1已扫描解析
     * @return IS_PARSE 0未扫描解析、1已扫描解析
     */
    public Boolean getIsParse() {
        return isParse;
    }

    /**
     * 0未扫描解析、1已扫描解析
     * @param isParse 0未扫描解析、1已扫描解析
     */
    public void setIsParse(Boolean isParse) {
        this.isParse = isParse;
    }

    /**
     * 已发短信：0未发、1已发
     * @return IS_SMS 已发短信：0未发、1已发
     */
    public Boolean getIsSms() {
        return isSms;
    }

    /**
     * 已发短信：0未发、1已发
     * @param isSms 已发短信：0未发、1已发
     */
    public void setIsSms(Boolean isSms) {
        this.isSms = isSms;
    }

    /**
     * 目标名称
     * @return TARGET_NAME 目标名称
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * 目标名称
     * @param targetName 目标名称
     */
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    /**
     * 目标身份证号
     * @return TARGET_ID 目标身份证号
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * 目标身份证号
     * @param targetId 目标身份证号
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * 目标手机号
     * @return TARGET_PHONE 目标手机号
     */
    public String getTargetPhone() {
        return targetPhone;
    }

    /**
     * 目标手机号
     * @param targetPhone 目标手机号
     */
    public void setTargetPhone(String targetPhone) {
        this.targetPhone = targetPhone;
    }
}