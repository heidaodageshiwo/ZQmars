package com.iekun.ef.jms.vo.send;

import java.util.List;

import com.iekun.ef.util.ConvertTools;

public class SendUpdateImsiLib {
	private int serialNumber;
	private int numInLib;
	private List<SendImsi> listRecvImsi;
	
	public byte[] getBytes()throws Exception{
		
		StringBuffer sb = new StringBuffer();
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.serialNumber)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.numInLib)), 8));
		
		for (int i = 0; i < listRecvImsi.size(); i++) {
			SendImsi recvImsi=listRecvImsi.get(i);
			sb.append(ConvertTools.StringImsi2Byte(recvImsi.getIbImsi()));
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(recvImsi.getProperty())), 2));
			sb.append("000000");
		}
		
		return ConvertTools.hexStringToByte(sb.toString());
	}
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		//sb.append("00000000");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.serialNumber)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.numInLib)), 8));
		
		for (int i = 0; i < listRecvImsi.size(); i++) {
			SendImsi recvImsi=listRecvImsi.get(i);
			sb.append(ConvertTools.fillZero(recvImsi.getIbImsi(), 16));
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(recvImsi.getProperty())), 2));
			sb.append("000000");
		}
		
		return sb.toString();
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

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
}
