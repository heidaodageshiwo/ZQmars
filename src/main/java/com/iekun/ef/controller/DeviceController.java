package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.dao.SysLogMapper;
import com.iekun.ef.model.*;
import com.iekun.ef.service.DeviceService;
import com.iekun.ef.service.DeviceDetailsService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.CommonConsts;

import com.iekun.ef.util.FtpUtil;
import com.iekun.ef.util.PropertyUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangqi.yang on 2016/10/26.
 */

@Controller
@RequestMapping("/device")
public class DeviceController {

	private static Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private String province;
    private String city;
    private String town;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
    //private String date=sdf.format(new Date());
    
    @Autowired
	private ResourceService resourceServ;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ComInfoUtil comInfoUtil;
	
	@Autowired
	private DeviceService  deviceService;

	@Autowired
	private DeviceDetailsService deviceDetailsService;

    @RequestMapping("/status")
    public String status( Map<String, Object> model ) {

		comInfoUtil.getTopHeaderInfo( model );
		comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/10/31
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "device/statusNew";
	}

    @RequestMapping("/index")
    public String index( Map<String, Object> model ){

		comInfoUtil.getTopHeaderInfo( model );
		comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/2
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "device/index";
    }
	@RequestMapping("/details")
	public String details( Map<String, Object> model ){

		comInfoUtil.getTopHeaderInfo( model );
		comInfoUtil.getSystemInfo( model );

		//// TODO: 2016/11/2
		long roleId = SigletonBean.getRoleId();
		resourceServ.getResourceInfoByRoleId(model, roleId);
		model.put("user", userService.getUserDetailInfo());
		return "device/details";
	}

    @RequestMapping("/group")
    public String group( Map<String, Object> model ) {

		comInfoUtil.getTopHeaderInfo( model );
		comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/3
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "device/group";
    }
    
    @RequestMapping("/getSites")
    @ResponseBody
    public JSONObject getSites(){

    	JSONObject deviceJoObj = null;
    	Long userId = SigletonBean.getUserId();
        List<Site> siteList= deviceDetailsService.getSiteListInfoByUserId(userId);
        //List<Site> siteList= deviceService.getSiteListInfo();
    	JSONObject jo=new JSONObject();
  		JSONArray ja = new JSONArray();
	    for (Site site : siteList) {
	    	JSONObject siteJo = new JSONObject();
	    	siteJo.put("id", site.getId());
	    	siteJo.put("sn", site.getSn());
	    	siteJo.put("name", site.getName());
	    	siteJo.put("longitude", site.getLongitude());
	    	siteJo.put("latitude", site.getLatitude());
	    	siteJo.put("address", site.getAddress());
	    	siteJo.put("remark", site.getRemark());
	    	siteJo.put("createTime", site.getCreateTime());
	    	siteJo.put("province",site.getProvince_name());
	    	siteJo.put("city",site.getCity_name());
	    	siteJo.put("town",site.getTown_name());
	    	siteJo.put("provinceId",site.getProvince_id());
	    	siteJo.put("cityId",site.getCity_id());
	    	siteJo.put("townId",site.getTown_id());
			siteJo.put("LC",site.getLC());
			siteJo.put("CI",site.getCI());
	    	deviceJoObj = this.getDevicesBySiteId(site.getId());
	    	if (deviceJoObj.getBooleanValue("status") == true)
	    	{
	    		siteJo.put("devices",deviceJoObj.getJSONArray("data"));
	    	}
	    	ja.add(siteJo);
		}
	   
	    jo.put("status", true);
	    jo.put("message", "成功");
	    jo.put("data", ja);
		return jo;

    }
    
    public JSONObject getDevicesBySiteId(
    		@RequestParam(value = "siteId", required = true ) Long siteId)
    {
    	boolean status = false;
    	List<Device> deviceList= deviceService.getDeviceListInfoBySiteId(siteId);
    	JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
		String modeTypeText;
		String operatorTypeText;
	    for (Device device : deviceList) {
	    	JSONObject deviceJo = new JSONObject();
	    	deviceJo.put("id", device.getId());
	    	deviceJo.put("sn", device.getSn());
	    	deviceJo.put("name", device.getName());
	    	deviceJo.put("type",  device.getType() );
	    	
	    	modeTypeText = this.getModeTypeText(device.getType());
	    	deviceJo.put("typeText",  modeTypeText );
	     	deviceJo.put("band",  device.getBand() );
	    	deviceJo.put("operator", device.getOperator());
	    	

	    	operatorTypeText = this.getOperatorTypeText(Integer.valueOf(device.getOperator()));
	    	deviceJo.put("operatorText", operatorTypeText );
	    	deviceJo.put("manufacturer", device.getManufacturer() );
	    	deviceJo.put("remark", device.getRemark() );
	    	deviceJo.put("createTime", device.getCreateTime());
	        ja.add(deviceJo);
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
        	jo.put("message", "创建用户失败");
        }
	   
	    return jo;
    }
    
