package com.iekun.ef.jms.vo.receive;


public class RcvNowPara {
	
	private String nowType;
	private int dataLength;
	private String nowPara;
	private String serialNum;
	private int sessionId;
	
	public String getNowType() {
		return nowType;
	}
	public void setNowType(String nowType) {
		this.nowType = nowType;
	}

	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}


	public int getDataLength() {
		return dataLength;
	}


	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}


	public String getNowPara() {
		return nowPara;
	}


	public void setNowPara(String nowPara) {
		this.nowPara = nowPara;
	}


	public int getSessionId() {
		return sessionId;
	}


	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
}
