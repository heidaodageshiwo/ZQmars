package com.iekun.ef.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.SysLogMapper;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.OperCode;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.SysLog;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.util.CommonConsts;
import com.iekun.ef.util.TimeUtils;

@Service
public class LoggerService {

	
	private static Logger logger = LoggerFactory.getLogger(LoggerService.class);
	
	@Autowired
	SysLogMapper sysLoggerMapper;
	
	@Autowired
	DeviceService deviceService;
	
	public JSONObject queryLoggerData(Integer draw, Integer length, Integer start, String startDate, String endDate,
			String type, String userName, String deviceSn)
	{
		long totalCnt = 0;
		boolean status = false;
		Map<String,Object> params=new HashMap<String, Object>();
        params.put("length", length);
        params.put("start", start);
        
        if (startDate == null || startDate.isEmpty())
        {
        	params.put("startDate", null);
            params.put("endDate", null);
        }
        else
        {
        	startDate = startDate.replace("/", "");
        	startDate = startDate.replace(" ", "");
        	startDate = startDate.replace(":", "");
         	endDate = endDate.replace("/", "");
        	endDate = endDate.replace(" ", "");
        	endDate = endDate.replace(":", "");
        
        	params.put("startDate", startDate);
            params.put("endDate", endDate);
        }	
        
        if (type == null || Integer.valueOf(type) == 99)
        {
        	params.put("type", null);
        }
        else
        {
           	params.put("type", type);
        }
        
        if (userName == null || userName.isEmpty())
        {
        	params.put("userName", null);
        }
        else
        {
        	params.put("userName", userName);
        }
        
        if (deviceSn == null || deviceSn.isEmpty())
        {
        	params.put("deviceSn", null);
        }
        else
        {
        	params.put("deviceSn", deviceSn);
        }
        
        if(SigletonBean.getRoleId() != 1)
		{
        	/*日志的查询和导出，系统管理员权限的用户能看到所有日志，其他角色用户只能看到自己的日志*/
        	params.put("owerId", SigletonBean.getUserId());
		}
        else
        {
        	params.put("owerId", null);
        }
        
        JSONObject jo=new JSONObject();
        JSONArray ja = new JSONArray();
        
        if( ( startDate != null) && ( endDate != null ) &&
                (!startDate.isEmpty()) && (!endDate.isEmpty())   )
        {
	        List<SysLog>  sysLogList =(List<SysLog>) sysLoggerMapper.selectLogList(params);
	     
		    for (SysLog sysLog : sysLogList) {
		    	JSONObject sysLogJo = new JSONObject();
		    	sysLogJo.put("id" , sysLog.getId());
		    	sysLogJo.put("time" , TimeUtils.getFormatTimeStr(sysLog.getOperTime()));
		    	sysLogJo.put("username" , sysLog.getUserName());
		    	sysLogJo.put("operator" , sysLog.getOperName());
		    	sysLogJo.put("deviceSn" , sysLog.getDeviceSn());
		    	sysLogJo.put("typeName" , CommonConsts.getLoggerTypeNameByLoggerTypeId(sysLog.getType()));
		    	sysLogJo.put("type" , sysLog.getType());
		    	
		        ja.add(sysLogJo);
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
			totalCnt = (long)sysLoggerMapper.getLoggerCnt(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return totalCnt;
	}
	
	public void insertLog(String operCodeVal, String serialNum, Long sessionId)
	{
		long userId;
		String siteName = null;
		OperCode operCodeObj = null;
		String operCode = null;
		String operName = null;
		int type = 5;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		
		if (serialNum != null)
		{
			Device device = (Device)SigletonBean.deviceSiteInfoMaps.get(serialNum);
			siteName = device.getSite().getName();
		}
		
		operCodeObj = (OperCode) SigletonBean.operCodeNameTypeMaps.get(operCodeVal);
		if(operCodeObj != null)
		{
			type = CommonConsts.getTypeIdByLoggerTypeName(operCodeObj.getOperType());
			operCode = operCodeObj.getOperCode();
			operName = operCodeObj.getOperName();
		}
		else
		{
			operCode = operCodeVal;
		}
		if (operCodeVal.equalsIgnoreCase("00F1") || operCodeVal.equalsIgnoreCase("00F2"))
		{
			return;
		}
	
		if (operCodeVal.equalsIgnoreCase("00D5") || operCodeVal.equalsIgnoreCase("00D0") || operCodeVal.equalsIgnoreCase("00AF") || (type == 11) || (type == 0))
		{
			userId = 0xffff;
		}
		else 
		{
			Subject currentUser = SecurityUtils.getSubject();
			if(!currentUser.isAuthenticated())
			{
				if (sessionId.equals(new Long(0)))
				{
					userId = 0xffff;
				}
				else
				{
					userId = sessionId.longValue();
				}
				
			}
			else
			{
				userId = SigletonBean.getUserId();
			}
		}
				
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("type", type);
		params.put("userId", userId);
		params.put("operCode", operCode);
		params.put("operName", operName);
		params.put("operTime", df.format(date));
		params.put("operStatus", 1);
		params.put("siteName", siteName);
		params.put("deviceSn", serialNum);
		params.put("sessionId", sessionId);
		try {
			sysLoggerMapper.insertLog(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params = null;
		
	}
	
	
}
