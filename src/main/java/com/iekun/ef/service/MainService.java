package com.iekun.ef.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iekun.ef.util.SystemInfo;

@Service
public class MainService {

	
	@Autowired
    private SystemParaService sysParaServ;
	
	/**
     * 获取系统信息
     * @return
     */
    public Map<String, Object> getSystemInfo() {

    	SimpleDateFormat df = new SimpleDateFormat("yyyy");
    	Date nowDate =  new Date();
    	String noDateStr = df.format(nowDate);
    	
        Map<String, Object> systemInfo = new HashMap();
        systemInfo.put("version",SystemInfo.getVersion());
        String companyYear = sysParaServ.getSysPara("companyYear");
        if(companyYear == null)
        {
        	companyYear = "2014";
        	sysParaServ.insertSysPara("companyYear", "2014");
        }
        systemInfo.put("companyYear",companyYear+ "-" + noDateStr);
      
        String companyName = sysParaServ.getSysPara("companyName");
        if (companyName == null)
        {
        	companyName = "上海翊坤";
        	sysParaServ.insertSysPara("companyName", "上海翊坤");
        }
        systemInfo.put("companyName",companyName);
        
        String companyUrl = sysParaServ.getSysPara("companyURL");
        if (companyUrl == null)
        {
        	companyUrl = "www.iekun.com";
        	sysParaServ.insertSysPara("companyURL", "http://www.iekun.cn");
        }
        systemInfo.put("companyURL", companyUrl);

        String SystemName = sysParaServ.getSysPara("systemName");
        if (SystemName == null)
        {
        	SystemName = "移动终端预警系统";
        	sysParaServ.insertSysPara("systemName", "移动终端预警系统");
        }
        systemInfo.put("projectName",SystemName);
        
        String systemIcon = sysParaServ.getSysPara("systemIcon");
        if (systemIcon == null)
        {
        	systemIcon = "/img/logo.png";
        	sysParaServ.insertSysPara("systemIcon", "/img/logo.png");
        }
        systemInfo.put("projectIcon",systemIcon);

        return systemInfo;
    }
    
	
}
