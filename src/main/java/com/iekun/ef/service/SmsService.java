package com.iekun.ef.service;

import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.AreaRuleMapper;
import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.dao.OutboxMapper;
import com.iekun.ef.dao.SiteMapper;
import com.iekun.ef.dao.SysParamMapper;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.TargetRuleMapper;
import com.iekun.ef.model.AreaRule;
import com.iekun.ef.model.ContactReceiver;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceSiteDetailInfo;
import com.iekun.ef.model.Outbox;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.SysParam;
import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.model.TargetRule;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.util.SmsUtils;
import com.iekun.ef.util.TimeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Service
public class SmsService{
	
	/*@Autowired
	@Qualifier("deviceDao")
	private DeviceDao deviceDao;
	
	@Autowired
	@Qualifier("iTargetMgrServiceDao")
	private ITargetMgrServiceDao iTargetMgrServiceDao;
	
	
	@Autowired
	@Qualifier("iTargetUeInfoDao")
	private ITargetUeInfoDao iTargetUeInfoDao;
	
	@Autowired
	@Qualifier("iSMSTempletService")
	private SMSTempletService smsTempletService;
	
	@Autowired
	@Qualifier("iSMSHistoryDao")
	private ISMSHistoryDao iSMSHistoryDao;
	
	
	
	@Autowired
	private AlarmMobileDao alarmMobileDao;*/
	
	@Autowired
	@Qualifier("smsUtils")
	private SmsUtils smsUtils;
	
	private TargetRuleMapper targetRuleMapper;
	
	private DeviceMapper deviceMapper;
	
	private SiteMapper siteMapper;
	
	private TargetAlarmMapper targetAlarmMapper;

	@Autowired
	private SysParamMapper sysParamMapper;

	@Autowired
	private OutboxMapper outboxMapper;
	
	private AreaRuleMapper areaRuleMapper;

	private DeviceService dvService;

	private static Logger logger = LoggerFactory.getLogger(SmsService.class);
	
	public void setMapper(DeviceService dvServ,TargetRuleMapper targetRuleMapper, AreaRuleMapper areaRuleMapper, DeviceMapper deviceMapper, TargetAlarmMapper targetAlarmMapper, SysParamMapper sysParamMapper, OutboxMapper outboxMapper)
	{
		this.deviceMapper = deviceMapper;
		this.targetAlarmMapper = targetAlarmMapper;
		this.sysParamMapper = sysParamMapper;
		this.outboxMapper = outboxMapper;
		this.targetRuleMapper = targetRuleMapper;
		this.areaRuleMapper = areaRuleMapper;
		this.dvService = dvServ;
	}
	
	private static final int sendCount = 10;

	@Value(" ${decimationCreateSms.cycleTimeLength}")   //?
    private int cycleTimeLength;
	
	@Value(" ${decimationCreateSms.DeduplicationTime}")  
    private int DeduplicationTime;
	
	@Value(" ${decimationCreateSms.LuTrigSmsFlag}")  
    private int LuTrigSmsFlag;
	
	/*private SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    private String date=sdf.format(new Date());*/
	