    public JSONObject getSiteUserBySiteIdAndUserId(
    		@RequestParam(value = "siteId", required = true ) Long siteId,
    		@RequestParam(value = "userId", required = true ) Long userId )
    {
    	boolean status = false;
    	SiteUser siteUserObj = new SiteUser(siteId, userId);
    	List<SiteUser> siteUserList= deviceService.getSiteUserInfoBySiteIdAndUserId(siteUserObj);
    	JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
	    for (SiteUser siteUser : siteUserList) {
	    	JSONObject siteUserJo = new JSONObject();
	    	siteUserJo.put("id", siteUser.getId());
	    	siteUserJo.put("siteId", siteUser.getSiteId());
	    	siteUserJo.put("userId", siteUser.getUserId());
	    	siteUserJo.put("deleteFlag",  siteUser.getDeleteFlag() );
	    	
	        ja.add(siteUserJo);
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
        	jo.put("message", "创建用户失败");
        }
	   
	    return jo;
    }
    
    @RequestMapping("/createSite")
    @ResponseBody
    public JSONObject createSite(
            @RequestParam(value = "siteSn", required = true ) String sn,
            @RequestParam(value = "siteName", required = true ) String name,
            @RequestParam(value = "province", required = true ) Integer provinceId,
            @RequestParam(value = "provinceText", required = false ) String provinceText,
            @RequestParam(value = "city", required = true ) Integer cityId,
            @RequestParam(value = "cityText", required = false ) String cityText,
            @RequestParam(value = "town", required = true ) Integer townId,
            @RequestParam(value = "townText", required = false ) String townText,
            @RequestParam(value = "siteAddress", required = true ) String address,
            @RequestParam(value = "siteLongitude", required = false ) Double longitude,
            @RequestParam(value = "siteLatitude", required = false ) Double latitude,
            @RequestParam(value = "siteRemark", required = false ) String remark,
			@RequestParam(value = "siteLC", required = false ) String LC,
			@RequestParam(value = "siteCI", required = false ) String CI
    ){
        JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        boolean innerReturnVal = false;
        Integer deleteFlag = 0;
        Site site = null;
       
        returnVal = this.checkSiteSnValid(sn);
        if(returnVal == true)
        { 
        	site = new Site(sn,name, Long.valueOf(provinceId), Long.valueOf(cityId), Long.valueOf(townId), provinceText, cityText, townText, address, longitude.toString(), latitude.toString(), remark, deleteFlag,LC,CI);
            String date=sdf.format(new Date());
            site.setCreateTime(date); 
            returnVal = deviceService.addSite(site);
        }
        
		if(returnVal == true)
        {
        	/* 创建站点成功，则自动分配该站点给该用户  */
			site = deviceService.selectBySiteSn(sn);
			
			long userId = SigletonBean.getUserId();
			SiteUser siteUser = new SiteUser(site.getId(), userId, 0);
			innerReturnVal = deviceService.assignDevice(siteUser);
			if (innerReturnVal == false)
			{
				jsonObject.put("status", true);
	            jsonObject.put("message", "站点创建成功，但分配给创建者失败");
			}
			
			jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "创建站点失败");
        }
     
