package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/*import java.sql.SQLException;*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iekun.ef.model.Device;
import com.iekun.ef.model.SelfAreaCode;
/*import com.iekun.ef.model.DeviceSiteDetailInfo;*/
/*import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;*/
import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.DeviceAlarmMapper;
import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.dao.UeInfoShardingDao;
import com.iekun.ef.jms.vo.receive.RcvAlarm;
import com.iekun.ef.jms.vo.receive.RcvDevVersion;
import com.iekun.ef.jms.vo.receive.RcvHeartBeat;
import com.iekun.ef.jms.vo.receive.RcvReqCnf;
import com.iekun.ef.jms.vo.receive.RcvUeInfo;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.scheduling.AnalysisScheduledTask;
import com.iekun.ef.util.CommonConsts;


@Service
public class MessageToDataBase {


	public static Logger logger = LoggerFactory.getLogger(MessageToDataBase.class);
	//@Autowired
	private UeInfo ueInfo ;
	
	private TargetAlarm targetAlarm;
	
	SimpleDateFormat dateformat =new SimpleDateFormat("yyyyMMddHHmmss");
	
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	public SqlSessionTemplate getSqlSessionTemplate() 
	{
	    return sqlSessionTemplate;
	}
	
	
	
	//@Override
    public boolean insertSplitSendUeInfo(List<UeInfo> members, UeInfoMapper ueInfoMapper, DeviceMapper deviceMapper, UeInfoShardingDao ueInfoShardingDao, String day)
     {
    	try {
				ueInfoShardingDao.batchInsertUeInfo(members, day);
				SigletonBean.insertUeInfoList.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return true;
    }
    
    public boolean insertSendUeInfo(List<RcvUeInfo> members, UeInfoMapper ueInfoMapper, DeviceMapper deviceMapper, SqlSessionFactory sqlSessionFactory)
     {
    	
    	byte indication = 0;
    	String proviceCode;
		String cityCode;
		
		
    	for (RcvUeInfo rcvUeInfo:members)
		{
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
    		ueInfo.setCreateTime(dateformat.format( new Date()));
    		ueInfo.setMac("abcd1234efdeab");
    		ueInfo.setRarta(Long.parseLong(String.valueOf(rcvUeInfo.getRaRta())));
       		SigletonBean.insertUeInfoList.add(ueInfo);
			
   		}
    	
    	try {
			this.batchInsertUeInfo(SigletonBean.insertUeInfoList, sqlSessionFactory);
			SigletonBean.insertUeInfoList.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return true;
    }
    
    
    private Device  getSiteDvInfo(String deviceSn, DeviceMapper deviceMapper)
    {
    	//Device device = new Device();
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("siteid", null);
	    params.put("sitesn", null);
	    params.put("devicesn", deviceSn);
	    
	    List<Device>  deviceList =(List<Device>) deviceMapper.selectDeviceDetails(params);
	    if (deviceList.size() != 0)
	    {
	    	return deviceList.get(0);
	    }
	    else
	    {
	    	return null;
	    }
    }

    public void batchInsertUeInfo(List<UeInfo> ueInfolist, SqlSessionFactory sqlSessionFactory) throws Exception {  
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);  
        try {  
            sqlSession.insert("com.iekun.ef.dao.UeInfoMapper.batchInsertUeInfo", ueInfolist);  
            sqlSession.commit();  
        } finally {  
            sqlSession.close();  
        }  
    } 
    
    public void batchInsertUeInfoToSplitTable(List<UeInfo> ueInfolist, SqlSessionFactory sqlSessionFactory) throws Exception {  
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);  
        try {  
            sqlSession.insert("com.iekun.ef.dao.UeInfoMapper.batchSplitInsertUeInfo", ueInfolist);  
            sqlSession.commit();  
        } finally {  
            sqlSession.close();  
        }  
    } 
    
