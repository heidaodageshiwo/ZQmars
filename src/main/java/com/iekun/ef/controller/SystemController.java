package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.service.*;
import com.iekun.ef.util.ComInfoUtil;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangqi.yang on 2016/10/21.
 */

@Controller
public class SystemController {

    public static Logger logger = LoggerFactory.getLogger(com.iekun.ef.controller.SystemController.class);

    @Autowired
    private  MainService mainServ;

    @Autowired
    private ResourceService resourceServ;

    @Autowired
    private UserService userService;

    @Autowired
    private ComInfoUtil comInfoUtil;

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private StorageService storageService;
    
    @Autowired
    private SystemParaService sysParaServ;
        
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private ExportDataService exportDataServ;

	@RequestMapping("/licensure")
    public String licensure( Map<String, Object> model ) {

        Map<String, Object> licenseInfo = new HashMap();

        licenseInfo.put("softwareName", "电子围栏系统2.0版本");
        licenseInfo.put("version",  licenseService.getProductVersion());
        licenseInfo.put("productSN",  licenseService.getProductKey());
        licenseInfo.put("jreInfo", licenseService.getJreInfo());
        licenseInfo.put("jvmInfo",  licenseService.getJvmInfo());
        licenseInfo.put("verifyLicense",  licenseService.verifyLicense() );
        if( 0 ==  licenseService.verifyLicense()) {
            licenseInfo.put("licensedTo", licenseService.getLicCompany() + "- 已授权 ");
            licenseInfo.put("publishDate", licenseService.getLicIssueDataText());
            licenseInfo.put("expiredDate", licenseService.getLicExpiryDateText());
        } else if ( 3 ==  licenseService.verifyLicense() ) {
            licenseInfo.put("licensedTo", licenseService.getLicCompany() + "- 授权已过期");
            licenseInfo.put("publishDate", licenseService.getLicIssueDataText());
            licenseInfo.put("expiredDate", licenseService.getLicExpiryDateText());
        } else {
            licenseInfo.put("licensedTo", "无效的授权");
            licenseInfo.put("publishDate", "");
            licenseInfo.put("expiredDate", "");
        }

        //// TODO: 2016/10/26
    	Map<String, Object> systemInfo = mainServ.getSystemInfo();
        //Map<String, Object> systemInfo = com.iekun.ef.test.ui.UITestData.getSystemInfo();

        model.put("systemInfo", systemInfo);

        model.put("licenseInfo", licenseInfo);

        return "system/licensure";
    }

    @RequestMapping("/system/license")
    public String license( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        Map<String, Object> licenseInfo = new HashMap();

        licenseInfo.put("softwareName", "电子围栏系统2.0版本");
        licenseInfo.put("version",  licenseService.getProductVersion());
        licenseInfo.put("productSN",  licenseService.getProductKey());
        licenseInfo.put("jreInfo", licenseService.getJreInfo());
        licenseInfo.put("jvmInfo",  licenseService.getJvmInfo());
        licenseInfo.put("verifyLicense",  licenseService.verifyLicense() );
        if( 0 ==  licenseService.verifyLicense()) {
            licenseInfo.put("licensedTo", licenseService.getLicCompany() + "- 已授权 ");
            licenseInfo.put("publishDate", licenseService.getLicIssueDataText());
            licenseInfo.put("expiredDate", licenseService.getLicExpiryDateText());
        } else if ( 3 ==  licenseService.verifyLicense() ) {
            licenseInfo.put("licensedTo", licenseService.getLicCompany() + "- 授权已过期");
            licenseInfo.put("publishDate", licenseService.getLicIssueDataText());
            licenseInfo.put("expiredDate", licenseService.getLicExpiryDateText());
        } else {
            licenseInfo.put("licensedTo", "无效的授权");
            licenseInfo.put("publishDate", "");
            licenseInfo.put("expiredDate", "");
        }

        //// TODO: 2016/12/03
        Map<String, Object> systemInfo = mainServ.getSystemInfo();

        model.put("systemInfo", systemInfo);
        model.put("licenseInfo", licenseInfo);

        return "system/licenseLogin";
    }

