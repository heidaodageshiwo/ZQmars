package com.iekun.ef.model;

import java.util.List;

public class Resource {
    /**
    * 资源ID
    */
    private Long id;

    /**
    * 资源类型
    */
    private Byte type;

    /**
    * 资源编号
    */
    private String code;

    /**
    * 资源名称
    */
    private String name;
    
     /**
     * 显示资源名称
     */
     private String displayName;

    /**
    * 资源地址
    */
    private String url;
    
    /**
     * 图标地址
     * */
    private String icon;

    /**
    * 上级ID
    */
    private Long parentId;

    /**
    * 资源展示序号
    */
    private Integer orderNumber;

    /**
    * 显示图标
    */
    private String detail;

    /**
    * 删除标志：0-未删除；1-已删除
    */
    private Boolean deleteFlag;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 最后更新时间
    */
    private String updateTime;

    /**
    * 创建人ID
    */
    private Long creatorId;

    /**
    * 说明
    */
    private String remark;

    /**
    * 资源属性标志位:0- R;1- R/W; 2-Hide
    */
    private Byte attribute;
    
    private List<Resource> childMenus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getAttribute() {
        return attribute;
    }

    public void setAttribute(Byte attribute) {
        this.attribute = attribute;
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Resource> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Resource> childMenus) {
		this.childMenus = childMenus;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}