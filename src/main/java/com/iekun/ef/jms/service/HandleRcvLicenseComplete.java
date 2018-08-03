package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.DeviceLicenseMapper;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvLicenseComplete;
import com.iekun.ef.jms.vo.receive.RcvLicenseNotifyAck;

public class HandleRcvLicenseComplete {

	String sessionId;
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvLicenseComplete.class);
	
	/*@Autowired
	DeviceLicenseMapper dvLicenseMapper;*/
	
	public void handleMessage(Message message, DeviceLicenseMapper dvLicenseMapper) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvLicenseComplete rcvLicenseComplete = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvLicenseComplete.class);
		/*if(!(sessionId.equals(String.valueOf(0))))
		{
			
			SigletonBean.putToMap(Integer.parseInt(sessionId), message.getSubOject().toString());
			logger.info("sessionid:"+ sessionId + "rcvLicenseNotifyAck:" +message.getSubOject().toString());
		}
		*/
		this.handleRcvLicenseComplete(deviceSn, rcvLicenseComplete, dvLicenseMapper);
		
	}
	
	
	private void handleRcvLicenseComplete(String dvSn, RcvLicenseComplete rcvLicenseComplete, DeviceLicenseMapper dvLicenseMapper)
	{
		SimpleDateFormat dateformat =new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String,Object> params=new HashMap<String, Object>();
		if (rcvLicenseComplete.getCode() != 0)
		{
			params.put("result", rcvLicenseComplete.getMessage());
			params.put("statusFlag", 0);
		}
		else
		{
			params.put("sucessTime", dateformat.format( new Date()));
			params.put("statusFlag", 3);
			params.put("result", "success");
		}
		
		params.put("updateTime", dateformat.format( new Date()));
		
		params.put("deviceSn", dvSn);
		
		
		
		try {
			dvLicenseMapper.updateUpgrade(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
