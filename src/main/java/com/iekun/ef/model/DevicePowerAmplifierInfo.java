package com.iekun.ef.model;

import java.io.Serializable;

public class DevicePowerAmplifierInfo implements Serializable {

	String 			valid; 
	String 			pa_num;
	String 			power;
	String 			band;// 
	String 			en_485; 
	String 			addr_485; 
	String 			provider;// 
	String 			sn;// 
	String 			factory;//
	String          paCnt;
	/**
	 * @return the valid
	 */
	public String getValid() {
		return valid;
	}
	/**
	 * @param valid the valid to set
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}
	/**
	 * @return the pa_num
	 */
	public String getPa_num() {
		return pa_num;
	}
	/**
	 * @param pa_num the pa_num to set
	 */
	public void setPa_num(String pa_num) {
		this.pa_num = pa_num;
	}
	/**
	 * @return the power
	 */
	public String getPower() {
		return power;
	}
	/**
	 * @param power the power to set
	 */
	public void setPower(String power) {
		this.power = power;
	}
	/**
	 * @return the band
	 */
	public String getBand() {
		return band;
	}
	/**
	 * @param band the band to set
	 */
	public void setBand(String band) {
		this.band = band;
	}
	/**
	 * @return the en_485
	 */
	public String getEn_485() {
		return en_485;
	}
	/**
	 * @param en_485 the en_485 to set
	 */
	public void setEn_485(String en_485) {
		this.en_485 = en_485;
	}
	/**
	 * @return the addr_485
	 */
	public String getAddr_485() {
		return addr_485;
	}
	/**
	 * @param addr_485 the addr_485 to set
	 */
	public void setAddr_485(String addr_485) {
		this.addr_485 = addr_485;
	}
	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}
	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	/**
	 * @return the factory
	 */
	public String getFactory() {
		return factory;
	}
	/**
	 * @param factory the factory to set
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}
	/**
	 * @return the paCnt
	 */
	public String getPaCnt() {
		return paCnt;
	}
	
	/**
	 * @param paCnt the paCnt to set
	 */
	public void setPaCnt(String paCnt) {
		this.paCnt = paCnt;
	}
	
}
