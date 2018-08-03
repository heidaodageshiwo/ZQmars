package com.iekun.ef.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iekun.ef.util.CustomJsonDateDeserializer;
import com.iekun.ef.util.CustomJsonDateSerializer;

import java.util.Date;

/**
 * 设备上号异常配置Model
 */
public class DeviceUpNumExModel {

    //编号
    private Integer id;
    //设备id
    private String deviceId;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //告警时间间隔
    private Integer interval;
    //是否有效
    private Integer isValid = 1;
    //备注
    private String remark = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    @JsonSerialize(using = CustomJsonDateSerializer.class)
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @JsonSerialize(using = CustomJsonDateSerializer.class)
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
