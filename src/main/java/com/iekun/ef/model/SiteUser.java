package com.iekun.ef.model;

public class SiteUser {
    /**
    * 设备、结构关联ID
    */
    private Long id;

    /**
    * 设备ID
    */
    private Long siteId;

    /**
    * 拓扑图结构节点ID
    */
    private Long userId;

    /**
    * 删
    * 除标记: 0-  正常，  1-  删除
    */
        
    private Integer deleteFlag;
    
    public SiteUser()
    {
    	super();
    }
    
    public SiteUser(Long siteId, Long userId, Integer action){
    	super();
    	this.siteId = siteId;
    	this.userId = userId;
    	this.deleteFlag = action;
    	
    }
    
    public SiteUser(Long siteId, Long userId){
    	super();
    	this.siteId = siteId;
    	this.userId = userId;
    	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}