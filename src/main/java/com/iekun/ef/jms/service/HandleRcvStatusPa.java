package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvPaStatus;
import com.iekun.ef.jms.vo.receive.RcvStatus;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.DeviceStatusPowerAmplifier;
/*import com.iekun.ef.model.TargetListSend;*/
import com.iekun.ef.service.LoggerService;

public class HandleRcvStatusPa {
	String sessionId;
	
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvStatusPa.class);
	
	//@Override
	public void handleMessage(Message message,  LoggerService  loggerServ) {
		String deviceSn 	= message.getSerialNum();
		sessionId 	= message.getSessionId();
		RcvPaStatus rcvPaStatus = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvPaStatus.class);
		if(!(sessionId.equals(String.valueOf(0))))
		{
			/*主动上报的sessionid 为0 不为查询所得，所以不入map*/
			SigletonBean.putToMap(sessionId, message.getSubOject().toString());
			logger.info("sessionid:"+ sessionId + "rcvPaStatus:" +message.getSubOject().toString());
		}
		
		this.handleRcvPaStatus(deviceSn, rcvPaStatus, loggerServ);
		
	}

	/**
	 * 读取RrcvPaStatus消息,处理rcvPaStatus消息
	 * @param rcvPaStatus
	 */
	private void handleRcvPaStatus(String deviceSn,  RcvPaStatus rcvPaStatus,  LoggerService  loggerServ)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		DeviceStatus DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		if (DvStatus != null)
		{
			DeviceStatusPowerAmplifier  DvPaStatus = DvStatus.getDeviceStatusPowerAmplifier();
			
			DvPaStatus.setAlc_value(String.valueOf(rcvPaStatus.getAlc_value()));
			DvPaStatus.setCurr_att_Pa(String.valueOf(rcvPaStatus.getCurr_att_Pa()));
			DvPaStatus.setForward_power(String.valueOf(rcvPaStatus.getForward_power()));
			DvPaStatus.setForward_power_2(String.valueOf(rcvPaStatus.getForward_power_2()));
			DvPaStatus.setInverse_power(String.valueOf(rcvPaStatus.getInverse_power()));
			DvPaStatus.setOn_off_Pa(String.valueOf(rcvPaStatus.getOn_off_Pa()));
			DvPaStatus.setStanding_wave_ratio(String.valueOf(rcvPaStatus.getStanding_wave_ratio()));
			DvPaStatus.setTemp(String.valueOf(rcvPaStatus.getTemp()));
			DvPaStatus.setValid(String.valueOf(rcvPaStatus.getValid()));
			DvPaStatus.setWarn_pa(String.valueOf(rcvPaStatus.getWarn_pa()));
			DvPaStatus.setWarn_power(String.valueOf(rcvPaStatus.getWarn_power()));
			DvPaStatus.setWarn_standing_wave_ratio(String.valueOf(rcvPaStatus.getWarn_standing_wave_ratio()));
			DvPaStatus.setWarn_temp(String.valueOf(rcvPaStatus.getWarn_temp()));
			//DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
			if((DvStatus.getDeviceStatusCommon().getDeviceStatus()).equals("offline"))
			{
				DvStatus.getDeviceStatusCommon().setDeviceStatus("run");
				loggerServ.insertLog("online", deviceSn, new Long(0));
			}
			DvStatus.getDeviceStatusCommon().setStatusUpdateTime(noDateStr);
		}
	}
	
	
}
