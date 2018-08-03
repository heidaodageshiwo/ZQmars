package com.iekun.ef.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iekun.ef.jms.service.IHandleMessageService;
import com.iekun.ef.jms.vo.receive.RcvUeInfo;
import com.iekun.ef.model.RuleSend;
import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.service.DeviceService;

import java.util.Map; 


public class SigletonBean {

	public static Logger logger = LoggerFactory.getLogger( SigletonBean.class);
	
	public static final int maxCount = 500;
	//public static Queue<RcvUeInfo> queue=new LinkedList<RcvUeInfo>();
	//public static Queue<RcvUeInfo> blackQueue=new LinkedList<RcvUeInfo>();
	public static List<RcvUeInfo> ueInfoList=Collections.synchronizedList(new ArrayList<RcvUeInfo>());
	public static List<RcvUeInfo> blackList=Collections.synchronizedList(new ArrayList<RcvUeInfo>());
	
	public static List<UeInfo> preDayList = Collections.synchronizedList(new  ArrayList<UeInfo>());
	public static List<UeInfo> currDayList = Collections.synchronizedList(new  ArrayList<UeInfo>());
	public static List<UeInfo> afterDayList = Collections.synchronizedList(new  ArrayList<UeInfo>());
	public static Map<String, Object> dateDataListMaps = new ConcurrentHashMap<String,Object>();
	public static Map<String, Object> listNameListUpdateMaps = new ConcurrentHashMap<String,Object>();
	
	public static List<Long>  useIdList = Collections.synchronizedList(new  ArrayList<Long>());
	
	public static Map<String, Object> userIdSessionMaps = new ConcurrentHashMap<String,Object>();
	
	public static Map<String, Object> deviceUserMaps = new ConcurrentHashMap<String,Object>();
	
	public static Map<String, Object> deviceSiteInfoMaps = new ConcurrentHashMap<String,Object>();
	
	public static Map<String, IHandleMessageService> receiveMsgHandleMap = new ConcurrentHashMap<String, IHandleMessageService>();
	
	public static Map<String, Object> deviceStatusInfoMaps = new ConcurrentHashMap<String,Object>();
	
	public static Map<String, Object> deviceAlarmInfoMaps = new ConcurrentHashMap<String,Object>();
	
	public static Map<String, Object> selfAreaCodeMaps  = new HashMap<String,Object>();
	
	public static Map<String, Object> operCodeNameTypeMaps = new HashMap<String,Object>();
	
	public static Map<String, Object> maps = new ConcurrentHashMap<String,Object>();
	//public static Map<String, Object> blackmaps = new HashMap<String,Object>();
	
	public static Map<String,Object> siteDeviceMaps = new ConcurrentHashMap<String,Object>();
	
	public static Map<String,Object> siteSnSiteMaps = new ConcurrentHashMap<String,Object>();
	
	public static Map<String, Object> showmaps = new ConcurrentHashMap<String,Object>();
	public static Map<String, Object> showbuffmaps = new ConcurrentHashMap<String,Object>();
	public static Map<String, Object> blackshowmaps = new ConcurrentHashMap<String,Object>();
	
	public static Map<String,Object>exportInfoMaps = new ConcurrentHashMap<String,Object>();
	
	
	public static List<RuleSend> targetRuleSendList = Collections.synchronizedList(new ArrayList<RuleSend>());
	
	public static List<RuleSend> areaRuleSendList = Collections.synchronizedList(new ArrayList<RuleSend>());
	
	public static List<UeInfo> insertUeInfoList = new ArrayList<UeInfo>();
	
	public static List<TargetAlarm> insertTargetAlarmList = new ArrayList<TargetAlarm>();
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> sendCnf = new ConcurrentHashMap<String,Object>();
	public static int sessionId = 0;
	public static final int maxSessionId = 65535;
	
	public static long exportId =0;
	public static final long maxExportId = 65535;
	
	public static final int SUCCESS = 0;
	public static final int FAILURE = 1;
	public static final String pwd = "123456";
	public static int zeroCount = 0;
	public static int blackZeroCount = 0;
	public static final int maxZeroCount = 0;
	
	
	@SuppressWarnings("unchecked")
	public static synchronized void putToMap(String key, String value){
		SigletonBean.sendCnf.put(key, value);
	}
	
	public static synchronized int getSendfCnfSize(){
		return SigletonBean.sendCnf.size();
	}
	
	public static synchronized void removeFromMap(String key){
		SigletonBean.sendCnf.remove(key);
	}
		
	public static synchronized Object getValue(String key){
		return SigletonBean.sendCnf.get(key);
	}
	
	public static long getUserId(){
		long userId = 0;
		Subject currentUser;
		try {
			currentUser = SecurityUtils.getSubject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return userId;
		}
		Session session = currentUser.getSession();
		userId = (long)session.getAttribute("userId");
		return userId;
	}
	
	public static long getRoleId(){
		long roleId = 0;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		roleId = (long)session.getAttribute("roleId");
		return roleId;
	}
	
	public static String getUserName(){
		String userName = null;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		userName = (String)session.getAttribute("userName");
		return userName;
	}
}
