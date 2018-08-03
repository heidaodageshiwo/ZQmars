package com.iekun.ef.service;

import java.util.*;

import com.iekun.ef.dao.UeInfoShardingDao;
import com.iekun.ef.util.TimeUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.SiteUser;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.util.CommonConsts;

import javax.annotation.PostConstruct;

@Service
public class HistroyDataService {

	 @Autowired
	  private UeInfoMapper ueInfoMapper;
	 
	 @Autowired
	 private ExportDataService exportDataServ;
	 
	 @Autowired
	 private SqlSessionFactory sqlSessionFactory;

	 @Autowired
	 DeviceService deviceService;
	 
	@Autowired
	private UeInfoShardingDao  ueInfoShardingDao;
	 
	 private static Logger logger = LoggerFactory.getLogger(HistroyDataService.class);
	/* public List<UeInfo>  getUeInfoListInfo(Map<String,Object> params){
		  
		    List<UeInfo>  ueList =(List<UeInfo>) ueInfoMapper.selectUeInfoList(params);
	        //User user=null;
	        return ueList;
	    }*/

	public JSONObject getUeInfoListInfo_debug(Integer draw, Integer length, Integer start, String startDate, String endDate,
			Integer operator, String homeOwnership, String siteSN, String deviceSN, String imsi)
	{
		JSONObject jo=new JSONObject();
		exportDataServ.exportUeInfoData(null,null, startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi, sqlSessionFactory);
		return jo;
	}
	public JSONObject getUeInfoListInfo(Integer draw, Integer length, Integer start, String startDate, String endDate,
			Integer operator, String homeOwnership, String siteSN, String deviceSN, String imsi) {
		// TODO Auto-generated method stub
		long totalCnt = 0;
		boolean status = false;
		Map<String,Object> params=new HashMap<String, Object>();
        params.put("length", length);
        params.put("start", start);
        
        if (startDate == null || startDate.isEmpty())
        {
        	params.put("startDate", null);
            params.put("endDate", null);
        }
        else
        {
        	/*startDate = startDate + ":00";
        	endDate   = endDate + ":00";*/
        	params.put("startDate", startDate.replace("/", "-"));
            params.put("endDate", endDate.replace("/", "-"));
        }	
        
        if (operator == null || operator==0)
        {
        	params.put("operator", null);
        }
        else
        {
           	params.put("operator", this.getOperatorTypeText(operator));
        }
        
        if (homeOwnership == null || homeOwnership.isEmpty())
        {
        	params.put("homeOwnership", null);
        }
        else
        {
        	params.put("homeOwnership", homeOwnership);
        }
        
        if (siteSN == null || siteSN.isEmpty())
        {
        	siteSN = null;
        }
        else
        {
        	 params.put("siteSn", siteSN);
        }
       
        if (deviceSN == null || deviceSN.isEmpty())
        {
        	params.put("deviceSn", null);
        }
        else
        {
        	params.put("deviceSn", deviceSN);
        }
        
        if (imsi == null || imsi.isEmpty())
        {
        	params.put("imsi", null);
        }
        else
        {
        	params.put("imsi", imsi);
        }
        
        Long userId = SigletonBean.getUserId();
        List<Site> sites= deviceService.getSiteListInfoByUserId(userId);
        params.put("sites", sites);
        
        JSONObject jo=new JSONObject();
        JSONArray ja = new JSONArray();
        
        if( ( startDate != null) && ( endDate != null ) &&
                (!startDate.isEmpty()) && (!endDate.isEmpty())   )
        {
	        List<UeInfo>  ueList =(List<UeInfo>) ueInfoMapper.selectUeInfoList(params);
	     
		    for (UeInfo ueInfo : ueList) {
		    	JSONObject ueInfoJo = new JSONObject();
		    	ueInfoJo.put("id", ueInfo.getId());
		    	ueInfoJo.put("imsi", ueInfo.getImsi());
		    	ueInfoJo.put("imei", ueInfo.getImei() );
		    	ueInfoJo.put("stmsi", ueInfo.getStmsi());
		    	ueInfoJo.put("mac",  ueInfo.getMac());
		    	ueInfoJo.put("latype", ueInfo.getLatype() );
		    	ueInfoJo.put("indication", ueInfo.getIndication() );
		    	ueInfoJo.put("realtime", ueInfo.getRealtime());
		    	ueInfoJo.put("captureTime", ueInfo.getCaptureTime() );
		    	ueInfoJo.put("cityName", ueInfo.getCityName());
		    	ueInfoJo.put("band", ueInfo.getBand());
		    	ueInfoJo.put("operator", ueInfo.getOperator());
		    	ueInfoJo.put("siteSN", ueInfo.getSiteSn());
		    	ueInfoJo.put("siteName", ueInfo.getSiteName());
		    	ueInfoJo.put("deviceSN", ueInfo.getDeviceSn());
		    	if(ueInfo.getDevice() != null)
		    	{
		    		ueInfoJo.put("deviceName", ueInfo.getDevice().getName());
		    	}
		    	else
		    	{
		    		ueInfoJo.put("deviceName", "未知设备名");
		    	}
		    	
		        ja.add(ueInfoJo);
		        status = true;
			}
		    if(status == true)
	        {
		    	/*jo.put("status", true);
		    	jo.put("message", "成功");
		    	jo.put("data", ja);*/
		    	
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
        	/*jo.put("status", false);
        	jo.put("message", "查询失败");*/
        	
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
			totalCnt = (long)ueInfoMapper.getUeInfoCnt(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return totalCnt;
	}
	

	
	private String getOperatorTypeText(Integer operatorType)
    {
    	/* 运营商：01- 移动 02- 电信 03- 联通 */
    	String operatorTypeText = "未知运营商";
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

	@PostConstruct
	public void start() throws Exception {
		logger.info("History Data Service is construct!\n");
		ueInfoShardingDao.initUeInfoDao();
	}

	public Long getShardingUeInfoCount(  String startTime, String endTime,
										 Integer operator, String homeOwnership,
										 String siteSN, String deviceSN, String imsi ) {
		Long ueInfoCnt = 0L;
		try{

			String operatorText = null;
			if ( operator != null && operator !=0)  {
				operatorText = this.getOperatorTypeText(operator);
			}
			
			long userId = SigletonBean.getUserId();
			ueInfoCnt = ueInfoShardingDao.getCount( startTime, endTime, operatorText, homeOwnership, siteSN, deviceSN, imsi, userId );

		} catch ( Exception e ) {
			e.printStackTrace();
			ueInfoCnt = 0L;
		}

		return ueInfoCnt;
	}
/*
	public List<UeInfo> queryShardingUeInfo(  Integer length, Integer start, String startTime, String endTime,
											  Integer operator, String homeOwnership, String siteSN, String deviceSN, String imsi ) {
		List<UeInfo> ueInfos = null;
		try{

			String operatorText = null;
			if ( operator != null && operator !=0)  {
				operatorText = this.getOperatorTypeText(operator);
			}

			long userId = SigletonBean.getUserId();
			ueInfos = ueInfoShardingDao.getUeInfo( startTime, endTime, operatorText,
					homeOwnership, siteSN, deviceSN, imsi,start, length,userId );

		} catch ( Exception e ) {
			e.printStackTrace();
			ueInfos = new LinkedList<>();
		}
		return ueInfos;
	}*/

	/*public List<UeInfo> queryShardingUeInfo(   String startTime, String endTime,
											  Integer operator, String homeOwnership, String siteSN, String deviceSN, String imsi ) {
		List<UeInfo> ueInfos = null;
		try{

			String operatorText = null;
			if ( operator != null && operator !=0)  {
				operatorText = this.getOperatorTypeText(operator);
			}

			long userId = SigletonBean.getUserId();
			ueInfos = ueInfoShardingDao.getUeInfo( startTime, endTime, operatorText,
					homeOwnership, siteSN, deviceSN, imsi,userId );

		} catch ( Exception e ) {
			e.printStackTrace();
			ueInfos = new LinkedList<>();
		}
		return ueInfos;
	}*/



	public Map queryShardingUeInfo(   String startTime, String endTime,
											   Integer operator, String homeOwnership, String siteSN, String deviceSN, String imsi ) {
		List<UeInfo> ueInfos = null;

			String operatorText = null;
			if ( operator != null && operator !=0)  {
				operatorText = this.getOperatorTypeText(operator);
			}

			long userId = SigletonBean.getUserId();
			Map 	map  = ueInfoShardingDao.getUeInfo( startTime, endTime, operatorText,
					homeOwnership, siteSN, deviceSN, imsi,userId );

		return map;
	}


	public List<UeInfo> queryShardingUeInfomap(   Map map) {
		List<UeInfo> ueInfos = null;
		try{



			long userId = SigletonBean.getUserId();
			ueInfos = ueInfoShardingDao.getShardingUeInfoList(map);

		} catch ( Exception e ) {
			e.printStackTrace();
			ueInfos = new LinkedList<>();
		}
		return ueInfos;
	}



}
