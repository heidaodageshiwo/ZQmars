package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.model.AreaCode;
import com.iekun.ef.model.ExportTaskInfo;
import com.iekun.ef.model.SelfAreaCode;
import com.iekun.ef.model.Site;
import com.iekun.ef.service.ExportTask;
import com.iekun.ef.service.UtilService;
import com.iekun.ef.controller.SigletonBean;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jiangqi.yang  on 2016/11/2.
 */

@Controller
@RequestMapping("/util")
public class UtilController {

	private static Logger logger = LoggerFactory.getLogger(UtilController.class);

    @Autowired
    private UtilService utilService;
    
    @Autowired
    private ExportTask exportTask;
    
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    
    @RequestMapping("/getProvinces")
    @ResponseBody
    public JSONObject getProvinces() {
     
        List<AreaCode> provinceList = utilService.getProvinceListInfo();
        JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
	    for (AreaCode province : provinceList) {
	    	JSONObject provinceJo = new JSONObject();
	       	provinceJo.put("id", province.getId());
	    	provinceJo.put("text", province.getShortName());
	        ja.add(provinceJo);
		}
	    
	    jo.put("status", true);
	    jo.put("message", "成功");
	    jo.put("data", ja);
	  
		return jo;

    }

    @RequestMapping("/getCities")
    @ResponseBody
    public JSONObject getCities( @RequestParam(value = "provinceId", required = true ) Integer provinceId ) {
    	List<AreaCode> cityList = utilService.getCityListInfo(Long.valueOf(provinceId));
        JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
		for (AreaCode city : cityList) {
			JSONObject cityJo = new JSONObject();
			cityJo.put("id", city.getId());
			cityJo.put("text", city.getShortName());
		    ja.add(cityJo);
		}
		
		jo.put("status", true);
		jo.put("message", "成功");
		jo.put("data", ja);
		  
	    return jo;
    }

    @RequestMapping("/getTowns")
    @ResponseBody
    public JSONObject getTowns( @RequestParam(value = "cityId", required = true ) Integer cityId ) {
    	List<AreaCode> townList = utilService.getTownListInfo(Long.valueOf(cityId));
        JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
		for (AreaCode town : townList) {
			JSONObject townJo = new JSONObject();
			townJo.put("id", town.getId());
			townJo.put("text", town.getShortName());
		    ja.add(townJo);
		}
		
		jo.put("status", true);
		jo.put("message", "成功");
		jo.put("data", ja);
		  
	    return jo;
    }
		
	@RequestMapping("/getCityCodes")	
	@ResponseBody
	public JSONObject getCityCodes(){
		//JSONObject jsonObject = new JSONObject();
		//// TODO: 2016/11/9
		
		List<SelfAreaCode> selfCityCodeList = utilService.getCityCodeList();
        JSONObject jo=new JSONObject();
		JSONArray ja = new JSONArray();
		for (SelfAreaCode selfcityCode : selfCityCodeList) {
			JSONObject cityCodeJo = new JSONObject();
			cityCodeJo.put("cityCode",  selfcityCode.getProvinceCode().trim() + selfcityCode.getCityCode().trim());
			cityCodeJo.put("cityName", selfcityCode.getCityName());
		    ja.add(cityCodeJo);
		}
		
		jo.put("status", true);
		jo.put("message", "成功");
		jo.put("data", ja);
		  
	    return jo;
		//jsonObject = com.iekun.ef.test.ui.UITestUtilData.getCityCodes();
		//return jsonObject;
	}


	@RequestMapping("/getAreas")
	@ResponseBody
	public JSONObject getAreas() {

		JSONObject jsonObject  = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		List<AreaCode> areaCodeList = utilService.getAreaCodeList();
		for ( AreaCode areaCode: areaCodeList ) {
			JSONObject area  = new JSONObject();
			area.put("areaId", areaCode.getId());
			area.put("areaName", areaCode.getMergerName());
			jsonArray.add(area);
		}

		jsonObject.put("status", true);
		jsonObject.put("message", "成功");
		jsonObject.put("data", jsonArray);

		return jsonObject;
	}


	@RequestMapping("/getTaskProgress")
	@ResponseBody
	public JSONObject getTaskProgress(
			@RequestParam(value = "taskId", required = true ) Integer taskId
	){
		//// TODO: 2016/11/22
		JSONObject jsonObject = new JSONObject();
		JSONObject dataObject = new JSONObject();


		dataObject.put("taskId", 122 );
		dataObject.put("valueMin", 0 );
		dataObject.put("valueMax", 100 );
		dataObject.put("valueNow", 20 );

		jsonObject.put("status", true);
		jsonObject.put("message", "成功");
		jsonObject.put("data", dataObject);

		return jsonObject;
	}

