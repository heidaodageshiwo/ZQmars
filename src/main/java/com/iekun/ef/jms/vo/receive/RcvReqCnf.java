package com.iekun.ef.jms.vo.receive;


public class RcvReqCnf {
	
	private String cnfType;
	private int cnfInd;
	private int sessionId;

	public String getCnfType() {
		return cnfType;
	}
	public void setCnfType(String cnfType) {
		this.cnfType = cnfType;
	}

	public int getCnfInd() {
		return cnfInd;
	}
	public void setCnfInd(int cnfInd) {
		this.cnfInd = cnfInd;
	}
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

}
