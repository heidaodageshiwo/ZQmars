package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendNowPara {
	private String nowType;
	private String padding;
	
	public byte[] getByte() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("0000");
		sb.append(ConvertTools.fillZero(this.nowType, 4));
		sb.append("00000000");
		
		return ConvertTools.hexStringToByte(sb.toString());
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("0000");
		sb.append(ConvertTools.fillZero(this.nowType, 4));
		sb.append("00000000000000000000000000000000");
		
		return sb.toString();
	}
	
	public String getNowType() {
		return nowType;
	}
	public void setNowType(String nowType) {
		this.nowType = nowType;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
}
