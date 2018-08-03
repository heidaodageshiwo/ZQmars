package com.iekun.ef.jms.vo.receive;

import java.util.List;

public class RcvUeInfos{
	
	private String padding;
	private int countUinfo;
	private List<RcvUeInfo> ueInfoList;
	
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	public int getCountUinfo() {
		return countUinfo;
	}
	public void setCountUinfo(int countUinfo) {
		this.countUinfo = countUinfo;
	}
	public List<RcvUeInfo> getUeInfoList() {
		return ueInfoList;
	}
	public void setUeInfoList(List<RcvUeInfo> ueInfoList) {
		this.ueInfoList = ueInfoList;
	}

}