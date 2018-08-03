package com.iekun.ef.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.iekun.ef.dao.*;
import com.iekun.ef.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.vo.send.SendImsi;
import com.iekun.ef.jms.vo.send.SendLocation;


@Service
public class TargetAlarmService {
	
	@Autowired
	TargetAlarmMapper targetAlarmMapper;
	@Autowired
	TargetAlarmNewEntityMapper targetAlarmNewEntityMapper;
	
	@Autowired
	AreaRuleMapper areaRuleMapper;
	
	@Autowired
	TargetRuleMapper targetRuleMapper;

	@Autowired
	DeviceService deviceService;
	
	@Autowired
	UserService userServ;
	@Autowired
	EarlWarningTaskMapper earlWarningTaskMapper;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	private String date=sdf.format(new Date());
	
	public boolean confirmTargetAlarm(Integer targetId)
	{
		boolean returnVal = true;
		try {
			targetAlarmMapper.setIsRead(Long.valueOf(targetId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnVal = false;
		}
		return returnVal;
	}
	
	public JSONObject getCurrentTargets()
	{
		 /* 获取当前未被确认的告警   ，系统管理员用户也只能看到自己创建黑名单或者列表的告警，不能看到与他不相关的告警。*/
		 boolean status = false;
		 JSONObject jo=new JSONObject();
	     JSONArray ja = new JSONArray();
		
	     Map<String,Object> params=new HashMap<String, Object>();
    	
	    Long userId = SigletonBean.getUserId();  
        long roleId = SigletonBean.getRoleId();
        if (roleId != 1)
        {
        	Map<String,Object> ruleParams=new HashMap<String, Object>();
        	ruleParams.put("creatorId", SigletonBean.getUserId());
        	List<String> targetImsiList = this.getTargetImsiListByCreatorId(ruleParams);
 	        List<String> targetAreaCodeList = this.getAreaCodeListByCreatorId(ruleParams);
 	        
        	if(targetImsiList.size() == 0)
	        {
	        	String targetImsi = "123409321sd23gff";
	        	targetImsiList.add(targetImsi);
	        }
	
        	params.put("targetImsi", targetImsiList);
 	
        	if(targetAreaCodeList.size() == 0)
	        {
	        	String targetAreaCode = "123409321sd23gff";
	        	targetAreaCodeList.add(targetAreaCode);
	        }
	
        	params.put("targetAreaCode", targetAreaCodeList);
        
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
        	 params.put("targetImsi", null);
        	 params.put("targetAreaCode", null);
        	 params.put("sites", null);

         }
	        
       params.put("creatorId", userId);
	   List<TargetAlarm>  targetList =(List<TargetAlarm>) targetAlarmMapper.getCurrentTargetList(params);
     
	    for (TargetAlarm targetAlarm : targetList) {
	    	
	    	JSONObject targetAlarmJo = new JSONObject();
	    	targetAlarmJo.put("id", targetAlarm.getId());
	    	targetAlarmJo.put("remark" , targetAlarm.getRemark());
	     	targetAlarmJo.put("ruleName" , targetAlarm.getRuleName());
	    	targetAlarmJo.put("indication" , targetAlarm.getIndication());
	    	targetAlarmJo.put("imsi" , targetAlarm.getImsi());
	    	targetAlarmJo.put("cityName" , targetAlarm.getCityName());
	    	targetAlarmJo.put("operator" , targetAlarm.getOperator());
	    	targetAlarmJo.put("siteName" , targetAlarm.getSiteName());
	    	if(targetAlarm.getDevice() != null)
	    	{
	    		targetAlarmJo.put("deviceName" , targetAlarm.getDevice().getName());
	    	}
	    	else
	    	{
	    		targetAlarmJo.put("deviceName" , "未知设备");
	    	}
	    	
	    	targetAlarmJo.put("captureTime" , targetAlarm.getCaptureTime());
	    	targetAlarmJo.put("isRead" , targetAlarm.getIsRead());

	        ja.add(targetAlarmJo);
	        status = true;
		}
	    if(status == true)
        {
	    	jo.put("status", true);
	    	jo.put("message", "成功");
	    	jo.put("data", ja);
        }
	    else
	    {
	    	jo.put("status", false);
	    	jo.put("message", "失败");
	    	jo.put("data", ja);
	    }
	 
	 return jo;
 }

	/**
	 * 获取预警列表信息
	 * @return
	 */
	public List<TargetAlarmNewEntity> getCurrentTargetsNew(Map<String, String> params) {
		params.put("userId", String.valueOf(SigletonBean.getUserId()));
		List<TargetAlarmNewEntity> records = targetAlarmNewEntityMapper.selectCurTargetByNameStr(params);
		return records;
	}

	/**
	 * 更新确认状态
	 * @param idsList
	 * @return
	 */
	public void confirmTargetAlarmNew(List<Integer> idsList) {
		Map<String, Object> params = new HashMap<>();
		params.put("userID", SigletonBean.getUserId());
		for(Integer id : idsList) {
			params.put("alarmId", id);
			targetAlarmNewEntityMapper.updateReadedById(params);
		}
	}

	public List<TargetAlarmNewEntity> queryHistoryAlarmDataLimit(Map<String, Object> params){
		return targetAlarmNewEntityMapper.queryHistoryAlarmDataLimit(params);
	}

	public List<TargetAlarmNewEntity> queryHistoryAlarmData(Map<String, Object> params) {
		params.put("userId", SigletonBean.getUserId());
		return targetAlarmNewEntityMapper.selectAllHistoryDataByUserId(params);
	}

	public int queryCountByParams(Map<String, Object> params){
		 return targetAlarmNewEntityMapper.queryCountByParams(params);

	}

	public JSONObject queryHistoryAlarmData(Integer draw, Integer length, Integer start, String startDate, String endDate,
			String ruleName, String homeOwnership, String siteSn,  String deviceSn, String imsi)
	{
			/*  历史预警查询 
		 	JSONObject jsonObject  = new JSONObject();
		 	return jsonObject;*/
		   // TODO Auto-generated method stub
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
	        	/*startDate = startDate + ":00";
	        	endDate   = endDate + ":00";*/
	        	params.put("startDate", startDate.replace("/", "-"));
	            params.put("endDate", endDate.replace("/", "-"));
	        }	
	        
	        if (ruleName == null || ruleName.isEmpty())
	        {
	        	params.put("ruleName", null);
	        }
	        else
	        {
	        	params.put("ruleName", ruleName);
	        }
	        
	        if (homeOwnership == null || homeOwnership.isEmpty())
	        {
	        	params.put("homeOwnership", null);
	        }
	        else
	        {
	        	 params.put("homeOwnership", homeOwnership);
	        }
	       
	        if (siteSn == null || siteSn.isEmpty())
	        {
	        	siteSn = null;
	        	params.put("siteSn", null);
	        }
	        else
	        {
	        	params.put("siteSn", siteSn);
	        }
	        
	        if (deviceSn == null || deviceSn.isEmpty())
	        {
	        	deviceSn = null;
	        	params.put("deviceSn", null);
	        }
	        else
	        {
	        	params.put("deviceSn", deviceSn);
	        }
	        
	        if (imsi == null || imsi.isEmpty())
	        {
	        	params.put("imsi", null);
	        }
	        else
	        {
	        	params.put("imsi", imsi);
	        }
	        
	        long roleId = SigletonBean.getRoleId();
	        if (deviceSn == null && siteSn == null)
	        {
	        	if (roleId != 1)
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
	        	
	        }
	        else
	        {
	        	params.put("sites", null);
	        }
	   
	        long userId = SigletonBean.getUserId();
	        if (roleId != 1)
	        {
	        	Map<String,Object> ruleParams=new HashMap<String, Object>();
	        	ruleParams.put("creatorId", userId);
	        	List<String> targetImsiList = this.getTargetImsiListByCreatorId(ruleParams);
	 	        List<String> targetAreaCodeList = this.getAreaCodeListByCreatorId(ruleParams);
	 	        
	        	if(targetImsiList.size() == 0)
		        {
		        	String targetImsi = "123409321sd23gff";
		        	targetImsiList.add(targetImsi);
		        }
		
	        	params.put("targetImsi", targetImsiList);
        	
	        	if(targetAreaCodeList.size() == 0)
		        {
		        	String targetAreaCode = "123409321sd23gff";
		        	targetAreaCodeList.add(targetAreaCode);
		        }
		
	        	params.put("targetAreaCode", targetAreaCodeList);
	        	params.put("creatorId", userId);
	        	
	         }
	         else
	         {
	        	 params.put("targetImsi", null);
	        	 params.put("targetAreaCode", null);
	        	 params.put("creatorId", null);
	         }
  
	        
	        JSONObject jo=new JSONObject();
	        JSONArray ja = new JSONArray();
	        
	        if( ( startDate != null) && ( endDate != null ) &&
	                (!startDate.isEmpty()) && (!endDate.isEmpty())   )
	        {
		        List<TargetAlarm>  TargetAlarmList =(List<TargetAlarm>) targetAlarmMapper.selectAlarmInfoList(params);
		     
			    for (TargetAlarm targetAlarm : TargetAlarmList) {
			    	/*if ( deviceService.deviceIsBelongToUser(SigletonBean.getUserId(), targetAlarm.getDeviceSn()) == false)
			    	{
			    		continue;
			    	}*/
			    	JSONObject targetAlarmJo = new JSONObject();
			    	targetAlarmJo.put("id", targetAlarm.getId());
			    	targetAlarmJo.put("remark" ,   targetAlarm.getRemark());
			    	targetAlarmJo.put("ruleName" , targetAlarm.getRuleName());
			    	targetAlarmJo.put("indication" , targetAlarm.getIndication());
			    	targetAlarmJo.put("imsi" , targetAlarm.getImsi());
			    	targetAlarmJo.put("cityName" , targetAlarm.getCityName());
			    	targetAlarmJo.put("operator" , targetAlarm.getOperator());
			    	targetAlarmJo.put("siteName" , targetAlarm.getSiteName());
			    	if( targetAlarm.getDevice() != null)
			    	{
			    		targetAlarmJo.put("deviceName" , targetAlarm.getDevice().getName());
			    	}
			    	else
			    	{
			    		targetAlarmJo.put("deviceName" , "未知设备");
			    	}
			    	targetAlarmJo.put("captureTime" , targetAlarm.getCaptureTime());
			    	targetAlarmJo.put("isRead" , targetAlarm.getIsRead());

			        ja.add(targetAlarmJo);
			        status = true;
				}
			    if(status == true)
		        {
			    	/*jo.put("status", true);
			    	jo.put("message", "成功");
			    	jo.put("data", ja);*/
			    	
			    	totalCnt = this.getTotalAlarmCount(params);
			    	jo.put("draw", draw);
			    	jo.put("recordsTotal", totalCnt);
			    	jo.put("recordsFiltered", totalCnt);
			    	jo.put("data", ja);
		            
		        }
			    else
			    {
			    	//logger.info("获取历史记录为空！");
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
	
	private long getTotalAlarmCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		long totalCnt = 0;
	      
        try {
			totalCnt = (long)targetAlarmMapper.getTotalAlarmCount(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return totalCnt;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getBlacklist()
	{
		 /* 获取黑名单列表   */
		boolean status = false;
		JSONObject jo=new JSONObject();
	    JSONArray ja = new JSONArray();
	    ArrayList<JSONObject> receiverListObj = null;
	    Map<String,Object> params=new HashMap<String, Object>();
	    long roleId = SigletonBean.getRoleId();
        if (roleId != 1)
        {
        	params.put("creatorId", SigletonBean.getUserId());
        }
        else
        {
        	params.put("creatorId", null);
        }
        
		List<TargetRule>  blackList =(List<TargetRule>) targetRuleMapper.getTargetRuleList(params);
	    for (TargetRule targetRule : blackList) {
	    	JSONObject targetRuleJo = new JSONObject();
//	    	targetRuleJo.put("id", targetRule.getId());
//
//	    	targetRuleJo.put("name" , targetRule.getName());
//	    	targetRuleJo.put("imsi" , targetRule.getImsi());
//	    	User user = userServ.getUserDetailInfo(targetRule.getCreatorId());
//	    	if(user != null)
//	    	{
//	    		targetRuleJo.put("creator" , user.getUserName());
//	    	}
//	    	else
//	    	{
//	    		targetRuleJo.put("creator" , "未知创建者");
//	    	}
//	    	targetRuleJo.put("createTime" , targetRule.getCreateTime());
//	    	targetRuleJo.put("remark" , targetRule.getRemark());
//
//	    	 if (targetRule.getReceiver().length() != 0)
//	    	 {
//	    		 receiverListObj = JSON.parseObject(targetRule.getReceiver(), new ArrayList<ContactReceiver>().getClass());
//	    	 }
//	    	targetRuleJo.put("receivers" , receiverListObj);
//	        ja.add(targetRuleJo);
//	        status = true;
//		}
//	    if(status == true)
//        {
//	    	jo.put("status", true);
//	    	jo.put("message", "成功");
//	    	jo.put("data", ja);
//        }
//	    else
//	    {
//	    	jo.put("status", false);
//	    	jo.put("message", "失败");
//	    	jo.put("data", ja);
	    }
		 
		 return jo;
	}
	
	public boolean addBlackToList(String name, String imsi, String remark, String receivers)
	{
		long creatorId = SigletonBean.getUserId();
		Integer type =0;
        TargetRule targetRule = new TargetRule(name, imsi, remark, receivers);
        targetRule.setCreateTime(date);
        targetRule.setTargetType(type);
        //targetRule.setCreatorId(creatorId);
        try {
			
        	targetRuleMapper.insert(targetRule);
        	this.clearAllTargetSyncSymbol();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return false;
	
	}


	public boolean delAllBlackList()
	{
		boolean returnval = true;
		targetRuleMapper.updateAllDeleteFlagByPrimaryKey();
		this.clearAllTargetSyncSymbol();
		/*for(int i = 0; i < ids.length; i++)
		{
			try {
				targetRuleMapper.updateDeleteFlagByPrimaryKey(Long.parseLong(ids[i]));
				this.clearAllTargetSyncSymbol();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnval = false;
			}
		}*/
		return returnval;
	}







	public boolean delBlackList(String[] ids)
	{
		boolean returnval = true;
		
		for(int i = 0; i < ids.length; i++)
		{
			try {
				targetRuleMapper.updateDeleteFlagByPrimaryKey(Long.parseLong(ids[i]));
				this.clearAllTargetSyncSymbol();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnval = false;
			}
		}
		return returnval;
	}
	
	 /***
     *  更新消息接收人
     * @param type  Integer, 0-黑名单消息接收人，1-归属地消息接受人
     * @param id Integer, 用户ID
     * @param receivers String， 通知人信息，JSON格式数据字符串
     * @return
     */
	public boolean updateReceviers(Integer type, Integer id, String receivers)
	{
		boolean returnVal = false;
		Map<String,Object> params=new HashMap<String, Object>();
        params.put("id", id);
        params.put("receiver", receivers);
        params.put("updateTime", date);
        
		switch(type)
		{
		case 0:
			try {
				
				targetRuleMapper.updateTargetRuleReceivers(params);
	        	returnVal =  true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				
	        	areaRuleMapper.updateAreaRuleReceivers(params);
	        	returnVal =  true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			
			break;
		}
		
		return returnVal;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getHomeOwnershipList()
	{
		 /* 获取黑名单列表   */
		boolean status = false;
		JSONObject jo=new JSONObject();
	    JSONArray ja = new JSONArray();
	    
	    ArrayList<JSONObject> receiverListObj = null;
	    Map<String,Object> params=new HashMap<String, Object>();
	    long roleId = SigletonBean.getRoleId();
        if (roleId != 1)
        {
        	params.put("creatorId", SigletonBean.getUserId());
        }
        else
        {
        	params.put("creatorId", null);
        }
		List<AreaRule>  homeOwnershipList =(List<AreaRule>) areaRuleMapper.getAreaRuleList(params);
	     
	    for (AreaRule areaRule : homeOwnershipList) {
	    	JSONObject areaRuleJo = new JSONObject();
	    
	    	areaRuleJo.put("id", areaRule.getId());
	    	areaRuleJo.put("name" , areaRule.getName());
	    	areaRuleJo.put("cityName" , areaRule.getSourceCityName());
	    	areaRuleJo.put("cityCode" , areaRule.getSourceCityCode());
	    	areaRuleJo.put("createTime" , areaRule.getCreateTime());
	    	
	    	if (areaRule.getReceiver().length() != 0)
	    	 {
	    		 receiverListObj = JSON.parseObject(areaRule.getReceiver(), new ArrayList<ContactReceiver>().getClass());
	    	 }
	    	User user = userServ.getUserDetailInfo(areaRule.getCreatorId());
	    	if(user != null)
	    	{
	    		areaRuleJo.put("creator" , user.getUserName());
	    	}
	    	else
	    	{
	    		areaRuleJo.put("creator" , "未知创建者");
	    	}
	    	areaRuleJo.put("remark" , areaRule.getRemark());
	    	
	    	areaRuleJo.put("receivers" , receiverListObj);
	        ja.add(areaRuleJo);
	        status = true;
		}
	    if(status == true)
        {
	    	jo.put("status", true);
	    	jo.put("message", "成功");
	    	jo.put("data", ja);
        }
	    else
	    {
	    	jo.put("status", false);
	    	jo.put("message", "失败");
	    	jo.put("data", ja);
	    }
		 
		return jo;
	}
		
	public boolean addHomeOwnership(String name, String cityName, String cityCode, String remark, String receivers)
	{
		long creatorId = SigletonBean.getUserId();
		AreaRule areaRule = new AreaRule(name, cityName, cityCode, remark, receivers);
		areaRule.setCreateTime(date);
		areaRule.setCreatorId(creatorId);
		areaRule.setDeleteFlag(0);
        try {
           	areaRuleMapper.insert(areaRule);
           	this.clearAllAreaSyncSymbol();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return false;
	}
	
	public boolean delHomeOwnership(String[] ids)
	{
		boolean returnval = true;
		
		for(int i = 0; i < ids.length; i++)
		{
			try {
				areaRuleMapper.updateDeleteFlagByPrimaryKey(Long.parseLong(ids[i]));
				this.clearAllAreaSyncSymbol();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnval = false;
			}
		}
		return returnval;
	}
	
	public JSONObject getAllRules()
	{
		boolean status = false;
		JSONObject jo=new JSONObject();
	    JSONArray ja = new JSONArray();
	    Map<String,Object> params=new HashMap<String, Object>();
	    long roleId = SigletonBean.getRoleId();
        if (roleId != 1)
        {
        	params.put("creatorId", SigletonBean.getUserId());
        }
        else
        {
        	params.put("creatorId", null);
        }
        
	    List<AreaRule>  homeOwnershipList =(List<AreaRule>) areaRuleMapper.getAreaRuleList(params);
	    for (AreaRule areaRule : homeOwnershipList) {
	    	JSONObject areaRuleJo = new JSONObject();
	    
	    	areaRuleJo.put("name" , areaRule.getName());
	    	ja.add(areaRuleJo);
	    	status = true;
	    }
	   
	    List<TargetRule>  blackList =(List<TargetRule>) targetRuleMapper.getTargetRuleList(params);
	     
	    for (TargetRule targetRule : blackList) {
	    	JSONObject targetRuleJo = new JSONObject();
	    	
	    	targetRuleJo.put("name" , targetRule.getName());
	    	ja.add(targetRuleJo);
	    	status = true;
	    }
	    
	    if(status == true)
        {
	    	jo.put("status", true);
	    	jo.put("message", "成功");
	    	jo.put("data", ja);
        }
	    else
	    {
	    	jo.put("status", false);
	    	jo.put("message", "失败");
	    	jo.put("data", ja);
	    }
		 
		return jo;
	}
	
	public List<SendImsi> getAllTargetRules()
	{
		List<SendImsi> targetRuleList = (List<SendImsi>) targetRuleMapper.getAllTargetRules();
		return targetRuleList;
	}
	
	public List<SendLocation> getAllAreaRules()
	{
		List<SendLocation> areaRuleList = (List<SendLocation>) areaRuleMapper.getAllAreaRules();
		return areaRuleList;
	}
	
	public List<String> getTargetImsiListByCreatorId(Map<String, Object> params)
	{
		List<String> targetImsiList = (List<String>)targetRuleMapper.getTargetImsiListByUserId(params);
		
		return targetImsiList;
	}
	
	public List<String> getAreaCodeListByCreatorId(Map<String, Object> params)
	{
		List<String> targetImsiList = (List<String>)areaRuleMapper.getTargetAreaCodeListByUserId(params);
		
		return targetImsiList;
	} 
	
	public void clearAllAreaSyncSymbol()
	{
		List<RuleSend>  areaRuleSendList = SigletonBean.areaRuleSendList;
		RuleSend ruleSend;
		for(int i=0; i< areaRuleSendList.size(); i++)
		{
			ruleSend = areaRuleSendList.get(i);
			ruleSend.setSyncIndexSymbol(0);
			
			if (ruleSend.getSegIdSyncSymbolMap() != null)
			{
				ruleSend.getSegIdSyncSymbolMap().clear();
			}
			
			if (ruleSend.getSessionIdSegIdMap() != null)
			{
				ruleSend.getSessionIdSegIdMap().clear();
			}
		}
		
	}
	
	public void clearAllTargetSyncSymbol()
	{
		List<RuleSend> targetRuleSendList = SigletonBean.targetRuleSendList;
		RuleSend ruleSend;
		for(int i=0; i< targetRuleSendList.size(); i++)
		{
			ruleSend = targetRuleSendList.get(i);
			ruleSend.setSyncIndexSymbol(0);
			if (ruleSend.getSegIdSyncSymbolMap() != null)
			{
				ruleSend.getSegIdSyncSymbolMap().clear();
			}
			
			if (ruleSend.getSessionIdSegIdMap() != null)
			{
				ruleSend.getSessionIdSegIdMap().clear();
			}
			
			
		}
		
	}
	
	public void addAreaSyncItemToList(String dvSn)
	{
		List<RuleSend>  areaRuleSendList = SigletonBean.areaRuleSendList;
		
		RuleSend ruleSend = new RuleSend();
		ruleSend.setSerialNum(dvSn);
		ruleSend.setSessionId(0);
		ruleSend.setSyncIndexSymbol(0);

		Map<Integer, Object> sessionIdSegIdMap = new ConcurrentHashMap<Integer,Object>();
		ruleSend.setSessionIdSegIdMap(sessionIdSegIdMap);
		
		Map<Integer, Object> segIdSyncSymbolMap= new ConcurrentHashMap<Integer,Object>();
		ruleSend.setSegIdSyncSymbolMap(segIdSyncSymbolMap);
		
		areaRuleSendList.add(ruleSend);
		
	}
	
	public void addTargetSyncItemToList(String dvSn)
	{
		List<RuleSend>  targetRuleSendList = SigletonBean.targetRuleSendList;
		
		RuleSend ruleSend = new RuleSend();
		
		ruleSend.setSerialNum(dvSn);
		ruleSend.setSessionId(0);
		ruleSend.setSyncIndexSymbol(0);
		
		Map<Integer, Object> sessionIdSegIdMap = new ConcurrentHashMap<Integer,Object>();
		ruleSend.setSessionIdSegIdMap(sessionIdSegIdMap);
		
		Map<Integer, Object> segIdSyncSymbolMap= new ConcurrentHashMap<Integer,Object>();
		ruleSend.setSegIdSyncSymbolMap(segIdSyncSymbolMap);
		
		targetRuleSendList.add(ruleSend);
		
	}
	
	public void delAreaSyncItemFromList(String dvSn)
	{
		List<RuleSend>  areaRuleSendList = SigletonBean.areaRuleSendList;
		String findDvSn;
		
		for(int i=0; i< areaRuleSendList.size(); i++)
		{
			findDvSn = areaRuleSendList.get(i).getSerialNum();
			if (findDvSn.equals(dvSn))
			{
				areaRuleSendList.remove(i);
				break;
			}
		}
		
	}
	
	public void delTargetSyncItemFromList(String dvSn)
	{
		List<RuleSend>  targetRuleSendList = SigletonBean.targetRuleSendList;
		String findDvSn;
		
		for(int i=0; i< targetRuleSendList.size(); i++)
		{
			findDvSn = targetRuleSendList.get(i).getSerialNum();
			if (findDvSn.equals(dvSn))
			{
				targetRuleSendList.remove(i);
				break;
			}
		}
		
	}
	public List<TargetAlarm>  gettableshuju(String table,String condition){
		List<TargetAlarm>  gettableshuju =(List<TargetAlarm>) earlWarningTaskMapper.gettableshuju(table,condition);
		return gettableshuju;
	}


}
