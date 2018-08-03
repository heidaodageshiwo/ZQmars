package com.iekun.ef.model;

import java.io.Serializable;

public class DeviceStatusEnviroment implements Serializable{

	
	String 		boardTemperature;
	String      cpuLoader;
	String 	    rfEnable;
	
	/**
	 * @return the boardTemperature
	 */
	public String getBoardTemperature() {
		return boardTemperature;
	}
	/**
	 * @param boardTemperature the boardTemperature to set
	 */
	public void setBoardTemperature(String boardTemperature) {
		this.boardTemperature = boardTemperature;
	}
	/**
	 * @return the cpuLoader
	 */
	public String getCpuLoader() {
		return cpuLoader;
	}
	/**
	 * @param cpuLoader the cpuLoader to set
	 */
	public void setCpuLoader(String cpuLoader) {
		this.cpuLoader = cpuLoader;
	}
	/**
	 * @return the rfEnable
	 */
	public String getRfEnable() {
		return rfEnable;
	}
	/**
	 * @param rfEnable the rfEnable to set
	 */
	public void setRfEnable(String rfEnable) {
		this.rfEnable = rfEnable;
	}

	
	
	
	
	
}
