package com.iekun.ef.jms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvReqCnf;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.RuleSend;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.util.TimeUtils;

@Service("handleSendReqCnf")
@Transactional(rollbackFor=java.lang.Exception.class)
@Scope("prototype")
public class HandleRcvReqCnf{

	String deviceSn;
	String sessionId;
	
	List<RuleSend> targetRuleSendList = SigletonBean.targetRuleSendList;
	List<RuleSend> areaRuleSendList = SigletonBean.areaRuleSendList;
	
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	
	private MessageToDataBase messageToDb = new MessageToDataBase();
	
	private static Logger logger = LoggerFactory.getLogger(HandleRcvReqCnf.class);
	
	//@Override
	public void handleMessage(Message message, LoggerService loggerServ) {
		deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		RcvReqCnf sendReqCnf = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvReqCnf.class);
		DeviceStatus dvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		//dvStatus.getDeviceStatusCommon().setDeviceStatus("run");
		if((dvStatus.getDeviceStatusCommon().getDeviceStatus()).equals("offline"))
		{
			dvStatus.getDeviceStatusCommon().setDeviceStatus("run");
			loggerServ.insertLog("online", deviceSn, new Long(0));
		}
		dvStatus.getDeviceStatusCommon().setStatusUpdateTime(noDateStr);
		String cnfMsgType = sendReqCnf.getCnfType();
		if((cnfMsgType.matches("00AF"))||(cnfMsgType.matches("00D0"))
				||(cnfMsgType.matches("00D5")))
		{
			this.handleCnf(sendReqCnf, dvStatus);
		}
		else
		{
			this.putToMap(sendReqCnf);
		}
		messageToDb.updateReqCnf(sendReqCnf);
	}
 
	/**
	 * 将应答消息放到map中
	 * @param sendReqCnf
	 */
	private void putToMap(RcvReqCnf sendReqCnf){
		int key = sendReqCnf.getSessionId();
		logger.info("step7-1 sessionId=" + key + " cnfInd=" + sendReqCnf.getCnfInd());
		StringBuffer value = new StringBuffer();
		value.append("命令发送" + (sendReqCnf.getCnfInd()==0?"成功":"失败"));
		SigletonBean.putToMap(String.valueOf(key), value.toString());
	}
		
	/**
	 * 读取cnf消息，根据消息的内容维护更新链表
	 * @param sendReqCnf
	 */
	private void handleCnf(RcvReqCnf sendReqCnf, DeviceStatus dvStatus)
	{
		int sessionId = sendReqCnf.getSessionId();
		String cnfType = sendReqCnf.getCnfType();
		boolean allsegIsSucc = false;
		int isTaretOrArea = 0;
		
		/* 是发送黑名单的cnf消息 则处理，否则则略过 */
		if (cnfType.matches("00D0"))
		{
			/* 遍历链表 */
			int targetListCnt = targetRuleSendList.size();
			for (int i =0; i < targetListCnt; i++) {
				/* 根据流水号和设备号查找链表节点，找到则同步指示标志位置为1 */
				RuleSend targetRule = targetRuleSendList.get(i);
				isTaretOrArea = 0;
				logger.info("recv sessiondId: "+ sessionId );
				logger.info("deviceSn :" + deviceSn + "list devicesn :" + targetRule.getSerialNum());
				logger.info("sendReqCnf.getCnfInd(): " + sendReqCnf.getCnfInd());
				
				if(deviceSn.equals(targetRule.getSerialNum()))
				{
					this.handleTargetSessionId(i, isTaretOrArea, targetRule, sendReqCnf);
					
					targetRule = targetRuleSendList.get(i);
					allsegIsSucc = this.checkAllSegSymbol(targetRule);
					
					/*if((targetList.getSessionId() == sessionId) && (deviceSn.equals(targetList.getSerialNum())) &&
							((sendReqCnf.getCnfInd()) == 0))*/
					if (allsegIsSucc == true)
					{
						targetRule.setSyncIndexSymbol(1);
						targetRuleSendList.set(i, targetRule);
						
						Date now = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
						String nowTime = dateFormat.format( now );
						logger.info("targetRule recv cnf time:"+nowTime);
						dvStatus.getDeviceStatusCommon().setTargetRuleSyncSuccTime(TimeUtils.timeFormatter.format(new Date()));
						
					}
					
					break;
				}
				
			}

		/*	if((sendReqCnf.getCnfInd()) == 0)
			{
				dvStatus.getDeviceStatusCommon().setTargetRuleSyncSuccTime(TimeUtils.timeFormatter.format(new Date()));
			}*/
			
			
		}
		
		/* 是发送特殊归属地的cnf消息 则处理，否则则略过 */
		if (cnfType.matches("00D5"))
		{
			/* 遍历链表 */
			int areaListCnt = areaRuleSendList.size();
			for (int i=0; i < areaListCnt; i++) {
				/* 根据流水号和设备号查找链表节点，找到则同步指示标志位置为1 */
				RuleSend areaRule = areaRuleSendList.get(i);
				isTaretOrArea = 1;
				logger.info("recv sessiondId: "+ sessionId);
				logger.info("deviceSn :" + deviceSn + " list devicesn :" + areaRule.getSerialNum());
				logger.info("sendReqCnf.getCnfInd(): " + sendReqCnf.getCnfInd());
				
				if(deviceSn.equals(areaRule.getSerialNum()))
				{
					this.handleTargetSessionId(i, isTaretOrArea, areaRule, sendReqCnf);
					
					areaRule = areaRuleSendList.get(i);
					allsegIsSucc = this.checkAllSegSymbol(areaRule);
					
					/*if((areaRule.getSessionId() == sessionId) && (deviceSn.equals(areaRule.getSerialNum())) && 
							((sendReqCnf.getCnfInd()) == 0))*/
					if (allsegIsSucc == true)
					{
						areaRule.setSyncIndexSymbol(1);
						areaRuleSendList.set(i, areaRule);
						
						Date now = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
						String nowTime = dateFormat.format( now );
						logger.info("areaRule  recv cnf time:"+nowTime);
						dvStatus.getDeviceStatusCommon().setAreaRuleSyncSuccTime(TimeUtils.timeFormatter.format(new Date()));	
						
					}
					break;
				}
				
				
			}
			
			/*if((sendReqCnf.getCnfInd()) == 0)
			{
				dvStatus.getDeviceStatusCommon().setAreaRuleSyncSuccTime(TimeUtils.timeFormatter.format(new Date()));	
			}*/
			
		}
		
		if (cnfType.matches("00AF"))
		{
			/* 暂时不做处理 */
		}
		
	}
	
	private void handleTargetSessionId(int index, int isTaretOrArea, RuleSend ruleSend, RcvReqCnf sendReqCnf)
	{
		int sessionId = sendReqCnf.getSessionId();
		Object segIdObj = ruleSend.getSessionIdSegIdMap().get(sessionId);
		
		if ((segIdObj != null)&&(sendReqCnf.getCnfInd()==0))
		{
			int segId = (int)segIdObj;
			ruleSend.getSegIdSyncSymbolMap().put(segId, 1);
			switch(isTaretOrArea)
			{
			case 0:
				targetRuleSendList.set(index, ruleSend);
				break;
			case 1:
				areaRuleSendList.set(index, ruleSend);
				break;
			default:
				break;
			}
		}
		
				
	}
	
	private boolean checkAllSegSymbol(RuleSend ruleSend)
	{
		boolean allIsTrue = true;
		Map<Integer, Object> segIdSyncSymbol = ruleSend.getSegIdSyncSymbolMap();
		for(Map.Entry<Integer, Object> entry:segIdSyncSymbol.entrySet())
		{
			int symbol = (int)entry.getValue();
			if (symbol == 0)
			{
				return false;
			}
		}
		return allIsTrue;
	}

}
