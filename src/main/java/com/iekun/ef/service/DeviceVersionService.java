package com.iekun.ef.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.UpgradeMapper;
import com.iekun.ef.dao.VersionLibMapper;
import com.iekun.ef.jms.service.OamMessageService;
import com.iekun.ef.jms.vo.send.SendLicenseNotify;
import com.iekun.ef.jms.vo.send.SendUpgradeNotify;
import com.iekun.ef.model.AreaRule;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceAlarm;
import com.iekun.ef.model.DeviceLicense;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.Upgrade;
import com.iekun.ef.model.VersionLib;
import com.iekun.ef.util.Md5;
import com.iekun.ef.util.TimeUtils;

@Service
public class DeviceVersionService {

	private static Logger logger = LoggerFactory.getLogger(DeviceVersionService.class);
	
	@Autowired
	private VersionLibMapper versionLibMapper;
	
    @Autowired
    private SystemParaService sysParaServ;
    
	@Autowired
	private UpgradeMapper upgradeMapper;
	
	@Autowired
	private OamMessageService oamMsgServ;
	
	@Autowired
	private DeviceService devServ;
	
	@Autowired
	private StorageService storageServ;
	
	//@Value(" ${ftpserver.port}")
	private String ftpServerPort;
	
	//@Value(" ${ftpserver.ip}")
	private String ftpServerIp;
	
    @Value(" ${storage.upgradePath}")
    private String upgradePathStr;
    
    @Value(" ${ftpserver.user}")
    private String ftpUser;
    
    @Value(" ${ftpserver.pwd}")
    private String ftpPwd;
	
