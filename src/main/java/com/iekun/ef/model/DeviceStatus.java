package com.iekun.ef.model;

import java.io.Serializable;

public class DeviceStatus implements Serializable{

	/* 设备一般的状态信息 */
	private DeviceStatusCommon deviceStatusCommon;
	
	/* 设备调试状态信息 */
	private DeviceStatusDebug deviceStatusDebug;
	
	/* 设备环境状态信息 */
	private DeviceStatusEnviroment deviceStatusEnviroment;
	
	/* 设备许可状态信息 */
    private DeviceStatusLicense  deviceStatusLicense;
    
    /* 设备功放状态信息 */
    private DeviceStatusPowerAmplifier deviceStatusPowerAmplifier;
    
    /* 设备功放基本信息 */
    private DevicePowerAmplifierInfo  devicePowerAmplifierInfo;
        
    /* 设备sniffer信息 */
    private DeviceStatusSniffer deviceStatusSniffer;

    /* 设备工作模式 */
    private DeviceStatusWorkMode deviceStatusWorkMode;

    public DeviceStatusCommon getDeviceStatusCommon() {
		return deviceStatusCommon;
	}

	public void setDeviceStatusCommon(DeviceStatusCommon deviceStatusCommon) {
		this.deviceStatusCommon = deviceStatusCommon;
	}

	public DeviceStatusDebug getDeviceStatusDebug() {
		return deviceStatusDebug;
	}

	public void setDeviceStatusDebug(DeviceStatusDebug deviceStatusDebug) {
		this.deviceStatusDebug = deviceStatusDebug;
	}

	public DeviceStatusEnviroment getDeviceStatusEnviroment() {
		return deviceStatusEnviroment;
	}

	public void setDeviceStatusEnviroment(DeviceStatusEnviroment deviceStatusEnviroment) {
		this.deviceStatusEnviroment = deviceStatusEnviroment;
	}

	public DeviceStatusLicense getDeviceStatusLicense() {
		return deviceStatusLicense;
	}

	public void setDeviceStatusLicense(DeviceStatusLicense deviceStatusLicense) {
		this.deviceStatusLicense = deviceStatusLicense;
	}

	public DeviceStatusPowerAmplifier getDeviceStatusPowerAmplifier() {
		return deviceStatusPowerAmplifier;
	}

	public void setDeviceStatusPowerAmplifier(DeviceStatusPowerAmplifier deviceStatusPowerAmplifier) {
		this.deviceStatusPowerAmplifier = deviceStatusPowerAmplifier;
	}

	public DeviceStatusSniffer getDeviceStatusSniffer() {
		return deviceStatusSniffer;
	}

	public void setDeviceStatusSniffer(DeviceStatusSniffer deviceStatusSniffer) {
		this.deviceStatusSniffer = deviceStatusSniffer;
	}

	public DevicePowerAmplifierInfo getDevicePowerAmplifierInfo() {
		return devicePowerAmplifierInfo;
	}

	public void setDevicePowerAmplifierInfo(DevicePowerAmplifierInfo devicePowerAmplifierInfo) {
		this.devicePowerAmplifierInfo = devicePowerAmplifierInfo;
	}

	public DeviceStatusWorkMode getDeviceStatusWorkMode() {
		return deviceStatusWorkMode;
	}

	public void setDeviceStatusWorkMode(DeviceStatusWorkMode deviceStatusWorkMode) {
		this.deviceStatusWorkMode = deviceStatusWorkMode;
	}
}
