package com.iekun.ef.model;

public class SiteAreaMapping {

    /**
     * 主键ID
     * */
    private String id;

    /**
     * 分组id
     * */
    private String warningId;

    /**
     * 区域Id
     * */
    private String areaId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarningId() {
        return warningId;
    }

    public void setWarningId(String warningId) {
        this.warningId = warningId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public SiteAreaMapping(String id, String warningId, String areaId) {
        this.id = id;
        this.warningId = warningId;
        this.areaId = areaId;
    }

    public SiteAreaMapping(){}
}
