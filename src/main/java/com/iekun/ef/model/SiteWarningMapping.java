package com.iekun.ef.model;

public class SiteWarningMapping {
    private long siteId;

    private String warningId;

    private long provinceId;

    public long getSiteId() {
        return siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public String getWarningId() {
        return warningId;
    }

    public void setWarningId(String warningId) {
        this.warningId = warningId;
    }

    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

    public SiteWarningMapping(long siteId, String warningId, long provinceId) {
        this.siteId = siteId;
        this.warningId = warningId;
        this.provinceId = provinceId;
    }

    public SiteWarningMapping(){}
}
