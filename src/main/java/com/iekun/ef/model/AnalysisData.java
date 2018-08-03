package com.iekun.ef.model;

public class AnalysisData {
private String id;
private String sitename  ;
    private String  sitesn  ;

    private String  devicesn;
    private String  operator;
    private String cityname  ;
    private String imsi  ;
    private String  datacount  ;
    private String   daycount   ;
    private String    one;
    private String two;
    private String capturetime;

    public void setCapturetime(String capturetime) {
        this.capturetime = capturetime;
    }

    public String getCapturetime() {

        return capturetime;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getOne() {

        return one;
    }

    public String getTwo() {
        return two;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public void setSitesn(String sitesn) {
        this.sitesn = sitesn;
    }

    public void setDevicesn(String devicesn) {
        this.devicesn = devicesn;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public void setDatacount(String datacount) {
        this.datacount = datacount;
    }

    public void setDaycount(String daycount) {
        this.daycount = daycount;
    }

    public String getId() {

        return id;
    }

    public String getSitename() {
        return sitename;
    }

    public String getSitesn() {
        return sitesn;
    }

    public String getDevicesn() {
        return devicesn;
    }

    public String getOperator() {
        return operator;
    }

    public String getCityname() {
        return cityname;
    }

    public String getImsi() {
        return imsi;
    }

    public String getDatacount() {
        return datacount;
    }

    public String getDaycount() {
        return daycount;
    }
}