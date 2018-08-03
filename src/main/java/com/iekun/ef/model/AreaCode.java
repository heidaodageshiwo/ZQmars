package com.iekun.ef.model;

public class AreaCode {
    private Long id;

    /**
    * 地区名称
    */
    private String name;

    /**
    * 父节点
    */
    private Long parentid;

    /**
    * 地区简称
    */
    private String shortName;

    /**
    * 级别
    */
    private Byte level;

    /**
    * 区号
    */
    private String cityCode;

    /**
    * 邮编
    */
    private String zipCode;

    /**
    * 地区全称
    */
    private String mergerName;

    /**
    * 经度
    */
    private String lng;

    /**
    * 纬度
    */
    private String lat;

    /**
    * 拼音
    */
    private String pinyin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}