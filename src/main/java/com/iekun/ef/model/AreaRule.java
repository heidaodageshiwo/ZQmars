package com.iekun.ef.model;

public class AreaRule {
    /**
    * 归属地预警列表ID
    */
    private Long id;

    /**
    * 源归属区号
    */
    private String sourceCityCode;
    
    
    /**
     * 源归属城市名
     */
    private String sourceCityName;
     
     
     /**
      * 预警的名字
      */
    private String name;
      
	/**
	 * 预警的名字
     */
	private String remark;
    /**
    * 目的地区号
    */
    private String destCityCode;

    /**
    * (保留字段)规则：0- include;1- exclude;2-
    */
    private Byte policy;

    /**
    * (保留字段)
    */
    private Byte policyPriority;

    /**
    * 创建人ID
    */
    private Long creatorId;

    /**
    * 创建时间
    */
    private String createTime;

    /**
    * 删除标记: 0-  正常，  1-  删除
    */
    private Integer deleteFlag;

    /**
     * 接受人员列表
     */
    private String receiver;
    
    
    public AreaRule()
    {
    	super();
    }
    
    public AreaRule(String name, String cityName, String cityCode, String remark, String receivers)
    {
    	super();
    	this.name = name;
    	this.sourceCityName = cityName;
    	this.sourceCityCode = cityCode;
    	this.remark = remark;
    	this.receiver = receivers;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceCityCode() {
        return sourceCityCode;
    }

    public void setSourceCityCode(String sourceCityCode) {
        this.sourceCityCode = sourceCityCode;
    }

    public String getDestCityCode() {
        return destCityCode;
    }

    public void setDestCityCode(String destCityCode) {
        this.destCityCode = destCityCode;
    }

    public Byte getPolicy() {
        return policy;
    }

    public void setPolicy(Byte policy) {
        this.policy = policy;
    }

    public Byte getPolicyPriority() {
        return policyPriority;
    }

    public void setPolicyPriority(Byte policyPriority) {
        this.policyPriority = policyPriority;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceCityName() {
		return sourceCityName;
	}

	public void setSourceCityName(String sourceCityName) {
		this.sourceCityName = sourceCityName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}