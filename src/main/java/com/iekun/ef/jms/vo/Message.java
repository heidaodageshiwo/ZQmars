package com.iekun.ef.jms.vo;


public class Message{

	private String head;
	private String pktType;
	private String serialNum;
	private int dataLength;
	private String sessionId;
	private Object subOject;
	
	public Message(){}
	
	public Message(String head, String pktType, String serialNum, int dataLength, String sessionId, Object subOject){
		this.head = head;
		this.pktType = pktType;
		this.serialNum = serialNum;
		this.dataLength = dataLength;
		this.sessionId = sessionId;
		this.subOject = subOject;
	}
	
	public String getHead() {
		return head;
	}

	public String getPktType() {
		return pktType;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public int getDataLength() {
		return dataLength;
	}

	public Object getSubOject() {
		return subOject;
	}
	public void setSubOject(Object subOject) {
		this.subOject = subOject;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public void setPktType(String pktType) {
		this.pktType = pktType;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
