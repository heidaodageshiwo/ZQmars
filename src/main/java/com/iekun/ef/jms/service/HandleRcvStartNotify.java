package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.OperCodeMapper;
import com.iekun.ef.dao.UpgradeMapper;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvStartNotify;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.DeviceStatusCommon;
import com.iekun.ef.model.DeviceStatusLicense;
import com.iekun.ef.model.OperCode;
import com.iekun.ef.model.RuleSend;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.util.CommonConsts;

public class HandleRcvStartNotify {
	
	String sessionId;
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvStartNotify.class);
	
	public void handleMessage(Message message, OperCodeMapper operCodeMapper, LoggerService  loggerServ) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvStartNotify rcvStartNotify = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvStartNotify.class);
		/*if(!(sessionId.equals(String.valueOf(0))))
		{
			
			SigletonBean.putToMap(Integer.parseInt(sessionId), message.getSubOject().toString());
			logger.info("sessionid:"+ sessionId + "rcvLicenseNotifyAck:" +message.getSubOject().toString());
		}
		*/
		this.handleRcvStartNotify(deviceSn, rcvStartNotify, operCodeMapper, loggerServ);
		
	}
		
	private void handleRcvStartNotify(String dvSn, RcvStartNotify rcvStartNotify, OperCodeMapper operCodeMapper, LoggerService  loggerServ)
	{
		
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(dvSn);
		if(DvStatus == null)
		{
			logger.info("DvStatus is null");
			return;
		}
		DeviceStatusCommon dvStatusComm = DvStatus.getDeviceStatusCommon();
		
		DeviceStatusLicense  deviceStatusLicense = DvStatus.getDeviceStatusLicense();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		
		//dvStatusComm.setFpgaVersion(rcvStartNotify.getFpgaVersion());
		//dvStatusComm.setBbuVersion(rcvStartNotify.getBBUVersion());
		//dvStatusComm.setSoftwareVersion(rcvStartNotify.getSoftwareVersion());
		//dvStatusComm.setReason(CommonConsts.getStartReason(rcvStartNotify.getReason()));
		String reason = (rcvStartNotify.getReason()).trim(); 
		int index = reason.indexOf("#"); 
		String rightReason = reason.substring(index+1);
					
		try {
			OperCode operCode = operCodeMapper.getOperNameByOperCode(rightReason);
			if (operCode != null)
			{
				dvStatusComm.setReason(operCode.getOperName());
			}
			else
			{
				dvStatusComm.setReason(rightReason);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loggerServ.insertLog( rightReason, dvSn, new Long(0));
		this.clearDvRuleSendSymbol(dvSn);
		if((DvStatus.getDeviceStatusCommon().getDeviceStatus()).equals("offline"))
		{
			DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
			loggerServ.insertLog("online", dvSn, new Long(0));
		}
		
		if(rcvStartNotify.getLicense() == 0)
		{
			deviceStatusLicense.setIsExpiration("正常");
		}
		
		if(rcvStartNotify.getLicense() == 1)
		{
			deviceStatusLicense.setIsExpiration("许可过期");
		}
		
	
	}
	
	private void clearDvRuleSendSymbol(String dvSn)
	{
		RuleSend rule;
		List<RuleSend> targetRuleSendList = SigletonBean.targetRuleSendList;
		List<RuleSend>  areaRuleSendList = SigletonBean.areaRuleSendList;
		int targetRuleSize = targetRuleSendList.size();
		int areaRuleSize = areaRuleSendList.size();
		for(int i =0; i < targetRuleSize; i ++)
		{
			rule = targetRuleSendList.get(i);
			if(rule.getSerialNum().equalsIgnoreCase(dvSn))
			{
				rule.getSegIdSyncSymbolMap().clear();
				rule.getSessionIdSegIdMap().clear();
				rule.setSyncIndexSymbol(0);
				rule.setSessionId(0);
				targetRuleSendList.add(i, rule);
				break;
			}
		}
		
		for(int j =0; j < areaRuleSize; j ++)
		{
			rule = areaRuleSendList.get(j);
			if(rule.getSerialNum().equalsIgnoreCase(dvSn))
			{
				rule.getSegIdSyncSymbolMap().clear();
				rule.getSessionIdSegIdMap().clear();
				rule.setSyncIndexSymbol(0);
				rule.setSessionId(0);
				areaRuleSendList.add(j, rule);
				break;
			}
		}
		
		return;
	}
	

}
