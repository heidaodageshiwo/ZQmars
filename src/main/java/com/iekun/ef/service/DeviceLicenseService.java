package com.iekun.ef.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.DeviceLicenseMapper;
import com.iekun.ef.jms.service.OamMessageService;
import com.iekun.ef.jms.vo.send.SendLicenseNotify;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceLicense;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.Site;
import com.iekun.ef.util.Md5;
import com.iekun.ef.util.TimeUtils;


@Service
public class DeviceLicenseService {

private static Logger logger = LoggerFactory.getLogger(DeviceLicenseService.class);
	
	@Autowired
	private DeviceLicenseMapper deviceLicenseMapper;
	
	@Autowired
	private OamMessageService oamMsgServ;
	
	@Autowired
	private StorageService storageServ;
	
	@Autowired
	private DeviceService devServ;
	
	
	@Value(" ${ftpserver.port}")
	private String ftpServerPort;
	
	@Value(" ${ftpserver.ip}")
	private String ftpServerIp;
	
	@Value("${storage.licensePath}")
	private String licensePath;
	
    @Value(" ${ftpserver.user}")
	private String ftpUser;
	    
	@Value(" ${ftpserver.pwd}")
	private String ftpPwd;
	
	public JSONObject getDevLicenses()
	{
		boolean status = false;
		JSONObject jo=new JSONObject();
	    JSONArray ja = new JSONArray();
	    Device device;
	    DeviceStatus deviceStatus;
	    String dvSn;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date nowDate =  new Date();
		String noDateStr = sdf.format(nowDate);
		
		Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
		    
		for(Map.Entry<String, Object> entry:dvStatusMaps.entrySet())
		{
			deviceStatus = (DeviceStatus)(entry.getValue());
		   	dvSn  = (entry.getKey());
		   	
		   	if(devServ.deviceIsBelongToUser(SigletonBean.getUserId(), dvSn) == false)
        	{
        		continue;
        	}
		   	
			JSONObject dvLicenseJo = new JSONObject();
			device = (Device)SigletonBean.deviceSiteInfoMaps.get(dvSn);
			if(device.getSite() == null)
			{
				dvLicenseJo.put("siteName", "未知站点");
			}
			else
			{
				dvLicenseJo.put("siteName", device.getSite().getName());
			}
			
			dvLicenseJo.put("deviceSN", dvSn);
			dvLicenseJo.put("deviceName",device.getName());
			if ((deviceStatus.getDeviceStatusLicense() == null) || deviceStatus.getDeviceStatusLicense().getExpirationTime() == null)
			{
				continue;
			}
			if (noDateStr.compareTo( deviceStatus.getDeviceStatusLicense().getExpirationTime()) >= 0)
			{
				dvLicenseJo.put("isLicense", 1); //0-没过期，1-过期
			}
			else
			{
				dvLicenseJo.put("isLicense", 0); //0-没过期，1-过期
			} 
		
			dvLicenseJo.put("expireTime",TimeUtils.getFormatTimeStr(deviceStatus.getDeviceStatusLicense().getExpirationTime()));//tbd
			    
			ja.add(dvLicenseJo);
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
	
	
	public JSONObject updateFile( String deviceSN, String filename, String uploadFilename)
	{
		/* 创建版本 ;包括三步   1.上传license文件并保存到系统指定的目录(可以由配置文件指定)  2.完成与设备的交互; 3.生成升级记录   */
		SendLicenseNotify sendLicenseNotify;
		JSONObject jsonObject = new JSONObject();
		String fileUrl = null;
		String Signature = null;
		Device device = null;
		boolean returnVal = false;
		DeviceStatus DvStatus = null;
		Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
		SimpleDateFormat dateformat =new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String,Object> params=new HashMap<String, Object>();
		
		if ( filename == null ||  filename.isEmpty())
		{
		    	params.put("fileName", null);
		}
		else
		{
			params.put("fileName",  filename);
		}
		
		if (uploadFilename == null || uploadFilename.isEmpty())
		{
		    	params.put("uploadFilename", null);
		}
		else
		{
			params.put("uploadFilename", uploadFilename);
		}
		
		if (deviceSN == null || deviceSN.isEmpty())
		{
		    	params.put("deviceSn", null);
		}
		else
		{
			params.put("deviceSn", deviceSN);
		}
		
		if (params.get("fileName") == null || params.get("uploadFilename") == null)
		{
			 returnVal = false;
		}
		
		fileUrl = storageServ.moveTempFile2LicensePath(uploadFilename, filename, deviceSN);//ftp://10.1.1.205/pub/upload/temp/046D9E8831E0F84EE9591FF313D98C4E这个文件
		String sendFileUrl = licensePath + "/" + deviceSN + "/" + filename;




		try {
			Runtime.getRuntime().exec("chmod -R 777 /var/ftp/pub/upload/license"+ "/" + deviceSN + "/" + filename);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String cc="/var/ftp/pub/upload/license"+ "/" + deviceSN + "/" + filename;
		try {
			ZipFile zf = new ZipFile(cc);
			InputStream in = new BufferedInputStream(new FileInputStream(cc));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry ze;
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.isDirectory()) {
				} else {
					System.err.println("file - " + ze.getName() + " : "
							+ ze.getSize() + " bytes");
					String n=ze.getName();
					n=n.substring(n.lastIndexOf("."),n.length());
					System.out.println(n);
					if(".wlic".equals(n)){
					}else{
						jsonObject.put("status", false);
						jsonObject.put("message", "失败");
						return jsonObject;
					}

					long size = ze.getSize();
					if (size > 0) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(zf.getInputStream(ze)));
						String line;
						while ((line = br.readLine()) != null) {
							System.out.println(line);
						}
						br.close();
					}
					System.out.println();
				}
			}
			zin.closeEntry();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jsonObject.put("status", false);
			jsonObject.put("message", "失败");
			return jsonObject;
		}


















