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

import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.RuleSend;
import com.iekun.ef.service.TargetAlarmService;
import com.iekun.ef.util.TimeUtils;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.service.HandleSendMessage;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.send.SendImsi;
import com.iekun.ef.jms.vo.send.SendUpdateImsiLib;

@Component
@ConditionalOnProperty(prefix = "efence.properties.old", value = "storage-send-target", matchIfMissing = false)
public class TargetRuleSendScheduleTask {
	
	@Autowired
	private HandleSendMessage handleServ;
	
	@Autowired
	TargetAlarmService targetAlarmServ;
		
	List<RuleSend> targetRuleSendList = SigletonBean.targetRuleSendList;
	private static Logger logger = LoggerFactory.getLogger(TargetRuleSendScheduleTask.class);

	@Value(" ${efence.properties.old.storage-send-target}")
	private boolean storageSendTarget;

	@Scheduled(fixedDelay = 300000)
	public void decimationSendTargetList()throws Exception{
		if(!storageSendTarget) {
			logger.info("efence.properties.old.storage-send-target = false");
			return;
		} else {
			logger.info("efence.properties.old.storage-send-target = true");
		}

		String sessionId;
		int syncIndexSymbol = 1;
		int index = 0;
		DeviceStatus DvStatus = null;
		//Object obj = null;
		//obj = this.sendUpdateImsiLibSend();
		//SendUpdateImsiLib sndUdImsiLib = (SendUpdateImsiLib)obj;
		List<SendImsi> list = targetAlarmServ.getAllTargetRules();
		/*if (list.size() == 0)
		{
			logger.info("黑名单为空!");
			return;
		}*/
		/* 从头开始扫描同步黑名单设备列表  */
		for (RuleSend targetRule: targetRuleSendList)
		{
			syncIndexSymbol = targetRule.getSyncIndexSymbol();
			DvStatus = (DeviceStatus)SigletonBean.deviceStatusInfoMaps.get(targetRule.getSerialNum());
			if (DvStatus == null)
			{
				logger.info("找不到 devSn:" + targetRule.getSerialNum() + "的设备");
				continue;
			}
			if(( 0 == syncIndexSymbol)&&((DvStatus.getDeviceStatusCommon().getDeviceStatus().equals("run"))||(DvStatus.getDeviceStatusCommon().getDeviceStatus().equals("warning"))||(DvStatus.getDeviceStatusCommon().getDeviceStatus().equals("willexpire"))))
			{
				/* 如果同步指示标志位为0，则把黑名单数据下发到该设备，并刷新流水号字段 */
				/*sessionId = this.getSessionId();
				targetRule.setSessionId(Integer.parseInt(sessionId));
				targetRuleSendList.set(index, targetRule);
				DvStatus.getDeviceStatusCommon().setTargetRuleSyncSendTime(TimeUtils.timeFormatter.format(new Date()));
				handleServ.handleSendMessageWithoutDelay(new Message("FFFF", "00D0", targetRule.getSerialNum(), 20, sessionId, obj.toString()));
				*/
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String nowTime = dateFormat.format( now );
				//logger.info("targetRule send time:"+ nowTime + "  device_sn:" + targetRule.getSerialNum());
				this.imsiLibSend(index, targetRule, list);
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

	//黑名单
	private Object sendUpdateImsiLibSend() throws Exception {
		
		List<SendImsi> list = targetAlarmServ.getAllTargetRules();
		
		SendUpdateImsiLib sendUpdateImsiLib=new SendUpdateImsiLib();
		sendUpdateImsiLib.setNumInLib(list.size());
		
		sendUpdateImsiLib.setListRecvImsi(list);
		
		return sendUpdateImsiLib;
	}
	
	private void imsiLibSend(int index, RuleSend targetRule, List<SendImsi> list)
	{
		SendUpdateImsiLib sendUpdateImsiLib;
		List<SendImsi> segList;
		int symbol;
		int listSize = list.size();
		int sendCnt  = (listSize-listSize%50)/50;
		//int sendCount = (listSize + (50 -listSize%50))/50;
		
		
		if (targetRule.getSegIdSyncSymbolMap() == null)
		{
			Map<Integer, Object> segIdSyncSymbolMap= new ConcurrentHashMap<Integer,Object>();
			targetRule.setSegIdSyncSymbolMap(segIdSyncSymbolMap);
		}
		
		if (targetRule.getSessionIdSegIdMap() == null)
		{
			Map<Integer, Object> sessionIdSegIdMap = new ConcurrentHashMap<Integer,Object>();
			targetRule.setSessionIdSegIdMap(sessionIdSegIdMap);
			
		}
		targetRuleSendList.set(index, targetRule);
		
		/*for(int i =0; i < sendCount-1; i ++)
		{
			if(targetRule.getSegIdSyncSymbolMap().get(i) == null)
			{
				targetRule.getSegIdSyncSymbolMap().put(i, 0);
			}
			else
			{
				symbol = (int)(targetRule.getSegIdSyncSymbolMap().get(i));
				if(symbol == 1)
				{
					continue;
				}
			}
			
			sendUpdateImsiLib=new SendUpdateImsiLib();
			segList = new ArrayList<SendImsi>();
			
			
			for(int j =0; j < 50; j++)
			{
				segList.add(list.get(i*50 + j));
			}
			sendUpdateImsiLib.setSerialNumber(i+1);
			sendUpdateImsiLib.setNumInLib(50);
			
			sendUpdateImsiLib.setListRecvImsi(segList);
			this.imsiLibSegSend(index, i, targetRule, sendUpdateImsiLib);
		}*/
		if(sendCnt == 0)
		{
			if(targetRule.getSegIdSyncSymbolMap().get(0) == null)
			{
				targetRule.getSegIdSyncSymbolMap().put(0, 0);
				symbol = 0;
			}
			else
			{
				symbol = (int)(targetRule.getSegIdSyncSymbolMap().get(0));
			}
			
			if (symbol == 0)
			{
				sendUpdateImsiLib=new SendUpdateImsiLib();
				sendUpdateImsiLib.setSerialNumber(1);
				sendUpdateImsiLib.setNumInLib(list.size());
				
				sendUpdateImsiLib.setListRecvImsi(list);
				this.imsiLibSegSend(index, 0, targetRule, sendUpdateImsiLib);
			}
			
			
		}
		else
		{
			for (int i =0; i < sendCnt; i ++)
			{
				if(targetRule.getSegIdSyncSymbolMap().get(i) == null)
				{
					targetRule.getSegIdSyncSymbolMap().put(i, 0);
					symbol = 0;
				}
				else
				{
					symbol = (int)(targetRule.getSegIdSyncSymbolMap().get(i));
				}
				
				if (symbol == 0)
				{
					sendUpdateImsiLib=new SendUpdateImsiLib();
					segList = new ArrayList<SendImsi>();
					for(int j =0; j < 50; j++)
					{
						segList.add(list.get(i*50 + j));
					}
					sendUpdateImsiLib.setSerialNumber(i+1);
					sendUpdateImsiLib.setNumInLib(50);
					
					sendUpdateImsiLib.setListRecvImsi(segList);
					this.imsiLibSegSend(index, i, targetRule, sendUpdateImsiLib);
				}
			}
			
			int listmodVal = listSize%50;
			
			if (listmodVal != 0)
			{  
				if(targetRule.getSegIdSyncSymbolMap().get(sendCnt) == null)
				{
					targetRule.getSegIdSyncSymbolMap().put(sendCnt, 0);
					symbol = 0;
				}
				else
				{
					symbol = (int)(targetRule.getSegIdSyncSymbolMap().get(sendCnt));
				}
				
				if(symbol == 0)
				{
					sendUpdateImsiLib=new SendUpdateImsiLib();
					segList = new ArrayList<SendImsi>();
					for(int k=0; k < listmodVal; k++)
					{
						segList.add(list.get(sendCnt*50 + k));
					}
					sendUpdateImsiLib.setSerialNumber(sendCnt+1);
					sendUpdateImsiLib.setNumInLib(listmodVal);
					sendUpdateImsiLib.setListRecvImsi(segList);
					
					this.imsiLibSegSend(index, sendCnt,targetRule, sendUpdateImsiLib);
				}
				
				
			}
		}
		
	}
	
	private void imsiLibSegSend(int ruleSendIndex, int segId, RuleSend targetRule, SendUpdateImsiLib sendUpdateImsiLib)
	{
		String sessionId;
		sessionId = this.getSessionId();
		targetRule.setSessionId(Integer.parseInt(sessionId));
		targetRule.getSessionIdSegIdMap().put(Integer.parseInt(sessionId), segId);
		DeviceStatus DvStatus = (DeviceStatus)SigletonBean.deviceStatusInfoMaps.get(targetRule.getSerialNum());
		targetRuleSendList.set(ruleSendIndex, targetRule);
		DvStatus.getDeviceStatusCommon().setTargetRuleSyncSendTime(TimeUtils.timeFormatter.format(new Date()));
		logger.info("segId: " + segId);
		try {
			handleServ.handleSendMessageWithoutDelay(new Message("FFFF", "00D0", targetRule.getSerialNum(), 20, sessionId, sendUpdateImsiLib.toString()));
			Thread.currentThread().sleep(50);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	

}
