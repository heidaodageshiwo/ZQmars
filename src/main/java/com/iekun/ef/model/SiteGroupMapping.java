package com.iekun.ef.model;

public class SiteGroupMapping {
    long id;
    long siteId;
    long groupId;

    public long getId() {
        return id;
    }

    public long getSiteId() {
        return siteId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public SiteGroupMapping(long id, long siteId, long groupId) {
        this.id = id;
        this.siteId = siteId;
        this.groupId = groupId;
    }

    public SiteGroupMapping(){}
}
