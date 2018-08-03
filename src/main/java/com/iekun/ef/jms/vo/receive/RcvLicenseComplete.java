package com.iekun.ef.jms.vo.receive;

public class RcvLicenseComplete {

	 int	   	code;
     String   	padding;
     String     message;
     String     licenseTime;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLicenseTime() {
		return licenseTime;
	}
	public void setLicenseTime(String licenseTime) {
		this.licenseTime = licenseTime;
	}
}
