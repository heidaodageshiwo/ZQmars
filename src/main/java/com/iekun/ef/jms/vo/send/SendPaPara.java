package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendPaPara {

	String 		rfSysNo;
	String		padding1;
	String		PowerAttenuation;
	String		padding2;
	public String getRfSysNo() {
		return rfSysNo;
	}
	public void setRfSysNo(String rfSysNo) {
		this.rfSysNo = rfSysNo;
	}
	public String getPadding1() {
		return padding1;
	}
	public void setPadding1(String padding1) {
		this.padding1 = padding1;
	}
	public String getPowerAttenuation() {
		return PowerAttenuation;
	}
	public void setPowerAttenuation(String powerAttenuation) {
		PowerAttenuation = powerAttenuation;
	}
	public String getPadding2() {
		return padding2;
	}
	public void setPadding2(String padding2) {
		this.padding2 = padding2;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.rfSysNo)), 2));
		sb.append("00");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.PowerAttenuation)), 4));
		sb.append("00000000");
		return sb.toString();
	}	
}
