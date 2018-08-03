package com.iekun.ef.jms.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.service.AnalysisService;
import com.iekun.ef.service.SystemMonitor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.iekun.ef.jms.dao.SqlMapClientCallback;
//import com.iekun.ef.jms.dao.SqlMapExecutor;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvUeInfos;
import com.iekun.ef.jms.vo.receive.RcvUeInfo;
import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.model.UserDevice;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceSiteDetailInfo;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.SelfAreaCode;
import com.iekun.ef.push.RealTimeDataWebSocketHandler;
import com.iekun.ef.util.CommonConsts;
import com.iekun.ef.util.Parameter;
import com.iekun.ef.util.TimeUtils;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.dao.UeInfoShardingDao;

//"HandleSendUeInfo"
@Service()
@Transactional(rollbackFor=java.lang.Exception.class)
@Scope("prototype")
public class HandleRcvUeInfoService{

	private UeInfo ueInfo ;
	public UeInfoMapper ueInnerInfoMapper;
	public DeviceMapper deviceInnerMapper;
	public SqlSessionFactory sqlInnerSessionFactory;
	public UeInfoShardingDao ueInnerInfoShardingDao;
	private static Logger logger = LoggerFactory.getLogger(HandleRcvUeInfoService.class);
	private static List<TargetAlarm> insertTargetAlarmList = new ArrayList<TargetAlarm>();
	private static List<UeInfo>  insertUeInfoList = Collections.synchronizedList(new ArrayList<UeInfo>());
	public  boolean valueThreshold  = false;
	SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private MessageToDataBase messageToDb = new MessageToDataBase();
	
	
	//Queue<UeInfo> queue=SigletonBean.queue;
	//Queue<RcvUeInfo> blackQueue=SigletonBean.blackQueue;
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;

	String msg = "{'type': 'newData', 'data': [{ " +
            "'imsi': '46000000001', " +
            "'indication': 0, " +
            "'operatorText': '中国移动', " +
            "'cityName': '上海市', " +
            "'numberSection': '139544**', " +
            "'siteName': '闵行站点', " +
            "'deviceName': '元江路设备', " +
            "'captureTime': '" + dateformat.format( new Date()) + "' " +
            "}] }";
	
	private String  getUeInfoString(RcvUeInfo ueInfo, DeviceMapper deviceMapper)
	{
		String displayUeInfo = null;
		String proviceCode;
		String cityCode;
		int isAlarm = 0;
		String cityStr = null;
		
		proviceCode =  CommonConsts.getAlignCharText(ueInfo.getCode_province());
		cityCode =  CommonConsts.getAlignCharText(ueInfo.getCode_city());
		SelfAreaCode selfAreaCode = (SelfAreaCode)SigletonBean.selfAreaCodeMaps.get(proviceCode+cityCode);
		if (selfAreaCode == null)
		{
			logger.info("未知的城市编码  proviceCode :" + proviceCode + "cityCode :" + cityCode);
			cityStr = "未知城市";
		}
		else
		{
			cityStr = selfAreaCode.getCityName();
		}
		
		DeviceSiteDetailInfo DvSiteInfo = this.getDvSiteDetailInfo(ueInfo.getSerialNum(), deviceMapper);
		if((ueInfo.getBoolLib() == 1)||(ueInfo.getBoolLocaleInlib() == 1))
		{
			isAlarm = 1;
		}
		
		displayUeInfo = "{'type': 'newData', 'data': [{ " +
        "'imsi': '" + ueInfo.getUeImsi() + "'," +
        "'indication': '" + isAlarm + "'," +
        "'operatorText': '" + DvSiteInfo.getOperator() + "'," +
        "'cityName': '" + cityStr + "'," +
        "'siteName': '" + DvSiteInfo.getSiteName() + "'," +
        "'deviceName': '" + DvSiteInfo.getDeviceName() + "',"  +
        "'captureTime': '" + ueInfo.getCaptureTime() + "' " +
        "}] }";
		
		return displayUeInfo; 
	}
	    
