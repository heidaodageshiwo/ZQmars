package com.iekun.ef.jms.vo.receive;

public class RcvStartNotify {

	String  reason;
    int 	license;
 
    String   fpgaVersion;
    String   BBUVersion;
    String   softwareVersion;
    
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getLicense() {
		return license;
	}
	public void setLicense(int license) {
		this.license = license;
	}
	public String getFpgaVersion() {
		return fpgaVersion;
	}
	public void setFpgaVersion(String fpgaVersion) {
		this.fpgaVersion = fpgaVersion;
	}
	public String getBBUVersion() {
		return BBUVersion;
	}
	public void setBBUVersion(String bBUVersion) {
		BBUVersion = bBUVersion;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
}