    @RequestMapping("/system/updateLicense")
    @ResponseBody
    public JSONObject  updateLicense(
            @RequestParam(value = "filename", required = true ) String filename,
            @RequestParam(value = "originalFilename", required = true ) String originalFilename
    ) {
        JSONObject jsonObject = new JSONObject();


        if( filename.isEmpty() || originalFilename.isEmpty() ) {
            jsonObject.put("status", false);
            jsonObject.put("message", "参数错误");
        }

        try {
            storageService.moveTempFile2LicensePath(filename,  originalFilename, "");

            if( licenseService.reloadLicenseFile() ){
                jsonObject.put("status", true);
                jsonObject.put("message", "更新成功");
            } else {
                jsonObject.put("status", false);
                jsonObject.put("message", "许可解析错误");
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            jsonObject.put("status", false);
            jsonObject.put("message", "未找到文件");
        }

        return jsonObject;
    }



    @RequestMapping("/system/databaseMaintenance")
    public  String databaseMaintenance( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/18
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "system/databaseMaintenance";
    }

    @RequestMapping("/system/databaseMaintenance/exportBaseData")
    @ResponseBody
    public JSONObject exportBaseData(
            @RequestParam(value = "userData", required = false ) String userData,
            @RequestParam(value = "siteData", required = false ) String siteData,
            @RequestParam(value = "blacklistData", required = false ) String blacklistData,
            @RequestParam(value = "homeOwnershipData", required = false ) String homeOwnershipData
    ){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();

        String fileUrl = exportDataServ.exportBaseData(userData, siteData, blacklistData, homeOwnershipData);
        if (fileUrl != null)
        {
        	 jsonObject.put("status", true);
             jsonObject.put("message", "成功");
        }
        else
        {
        	 jsonObject.put("status", false);
             jsonObject.put("message", "失败");
        }
       
        //data.put("url", "/upload/xxx.text");
        data.put("url", fileUrl);
        jsonObject.put("data", data);

        return jsonObject;
    }

    @RequestMapping("/system/databaseMaintenance/importBaseData")
    @ResponseBody
    public JSONObject importBaseData(
            @RequestParam(value = "file", required = true ) String file
    ){
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        boolean importSuccess = false;
        //String tempfile = "e:/teeeee/baseData.sql";
        logger.info("import baseData excution sql: " + file);
        importSuccess = exportDataServ.importBaseData(file, sqlSessionFactory);
        
        if(true == importSuccess)
        {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        }
        else
        {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }

        jsonObject.put("data", dataObject);

        return jsonObject;
    }


    @RequestMapping("/system/upgrade")
    public String upgrade( Map<String, Object> model ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        //// TODO: 2016/12/22
        Map<String, Object> upgradeInfo = new HashMap();
        upgradeInfo.put("serverUrl",sysParaServ.getSysPara("upgradeURL"));
        model.put("upgrade", upgradeInfo );

        return "system/upgrade";
    }


    @RequestMapping("/system/configuration")
    public String configuration(  Map<String, Object> model ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "system/configuration";
    }

    @RequestMapping("/system/getConfig")
    @ResponseBody
    public JSONObject getConfig(){
    	   //JSONObject jsonObject = com.iekun.ef.test.ui.SystemConfigData.getConfig();
       	   JSONObject jsonObject = new JSONObject();
           JSONObject dataObj = new JSONObject();

           String systemName = sysParaServ.getSysPara("systemName"); 
           if ( null == systemName)
           {
        	   dataObj.put("systemName", "移动终端预警系统");
        	   sysParaServ.insertSysPara("systemName", "移动终端预警系统");
           }
           else
           {
        	   dataObj.put("systemName", systemName);  
           }
           
           String systemIcon = sysParaServ.getSysPara("systemIcon"); 
           if ( null == systemIcon)
           {
        	   dataObj.put("systemIcon", "/img/logo.png");
        	   sysParaServ.insertSysPara("systemIcon", "/img/logo.png");
           }
           else
           {
        	   dataObj.put("systemIcon", systemIcon);  
           }
          
           String companyYear = sysParaServ.getSysPara("companyYear"); 
           if ( null == companyYear)
           {
        	   dataObj.put("companyYear", "2014");
        	   sysParaServ.insertSysPara("companyYear", "2014");
           }
           else
           {
        	   dataObj.put("companyYear", companyYear);  
           }

           String companyName = sysParaServ.getSysPara("companyName"); 
           if ( null == companyName)
           {
        	   dataObj.put("companyName", "上海翊坤");
        	   sysParaServ.insertSysPara("companyName", "上海翊坤");
           }
           else
           {
        	   dataObj.put("companyName", companyName);  
           }
           
           String companyURL = sysParaServ.getSysPara("companyURL");
           if ( null == companyURL)
           {
        	   dataObj.put("companyURL", "http://www.iekun.cn");
        	   sysParaServ.insertSysPara("companyURL", "http://www.iekun.cn");
           }
           else
           {
        	   dataObj.put("companyURL", companyURL);  
           }
           
           String upgradeURL = sysParaServ.getSysPara("upgradeURL");
           if ( null == upgradeURL)
           {
        	   dataObj.put("upgradeURL", "http://192.168.1.200:9191");
        	   sysParaServ.insertSysPara("upgradeURL", "http://192.168.1.200:9191");
           }
           else
           {
        	   dataObj.put("upgradeURL", upgradeURL);  
           }
           
           String ftpIP = sysParaServ.getSysPara("ftpIP");
           if ( null == ftpIP)
           {
        	   dataObj.put("ftpIP", "http://192.168.1.200");
        	   sysParaServ.insertSysPara("ftpIP", "http://192.168.1.200");
           }
           else
           {
        	   dataObj.put("ftpIP", ftpIP);  
           }
           
           String ftpPort = sysParaServ.getSysPara("ftpPort");
           if ( null == ftpPort)
           {
        	   dataObj.put("ftpPort", 21);
        	   sysParaServ.insertSysPara("ftpPort", "21");
           }
           else
           {
        	   dataObj.put("ftpPort", ftpPort);  
           }
           
           String mapCenterLng = sysParaServ.getSysPara("mapCenterLng");
           if ( null == mapCenterLng)
           {
        	   dataObj.put("mapCenterLng", 121.386154);
        	   sysParaServ.insertSysPara("mapCenterLng", "121.386154");
           }
           else
           {
        	   dataObj.put("mapCenterLng", Float.parseFloat(mapCenterLng));  
           }
           
           String mapCenterLat = sysParaServ.getSysPara("mapCenterLat");
           if ( null == mapCenterLat)
           {
        	   dataObj.put("mapCenterLat", 31.115175);
        	   sysParaServ.insertSysPara("mapCenterLat", "31.115175");
           }
           else
           {
        	   dataObj.put("mapCenterLat", Float.parseFloat(mapCenterLat));  
           }
           
           String mapZoom = sysParaServ.getSysPara("mapZoom");
           if ( null == mapZoom)
           {
        	   dataObj.put("mapZoom", 15);
        	   sysParaServ.insertSysPara("mapZoom", "15");
           }
           else
           {
        	   dataObj.put("mapZoom", Integer.parseInt(mapZoom));  
           }
                 
           String mapDataSwitch = sysParaServ.getSysPara("mapDataSwitch");
           if ( null == mapDataSwitch)
           {
        	   dataObj.put("mapDataSwitch", 1);
        	   sysParaServ.insertSysPara("mapDataSwitch", "1");
           }
           else
           {
        	   dataObj.put("mapDataSwitch", mapDataSwitch);  
           }
           
           String devAlarmSms = sysParaServ.getSysPara("devAlarmSms");
           if ( null == devAlarmSms)
           {
        	   dataObj.put("devAlarmSms", 0);
        	   sysParaServ.insertSysPara("devAlarmSms", "0");
           }
           else
           {
        	   dataObj.put("devAlarmSms", devAlarmSms);  
           }
           
           jsonObject.put("status", true);
           jsonObject.put("message", "成功");
           jsonObject.put("data", dataObj);
           
           return jsonObject;
    }

    @RequestMapping("/system/setConfig")
    @ResponseBody
    public JSONObject setConfig(
            @RequestParam(value = "systemName", required = true ) String systemName,
            @RequestParam(value = "systemIcon", required = true ) String systemIcon,
            @RequestParam(value = "companyName", required = true ) String companyName,
            @RequestParam(value = "companyURL", required = true ) String companyURL,
            @RequestParam(value = "companyYear", required = true ) String companyYear,
            @RequestParam(value = "upgradeURL", required = true ) String upgradeURL,
            @RequestParam(value = "ftpIP", required = true ) String ftpIP,
            @RequestParam(value = "ftpPort", required = true ) String ftpPort,
            @RequestParam(value = "mapCenterLng", required = true ) Float mapCenterLng,
            @RequestParam(value = "mapCenterLat", required = true ) Float mapCenterLat,
            @RequestParam(value = "mapZoom", required = true ) Integer mapZoom,
            @RequestParam(value = "mapDataSwitch", required = false ) String mapDataSwitch,
            @RequestParam(value = "devAlarmSms", required = false ) Integer devAlarmSms
    ){
        JSONObject jsonObject = new JSONObject();
        boolean returnVal = true;
        
        try {
			sysParaServ.setSysPara("systemName", systemName);
			sysParaServ.setSysPara("systemIcon", systemIcon);
			sysParaServ.setSysPara("companyName", companyName);
			sysParaServ.setSysPara("companyURL", companyURL);
			sysParaServ.setSysPara("companyYear", companyYear);
			sysParaServ.setSysPara("upgradeURL", upgradeURL);
			sysParaServ.setSysPara("ftpIP", ftpIP);
			sysParaServ.setSysPara("ftpPort", ftpPort);
			sysParaServ.setSysPara("mapCenterLng", String.valueOf(mapCenterLng));
			sysParaServ.setSysPara("mapCenterLat",  String.valueOf(mapCenterLat));
			sysParaServ.setSysPara("devAlarmSms", String.valueOf(devAlarmSms));
			sysParaServ.setSysPara("mapZoom", String.valueOf(mapZoom));
			
			if ((mapDataSwitch== null)||(mapDataSwitch.isEmpty()))
			{
				sysParaServ.setSysPara("mapDataSwitch", "0");
			}
			else
			{
				sysParaServ.setSysPara("mapDataSwitch", "1");
			}
		
        } catch (Exception e) {
			// TODO Auto-generated catch block
			returnVal = false;
			e.printStackTrace();
		}
                
        if(returnVal == false)
        {
        	jsonObject.put("status",false);
            jsonObject.put("message", "配置失败");
        }
        else
        {
        	jsonObject.put("status",true);
            jsonObject.put("message", "配置成功");
        }
        
        return jsonObject;
    }


}