	public void  decimationCreateSms() throws Exception{

		String imsi;
		String sms_time;
		String latest_device_sn;
		String current_device_sn;
		String  targetRuleName;
		String sourceCityCode;
		long targetRuleId;
		HashMap<String, String> params;
		HashMap<String, String> areaParams;

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = new GregorianCalendar();
		Date date = new Date();
		System.out.println("系统当前时间："+df.format(date));
		c.setTime(date);//设置参数时间
		c.add(Calendar.SECOND,-cycleTimeLength);//把日期往后增加jobExcuteCycleTime 秒.整数往后推,负数往前移动
		date=c.getTime(); //这个时间就是日期往后推了的结果
		String fetchStartTime = df.format(date);
		System.out.println("系统前jobExcuteCycleTime秒时间："+fetchStartTime);

		Map<String,Object> ruleparams=new HashMap<String, Object>();
		ruleparams.put("creatorId", null);

		List<TargetRule>  targetRuleList= (List<TargetRule>) targetRuleMapper.getTargetRuleList(ruleparams);

		for (TargetRule targetRule: targetRuleList) {

			imsi = targetRule.getImsi();
			sms_time = targetRule.getSmsTime();
			latest_device_sn = targetRule.getLatestDeviceSn();
			targetRuleId = targetRule.getId().longValue();
			targetRuleName = targetRule.getName();

			params = new HashMap<String, String>();
			params.put("imsi", imsi);
			params.put("startDate", fetchStartTime);

			List<TargetAlarm>  targetAlarmList = targetAlarmMapper.getTargetAlarms(params);

			if(null == targetAlarmList || targetAlarmList.size() ==0 )
			{
				continue;
			}
			else
			{
				for (TargetAlarm targetAlarm : targetAlarmList) {

					current_device_sn = targetAlarm.getDeviceSn();
//					if (dvService.deviceIsBelongToUser(targetRule.getCreatorId(), current_device_sn) == true)
//					{
//						this.determindToCreateSms(targetAlarm, imsi, latest_device_sn, current_device_sn, sms_time, targetRuleId, targetRuleName);
//					}
				}
			}

		}

		List<AreaRule>  AreaRuleList= (List<AreaRule>) areaRuleMapper.getAreaRuleList(ruleparams);
		for (AreaRule areaRule: AreaRuleList)
		{
			sourceCityCode = areaRule.getSourceCityCode();
			areaParams = new HashMap<String, String>();
			areaParams.put("cityCode", sourceCityCode);
			areaParams.put("startDate", fetchStartTime);

			List<TargetAlarm>  targetAlarmList = targetAlarmMapper.getAreaTargetAlarms(areaParams);
			if(null == targetAlarmList || targetAlarmList.size() ==0 )
			{
				continue;
			}
			else
			{
				for (TargetAlarm targetAlarm : targetAlarmList)
				{

					current_device_sn = targetAlarm.getDeviceSn();
					if (dvService.deviceIsBelongToUser(areaRule.getCreatorId(), current_device_sn) == true)
					{

						this.decimationCreate(targetAlarm, areaRule.getId(), areaRule.getName(), 2); /* 1 为归属地类型   */

					}//this.determindToCreateSms(targetAlarm, imsi, latest_device_sn, current_device_sn, sms_time, targetRuleId, targetRuleName);
				}
			}

		}


	}
	
	private void determindToCreateSms(TargetAlarm targetUeinfo, String imsi, String latestDeviceSn, String currentDeviceSn, String smsTime, long targetId,  String targetName) throws Exception{
		
		long timeSpan;
		String currentTime = this.currentTime();
		
		if (smsTime!= null)
		{
			timeSpan = (this.fromDateStringToLong(currentTime) - this.fromDateStringToLong(smsTime))/1000;//秒数
			
		}
		else
		{
			timeSpan = DeduplicationTime + 1;
		}
		
		if (LuTrigSmsFlag ==1)
		{
				if (currentDeviceSn.equals(latestDeviceSn)) //(currentDeviceSn != latestDeviceSn)
				{
							
					if (timeSpan > DeduplicationTime)
					{
						this.createSmsAndUpdateTarget(targetUeinfo, imsi, currentTime, currentDeviceSn, targetId, targetName);
					}
                    else
                    {
                    	this.UpdateTargetWithDeviceSnAndsmsTime( imsi, smsTime, targetId, currentDeviceSn);
                    }

				}
	          else 
				{
	        	  	this.createSmsAndUpdateTarget( targetUeinfo, imsi, currentTime, currentDeviceSn, targetId, targetName);
				}
		   }
	   else
		   {	
			
				   if (timeSpan > DeduplicationTime)
				   {
					   this.createSmsAndUpdateTarget( targetUeinfo, imsi, currentTime, currentDeviceSn, targetId, targetName);
				    }
					else
					{
						/* 没发短信，时间保持不变 */
						this.UpdateTargetWithDeviceSnAndsmsTime( imsi, smsTime, targetId, currentDeviceSn);
					}
			} 
		
	}
	
	
	 private long fromDateStringToLong(String inVal) 
	 { 
	     //此方法计算时间毫秒
	     Date date = null;   //定义时间类型       
	     SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	     try {
	           date = inputFormat.parse(inVal); //将字符型转换成日期型
	         } catch (Exception e) {
	             e.printStackTrace();
	        }
	      return date.getTime();   //返回毫秒数
	  }
	 
	 
	 private  String currentTime() 
	 {  
	   //此方法用于获得当前系统时间（格式类型2007-11-6 15:10:58）
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//System.out.println("系统当前时间      ："+df.format(date));
			
	   // date = new Date();  //实例化日期类型
	   String today = df.format(date);//date.toLocaleString(); //获取当前时间
	   System.out.println("获得当前系统时间 "+today);  //显示
	   return today;  //返回当前时间
	  }
	
	
	private void createSmsAndUpdateTarget( TargetAlarm targetUeinfo, String imsi, String currentTime, String latestDeviceSn, long targetId,  String targetName)throws Exception
	{
		
		this.decimationCreate(targetUeinfo, targetId, targetName, 1); /* 1 为黑名单类型   */
		
		TargetRule targetRule = new TargetRule(targetId, currentTime, latestDeviceSn);
		targetRuleMapper.updateByPrimaryKeySelective(targetRule);
	}
		
