package com.iekun.ef.model;

public class DeviceSiteDetailInfo {

		 /**
		* 设备序列号
		*/
		private String deviceSn;
		
		  /**
		* 运营商：01- 移动 02- 电信 03- 联通
		*/
		private String operator;
		
		 /**
		* 设备名称
		*/
		private String deviceName;
		
		
		 /**
		* 站点名称
		*/
		private String siteName;
		
		/**
		* 站点所在城市
		*/
		private String siteCity;

		public String getDeviceSn() {
			return deviceSn;
		}

		public void setDeviceSn(String deviceSn) {
			this.deviceSn = deviceSn;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getDeviceName() {
			return deviceName;
		}

		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}

		public String getSiteName() {
			return siteName;
		}

		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}

		public String getSiteCity() {
			return siteCity;
		}

		public void setSiteCity(String siteCity) {
			this.siteCity = siteCity;
		}

		
}
