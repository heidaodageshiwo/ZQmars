package com.iekun.ef.model;

import java.util.List;

/**
 * 站点实体类（包含设备列表）
 */
public class SiteEntity {

    /**
     * 站点ID
     */
    private Long id;

    /**
     * 站点编号
     */
    private String sn;

    /**
     * 站点名称
     */
    private String name;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 站点详细地址
     */
    private String address;

    /**
     * 说明
     */
    private String remark;

    /**
     * 删除标记: 0-  正常，  1-  删除
     */
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建时间
     */
    private String updateTime;

    /**
     * 区号
     */
    private String cityCode;

    /**
     *  省ID
     */
    private Long province_id;

    /**
     *  市ID
     * */
    private Long city_id;

    /**
     * 区ID
     * */
    private Long town_id;

    /**
     *  省名称
     */
    private String province_name;

    /**
     *  市名称
     * */
    private String city_name;

    /**
     * 区名称
     * */
    private String town_name;

    /**
     * 地区编码（邮编）
     */
    private String zipCode;

    private String lc;

    private String ci;

    private List<DeviceEntity> deviceEntityList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getProvince_id() {
        return province_id;
    }

    public void setProvince_id(Long province_id) {
        this.province_id = province_id;
    }

    public Long getCity_id() {
        return city_id;
    }

    public void setCity_id(Long city_id) {
        this.city_id = city_id;
    }

    public Long getTown_id() {
        return town_id;
    }

    public void setTown_id(Long town_id) {
        this.town_id = town_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getTown_name() {
        return town_name;
    }

    public void setTown_name(String town_name) {
        this.town_name = town_name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public List<DeviceEntity> getDeviceEntityList() {
        return deviceEntityList;
    }

    public void setDeviceEntityList(List<DeviceEntity> deviceEntityList) {
        this.deviceEntityList = deviceEntityList;
    }
}