    private DeviceSiteDetailInfo getDvSiteDetailInfo(String deviceSn, DeviceMapper deviceMapper)
    {
    	
	    String operatorText; 
    	DeviceSiteDetailInfo DvSiteInfo = new DeviceSiteDetailInfo();
    	
	    Device deviceDetail = (Device)SigletonBean.deviceSiteInfoMaps.get(deviceSn);
	    if (deviceDetail != null)
	    {
	    	DvSiteInfo.setDeviceName(deviceDetail.getName());
	    	DvSiteInfo.setDeviceSn(deviceSn);
	    	operatorText = CommonConsts.getOperatorTypeText(Integer.valueOf(deviceDetail.getOperator()));
	    	DvSiteInfo.setOperator(operatorText);
	    	if(deviceDetail.getSite() != null)
	    	{
	    		DvSiteInfo.setSiteCity(deviceDetail.getSite().getCity_name());
		    	DvSiteInfo.setSiteName(deviceDetail.getSite().getName());
	    	}
	    	else
	    	{
	    		logger.info("未知site名和site城市");
	    	}
	    	
	    }
       return DvSiteInfo;
    }
    
	@SuppressWarnings({ "unchecked", "static-access" })
    public void handleMessage(Message message, UeInfoMapper ueInfoMapper, TargetAlarmMapper targetAlarmMapper, DeviceMapper deviceMapper, SystemMonitor systemMonitor,SqlSessionFactory sqlSessionFactory,  LoggerService  loggerServ, UeInfoShardingDao ueInfoShardingDao) {
		String deviceSn 	= message.getSerialNum();
		DeviceStatus dvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSn);
		//dvStatus.getDeviceStatusCommon().setDeviceStatus("run");
		if((dvStatus.getDeviceStatusCommon().getDeviceStatus()).equals("offline"))
		{
			dvStatus.getDeviceStatusCommon().setDeviceStatus("run");
			loggerServ.insertLog("online", deviceSn, new Long(0));
		}
		dvStatus.getDeviceStatusCommon().setStatusUpdateTime(TimeUtils.timeFormatterStr.format(new Date()));
		RcvUeInfos sendUeInfo = JSONObject.toJavaObject(JSONObject.parseObject(message.getSubOject().toString()), RcvUeInfos.class);
		List<RcvUeInfo> ueInfoList = sendUeInfo.getUeInfoList();
		List<Long> userIdList = null;
		Queue<RcvUeInfo> seekedQueue = null;
		Map<String, Object> dvUserMaps = SigletonBean.deviceUserMaps;
		RcvUeInfo insertUeInfo = null;
		int ueCnt = 0;
		int targetUeCnt = 0;
		UeInfo internalUeInfo;
		TargetAlarm targetAlarm;