		params.put("fileUrl", sendFileUrl);
		//params.put("deleteFlag", 0);
		
		try {
			Runtime.getRuntime().exec("chmod -R 777 " + fileUrl);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.needDelay(500);
		Md5 md5 = new Md5();
		File big = new File(fileUrl + "/" + filename);
		try {
			Signature = md5.getFileMD5String(big);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Runtime.getRuntime().exec("chmod -R  777 " + fileUrl +"/"+ filename);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.needDelay(500);
		params.put("checksum", Signature);
		device = (Device)SigletonBean.deviceSiteInfoMaps.get(deviceSN);
		if(device == null)
		{
			logger.info("device =" +  deviceSN + "的设备已经被删除");
			jsonObject.put("status", false);
		    jsonObject.put("message", "失败");
		    return jsonObject;
		}
		DvStatus = (DeviceStatus)DvStatusInfoMap.get(deviceSN);
		if(DvStatus != null)
		{
			params.put("bExpireTime", DvStatus.getDeviceStatusLicense().getExpirationTime());
		}
		params.put("siteName", device.getSite().getName());
	    params.put("uploadUser", SigletonBean.getUserName());
	    params.put("uploadTime", dateformat.format( new Date()));
	    params.put("createTime", dateformat.format( new Date()));
	    params.put("updateTime", dateformat.format( new Date()));
	    params.put("aExpireTime", null);
	    params.put("statusFlag", 0);
	    params.put("scheduleTime", null);
	    params.put("retries", 1);
	    params.put("checksum", Signature.trim());
		 			
		try {
			deviceLicenseMapper.insertDeviceLicense(params);
			returnVal = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(returnVal == true)
		{
			jsonObject.put("status", true);
		    jsonObject.put("message", "成功");
		}
		else
		{
			jsonObject.put("status", false);
		    jsonObject.put("message", "失败");
		}
		
		this.licenseNotifySend();
	    return jsonObject;
	}
	
	
	public void licenseNotifySend()
	{
		/*1. 查找数据库，得到未成功收到响应complete消息的且尝试次数 ，2、满足条件记录的device逐个发送 */

		Map<String,Object> params=new HashMap<String, Object>();
		SendLicenseNotify sendLicenseNotify;
		int maxSendTimes = 3;
		byte tries = 0;
		params.put("maxRetries", maxSendTimes);
		params.put("status", 0);
		
		
		List<DeviceLicense>  deviceLicenseList =(List<DeviceLicense>) deviceLicenseMapper.selectUnsendDvLicenseList(params);
	    for (DeviceLicense deviceLicense : deviceLicenseList) {
			 
	    	/* 更新尝试发送的次数 ,调度时间，更新时间  */
	    	tries = deviceLicense.getRetrties();
	    	tries = (byte)(tries +1);
	    	Byte aa = new Byte(tries);
	    	deviceLicense.setRetrties(aa);
	    	
	    	deviceLicense.setScheduleTime(TimeUtils.timeFormatterStr.format( new Date()));
	    	deviceLicense.setUpdateTime(TimeUtils.timeFormatterStr.format( new Date()));
	    	
	    	try {
	    		deviceLicenseMapper.updateByPrimaryKeySelective(deviceLicense);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	/* 发送notify 消息*/
	    	sendLicenseNotify = new SendLicenseNotify(); 
			sendLicenseNotify.setUsername(ftpUser.trim());
			sendLicenseNotify.setFilePath(deviceLicense.getFileUrl());
			sendLicenseNotify.setType("0");
			sendLicenseNotify.setTime(TimeUtils.timeFormatterStr.format( new Date()));
			sendLicenseNotify.setFtpIp(ftpServerIp.trim());
			sendLicenseNotify.setFtpPort(ftpServerPort.trim());
			
			sendLicenseNotify.setSignature(deviceLicense.getCheckSum().trim());
			sendLicenseNotify.setProtocol("0");
			sendLicenseNotify.setPassword(ftpPwd.trim());
			oamMsgServ.setLicenseNotify(deviceLicense.getDeviceSn(), sendLicenseNotify);
	    
		 }
	
	}
			
	public JSONObject queryHistoryData(Integer draw, Integer length, Integer start, String startDate,
			String endDate, String deviceSN, String status)
	{
	   	long totalCnt = 0;
     	Device device;
 		boolean queryStatus = false;
 		JSONObject jo=new JSONObject();
 		SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
         
         if (status == null || status.equals("0"))
         {
         	params.put("status", null);
         }
         else
         {
        	switch(status)
        	{
        	case "1":
        		params.put("status", 0);
        		break;
        	case "2":
        		params.put("status", 1);
        		break;
        	}
         	
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
         	List<DeviceLicense>  deviceLicenseList =(List<DeviceLicense>) deviceLicenseMapper.selectDvLicenseList(params);
         	if (deviceLicenseList.size() == 0)
     		{
     			logger.info("没找到license授权记录");
     		}
 		    for (DeviceLicense deviceLicense : deviceLicenseList) {
 		    	JSONObject deviceLicenseJo = new JSONObject();
 		    	device = (Device)SigletonBean.deviceSiteInfoMaps.get(deviceLicense.getDeviceSn());
 				if(device == null)
 				{
 					logger.info("device =" +  deviceLicense.getDeviceSn() + "的设备已经被删除");
 					continue;
 				}
 				
 				/*if(devServ.deviceIsBelongToUser(SigletonBean.getUserId(), deviceLicense.getDeviceSn()) == false)
	        	{
	        		continue;
	        	}*/
 				/*deviceLicenseJo.put("id", deviceLicense.getId());*/
 				deviceLicenseJo.put("siteName", deviceLicense.getSiteName());
 				deviceLicenseJo.put("deviceSN", deviceLicense.getDeviceSn());
 				deviceLicenseJo.put("deviceName", device.getName());
 				deviceLicenseJo.put("updateTime", TimeUtils.getFormatTimeStr(deviceLicense.getUpdateTime()));
 				deviceLicenseJo.put("bExpireTime", TimeUtils.getFormatTimeStr(deviceLicense.getbExpireTime()));
 				deviceLicenseJo.put("aExpireTime", TimeUtils.getFormatTimeStr(deviceLicense.getaExpireTime()));
				deviceLicenseJo.put("status", deviceLicense.getStatusFlag());
 				ja.add(deviceLicenseJo);

 				queryStatus = true;
 			}
 		    if(queryStatus == true)
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
			totalCnt = (long)deviceLicenseMapper.getDvLicenseCnt(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return totalCnt;
	}
	
	private void needDelay(int dealayMiniSeconds)
	{
		try {
			Thread.currentThread().sleep(dealayMiniSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
