package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.model.*;
import com.iekun.ef.service.DeviceDetailsService;
import com.iekun.ef.util.CommonConsts;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/details")
public class DeviceDetailsController {
	Map<String, Object>  DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;

	private static Logger logger = LoggerFactory.getLogger(DeviceDetailsController.class);
	@Autowired
	private DeviceDetailsService deviceDetailsService;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * 获取站点-设备详情
	 *
	 * @return
	 */
	@RequestMapping("/getSites")
	@ResponseBody
	public JSONObject getSitesOfUser() {

		String shsj = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//获取当前时间对应ue sharding表
		String table = deviceDetailsService.getTableName(shsj);

		//获取当前用户所分配的站点列表
		Long userId = SigletonBean.getUserId();
		List<SiteEntity> siteList = deviceDetailsService.getSiteEntityListByUser(userId);
		JSONObject resultJo = new JSONObject();
		JSONArray resultJa = new JSONArray();
		for (SiteEntity site : siteList) {
			JSONObject siteJo = new JSONObject();
			siteJo.put("id", site.getId());
			siteJo.put("sn", site.getSn());
			siteJo.put("name", site.getName());
			siteJo.put("longitude", site.getLongitude());
			siteJo.put("latitude", site.getLatitude());
			siteJo.put("address", site.getAddress());
			siteJo.put("remark", site.getRemark());
			siteJo.put("createTime", site.getCreateTime());
			siteJo.put("province", site.getProvince_name());
			siteJo.put("city", site.getCity_name());
			siteJo.put("town", site.getTown_name());
			siteJo.put("provinceId", site.getProvince_id());
			siteJo.put("cityId", site.getCity_id());
			siteJo.put("townId", site.getTown_id());
			siteJo.put("LC", site.getLc());
			siteJo.put("CI", site.getCi());

			//处理站点的设备列表
			List<DeviceEntity> deviceEntities = site.getDeviceEntityList();
			if (deviceEntities != null && deviceEntities.size() > 0) {
				JSONArray deviceJa = new JSONArray();

				for (DeviceEntity device : deviceEntities) {
					JSONObject deviceJo = new JSONObject();
					deviceJo.put("id", device.getId());
					deviceJo.put("sn", device.getSn());
					deviceJo.put("name", device.getName());
					deviceJo.put("type", device.getType());
					String modeTypeText = this.getModeTypeText(device.getType());
					deviceJo.put("typeText", modeTypeText);
					deviceJo.put("band", device.getBand());
					deviceJo.put("operator", device.getOperator());
					String operatorTypeText = this.getOperatorTypeText(Integer.valueOf(device.getOperator()));
					deviceJo.put("operatorText", operatorTypeText);
					deviceJo.put("manufacturer", device.getManufacturer());
					deviceJo.put("remark", device.getRemark());
					deviceJo.put("createTime", device.getCreateTime());

					//通过device.getSn()编号去查询 上号数据
					String deviceSn = device.getSn();

					//h获取上号统计数据 TODO
					List<DeviceDetailsCount> DeviceDetailsCountlist = deviceDetailsService.getDeviceDetailsCountlist(deviceSn, table);
					//if(DeviceDetailsCountlist.size()==0){
					deviceJo.put("yd", "0");
					deviceJo.put("lt", "0");
					deviceJo.put("dx", "0");
					deviceJo.put("wz", "0");
					//}else{
					for (DeviceDetailsCount deviceDetailsCount : DeviceDetailsCountlist) {
						if ("中国移动".equals(deviceDetailsCount.getOperator())) {
							deviceJo.put("yd", deviceDetailsCount.getSum());
						} else if ("中国联通".equals(deviceDetailsCount.getOperator())) {
							deviceJo.put("lt", deviceDetailsCount.getSum());
						} else if ("中国电信".equals(deviceDetailsCount.getOperator())) {
							deviceJo.put("dx", deviceDetailsCount.getSum());
						} else if ("未知运营商".equals(deviceDetailsCount.getOperator())) {
							deviceJo.put("wz", deviceDetailsCount.getSum());
						}
					}
					//}

					DeviceStatus dvStatus = (DeviceStatus) DvStatusInfoMap.get(device.getSn());
					if(dvStatus != null){
						if (dvStatus.getDeviceStatusCommon().getDeviceStatus().equals("run")) {
							deviceJo.put("zt", "在线");
						} else {
							deviceJo.put("zt", "离线");
						}
					}else{
						deviceJo.put("zt", "未知");
					}

					deviceJa.add(deviceJo);
				}

				siteJo.put("devices", deviceJa);
			}
			resultJa.add(siteJo);
		}
		resultJo.put("status", true);
		resultJo.put("message", "成功");
		resultJo.put("data", resultJa);
		return resultJo;
	}

