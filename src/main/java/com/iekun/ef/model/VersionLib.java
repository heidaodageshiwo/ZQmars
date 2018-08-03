package com.iekun.ef.model;

public class VersionLib {
    /**
    * 设备版本ID
    */
    private Long id;

    /**
    * 版本类型:01-FPGA BBU,SW;02-FPGA BBU;03-FPGA SW;04-BBU SW;05-FPGA;06-BBU;07-SW
    */
    private Byte versionType;

    /**
    * 设备总版本号
    */
    private String version;

    /**
    * FPGA版本号
    */
    private String fpgaVersion;

    /**
    * BBU版本号
    */
    private String bbuVersion;

    /**
    * 软件版本号
    */
    private String swVersion;

    /**
    * 上传人的用户名
    */
    private String uploadUser;

    /**
    * 上传的时间
    */
    private String uploadTime;

    /**
    * 文件保存路径
    */
    private String fileUrl;

    /**
    * 说明
    */
    private String remark;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 删除标记: 0-  正常，  1-  删除
    */
    private Boolean deleteFlag;

    /**
    * 检验和
    */
    private String checksum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getVersionType() {
        return versionType;
    }

    public void setVersionType(Byte versionType) {
        this.versionType = versionType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFpgaVersion() {
        return fpgaVersion;
    }

    public void setFpgaVersion(String fpgaVersion) {
        this.fpgaVersion = fpgaVersion;
    }

    public String getBbuVersion() {
        return bbuVersion;
    }

    public void setBbuVersion(String bbuVersion) {
        this.bbuVersion = bbuVersion;
    }

    public String getSwVersion() {
        return swVersion;
    }

    public void setSwVersion(String swVersion) {
        this.swVersion = swVersion;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}