		for (RcvUeInfo ueInfo : ueInfoList) {

			systemMonitor.incrementUeInfoTotal();
			
			/*根据ueinfo信息中的设备序列号，查找到对应的归属用户 */
			try {
				userIdList = (ArrayList<Long>)dvUserMaps.get(ueInfo.getSerialNum());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (userIdList != null)
			{
				for(Long userId : userIdList)
				{
					//userId = userDevice.getUserId();
					Map<String, Object> mmap = SigletonBean.maps; 
				    /*根据userid查找map表，如果map表中找到已经有该用户的队列queue，则数据入队列，否则创建队列，再把数据插入队列*/
					
					seekedQueue = (Queue<RcvUeInfo>)mmap.get(String.valueOf(userId));
					
					if(null == seekedQueue)
					{
						/* 没找到对应的队列，则申请新的队列 ，并插入*/
						seekedQueue = new LinkedList<RcvUeInfo>();
						mmap.put(String.valueOf(userId), seekedQueue);
					}
										
					Session session = (Session)SigletonBean.userIdSessionMaps.get(userId.toString());
					if(session == null)
					{
						if(seekedQueue.size() != 0)
						{
							seekedQueue.clear();
							logger.info(" seeked queue is clear!");
						}
						
					}
					else
					{
						insertUeInfo = this.cpObj(ueInfo);
						if(insertUeInfo == null)
						{
							logger.info("insertUeInfo is null");
						}
						else
						{
							if(seekedQueue.size() == SigletonBean.maxCount)
							{
								seekedQueue.poll();
							}
							seekedQueue.offer(insertUeInfo);
						}
						
					}
					
				}
			}
			
			if((ueInfo.getBoolLib() == 1)||(ueInfo.getBoolLocaleInlib() == 1)){
				if (ueInfo.getBoolLib() == 1)
				{
					systemMonitor.incrementBlacklistAlarmTotal();
				}
				
				if (ueInfo.getBoolLocaleInlib() == 1)
				{
					systemMonitor.incrementHomeOwnershipAlarmTotal();
				} 
			
				RcvUeInfo targetUeInfo = new RcvUeInfo();
				
				try {
					BeanUtils.copyProperties(targetUeInfo, ueInfo);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				} 
			
				targetAlarm = this.recvTargetInfoFormat2Internal(ueInfo);
				this.insertTargetAlarmList.add(targetAlarm);
			}
			
			internalUeInfo  = this.recvUeInfoFormat2Internal(ueInfo);
			this.insertUeInfoList.add(internalUeInfo);
		}
		/* 大于100条写入数据库*/
		if (this.insertTargetAlarmList.size() > 100)
		{
			try {
				messageToDb.batchInsertTargetAlarm(this.insertTargetAlarmList, sqlSessionFactory);
				//logger.info("insert target info");
				this.insertTargetAlarmList.clear();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		/* 大于1000条写入数据库*/
		if (this.insertUeInfoList.size() > 1000)
		{
		try {
				this.classifyUeInfos(this.insertUeInfoList, ueInfoMapper, deviceMapper,  sqlSessionFactory, ueInfoShardingDao);
				this.insertUeInfoList.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
		ueInfoList = null;
		sendUeInfo = null;
		
	}
	
	/* 没有数据上报的情况下，检查所有对应日期的链表里是否有残留数据，如有则刷新写入数据库 */
	@SuppressWarnings("static-access")
	public void remainedUeinfoIntoDbWithoutReport(UeInfoMapper ueInfoMapper, DeviceMapper deviceMapper, SqlSessionFactory sqlSessionFactory, UeInfoShardingDao ueInfoShardingDao)
	{
		List<UeInfo> ueInfoList = null;
		String captureDay = null;
		int ueinfoSize = this.insertUeInfoList.size();
		if ((ueinfoSize <= 1000)&&(ueinfoSize > 0))
		{
			this.classifyUeInfos(this.insertUeInfoList, ueInfoMapper, deviceMapper,  sqlSessionFactory, ueInfoShardingDao);
			this.insertUeInfoList.clear();
			
			Map<String, Object> dateDataListMap = SigletonBean.dateDataListMaps;
			//Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
	        
	        for(Map.Entry<String, Object> entry:dateDataListMap.entrySet())
	        {
	        	ueInfoList = (List<UeInfo>)(entry.getValue());
	        	captureDay = (String)(entry.getKey());
	        	if(ueInfoList.size() > 0)
	        	{
	        		messageToDb.insertSplitSendUeInfo(ueInfoList, ueInfoMapper, deviceMapper, ueInfoShardingDao, captureDay);
					ueInfoList.clear();
	        	}
	        	
	        }
			
		}
		
	}
	
	/* 有数据上报的情况下，检查对应日期的链表里是否有残留数据，如果有且时间超过30秒没有新的数据报上来，则刷新写入数据库 */
	public void checkRemainedDataWithReport(UeInfoMapper ueInfoMapper, DeviceMapper deviceMapper, SqlSessionFactory sqlSessionFactory, UeInfoShardingDao ueInfoShardingDao)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		List<UeInfo> ueInfoList = null;
		Map<String, Object> listNameListUpdateMap = SigletonBean.listNameListUpdateMaps;
		Map<String, Object> dateDataListMap = SigletonBean.dateDataListMaps;
		String captureDay = null;
		String updateTime = null;
		
		Date nowDate =  new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(nowDate);//设置参数时间
		c.add(Calendar.SECOND,-30);
		nowDate = c.getTime(); 
		String noDateStr = df.format(nowDate);
		
		for(Map.Entry<String, Object> entry:listNameListUpdateMap.entrySet())
        {
			captureDay = (String)(entry.getKey());
			updateTime = (String)(entry.getValue());
			if ((noDateStr.compareTo(updateTime) >= 0))
			{
				ueInfoList = (List<UeInfo>) dateDataListMap.get(captureDay);
				if(ueInfoList.size() > 0)
	        	{
	        		messageToDb.insertSplitSendUeInfo(ueInfoList, ueInfoMapper, deviceMapper, ueInfoShardingDao, captureDay);
					ueInfoList.clear();
	        	}
			}
        	
        }
	}
	
	
	public void checkRemainedTargetUeInfo( SqlSessionFactory sqlSessionFactory)
	{
		int targetAlarmSize = this.insertTargetAlarmList.size();
		if ((targetAlarmSize <= 100)&&(targetAlarmSize > 0))
		{
			try {
				messageToDb.batchInsertTargetAlarm(this.insertTargetAlarmList, sqlSessionFactory);
				this.insertTargetAlarmList.clear();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public RcvUeInfo cpObj( RcvUeInfo srcUeInfo)
	{
		RcvUeInfo destUeInfo = new RcvUeInfo();
		destUeInfo.setBoolLib(srcUeInfo.getBoolLib());
		destUeInfo.setBoolLocaleInlib(srcUeInfo.getBoolLocaleInlib());
		destUeInfo.setBoolsys(srcUeInfo.getBoolsys());
		destUeInfo.setCaptureTime(srcUeInfo.getCaptureTime());
		destUeInfo.setCode_city(srcUeInfo.getCode_city());
		destUeInfo.setCode_operator(srcUeInfo.getCode_operator());
		destUeInfo.setCode_province(srcUeInfo.getCode_province());
		destUeInfo.setRaRta(srcUeInfo.getRaRta());
		destUeInfo.setSerialNum(srcUeInfo.getSerialNum());
		destUeInfo.setTmHour(srcUeInfo.getTmHour());
		destUeInfo.setTmMday(srcUeInfo.getTmMday());
		destUeInfo.setTmMin(srcUeInfo.getTmMin());
		destUeInfo.setTmMon(srcUeInfo.getTmMon());
		destUeInfo.setTmSec(srcUeInfo.getTmSec());
		destUeInfo.setTmYear(srcUeInfo.getTmYear());
		destUeInfo.setUeImei(srcUeInfo.getUeImei());
		destUeInfo.setUeImsi(srcUeInfo.getUeImsi());
		destUeInfo.setUeSTmsi(srcUeInfo.getUeSTmsi());
		destUeInfo.setUeTaType(srcUeInfo.getUeTaType());
		return destUeInfo;
	}
	
	
	public UeInfo recvUeInfoFormat2Internal(RcvUeInfo rcvUeInfo)
	{
		SimpleDateFormat dateformat =new SimpleDateFormat("yyyyMMddHHmmss");
		byte indication = 0;
    	String proviceCode;
		String cityCode;
		UeInfo ueInfo ;
		
		if(rcvUeInfo == null)
		{
			logger.info("rcvUeInfo is null");
			return null;
		}
		proviceCode = CommonConsts.getAlignCharText(rcvUeInfo.getCode_province());
		cityCode =  CommonConsts.getAlignCharText(rcvUeInfo.getCode_city());
		
		ueInfo  = new UeInfo(rcvUeInfo.getCaptureTime(), rcvUeInfo.getSerialNum(), rcvUeInfo.getUeImei(), rcvUeInfo.getUeImsi(),
				String.valueOf(rcvUeInfo.getUeTaType()), rcvUeInfo.getUeSTmsi(), Long.parseLong(String.valueOf(rcvUeInfo.getRaRta())));
		
		SelfAreaCode selfAreaCode = (SelfAreaCode)SigletonBean.selfAreaCodeMaps.get(proviceCode+cityCode);
		if (selfAreaCode != null)
		{
			ueInfo.setCityName(selfAreaCode.getProvinceName()+selfAreaCode.getCityName());
			ueInfo.setCityCode(proviceCode+cityCode);
		}
		
		Device device =  (Device)(SigletonBean.deviceSiteInfoMaps.get(rcvUeInfo.getSerialNum()));
		//Device device = this.getSiteDvInfo(rcvUeInfo.getSerialNum(), deviceMapper);
		if (device != null)
		{
			ueInfo.setBand(device.getBand());
    		ueInfo.setOperator(CommonConsts.getOperatorTypeText(Integer.valueOf(device.getOperator())));
    		ueInfo.setSiteSn(device.getSite().getSn());
    		ueInfo.setSiteName(device.getSite().getName());
		}
		if (rcvUeInfo.getBoolLib() == 1)
		{
			indication = 1;
		}
		else
		{
			if(rcvUeInfo.getBoolLocaleInlib() == 1)
			{
				indication = 2;
			}
		}
		
		ueInfo.setRealtime(new Integer(rcvUeInfo.getBoolsys()));
		ueInfo.setIndication(new Byte(indication));
		ueInfo.setCreateTime(TimeUtils.timeFormatterStr.format(new Date()));
		ueInfo.setMac("abcd1234efdeab");
		ueInfo.setRarta(Long.parseLong(String.valueOf(rcvUeInfo.getRaRta())));
   		
		
   		return ueInfo;
	}
	
	public TargetAlarm recvTargetInfoFormat2Internal(RcvUeInfo recvTargetAlarm)
	{
		SimpleDateFormat dateformat =new SimpleDateFormat("yyyyMMddHHmmss");
		byte isRead = 0;
		
		String proviceCode;
		String cityCode;
		TargetAlarm targetAlarm;
		
		if(recvTargetAlarm == null)
		{
			logger.info("RecvTargetAlarm is null");
			return null;
		}
		
		byte indication = 0;
		
		targetAlarm  = new TargetAlarm(recvTargetAlarm.getCaptureTime(), recvTargetAlarm.getSerialNum(), recvTargetAlarm.getUeImei(), recvTargetAlarm.getUeImsi(),
				String.valueOf(recvTargetAlarm.getUeTaType()), recvTargetAlarm.getUeSTmsi(), Long.parseLong(String.valueOf(recvTargetAlarm.getRaRta())));
		
		proviceCode = CommonConsts.getAlignCharText(recvTargetAlarm.getCode_province());
		cityCode =  CommonConsts.getAlignCharText(recvTargetAlarm.getCode_city());
		SelfAreaCode selfAreaCode = (SelfAreaCode)SigletonBean.selfAreaCodeMaps.get(proviceCode+cityCode);
		if (selfAreaCode != null)
		{
			targetAlarm.setCityName(selfAreaCode.getProvinceName() + selfAreaCode.getCityName());
			targetAlarm.setCityCode(proviceCode+cityCode);
		}
		
		Device device =  (Device)(SigletonBean.deviceSiteInfoMaps.get(recvTargetAlarm.getSerialNum()));
		//Device device = this.getSiteDvInfo(RecvTargetAlarm.getSerialNum(), deviceMapper);
		if (device != null)
		{
			targetAlarm.setBand(device.getBand());
			targetAlarm.setOperator(CommonConsts.getOperatorTypeText(Integer.valueOf(device.getOperator())));
			targetAlarm.setSiteSn(device.getSite().getSn());
			targetAlarm.setSiteName(device.getSite().getName());
		}
		
		/*if (recvTargetAlarm.getBoolLib() == 1)
		{
			indication = 1;
		}
		else
		{
			if(recvTargetAlarm.getBoolLocaleInlib() == 1)
			{
				indication = 2;
			}
		}*/
		
		if ((recvTargetAlarm.getBoolLib() == 1) 
				&&(recvTargetAlarm.getBoolLocaleInlib() == 1))
		{
			 indication = 3;
		}
		else
		{
			if (recvTargetAlarm.getBoolLib() == 1) 
			{
				indication = 1;
			}
			{
				if(recvTargetAlarm.getBoolLocaleInlib() == 1)
				{
					indication = 2;
				}
			}
		}
		
		targetAlarm.setIndication(new Byte(indication));
		targetAlarm.setRealtime(new Integer(recvTargetAlarm.getBoolsys()));
		targetAlarm.setIsRead(isRead);
		targetAlarm.setCreateTime(dateformat.format( new Date()));
	    targetAlarm.setMac("abcd1234efdeab");
		targetAlarm.setRarta(Long.parseLong(String.valueOf(recvTargetAlarm.getRaRta())));
		
		//targetAlarmMapper.insert(targetAlarm);
		//SigletonBean.insertTargetAlarmList.add(targetAlarm);
		return targetAlarm;
	}
	
	private void classifyUeInfos_old(List<UeInfo> members,  UeInfoMapper ueInfoMapper, DeviceMapper deviceMapper, SqlSessionFactory sqlSessionFactory, UeInfoShardingDao ueInfoShardingDao)
	{
		UeInfo ueInfo = null;
		String currTempDate   = TimeUtils.daySimpleFormatter.format(new Date());
		String currDate   = TimeUtils.dayFormatter.format(new Date());
		String preDate    = TimeUtils.getSpecifiedDayBefore(currTempDate);
		String afterDate  = TimeUtils.getSpecifiedDayAfter(currTempDate);
		String captureDay = null; 
		
		for(int i=0; i < members.size(); i++)
		{
			ueInfo = members.get(i);
			
			captureDay = ueInfo.getCaptureTime().substring(0, 10);
			
			//TimeUtils.getDayString(ueInfo.getTmYear(), rcvUeInfo.getTmMon(), rcvUeInfo.getTmMday());
			/*if (captureDay.equals(preDate))
			{
				SigletonBean.preDayList.add(ueInfo);
			}
			else if (captureDay.equals(currDate))
			{*/
			SigletonBean.currDayList.add(ueInfo);
			/*}
			else if (captureDay.equals(afterDate))
			{
				SigletonBean.afterDayList.add(ueInfo);
			}
			else
			{
				logger.info("invalid capture date " + captureDay);
				logger.info(" currDate  " + currDate);
			}*/
			
		}
		
		/*if (SigletonBean.preDayList.size() != 0)
		{
			messageToDb.insertSplitSendUeInfo(SigletonBean.preDayList, ueInfoMapper, deviceMapper, ueInfoShardingDao, preDate);
			SigletonBean.preDayList.clear();
		}
		*/
		if (SigletonBean.currDayList.size() != 0)
		{
			messageToDb.insertSplitSendUeInfo(SigletonBean.currDayList, ueInfoMapper, deviceMapper, ueInfoShardingDao, captureDay);
			SigletonBean.currDayList.clear();
		}
		
		/*if (SigletonBean.afterDayList.size() != 0)
		{
			messageToDb.insertSplitSendUeInfo(SigletonBean.afterDayList, ueInfoMapper, deviceMapper, ueInfoShardingDao, afterDate);
			SigletonBean.afterDayList.clear();
		}
		*/
		return;
	}
	
	private void classifyUeInfos(List<UeInfo> members,  UeInfoMapper ueInfoMapper, DeviceMapper deviceMapper, SqlSessionFactory sqlSessionFactory, UeInfoShardingDao ueInfoShardingDao)
	{
		String captureDay = null;
		List<UeInfo> ueInfoList = null;
		SimpleDateFormat dateformat =new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> dateDataListMap = SigletonBean.dateDataListMaps;
		Map<String, Object> listNameListUpdateMap = SigletonBean.listNameListUpdateMaps;
		for(int i=0; i < members.size(); i++)
		{
			ueInfo = members.get(i);
			//dateformat.format( new Date());
			captureDay = ueInfo.getCaptureTime().substring(0, 10);
			ueInfoList = (List<UeInfo>)dateDataListMap.get(captureDay);
			if(ueInfoList == null)
			{
				ueInfoList = Collections.synchronizedList(new ArrayList<UeInfo>());
				ueInfoList.add(ueInfo);
				dateDataListMap.put(captureDay, ueInfoList);
			}
			else
			{
				ueInfoList.add(ueInfo);
			
			}
		
			if (listNameListUpdateMap.get(captureDay) == null)
			{
				listNameListUpdateMap.put(captureDay, dateformat.format( new Date()));
			}
			else
			{
				listNameListUpdateMap.replace(captureDay, dateformat.format( new Date()));
			}
			
			
			if(ueInfoList.size() > 100)
			{
				messageToDb.insertSplitSendUeInfo(ueInfoList, ueInfoMapper, deviceMapper, ueInfoShardingDao, captureDay);
				ueInfoList.clear();
			}
			
		}
		
		return;
	}
	
	
	
    
}
    
 