	private void UpdateTargetWithDeviceSnAndsmsTime( String imsi, String currentTime, long targetId, String latestDeviceSn)
	{		
		TargetRule targetRule = new TargetRule(targetId, currentTime, latestDeviceSn);
		targetRuleMapper.updateByPrimaryKeySelective(targetRule);
	}
	
	/**
	 * 发送短信
	 * @param targetUeInfo
	 * @throws Exception
	 */

	private void decimationCreate(TargetAlarm targetAlarm, long targetId,  String targetName, int TargetType)throws Exception{
		
			//查找目标信息
			HashMap<String, Object> params;
		    Byte smsType = 0;
		    Byte eventType = 0;
		    Byte stateType = 0;
		    Byte deleteFlag = 0;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		    String date=sdf.format(new Date());
		    
			List<ContactReceiver>  list = null;
			switch (TargetType )
			{
				case 1:
					TargetRule targetRule = targetRuleMapper.selectByPrimaryKey(targetId);
		
					if (targetRule.getReceiver().length() != 0)
			    	{
						list = JSONObject.parseArray(targetRule.getReceiver(), ContactReceiver.class);
			    	}
					
					if (list.isEmpty())
					{
						return;
					}
			
					break;
				case 2:
					AreaRule areaRule = areaRuleMapper.selectByPrimaryKey(targetId);
					
					if (areaRule.getReceiver().length() != 0)
			    	{
						list = JSONObject.parseArray(areaRule.getReceiver(), ContactReceiver.class);
			    	}
					
					if (list.isEmpty())
					{
						return;
					}
					break;
				
				default:
					return;
			}
			
			
			SysParam sysParam  = sysParamMapper.getSmsTemplate("smsTemplate");
			String smsTemplate = sysParam.getValue();
			params = new HashMap<String, Object>();
			params.put("devicesn", targetAlarm.getDeviceSn());
			List<Device>  deviceList =(List<Device>) deviceMapper.selectDeviceDetails(params);
			if (deviceList.isEmpty())
			{
				return;
			}
			
			if (deviceList.get(0).getSite()== null)
			{
				return;
			}
			
			if( smsTemplate != null && !smsTemplate.equals("")  ) {
				
				for( ContactReceiver contactReceiver: list ){	
					try {
						String msg = this.renderSMSTemplet( 
								contactReceiver.getName(),
								targetAlarm.getImsi(),
								targetAlarm.getCaptureTime(),
								deviceList.get(0).getSite().getAddress(),
								targetName,
								smsTemplate							
								);
						 
						 Outbox outbox = new Outbox(contactReceiver.getName(), contactReceiver.getPhone(), msg);
						 
						 outbox.setNotifyType(smsType);
						 outbox.setEventType(eventType);
						 outbox.setStatus(stateType);
						 outbox.setCreateTime(date);
						 outbox.setDeleteFlag(deleteFlag);
						 
						 outboxMapper.insert(outbox);
						 
						//smsUtils.httpSendMessage( msg, contactReceiver.getPhone());
												
						/*//历史纪录
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SMSHistoryVo smsHistory = new SMSHistoryVo();
						smsHistory.setTargetName(targetName);
						smsHistory.setTargetIMSI(targetAlarm.getUeImsi());
						smsHistory.setTargetLocation(device.getAddress());
						smsHistory.setTargetCaptureTime(targetAlarm.getCaptureTime());
						smsHistory.setTargetId(targetId);
						smsHistory.setAlarmName(alarmMobile.getAlarmName());
						smsHistory.setAlarmNumber(alarmMobile.getAlarmNumber());
						smsHistory.setAlarmId(alarmMobile.getAlarmId());
						smsHistory.setAlarmContent(msg);
						smsHistory.setAlarmTime(sdf.format(new Date()));
						smsHistory.setAlarmStatus(0);
						iSMSHistoryDao.addSMSHistory(smsHistory);*/
						
					} catch ( Exception e )  {
						e.printStackTrace();
					}
				}
			}
			
	
	}
		
	
	private String renderSMSTemplet( String user, String imsi, String time, String address, String target, String smsTemplet ) {
		
		String msg="";
		msg=smsTemplet.replace("{%user%}", user );
		if(msg.contains("{%imsi%}") == true)
		{
			msg=msg.replace("{%imsi%}", imsi );
		}
		
		if(msg.contains("{%time%}") == true)
		{
			msg=msg.replace("{%time%}", time );
		}
		
		if(msg.contains("{%location%}") == true)
		{
			msg=msg.replace("{%location%}", address );
		}
		
		if(msg.contains("{%target%}") == true)
		{
			msg=msg.replace("{%target%}", target );
		}
		
		return msg;
	}
	
