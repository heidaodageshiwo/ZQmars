package com.iekun.ef.model;

public class DeviceUser {
	
	/**
	* 设备序列号
	*/
	private String sn;
	
	
	/**
	* siteId
	*/
	private Long siteId;
	
	/**
	* 用户id
	*/
	private Long userId;
	
	
	public String getSn() {
			return sn;
		}
	
	
	public void setSn(String sn) {
		this.sn = sn;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
