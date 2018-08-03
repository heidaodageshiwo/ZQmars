package com.iekun.ef.jms.vo.send;

import java.math.BigInteger;

import com.iekun.ef.util.ConvertTools;

public class SendCtrlRange {
	String		open;
	String	    lev;
	String		padding2;
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
	public String getPadding2() {
		return padding2;
	}
	public void setPadding2(String padding2) {
		this.padding2 = padding2;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.open)), 2));
		Byte bb = Byte.parseByte(this.lev);
		byte aa = bb.byteValue();
		//int ddddd = Integer.parseInt(this.lev);
		if (aa < 0)
		{
			/*int cc = ~aa + 1 + 0x80;
			Integer dd = new Integer(cc);*/
			
		    int cc = aa; 
	        cc = cc&0x000000ff;
			Integer dd = new Integer(cc);
			
			sb.append(ConvertTools.fillZero(Integer.toHexString(dd), 2));
		}
		else
		{
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.lev)), 2));
		}
		sb.append("0000");
		return sb.toString();
	}	
}
