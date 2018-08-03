package com.iekun.ef.model;

public class NewHistoryParamsEntity {
    public String tableName = "";
    public String startTime = "";
    public String endTime = "";
    public String siteSn = "";
    public String deviceSn = "";
    public String imsi = "";
    public String imei = "";
    public String operator = "";
    public String area = "";
    public int sCount = 0;
    public int wCount = 0;
    public String filterStart = "";
    public String filterEnd = "";

    public String getFilterStart() {
        return filterStart;
    }

    public void setFilterStart(String filterStart) {
        this.filterStart = filterStart;
    }

    public String getFilterEnd() {
        return filterEnd;
    }

    public void setFilterEnd(String filterEnd) {
        this.filterEnd = filterEnd;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSiteSn() {
        return siteSn;
    }

    public void setSiteSn(String siteSn) {
        this.siteSn = siteSn;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getsCount() {
        return sCount;
    }

    public void setsCount(int sCount) {
        this.sCount = sCount;
    }

    public int getwCount() {
        return wCount;
    }

    public void setwCount(int wCount) {
        this.wCount = wCount;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }
}
