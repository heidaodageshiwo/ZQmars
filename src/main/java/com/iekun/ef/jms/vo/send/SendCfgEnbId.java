package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendCfgEnbId {

	String	identity;
	String	padding1;
	String	padding2;
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getPadding1() {
		return padding1;
	}
	public void setPadding1(String padding1) {
		this.padding1 = padding1;
	}
	public String getPadding2() {
		return padding2;
	}
	public void setPadding2(String padding2) {
		this.padding2 = padding2;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.identity)), 4));
		sb.append("0000");
		sb.append("00000000");
		sb.append("00000000");
		sb.append("00000000");
		sb.append("00000000");
		return sb.toString();
	}	
	
}