        return jsonObject;
    }

    @RequestMapping("/updateSite")
    @ResponseBody
    public JSONObject updateSite(
            @RequestParam(value = "id", required = true ) Integer id,
            @RequestParam(value = "siteName", required = true ) String name,
            @RequestParam(value = "province", required = true ) Integer provinceId,
            @RequestParam(value = "provinceText", required = false ) String provinceText,
            @RequestParam(value = "city", required = true ) Integer cityId,
            @RequestParam(value = "cityText", required = false ) String  cityText,
            @RequestParam(value = "town", required = true ) Integer townId,
            @RequestParam(value = "townText", required = false ) String townText,
            @RequestParam(value = "siteAddress", required = true ) String address,
            @RequestParam(value = "siteLongitude", required = false ) Double longitude,
            @RequestParam(value = "siteLatitude", required = false ) Double latitude,
            @RequestParam(value = "siteRemark", required = false ) String remark,
			@RequestParam(value = "siteLC", required = false ) String LC,
			@RequestParam(value = "siteCI", required = false ) String CI
    ){
        JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        
        Site site = new Site(Long.valueOf(id),name, Long.valueOf(provinceId), Long.valueOf(cityId), Long.valueOf(townId), provinceText, cityText, townText, address, longitude.toString(), latitude.toString(), remark,LC,CI);
        String date=sdf.format(new Date());
        site.setUpdateTime(date); 
        returnVal = deviceService.updateSite(site);
		if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "创建用户失败");
        }
     
        return jsonObject;
    }

    @RequestMapping("/delSite")
    @ResponseBody
    public JSONObject delSite( @RequestParam(value = "id", required = true ) Integer siteId ){

        JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        Integer deleteFlag = 1;
        
        
        Site site = new Site(Long.valueOf(siteId),deleteFlag);
		
        returnVal = deviceService.delSite(site);
		if(returnVal == true)
        {
			List<Device> deviceList= deviceService.getDeviceListInfoBySiteId(siteId);
			for (Device device : deviceList) {
		        
		        Device delDevice = new Device(device.getId(),deleteFlag);
				
		        returnVal = deviceService.delDevice(delDevice);
		        if (returnVal == false)
		        {
		        	jsonObject.put("status", false);
		            jsonObject.put("message", "删除站点下设备失败");
		        }
		        else
		        {
		        	deviceService.delItemFromRuleList(delDevice.getSn());
		        }
		       
			 }
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "删除站点失败");
        }
     
        return jsonObject;
    }

    private String getModeTypeText(Integer modeType)
    {
    	/* 设备类型：01- cdma；02- gsm；03- wcdma；04- td；05- tdd；06- fdd */
    	String modeTypeText;
    	switch(modeType)
    	{
    		case 1:
    			modeTypeText = CommonConsts.CDMA;
    			break;
    		case 2:
    			modeTypeText = CommonConsts.GSM;
    			break;
    		case 3:
    			modeTypeText = CommonConsts.WCDMA;
    			break;
    		case 4:
    			modeTypeText = CommonConsts.TD;
    			break;
    		case 5:
    			modeTypeText = CommonConsts.TDD;
    			break;
    		case 6:
    			modeTypeText = CommonConsts.FDD;
    			break;
    		default:
    			modeTypeText = "未知制式";
    			break;
    	}
    	
    	return modeTypeText;
    }
    
    private String getOperatorTypeText(Integer operatorType)
    {
    	/* 运营商：01- 移动 02- 电信 03- 联通 */
    	String operatorTypeText;
    	switch(operatorType)
    	{
	    	case 1:
	    		operatorTypeText = CommonConsts.CHINAMOBILE;
	    		break;
	    	case 2:
	    		operatorTypeText = CommonConsts.CHINATELECOM;
	    		break;
	    	case 3:
	    		operatorTypeText = CommonConsts.CHINAUNICOM;
	    		break;
    		default:
    			operatorTypeText = "未知运营商";
    			break;
    	}
    	
    	return operatorTypeText;
    }
    
    @RequestMapping("/getDevices")
    @ResponseBody
    public JSONObject getDevices(
			@RequestParam(value = "siteId", required = false) Integer siteId,
			@RequestParam(value = "siteSN", required = false) String siteSN
	){
    	
    	List<Device> deviceList = deviceService.getDeviceListInfo(siteId, siteSN);
    	JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
		String modeTypeText;
		String operatorTypeText;
	    for (Device device : deviceList) {
	    	if ( deviceService.deviceIsBelongToUser(SigletonBean.getUserId(), device.getSn()) == false)
	    	{
	    		continue;
	    	}
	    	JSONObject deviceJo = new JSONObject();
	    	deviceJo.put("id", device.getId());
	    	deviceJo.put("sn", device.getSn());
	    	deviceJo.put("name", device.getName());
	    	deviceJo.put("type",  device.getType());
	    	
	    	modeTypeText = this.getModeTypeText(device.getType());
	    	deviceJo.put("typeText",  modeTypeText );
	    	
	    	deviceJo.put("band",  device.getBand() );
	    	deviceJo.put("operator", device.getOperator());
	    	
	    	operatorTypeText = this.getOperatorTypeText(Integer.valueOf(device.getOperator()));
	    	deviceJo.put("operatorText", operatorTypeText );
	    	
	    	deviceJo.put("manufacturer", device.getManufacturer());
	    	deviceJo.put("remark", device.getRemark() );
	    	deviceJo.put("createTime", device.getCreateTime());
	    	if (device.getSite() == null )
	    	{
	    		deviceJo.put("siteId", null );
		    	deviceJo.put("siteSn", null);
		    	deviceJo.put("siteName", null);
		    	deviceJo.put("siteLongitude", null);
		    	deviceJo.put("siteLatitude", null);
		    	deviceJo.put("siteAddress", null);
		    	deviceJo.put("siteRemark", null);
	    	}
	    	else
	    	{
	    		deviceJo.put("siteId", device.getSite().getId() );
		    	deviceJo.put("siteSn", device.getSite().getSn());
		    	deviceJo.put("siteName", device.getSite().getName());
		    	deviceJo.put("siteLongitude", device.getSite().getLongitude());
		    	deviceJo.put("siteLatitude", device.getSite().getLatitude());
		    	deviceJo.put("siteAddress", device.getSite().getAddress());
		    	deviceJo.put("siteRemark", device.getSite().getRemark() );
		    }
	    	deviceJo.put("siteCreateTime", device.getCreateTime());
	    	       
	        ja.add(deviceJo);
		}
	    
	    jo.put("status", true);
	    jo.put("message", "成功");
	    jo.put("data", ja);
	  
		return jo;

    }

    @RequestMapping("/createDevice")
    @ResponseBody
    public JSONObject createDevice(
            @RequestParam(value = "deviceSn", required = true ) String sn,
            @RequestParam(value = "deviceName", required = true ) String name,
            @RequestParam(value = "deviceType", required = false ) Integer type,
            @RequestParam(value = "deviceBand", required = false ) String band,
            @RequestParam(value = "deviceOperator", required = false ) Integer operator,
            @RequestParam(value = "deviceManufacturer", required = false ) String manufacturer,
            @RequestParam(value = "deviceRemark", required = false ) String remark,
            @RequestParam(value = "belongTo", required = true ) String belongTo
    ){
    	JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        
        returnVal = this.checkDeviceSnValid(sn);
        if(returnVal == true)
        {
        	Device device = new Device(sn.toUpperCase(), name, type, band, operator.toString(), manufacturer, remark, belongTo);
        	String date=sdf.format(new Date());
        	device.setCreateTime(date);
        	returnVal = deviceService.addDevice(device);
        }
		
		if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "创建设备失败");
        }
     
        return jsonObject;
    }

	@RequestMapping("/checkSitSn")
	@ResponseBody
	public String checkSitSn(
			@RequestParam(value = "sn", required = true ) String sn
	) {
		List<Site> siteList= deviceService.getAllSiteListInfo();
		for (Site site : siteList) 
		{
			if (sn.compareToIgnoreCase(site.getSn()) == 0)
    		{
    			return "false";
    		}
		}
		
		return "true";
	}
	
	public boolean checkSiteSnValid(String sn) 
	{
		List<Site> siteList= deviceService.getAllSiteListInfo();
		for (Site site : siteList) 
		{
			if (sn.compareToIgnoreCase(site.getSn()) == 0)
    		{
    			return false;
    		}
		}
		
		return true;
	}
	
	@RequestMapping("/checkDeviceSn")
	@ResponseBody
	public String checkDeviceSn(
			@RequestParam(value = "sn", required = true ) String sn
	) {
		List<Device> deviceList= deviceService.getDeviceList();
		for (Device device : deviceList) 
		{
			if (sn.equalsIgnoreCase(device.getSn()) == true)
    		{
    			return "false";
    		}
		}
		return "true";
	}

	public boolean checkDeviceSnValid(String sn) 
	{
		List<Device> deviceList= deviceService.getDeviceList();
		for (Device device : deviceList) 
		{
			if (sn.equalsIgnoreCase(device.getSn()) == true)
    		{
    			return false;
    		}
		}
		return true;
	}
	
    
    @RequestMapping("/updateDevice")
    @ResponseBody
    public JSONObject updateDevice(
            @RequestParam(value = "id", required = true ) Integer id,
            @RequestParam(value = "deviceName", required = true ) String name,
            @RequestParam(value = "deviceType", required = false ) Integer type,
            @RequestParam(value = "deviceBand", required = false ) String band,
            @RequestParam(value = "deviceOperator", required = false ) Integer operator,
            @RequestParam(value = "deviceManufacturer", required = false ) String manufacturer,
            @RequestParam(value = "deviceRemark", required = false ) String remark,
            @RequestParam(value = "belongTo", required = true ) String belongTo
    ){
    	JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        
        Device device = new Device(id, name, type,  band, operator.toString(), manufacturer, remark, belongTo);
        String date=sdf.format(new Date());
        device.setUpdateTime(date);
		returnVal = deviceService.updateDevice(device);
		if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "更新设备失败");
        }
     
        return jsonObject;
    }

    @RequestMapping("/delDevice")
    @ResponseBody
    public JSONObject delDevice( @RequestParam(value = "id", required = true ) Integer deviceId ){
    	JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        //Integer deleteFlag = 1;
        
        
        //Device device = new Device(Long.valueOf(deviceId),deleteFlag);
        Device device = deviceService.getDeviceById(deviceId);
        if (device == null)
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "要删除的设备不存在");
        }
        else
        {
        	returnVal = deviceService.delDevice(device);
    		if(returnVal == true)
            {
            	jsonObject.put("status", true);
                jsonObject.put("message", "成功");
                
            }
            else
            {
            	jsonObject.put("status", false);
                jsonObject.put("message", "删除设备失败");
            }
        }
        
		
		 return jsonObject;
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping("/getAllDeviceTree")
    @ResponseBody
    public JSONObject getDevicesTree( @RequestParam(value = "id", required = true ) String userId/*Integer userId*/ ) {

        //JSONObject jsonObject  = new JSONObject();
        
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
        List<Site> siteList= deviceService.selectSiteList();
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
            jsonObject.put("message", "没有站点可供分配!");
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
                 for(int k=0; k < townCnt; k++)
                 {
                	 TownObject.put("id", currentTownList.get(0).getTown_id());
                	 TownObject.put("text", currentTownList.get(0).getTown_name());
                	 TownObject.put("zipCode", currentTownList.get(0).getZipCode());
                	 TownObject.put("cityCode", currentTownList.get(0).getCityCode());
                	 TownObject.put("state", selOffObject );
                	 TownObject.put("nodes", SitesObject );
                	
                	 
                	 List<Site>  currentSiteList = (List<Site>)townmaps.get(String.valueOf(currentTownList.get(0).getTown_id()));
                	 JSONObject  SiteObject = new JSONObject();
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
                     	SiteObject.put("createTime", currentSiteList.get(0).getCreateTime());
                     	SiteObject.put("province", currentSiteList.get(0).getProvince_name());
                     	SiteObject.put("provinceId", currentSiteList.get(0).getProvince_id());
                     	SiteObject.put("city", currentSiteList.get(0).getCity_name());
                     	SiteObject.put("cityId", currentSiteList.get(0).getCity_id());
                     	SiteObject.put("town", currentSiteList.get(0).getTown_name());
                     	SiteObject.put("townId", currentSiteList.get(0).getTown_id());
                     	
                     	JSONObject siteUserJoObj = this.getSiteUserBySiteIdAndUserId(currentSiteList.get(0).getId(), Long.parseLong(userId));
            	    	if (siteUserJoObj.getBooleanValue("status") == true)
            	    	{
            	    		SiteObject.put("state", selOnObject);
            	    	}
            	    	else
            	    	{
            	    		SiteObject.put("state", selOffObject);
            	    	}
            	    
                     	SitesObject.add( SiteObject);
                     	SiteObject = new JSONObject();
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
   
     

    /***
     *   绑定用户和设备，或解除绑定
     * @param action  Integer,  0 - 解除绑定， 1-绑定
     * @param userId  Integer,  用户ID值
     * @param siteId  Integer,  站点ID值
     * @return
     */
    @RequestMapping("/assignDevice")
    @ResponseBody
    public JSONObject assignDevice(
            @RequestParam(value = "action", required = true ) Integer action,
            @RequestParam(value = "userId", required = true ) Integer userId,
            @RequestParam(value = "siteId", required = true ) Integer siteId
    ) {
    	JSONObject jsonObject  = new JSONObject();
        boolean returnVal = false;
        Integer deleteFlag = 1;
        
        /* 说明： 有这条记录则刷新delete字段，没有这条记录则创建这条记录 */
        if (action == 1)
        {
        	deleteFlag = 0;
        }
        else
        {
        	deleteFlag = 1;
        }
        SiteUser siteUser = new SiteUser(Long.valueOf(siteId),Long.valueOf(userId), deleteFlag);
		
        JSONObject siteUserJoObj = this.getSiteUserBySiteIdAndUserId(Long.valueOf(siteId),Long.valueOf(userId));
        if (siteUserJoObj.getBooleanValue("status") == true)
    	{
        	returnVal = deviceService.updateAssign(siteUser);
    	}
    	else
    	{
    		returnVal = deviceService.assignDevice(siteUser);
    	}
             
		if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
            
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "更新分配关系失败");
        }
		
		 return jsonObject;
    }

	@RequestMapping("/getDeviceStatusTree")
	@ResponseBody
	public JSONObject getDeviceStatusTree(
			@RequestParam(value = "filter", required = false ) String  filter
	){
		JSONObject jsonObject = new JSONObject();
		if( null == filter) {
			filter="all";
		}

		//jsonObject = com.iekun.ef.test.ui.UITestDeviceData.getDeviceStatusTree( filter );
		jsonObject = deviceService.getDevicesTreeServ(SigletonBean.getUserId(), filter );

		return  jsonObject;
	}

	@RequestMapping("/getDeviceStatusTreeNew")
	@ResponseBody
	public List<DeviceTreeNode> getDeviceStatusTreeNew(String status){

		//要返回的zTree
		List<DeviceTreeNode> list = new ArrayList<>();
		//得到所有站点
		List<Site> siteList = deviceService.getSiteListInfoByUserId(SigletonBean.getUserId());
		List<Long>  provinceList = new ArrayList<>();
		List<Long> cityList = new ArrayList<>();
		List<Long> townList = new ArrayList<>();

		for(Site site: siteList){

			if(!provinceList.contains(site.getProvince_id())){
				provinceList.add(site.getProvince_id());
				list.add(new DeviceTreeNode(site.getProvince_id().toString(),"0",site.getProvince_name(),0,true));//省份

			}

			if(!cityList.contains(site.getCity_id())){
				cityList.add(site.getCity_id());
				list.add(new DeviceTreeNode(site.getCity_id().toString(),site.getProvince_id().toString(),site.getCity_name(),1,true));//城市
			}

			if(!townList.contains(site.getTown_id())){
				townList.add(site.getTown_id());
				list.add(new DeviceTreeNode(site.getTown_id().toString(), site.getCity_id().toString(), site.getTown_name(),2,true));//区域
			}

			list.add(new DeviceTreeNode(site.getId().toString(), site.getTown_id().toString(), site.getName(),3,true));//站点

			List<Device> deviceList = deviceService.findDeviceList(site.getId(), "all");
			for(Device device : deviceList){
				DeviceTreeNode deviceTreeNode = new DeviceTreeNode(device.getId().toString(), site.getId().toString(), device.getName(), 4, false);
				deviceTreeNode.setSn(device.getSn());
				String deviceStatus = deviceService.getfilterFromStatus(device.getSn());
				if(status.equals("all") || deviceStatus.equals(status)){
					deviceTreeNode.setStatus(deviceStatus);
					deviceTreeNode.setIconSkin(deviceStatus);
					String font = deviceService.getDeviceNameFont(deviceStatus);
					Map<String, Object> fontObj = new HashMap<>();
					fontObj.put("color", font);
					deviceTreeNode.setFont(fontObj);
					list.add(deviceTreeNode);
				}
			}
		}

		return list;
	}

	@RequestMapping("/getDeviceStatusTreeOnline")
	@ResponseBody
	public List<DeviceTreeNode> getDeviceStatusTreeOnline(){

		//要返回的zTree
		List<DeviceTreeNode> list = new ArrayList<>();
		//得到所有站点
		List<Site> siteList = deviceService.getSiteListInfoByUserId(SigletonBean.getUserId());
		List<Long>  provinceList = new ArrayList<>();
		List<Long> cityList = new ArrayList<>();
		List<Long> townList = new ArrayList<>();

		for(Site site: siteList){

			if(!provinceList.contains(site.getProvince_id())){
				provinceList.add(site.getProvince_id());
				list.add(new DeviceTreeNode(site.getProvince_id().toString(),"0",site.getProvince_name(),0,true));//省份

			}

			if(!cityList.contains(site.getCity_id())){
				cityList.add(site.getCity_id());
				list.add(new DeviceTreeNode(site.getCity_id().toString(),site.getProvince_id().toString(),site.getCity_name(),1,true));//城市
			}

			if(!townList.contains(site.getTown_id())){
				townList.add(site.getTown_id());
				list.add(new DeviceTreeNode(site.getTown_id().toString(), site.getCity_id().toString(), site.getTown_name(),2,true));//区域
			}

			list.add(new DeviceTreeNode(site.getId().toString(), site.getTown_id().toString(), site.getName(),3,true));//站点

			List<Device> deviceList = deviceService.findDeviceList(site.getId(), "all");
			for(Device device : deviceList){
				DeviceTreeNode deviceTreeNode = new DeviceTreeNode(device.getId().toString(), site.getId().toString(), device.getName(), 4, false);
				deviceTreeNode.setSn(device.getSn());
				String deviceStatus = deviceService.getfilterFromStatus(device.getSn());
				if(!deviceStatus.equals("offline")){
					deviceTreeNode.setStatus(deviceStatus);
					deviceTreeNode.setIconSkin(deviceStatus);
					String font = deviceService.getDeviceNameFont(deviceStatus);
					Map<String, Object> fontObj = new HashMap<>();
					fontObj.put("color", font);
					deviceTreeNode.setFont(fontObj);
					list.add(deviceTreeNode);
				}
			}
		}

		return list;
	}

	@RequestMapping("/getDeviceStatusInfo")
	@ResponseBody
	public JSONObject getDeviceStatusInfo(
			@RequestParam(value = "sn", required = true ) String  sn
	){
		JSONObject jsonObject = new JSONObject();
		//jsonObject = com.iekun.ef.test.ui.UITestDeviceData.getDeviceStatusInfo(sn);
		jsonObject = deviceService.getDeviceStatusInfo(sn);
		return  jsonObject;
	}

	@RequestMapping("/getSiteStatusInfo")
	@ResponseBody
	public JSONObject getSiteStatusInfo(
			@RequestParam(value = "sn", required = true ) String  sn
	){
		JSONObject jsonObject = new JSONObject();
		//jsonObject = com.iekun.ef.test.ui.UITestDeviceData.getSiteStatusInfo(sn);
		jsonObject = deviceService.getSiteStatusInfo(sn);
		return  jsonObject;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getDevicesByUserId")
    @ResponseBody
    public JSONObject getDevicesByUseId() {
		
		Long userId = SigletonBean.getUserId();
		List<Device> deviceList = deviceService.getDevicesByUseId(userId);
		JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
	    for (Device device : deviceList) {
	    	JSONObject deviceJo = new JSONObject();
	    	deviceJo.put("sn", device.getSn());
	    	deviceJo.put("name", device.getName());
	    	ja.add(deviceJo);
		}
	    
	    jo.put("status", true);
	    jo.put("message", "成功");
	    jo.put("data", ja);
	  
		return jo;

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSitesByUserId")
    @ResponseBody
    public JSONObject getSitesByUseId() {
		Long userId = SigletonBean.getUserId();
        List<Site> siteList= deviceService.getSiteListInfoByUserId(userId);
    	JSONObject jo=new JSONObject();
  		JSONArray ja = new JSONArray();
	    for (Site site : siteList) {
	    	JSONObject siteJo = new JSONObject();
	    	siteJo.put("sn", site.getSn());
	    	siteJo.put("name", site.getName());
	    	ja.add(siteJo);
		}
	   
	    jo.put("status", true);
	    jo.put("message", "成功");
	    jo.put("data", ja);
		return jo;
	}




	/*@RequestMapping("/deviceLogdown")
	@ResponseBody
	public JSONObject deviceLogdown(
			@RequestParam(value = "fileName", required = true ) String fileName
	) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", true);
		jsonObject.put("message", "正在下载，请稍后...");
		FTPClient ftpClient = new FTPClient();
		try {
			int ftpport= Integer.parseInt(PropertyUtil.getProperty("ftpserverPort"));
			if (ftpport != 0) {
				ftpClient.connect(PropertyUtil.getProperty("ftpserverIp"), ftpport);
			}else {
				ftpClient.connect(PropertyUtil.getProperty("ftpserverIp"));
			}
			ftpClient.login(PropertyUtil.getProperty("ftpserverUser"), PropertyUtil.getProperty("ftpserverPwd"));
			ftpClient.setControlEncoding("UTF-8");
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.info("FTP client error");
                jsonObject.put("status", false);
                jsonObject.put("message", "网络链接错误！");
				return jsonObject;
			}
			ftpClient.enterLocalPassiveMode();
			FTPFile[] files = ftpClient.listFiles();


			for(FTPFile file: files){
				String filename = fileName;
				*//*if (filename.equalsIgnoreCase(file.getName())) {

				}*//*
				System.out.println("------filename--------------------"+file);
			}

			for(FTPFile file: files){
				String filename = fileName;
				if (filename.equalsIgnoreCase(file.getName())) {
					DeleteFolder(PropertyUtil.getProperty("tasksave.filePath")+"/"+ file.getName());
					File localFile = new File(PropertyUtil.getProperty("tasksave.filePath")+"/"+ file.getName());
					OutputStream os = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), os);
					os.close();
				}
			}



			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("FTP client error");
            jsonObject.put("status", false);
            jsonObject.put("message", "网络链接错误！");
            return jsonObject;
		} finally {
			closeFtpCilent(ftpClient);
		}
		return jsonObject;
	}
	private void closeFtpCilent(FTPClient ftpClient) {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("close ftp error");
			}
		}
	}
	public static boolean DeleteFolder(String sPath) {
		//boolean  flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) {  // 不存在返回 false
			return false;
		} else {
			if(file.exists()&&file.isFile())
				System.gc();
			file.delete();
			return true;
		}
	}*/
	@Value("${ftpserver.downloadlog.ip}")
	private String ftpServerIp;

	@Value("${ftpserver.downloadlog.port}")
	private String ftpServerPort;

	@Value("${ftpserver.downloadlog.user}")
	private String ftpUsername;

	@Value("${ftpserver.downloadlog.pwd}")
	private String ftpPassword;
	public JSONObject deviceLogdown(
			@RequestParam(value = "fileName", required = true ) String fileName
	) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", true);
		jsonObject.put("info", "正在下载，请稍后...");
		DeleteFolder(PropertyUtil.getProperty("tasksave.filePath")+"/"+ fileName);
		FtpUtil.downloadFtpFile(PropertyUtil.getProperty("ftpserver.downloadlog.ip"), PropertyUtil.getProperty("ftpserver.downloadlog.user"), PropertyUtil.getProperty("ftpserver.downloadlog.pwd"),
				Integer.parseInt(PropertyUtil.getProperty("ftpserver.downloadlog.port")), "/", PropertyUtil.getProperty("tasksave.filePath"), fileName);



		return jsonObject;
	}

	private void closeFtpCilent(FTPClient ftpClient) {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("close ftp error");
			}
		}
	}
	public static boolean DeleteFolder(String sPath) {
		//boolean  flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) {  // 不存在返回 false
			return false;
		} else {
			if(file.exists()&&file.isFile())
				System.gc();
			file.delete();
			return true;
		}
	}


	@Autowired
	private SysLogMapper sysLogMapper;
	/**
	 * 下载zip文件
	 * */
	@RequestMapping("/deviceLogdownFromHttp")
	public void deviceLogdownFromHttp(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String fileName = request.getParameter("fileName");
		try{
			String resultPath =PropertyUtil.getProperty("tasksave.filePath")+"/"+fileName;// "E://AllTxt//zqzipzipzipzipzip.rar";
			String zipName = resultPath.substring(0,resultPath.lastIndexOf(".")) + ".zip";
			File zipFile = new File(zipName);
			//读写zip文件
			InputStream fis = new BufferedInputStream(new FileInputStream(zipFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			response.reset();
			String targetName = zipName.substring(zipName.lastIndexOf("/")+1);
			targetName = new String(targetName.getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + targetName + "\"");
			response.addHeader("Content-lenth", "" + buffer.length);
			response.setContentType("application/octet-stream;charset=UTF-8");

			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);
			os.flush();
			os.close();
			response.flushBuffer();
	String cc=fileName.substring(0,fileName.lastIndexOf(".")).toUpperCase();
			sysLogMapper.deleteDownloadLogTaskByDeviceSn(fileName.substring(0,fileName.lastIndexOf(".")).toUpperCase());
		}catch(Exception e){
			e.printStackTrace();
		}



	}

}
