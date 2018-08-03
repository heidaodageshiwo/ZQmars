package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendAdjustTime {

	String  padding;		
	String 	sadjustsecond;
	String 	sadjustusecond;
	String 	csecond;
	String 	cusecond;
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	public String getSadjustsecond() {
		return sadjustsecond;
	}
	public void setSadjustsecond(String sadjustsecond) {
		this.sadjustsecond = sadjustsecond;
	}
	public String getSadjustusecond() {
		return sadjustusecond;
	}
	public void setSadjustusecond(String sadjustusecond) {
		this.sadjustusecond = sadjustusecond;
	}
	public String getCsecond() {
		return csecond;
	}
	public void setCsecond(String csecond) {
		this.csecond = csecond;
	}
	public String getCusecond() {
		return cusecond;
	}
	public void setCusecond(String cusecond) {
		this.cusecond = cusecond;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("00000000");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.sadjustsecond)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.sadjustusecond)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.csecond)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.cusecond)), 8));
		
		return sb.toString();
	}	
	
}