	/* 发送任务，从数据库中取出记录发送 */
	public void  decimationSendSms() throws Exception
	{
		/**
		 * 1、 从数据库表t_outbox中取出所有未发的记录
		 * 2、循环发送出去
		 * 3、更新数据库发送时间字段和发送状态字段
		 * */
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	    String date=sdf.format(new Date());
	    
	    String phone = null;
	    String content = null;
	    long id;
		HashMap<String, Object> params;
		params = new HashMap<String, Object>();
		params.put("status", 0);
		params.put("notifyType", 0);

		/* 一次只取一条未发的短信来发送，避免发送太快导致smsserver处理不过来  */
		List<Outbox>  outboxList =(List<Outbox>) outboxMapper.selectOutboxList(params);
		if (outboxList.isEmpty())
		{
			return;
		}
		
		for(Outbox outbox: outboxList)
		{
			phone 	= outbox.getNotifyPhone();
			content	= outbox.getContent();
			id = outbox.getId().longValue();
	    	//boolean isSuccess =smsUtils.httpSendMessage( content, phone);
	    	boolean isSuccess =smsUtils.httpSendMessageClient( content, phone);
	    	
			
			params.put("id", id);
			if (isSuccess == true)
			{
				params.replace("status", 1);
			}
			else
			{
				params.replace("status", 2);
			}
			
		    params.put("lastSendTime", date);
		    
		    try {
				outboxMapper.updateById(params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
		
	}
	
	 public  JSONObject getSmsTemplate()
	 {
		JSONObject jsonObject = new JSONObject();
		SysParam sysParam  = sysParamMapper.getSmsTemplate("smsTemplate");
		String smsTemplate = sysParam.getValue();
		//String smsTemplate = "尊敬的{%user%}, 在{%time%}时间目标{%target%}出现于{%location%}位置";
		jsonObject.put("status", true);
		jsonObject.put("message", "成功");
		jsonObject.put("data", smsTemplate );
		return jsonObject;
	 }

	 @Transactional
	 public  JSONObject setSmsTemplate(String smsTemplate)
	 {
		@SuppressWarnings("unused")
		boolean successOrNot = false;
		JSONObject jsonObject = new JSONObject();
		SysParam sysParam = new SysParam();
		sysParam.setKey("smsTemplate");
		sysParam.setValue(smsTemplate);
		try {
			sysParamMapper.updateSysPara(sysParam);
			successOrNot = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			successOrNot =false;
			e.printStackTrace();
		}
		
	    if (successOrNot = true)
	    {
	    	jsonObject.put("status", true);
			jsonObject.put("message", "成功");
		}
	    else
	    {
	    	jsonObject.put("status", false);
			jsonObject.put("message", "失败");
	    }
		
		return jsonObject;
		 
	 }

	 public  JSONObject queryHistorySms(Integer draw, Integer length, Integer start, 
			 	String startDate, String endDate,String notifyName,String notifyPhone)
	 {
			long totalCnt = 0;
			boolean status = false;
			
			HashMap<String, Object> params=new HashMap<String, Object>();
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
	        	startDate = startDate.replace("/", "");
	        	startDate = startDate.replace(" ", "");
	        	startDate = startDate.replace(":", "");

	        	endDate = endDate.replace("/", "");
	        	endDate = endDate.replace(" ", "");
	        	endDate = endDate.replace(":", "");
	        	params.put("startDate", startDate);
	            params.put("endDate", endDate);
	        }
	        if (notifyName == null || notifyName.isEmpty())
	        {
	        	params.put("notifyName", null);
	        }
	        else
	        {
	        	params.put("notifyName", notifyName);
	        }

	        if (notifyPhone == null || notifyPhone.isEmpty())
	        {
				params.put("notifyPhone", null);
	        }
	        else
	        {
	        	 params.put("notifyPhone", notifyPhone);
	        }
	      
	        JSONObject jo=new JSONObject();
	        JSONArray ja = new JSONArray();
	        
	        if( ( startDate != null) && ( endDate != null ) &&
	                (!startDate.isEmpty()) && (!endDate.isEmpty())   )
	        {
	        	List<Outbox>  outboxList =(List<Outbox>) outboxMapper.querySmsOutboxList(params);

			    for (Outbox outboxInfo : outboxList) {
			    	JSONObject outboxInfoJo = new JSONObject();
			    	outboxInfoJo.put("id", outboxInfo.getId());
			    	outboxInfoJo.put("notifyName", outboxInfo.getNotifyName());
			    	outboxInfoJo.put("notifyPhone", outboxInfo.getNotifyPhone());
			    	outboxInfoJo.put("eventType", outboxInfo.getEventType() );
			    	outboxInfoJo.put("content", outboxInfo.getContent());
			    	outboxInfoJo.put("lastSendTime", TimeUtils.getFormatTimeStr(outboxInfo.getLastSendTime()));
			        ja.add(outboxInfoJo);
			        status = true;
				}
			    if(status == true)
		        {
			    	totalCnt = this.getSmsTotalCount(params);
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
	        	jo.put("draw", draw);
	        	jo.put("recordsTotal", 0);
	        	jo.put("recordsFiltered", 0);
	        	jo.put("data", ja);

	        }
		   
		    return jo;
	
	 }
	 
	 public long getSmsTotalCount(Map<String, Object> params) 
	 {
		
		long totalCnt = 0;
		      
        try {
			totalCnt = (long)outboxMapper.getSmsOutboxCnt(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return totalCnt;
	}
	 
	public void createDvAlarmSms(String userName, String phoneNum, String dvName)
	{
		 String Msg = null;
		 byte smsType = 0;
		 byte eventType = 2;
		 byte stateType = 0;
		 byte deleteFlag = 0;
		 Date date = new Date();
		 
		 Msg= "尊贵的用户: " + userName + "你管理的设备"+ dvName + "于" + TimeUtils.timeFormatter.format(date) +"出现告警";
		 Outbox outbox = new Outbox(userName, phoneNum, Msg);
		 outbox.setNotifyType(smsType);
		 outbox.setEventType(eventType);
		 outbox.setStatus(stateType);
		 outbox.setCreateTime(TimeUtils.timeFormatterStr.format(date));
		 outbox.setDeleteFlag(deleteFlag);
		 
		 outboxMapper.insert(outbox);
	}
	
}
