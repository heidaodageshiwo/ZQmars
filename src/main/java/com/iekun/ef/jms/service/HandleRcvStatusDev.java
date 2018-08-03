package com.iekun.ef.jms.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvStatus;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.DeviceStatusDebug;
import com.iekun.ef.model.DeviceStatusEnviroment;
import com.iekun.ef.model.DeviceStatusLicense;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.util.TimeUtils;


public class HandleRcvStatusDev{
	String sessionId;
	
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;

	//@Override
	public void handleMessage(Message message,  LoggerService  loggerServ) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvStatus rcvStatus = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvStatus.class);

		this.handleRcvStatus(deviceSn, rcvStatus, loggerServ);
		/*try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	/**
	 * 读取RcvStatus消息,处理RcvStatus消息
	 * @param rcvStatus
	 * @throws ParseException 
	 */
	@SuppressWarnings("null")
	private void handleRcvStatus(String deviceSn,  RcvStatus rcvStatus, LoggerService  loggerServ) 
	{
		/*long diff;*/
		String expirationTime = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		
		Date backAccordingNow = TimeUtils.getDateBefore(nowDate, 3);
		String backDateStr = df.format(backAccordingNow);
		
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		if (DvStatus != null)
		{
			DeviceStatusEnviroment  devStatusEnv = DvStatus.getDeviceStatusEnviroment();
			DeviceStatusDebug  devStatusDebug 	 = DvStatus.getDeviceStatusDebug();
			DeviceStatusLicense devStatusLicense = DvStatus.getDeviceStatusLicense();
			
			devStatusEnv.setBoardTemperature(String.valueOf(rcvStatus.getTemperature()));
			devStatusEnv.setCpuLoader(String.valueOf(rcvStatus.getCpu_Load()));
			
			devStatusDebug.setCount_MSG3_success(rcvStatus.getCount_MSG3_success());
			devStatusDebug.setCount_MSG4(rcvStatus.getCount_MSG4());
			devStatusDebug.setCount_MSG5(rcvStatus.getCount_MSG5());
			devStatusDebug.setCount_RACH(rcvStatus.getCount_RACH());
			devStatusDebug.setCount_schel_MSG3(rcvStatus.getCount_schel_MSG3());
			devStatusDebug.setCount_UE_L3(rcvStatus.getCount_UE_L3());
			devStatusLicense.setExpirationTime(rcvStatus.getExpiration_time());
			if((DvStatus.getDeviceStatusCommon().getDeviceStatus()).equals("offline"))
			{
				DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
				loggerServ.insertLog("online", deviceSn, new Long(0));
			}
			DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
			loggerServ.insertLog("online", deviceSn, new Long(0));
			DvStatus.getDeviceStatusCommon().setStatusUpdateTime(sdf.format(nowDate));
			
			//diff = nowDate.getTime() - df.parse(rcvStatus.getExpiration_time()).getTime();
			//if(diff > 0)
			expirationTime = TimeUtils.getFormatTimeStr(rcvStatus.getExpiration_time());
			//expirationTime = "2017-02-06 13:59:59";
			if (noDateStr.compareTo(expirationTime) >= 0)
			{
				devStatusLicense.setIsExpiration("已过期");
				DvStatus.getDeviceStatusCommon().setDeviceStatus("expiredfailure");
			}
			else
			{
				
				if(backDateStr.compareTo(expirationTime) >= 0)
				{
					DvStatus.getDeviceStatusCommon().setDeviceStatus("willexpire");
					devStatusLicense.setIsExpiration("即将过期");
				}
				else
				{
					devStatusLicense.setIsExpiration("未过期");
				}
				
			}  
			
			/*Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			
			try {
				c1.setTime(df.parse(rcvStatus.getExpiration_time()));
				c2.setTime(df.parse(noDateStr));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (c1.compareTo(c2) < 0)
			{
				devStatusLicense.setIsExpiration("已过期");
			}
			else
			{
				devStatusLicense.setIsExpiration("未过期");
			}  */
			
		    
		}
	}
	
	
	public static boolean dateCompare(Date bDate, Date eDate)
	{
		boolean flag = false;
		try 
		{
			
			if (bDate.before(eDate) || bDate.equals(eDate))
			{
				flag = true;
			}
		}
		catch (Exception e) 
		{
		
			flag = false;
		}
		
		return flag;
	}
	
}
