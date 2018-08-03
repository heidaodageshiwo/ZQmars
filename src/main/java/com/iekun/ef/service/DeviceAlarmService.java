package com.iekun.ef.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.DeviceAlarmMapper;
import com.iekun.ef.jms.vo.receive.CurrentAlarm;
import com.iekun.ef.jms.vo.receive.RcvAlarm;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceAlarm;
import com.iekun.ef.model.Site;
import com.iekun.ef.util.CommonConsts;
import com.iekun.ef.util.TimeUtils;

@Service
public class DeviceAlarmService {
	
	@Autowired
	private DeviceAlarmMapper deviceAlarmMapper;
	
	@Autowired
	DeviceService deviceService;
	
	private static Logger logger = LoggerFactory.getLogger(DeviceAlarmService.class);

	public JSONObject getCurrentAlarms()
	{
		JSONObject jsonObject = new JSONObject();
		String dvSn;
		String currAlarmInfo;
		Device device;
		CurrentAlarm currAlarm;
		//todo 数据库记录下来供历史查询，内存存储的是当前最新告警，每次新来的告警进行数据覆盖并写入数据库
		Map<String, Object>  DvAlarmInfoMap = SigletonBean.deviceAlarmInfoMaps;
		JSONArray ja = new JSONArray();
		int alarmCnt = 1;
		if (DvAlarmInfoMap.size() != 0)
		{
			for(Map.Entry<String, Object> dvAlarm:DvAlarmInfoMap.entrySet()){
				
				dvSn = dvAlarm.getKey();
				if ( deviceService.deviceIsBelongToUser(SigletonBean.getUserId(), dvSn) == false)
		    	{
		    		continue;
		    	}
				device = (Device)SigletonBean.deviceSiteInfoMaps.get(dvSn);
				if (device == null)
				{
					continue;
				}
				
				@SuppressWarnings("unchecked")
				ArrayList<CurrentAlarm> currAlarmList = (ArrayList<CurrentAlarm>)dvAlarm.getValue();
				if (currAlarmList.isEmpty())
				{
					continue;
				}
			
				for ( int i=0; i < currAlarmList.size(); i++)
				{
					currAlarm = currAlarmList.get(i);
					currAlarmInfo = currAlarm.getDvAlarm();
			        JSONObject deviceAlarmJo = new JSONObject();
			    	deviceAlarmJo.put("id", alarmCnt);
			    	deviceAlarmJo.put("code", CommonConsts.getAlarmNum(currAlarmInfo));
			    	deviceAlarmJo.put("info", currAlarmInfo);
			    	deviceAlarmJo.put("time", currAlarm.getAlarmTime());
			    	deviceAlarmJo.put("deviceSN", dvSn);
			    	deviceAlarmJo.put("deviceName", device.getName());
			    	deviceAlarmJo.put("siteSN", device.getSite().getSn());
			    	deviceAlarmJo.put("siteName", device.getSite().getName());
			    	alarmCnt ++;
			        ja.add(deviceAlarmJo);
				}
				
			}
		}
		
		 jsonObject.put("status", true);
	     jsonObject.put("message", "成功");
	     jsonObject.put("data", ja);

	     return jsonObject;
	
	}
	
