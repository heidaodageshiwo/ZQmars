package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
/*import java.util.Date;*/
import java.util.GregorianCalendar;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvAjustTime;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.util.TimeUtils;

@Service
public class HandleRcvAjustTime {

	String sessionId;
	
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvAjustTime.class);
	
	//@Override
	public void handleMessage(Message message, OamMessageService oamMsgServ) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvAjustTime rcvAjustTime = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvAjustTime.class);
		
		SigletonBean.putToMap(sessionId, rcvAjustTime.toString());
		this.handleRcvAjustTime(deviceSn, rcvAjustTime, oamMsgServ);
		
	}

	/**
	 * 读取rcvAjustTime消息,处理rcvAjustTime消息
	 * @param rcvAjustTime
	 */
	private void handleRcvAjustTime(String deviceSn,  RcvAjustTime rcvAjustTime, OamMessageService oamMsgServ)
	{
		GregorianCalendar gc;
		SimpleDateFormat format;
		format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		if (DvStatus != null)
		{
			gc = new GregorianCalendar(); 
		    gc.setTimeInMillis(rcvAjustTime.getcSecond() * 1000 + rcvAjustTime.getCuSecond()/1000);
			DvStatus.getDeviceStatusCommon().setRcvBoardTime(format.format(gc.getTime()));
	
			//gc.setTimeInMillis(System.currentTimeMillis());
			// DvStatus.getDeviceStatusCommon().setSendServTime(format.format(gc.getTime()));
			//DvStatus.getDeviceStatusCommon().setSendServTime(TimeUtils.timeFormatter.format(new Date()));
			Date nowTime = new Date(System.currentTimeMillis());
			DvStatus.getDeviceStatusCommon().setSendServTime(TimeUtils.timeFormatter.format(nowTime));
			long timeLen = (long)System.currentTimeMillis();
			int seconds = (int)(timeLen/1000);
			int miniSecods = (int)(timeLen%1000);
			rcvAjustTime.setsAdjustSecond(seconds);
			rcvAjustTime.setsAdjustuSecond(miniSecods*1000);
			oamMsgServ.setAdjustTimeParam(deviceSn, rcvAjustTime);
		}
	}
}
