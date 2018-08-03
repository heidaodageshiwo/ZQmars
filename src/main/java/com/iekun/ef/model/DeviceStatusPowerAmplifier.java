package com.iekun.ef.model;

import java.io.Serializable;

public class DeviceStatusPowerAmplifier  implements Serializable{

	String 			valid;
	String 			warn_pa;							//功放故障告警 uint8_t
	String 			warn_standing_wave_ratio;			//驻波比告警
	String 			warn_temp;							//过温告警
	String 			warn_power;							//过功率告警
	String 			on_off_Pa;							//功放开关
	String  		inverse_power;						//检测的反向功率
	String  		temp;								//功放温度 ℃
	String  		alc_value;							//功放 ALC 值
	String  		standing_wave_ratio;				//驻波比=实际驻波比*10
	String  		curr_att_Pa;						//功放 ATT
	String  		forward_power;						//检测的前向功率
	String  		forward_power_2;					//检测的前向功率
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
	 * @return the warn_pa
	 */
	public String getWarn_pa() {
		return warn_pa;
	}
	/**
	 * @param warn_pa the warn_pa to set
	 */
	public void setWarn_pa(String warn_pa) {
		this.warn_pa = warn_pa;
	}
	/**
	 * @return the warn_standing_wave_ratio
	 */
	public String getWarn_standing_wave_ratio() {
		return warn_standing_wave_ratio;
	}
	/**
	 * @param warn_standing_wave_ratio the warn_standing_wave_ratio to set
	 */
	public void setWarn_standing_wave_ratio(String warn_standing_wave_ratio) {
		this.warn_standing_wave_ratio = warn_standing_wave_ratio;
	}
	/**
	 * @return the warn_temp
	 */
	public String getWarn_temp() {
		return warn_temp;
	}
	/**
	 * @param warn_temp the warn_temp to set
	 */
	public void setWarn_temp(String warn_temp) {
		this.warn_temp = warn_temp;
	}
	/**
	 * @return the warn_power
	 */
	public String getWarn_power() {
		return warn_power;
	}
	/**
	 * @param warn_power the warn_power to set
	 */
	public void setWarn_power(String warn_power) {
		this.warn_power = warn_power;
	}
	/**
	 * @return the on_off_Pa
	 */
	public String getOn_off_Pa() {
		return on_off_Pa;
	}
	/**
	 * @param on_off_Pa the on_off_Pa to set
	 */
	public void setOn_off_Pa(String on_off_Pa) {
		this.on_off_Pa = on_off_Pa;
	}
	/**
	 * @return the inverse_power
	 */
	public String getInverse_power() {
		return inverse_power;
	}
	/**
	 * @param inverse_power the inverse_power to set
	 */
	public void setInverse_power(String inverse_power) {
		this.inverse_power = inverse_power;
	}
	/**
	 * @return the temp
	 */
	public String getTemp() {
		return temp;
	}
	/**
	 * @param temp the temp to set
	 */
	public void setTemp(String temp) {
		this.temp = temp;
	}
	/**
	 * @return the alc_value
	 */
	public String getAlc_value() {
		return alc_value;
	}
	/**
	 * @param alc_value the alc_value to set
	 */
	public void setAlc_value(String alc_value) {
		this.alc_value = alc_value;
	}
	/**
	 * @return the standing_wave_ratio
	 */
	public String getStanding_wave_ratio() {
		return standing_wave_ratio;
	}
	/**
	 * @param standing_wave_ratio the standing_wave_ratio to set
	 */
	public void setStanding_wave_ratio(String standing_wave_ratio) {
		this.standing_wave_ratio = standing_wave_ratio;
	}
	/**
	 * @return the curr_att_Pa
	 */
	public String getCurr_att_Pa() {
		return curr_att_Pa;
	}
	/**
	 * @param curr_att_Pa the curr_att_Pa to set
	 */
	public void setCurr_att_Pa(String curr_att_Pa) {
		this.curr_att_Pa = curr_att_Pa;
	}
	/**
	 * @return the forward_power
	 */
	public String getForward_power() {
		return forward_power;
	}
	/**
	 * @param forward_power the forward_power to set
	 */
	public void setForward_power(String forward_power) {
		this.forward_power = forward_power;
	}
	/**
	 * @return the forward_power_2
	 */
	public String getForward_power_2() {
		return forward_power_2;
	}
	/**
	 * @param forward_power_2 the forward_power_2 to set
	 */
	public void setForward_power_2(String forward_power_2) {
		this.forward_power_2 = forward_power_2;
	}
	
	
	
}
