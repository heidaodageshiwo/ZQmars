package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendStartRf {
	private String rfEnable;
	private String padding;
	
	public byte[] getByte() {
		StringBuffer sb=new StringBuffer();
		
		sb.append("00");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.rfEnable, 16)), 2));
		sb.append("0000");
		sb.append("00000000");
	
		return ConvertTools.hexStringToByte(sb.toString());
	}
	
	public String toString() {
		StringBuffer sb=new StringBuffer();
		
		sb.append("00");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.rfEnable)), 2));
		sb.append("0000");
		sb.append("00000000000000000000000000000000");
		
		return sb.toString();
	}
	
	public String getRfEnable() {
		return rfEnable;
	}
	public void setRfEnable(String rfEnable) {
		this.rfEnable = rfEnable;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
}
