package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendDataSendTypeCfg {

	String 	realtimesend;
	String 	interalmin;
	String 	uecountsend;
	String 	padding;
	public String getRealtimesend() {
		return realtimesend;
	}
	public void setRealtimesend(String realtimesend) {
		this.realtimesend = realtimesend;
	}
	public String getInteralmin() {
		return interalmin;
	}
	public void setInteralmin(String interalmin) {
		this.interalmin = interalmin;
	}
	public String getUecountsend() {
		return uecountsend;
	}
	public void setUecountsend(String uecountsend) {
		this.uecountsend = uecountsend;
	}
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.realtimesend)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.interalmin)), 2));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.uecountsend)), 4));
		sb.append("00000000");
		return sb.toString();
	}
}
