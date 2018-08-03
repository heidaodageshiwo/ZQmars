package com.iekun.ef.jms.vo.receive;

import java.util.List;

import com.iekun.ef.jms.vo.receive.paDetailInfo;

public class RcvPaInfos {

	private int countPaInfo;
	private List<paDetailInfo> paDetailInfoList;
	
	
	 /* @return the countPaInfo
	 */
	public int getCountPaInfo() {
		return countPaInfo;
	}
	/**
	 * @param countPaInfo the countPaInfo to set
	 */
	public void setCountPaInfo(int countPaInfo) {
		this.countPaInfo = countPaInfo;
	}
	/**
	 * @return the paDetailInfoList
	 */
	public List<paDetailInfo> getPaDetailInfoList() {
		return paDetailInfoList;
	}
	/**
	 * @param paDetailInfoList the paDetailInfoList to set
	 */
	public void setPaDetailInfoList(List<paDetailInfo> paDetailInfoList) {
		this.paDetailInfoList = paDetailInfoList;
	}
	
	
}
