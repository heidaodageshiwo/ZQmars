package com.iekun.ef.model;

public class Whitelist {

    /**
     * 主键ID
     * */
    private String id;

    /**
     * imsi号码
     * */
    private String imsi;

    /**
     * 手机号码
     * */
    private String phone;

    /**
     * 姓名
     * */
    private String name;

    /**
     * 运营商
     * */
    private String operator;

    /**
     * 归属地
     * */
    private String ownership;

    /**
     * 所属机构
     * */
    private String organization;

    /**
     * 创建人ID
     * */
    private long creatorId;

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Whitelist(String id, String imsi, String phone, String name, String operator, String ownership, String organization, long creatorId) {
        this.id = id;
        this.imsi = imsi;
        this.phone = phone;
        this.name = name;
        this.operator = operator;
        this.ownership = ownership;
        this.organization = organization;
        this.creatorId = creatorId;
    }

    public Whitelist(){}
}
