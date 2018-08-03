package com.iekun.ef.jms.vo.receive;

public class RcvUeInfo{
	private String ueImsi;
	private String ueImei;
	private String ueSTmsi;
	private int ueTaType;
	private int boolLib;
	private int boolsys;
	private String serialNum;
	private int raRta;
	private int tmSec;
	private int tmMin;
	private int tmHour;
	private int tmMday;
	private int tmMon;
	private int tmYear;
	private String captureTime;
	private	int code_province;
	private	int code_city;			
	private	int code_operator;
	private	int boolLocaleInlib;
	
	public String getUeImsi() {
		return ueImsi;
	}
	public void setUeImsi(String ueImsi) {
		this.ueImsi = ueImsi;
	}
	public String getUeImei() {
		return ueImei;
	}
	public void setUeImei(String ueImei) {
		this.ueImei = ueImei;
	}
	public String getUeSTmsi() {
		return ueSTmsi;
	}
	public void setUeSTmsi(String ueSTmsi) {
		this.ueSTmsi = ueSTmsi;
	}
	public int getUeTaType() {
		return ueTaType;
	}
	public void setUeTaType(int ueTaType) {
		this.ueTaType = ueTaType;
	}
	public int getBoolLib() {
		return boolLib;
	}
	public void setBoolLib(int boolLib) {
		this.boolLib = boolLib;
	}
	public int getBoolsys() {
		return boolsys;
	}
	public void setBoolsys(int boolsys) {
		this.boolsys = boolsys;
	}

	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public int getRaRta() {
		return raRta;
	}
	public void setRaRta(int raRta) {
		this.raRta = raRta;
	}
	public int getTmSec() {
		return tmSec;
	}
	public void setTmSec(int tmSec) {
		this.tmSec = tmSec;
	}
	public int getTmMin() {
		return tmMin;
	}
	public void setTmMin(int tmMin) {
		this.tmMin = tmMin;
	}
	public int getTmHour() {
		return tmHour;
	}
	public void setTmHour(int tmHour) {
		this.tmHour = tmHour;
	}
	public int getTmMday() {
		return tmMday;
	}
	public void setTmMday(int tmMday) {
		this.tmMday = tmMday;
	}
	public int getTmMon() {
		return tmMon;
	}
	public void setTmMon(int tmMon) {
		this.tmMon = tmMon;
	}
	public int getTmYear() {
		return tmYear;
	}
	public void setTmYear(int tmYear) {
		this.tmYear = tmYear;
	}
	public String getCaptureTime() {
		return captureTime;
	}
	public void setCaptureTime(String captureTime) {
		this.captureTime = captureTime;
	}
	/**
	 * @return the code_province
	 */
	public int getCode_province() {
		return code_province;
	}
	/**
	 * @param code_province the code_province to set
	 */
	public void setCode_province(int code_province) {
		this.code_province = code_province;
	}
	/**
	 * @return the code_city
	 */
	public int getCode_city() {
		return code_city;
	}
	/**
	 * @param code_city the code_city to set
	 */
	public void setCode_city(int code_city) {
		this.code_city = code_city;
	}
	/**
	 * @return the code_operator
	 */
	public int getCode_operator() {
		return code_operator;
	}
	/**
	 * @param code_operator the code_operator to set
	 */
	public void setCode_operator(int code_operator) {
		this.code_operator = code_operator;
	}
	/**
	 * @return the boolLocaleInlib
	 */
	public int getBoolLocaleInlib() {
		return boolLocaleInlib;
	}
	/**
	 * @param boolLocaleInlib the boolLocaleInlib to set
	 */
	public void setBoolLocaleInlib(int boolLocaleInlib) {
		this.boolLocaleInlib = boolLocaleInlib;
	}
}
