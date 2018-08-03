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
import com.iekun.ef.dao.UpgradeMapper;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvUpgradeComplete;

public class HandleRcvUpgradeComplete {

	/*@Autowired
	private UpgradeMapper upgradeMapper;*/
	
	String sessionId;
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvUpgradeComplete.class);
	
	public void handleMessage(Message message, UpgradeMapper upgradeMapper) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvUpgradeComplete rcvUpgradeComplete = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvUpgradeComplete.class);
		/*if(!(sessionId.equals(String.valueOf(0))))
		{
			
			SigletonBean.putToMap(Integer.parseInt(sessionId), message.getSubOject().toString());
			logger.info("sessionid:"+ sessionId + "rcvLicenseNotifyAck:" +message.getSubOject().toString());
		}
		*/
		logger.info("deviceSn:" + deviceSn + "recv upgrade !");
		this.handleRcvUpgradeComplete(deviceSn, rcvUpgradeComplete, upgradeMapper);
		
	}
	
	
	private void handleRcvUpgradeComplete(String dvSn, RcvUpgradeComplete rcvUpgradeComplete, UpgradeMapper upgradeMapper)
	{
		
		SimpleDateFormat dateformat =new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String,Object> params=new HashMap<String, Object>();
		if (rcvUpgradeComplete.getCode() != 0)
		{
			params.put("result", rcvUpgradeComplete.getMessage());
			params.put("statusFlag", 0);
			logger.info("device sn =" + dvSn + "fail rcvUpgradeComplete code " + rcvUpgradeComplete.getCode());
		}
		else
		{     
			params.put("sucessTime", dateformat.format( new Date()));
			params.put("result", "success");
			params.put("statusFlag", 3);
			logger.info("device sn =" + dvSn + "sucess rcvUpgradeComplete code " + rcvUpgradeComplete.getCode());
		}
		                    
		params.put("updateTime", dateformat.format( new Date()));
		
		params.put("deviceSn", dvSn);
				
		try {
			upgradeMapper.updateUpgrade(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
