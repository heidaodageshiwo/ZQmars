package com.iekun.ef.model;

import java.io.Serializable;

public class DeviceStatusDebug implements Serializable{

	int numberOfConnectedUEs;
	
	String  isDspHearbeatRecv;
	
	int RateMsg4PkMsg3;
	
	int RateMsg5PkMsg4;
	
	int count_RACH;
	
	int count_schel_MSG3;				//the count of sched msg3
	
	int count_MSG3_success;				//the msg3 count of success
	
	int count_MSG4;						//MSG4
	
	int count_MSG5;						//MSG5
	
	int count_UE_L3;
	

	public int getNumberOfConnectedUEs() {
		return numberOfConnectedUEs;
	}

	public void setNumberOfConnectedUEs(int numberOfConnectedUEs) {
		this.numberOfConnectedUEs = numberOfConnectedUEs;
	}

	public int getRateMsg4PkMsg3() {
		return RateMsg4PkMsg3;
	}

	public void setRateMsg4PkMsg3(int rateMsg4PkMsg3) {
		RateMsg4PkMsg3 = rateMsg4PkMsg3;
	}

	public int getRateMsg5PkMsg4() {
		return RateMsg5PkMsg4;
	}

	public void setRateMsg5PkMsg4(int rateMsg5PkMsg4) {
		RateMsg5PkMsg4 = rateMsg5PkMsg4;
	}

	public int getCount_RACH() {
		return count_RACH;
	}

	public void setCount_RACH(int count_RACH) {
		this.count_RACH = count_RACH;
	}

	public int getCount_schel_MSG3() {
		return count_schel_MSG3;
	}

	public void setCount_schel_MSG3(int count_schel_MSG3) {
		this.count_schel_MSG3 = count_schel_MSG3;
	}

	public int getCount_MSG3_success() {
		return count_MSG3_success;
	}

	public void setCount_MSG3_success(int count_MSG3_success) {
		this.count_MSG3_success = count_MSG3_success;
	}

	public int getCount_MSG4() {
		return count_MSG4;
	}

	public void setCount_MSG4(int count_MSG4) {
		this.count_MSG4 = count_MSG4;
	}

	public int getCount_MSG5() {
		return count_MSG5;
	}

	public void setCount_MSG5(int count_MSG5) {
		this.count_MSG5 = count_MSG5;
	}

	public int getCount_UE_L3() {
		return count_UE_L3;
	}

	public void setCount_UE_L3(int count_UE_L3) {
		this.count_UE_L3 = count_UE_L3;
	}

	/**
	 * @return the isDspHearbeatRecv
	*/
	public String getIsDspHearbeatRecv() {
		return isDspHearbeatRecv;
	}

	/**
	 * @param isDspHearbeatRecv the isDspHearbeatRecv to set
	 */
	public void setIsDspHearbeatRecv(String isDspHearbeatRecv) {
		this.isDspHearbeatRecv = isDspHearbeatRecv;
	}	
	
}
