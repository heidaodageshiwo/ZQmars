package com.iekun.ef.jms.vo.receive;


public class RcvHeartBeat {

	private int seconds;
	private int minites;
	private int hours;
	private int days;
	private int numberOfConnectedUEs;
	private int idDspHearbeatRecv;
	private int rfEnable;  /*0表示射频关闭，1表示射频打开*/
	private int RateMsg4PkMsg3;
	private int RateMsg5PkMsg4;
	private int padding;
	private String serialNum;
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public int getMinites() {
		return minites;
	}
	public void setMinites(int minites) {
		this.minites = minites;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getNumberOfConnectedUEs() {
		return numberOfConnectedUEs;
	}
	public void setNumberOfConnectedUEs(int numberOfConnectedUEs) {
		this.numberOfConnectedUEs = numberOfConnectedUEs;
	}
	public int getIdDspHearbeatRecv() {
		return idDspHearbeatRecv;
	}
	public void setIdDspHearbeatRecv(int idDspHearbeatRecv) {
		this.idDspHearbeatRecv = idDspHearbeatRecv;
	}
	public int getRfEnable() {
		return rfEnable;
	}
	public void setRfEnable(int rfEnable) {
		this.rfEnable = rfEnable;
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
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
}
