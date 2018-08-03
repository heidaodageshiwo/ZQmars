package com.iekun.ef.jms.vo.receive;

public class RcvStatus {
	
	private String 	serialNum;
	private int 	temperature; 			//temperature of borad
	private int  	cpu_Load;		//CPU utility
	private int  	count_RACH;
	private int  	count_schel_MSG3;   //the count of sched msg3
	private int  	count_MSG3_success; //the msg3 count of success
	private int  	count_MSG4;		 	//MSG4
	private int  	count_MSG5;		 	//MSG5
	private int  	count_UE_L3;	    //the count of ue statistics in L3
	private String 	expiration_time;  	//license过期时间
	
	/**
	 * @return the serialNum
	 */
	public String getSerialNum() {
		return serialNum;
	}
	/**
	 * @param serialNum the serialNum to set
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	/**
	 * @return the count_RACH
	 */
	public int getCount_RACH() {
		return count_RACH;
	}
	/**
	 * @param count_RACH the count_RACH to set
	 */
	public void setCount_RACH(int count_RACH) {
		this.count_RACH = count_RACH;
	}
	/**
	 * @return the count_schel_MSG3
	 */
	public int getCount_schel_MSG3() {
		return count_schel_MSG3;
	}
	/**
	 * @param count_schel_MSG3 the count_schel_MSG3 to set
	 */
	public void setCount_schel_MSG3(int count_schel_MSG3) {
		this.count_schel_MSG3 = count_schel_MSG3;
	}
	/**
	 * @return the count_MSG3_success
	 */
	public int getCount_MSG3_success() {
		return count_MSG3_success;
	}
	/**
	 * @param count_MSG3_success the count_MSG3_success to set
	 */
	public void setCount_MSG3_success(int count_MSG3_success) {
		this.count_MSG3_success = count_MSG3_success;
	}
	/**
	 * @return the count_MSG4
	 */
	public int getCount_MSG4() {
		return count_MSG4;
	}
	/**
	 * @param count_MSG4 the count_MSG4 to set
	 */
	public void setCount_MSG4(int count_MSG4) {
		this.count_MSG4 = count_MSG4;
	}
	/**
	 * @return the count_MSG5
	 */
	public int getCount_MSG5() {
		return count_MSG5;
	}
	/**
	 * @param count_MSG5 the count_MSG5 to set
	 */
	public void setCount_MSG5(int count_MSG5) {
		this.count_MSG5 = count_MSG5;
	}
	/**
	 * @return the count_UE_L3
	 */
	public int getCount_UE_L3() {
		return count_UE_L3;
	}
	/**
	 * @param count_UE_L3 the count_UE_L3 to set
	 */
	public void setCount_UE_L3(int count_UE_L3) {
		this.count_UE_L3 = count_UE_L3;
	}
	/**
	 * @return the expiration_time
	 */
	public String getExpiration_time() {
		return expiration_time;
	}
	/**
	 * @param expiration_time the expiration_time to set
	 */
	public void setExpiration_time(String expiration_time) {
		this.expiration_time = expiration_time;
	}
	/**
	 * @return the cpu_Load
	 */
	public int getCpu_Load() {
		return cpu_Load;
	}
	/**
	 * @param cpu_Load the cpu_Load to set
	 */
	public void setCpu_Load(int cpu_Load) {
		this.cpu_Load = cpu_Load;
	}
	


}
