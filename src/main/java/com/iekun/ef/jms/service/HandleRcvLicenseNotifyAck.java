package com.iekun.ef.jms.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvLicenseNotifyAck;
import com.iekun.ef.jms.vo.receive.RcvUpgradeNotifyAck;

public class HandleRcvLicenseNotifyAck {

	String sessionId;
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvLicenseNotifyAck.class);
	
	public void handleMessage(Message message) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvLicenseNotifyAck rcvLicenseNotifyAck = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvLicenseNotifyAck.class);
		if(!(sessionId.equals(String.valueOf(0))))
		{
			/*主动上报的sessionid 为0 不为查询所得，所以不入map*/
			SigletonBean.putToMap(sessionId, message.getSubOject().toString());
			logger.info("sessionid:"+ sessionId + "rcvLicenseNotifyAck:" +message.getSubOject().toString());
		}
		
		/*this.handleRcvPaStatus(deviceSn, rcvUpgradeNotifyAck);*/
		
	}
	

}