	public JSONObject getLibraries()
	{

		boolean status = false;
		JSONObject jo=new JSONObject();
	    JSONArray ja = new JSONArray();
		
		List<VersionLib>  versionLibList =(List<VersionLib>) versionLibMapper.getVersionLibList();
		if (versionLibList.size() == 0)
		{
			logger.info("版本库为空");
		}
		
		for(VersionLib versionLib:versionLibList)
		{
			JSONObject versionLibJo = new JSONObject();
					
			versionLibJo.put("id", versionLib.getId());
			versionLibJo.put("version", versionLib.getVersion());
			versionLibJo.put("remark", versionLib.getRemark());
			versionLibJo.put("fpgaVersion", versionLib.getFpgaVersion());
			versionLibJo.put("bbuVersion", versionLib.getBbuVersion());
			versionLibJo.put("swVersion", versionLib.getSwVersion());
			String userId = versionLib.getUploadUser();
			String userName = versionLibMapper.queryUserNameById(userId);
			versionLibJo.put("uploadUser", userName);
			versionLibJo.put("uploadTime", TimeUtils.getFormatTimeStr(versionLib.getUploadTime()));
		        
			ja.add(versionLibJo);
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
		
	public boolean createLibrary(String fileName, String uploadFilename, String version, String remark, 
			String fpgaVersion, String bbuVersion,String swVersion) 
	{
		/* 创建版本 ;包括两步   1.上传版本文件并保存到系统指定的目录(可以由配置文件指定) 2.生成版本库记录   */
		boolean isDeviceVer = true;
		String fileUrl = null;
		String Signature = null;
		boolean returnVal = false;
		Map<String,Object> params=new HashMap<String, Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		//String userName = SigletonBean.getUserName();
		Date updateDate = new Date();
		params.put("uploadTime", df.format(updateDate));
		params.put("uploadUser", SigletonBean.getUserId());
		if (fileName == null || fileName.isEmpty())
		{
		    	params.put("fileName", null);
		}
		else
		{
			params.put("fileName", fileName);
		}
		
		if (uploadFilename == null || uploadFilename.isEmpty())
		{
		    	params.put("uploadFilename", null);
		}
		else
		{
			params.put("uploadFilename", uploadFilename);
		}
		
		if (version == null || version.isEmpty())
		{
		    	params.put("version", null);
		}
		else
		{
			params.put("version", version);
		}
		
		if (remark == null || remark.isEmpty())
		{
		    	params.put("remark", null);
		}
		else
		{
			params.put("remark", remark);
		}
		if (fpgaVersion == null || fpgaVersion.isEmpty())
		{
		    	params.put("fpgaVersion", null);
		}
		else
		{
			params.put("fpgaVersion", version);
		}
		if (bbuVersion == null || bbuVersion.isEmpty())
		{
		    	params.put("bbuVersion", null);
		}
		else
		{
			params.put("bbuVersion", bbuVersion);
		}
		if (swVersion == null || swVersion.isEmpty())
		{
		    	params.put("swVersion", null);
		}
		else
		{
			params.put("swVersion", swVersion);
		}
		
		if (params.get("fileName") == null || params.get("uploadFilename") == null)
		{
			 returnVal = false;
			 //params.put("fileUrl", fileUrl);
		}
		fileUrl = storageServ.moveTempFile2UpgradePath(uploadFilename, fileName, isDeviceVer);
		params.put("fileUrl", upgradePathStr + "/deviceSN" + "/" + fileName);
		params.put("deleteFlag", 0);
		
		//params.put("ftpServerPort", ftpServerPort);
	
		Md5 md5 = new Md5();
		File big = new File(fileUrl +"/"+ fileName);
		try {
			Signature = md5.getFileMD5String(big);
			params.put("checksum", Signature);
			returnVal = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		try {
			Runtime.getRuntime().exec("chmod 777 " + fileUrl +"/"+ fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Date createDate = new Date();
		params.put("createTime", df.format(createDate));	
		
		try {
			
			versionLibMapper.insertVersionLib(params);
			returnVal = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnVal;
	}
	
	
	public boolean delLibrary(Long id) 
	{
		/* 删除版本 ,只是软删除verisonlib中对应的记录, 对应的版本文件不删除，由后台任务统一清理  */
		boolean returnVal =false;
		try {
			versionLibMapper.deleteLibByPrimaryKey(id);
			returnVal = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnVal;
	}
	
	
	public boolean upgradeDevices(Long versionId,  List<String>  snList)
	{
		/*  生成升级记录，插入升级记录表。  */
		boolean returnval = true;
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		for(int i = 0; i < snList.size(); i++)
		{
			try {
					
				this.insertVerUpdateInfo(versionId, snList.get(i));
					
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				returnval = false;
			}
		}
		this.devVersionNotifySend();
		return returnval;
	}
	
	/*本函数两种调用场景：1、创建升级任务的时候会被调用一次；2：定时任务定时执行的时候会被调用 */
	public void devVersionNotifySend()
	{
		/*1. 查找数据库，得到未成功收到响应complete消息的且尝试次数小于最大尝试次数的记录 ；2、满足条件记录的device逐个发送, 3. 逐个设备升级  */

		Map<String,Object> params=new HashMap<String, Object>();
		SendUpgradeNotify sendUpgradeNotifyPara;
		int maxSendTimes = 3;
		byte tries = 1;
  		params.put("maxRetries", maxSendTimes);
		params.put("status", 0);
		
		ftpServerPort = (sysParaServ.getSysPara("ftpPort")).trim();
		ftpServerIp = (sysParaServ.getSysPara("ftpIp")).trim();
		
		/*检查升级任务表，所有升级超过三次都不再尝试升级 */
		this.setUpgradBybondMaxTries();
		
		List<Upgrade>  upgradeList =(List<Upgrade>) upgradeMapper.selectUnsuccDvVerList(params);
		logger.info("id " + "devVersionNotifySend  :" + upgradeList.size());
	    for (Upgrade upgrade : upgradeList) {
	    	
	    	/* 更新尝试发送的次数 ,调度时间，更新时间  */
	    	tries = upgrade.getRetrties();
	    	tries = (byte)(tries +1);
	    	Byte aa = new Byte(tries);
	    	upgrade.setRetrties(aa);
	    	
	    	upgrade.setScheduleTime(TimeUtils.timeFormatterStr.format( new Date()));
	    	upgrade.setUpdateTime(TimeUtils.timeFormatterStr.format( new Date()));
	    	
	    	try {
				upgradeMapper.updateByPrimaryKeySelective(upgrade);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	/* 发送notify 消息*/
	    	sendUpgradeNotifyPara = new SendUpgradeNotify();
	    	sendUpgradeNotifyPara.setFilePath((upgrade.getFileUrl()).trim());
			sendUpgradeNotifyPara.setUsername(ftpUser.trim());
			sendUpgradeNotifyPara.setVersion(upgrade.getVersion());
			sendUpgradeNotifyPara.setType("0");
			sendUpgradeNotifyPara.setTime(TimeUtils.timeFormatterStr.format( new Date()));
			sendUpgradeNotifyPara.setFtpIp(ftpServerIp.trim());
			sendUpgradeNotifyPara.setFtpPort(ftpServerPort.trim());
			sendUpgradeNotifyPara.setSignature(upgrade.getCheckSum());
			sendUpgradeNotifyPara.setProtocol("0");
			sendUpgradeNotifyPara.setPassword(ftpPwd.trim());
			
			
			logger.info("version " + upgrade.getVersion());
			logger.info("checksum " + upgrade.getCheckSum());
			oamMsgServ.setUpgradeNotify(upgrade.getDeviceSn(), sendUpgradeNotifyPara);
	    
		 }
	
	}
	
	private void setUpgradBybondMaxTries()
	{
		Map<String,Object> params=new HashMap<String, Object>();
		
		params.put("result", "3次尝试下载，均失败!");
		params.put("statusFlag", 4);
		params.put("updateTime", TimeUtils.timeFormatterStr.format( new Date()));
		params.put("maxTryTimes", 3);
			
		try {
			upgradeMapper.updateUpgradeOfMaxTryTimes(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void insertVerUpdateInfo(Long versionId, String dvSn)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Device device;
		Byte statusFlag = 0;
		Byte retries = 0;
		device = (Device)SigletonBean.deviceSiteInfoMaps.get(dvSn);
			
	    Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
		Upgrade upgrade = new Upgrade();
		upgrade.setCreateTime(df.format(new Date()));
		upgrade.setDeviceSn(dvSn);
		upgrade.setSiteName(device.getSite().getName());
		upgrade.setVersionId(versionId);
		upgrade.setStatusFlag(statusFlag);
		upgrade.setRetrties(retries);
		
		DeviceStatus deviceStatus = (DeviceStatus)dvStatusMaps.get(dvSn);
		if (deviceStatus != null)
		{
			if (deviceStatus.getDeviceStatusCommon()!= null)
			{
				upgrade.setOldVersion(deviceStatus.getDeviceStatusCommon().getSoftwareVersion());
				upgrade.setOldSwVersion(deviceStatus.getDeviceStatusCommon().getSoftwareVersion());
				upgrade.setOldFpgaVersion(deviceStatus.getDeviceStatusCommon().getFpgaVersion());
				upgrade.setOldBbuVersion(deviceStatus.getDeviceStatusCommon().getBbuVersion());
			}
			
		}
		try {
			upgradeMapper.insert(upgrade);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
     
	}
	
	/* 以下两个方法查询t_upgrade 表*/
	public JSONObject getDevVerList_old()
	{
		/* 查询设备当前版本记录 */
		boolean status = false;
		JSONObject jo=new JSONObject();
	    JSONArray ja = new JSONArray();
		Device device = null;
		
		List<Upgrade>  upgradeList =(List<Upgrade>) upgradeMapper.getUpgradeList();
		if (upgradeList.size() == 0)
		{
			logger.info("没找到设备升级记录");
		}
		
		for(Upgrade upgrade:upgradeList)
		{
			JSONObject upgradeJo = new JSONObject();
			device = (Device)SigletonBean.deviceSiteInfoMaps.get(upgrade.getDeviceSn());
			if(device == null)
			{
				logger.info("device =" +  upgrade.getDeviceSn() + "的设备已经被删除");
				continue;
			}
			upgradeJo.put("siteName", upgrade.getSiteName());
			upgradeJo.put("deviceSN", upgrade.getDeviceSn());
			upgradeJo.put("deviceName", device.getName());
			upgradeJo.put("version", upgrade.getOldBbuVersion());
			upgradeJo.put("fpgaVersion", upgrade.getOldFpgaVersion());
			upgradeJo.put("bbuVersion", upgrade.getOldBbuVersion());
			upgradeJo.put("swVersion", upgrade.getOldSwVersion());
			
			ja.add(upgradeJo);
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
	
	/*@SuppressWarnings("unchecked")
	public boolean deviceIsBelongToUser(long userId, String devSn)
	{
		List<Long> userIdList = null;
		Map<String, Object> dvUserMaps = SigletonBean.deviceUserMaps;
		userIdList = (ArrayList<Long>)dvUserMaps.get(devSn);
		for(int i =0; i < userIdList.size(); i++)
		{
			Long findUserId =  userIdList.get(i);
			if(findUserId.equals(new Long(userId)))
			{
				return true;
			}
		}
		
		return false;
	}*/
	
	public JSONObject getDevVerList()
	{
		/* 查询设备当前版本记录 */
		boolean status = false;
		JSONObject jo=new JSONObject();
	    JSONArray ja = new JSONArray();
		DeviceStatus deviceStatus;
		Device deviceDetail;
				
		  Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
		  //SigletonBean.getUserId()
	        for(Map.Entry<String, Object> entry:dvStatusMaps.entrySet())
	        {
	        	JSONObject upgradeJo = new JSONObject();
	        	
	        	if(devServ.deviceIsBelongToUser(SigletonBean.getUserId(), entry.getKey()) == false)
	        	{
	        		continue;
	        	}
	        	deviceStatus = (DeviceStatus)(entry.getValue());
	        	deviceDetail = (Device)SigletonBean.deviceSiteInfoMaps.get(entry.getKey());
	        	
	        	if (deviceDetail != null)
	        	{
	        		//List<Device>  deviceList = devServ.getDeviceListInfoBySiteId(deviceDetail.getSiteId()); 
	        		upgradeJo.put("deviceName", deviceDetail.getName());
	        		if(deviceDetail.getSite() != null)
	        		{
	        			upgradeJo.put("siteName", deviceDetail.getSite().getName());
	        		}
	        		else
	        		{
	        			upgradeJo.put("siteName", null);
	        		}
	        		
	        	}
	        	else
	        	{
	        		upgradeJo.put("siteName", null);
	        		upgradeJo.put("deviceName", null);
	        	}
	        	
	        	upgradeJo.put("deviceSN", entry.getKey());
	        	
	        	upgradeJo.put("version",deviceStatus.getDeviceStatusCommon().getSoftwareVersion());
	        	upgradeJo.put("fpgaVersion", deviceStatus.getDeviceStatusCommon().getFpgaVersion());
	        	upgradeJo.put("bbuVersion", deviceStatus.getDeviceStatusCommon().getBbuVersion());
	        	upgradeJo.put("swVersion", deviceStatus.getDeviceStatusCommon().getSoftwareVersion());
				
	        	ja.add(upgradeJo);
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
   
    public JSONObject  queryHistoryData(Integer draw, Integer length, Integer start, String startDate, String endDate,
    		String deviceSN, String version, String upgradeStatus)
    {
    	long totalCnt = 0;
    	Device device;
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
        	deviceSN = null ;
        }
        else
        {
        	params.put("deviceSn", deviceSN);
        }
        
        if (version == null || version.isEmpty() || version.equals("-1"))
        {
        	params.put("version", null);
        }
        else
        {
        	params.put("version", version);
        }
        
        if (upgradeStatus == null || upgradeStatus.isEmpty()||upgradeStatus.equals("0"))
        {
        	params.put("status", null);
        }
        else
        {
        	 params.put("status", upgradeStatus);
        }
        
        if (deviceSN == null )
        {
	        Long userId = SigletonBean.getUserId();
	        List<Site> sites= devServ.getSiteListInfoByUserId(userId);
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
        	List<Upgrade>  upgradeList =(List<Upgrade>) upgradeMapper.selectUpgradeList(params);
        	if (upgradeList.size() == 0)
    		{
    			logger.info("没找到设备升级记录");
    		}
		    for (Upgrade upgrade : upgradeList) {
		    	JSONObject upgradeJo = new JSONObject();
		    	device = (Device)SigletonBean.deviceSiteInfoMaps.get(upgrade.getDeviceSn());
				if(device == null)
				{
					logger.info("device =" +  upgrade.getDeviceSn() + "的设备已经被删除");
					continue;
				}
				
				/*if(devServ.deviceIsBelongToUser(SigletonBean.getUserId(), upgrade.getDeviceSn()) == false)
	        	{
	        		continue;
	        	}*/
		    	upgradeJo.put("siteName", upgrade.getSiteName());
				upgradeJo.put("deviceSN", upgrade.getDeviceSn());
				upgradeJo.put("deviceName", device.getName());
				upgradeJo.put("upgradeTime", upgrade.getCreateTime());
				upgradeJo.put("successTime", upgrade.getSuccessTime());
				upgradeJo.put("oldVersion", upgrade.getOldVersion());
				upgradeJo.put("newVersion", upgrade.getVersion());
				upgradeJo.put("status", this.getStatuInfoByFlag(upgrade.getStatusFlag()));
				
				ja.add(upgradeJo);
    	        status = true;
			}
		    if(status == true)
	        {
		        	
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
			totalCnt = (long)upgradeMapper.getUpgradeCnt(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return totalCnt;
	}
    
    private String getStatuInfoByFlag(Byte Flag)
    {
    	String statusInfo = "未知状态信息"; 
    	switch(Flag)
    	{
    		/*0- OPEN；1- FREEZE；2- REOPEN；3-CLOSE*/
    		case 0:
    			statusInfo = "升级中";
    			break;
     		case 1:
    			statusInfo = "冻结";
    			break;
    		case 2:
    			statusInfo = "重新开启";
    			break;
    		case 3:
    			statusInfo = "成功";
    			break;
    		case 4:
    			statusInfo = "失败";
    			break;
    		default:
    			break;
    	}
    	return statusInfo;
    }
	
	
}
