package com.iekun.ef.scheduling;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.DeviceStatusCommon;
import com.iekun.ef.service.LoggerService;

@Component
public class DeviceLinkDetectScheduleTask {

	private Logger logger = LoggerFactory.getLogger(DeviceLinkDetectScheduleTask.class);
	
	Map<String, Object>  dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
	DeviceStatus deviceStatus;
	
	@Autowired
	LoggerService loggerServ;
	
	
	@Value(" ${linkDetect.interval}")  
    private int linkDetectTimeInterval;
	
	@Scheduled(fixedDelay = 5000)
	public void deviceLinkDetectTaskProcess() {
		 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
        for(Map.Entry<String, Object> entry:dvStatusMaps.entrySet())
        {
        	deviceStatus = (DeviceStatus)(entry.getValue());
        	
        	Date nowDate =  new Date();
			Calendar c = new GregorianCalendar();
			c.setTime(nowDate);//设置参数时间
			c.add(Calendar.SECOND,-linkDetectTimeInterval);
			nowDate = c.getTime(); 
			String noDateStr = df.format(nowDate);
			if (deviceStatus.getDeviceStatusCommon().getStatusUpdateTime() == null)
			{
				deviceStatus.getDeviceStatusCommon().setDeviceStatus("offline");
				//loggerServ.insertLog("offline", entry.getKey(), new Long(0));
			}
			else
			{
				if ((noDateStr.compareTo(deviceStatus.getDeviceStatusCommon().getStatusUpdateTime()) >= 0))
				{
					if ((deviceStatus.getDeviceStatusCommon().getDeviceStatus().equals("offline")) == false)
					{
						deviceStatus.getDeviceStatusCommon().setDeviceStatus("offline");
		        		loggerServ.insertLog("offline", entry.getKey(), new Long(0));
		        		logger.info(entry.getKey() + " is offline");
					}
	        		
				}
				
			}
        	
        }	
		 
	 }
}
