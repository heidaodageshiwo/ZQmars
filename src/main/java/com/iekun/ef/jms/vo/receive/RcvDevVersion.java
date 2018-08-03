package com.iekun.ef.jms.vo.receive;

public class RcvDevVersion {
	
	private int license;
	private String fpgaVersion;
	private String bbuVersion;
	private String softWareVersion;
	private String serialNum;
	private int sessionId;
	
	public String getFpgaVersion() {
		return fpgaVersion;
	}
	public void setFpgaVersion(String fpgaVersion) {
		this.fpgaVersion = fpgaVersion;
	}
	public String getBbuVersion() {
		return bbuVersion;
	}
	public void setBbuVersion(String bbuVersion) {
		this.bbuVersion = bbuVersion;
	}
	public String getSoftWareVersion() {
		return softWareVersion;
	}
	public void setSoftWareVersion(String softWareVersion) {
		this.softWareVersion = softWareVersion;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public int getLicense() {
		return license;
	}
	public void setLicense(int license) {
		this.license = license;
	}
	
}
