package com.iekun.ef.scheduling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.service.HandleSendMessage;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.send.SendLocation;
import com.iekun.ef.jms.vo.send.SendUpdateLocationLib;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.RuleSend;
import com.iekun.ef.service.TargetAlarmService;
import com.iekun.ef.util.TimeUtils;

@Component
@ConditionalOnProperty(prefix = "efence.properties.old", value = "storage-send-area", matchIfMissing = false)
public class AreaRuleSendScheduleTask {

	private static Logger logger = LoggerFactory.getLogger(AreaRuleSendScheduleTask.class);

	@Autowired
	private HandleSendMessage handleServ;
	
	@Autowired
	TargetAlarmService targetAlarmServ;

	List<RuleSend> areaRuleSendList = SigletonBean.areaRuleSendList;

	@Value(" ${efence.properties.old.storage-send-area}")
	private boolean storageSendArea;

	@Scheduled(fixedDelay = 300000)
	public void decimationSendAreaRuleList()throws Exception{
		if(!storageSendArea) {
			logger.info("efence.properties.old.storage-send-area = false");
			return;
		} else {
			logger.info("efence.properties.old.storage-send-area = true");
		}

		String sessionId;
		int syncIndexSymbol = 1;
		int index = 0;
		DeviceStatus DvStatus;
		/*Object obj = null;
		obj = this.sendUpdateAreaLibSend();
		SendUpdateLocationLib sndUdLocLib = (SendUpdateLocationLib)obj;*/
		List<SendLocation> list = targetAlarmServ.getAllAreaRules();
		//if (sndUdLocLib.getListRecvLocation().isEmpty())
		/*if(list.size() == 0)
		{
			logger.info("特殊归属地名单为空 !");
			return;
		}*/
		/* 从头开始扫描同步区域规则设备列表  */
		for (RuleSend areaRule: areaRuleSendList)
		{
			syncIndexSymbol = areaRule.getSyncIndexSymbol();
			DvStatus = (DeviceStatus)SigletonBean.deviceStatusInfoMaps.get(areaRule.getSerialNum());
			if (DvStatus == null)
			{
				logger.info("找不到 devSn:" + areaRule.getSerialNum() + "的设备");
				continue;
			}
			if(( 0 == syncIndexSymbol)&&((DvStatus.getDeviceStatusCommon().getDeviceStatus().equals("run"))||(DvStatus.getDeviceStatusCommon().getDeviceStatus().equals("warning"))||(DvStatus.getDeviceStatusCommon().getDeviceStatus().equals("willexpire"))))
			{
				// 如果同步指示标志位为0，则把区域规则数据下发到该设备，并刷新流水号字段 
				/* sessionId = this.getSessionId();
				areaRule.setSessionId(Integer.parseInt(sessionId));
				areaRuleSendList.set(index, areaRule);
				DvStatus.getDeviceStatusCommon().setAreaRuleSyncSendTime(TimeUtils.timeFormatter.format(new Date()));
				handleServ.handleSendMessageWithoutDelay(new Message("FFFF", "00D5", areaRule.getSerialNum(), 20, sessionId, obj.toString()));
			    */
				this.areaLibSend(index, areaRule, list);
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String nowTime = dateFormat.format( now );
				//logger.info("areaRule send time:"+nowTime);
			}
			
			index ++;
		}
	}
	
	private synchronized String getSessionId(){
		int sessionId = SigletonBean.sessionId++;
		if(sessionId > SigletonBean.maxSessionId){
			SigletonBean.sessionId = 0;
			sessionId = 1;
		}
		return String.valueOf(sessionId);
	}

	//区域规则
	private Object sendUpdateAreaLibSend() throws Exception {
		
		List<SendLocation> list = targetAlarmServ.getAllAreaRules();
		
		SendUpdateLocationLib sendUpdateLocationLib=new SendUpdateLocationLib();
		sendUpdateLocationLib.setNumInLib(list.size());
		
		sendUpdateLocationLib.setListRecvLocation(list);
		
		return sendUpdateLocationLib;
	}
	
