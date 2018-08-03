package com.iekun.ef.model;

import java.io.Serializable;

public class DeviceStatusSniffer implements Serializable{


	int     valid;          //是否开启标识
	int  	syncStatus; 	//同步状态
	int 	PCI;			//同步小区的功率
	int 	power;			//同步小区的PCI
	int 	frequency;		//同步小区的频率
	
	public int getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}
	public int getPCI() {
		return PCI;
	}
	public void setPCI(int pCI) {
		PCI = pCI;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	
}
