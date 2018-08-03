package com.iekun.ef.jms.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvHeartBeat;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.DeviceStatusCommon;
import com.iekun.ef.model.DeviceStatusDebug;
import com.iekun.ef.model.DeviceStatusEnviroment;
import com.iekun.ef.service.LoggerService;
//import com.iekun.ef.util.Logger;
import com.iekun.ef.util.TimeUtils;

@Service("handleSendHeartBeat")
@Transactional(rollbackFor=java.lang.Exception.class)
@Scope("prototype")
public class HandleRcvHeartBeat{

	private static Logger logger = LoggerFactory.getLogger(HandleRcvHeartBeat.class);
	
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;

	public void handleMessage(Message message,  LoggerService  loggerServ) {
		String deviceSn 	= message.getSerialNum();
		JSONObject mapObject = JSONObject.parseObject(message.getSubOject().toString());
		RcvHeartBeat sendHeartBeat = JSONObject.toJavaObject(mapObject, RcvHeartBeat.class);
		/*sendHeartBeat.setSerialNum(message.getSerialNum());
		messageToDb.updateSendHeartBeat(sendHeartBeat);*/
		
		this.handleRcvHearbeat(deviceSn, sendHeartBeat, loggerServ);
		
		sendHeartBeat = null;
		mapObject = null;
		
	}
	
	private void handleRcvHearbeat(String deviceSn,  RcvHeartBeat sendHeartBeat, LoggerService  loggerServ) 
	{
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		if(DvStatus == null)
		{
			logger.info("device status error! deviceSn = " + deviceSn);
			return;
		}
		
		DeviceStatusDebug dvStatusDebug = DvStatus.getDeviceStatusDebug();
		DeviceStatusEnviroment dvStatusEnv = DvStatus.getDeviceStatusEnviroment();
		DeviceStatusCommon   dvStatusCommon = DvStatus.getDeviceStatusCommon();
		dvStatusDebug.setRateMsg4PkMsg3(sendHeartBeat.getRateMsg4PkMsg3());
		dvStatusDebug.setRateMsg5PkMsg4(sendHeartBeat.getRateMsg5PkMsg4());
		dvStatusDebug.setIsDspHearbeatRecv(String.valueOf(sendHeartBeat.getIdDspHearbeatRecv()));
		dvStatusDebug.setNumberOfConnectedUEs(sendHeartBeat.getNumberOfConnectedUEs());
		dvStatusEnv.setRfEnable(String.valueOf(sendHeartBeat.getRfEnable()));
		if((dvStatusCommon.getDeviceStatus()).equals("offline"))
		{
			dvStatusCommon.setDeviceStatus("run");
			loggerServ.insertLog("online", deviceSn, new Long(0));
		}
		dvStatusCommon.setDeviceRunTimeLen(String.valueOf(sendHeartBeat.getDays()) + "天" + String.valueOf(sendHeartBeat.getHours()) + "小时" + String.valueOf(sendHeartBeat.getMinites()) + "分" + String.valueOf(sendHeartBeat.getSeconds()) + "秒");
     	//dvStatusCommon.setHeartBeatUpdateTime(noDateStr);
		dvStatusCommon.setStatusUpdateTime(TimeUtils.timeFormatterStr.format(new Date()));
		
	}

}
