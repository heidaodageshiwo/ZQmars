package com.iekun.ef.jms.vo.receive;

public class RcvPaStatus {

	int			 valid;
	int			 warn_pa;					//功放故障告警 uint8_t
	int			 warn_standing_wave_ratio;	//驻波比告警
	int			 warn_temp;				//过温告警
	int			 warn_power;				//过功率告警
	int			 on_off_Pa;				//功放开关
	int			 inverse_power;						//检测的反向功率
	int			 temp;								//功放温度 ℃
	int			 alc_value;							//功放 ALC 值
	int			 standing_wave_ratio;		//驻波比=实际驻波比*10
	int			 curr_att_Pa;				//功放 ATT
	int			 forward_power;						//检测的前向功率
	int			 forward_power_2;					//检测的前向功率
	/**
	 * @return the valid
	 */
	public int getValid() {
		return valid;
	}
	/**
	 * @param valid the valid to set
	 */
	public void setValid(int valid) {
		this.valid = valid;
	}
	/**
	 * @return the warn_pa
	 */
	public int getWarn_pa() {
		return warn_pa;
	}
	/**
	 * @param warn_pa the warn_pa to set
	 */
	public void setWarn_pa(int warn_pa) {
		this.warn_pa = warn_pa;
	}
	/**
	 * @return the warn_standing_wave_ratio
	 */
	public int getWarn_standing_wave_ratio() {
		return warn_standing_wave_ratio;
	}
	/**
	 * @param warn_standing_wave_ratio the warn_standing_wave_ratio to set
	 */
	public void setWarn_standing_wave_ratio(int warn_standing_wave_ratio) {
		this.warn_standing_wave_ratio = warn_standing_wave_ratio;
	}
	/**
	 * @return the warn_temp
	 */
	public int getWarn_temp() {
		return warn_temp;
	}
	/**
	 * @param warn_temp the warn_temp to set
	 */
	public void setWarn_temp(int warn_temp) {
		this.warn_temp = warn_temp;
	}
	/**
	 * @return the warn_power
	 */
	public int getWarn_power() {
		return warn_power;
	}
	/**
	 * @param warn_power the warn_power to set
	 */
	public void setWarn_power(int warn_power) {
		this.warn_power = warn_power;
	}
	/**
	 * @return the on_off_Pa
	 */
	public int getOn_off_Pa() {
		return on_off_Pa;
	}
	/**
	 * @param on_off_Pa the on_off_Pa to set
	 */
	public void setOn_off_Pa(int on_off_Pa) {
		this.on_off_Pa = on_off_Pa;
	}
	/**
	 * @return the inverse_power
	 */
	public int getInverse_power() {
		return inverse_power;
	}
	/**
	 * @param inverse_power the inverse_power to set
	 */
	public void setInverse_power(int inverse_power) {
		this.inverse_power = inverse_power;
	}
	/**
	 * @return the temp
	 */
	public int getTemp() {
		return temp;
	}
	/**
	 * @param temp the temp to set
	 */
	public void setTemp(int temp) {
		this.temp = temp;
	}
	/**
	 * @return the alc_value
	 */
	public int getAlc_value() {
		return alc_value;
	}
	/**
	 * @param alc_value the alc_value to set
	 */
	public void setAlc_value(int alc_value) {
		this.alc_value = alc_value;
	}
	/**
	 * @return the standing_wave_ratio
	 */
	public int getStanding_wave_ratio() {
		return standing_wave_ratio;
	}
	/**
	 * @param standing_wave_ratio the standing_wave_ratio to set
	 */
	public void setStanding_wave_ratio(int standing_wave_ratio) {
		this.standing_wave_ratio = standing_wave_ratio;
	}
	/**
	 * @return the curr_att_Pa
	 */
	public int getCurr_att_Pa() {
		return curr_att_Pa;
	}
	/**
	 * @param curr_att_Pa the curr_att_Pa to set
	 */
	public void setCurr_att_Pa(int curr_att_Pa) {
		this.curr_att_Pa = curr_att_Pa;
	}
	/**
	 * @return the forward_power
	 */
	public int getForward_power() {
		return forward_power;
	}
	/**
	 * @param forward_power the forward_power to set
	 */
	public void setForward_power(int forward_power) {
		this.forward_power = forward_power;
	}
	/**
	 * @return the forward_power_2
	 */
	public int getForward_power_2() {
		return forward_power_2;
	}
	/**
	 * @param forward_power_2 the forward_power_2 to set
	 */
	public void setForward_power_2(int forward_power_2) {
		this.forward_power_2 = forward_power_2;
	}
	
	
	
}