	private void areaLibSend(int index, RuleSend areaRule, List<SendLocation> list)
	{
		SendUpdateLocationLib sendUpdateLocationLib;
		List<SendLocation> segList;
		int symbol;
		int listSize = list.size();
		int sendCnt  = (listSize-listSize%50)/50;
		
		if (areaRule.getSegIdSyncSymbolMap() == null)
		{
			Map<Integer, Object> segIdSyncSymbolMap= new ConcurrentHashMap<Integer,Object>();
			areaRule.setSegIdSyncSymbolMap(segIdSyncSymbolMap);
		}
		
		if (areaRule.getSessionIdSegIdMap() == null)
		{
			Map<Integer, Object> sessionIdSegIdMap = new ConcurrentHashMap<Integer,Object>();
			areaRule.setSessionIdSegIdMap(sessionIdSegIdMap);
			
		}
		areaRuleSendList.set(index, areaRule);
		if(sendCnt == 0)
		{
			if(areaRule.getSegIdSyncSymbolMap().get(0) == null)
			{
				areaRule.getSegIdSyncSymbolMap().put(0, 0);
				symbol = 0;
			}
			else
			{
				symbol = (int)(areaRule.getSegIdSyncSymbolMap().get(0));
			}
			
			if (symbol == 0)
			{
				sendUpdateLocationLib=new SendUpdateLocationLib();
				sendUpdateLocationLib.setSerialNumber(1);
				sendUpdateLocationLib.setNumInLib(list.size());
				
				sendUpdateLocationLib.setListRecvLocation(list);
				this.areaLibSegSend(index, 0, areaRule, sendUpdateLocationLib);
			}
			
		}
		else
		{
			for (int i =0; i < sendCnt; i ++)
			{
				if(areaRule.getSegIdSyncSymbolMap().get(i) == null)
				{
					areaRule.getSegIdSyncSymbolMap().put(i, 0);
				}
				else
				{
					symbol = (int)(areaRule.getSegIdSyncSymbolMap().get(i));
					if(symbol == 1)
					{
						continue;
					}
				}
				
				sendUpdateLocationLib=new SendUpdateLocationLib();
				segList = new ArrayList<SendLocation>();
				for(int j =0; j < 50; j++)
				{
					segList.add(list.get(i*50 + j));
				}
				sendUpdateLocationLib.setSerialNumber(i+1);
				sendUpdateLocationLib.setNumInLib(50);
				
				sendUpdateLocationLib.setListRecvLocation(segList);
				this.areaLibSegSend(index, i, areaRule, sendUpdateLocationLib);
			}
			
			int listmodVal = listSize%50;
			
			if (listmodVal != 0)
			{
				if(areaRule.getSegIdSyncSymbolMap().get(sendCnt) == null)
				{
					areaRule.getSegIdSyncSymbolMap().put(sendCnt, 0);
					symbol = 0;
				}
				else
				{
					symbol = (int)(areaRule.getSegIdSyncSymbolMap().get(sendCnt));
				}
				
				if(symbol == 0)
				{
					sendUpdateLocationLib=new SendUpdateLocationLib();
					segList = new ArrayList<SendLocation>();
					for(int k=0; k < listmodVal; k++)
					{
						segList.add(list.get(sendCnt*50 + k));
					}
					sendUpdateLocationLib.setSerialNumber(sendCnt+1);
					sendUpdateLocationLib.setNumInLib(listmodVal);
					sendUpdateLocationLib.setListRecvLocation(segList);
					
					this.areaLibSegSend(index, sendCnt,areaRule, sendUpdateLocationLib);
				}
				
				
			}
		}
		
	}
	
	private void areaLibSegSend(int ruleSendIndex, int segId, RuleSend areaRule, SendUpdateLocationLib sendUpdateLocationLib)
	{
		String sessionId;
		sessionId = this.getSessionId();
		areaRule.setSessionId(Integer.parseInt(sessionId));
		areaRule.getSessionIdSegIdMap().put(Integer.parseInt(sessionId), segId);
		DeviceStatus DvStatus = (DeviceStatus)SigletonBean.deviceStatusInfoMaps.get(areaRule.getSerialNum());
		areaRuleSendList.set(ruleSendIndex, areaRule);
		DvStatus.getDeviceStatusCommon().setAreaRuleSyncSendTime(TimeUtils.timeFormatter.format(new Date()));
		//setTargetRuleSyncSendTime(TimeUtils.timeFormatter.format(new Date()));
		
		try {
			handleServ.handleSendMessageWithoutDelay(new Message("FFFF", "00D5", areaRule.getSerialNum(), 20, sessionId, sendUpdateLocationLib.toString()));
			Thread.currentThread().sleep(50);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
