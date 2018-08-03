package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.paDetailInfo;
import com.iekun.ef.jms.vo.receive.RcvPaInfos;
import com.iekun.ef.jms.vo.receive.RcvUeInfos;
import com.iekun.ef.model.DevicePowerAmplifierInfo;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.service.LoggerService;


public class HandleRcvInfoPa {
	String sessionId;
	
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvInfoPa.class);
	
	//@Override
	public void handleMessage(Message message,  LoggerService  loggerServ) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvPaInfos rcvPaInfos = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvPaInfos.class);
		if(!(sessionId.equals(String.valueOf(0))))
		{
			/*主动上报的sessionid 不为0 为查询所得，所以不入map*/
			SigletonBean.putToMap(sessionId, message.getSubOject().toString());
			logger.info("sessionid:"+ sessionId + "painfos:" + message.getSubOject().toString());
		}
		this.handlePaInfos(deviceSn, rcvPaInfos, loggerServ);
			
	}

	/**
	 * 读取rcvPaInfo消息,处理rcvPaInfo消息
	 * @param rcvPaInfo
	 */
	private void handlePaInfos(String deviceSn,  RcvPaInfos rcvPaInfos,  LoggerService  loggerServ)
	{
		/* 目前只有一个pa 所以为简化处理现在只处理一个pa信息，后续扩展为多个的时候需要修改!*/
		if (rcvPaInfos.getPaDetailInfoList() != null)
		{
			this.handlePaDetailInfo(deviceSn, rcvPaInfos.getPaDetailInfoList().get(0), loggerServ);
			//DeviceStatus dvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
			//dvStatus.getDeviceStatusCommon().setDeviceStatus("run");
			/*if((dvStatus.getDeviceStatusCommon().getDeviceStatus()).equals("offline"))
			{
				dvStatus.getDeviceStatusCommon().setDeviceStatus("run");
				loggerServ.insertLog("online", deviceSn, new Integer(0));
			}*/
		}
	}
	
	/**
	 * 读取PaDetailInfo消息,处理rcvPaDetailInfo消息 
	 * @param rcvPaDetailInfo
	 */
	private void handlePaDetailInfo(String deviceSn,  paDetailInfo rcvPaDetailInfo, LoggerService  loggerServ)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		if (DvStatus != null)
		{
			DevicePowerAmplifierInfo DvPaInfo = DvStatus.getDevicePowerAmplifierInfo();
			DvPaInfo.setAddr_485(String.valueOf(rcvPaDetailInfo.getAddr_485()));
			DvPaInfo.setBand(String.valueOf(rcvPaDetailInfo.getBand()));
			DvPaInfo.setEn_485(String.valueOf(rcvPaDetailInfo.getEn_485()));
			DvPaInfo.setFactory(String.valueOf(rcvPaDetailInfo.getFactory()));
			DvPaInfo.setPa_num(String.valueOf(rcvPaDetailInfo.getPa_num()));
			DvPaInfo.setPaCnt(String.valueOf(1)/*rcvPaDetailInfo.get*/);
			DvPaInfo.setPower(String.valueOf(rcvPaDetailInfo.getPower()));
			DvPaInfo.setProvider(rcvPaDetailInfo.getProvider());
			DvPaInfo.setSn(rcvPaDetailInfo.getSn());
			DvPaInfo.setValid(String.valueOf(rcvPaDetailInfo.getValid()));
			//DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
			if((DvStatus.getDeviceStatusCommon().getDeviceStatus()).equals("offline"))
			{
				DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
				loggerServ.insertLog("online", deviceSn, new Long(0));
			}
			DvStatus.getDeviceStatusCommon().setStatusUpdateTime(noDateStr);
		}
	}
}
