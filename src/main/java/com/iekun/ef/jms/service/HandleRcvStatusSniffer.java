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
import com.iekun.ef.jms.vo.receive.RcvSniffer;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.DeviceStatusSniffer;
import com.iekun.ef.service.LoggerService;


public class HandleRcvStatusSniffer {
	String sessionId;
	
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvReqCnf.class);
	
	//@Override
	public void handleMessage(Message message,  LoggerService  loggerServ) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvSniffer rcvSniffer = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvSniffer.class);
		
		this.handleRcvSniffer(deviceSn, rcvSniffer, loggerServ);
		
	}

	/**
	 * 读取rcvSniffer消息,处理rcvSniffer消息
	 * @param rcvSniffer
	 */
	private void handleRcvSniffer(String deviceSn,  RcvSniffer rcvSniffer,  LoggerService  loggerServ)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		if (DvStatus != null)
		{
			DeviceStatusSniffer dvSnifferStatus = DvStatus.getDeviceStatusSniffer();
			dvSnifferStatus.setFrequency(rcvSniffer.getFrequency());
			dvSnifferStatus.setPCI(rcvSniffer.getPci());
			dvSnifferStatus.setValid(rcvSniffer.getValid());
			dvSnifferStatus.setPower(rcvSniffer.getPower());
			dvSnifferStatus.setSyncStatus(rcvSniffer.getSyncStatus());
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
