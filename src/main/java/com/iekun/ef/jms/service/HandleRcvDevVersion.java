package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvDevVersion;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.DeviceStatusCommon;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.controller.SigletonBean;

@Service("handleSendDevVersion")
@Transactional(rollbackFor=java.lang.Exception.class)
@Scope("prototype")
public class HandleRcvDevVersion{
	
	private static Logger logger = LoggerFactory.getLogger(HandleRcvDevVersion.class);
	DeviceStatusCommon devStatusCommon;
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	//private MessageToDataBase messageToDb = new MessageToDataBase();
	//@Override
	public void handleMessage(Message message, LoggerService  loggerServ) {
		String deviceSn 	= message.getSerialNum();
		JSONObject mapObject = JSONObject.parseObject(message.getSubOject().toString());
		RcvDevVersion sendDevVersion = JSONObject.toJavaObject(mapObject, RcvDevVersion.class);
		this.putToMap(sendDevVersion);
		//messageToDb.updateOperLog(sendDevVersion.getSessionId(), 0);
		this.handleRcvDevVersion(deviceSn, sendDevVersion, loggerServ);
	}
	
	
	private void handleRcvDevVersion(String deviceSn, RcvDevVersion sendDevVersion,  LoggerService  loggerServ) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String softWareVer = null;
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		if (DvStatus != null)
		{
			devStatusCommon = DvStatus.getDeviceStatusCommon();
			devStatusCommon.setBbuVersion(sendDevVersion.getBbuVersion());
			devStatusCommon.setFpgaVersion(sendDevVersion.getFpgaVersion());
			softWareVer = sendDevVersion.getSoftWareVersion();
			if (softWareVer.length() > 64)
			{
				softWareVer = softWareVer.substring(0, 64);
			}
			devStatusCommon.setSoftwareVersion(softWareVer);
			if((devStatusCommon.getDeviceStatus()).equals("offline"))
			{
				devStatusCommon.setDeviceStatus("run");
				loggerServ.insertLog("online" , deviceSn, new Long(0));
			}
			devStatusCommon.setStatusUpdateTime(noDateStr);
			
		}
		
	}


	/**
	 * 将应答消息放到map中
	 * @param sendDevVersion
	 * @throws Exception
	 */
	private synchronized void putToMap(RcvDevVersion sendDevVersion){
		
		int key = sendDevVersion.getSessionId();
		
		logger.info("版本查询SessionId：" + key + " cnfmap_size: " + SigletonBean.getSendfCnfSize());
		//SigletonBean.sendCnf
//		StringBuffer value = new StringBuffer();
//		value.append("设备编号：" + sendDevVersion.getSerialNum() + "\r\n");
//		value.append("FPGA版本：" + sendDevVersion.getFpgaVersion() + "\r\n");
//		value.append("BBU版本：" + sendDevVersion.getBbuVersion() + "\r\n");
//		value.append("软件版本：" + sendDevVersion.getSoftWareVersion());
		String value = JSONObject.toJSON(sendDevVersion).toString();
		SigletonBean.putToMap(String.valueOf(key), value);
	}

}
