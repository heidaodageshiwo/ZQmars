package com.iekun.ef.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.iekun.ef.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iekun.ef.dao.UserMapper;
import com.iekun.ef.jms.vo.receive.RcvUeInfo;
import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.dao.OperCodeMapper;
import com.iekun.ef.dao.RoleMapper;
import com.iekun.ef.dao.SiteMapper;
import com.iekun.ef.dao.SiteUserMapper;
import com.iekun.ef.util.CommonConsts;
import com.iekun.ef.util.TimeUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;

@Service
public class DeviceService {
	
	 public static Logger logger = LoggerFactory.getLogger( DeviceService.class);
	 
	  @Autowired
	  private DeviceMapper deviceMapper;
	  
	  @Autowired
	  private SiteMapper siteMapper;
	  
	  @Autowired
	  private SiteUserMapper siteUserMapper;
	  
	  @Autowired 
	  private OperCodeMapper operCodeMapper;
	  
//	  @Autowired
//	  private TargetAlarmService targetServ;
	  
		  
	  public List<Site>  getSiteListInfo(){
		  
		    List<Site>  siteList =(List<Site>) siteMapper.selectSiteList();
	        //User user=null;
	        return siteList;
	    }
	 	  
	  public List<Site>  getSiteListInfoByUserId(Long userId){
		  
		    List<Site>  siteList =(List<Site>) siteMapper.selectSiteListByUserId(userId);
	        //User user=null;
	        return siteList;
	    }	
	 
		  
	  public boolean addSite(Site site) 
		{
			try {
				
				siteMapper.insert(site);
				this.clearGlobalMapInfo();
				this.updateGlobalMapInfo();
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	  
	  public boolean updateSite(Site site) 
		{
			try {
				
				siteMapper.updateByPrimaryKeySelective(site);
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	  
	  
	  public boolean delSite(Site site) 
		{
			try {
				//删除站点的时候去删除t_site_user的映射关系
				siteMapper.deleteSiteUserMapping(site.getId());
				//更新站点状态DELETE_FLAG
				siteMapper.updateByPrimaryKeySelective(site);
				this.clearGlobalMapInfo();
				this.updateGlobalMapInfo();
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	  
	  public Site selectBySiteSn(String siteSn)
	  {
		  Site site = siteMapper.selectBySiteSn(siteSn);
		  return site;
	  }

	/**
	 * 根据站点ID，站点号获取所有设备信息
	 * @param siteId
	 * @param siteSN
	 * @return
	 */
	  public List<Device>  getDeviceListInfo(Integer siteId, String siteSN){
		  
		    Map<String,Object> params=new HashMap<String, Object>();
		    if (siteId == null || siteId == 0)
		    {
		    	 params.put("siteid", null);
		    }
		    else
		    {
		    	 params.put("siteid", siteId);
		    }
		    
		    if (siteSN == null || siteSN.isEmpty())
		    {
		    	params.put("sitesn", null);
		    }
		    else
		    {
		    	params.put("sitesn", siteSN);
		    }
		 
		    List<Device>  deviceList = (List<Device>) deviceMapper.selectDeviceDetails(params);
	        //User user=null;
	        return deviceList;
	    }
	  
	  public List<Device>  getDeviceListInfoBySiteId(long siteId){
		  
		     List<Device>  deviceList = (List<Device>) deviceMapper.selectDeviceDetailsBySiteId(siteId);
	        //User user=null;
	        return deviceList;
	    }
	  
	  public List<Device>  getDeviceList(){
			  
		     List<Device>  deviceList =(List<Device>) deviceMapper.selectDevList();
	        //User user=null;
	        return deviceList;
	    }
		  
	  public Device getDeviceById(long deviceId)
	  {
		  Device device = deviceMapper.selectByPrimaryKey(deviceId);
		  return device;
	  }
	    
	  public boolean addDevice(Device device) 
	  {
			try {
				
				deviceMapper.insert(device);
				//deviceMapper.insertSelective(device);
				this.clearGlobalMapInfo();
				this.updateGlobalMapInfo();
			
				this.addItemIntoRuleList(device.getSn());
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	  	  
	  public boolean updateDevice(Device device) 
		{
			try {
				
				deviceMapper.updateByPrimaryKeySelective(device);
				//deviceMapper.updateDeviceById(device);
				/*更新缓存device的信息*/
				this.clearGlobalMapInfo();
				this.updateGlobalMapInfo();
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	  
	  
	  public boolean delDevice(Device device) 
		{
			try {
				
				deviceMapper.deleteDeviceById(device);
				this.clearGlobalMapInfo();
				this.updateGlobalMapInfo();
				this.deleteDeviceInfoFromGlobalMap(device.getSn());
				this.delItemFromRuleList(device.getSn());
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	  
	  
	  public boolean assignDevice(SiteUser siteUser) 
		{
			try {
				
				siteUserMapper.insert(siteUser);
				/*更新 deviceuser Map表  user-->site-->device*/
				this.updateDeviceUseMaps(siteUser);
				return true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
			
    public List<DeviceUser> getDeviceUserList()
    {
    	List<DeviceUser> deviceUserList = null;
		try {
			deviceUserList = (List<DeviceUser>) deviceMapper.selectDeviceUsers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return deviceUserList;
    }

	public List<SiteUser> getSiteUserInfoBySiteIdAndUserId(SiteUser siteUser) {
		// TODO Auto-generated method stub
		 
		 List<SiteUser>  siteUserList =(List<SiteUser>) siteUserMapper.selectSUListBySIdAndUId(siteUser);
         return siteUserList;
	}

	public List<Site> selectSiteList() {
		// TODO Auto-generated method stub
		
		List<Site>  siteList =(List<Site>) siteMapper.selectSiteList();
	    return siteList;
	}

	public boolean updateAssign(SiteUser siteUser) {
		// TODO Auto-generated method stub
		try {
	    	siteUserMapper.update(siteUser);
	    	/*更新 deviceuser Map表   user-->site-->device*/
	    	this.updateDeviceUseMaps(siteUser);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void updateDeviceUseMaps(SiteUser siteUser)
	{
		Map<String, Object> dvUserMaps = SigletonBean.deviceUserMaps;
		List<Long> userIdList;
		boolean removeSucc = true;
		int delFlag = siteUser.getDeleteFlag();
		List<Device>  deviceList = getDeviceListInfoBySiteId(siteUser.getSiteId());
		if (delFlag == 0)
		{
			/* 分配 */
			for(int i =0; i < deviceList.size(); i ++)
			{
				userIdList = (ArrayList<Long>)dvUserMaps.get(deviceList.get(i).getSn());
				if (userIdList == null)
				{
					logger.info("device sn: " + deviceList.get(i).getSn());
					userIdList = new ArrayList<Long>();
					dvUserMaps.put(deviceList.get(i).getSn(), userIdList);
					
				}
				
				while(removeSucc == true)
				{
					removeSucc = userIdList.remove(siteUser.getUserId());
				}
				userIdList.add(siteUser.getUserId());
				
			}
			
		}
		else if (delFlag == 1)
		{
			/* 去分配 */
			for(int i =0; i < deviceList.size(); i ++)
			{
				userIdList = (ArrayList<Long>)dvUserMaps.get(deviceList.get(i).getSn());
				if (userIdList == null)
				{
					logger.info("device sn: " + deviceList.get(i).getSn());
				}
				userIdList.remove(siteUser.getUserId());
			}
		}
		else
		{
			logger.info(" invalid oper");
		}
			 
		return;
	}
	
	@PostConstruct
    public void start() throws Exception {
        logger.info("updateGlobalMapInfo is running!\n");
        this.updateGlobalMapInfo();

    }
	
	@PreDestroy
    public void stop() throws Exception {
        logger.info("updateGlobalMapInfo is destroy!\n");
    }
	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public void updateGlobalMapInfo()
	{

		String DeviceSn = null;
		List<DeviceUser> dvUserList = null;
		//DeviceUser deviceUser;
		List<Long> useIdList = null;
		
		List<Device> DeviceListBysiteId = null;
		
		DeviceStatus deviceStatus;
		Device enterDevice;
		SelfAreaCode selfAreaCode;
		OperCode operCode;
		
		Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
		
		/* 查找所有设备sn, 初始化 */
		List<Device> deviceList =  this.getDeviceListInfo(null, null);
		for(Device device : deviceList)
		{
			DeviceSn = device.getSn();
			/* 如果该设备没有分配过状态map，则加入状态map信息 */
			if (dvStatusMaps.get(DeviceSn) == null)
			{
				/* 设备一般的状态信息 */
				DeviceStatusCommon deviceStatusCommon = new DeviceStatusCommon();
				/* 设备状态初始化 */
				deviceStatusCommon.setStatusUpdateTime(null);
				deviceStatusCommon.setDeviceStatus("offline");
				
				/* 设备调试状态信息 */
				DeviceStatusDebug deviceStatusDebug = new DeviceStatusDebug();
				
				/* 设备环境状态信息 */
				DeviceStatusEnviroment deviceStatusEnviroment = new DeviceStatusEnviroment();
				
				/* 设备许可状态信息 */
			    DeviceStatusLicense  deviceStatusLicense = new DeviceStatusLicense();
			    deviceStatusLicense.setExpirationTime(null);
			    
			    /* 设备功放状态信息 */
			    DeviceStatusPowerAmplifier deviceStatusPowerAmplifier = new DeviceStatusPowerAmplifier();
			    
			    /* 设备功放基本信息 */
			    DevicePowerAmplifierInfo  devicePowerAmplifierInfo = new DevicePowerAmplifierInfo();
			        
			    /* 设备sniffer信息 */
			    DeviceStatusSniffer deviceStatusSniffer = new DeviceStatusSniffer();

			    DeviceStatusWorkMode deviceStatusWorkMode = new DeviceStatusWorkMode();
			    
				deviceStatus = new DeviceStatus();
				deviceStatus.setDevicePowerAmplifierInfo(devicePowerAmplifierInfo);
				deviceStatus.setDeviceStatusCommon(deviceStatusCommon);
				deviceStatus.setDeviceStatusDebug(deviceStatusDebug);
				deviceStatus.setDeviceStatusEnviroment(deviceStatusEnviroment);
				deviceStatus.setDeviceStatusLicense(deviceStatusLicense);
				deviceStatus.setDeviceStatusPowerAmplifier(deviceStatusPowerAmplifier);
				deviceStatus.setDeviceStatusSniffer(deviceStatusSniffer);
				deviceStatus.setDeviceStatusWorkMode(deviceStatusWorkMode);
				
			    dvStatusMaps.put(DeviceSn, deviceStatus);
			}
		}
		
		/* 设备和userid的对应表  */
		Map<String, Object> dvUserMaps = SigletonBean.deviceUserMaps;
		dvUserList = this.getDeviceUserList();
		if (dvUserList != null)
		{
			for(DeviceUser DvUser:dvUserList)
			{
				if (dvUserMaps.get(DvUser.getSn())== null)
				{
					useIdList = new ArrayList<Long>();
					useIdList.add(DvUser.getUserId());
					dvUserMaps.put(DvUser.getSn(), useIdList);
				}
				else
				{   useIdList = (ArrayList<Long>)dvUserMaps.get(DvUser.getSn());
					useIdList.add(DvUser.getUserId());
					dvUserMaps.put(DvUser.getSn(), useIdList);
				}
			}
			
		}
		
		/* 站点id与设备关联表 */
		this.siteIdDeviceMapGen();
		
		/* 站点id与站点sn关联表 */
		this.siteIdSiteSnMapGen();
		
		/* 设备sn 与设备消息信息的映射表  */
		this.deviceSnDeviceInfoMapGen();
		

	    /* targetRule初始化  系统启动时初始化发送列表，后续增加和删除设备需要保持当前发送状态，不需要此操作 */
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("siteid", null);
	    params.put("sitesn", null);
	    params.put("devicesn", null);
	    List<Device>  deviceDetailList = (List<Device>) deviceMapper.selectDeviceDetails(params);

	    List<RuleSend> targetRuleSendList = SigletonBean.targetRuleSendList;
		if ((deviceDetailList.size() != 0)&&(targetRuleSendList.size() == 0))
	    {
	    	for(Device deviceDetail:deviceDetailList)
	    	{
	    		
		    	RuleSend targetRuleSend = new RuleSend();
		    	targetRuleSend.setSerialNum(deviceDetail.getSn());
		    	targetRuleSend.setSessionId(0);
		    	targetRuleSend.setSyncIndexSymbol(0);
		    	Map<Integer, Object> targetSessionIdSegIdMap = new ConcurrentHashMap<Integer,Object>();
				targetRuleSend.setSessionIdSegIdMap(targetSessionIdSegIdMap);
					
			    Map<Integer, Object> targetSegIdSyncSymbolMap= new ConcurrentHashMap<Integer,Object>();
				targetRuleSend.setSegIdSyncSymbolMap(targetSegIdSyncSymbolMap);
		    	targetRuleSendList.add(targetRuleSend);
	    	}
	    }
	    
	    /* areaRule 初始化，  系统启动时初始化发送列表，后续增加和删除设备需要保持当前发送状态，不需要此操作 */
	    List<RuleSend> areaRuleSendList = SigletonBean.areaRuleSendList;
		if ((deviceDetailList.size() != 0)&&(areaRuleSendList.size() == 0))
	    {
	    	for(Device deviceDetail:deviceDetailList)
	    	{ 
		    	RuleSend ruleSend = new RuleSend();
		    	ruleSend.setSerialNum(deviceDetail.getSn());
		    	ruleSend.setSessionId(0);
		    	ruleSend.setSyncIndexSymbol(0);
		    	Map<Integer, Object> areaSessionIdSegIdMap = new ConcurrentHashMap<Integer,Object>();
		    	ruleSend.setSessionIdSegIdMap(areaSessionIdSegIdMap);
					
				Map<Integer, Object> areaSegIdSyncSymbolMap= new ConcurrentHashMap<Integer,Object>();
				ruleSend.setSegIdSyncSymbolMap(areaSegIdSyncSymbolMap);
		    	areaRuleSendList.add(ruleSend);
	    	}
	    }
	    
	    /* selfcitycode 与 areaCode的map表  */
	    Map<String, Object> sfAreaCodeMaps = SigletonBean.selfAreaCodeMaps;
		List<SelfAreaCode>  selfareaCodeDetailList =(List<SelfAreaCode>) deviceMapper.selectAreaCodeDetails();
	    if (selfareaCodeDetailList.size() != 0)
	    {
	    	for(SelfAreaCode areaCodeDetail:selfareaCodeDetailList)
	    	{
	    		selfAreaCode = new SelfAreaCode();
	    		selfAreaCode = areaCodeDetail;
	    		sfAreaCodeMaps.put((areaCodeDetail.getProvinceCode() + areaCodeDetail.getCityCode()), selfAreaCode);
	    	}
	    }
	    
	    
	    Map<String, Object> operCodeNameTypeMap = SigletonBean.operCodeNameTypeMaps;
	    List<OperCode> operCodeList = (List<OperCode>)operCodeMapper.selectOperCodeList();
	    if (operCodeList.size() != 0)
	    {
	    	for(OperCode operCodeDetail:operCodeList)
	    	{
	    		operCode = new OperCode();
	    		operCode = operCodeDetail;
	    		operCodeNameTypeMap.put(operCodeDetail.getOperCode(), operCode);
	    	}
	    }
	    
	    
	}
	
	public void deviceSnDeviceInfoMapGen()
	{
		Device enterDevice;
		Map<String, Object> dvSnDeviceMaps = SigletonBean.deviceSiteInfoMaps;
		//DeviceSiteDetailInfo DvSiteInfo =  (DeviceSiteDetailInfo)SigletonBean.deviceSiteInfoMaps.get(deviceSn);
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("siteid", null);
	    params.put("sitesn", null);
	    params.put("devicesn", null);
	    List<Device>  deviceDetailList =(List<Device>) deviceMapper.selectDeviceDetails(params);
	    if (deviceDetailList.size() != 0)
	    {
	    	for(Device deviceDetail:deviceDetailList)
	    	{
	       		enterDevice = new Device();
	    		enterDevice = deviceDetail;
	    		dvSnDeviceMaps.put(deviceDetail.getSn(), enterDevice);
	    	}
	    }
	}

	public void siteIdSiteSnMapGen()
	{
		Map<String, Object> siteSnSiteMaps = SigletonBean.siteSnSiteMaps;
		List<Site> sitesList =  this.getSiteListInfo();
		if (sitesList != null)
		{
			if (sitesList.size() != 0)
			{
				for(Site site:sitesList)
				{
					if (siteSnSiteMaps.get(site.getSn())== null)
					{   
						siteSnSiteMaps.put(site.getSn(), site);
					}
					
				}
			}
		}
	}

	//生成站点id 对应 设备列表 映射
	public void siteIdDeviceMapGen()
	{
		List<Device> DeviceListBysiteId = null;
	
		DeviceStatus deviceStatus;
		//Device enterDevice;
		
		Map<String, Object> siteIdDeviceMaps = SigletonBean.siteDeviceMaps;
		List<Device> devicesList = this.getDeviceListInfo(null, null);
		if (devicesList != null)
		{

			for(Device device1 : devicesList)
			{
				if (device1.getSite() == null)
				{
					continue;
				}

				if (siteIdDeviceMaps.get(device1.getSite().getId().toString()) == null)
				{
					DeviceListBysiteId = new ArrayList<Device>();
					//enterDevice = new Device();
					//enterDevice = device1;
					DeviceListBysiteId.add(device1);
					siteIdDeviceMaps.put(device1.getSite().getId().toString(), DeviceListBysiteId);
				}
				else
				{
					DeviceListBysiteId = (ArrayList<Device>)siteIdDeviceMaps.get(device1.getSite().getId().toString());
					//enterDevice = new Device();
					//enterDevice = device1;
					//这里应该判断siteId对应的设备列表是否已经包含该设备
					List<Device> filterList  = DeviceListBysiteId.stream().filter(s -> s.getId() == device1.getId() && s.getSn() == device1.getSn()).collect(Collectors.toList());
					if(filterList.size() > 0){
						continue;
					}
					DeviceListBysiteId.add(device1);
					siteIdDeviceMaps.put(device1.getSite().getId().toString(), DeviceListBysiteId);
				}
			}

		}
	}
	
	public void addItemIntoRuleList(String devSn)
	{
		 List<RuleSend> areaRuleSendList = SigletonBean.areaRuleSendList;
		 List<RuleSend> targetRuleSendList = SigletonBean.targetRuleSendList;
		 
		 RuleSend areaRuleSend = new RuleSend();
		 areaRuleSend.setSerialNum(devSn);
		 areaRuleSend.setSessionId(0);
		 areaRuleSend.setSyncIndexSymbol(0);
		 Map<Integer, Object> areaSessionIdSegIdMap = new ConcurrentHashMap<Integer,Object>();
		 areaRuleSend.setSessionIdSegIdMap(areaSessionIdSegIdMap);
			
		 Map<Integer, Object> areaSegIdSyncSymbolMap= new ConcurrentHashMap<Integer,Object>();
		 areaRuleSend.setSegIdSyncSymbolMap(areaSegIdSyncSymbolMap);
		 areaRuleSendList.add(areaRuleSend);
		 
		 RuleSend targetRuleSend = new RuleSend();
		 targetRuleSend.setSerialNum(devSn);
		 targetRuleSend.setSessionId(0);
		 targetRuleSend.setSyncIndexSymbol(0);
		 Map<Integer, Object> targetSessionIdSegIdMap = new ConcurrentHashMap<Integer,Object>();
		 targetRuleSend.setSessionIdSegIdMap(targetSessionIdSegIdMap);
			
		 Map<Integer, Object> targetSegIdSyncSymbolMap= new ConcurrentHashMap<Integer,Object>();
		 targetRuleSend.setSegIdSyncSymbolMap(targetSegIdSyncSymbolMap);
		 targetRuleSendList.add(targetRuleSend);
		 
	}
	
	public void delItemFromRuleList(String devSn)
	{
		List<RuleSend> areaRuleSendList = SigletonBean.areaRuleSendList;
		List<RuleSend> targetRuleSendList = SigletonBean.targetRuleSendList;
		int areaRuleListSize = areaRuleSendList.size();
		int targetRuleListSize = targetRuleSendList.size();
		String listInnerDevSn = null;
		for(int i=0; i < areaRuleListSize; i++)
		{
			listInnerDevSn = areaRuleSendList.get(i).getSerialNum();
			if(listInnerDevSn.equalsIgnoreCase(devSn))
			{
				areaRuleSendList.remove(i);
				break;
			}
		}
		
		for(int i=0; i < targetRuleListSize; i++)
		{
			listInnerDevSn = targetRuleSendList.get(i).getSerialNum();
			if(listInnerDevSn.equalsIgnoreCase(devSn))
			{
				targetRuleSendList.remove(i);
				break;
			}
		}
		
	}
	
	
    public void clearGlobalMapInfo()
    {
    	
    	Map<String, Object> siteIdDeviceMaps = SigletonBean.siteDeviceMaps;
    	
    	siteIdDeviceMaps.clear();
    	
    	Map<String, Object> dvUserMaps = SigletonBean.deviceUserMaps;
    	
    	dvUserMaps.clear();
    	
    	Map<String, Object> siteSnSiteMaps = SigletonBean.siteSnSiteMaps;
    	
    	siteSnSiteMaps.clear();
    	
    	Map<String, Object> dvSnDeviceMaps = SigletonBean.deviceSiteInfoMaps;
    	
    	dvSnDeviceMaps.clear();
    	    	
    }
    
    public void deleteDeviceInfoFromGlobalMap(String deviceSn)
    {
    	Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
    	dvStatusMaps.remove(deviceSn);
    }
    
    public void addDeviceInfoFromGlobalMap(String deviceSn)
    {
    	Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
    	DeviceStatus deviceStatus;
    	
    	/* 设备一般的状态信息 */
		DeviceStatusCommon deviceStatusCommon = new DeviceStatusCommon();
		/* 设备状态初始化 */
		deviceStatusCommon.setDeviceStatus("offline");
		
		/* 设备调试状态信息 */
		DeviceStatusDebug deviceStatusDebug = new DeviceStatusDebug();
		
		/* 设备环境状态信息 */
		DeviceStatusEnviroment deviceStatusEnviroment = new DeviceStatusEnviroment();
		
		/* 设备许可状态信息 */
	    DeviceStatusLicense  deviceStatusLicense = new DeviceStatusLicense();
	    
	    /* 设备功放状态信息 */
	    DeviceStatusPowerAmplifier deviceStatusPowerAmplifier = new DeviceStatusPowerAmplifier();
	    
	    /* 设备功放基本信息 */
	    DevicePowerAmplifierInfo  devicePowerAmplifierInfo = new DevicePowerAmplifierInfo();
	        
	    /* 设备sniffer信息 */
	    DeviceStatusSniffer deviceStatusSniffer = new DeviceStatusSniffer();

	    /* 设备工作模式 */
	    DeviceStatusWorkMode deviceStatusWorkMode = new DeviceStatusWorkMode();
	    
		deviceStatus = new DeviceStatus();
		deviceStatus.setDevicePowerAmplifierInfo(devicePowerAmplifierInfo);
		deviceStatus.setDeviceStatusCommon(deviceStatusCommon);
		deviceStatus.setDeviceStatusDebug(deviceStatusDebug);
		deviceStatus.setDeviceStatusEnviroment(deviceStatusEnviroment);
		deviceStatus.setDeviceStatusLicense(deviceStatusLicense);
		deviceStatus.setDeviceStatusPowerAmplifier(deviceStatusPowerAmplifier);
		deviceStatus.setDeviceStatusSniffer(deviceStatusSniffer);
		deviceStatus.setDeviceStatusWorkMode(deviceStatusWorkMode);
		
	    dvStatusMaps.put(deviceSn, deviceStatus);
    }
    
	
	@SuppressWarnings("unchecked")
	public JSONObject getDevicesTreeServ(Long userId, String  filter)
	{
		Map<String, Object> citymaps = new HashMap<String,Object>();
    	
    	Map<String, Object> provincemaps = new HashMap<String,Object>();

    	Map<String, Object> townmaps = new HashMap<String,Object>();
    	
    	List<Site>  currentList = null;
    	
    	
    	Site currentSite;
    	
    	boolean isFind = false;

        /*jsonObject = com.iekun.ef.test.ui.UITestDeviceData.getDevicesTree( userId );
        */
        /*return jsonObject;*/
        
        /* 1、先根据userId查找t_site_user表，得到所有与该user相关的site的siteId
         * 2、根据siteId逐条查找t_site表，得到的记录。
         * 3、就得到数据集进行操作，中间层级节点去重，生成树状js格式数据返回给前端。*/
        
        /*去重算法如下：
         * 输入：site记录集
        * 1、按town遍历site记录集，得到按townID的分类集合；如（townID1：站点1，站点2...）.....(townIDn: 站点n1，站点n2....)
        * 2、按city遍历site记录集，得到按cityID的分类集合，重复的不添加；如(cityID1:town1，town2....)
        * 3、按province遍历site记录集，得到按provinceID的分类集合，重复的不添加；如(provinceID1: city1,city2...)            * 
        * */
        /* 联表查询 */
        List<Site> siteList= this.getSiteListInfoByUserId(userId);
        for (Site site : siteList)
        {
        	/*按town归类*/
        	currentList = (List<Site>)townmaps.get(String.valueOf(site.getTown_id()));
        	if (/* town id 没有，则*/null == currentList)
        	{
        		currentList = new ArrayList<Site>();
        		currentList.add(site);
        		townmaps.put(String.valueOf(site.getTown_id()), currentList);
        	}
        	else
        	{
        		currentList.add(site);
        		//townmaps.put(String.valueOf(site.getTown_id()), currentList);
        	  		
        	}
        	
        	/*按city归类，但相同town的记录要去重*/
        	currentList = (List<Site>)citymaps.get(String.valueOf(site.getCity_id()));
        	if (/* 对应city id 记录没有，则*/null == currentList)
        	{
        		currentList = new ArrayList<Site>();
        		currentList.add(site);
        		citymaps.put(String.valueOf(site.getCity_id()), currentList);
        	}
        	else
        	{
           		for (int i=0; i<currentList.size(); i++)
        		{
        			currentSite = currentList.get(i);
        			if(currentSite.getTown_id().longValue() == site.getTown_id().longValue())
        			{
        				isFind = true;
        			}
        		}
        		if(isFind==false)
        		{
        			currentList.add(site);
            		//citymaps.put(String.valueOf(site.getCity_id()), currentList);
        		}
        		else
        		{
        			isFind = false;
        		}
        	}
        	
        	/*按province归类，但相同city的记录要去重*/
        	currentList = (List<Site>)provincemaps.get(String.valueOf(site.getProvince_id()));
        	if (/* 对应province id的记录没有，则*/null == currentList)
        	{
        		currentList = new ArrayList<Site>();
        		currentList.add(site);
        		provincemaps.put(String.valueOf(site.getProvince_id()), currentList);
        	}
        	else 
        	{
        		for (int i=0; i<currentList.size(); i++)
        		{
        			currentSite = currentList.get(i);
        			if(currentSite.getCity_id().longValue() == site.getCity_id().longValue())
        			{
        				isFind = true;
        			}
        		}
        		if(isFind==false)
        		{
        			currentList.add(site);
            		//provincemaps.put(String.valueOf(site.getProvince_id()), currentList);
        		}
        		else
        		{
        			isFind = false;
        		}
        		
        	}
        	
        }
     
        JSONObject jsonObject  = new JSONObject();
       
        if (siteList.isEmpty())
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "没有分配给阁下的站点!");
            return jsonObject;
        }
        
        /* 遍历三个map，生成树状json格式数据 */
        JSONArray jsonArray = new JSONArray();
        JSONObject  selOffObject = new JSONObject();
        selOffObject.put("checked", false);
        
        JSONObject  selOnObject = new JSONObject();
        selOnObject.put("checked", true);
        
        for(Map.Entry<String, Object> entryProvince : provincemaps.entrySet() )
        {
        	 JSONObject  provinceObject = new JSONObject();
             JSONArray   CitiesObject = new JSONArray();
             List<Site>  currentCityList;
             currentCityList = (List<Site>)entryProvince.getValue();
   
             provinceObject.put("id", currentCityList.get(0).getProvince_id() );
             provinceObject.put("text", currentCityList.get(0).getProvince_name());  //显示文字
             provinceObject.put("state", selOffObject );
             provinceObject.put("nodes", CitiesObject );  //下级节点

             JSONObject  CityObject = new JSONObject();
             JSONArray   TownsObject = new JSONArray();
             int cityCnt = currentCityList.size();
             for(int j=0; j < cityCnt; j++)
             {
               	 CityObject.put("id", currentCityList.get(0).getCity_id());
                 CityObject.put("text", currentCityList.get(0).getCity_name());
                 CityObject.put("zipCode", currentCityList.get(0).getZipCode());
                 CityObject.put("cityCode", currentCityList.get(0).getCityCode());
                 CityObject.put("state", selOffObject );
                 CityObject.put("nodes", TownsObject );
             

                 List<Site>  currentTownList = (List<Site>)citymaps.get(String.valueOf(currentCityList.get(0).getCity_id()));
                 
                 JSONObject  TownObject = new JSONObject();
                 JSONArray   SitesObject = new JSONArray();
                 int townCnt = currentTownList.size();
                 for(int k=0; k <townCnt; k++)
                 {
                	 TownObject.put("id", currentTownList.get(0).getTown_id());
                	 TownObject.put("text", currentTownList.get(0).getTown_name());
                	 TownObject.put("zipCode", currentTownList.get(0).getZipCode());
                	 TownObject.put("cityCode", currentTownList.get(0).getCityCode());
                	 TownObject.put("state", selOffObject );
                	 TownObject.put("nodes", SitesObject );
               	 
                	 List<Site>  currentSiteList = (List<Site>)townmaps.get(String.valueOf(currentTownList.get(0).getTown_id()));
                	 JSONObject  SiteObject = new JSONObject();
                	 JSONArray   DevicesObject = new JSONArray();
                     int sizeCnt = currentSiteList.size();
                	 for(int h=0; h < sizeCnt; h++)
                     {
                     	SiteObject.put("id", currentSiteList.get(0).getId());
                     	SiteObject.put("sn", currentSiteList.get(0).getSn());
                     	SiteObject.put("name", currentSiteList.get(0).getName());
                     	SiteObject.put("text", currentSiteList.get(0).getName());
                     	SiteObject.put("longitude", currentSiteList.get(0).getLongitude());
                     	SiteObject.put("latitude", currentSiteList.get(0).getLatitude());
                     	SiteObject.put("address", currentSiteList.get(0).getAddress());
                     	SiteObject.put("remark", currentSiteList.get(0).getRemark());
                     	SiteObject.put("createTime", TimeUtils.getFormatTimeStr(currentSiteList.get(0).getCreateTime()));
                     	SiteObject.put("province", currentSiteList.get(0).getProvince_name());
                     	SiteObject.put("provinceId", currentSiteList.get(0).getProvince_id());
                     	SiteObject.put("city", currentSiteList.get(0).getCity_name());
                     	SiteObject.put("cityId", currentSiteList.get(0).getCity_id());
                     	SiteObject.put("town", currentSiteList.get(0).getTown_name());
                     	SiteObject.put("townId", currentSiteList.get(0).getTown_id());
                   	
                     	SiteUser siteUserObj = new SiteUser(currentSiteList.get(0).getId(), userId);
                     	List<SiteUser> siteUserList= this.getSiteUserInfoBySiteIdAndUserId(siteUserObj);
            	    	if (siteUserList.isEmpty())
            	    	{
            	    		SiteObject.put("state", selOffObject);
            	    	}
            	    	else
            	    	{
            	    		SiteObject.put("state", selOnObject);
            	    	}
            	    	
            	    	/* 得到该站点下满足设备状态过滤条件的所有设备，并刷出来 */
            	    	List<Device> deviceList = this.findDeviceList(currentSiteList.get(0).getId(), filter);
            	    	if(deviceList != null)
            	    	{
            	    		if (deviceList.size() != 0/*该站点有符合条件的设备则添加节点*/)
                	    	{
                	    		SiteObject.put("nodes", DevicesObject);
                	    		JSONObject  DeviceObject = new JSONObject();
                	    		int deviceCnt = deviceList.size();
                	    		for(int m = 0; m < deviceCnt; m++)
                    	    	{
                   	    			DeviceObject.put("id", deviceList.get(0).getId());
                	    			DeviceObject.put("sn", deviceList.get(0).getSn());
                	    			DeviceObject.put("name", deviceList.get(0).getName());
                	    			DeviceObject.put("text", deviceList.get(0).getName());
                	    			DeviceObject.put("icon", "fa fa-circle");
                	    			DeviceObject.put("color", /*"#090"*/this.getDeviceColor(this.getfilterFromStatus(deviceList.get(0).getSn()))/*colorWarning*/);// todo
                	    			DeviceObject.put("type", deviceList.get(0).getType());
                	    			DeviceObject.put("typeText", CommonConsts.getModeTypeText(deviceList.get(0).getType()));
                	    			DeviceObject.put("band", deviceList.get(0).getBand());
                	    			DeviceObject.put("operator", deviceList.get(0).getOperator());
                	    			DeviceObject.put("operatorText", CommonConsts.getOperatorTypeText(Integer.valueOf(deviceList.get(0).getOperator())));
                	    			DeviceObject.put("manufacturer", deviceList.get(0).getManufacturer());
                	    			DeviceObject.put("remark", deviceList.get(0).getRemark());
                	    			DeviceObject.put("createTime", TimeUtils.getFormatTimeStr(deviceList.get(0).getCreateTime()));
                  	    			
                	    			DevicesObject.add( DeviceObject);
                	    			DeviceObject = new JSONObject();
                	    			deviceList.remove(0);
                    	    	}
                	    	}
            	    	}
            	    	
                     	SitesObject.add( SiteObject);
                     	SiteObject = new JSONObject();
                     	DevicesObject = new JSONArray();
                     	currentSiteList.remove(0);
                     }
                	 
                	 TownsObject.add( TownObject);
                	 TownObject = new JSONObject();
                	 SitesObject = new JSONArray();
                	 currentTownList.remove(0);
                 }
                 
                 CitiesObject.add( CityObject);
                 CityObject = new JSONObject();
                 TownsObject = new JSONArray();
                 currentCityList.remove(0);
             }
            
            CitiesObject = new JSONArray();
        	jsonArray.add(provinceObject);
       	
        }
        
        provincemaps.clear();
        citymaps.clear();
        townmaps.clear();
        
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return jsonObject;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 根据站点ID，过滤条件（在线、离线）获取设备列表
	 */
	public List<Device> findDeviceList(Long siteId, String filter)
	{
		List<Device> deviceList = null;
		String filterDevice;
		List<Device> deviceFilterList = new ArrayList<Device>();
		
		//获取站点ID对应的设备列表
		Map<String, Object> siteDeviceMaps = SigletonBean.siteDeviceMaps;
		deviceList = (List<Device>)siteDeviceMaps.get(String.valueOf(siteId));

		//若站点下不存在设备
		if (deviceList == null)
		{
			return new ArrayList<Device>(0);
		}
		
		logger.info(" siteId :" + siteId + " deviceList.size: " + deviceList.size() + " filter: "+ filter);
		/*if (filter.equals("all"))
		{
			
			return deviceList;
		}*/

		//若站点下存在设备
		if (deviceList.size() != 0)
		{
			for (int i=0; i < deviceList.size(); i++)
			{
				if (filter.matches("all"))
				{
					deviceFilterList.add(deviceList.get(i));
					continue;
				}
				
				if (filter.matches("notoffline"))
				{
					filterDevice = this.getfilterFromStatus(deviceList.get(i).getSn());
					if(filterDevice.equals("offline"))
					{
						continue;
					}
					else
					{
						deviceFilterList.add(deviceList.get(i));
						continue;
					}
				}
				
				filterDevice = this.getfilterFromStatus(deviceList.get(i).getSn());
				if (filterDevice.equals(filter))
				{
					deviceFilterList.add(deviceList.get(i));
				}
			}
		}
		
		return deviceFilterList;
	}

	/**
	 * 根据设备SN号获取设备状态
	 * @param deviceSn
	 * @return
	 */
	public String getfilterFromStatus(String deviceSn)
	{
		Map<String, Object> DeviceStatusMaps = SigletonBean.deviceStatusInfoMaps;
		
		DeviceStatus deviceStatus = (DeviceStatus)DeviceStatusMaps.get(deviceSn);
		
		return deviceStatus.getDeviceStatusCommon().getDeviceStatus();
	}

	//根据设备状态给与不同的颜色color
	 private String getDeviceColor(String deviceStatus)
	 {
		 String colorRun = "#090";
	     String colorOffline = "#999999";
	     String colorWillExpire = "#00ccff";
	     String colorExpiredFailure = "#ff0000";
	     String colorWarning = "#ff851b";
	     
	     String returnDeviceColor = null;
	     switch(deviceStatus)
	     {
	     	case "offline":
	     		returnDeviceColor = colorOffline;
	     		break;
	     	case "run":
	     		returnDeviceColor = colorRun;
	     		break;
	     	case "willexpire":
	     		returnDeviceColor = colorWillExpire;
	     		break;
	     	case "expiredfailure":
	     		returnDeviceColor = colorExpiredFailure;
	     		break;
	       	case "warning":
	     		returnDeviceColor = colorWarning;
	     		break;
	     	default:
	     		break;
	     }
	     
	     return returnDeviceColor;
	    
	 }

	 //根据设备状态给与不同的字体
	 public String getDeviceNameFont(String deviceStatus) {
         String colorRun = "#090";
         String colorOffline = "#999999";
         String colorWillExpire = "#00ccff";
         String colorExpiredFailure = "#ff0000";
         String colorWarning = "#ff851b";

         String returnDeviceFont = null;
         switch (deviceStatus) {
             case "offline":
                 returnDeviceFont = colorOffline;
                 break;
             case "run":
                 returnDeviceFont = colorRun;
                 break;
             case "willexpire":
                 returnDeviceFont = colorWillExpire;
                 break;
             case "expiredfailure":
                 returnDeviceFont = colorExpiredFailure;
                 break;
             case "warning":
                 returnDeviceFont = colorWarning;
                 break;
             default:
                 break;
         }

         return returnDeviceFont;
     }

	public JSONObject getDeviceStatusInfo(String sn) {

		JSONObject jsonObject = new JSONObject();

		DeviceStatus deviceStatus = (DeviceStatus)SigletonBean.deviceStatusInfoMaps.get(sn);
		if(deviceStatus == null){
			jsonObject.put("status", false);
			jsonObject.put("message", "成功");
			return jsonObject;
		}
		
        JSONObject dataObject = new JSONObject();
        JSONArray  normalArray = new JSONArray();
        JSONArray  boardArray = new JSONArray();
        JSONArray  licenseArray = new JSONArray();
        JSONArray  paStatusArray = new JSONArray();
        JSONArray  paInfoArray = new JSONArray();
        JSONArray  snifferArray = new JSONArray();
        JSONArray  debugArray = new JSONArray();
        JSONArray workModeArray = new JSONArray();

        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //一般信息
        JSONObject updateDataJo = new JSONObject();
        updateDataJo.put("label", "更新时间" );
        updateDataJo.put("value", TimeUtils.getFormatTimeStr(deviceStatus.getDeviceStatusCommon().getStatusUpdateTime()));

        normalArray.add( updateDataJo);
        JSONObject lineJo = new JSONObject();
        lineJo.put("label", "状态" );
        lineJo.put("value", deviceStatus.getDeviceStatusCommon().getDeviceStatus());
        normalArray.add( lineJo);
        JSONObject lineTimeJo = new JSONObject();
        if (deviceStatus.getDeviceStatusCommon().getDeviceStatus().equals("offline"))
        {
        	lineTimeJo.put("label", "设备运行时长" );
	        lineTimeJo.put("value", "掉线，未知!" );
        }
        else
        {
	        lineTimeJo.put("label", "设备运行时长" );
   	        lineTimeJo.put("value", deviceStatus.getDeviceStatusCommon().getDeviceRunTimeLen());
        }
        normalArray.add( lineTimeJo);
        
        JSONObject swVersionJo = new JSONObject();
        swVersionJo.put("label", "软件版本" );
        swVersionJo.put("value", deviceStatus.getDeviceStatusCommon().getSoftwareVersion());
        normalArray.add( swVersionJo);
        JSONObject fpgaVersionJo = new JSONObject();
        fpgaVersionJo.put("label", "FPGA版本" );
        fpgaVersionJo.put("value", deviceStatus.getDeviceStatusCommon().getFpgaVersion());
        normalArray.add( fpgaVersionJo);
        JSONObject bbuVersionJo = new JSONObject();
        bbuVersionJo.put("label", "BBU版本" );
        bbuVersionJo.put("value", deviceStatus.getDeviceStatusCommon().getBbuVersion());
        JSONObject boradTimeJo = new JSONObject();
        boradTimeJo.put("label", "校时设备时间" );
        boradTimeJo.put("value", TimeUtils.getFormatTimeStr(deviceStatus.getDeviceStatusCommon().getRcvBoardTime()));
        normalArray.add( boradTimeJo);
        JSONObject  serverTimeJo = new JSONObject();
        serverTimeJo.put("label", "服务校准时间" );
        serverTimeJo.put("value", deviceStatus.getDeviceStatusCommon().getSendServTime());
        normalArray.add( serverTimeJo);
        
        JSONObject  deviceStartReasonJo = new JSONObject();
        deviceStartReasonJo.put("label", "设备启动原因" );
        deviceStartReasonJo.put("value", deviceStatus.getDeviceStatusCommon().getReason());
        normalArray.add( deviceStartReasonJo);
        
       /* JSONObject  deviceAreaRuleSyncSendTimeJo = new JSONObject();
        deviceAreaRuleSyncSendTimeJo.put("label", "区域规则发送时间" );
        deviceAreaRuleSyncSendTimeJo.put("value", deviceStatus.getDeviceStatusCommon().getAreaRuleSyncSendTime());
        normalArray.add( deviceAreaRuleSyncSendTimeJo);
        
        JSONObject  deviceAreaRuleSyncSendSuccTimeJo = new JSONObject();
        deviceAreaRuleSyncSendSuccTimeJo.put("label", "区域规则同步成功时间" );
        deviceAreaRuleSyncSendSuccTimeJo.put("value", deviceStatus.getDeviceStatusCommon().getAreaRuleSyncSuccTime());
        normalArray.add( deviceAreaRuleSyncSendSuccTimeJo);
        
        JSONObject  deviceTargetRuleSyncSendTimeJo = new JSONObject();
        deviceTargetRuleSyncSendTimeJo.put("label", "黑名单规则发送时间" );
        deviceTargetRuleSyncSendTimeJo.put("value", deviceStatus.getDeviceStatusCommon().getTargetRuleSyncSendTime());
        normalArray.add( deviceTargetRuleSyncSendTimeJo);
        
        JSONObject  deviceTargetRuleSyncSendSuccTimeJo = new JSONObject();
        deviceTargetRuleSyncSendSuccTimeJo.put("label", "黑名单规则同步成功时间" );
        deviceTargetRuleSyncSendSuccTimeJo.put("value", deviceStatus.getDeviceStatusCommon().getTargetRuleSyncSuccTime());
        normalArray.add( deviceTargetRuleSyncSendSuccTimeJo);*/
     
        //设备板子信息
        JSONObject tempJo = new JSONObject();
        tempJo.put("label", "板子温度");
        tempJo.put("value", deviceStatus.getDeviceStatusEnviroment().getBoardTemperature());
        boardArray.add(tempJo);
        JSONObject cpuJo = new JSONObject();
        cpuJo.put("label", "CPU负载");
        cpuJo.put("value", deviceStatus.getDeviceStatusEnviroment().getCpuLoader());
        boardArray.add(cpuJo);
        JSONObject rfSwJo = new JSONObject();
        rfSwJo.put("label", "射频开关");
        if(deviceStatus.getDeviceStatusEnviroment().getRfEnable() != null)
        {
        	rfSwJo.put("value", this.getSwitchInfo(Integer.parseInt(deviceStatus.getDeviceStatusEnviroment().getRfEnable())));
        }
        boardArray.add(rfSwJo);

        //许可信息
        JSONObject licenceIsJo = new JSONObject();
        licenceIsJo.put("label", "是否过期");
        licenceIsJo.put("value", deviceStatus.getDeviceStatusLicense().getIsExpiration());
        licenseArray.add(licenceIsJo);
        JSONObject licenseTimeJo = new JSONObject();
        licenseTimeJo.put("label", "过期时间");
        licenseTimeJo.put("value", TimeUtils.getFormatTimeStr(deviceStatus.getDeviceStatusLicense().getExpirationTime()));
        licenseArray.add(licenseTimeJo);

        //工作模式信息
        JSONObject syncModeJo = new JSONObject();
		syncModeJo.put("label", "同步方式");
		syncModeJo.put("value", deviceStatus.getDeviceStatusWorkMode().getSyncMode());
		workModeArray.add(syncModeJo);
		JSONObject authTypeJo = new JSONObject();
		authTypeJo.put("label", "是否授权");
		authTypeJo.put("value", deviceStatus.getDeviceStatusWorkMode().getAuthType());
		workModeArray.add(authTypeJo);
		JSONObject deviceCodeJo = new JSONObject();
		deviceCodeJo.put("label", "设备编码");
		deviceCodeJo.put("value", deviceStatus.getDeviceStatusWorkMode().getDeviceCode());
		workModeArray.add(deviceCodeJo);
		JSONObject slaveBandJo = new JSONObject();
		slaveBandJo.put("label", "从载波band");
		slaveBandJo.put("value", deviceStatus.getDeviceStatusWorkMode().getSlaveBand());
		workModeArray.add(slaveBandJo);
		JSONObject masterBandJo = new JSONObject();
		masterBandJo.put("label", "主载波band");
		masterBandJo.put("value", deviceStatus.getDeviceStatusWorkMode().getMasterBand());
		workModeArray.add(masterBandJo);
		JSONObject permissionJo = new JSONObject();
		permissionJo.put("label", "工作的许可");
		permissionJo.put("value", deviceStatus.getDeviceStatusWorkMode().getPermission());
		workModeArray.add(permissionJo);

        //PA状态
        JSONObject validPaJo = new JSONObject();
        validPaJo.put("label", "是否有效");
        if (deviceStatus.getDeviceStatusPowerAmplifier().getValid() != null)
        {
        	validPaJo.put("value", this.getInvalidInfo(Integer.parseInt(deviceStatus.getDeviceStatusPowerAmplifier().getValid())));
        }
        paStatusArray.add(validPaJo);
        JSONObject warnPaJo = new JSONObject();
        warnPaJo.put("label", "故障告警");
        if (deviceStatus.getDeviceStatusPowerAmplifier().getWarn_pa() != null)
        {
        	warnPaJo.put("value", this.getAlarm(Integer.parseInt(deviceStatus.getDeviceStatusPowerAmplifier().getWarn_pa())));
        }
        paStatusArray.add(warnPaJo);
        JSONObject warn_standing_wave_ratioPaJo = new JSONObject();
        warn_standing_wave_ratioPaJo.put("label", "驻波比告警");
        if (deviceStatus.getDeviceStatusPowerAmplifier().getWarn_standing_wave_ratio() != null)
        {
        	warn_standing_wave_ratioPaJo.put("value", this.getAlarm(Integer.parseInt(deviceStatus.getDeviceStatusPowerAmplifier().getWarn_standing_wave_ratio())));
        }
        paStatusArray.add(warn_standing_wave_ratioPaJo);
        JSONObject warn_tempPaJo = new JSONObject();
        warn_tempPaJo.put("label", "过温告警");
        if (deviceStatus.getDeviceStatusPowerAmplifier().getWarn_temp() != null)
        {
        	warn_tempPaJo.put("value", this.getAlarm(Integer.parseInt(deviceStatus.getDeviceStatusPowerAmplifier().getWarn_temp())));
        }
        
        paStatusArray.add(warn_tempPaJo);
        JSONObject warn_powerPaJo = new JSONObject();
        warn_powerPaJo.put("label", "过功率告警");
        if (deviceStatus.getDeviceStatusPowerAmplifier().getWarn_power() != null)
        {
        	warn_powerPaJo.put("value", this.getAlarm(Integer.parseInt(deviceStatus.getDeviceStatusPowerAmplifier().getWarn_power())));
            
        }
        paStatusArray.add(warn_powerPaJo);
        JSONObject on_off_PaJo = new JSONObject();
        on_off_PaJo.put("label", "功放开关");
        if (deviceStatus.getDeviceStatusPowerAmplifier().getOn_off_Pa() != null)
        {
        	on_off_PaJo.put("value", this.getSwitchInfo(Integer.parseInt(deviceStatus.getDeviceStatusPowerAmplifier().getOn_off_Pa())));
        }
        paStatusArray.add(on_off_PaJo);
        JSONObject inverse_powerPaJo = new JSONObject();
        inverse_powerPaJo.put("label", "反向功率");
        inverse_powerPaJo.put("value", deviceStatus.getDeviceStatusPowerAmplifier().getInverse_power());
        paStatusArray.add(inverse_powerPaJo);
        JSONObject tempPaJo = new JSONObject();
        tempPaJo.put("label", "功放温度");
        tempPaJo.put("value", deviceStatus.getDeviceStatusPowerAmplifier().getTemp());
        paStatusArray.add(tempPaJo);
        JSONObject alcPaJo = new JSONObject();
        alcPaJo.put("label", "ALC");
        alcPaJo.put("value", deviceStatus.getDeviceStatusPowerAmplifier().getAlc_value());
        paStatusArray.add(alcPaJo);
        JSONObject standing_wave_ratioPaJo = new JSONObject();
        standing_wave_ratioPaJo.put("label", "驻波比");
        if (deviceStatus.getDeviceStatusPowerAmplifier().getStanding_wave_ratio() != null)
        {
        	standing_wave_ratioPaJo.put("value", Float.parseFloat(deviceStatus.getDeviceStatusPowerAmplifier().getStanding_wave_ratio())/10);
        }
        paStatusArray.add(standing_wave_ratioPaJo);
        JSONObject curr_att_PaJo = new JSONObject();
        curr_att_PaJo.put("label", "功放衰减");
        curr_att_PaJo.put("value", deviceStatus.getDeviceStatusPowerAmplifier().getCurr_att_Pa());
        paStatusArray.add(curr_att_PaJo);
        JSONObject forward_powerPaJo = new JSONObject();
        forward_powerPaJo.put("label", "前向功率");
        forward_powerPaJo.put("value", deviceStatus.getDeviceStatusPowerAmplifier().getForward_power());
        paStatusArray.add(forward_powerPaJo);
        JSONObject forward_power2PaJo = new JSONObject();
        forward_power2PaJo.put("label", "前向功率2");
        forward_power2PaJo.put("value", deviceStatus.getDeviceStatusPowerAmplifier().getForward_power_2());
        paStatusArray.add(forward_power2PaJo);

        //PA信息
        JSONObject paCountJo = new JSONObject();
        paCountJo.put("label", "功放个数");
        paCountJo.put("value", deviceStatus.getDevicePowerAmplifierInfo().getPaCnt());
        paInfoArray.add(paCountJo);
//        JSONObject validPaLaJo = new JSONObject();
//        validPaLaJo.put("label", "是否有效");
//        validPaLaJo.put("value", "");
//        paArray.add(validPaLaJo);
        JSONObject powerPaJo = new JSONObject();
        powerPaJo.put("label", "额定输出功率");
        powerPaJo.put("value", deviceStatus.getDevicePowerAmplifierInfo().getPower());
        paInfoArray.add(powerPaJo);
        JSONObject numPaJo = new JSONObject();
        numPaJo.put("label", "功放序号");
        numPaJo.put("value", deviceStatus.getDevicePowerAmplifierInfo().getPa_num());
        paInfoArray.add(numPaJo);
        JSONObject bandPaJo = new JSONObject();
        bandPaJo.put("label", "频带");
        bandPaJo.put("value", deviceStatus.getDevicePowerAmplifierInfo().getBand());
        paInfoArray.add(bandPaJo);
        JSONObject en485PaJo = new JSONObject();
        en485PaJo.put("label", "485接口");
        if (deviceStatus.getDevicePowerAmplifierInfo().getEn_485() != null)
        {
        	en485PaJo.put("value", this.get485StatusInfo(Integer.parseInt(deviceStatus.getDevicePowerAmplifierInfo().getEn_485())));
        }
        paInfoArray.add(en485PaJo);
        JSONObject addr485PaJo = new JSONObject();
        addr485PaJo.put("label", "485接口的地址");
        addr485PaJo.put("value", deviceStatus.getDevicePowerAmplifierInfo().getAddr_485());
        paInfoArray.add(addr485PaJo);
        JSONObject providerPaJo = new JSONObject();
        providerPaJo.put("label", "供应商");
        providerPaJo.put("value", deviceStatus.getDevicePowerAmplifierInfo().getProvider());
        paInfoArray.add(providerPaJo);
        JSONObject snPaJo = new JSONObject();
        snPaJo.put("label", "序列号");
        snPaJo.put("value", deviceStatus.getDevicePowerAmplifierInfo().getSn());
        paInfoArray.add(snPaJo);
        JSONObject factoryPaJo = new JSONObject();
        factoryPaJo.put("label", "出厂文件");
        factoryPaJo.put("value", deviceStatus.getDevicePowerAmplifierInfo().getFactory());
        paInfoArray.add(factoryPaJo);

        //sniffer信息
        JSONObject syncSnifferOpenJo = new JSONObject();
        syncSnifferOpenJo.put("label","sniffer是否开启");
        if(deviceStatus.getDeviceStatusSniffer().getValid() == 0)
        {
        	syncSnifferOpenJo.put("value","未开启");
        }
        else
        {
        	syncSnifferOpenJo.put("value","已开启");
        }
        snifferArray.add(syncSnifferOpenJo);
        
        JSONObject syncStatusJo = new JSONObject();
        syncStatusJo.put("label","同步状态");
       	syncStatusJo.put("value",CommonConsts.getSyncStatusInfo(deviceStatus.getDeviceStatusSniffer().getSyncStatus()));
        snifferArray.add(syncStatusJo);
        
        JSONObject powerJo = new JSONObject();
        powerJo.put("label","同步小区的功率");
        powerJo.put("value",deviceStatus.getDeviceStatusSniffer().getPower() + "dbm");
        snifferArray.add(powerJo);
        JSONObject pciJo = new JSONObject();
        pciJo.put("label","同步小区的PCI");
        pciJo.put("value",deviceStatus.getDeviceStatusSniffer().getPCI());
        snifferArray.add(pciJo);
        JSONObject frequencyJo = new JSONObject();
        frequencyJo.put("label","同步小区的频率");
        frequencyJo.put("value",deviceStatus.getDeviceStatusSniffer().getFrequency());
        snifferArray.add(frequencyJo);

        //debugger信息
        JSONObject ueNumJo = new JSONObject();
        ueNumJo.put("label", "numberOfConnectedUEs");
        ueNumJo.put("value", deviceStatus.getDeviceStatusDebug().getNumberOfConnectedUEs());
        debugArray.add(ueNumJo);
        JSONObject isDspHearbeatRecvJo = new JSONObject();
        isDspHearbeatRecvJo.put("label", "isDspHearbeatRecv");
        isDspHearbeatRecvJo.put("value", deviceStatus.getDeviceStatusDebug().getIsDspHearbeatRecv());
        debugArray.add(isDspHearbeatRecvJo);
        JSONObject rateMsg4PkMsg3Jo = new JSONObject();
        rateMsg4PkMsg3Jo.put("label", "RateMsg4PkMsg3");
        rateMsg4PkMsg3Jo.put("value", deviceStatus.getDeviceStatusDebug().getRateMsg4PkMsg3() );
        debugArray.add(rateMsg4PkMsg3Jo);
        JSONObject rateMsg5PkMsg4Jo = new JSONObject();
        rateMsg5PkMsg4Jo.put("label", "RateMsg5PkMsg4");
        rateMsg5PkMsg4Jo.put("value", deviceStatus.getDeviceStatusDebug().getRateMsg5PkMsg4() );
        debugArray.add(rateMsg5PkMsg4Jo);
        JSONObject countRACHJo = new JSONObject();
        countRACHJo.put("label", "count_RACH");
        countRACHJo.put("value", deviceStatus.getDeviceStatusDebug().getCount_RACH() );
        debugArray.add(countRACHJo);
        JSONObject countSchelMSG3Jo = new JSONObject();
        countSchelMSG3Jo.put("label", "count_schel_MSG3");
        countSchelMSG3Jo.put("value", deviceStatus.getDeviceStatusDebug().getCount_schel_MSG3() );
        debugArray.add(countSchelMSG3Jo);
        JSONObject countMSG3SuccessJo = new JSONObject();
        countMSG3SuccessJo.put("label", "count_MSG3_success");
        countMSG3SuccessJo.put("value", deviceStatus.getDeviceStatusDebug().getCount_MSG3_success() );
        debugArray.add(countMSG3SuccessJo);
        JSONObject countMSG4 = new JSONObject();
        countMSG4.put("label", "count_MSG4");
        countMSG4.put("value", deviceStatus.getDeviceStatusDebug().getCount_MSG4() );
        debugArray.add(countMSG4);
        JSONObject countMSG5Jo = new JSONObject();
        countMSG5Jo.put("label", "count_MSG5");
        countMSG5Jo.put("value", deviceStatus.getDeviceStatusDebug().getCount_MSG5() );
        debugArray.add(countMSG5Jo);
        JSONObject countUEL3Jo = new JSONObject();
        countUEL3Jo.put("label", "count_UE_L3");
        countUEL3Jo.put("value", deviceStatus.getDeviceStatusDebug().getCount_UE_L3());
        debugArray.add(countUEL3Jo);

        dataObject.put("normal", normalArray);
        dataObject.put("board",boardArray );
        dataObject.put("license", licenseArray );
        dataObject.put("paInfo", paInfoArray );
        dataObject.put("paStatus", paStatusArray );
        dataObject.put("sniffer",snifferArray );
        dataObject.put("debug", debugArray);
        dataObject.put("workmode", workModeArray);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);
        return  jsonObject;
		
	}

	public  JSONObject getSiteStatusInfo( String sn ){

        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONArray  deviceArray  = new JSONArray();
        
        JSONObject deviceObject = null;
        JSONArray  dvStatusJo = null;

        //SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> siteSnSiteMaps = SigletonBean.siteSnSiteMaps;
        Site site = (Site)siteSnSiteMaps.get(sn);
        if (site == null)
        {
        	 jsonObject.put("status", false);
             jsonObject.put("message", "失败");
             jsonObject.put("data", null);
             return jsonObject;
        }
        
        Map<String, Object> siteIdDeviceMaps = SigletonBean.siteDeviceMaps;
		@SuppressWarnings("unchecked")
		List<Device> devicesList = (List<Device>)(siteIdDeviceMaps.get(site.getId().toString()));
		
		Map<String, Object> dvStatausMaps = SigletonBean.deviceStatusInfoMaps;
		DeviceStatus deviceStatus; 
		//for(Device device: devicesList)
		for (int i=0; i< devicesList.size(); i++)
		{
			deviceObject = new JSONObject();
			dvStatusJo = new JSONArray();
			//一般信息
			deviceStatus = (DeviceStatus)dvStatausMaps.get(devicesList.get(i).getSn());
			
	        JSONObject updateDataJo = new JSONObject();
	        updateDataJo.put("label", "更新时间" );
	        updateDataJo.put("value", TimeUtils.getFormatTimeStr(deviceStatus.getDeviceStatusCommon().getStatusUpdateTime()));
	        dvStatusJo.add( updateDataJo);
	        
	        JSONObject lineJo = new JSONObject();
	        lineJo.put("label", "状态" );
	        lineJo.put("value", deviceStatus.getDeviceStatusCommon().getDeviceStatus());
	        dvStatusJo.add( lineJo);
	        
	        JSONObject lineTimeJo = new JSONObject();
	        if (deviceStatus.getDeviceStatusCommon().getDeviceStatus().equals("offline"))
	        {
	   	        lineTimeJo.put("label", "设备运行时长" );
		        lineTimeJo.put("value", "掉线，未知!" );
	        }
	        else
	        {
	        	lineTimeJo.put("label", "设备运行时长" );
	   	        lineTimeJo.put("value", deviceStatus.getDeviceStatusCommon().getDeviceRunTimeLen());
	   	    }
	        
	        dvStatusJo.add( lineTimeJo);
	        
	        JSONObject swVersionJo = new JSONObject();
	        swVersionJo.put("label", "软件版本" );
	        swVersionJo.put("value", deviceStatus.getDeviceStatusCommon().getSoftwareVersion()); 
	        dvStatusJo.add( swVersionJo);
	        
	        JSONObject fpgaVersionJo = new JSONObject();
	        fpgaVersionJo.put("label", "FPGA版本" );
	        fpgaVersionJo.put("value", deviceStatus.getDeviceStatusCommon().getFpgaVersion());
	        dvStatusJo.add( fpgaVersionJo);
	        
	        JSONObject bbuVersionJo = new JSONObject();
	        bbuVersionJo.put("label", "BBU版本" );
	        bbuVersionJo.put("value", deviceStatus.getDeviceStatusCommon().getBbuVersion());
	        dvStatusJo.add( bbuVersionJo);

	        //设备板子信息
	        JSONObject tempJo = new JSONObject();
	        tempJo.put("label", "板子温度");
	        tempJo.put("value", deviceStatus.getDeviceStatusEnviroment().getBoardTemperature());
	        dvStatusJo.add(tempJo);
	        JSONObject cpuJo = new JSONObject();
	        cpuJo.put("label", "CPU负载");
	        cpuJo.put("value", deviceStatus.getDeviceStatusEnviroment().getCpuLoader());
	        dvStatusJo.add(cpuJo);
	        JSONObject rfSwJo = new JSONObject();
	        rfSwJo.put("label", "射频开关");
	        if (deviceStatus.getDeviceStatusEnviroment().getRfEnable() != null)
	        {
	        	rfSwJo.put("value", this.getSwitchInfo(Integer.parseInt(deviceStatus.getDeviceStatusEnviroment().getRfEnable())));
		    }
	        dvStatusJo.add(rfSwJo);

	        //许可信息
	        JSONObject licenceIsJo = new JSONObject();
	        licenceIsJo.put("label", "是否过期");
	        licenceIsJo.put("value", deviceStatus.getDeviceStatusLicense().getIsExpiration());
	        dvStatusJo.add(licenceIsJo);
	        JSONObject licenseTimeJo = new JSONObject();
	        licenseTimeJo.put("label", "过期时间");
	        licenseTimeJo.put("value", TimeUtils.getFormatTimeStr(deviceStatus.getDeviceStatusLicense().getExpirationTime()));
	        dvStatusJo.add(licenseTimeJo);

	        //sniffer信息
	        JSONObject syncSnifferOpenJo = new JSONObject();
	        syncSnifferOpenJo.put("label","sniffer是否开启");
	        if(deviceStatus.getDeviceStatusSniffer().getValid() == 0)
	        {
	        	syncSnifferOpenJo.put("value","未开启");
	        }
	        else
	        {
	        	syncSnifferOpenJo.put("value","已开启");
	        }
	        dvStatusJo.add(syncSnifferOpenJo);
	        
	        JSONObject syncStatusJo = new JSONObject();
	        syncStatusJo.put("label","同步状态");
	        syncStatusJo.put("value",CommonConsts.getSyncStatusInfo(deviceStatus.getDeviceStatusSniffer().getSyncStatus()));
	        dvStatusJo.add(syncStatusJo);

	        deviceObject.put("id", devicesList.get(i).getId());
	        deviceObject.put("sn", devicesList.get(i).getSn());
	        deviceObject.put("name", devicesList.get(i).getName());
	        deviceObject.put("status", dvStatusJo );
	        deviceArray.add(deviceObject);
	        

		}
		
		dataObject.put("id", site.getId());
        dataObject.put("sn", sn);
        dataObject.put("name", site.getName());
        // dataObject.put("status", );
        if(devicesList.size() == 0)
        {
        	dataObject.put("devices", null);
        }
        else
        {
        	dataObject.put("devices", deviceArray);
        }
        
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);
        
        return  jsonObject;
    }
	
	private String getInvalidInfo(int valid)
	{
		String invalidInfo = "未知状态";
		switch(valid)
		{
		case 0:
			invalidInfo = "无效";
			break;
		case 1:
			invalidInfo = "有效";
			break;
		case 2:
			invalidInfo = "485连接异常";
			break;
		default:
		 break;
		}
		
		return 	invalidInfo;	
	}
	
	private String getAlarm(int warn)
	{
		String isAlarm = "未知状态";
		switch(warn)
		{
		case 0:
			isAlarm = "正常";
			break;
		case 1:
			isAlarm = "告警";
			break;
		default:
			break;
		}
		return isAlarm;
		
	}
	
	private String getSwitchInfo(int swtich)
	{
		String paSwtichInfo = "未知状态";
		switch(swtich)
		{
		case 0:
			paSwtichInfo = "关";
			break;
		case 1:
			paSwtichInfo = "开";
			break;
		default:
			break;	
			
		}
		
		return paSwtichInfo;
		
	 }
	
	private String get485StatusInfo(int status)
	{
		String Status485Info = "未知状态";
		switch(status)
		{
		case 0:
			Status485Info = "未开启485接口";
			break;
		case 1:
			Status485Info = "已开启485接口";
			break;
		default:
			break;	
			
		}
		
		return Status485Info;
	}

	public List<Site> getAllSiteListInfo() {
	    List<Site>  siteList =(List<Site>) siteMapper.selectAllSiteList();
        //User user=null;
        return siteList;
	}

	public List<Device>  getDevicesByUseId(Long userId)
	{
		List<Device>  deviceList =(List<Device>) deviceMapper.selectUserDevices(userId);
		//User user=null;
		return deviceList;
	}
	
	public boolean deviceIsExist(String sn)
	{
		DeviceStatus deviceStatus = (DeviceStatus)SigletonBean.deviceStatusInfoMaps.get(sn);
		Device device;
		boolean dvIsReg = false;
		if (deviceStatus == null)
		{
			device = deviceMapper.selectByDevSN(sn);
			if(device != null && !device.equals(""))
			{
				dvIsReg = true;
			}
			else
			{
				dvIsReg = false;
			}
		}
		else
		{
			dvIsReg = true;
		}
		return dvIsReg;
	}
	
	@SuppressWarnings("unchecked")
	public boolean deviceIsBelongToUser(long userId, String devSn)
	{
		List<Long> userIdList = null;
		Map<String, Object> dvUserMaps = SigletonBean.deviceUserMaps;
		userIdList = (ArrayList<Long>)dvUserMaps.get(devSn);
		if(userIdList == null)
		{
			logger.info("device " + devSn + " not assigned to any device");
			return false;
		}
		for(int i =0; i < userIdList.size(); i++)
		{
			Long findUserId =  userIdList.get(i);
			if(findUserId.equals(new Long(userId)))
			{
				return true;
			}
		}
		
		return false;
	}
	
	 
	
	
}

