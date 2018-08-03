package com.iekun.ef.jms.service;


import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.DeviceAlarmMapper;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.CurrentAlarm;
import com.iekun.ef.jms.vo.receive.RcvAlarm;
import com.iekun.ef.jms.vo.receive.RcvAlarms;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.User;
import com.iekun.ef.service.SmsService;
import com.iekun.ef.service.SystemParaService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.service.EmailService;
import com.iekun.ef.service.MessageService;
import com.iekun.ef.util.TimeUtils;


public class HandleRcvAlarm {

	String sessionId;
	
	/*@Autowired
	private UserService userService;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private SystemParaService SysParaServ;
	
	@Autowired
	private MessageService msgServ;*/
	
	Map<String, Object>  DvAlarmInfoMap = SigletonBean.deviceAlarmInfoMaps;
	
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	
	private static Logger logger = LoggerFactory.getLogger(HandleRcvAlarm.class);
	
	
	
	private MessageToDataBase messageToDb = new MessageToDataBase();
	//@Override
	public void handleMessage(Message message, DeviceAlarmMapper dvAlarmMapper, UserService userService, SmsService smsService, EmailService emailService, SystemParaService SysParaServ, MessageService msgServ) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvAlarms rcvAlarms = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvAlarms.class);
		
		this.handleRcvAlarms(deviceSn, rcvAlarms, dvAlarmMapper);
		
		String dvAlarmSms = SysParaServ.getSysPara("devAlarmSms");
		if(dvAlarmSms != null)
		{
			if (dvAlarmSms.equals("1"))
			{
				if (rcvAlarms.getRcvAlarmList() != null)
				{
					if(rcvAlarms.getRcvAlarmList().size() != 0)
					{
						this.sendAlarmSms(deviceSn, userService, smsService , msgServ);
						this.sendAlarmEmail(deviceSn, userService, emailService, msgServ);
					}
				}
				
			}
		}	
		
	}

	/**
		 * 读取rcvAlarms消息,处理rcvAlarms消息
	 * @param rcvAlarms
	 */
	@SuppressWarnings("unchecked")
	private void handleRcvAlarms(String deviceSn,  RcvAlarms rcvAlarms, DeviceAlarmMapper dvAlarmMapper)
	{
		/* 两步：1：写入数据库告警表，供历史查询； 2：写入缓存map，供前台当告警前查询  */
		ArrayList<RcvAlarm> rcvAlarmList = rcvAlarms.getRcvAlarmList();
		logger.info("dev alarmcnt =" + rcvAlarmList.size());
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		this.writeIntoCurrentList(deviceSn, rcvAlarmList);
		
		if (rcvAlarms.getRcvAlarmList() != null)
		{
			if(rcvAlarms.getRcvAlarmList().size() != 0)
			{
				this.writeIntoDb(deviceSn, rcvAlarmList, dvAlarmMapper);
				DvStatus.getDeviceStatusCommon().setDeviceStatus("warning");
			}
			else
			{
				DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
			}
			
		}
		else
		{
			DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
		}
	}
	
	
	private void writeIntoCurrentList(String deviceSn,  ArrayList<RcvAlarm> rcvAlarmList)
	{
		ArrayList<CurrentAlarm> currentAlarmList = null;
		CurrentAlarm currentAlarm = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		
		currentAlarmList = (ArrayList<CurrentAlarm>)DvAlarmInfoMap.get(deviceSn);
		
		if(null == currentAlarmList)
		{
			/* 没找到对应的链表，则生成新的链表，有数据则插入，否则链表数据为空 */
			currentAlarmList = new ArrayList<CurrentAlarm>();
			if(rcvAlarmList.size() > 0)
			{
				for (RcvAlarm rcvAlarm : rcvAlarmList)
				{
					currentAlarm = new CurrentAlarm();
					currentAlarm.setAlarmTime(noDateStr);
					currentAlarm.setDvAlarm(rcvAlarm.getDvAlarm().trim());
					currentAlarmList.add(currentAlarm);
				}
			}

			DvAlarmInfoMap.put(deviceSn, currentAlarmList);
		}
		else
		{
			/* 清空对应的链表，如果有新告警，则相当于清除老数据，插入新数据，否则只是清空*/
			currentAlarmList.clear();
			if(rcvAlarmList.size() > 0)
			{
				for (RcvAlarm rcvAlarm : rcvAlarmList)
				{
					currentAlarm = new CurrentAlarm();
					currentAlarm.setAlarmTime(noDateStr);
					currentAlarm.setDvAlarm(rcvAlarm.getDvAlarm().trim());
					currentAlarmList.add(currentAlarm);
				}
				DvAlarmInfoMap.put(deviceSn, currentAlarmList);
			}
			
		}
	}	

	
	private void writeIntoDb(String deviceSn,  ArrayList<RcvAlarm> rcvAlarmList, DeviceAlarmMapper dvAlarmMapper)
	{
		if(rcvAlarmList.size() > 0){
			try {
				messageToDb.insertDvAlarmInfo(deviceSn, rcvAlarmList, dvAlarmMapper);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void sendAlarmSms(String deviceSn, UserService userService, SmsService smsService, MessageService msgServ)
	{
		Map<String, Object> dvUserMaps = SigletonBean.deviceUserMaps;
		List<Long> useIdList = null;
	    long userId;
	    User user = null;
	    String nowDateStr = null;
	   
	    Device device = (Device)SigletonBean.deviceSiteInfoMaps.get(deviceSn);	
		if (device == null)
		{
			logger.info("deviceSn = " + deviceSn + "不存在");
			return;
		}
		String latestAlarmSmsSendTime = device.getDvLatestAlarmSmsSendTime();
		if (latestAlarmSmsSendTime!= null)
		{
			nowDateStr = TimeUtils.getBackWardsAccordingBaseTime(30);
			if (nowDateStr.compareTo(latestAlarmSmsSendTime) < 0)
			{
				
				return;
			}
			else
			{
				device.setDvLatestAlarmSmsSendTime(TimeUtils.getBackWardsAccordingBaseTime(0));
			}
			 
		}
		else
		{
			device.setDvLatestAlarmSmsSendTime(TimeUtils.getBackWardsAccordingBaseTime(0));
		}
		
	    useIdList = (ArrayList<Long>)dvUserMaps.get(deviceSn);
		for(int i=0; i < useIdList.size(); i++)
		{
			userId = useIdList.get(i);
			user = userService.getUserDetailInfo(userId);
			if (user != null)
			{
				smsService.createDvAlarmSms(user.getUserName(), user.getMobilePhone(), device.getName());
				msgServ.createMessage(userId, 0, device.getName() + "设备告警",  "/user/notifications");
			}
			
		}
		
		
	}
	
	private void sendAlarmEmail(String deviceSn, UserService userService, EmailService emailService, MessageService msgServ)
	{
		Map<String, Object> dvUserMaps = SigletonBean.deviceUserMaps;
		List<Long> useIdList = null;
	    long userId;
	    User user = null;
	    String nowDateStr = null;
	    Map<String,Object> params=new HashMap<String, Object>();
	   
	    Device device = (Device)SigletonBean.deviceSiteInfoMaps.get(deviceSn);	
		if (device == null)
		{
			logger.info("deviceSn = " + deviceSn + "不存在");
			return;
		}
		
		String latestAlarmEmailSendTime = device.getDvLatestAlarmEmailSendTime();
		if (latestAlarmEmailSendTime!= null)
		{
			nowDateStr = TimeUtils.getBackWardsAccordingBaseTime(30);
			if (nowDateStr.compareTo(latestAlarmEmailSendTime) < 0)
			{
				
				return;
			}
			else
			{
				device.setDvLatestAlarmEmailSendTime(TimeUtils.getBackWardsAccordingBaseTime(0));
			}
			 
		}
		else
		{
			device.setDvLatestAlarmEmailSendTime(TimeUtils.getBackWardsAccordingBaseTime(0));
		}
    	
		
		params.put("devicesn", deviceSn);
		logger.info("sendAlarmEmail devicesn: "+ deviceSn);
  
    	useIdList = (ArrayList<Long>)dvUserMaps.get(deviceSn);
		for(int i=0; i < useIdList.size(); i++)
		{
			userId = useIdList.get(i);
			user = userService.getUserDetailInfo(userId);
			if (user != null)
			{
				emailService.createEmail( user.getUserName(), user.getEmail(),  params, 2);
			}
			
		}
		
		
	}
	
	

}
