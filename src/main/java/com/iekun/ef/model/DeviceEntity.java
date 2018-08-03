package com.iekun.ef.model;

public class DeviceEntity {

    /**
     * 子设备ID
     */
    private Long id;

    /**
     * 设备所属站点
     */
    private Site site;

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

    /**
     * 设备最近告警短信发送时间
     */
    private String dvLatestAlarmSmsSendTime;

    /*设备最近告警邮件发送时间*/
    private String dvLatestAlarmEmailSendTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
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