    public JSONObject getDevicesBySiteId(
    		@RequestParam(value = "siteId", required = true ) Long siteId)
    {
    	boolean status = false;
    	List<Device> deviceList= deviceDetailsService.getDeviceListInfoBySiteId(siteId);
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
			//通过device.getSn();编号去查询 上号数据
			String devicesn=device.getSn();
			String shsj= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String table = deviceDetailsService.getTableName(shsj);
				List<DeviceDetailsCount> DeviceDetailsCountlist= deviceDetailsService.getDeviceDetailsCountlist(devicesn,table);
				//if(DeviceDetailsCountlist.size()==0){
					deviceJo.put("yd","0");
					deviceJo.put("lt","0");
					deviceJo.put("dx","0");
					deviceJo.put("wz","0");
				//}else{
					for (DeviceDetailsCount deviceDetailsCount : DeviceDetailsCountlist) {
						if("中国移动".equals(deviceDetailsCount.getOperator())){
							deviceJo.put("yd",deviceDetailsCount.getSum());
						}else if("中国联通".equals(deviceDetailsCount.getOperator())){
							deviceJo.put("lt",deviceDetailsCount.getSum());
						}else if("中国电信".equals(deviceDetailsCount.getOperator())){
							deviceJo.put("dx",deviceDetailsCount.getSum());
						}else if("未知运营商".equals(deviceDetailsCount.getOperator())){
							deviceJo.put("wz",deviceDetailsCount.getSum());
						}
					}
				//}
			device.getSn();
			DeviceStatus dvStatus = (DeviceStatus)DvStatusInfoMap.get(device.getSn());
			if(dvStatus.getDeviceStatusCommon().getDeviceStatus().equals("run")){
				deviceJo.put("zt","在线");
			}else{
				deviceJo.put("zt","离线");
			}



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




	@RequestMapping("/exportData")
	@ResponseBody
	public JSONObject exportData(/*
			@RequestParam(value = "type", required = true ) Integer type,
			@RequestParam(value = "fileType", required = true ) Integer fileType,
			@RequestParam(value = "condition", required = true ) String condition*/
	) {
		JSONObject dataObject = new JSONObject();
		JSONObject jsonObject = new JSONObject();

		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		jo = getSitesList();
		ja = (JSONArray) jo.get("data");
		List<DeviceDetailsModel> DeviceDetailsModelList=new ArrayList<DeviceDetailsModel>();
		for (int i = 0; i < ja.size(); i++) {
			JSONObject jc = (JSONObject) ja.get(i);
			JSONArray jcc = (JSONArray) jc.get("devices");
			if (jcc == null) {
				DeviceDetailsModel DeviceDetailsModel1=new DeviceDetailsModel();
				DeviceDetailsModel1.setSitesn((String) jc.get("sn"));
				DeviceDetailsModel1.setSitename((String) jc.get("name"));
				DeviceDetailsModel1.setLongitude((String) jc.get("longitude"));
				DeviceDetailsModel1.setLatitude((String) jc.get("latitude"));
				DeviceDetailsModel1.setCreatetime((String) jc.get("createTime"));
				DeviceDetailsModel1.setLc((String) jc.get("LC"));
				DeviceDetailsModel1.setCi((String) jc.get("CI"));

				DeviceDetailsModel1.setDevicesn(null);
				DeviceDetailsModel1.setDevicename(null);
				DeviceDetailsModel1.setYd(null);
				DeviceDetailsModel1.setLt(null);
				DeviceDetailsModel1.setDx(null);
				DeviceDetailsModel1.setWz(null);
				DeviceDetailsModel1.setZt(null);
				DeviceDetailsModelList.add(DeviceDetailsModel1);
			}else{
				for(int j=0;j<jcc.size();j++) {
					JSONObject jcc1 = (JSONObject) jcc.get(j);


					DeviceDetailsModel DeviceDetailsModel1=new DeviceDetailsModel();
					DeviceDetailsModel1.setSitesn((String) jc.get("sn"));
					DeviceDetailsModel1.setSitename((String) jc.get("name"));
					DeviceDetailsModel1.setLongitude((String) jc.get("longitude"));
					DeviceDetailsModel1.setLatitude((String) jc.get("latitude"));
					DeviceDetailsModel1.setCreatetime((String) jc.get("createTime"));
					DeviceDetailsModel1.setLc((String) jc.get("LC"));
					DeviceDetailsModel1.setCi((String) jc.get("CI"));

					DeviceDetailsModel1.setDevicesn((String) jcc1.get("sn"));
					DeviceDetailsModel1.setDevicename((String) jcc1.get("name"));
					DeviceDetailsModel1.setYd((String) jcc1.get("yd"));
					DeviceDetailsModel1.setLt((String) jcc1.get("lt"));
					DeviceDetailsModel1.setDx((String) jcc1.get("dx"));
					DeviceDetailsModel1.setWz((String) jcc1.get("wz"));
					DeviceDetailsModel1.setZt((String) jcc1.get("zt"));
					DeviceDetailsModelList.add(DeviceDetailsModel1);

				}
			}
		}
		String  exportId = null;
		exportId  = this.getExportId();
		ExportTaskInfo exportTaskInfo = new ExportTaskInfo();
		SigletonBean.exportInfoMaps.put(exportId, exportTaskInfo);
		deviceDetailsService.exportData(exportId,DeviceDetailsModelList);
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

	private synchronized String getExportId(){
		long exportId = SigletonBean.exportId++;
		if(exportId > SigletonBean.maxExportId){
			SigletonBean.exportId = 1;
			exportId = 2;
		}
		return String.valueOf(exportId);
	}

	public JSONObject getSitesList(){
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
			deviceJoObj = this.getDevicesBySiteIdList(site.getId());
			if (deviceJoObj.getBooleanValue("status") == true)
			{
				siteJo.put("devices",deviceJoObj.getJSONArray("data"));
			}else{
                siteJo.put("devices",null);
            }
			ja.add(siteJo);
		}
		jo.put("status", true);
		jo.put("message", "成功");
		jo.put("data", ja);
		return jo;
	}


	public JSONObject getDevicesBySiteIdList(
			@RequestParam(value = "siteId", required = true ) Long siteId)
	{
		boolean status = false;
		List<Device> deviceList= deviceDetailsService.getDeviceListInfoBySiteId(siteId);
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
			//通过device.getSn();编号去查询 上号数据
			String devicesn=device.getSn();
			String shsj= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String table = deviceDetailsService.getTableName(shsj/*shsj.substring(0,4)+"-"+shsj.substring(4,6)+"-"+shsj.substring(6,8)*/);
			List<DeviceDetailsCount> DeviceDetailsCountlist= deviceDetailsService.getDeviceDetailsCountlist(devicesn,table);
			//if(DeviceDetailsCountlist.size()==0){
			deviceJo.put("yd","0");
			deviceJo.put("lt","0");
			deviceJo.put("dx","0");
			deviceJo.put("wz","0");
			//}else{
			for (DeviceDetailsCount deviceDetailsCount : DeviceDetailsCountlist) {
				if("中国移动".equals(deviceDetailsCount.getOperator())){
					deviceJo.put("yd",deviceDetailsCount.getSum());
				}else if("中国联通".equals(deviceDetailsCount.getOperator())){
					deviceJo.put("lt",deviceDetailsCount.getSum());
				}else if("中国电信".equals(deviceDetailsCount.getOperator())){
					deviceJo.put("dx",deviceDetailsCount.getSum());
				}else if("未知运营商".equals(deviceDetailsCount.getOperator())){
					deviceJo.put("wz",deviceDetailsCount.getSum());
				}
			}
			//}
			device.getSn();
			DeviceStatus dvStatus = (DeviceStatus)DvStatusInfoMap.get(device.getSn());
			if(dvStatus.getDeviceStatusCommon().getDeviceStatus().equals("run")){
				deviceJo.put("zt","在线");
			}else{
				deviceJo.put("zt","离线");
			}
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
}
