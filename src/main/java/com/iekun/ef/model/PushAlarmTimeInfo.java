package com.iekun.ef.model;

public class PushAlarmTimeInfo {

	private String alarmTime;
	
	private String alarmDetailInfo;

	public PushAlarmTimeInfo(String alarmTime, String alarmDetailInfo) {
		super();
		this.alarmTime = alarmTime;
		this.alarmDetailInfo = alarmDetailInfo;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmDetailInfo() {
		return alarmDetailInfo;
	}

	public void setAlarmDetailInfo(String alarmDetailInfo) {
		this.alarmDetailInfo = alarmDetailInfo;
	}
}
