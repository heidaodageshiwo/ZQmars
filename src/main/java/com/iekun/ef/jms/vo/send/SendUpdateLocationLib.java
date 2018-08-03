package com.iekun.ef.jms.vo.send;

import java.util.List;

import com.iekun.ef.util.ConvertTools;

public class SendUpdateLocationLib {

	private int serialNumber;
	private int numInLib;
	private List<SendLocation> listRecvLocation;
	
	public String toString(){
		
		@SuppressWarnings("unused")
		String provinceCode = null;
		@SuppressWarnings("unused")
		String cityCode = null;
		StringBuffer sb = new StringBuffer();
		String provinceStr = null;
		//sb.append("00000000");
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.serialNumber)), 8));
		sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.numInLib)), 8));
		
		for (int i = 0; i < listRecvLocation.size(); i++) {
			SendLocation sendLocation=listRecvLocation.get(i);
			provinceStr = sendLocation.getIbProvinceCity();
			provinceCode = provinceStr.substring(0,2); //取前两位
			cityCode = provinceStr.substring(2,4); //取后两位
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(provinceCode)), 2));
			sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(cityCode)), 2));
			sb.append("000000000000");
		}
		
		return sb.toString();
	}

	

	public int getNumInLib() {
		return numInLib;
	}

	public void setNumInLib(int numInLib) {
		this.numInLib = numInLib;
	}

	public List<SendLocation> getListRecvLocation() {
		return listRecvLocation;
	}

	public void setListRecvLocation(List<SendLocation> listRecvLocation) {
		this.listRecvLocation = listRecvLocation;
	}



	public int getSerialNumber() {
		return serialNumber;
	}



	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
