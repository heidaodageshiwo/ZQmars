package com.iekun.ef.model;

public class User {
    /**
    * 用户ID
    */
    private Long id;

    /**
    * 登录用户名
    */
    private String loginName;

    /**
    * 用户姓名
    */
    private String userName;

    /**
    * 性别:0-男，1-女
    */
    private Integer sex;

    /**
    * 登陆密码
    */
    private String password;

    /**
    * 电子邮件
    */
    private String email;

    /**
    * 手机
    */
    private String mobilePhone;

    /**
     * 头像存储所在的URL
     * */
    private String avator;
    
    /**
    * 说明
    */
    private String remark;

    /**
    * 删除标志：0-未删除；1-已删除
    */
    private int deleteFlag;

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
    * 密码SALT
    */
    private String salt;

    /**
    * 锁定标志：0-否；1-是
    */
    private int locked;

    /**
    * 角色ID
    */
    private Long roleId;

    
    
    public User() {
		super();
	}
    
    public User( Long userId, String userName, Integer sex, String password, String email,String mobilePhone, String updateTime)
    {
    	super();
    	this.id = userId;
    	this.userName = userName;
		this.sex = sex;
		this.password = password;
		this.email = email;
		this.mobilePhone = mobilePhone;
		this.updateTime = updateTime;
    	
    }
	public User(String loginName, long roleId, String userName,  Integer sex,  String password, String email,String mobilePhone, String remark,int locked) {
		super();
		this.loginName = loginName;
		this.roleId = roleId;
		this.userName = userName;
		this.sex = sex;
		this.password = password;
		this.email = email;
		this.mobilePhone = mobilePhone;
		this.remark = remark;
		this.locked = locked;
		this.deleteFlag = 0;		
	}
	
	public User(long id,long roleId, String userName, Integer sex, String  email, String mobilePhone,String remark,int locked)
	{
		super();
		this.id = id;
		this.roleId = roleId;
		this.userName = userName;
		this.sex = sex;
		this.email = email;
		this.mobilePhone = mobilePhone;
		this.remark = remark;
		this.locked = locked;
		
	}
	
	public User(long id,String password)
	{
		super();
		this.id = id;
		this.password = password;
	}
	
	public User(long id,int locked)
	{
		super();
		this.id = id;
		this.locked = locked;
	}
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

	public String getAvator() {
		return avator;
	}

	public void setAvator(String avator) {
		this.avator = avator;
	}
}