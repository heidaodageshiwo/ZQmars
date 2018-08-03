package com.iekun.ef.model;

import java.io.Serializable;

/* 设备一般的状态信息 */
public class DeviceStatusCommon implements Serializable{

		/* 设备状态更新时间 */
		String statusUpdateTime;
		
		String softwareVersion;
		
		String fpgaVersion;
		
		String bbuVersion;
		
		String heartBeatUpdateTime;
		
		String rcvBoardTime;
		
		String sendServTime;
		
		String reason;
		
		String deviceRunTimeLen;
		
		String targetRuleSyncSendTime;
		
		String targetRuleSyncSuccTime;
		
		String areaRuleSyncSendTime;
		
		String areaRuleSyncSuccTime;
		
		
		/* 记录设备在线还是离线状态还是其他状态,告警也要刷状态  */
		String deviceStatus;   

		public String getStatusUpdateTime() {
			return statusUpdateTime;
		}

		public void setStatusUpdateTime(String statusUpdateTime) {
			this.statusUpdateTime = statusUpdateTime;
		}

		public String getSoftwareVersion() {
			return softwareVersion;
		}

		public void setSoftwareVersion(String softwareVersion) {
			this.softwareVersion = softwareVersion;
		}

		public String getFpgaVersion() {
			return fpgaVersion;
		}

		public void setFpgaVersion(String fpgaVersion) {
			this.fpgaVersion = fpgaVersion;
		}

		public String getBbuVersion() {
			return bbuVersion;
		}

		public void setBbuVersion(String bbuVersion) {
			this.bbuVersion = bbuVersion;
		}

		public String getDeviceStatus() {
			return deviceStatus;
		}

		public void setDeviceStatus(String deviceStatus) {
			this.deviceStatus = deviceStatus;
		}

		/**
		 * @return the heartBeatUpdateTime
		 */
		public String getHeartBeatUpdateTime() {
			return heartBeatUpdateTime;
		}

		/**
		 * @param heartBeatUpdateTime the heartBeatUpdateTime to set
		 */
		public void setHeartBeatUpdateTime(String heartBeatUpdateTime) {
			this.heartBeatUpdateTime = heartBeatUpdateTime;
		}

		public String getRcvBoardTime() {
			return rcvBoardTime;
		}

		public void setRcvBoardTime(String rcvBoardTime) {
			this.rcvBoardTime = rcvBoardTime;
		}

		public String getSendServTime() {
			return sendServTime;
		}

		public void setSendServTime(String sendServTime) {
			this.sendServTime = sendServTime;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getDeviceRunTimeLen() {
			return deviceRunTimeLen;
		}

		public void setDeviceRunTimeLen(String deviceRunTimeLen) {
			this.deviceRunTimeLen = deviceRunTimeLen;
		}

		public String getTargetRuleSyncSendTime() {
			return targetRuleSyncSendTime;
		}

		public void setTargetRuleSyncSendTime(String targetRuleSyncSendTime) {
			this.targetRuleSyncSendTime = targetRuleSyncSendTime;
		}

		public String getTargetRuleSyncSuccTime() {
			return targetRuleSyncSuccTime;
		}

		public void setTargetRuleSyncSuccTime(String targetRuleSyncSuccTime) {
			this.targetRuleSyncSuccTime = targetRuleSyncSuccTime;
		}

		public String getAreaRuleSyncSendTime() {
			return areaRuleSyncSendTime;
		}

		public void setAreaRuleSyncSendTime(String areaRuleSyncSendTime) {
			this.areaRuleSyncSendTime = areaRuleSyncSendTime;
		}

		public String getAreaRuleSyncSuccTime() {
			return areaRuleSyncSuccTime;
		}

		public void setAreaRuleSyncSuccTime(String areaRuleSyncSuccTime) {
			this.areaRuleSyncSuccTime = areaRuleSyncSuccTime;
		}
	
			
}