	private synchronized String getExportId(){
		long exportId = SigletonBean.exportId++;
		if(exportId > SigletonBean.maxExportId){
			SigletonBean.exportId = 1;
			exportId = 2;
		}
		return String.valueOf(exportId);
	}
	
	@RequestMapping("/exportData")
	@ResponseBody
	public JSONObject exportData(
			@RequestParam(value = "type", required = true ) Integer type,
			@RequestParam(value = "fileType", required = true ) Integer fileType,
			@RequestParam(value = "condition", required = true ) String condition
	) {

		JSONObject jsonObject = new JSONObject();
		JSONObject dataObject = new JSONObject();
		String  startDate;
		String  endDate;
		String  homeOwnership;
		String  siteSN;
		String  deviceSN;
		String  imsi;
		Integer operator;
		String  ruleName;

		String  exportId = null;
		
		if(type.equals(0))
		{
			
			JSONObject jsonObj = JSONObject.parseObject(condition);
			startDate = jsonObj.get("startTime").toString();
			endDate   =  jsonObj.get("endTime").toString();
			homeOwnership = jsonObj.get("homeOwnership").toString();
			siteSN = jsonObj.get("siteSN").toString();
			deviceSN = jsonObj.get("deviceSN").toString();
			imsi = jsonObj.get("imsi").toString();
			operator =  Integer.valueOf(jsonObj.get("operator").toString());
			exportId  = this.getExportId();
			ExportTaskInfo exportTaskInfo = new ExportTaskInfo();
			SigletonBean.exportInfoMaps.put(exportId, exportTaskInfo);
			exportTask.exeUeInfoExport(fileType, exportId, startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi, sqlSessionFactory);	
			
		}
		else if(type.equals(1)) 
		{
			JSONObject jsonObj = JSONObject.parseObject(condition);
			startDate = jsonObj.get("startTime").toString();
			endDate   =  jsonObj.get("endTime").toString();
			homeOwnership = jsonObj.get("homeOwnership").toString();
			siteSN = jsonObj.get("siteSN").toString();
			deviceSN = jsonObj.get("deviceSN").toString();
			imsi = jsonObj.get("imsi").toString();
			ruleName = jsonObj.get("ruleName").toString();
			exportId  = this.getExportId();
			ExportTaskInfo exportTaskInfo = new ExportTaskInfo();
			SigletonBean.exportInfoMaps.put(exportId, exportTaskInfo);
			exportTask.exeTargetInfoExport(fileType, exportId, startDate, endDate, ruleName, homeOwnership, siteSN, deviceSN, imsi, sqlSessionFactory);
		}
		else if (type.equals(2))
		{
			JSONObject jsonObj = JSONObject.parseObject(condition);
			startDate = jsonObj.get("startTime").toString();
			endDate   =  jsonObj.get("endTime").toString();
			String userName  = jsonObj.get("username").toString();
			String logType  = jsonObj.get("type").toString();
			String deviceSn = jsonObj.get("deviceSn").toString(); 
			
			exportId  = this.getExportId();
			ExportTaskInfo exportTaskInfo = new ExportTaskInfo();
			SigletonBean.exportInfoMaps.put(exportId, exportTaskInfo);
			exportTask.exeLogExport(fileType, exportId, startDate, endDate, userName, logType, deviceSn, sqlSessionFactory);

		}
		else
		{
			dataObject.put("exportId", null);
			jsonObject.put("status", false);
			jsonObject.put("message", "失败");
		    return jsonObject;
		}
							
		dataObject.put("exportId", exportId );
		jsonObject.put("status", true);
		jsonObject.put("message", "成功");
		jsonObject.put("data", dataObject);

		return jsonObject;
	}

	@RequestMapping("/exportDataProgress")
	@ResponseBody
	public JSONObject exportDataProgress(
			@RequestParam(value = "exportId", required = true ) Long exportId
	) {

		JSONObject jsonObject = new JSONObject();
		JSONObject dataObject = new JSONObject();
	
		ExportTaskInfo ExportTaskInfo = (ExportTaskInfo)SigletonBean.exportInfoMaps.get(exportId.toString());
		dataObject.put("progress", ExportTaskInfo.getProgress());
		dataObject.put("url", ExportTaskInfo.getUrl());
		
		jsonObject.put("status", true);
		jsonObject.put("message", "成功");
		jsonObject.put("data", dataObject);

		return jsonObject;
	}


}