	public JSONObject queryHistoryData(Integer draw, Integer length, Integer start, String startDate, String endDate,
			String deviceSN, String deviceName, String alarmCode) {
		// TODO Auto-generated method stub
		long totalCnt = 0;
		boolean status = false;
		JSONObject jo=new JSONObject();
		Map<String,Object> params=new HashMap<String, Object>();
        params.put("length", length);
        params.put("start", start);
        
        if (startDate == null || startDate.isEmpty())
        {
        	params.put("startDate", null);
            params.put("endDate", null);
            //return jo;
        }
        else
        {
        	/*startDate = startDate + ":00";
        	endDate   = endDate + ":00";*/
        	//params.put("startDate", startDate.replace("/", "-"));
            //params.put("endDate", endDate.replace("/", "-"));
        	startDate = startDate.replace("/", "");
        	startDate = startDate.replace(" ", "");
        	startDate = startDate.replace(":", "");
         	endDate = endDate.replace("/", "");
        	endDate = endDate.replace(" ", "");
        	endDate = endDate.replace(":", "");
        
        	params.put("startDate", startDate);
            params.put("endDate", endDate);
        }	
                             
        if (deviceSN == null || deviceSN.isEmpty())
        {
        	params.put("deviceSn", null);
        	deviceSN = null;
        }
        else
        {
        	params.put("deviceSn", deviceSN);
        }
        
        if (deviceName == null || deviceName.isEmpty())
        {
        	params.put("deviceName", null);
        }
        else
        {
        	params.put("deviceName", "%"+deviceName+"%");
        }
        
        if (alarmCode == null || alarmCode.isEmpty())
        {
        	params.put("alarmCode", null);
        }
        else
        {
        	 params.put("alarmCode", alarmCode);
        }
        
        if (deviceSN == null )
        {
	        Long userId = SigletonBean.getUserId();
	        List<Site> sites= deviceService.getSiteListInfoByUserId(userId);
	        if(sites.size() == 0)
	        {
	        	Site site = new Site();
	        	site.setSn("11323rtytewv12");
	        	sites.add(site);
	        }
	        
	        params.put("sites", sites);
	     
	       
        }
        else
        {
        	params.put("sites", null);
        }
        	
        JSONArray ja = new JSONArray();
        
        if( ( startDate != null) && ( endDate != null ) &&
                (!startDate.isEmpty()) && (!endDate.isEmpty())   )
        {
	        List<DeviceAlarm>  deviceAlarmList =(List<DeviceAlarm>) deviceAlarmMapper.selectDeviceAlarmList(params);
	     
		    for (DeviceAlarm deviceAlarm : deviceAlarmList) {
		    	
		    	/*if ( deviceService.deviceIsBelongToUser(SigletonBean.getUserId(), deviceAlarm.getDeviceSn()) == false)
		    	{
		    		continue;
		    	}*/
		    	
		    	JSONObject deviceAlarmJo = new JSONObject();
		    	
		    	deviceAlarmJo.put("id", deviceAlarm.getId());
		    	deviceAlarmJo.put("code", deviceAlarm.getAlarmCode());
		    	deviceAlarmJo.put("info", deviceAlarm.getAlarmInfo());
		    	deviceAlarmJo.put("time", TimeUtils.getFormatTimeStr(deviceAlarm.getAlarmTime()));
		    	deviceAlarmJo.put("deviceSN", deviceAlarm.getDeviceSn());
		    	if (deviceAlarm.getDevice() == null)
		    	{
		    		deviceAlarmJo.put("deviceName", "未知设备");
		    	}
		    	else
		    	{
		    		deviceAlarmJo.put("deviceName", deviceAlarm.getDevice().getName());
		    	}
		    	if(deviceAlarm.getSite() == null)
		    	{
		    		deviceAlarmJo.put("siteSN", "未知站点sn");
			    	deviceAlarmJo.put("siteName", "未知站点名");
		    	}
		    	else
		    	{
		    		deviceAlarmJo.put("siteSN", deviceAlarm.getSite().getSn());
			    	deviceAlarmJo.put("siteName", deviceAlarm.getSite().getName());
		    	}
		    	
    	
		        ja.add(deviceAlarmJo);
		        status = true;
			}
		    if(status == true)
	        {
		    	/*jo.put("status", true);
		    	jo.put("message", "成功");
		    	jo.put("data", ja);*/
		    	
		    	totalCnt = this.getTotalCount(params);
		    	jo.put("draw", draw);
		    	jo.put("recordsTotal", totalCnt);
		    	jo.put("recordsFiltered", totalCnt);
		    	jo.put("data", ja);
	            
	        }
		    else
		    {
		    	logger.info("获取历史记录为空！");
		    	jo.put("draw", draw);
	        	jo.put("recordsTotal", 0);
	        	jo.put("recordsFiltered", 0);
	        	jo.put("data", ja);
		    }
	    }
        else
        {
        	/*jo.put("status", false);
        	jo.put("message", "查询失败");*/
        	
        	jo.put("draw", draw);
        	jo.put("recordsTotal", 0);
        	jo.put("recordsFiltered", 0);
        	jo.put("data", ja);

        }
	   
	    return jo;
		
	}
	
	public long getTotalCount(Map<String, Object> params) 
	{
		
		long totalCnt = 0;
		      
        try {
			totalCnt = (long)deviceAlarmMapper.getDeviceAlarmCnt(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return totalCnt;
	}
}
