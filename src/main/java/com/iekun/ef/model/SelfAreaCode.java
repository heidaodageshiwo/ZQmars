package com.iekun.ef.model;

public class SelfAreaCode {

	private Long id;
	 
	/**
	* 省编码
	*/
	private String provinceCode;
	
	/**
	* 省名
	*/
	private String provinceName;
	
    /**
	* 城市编码
	*/
	private String cityCode;
	
	/**
	* 城市名
	*/
	private String cityName;
	
	/**
	* 行政级别
	*/
	private Integer level;



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}



	/**
	 * @return the provinceCode
	 */
	public String getProvinceCode() {
		return provinceCode;
	}



	/**
	 * @param provinceCode the provinceCode to set
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}



	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}



	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}



	/**
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}



	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}



	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}



	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}



	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}



	
}
