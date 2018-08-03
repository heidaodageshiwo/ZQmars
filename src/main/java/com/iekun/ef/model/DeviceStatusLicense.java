package com.iekun.ef.model;

import java.io.Serializable;

public class DeviceStatusLicense  implements Serializable{

	String     isExpiration;
	String 	 	expirationTime;
	/**
	 * @return the isExpiration
	 */
	public String getIsExpiration() {
		return isExpiration;
	}
	/**
	 * @param isExpiration the isExpiration to set
	 */
	public void setIsExpiration(String isExpiration) {
		this.isExpiration = isExpiration;
	}
	/**
	 * @return the expirationTime
	 */
	public String getExpirationTime() {
		return expirationTime;
	}
	/**
	 * @param expirationTime the expirationTime to set
	 */
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	
	
	
}