	//@Override
	public void updateSendHeartBeat(RcvHeartBeat sendHeartBeat) {
		//this.getSqlMapClientTemplate().update("jmsMessageHandle.updateSendHeartBeat", sendHeartBeat);
		//heartbeat作为状态字段，需要保存，保存方式分为 1、hash表，在SigletonBean定义一个静态hashmap；2、内存表 可以采用内存数据库的方式保存
		// todo 
		return;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	//@Override
	public void addOperLog(String pktType, String serialNum, int userId, int sessionId) {
		HashMap map = new HashMap();
		map.put("pktType", pktType);
		map.put("serialNum", serialNum);
		map.put("userId", userId);
		map.put("sessionId", sessionId);
		
		return ;
		//this.getSqlMapClientTemplate().insert("jmsMessageHandle.addOperLog", map);
	}

	//@Override
	public void updateReqCnf(RcvReqCnf sendReqCnf) {
		//this.getSqlMapClientTemplate().update("jmsMessageHandle.updateReqCnf", sendReqCnf);
		//对所有oam消息都要做cnf都要处理，
		return;
	}

	//@Override
	public void deleteDevVersion(RcvDevVersion sendDevVersion) {
		//this.getSqlMapClientTemplate().delete("jmsMessageHandle.deleteDevVersion", sendDevVersion);
		return;
	}

	//@Override
	public void insertDevVersion(RcvDevVersion sendDevVersion) {
		//this.getSqlMapClientTemplate().insert("jmsMessageHandle.insertDevVersion", sendDevVersion);
		return;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	//@Override
	public void updateOperLog(int sessionId, int operStatus) {
		HashMap map = new HashMap();
		map.put("sessionId", sessionId);
		map.put("operStatus", operStatus);
		//this.getSqlMapClientTemplate().update("jmsMessageHandle.updateOperLog", map);
		return;
	}

	//@Override
	public boolean insertTargetUeInfo(List<RcvUeInfo> members, TargetAlarmMapper targetAlarmMapper, DeviceMapper deviceMapper, SqlSessionFactory sqlSessionFactory) 
	  {
		SimpleDateFormat dateformat =new SimpleDateFormat("yyyyMMddHHmmss");
		byte isRead = 0;
		
		String proviceCode;
		String cityCode;

		for (RcvUeInfo RecvTargetAlarm : members)
		{
			byte indication = 0;
			
			targetAlarm  = new TargetAlarm(RecvTargetAlarm.getCaptureTime(), RecvTargetAlarm.getSerialNum(), RecvTargetAlarm.getUeImei(), RecvTargetAlarm.getUeImsi(),
    				String.valueOf(RecvTargetAlarm.getUeTaType()), RecvTargetAlarm.getUeSTmsi(), Long.parseLong(String.valueOf(RecvTargetAlarm.getRaRta())));
			
			proviceCode = CommonConsts.getAlignCharText(RecvTargetAlarm.getCode_province());
			cityCode =  CommonConsts.getAlignCharText(RecvTargetAlarm.getCode_city());
			SelfAreaCode selfAreaCode = (SelfAreaCode)SigletonBean.selfAreaCodeMaps.get(proviceCode+cityCode);
			if (selfAreaCode != null)
    		{
    			targetAlarm.setCityName(selfAreaCode.getProvinceName() + selfAreaCode.getCityName());
    			targetAlarm.setCityCode(proviceCode+cityCode);
    		}
			
			Device device =  (Device)(SigletonBean.deviceSiteInfoMaps.get(RecvTargetAlarm.getSerialNum()));
			//Device device = this.getSiteDvInfo(RecvTargetAlarm.getSerialNum(), deviceMapper);
    		if (device != null)
    		{
    			targetAlarm.setBand(device.getBand());
    			targetAlarm.setOperator(CommonConsts.getOperatorTypeText(Integer.valueOf(device.getOperator())));
    			targetAlarm.setSiteSn(device.getSite().getSn());
    			targetAlarm.setSiteName(device.getSite().getName());
    		}
    		
    		if (RecvTargetAlarm.getBoolLib() == 1)
			{
				indication = 1;
			}
    		else
    		{
    			if(RecvTargetAlarm.getBoolLocaleInlib() == 1)
    			{
    				indication = 2;
    			}
    		}
			
			targetAlarm.setIndication(new Byte(indication));
			targetAlarm.setRealtime(new Integer(RecvTargetAlarm.getBoolsys()));
    		targetAlarm.setIsRead(isRead);
			targetAlarm.setCreateTime(dateformat.format( new Date()));
    	    targetAlarm.setMac("abcd1234efdeab");
			targetAlarm.setRarta(Long.parseLong(String.valueOf(RecvTargetAlarm.getRaRta())));
			//targetAlarmMapper.insert(targetAlarm);
			SigletonBean.insertTargetAlarmList.add(targetAlarm);
		}
		
		try {
			this.batchInsertTargetAlarm(SigletonBean.insertTargetAlarmList, sqlSessionFactory);
			SigletonBean.insertTargetAlarmList.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	/*@Transactional()  
	public void batchInsertTargetAlarmTransactional(List<TargetAlarm> targetAlarmlist) throws Exception {  
	    for (TargetAlarm targetAlarm : targetAlarmlist) {  
	    	targetAlarmMapper.insert(targetAlarm);  
	    }  
	} */
	
	public void batchInsertTargetAlarm(List<TargetAlarm> targetAlarmlist, SqlSessionFactory sqlSessionFactory) throws Exception {  
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);  
        try {  
            sqlSession.insert("com.iekun.ef.dao.TargetAlarmMapper.batchInsertTargetAlarm", targetAlarmlist);  
            sqlSession.commit();  
        } finally {  
            sqlSession.close();  
        }  
    } 
		
	public void insertDvAlarmInfo(String deviceSn, ArrayList<RcvAlarm> rcvAlarmList, DeviceAlarmMapper dvAlarmMapper)
	{
		String rcvAlarmInfo; 
		Map<String,Object> params;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowDate =  new Date();
		String noDateStr = df.format(nowDate);
		Map<String, Object>  DvSiteInfoMap = SigletonBean.deviceSiteInfoMaps;
		
		Device device = (Device)DvSiteInfoMap.get(deviceSn);
		for (RcvAlarm rcvAlarm : rcvAlarmList)
		{
			rcvAlarmInfo = rcvAlarm.getDvAlarm().trim();
			params=new HashMap<String, Object>();
			params.put("alarmType", "设备告警");
			params.put("alarmCode", CommonConsts.getAlarmNum(rcvAlarmInfo));
	        params.put("alarmInfo", CommonConsts.getAlarmInfo(rcvAlarmInfo));
	        params.put("alarmTime", noDateStr);
	        params.put("alarmLevel", "1");
	        params.put("siteId", device.getSite().getId());
	        params.put("deviceSn", deviceSn);
			
	        dvAlarmMapper.insetAlarmInfo(params);
		}
	}
	
}
