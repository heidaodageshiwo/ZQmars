package com.iekun.ef.jms.vo.send;

import java.util.List;

import com.iekun.ef.util.ConvertTools;

public class SendvibOper {
	private String padding;
	private int numInLib;
	private List<SendImsi> listRecvImsi;
	
	public byte[] getBytes()throws Exception{
		
		StringBuffer sb = new StringBuffer();
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.padding)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.numInLib)), 8));
		
		for (int i = 0; i < listRecvImsi.size(); i++) {
			SendImsi recvImsi=listRecvImsi.get(i);
			sb.append(ConvertTools.fillZero(ConvertTools.bytesToHexString(recvImsi.getIbImsi().getBytes()), 16));
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(recvImsi.getPadding())), 6));
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(recvImsi.getProperty())), 2));
		}
		
		return ConvertTools.hexStringToByte(sb.toString());
	}
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.padding)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.numInLib)), 8));
		
		for (int i = 0; i < listRecvImsi.size(); i++) {
			SendImsi recvImsi=listRecvImsi.get(i);
			sb.append(ConvertTools.fillZero(ConvertTools.bytesToHexString(recvImsi.getIbImsi().getBytes()), 16));
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(recvImsi.getPadding())), 6));
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(recvImsi.getProperty())), 2));
		}
		
		return sb.toString();
	}
	
	
	public String getPadding() {
		return padding;
	}
	public void setPadding(String padding) {
		this.padding = padding;
	}
	public int getNumInLib() {
		return numInLib;
	}
	public void setNumInLib(int numInLib) {
		this.numInLib = numInLib;
	}
	public List<SendImsi> getListRecvImsi() {
		return listRecvImsi;
	}
	public void setListRecvImsi(List<SendImsi> listRecvImsi) {
		this.listRecvImsi = listRecvImsi;
	}
}
