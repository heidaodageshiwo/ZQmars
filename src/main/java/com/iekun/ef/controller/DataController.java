package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iekun.ef.dao.DataTablePager;
import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.jms.service.HandleRcvUeInfoService;
import com.iekun.ef.jms.vo.receive.RcvUeInfo;
import com.iekun.ef.model.*;
import com.iekun.ef.service.HistroyDataService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;

import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.CommonConsts;

import com.iekun.ef.util.M16Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jiangqi.yang  on 2016/10/26.
 */

@Controller
public class DataController {

    private static Logger logger = LoggerFactory.getLogger(DataController.class);
    
    private static final int onetime = 20;
    
    @Autowired
    private HistroyDataService  histroyDataService;
    
    @Autowired
	private ResourceService resourceServ;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DeviceMapper deviceMapper;
	
	@Autowired
	private HandleRcvUeInfoService handleRcvUeInfoServ;

    @Autowired
    private ComInfoUtil comInfoUtil;

    @RequestMapping("/realTimeData")
    public String realTimeData( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/10/26
        /* 测试数据-start */
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        /* 测试数据-end */

        return "business/realTimeData";
    }

    @RequestMapping("/historyData")
    public String historyData( Map<String, Object> model ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/10/26
        /* 测试数据-start */
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        /* 测试数据-end */
        return "business/historyData";
    }

	@RequestMapping("/newHistoryData")
	public String newHistoryData( Map<String, Object> model ){
		comInfoUtil.getTopHeaderInfo( model );
		comInfoUtil.getSystemInfo( model );
		//// TODO: 2016/10/26
        /* 测试数据-start */
		long roleId = SigletonBean.getRoleId();
		resourceServ.getResourceInfoByRoleId(model, roleId);
		model.put("user", userService.getUserDetailInfo());
        /* 测试数据-end */
		return "business/newHistoryData";
	}

   /* @RequestMapping("/queryHistoryData")
    @ResponseBody
    public JSONObject queryHistoryData(
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "start", required = false ) Integer start,
            @RequestParam(value = "startDate", required = false ) String startDate,
            @RequestParam(value = "endDate", required = false ) String endDate,
            @RequestParam(value = "operator", required = false ) Integer operator,
            @RequestParam(value = "homeOwnership", required = false ) String homeOwnership,
            @RequestParam(value = "siteSN", required = false ) String siteSN,
            @RequestParam(value = "deviceSN", required = false ) String deviceSN,
            @RequestParam(value = "imsi", required = false ) String imsi
    ) {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

		long wholeQuerySTime = System.currentTimeMillis();

		if( startDate != null && endDate != null ) {

           Long recordsTotal = histroyDataService.getShardingUeInfoCount( startDate.replace("/", "-"),
				   endDate.replace("/", "-"), operator, homeOwnership, siteSN, deviceSN, imsi );
			if( recordsTotal > 0) {

				List<UeInfo> ueInfos = histroyDataService.queryShardingUeInfo( length, start ,
						startDate.replace("/", "-"), endDate.replace("/", "-"),
						operator, homeOwnership, siteSN, deviceSN, imsi );

				for (UeInfo ueInfo : ueInfos) {
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
					if (ueInfo.getDevice() != null)
					{
						ueInfoJo.put("deviceName", ueInfo.getDevice().getName());
					}
					else
					{
						ueInfoJo.put("deviceName", "未知设备");
					}
					ja.add(ueInfoJo);
				}

			}

			jo.put("draw", draw);
			jo.put("recordsTotal", recordsTotal);
			jo.put("recordsFiltered", recordsTotal);
			jo.put("data", ja);

		} else {
			jo.put("draw", draw);
			jo.put("recordsTotal", 0);
			jo.put("recordsFiltered", 0);
			jo.put("data", ja);
		}

		long wholeQueryETime = System.currentTimeMillis();
		this.logger.info("总查询结束，共耗时" + (wholeQueryETime - wholeQuerySTime) + "ms");

        return jo;
    }*/

   @RequestMapping("/getRealTimeData")
   @ResponseBody
   public JSONObject getRealTimeData(
		   @RequestParam(value = "type", required = true ) Integer type,   //1-实时，0-缓存
		   @RequestParam(value = "sn[]", required = false ) String[]  sn
   ){
       JSONObject jsonObject  = new JSONObject();
       //JSONArray   dataObj = new JSONArray(); 
       JSONArray   dataObj =  this.getNewRecord(deviceMapper, type, sn);
       jsonObject.put("status", true);
       jsonObject.put("message", "成功");
       jsonObject.put("data", dataObj);
       return jsonObject;
   }

   
   private DeviceSiteDetailInfo getDvSiteDetailInfo(String deviceSn, DeviceMapper deviceMapper)
   {
   	
	    String operatorText; 
	    DeviceSiteDetailInfo DvSiteInfo = new DeviceSiteDetailInfo();
   	
	    Device deviceDetail = (Device)SigletonBean.deviceSiteInfoMaps.get(deviceSn);
	    if (deviceDetail != null)
	    {
	    	DvSiteInfo.setDeviceName(deviceDetail.getName());
	    	DvSiteInfo.setDeviceSn(deviceSn);
	    	operatorText = CommonConsts.getOperatorTypeText(Integer.valueOf(deviceDetail.getOperator()));
	    	DvSiteInfo.setOperator(operatorText);
	    	DvSiteInfo.setSiteCity(deviceDetail.getSite().getCity_name());
	    	DvSiteInfo.setSiteName(deviceDetail.getSite().getName());
	    }
      return DvSiteInfo;
   }
   
   private JSONObject  getShowUeInfoObj(RcvUeInfo ueInfo, DeviceMapper deviceMapper)
	{
		String displayUeInfo = null;
		String proviceCode;
		String cityCode;
		int isAlarm = 0;
		String cityStr = null;
		
		proviceCode =  CommonConsts.getAlignCharText(ueInfo.getCode_province());
		cityCode =  CommonConsts.getAlignCharText(ueInfo.getCode_city());
		SelfAreaCode selfAreaCode = (SelfAreaCode)SigletonBean.selfAreaCodeMaps.get(proviceCode+cityCode);
		if (selfAreaCode == null)
		{
			logger.info("未知的城市编码  proviceCode :" + proviceCode + "cityCode :" + cityCode);
			cityStr = "未知城市";
		}
		else
		{
			cityStr = selfAreaCode.getCityName();
		}
		
		DeviceSiteDetailInfo DvSiteInfo = this.getDvSiteDetailInfo(ueInfo.getSerialNum(), deviceMapper);
		if((ueInfo.getBoolLib() == 1)||(ueInfo.getBoolLocaleInlib() == 1))
		{
			isAlarm = 1;
		}
		
		JSONObject ueInfoShowJo = new JSONObject();
		ueInfoShowJo.put("imsi", ueInfo.getUeImsi());
		ueInfoShowJo.put("indication", isAlarm);
		ueInfoShowJo.put("operatorText", DvSiteInfo.getOperator());
		ueInfoShowJo.put("cityName", cityStr);
		ueInfoShowJo.put("siteName", DvSiteInfo.getSiteName());
		ueInfoShowJo.put("deviceName", DvSiteInfo.getDeviceName());
		ueInfoShowJo.put("captureTime", ueInfo.getCaptureTime());
			
		return ueInfoShowJo; 
	}
	
	private void handleCurrentList(List<RcvUeInfo> ListRecvUeinfo, String[] sn)
	{
		Iterator iterator = ListRecvUeinfo.iterator();
		while(iterator.hasNext()){
			RcvUeInfo recvUeinfo = (RcvUeInfo)(iterator.next());
			if(this.isFilterImsi(recvUeinfo.getSerialNum(), sn) == false)
			{
				 iterator.remove();//此处采用iterator本身的remove方法，可以完美解决上述问题
			}
		   
		}
	}
	
	public JSONArray getNewRecord(DeviceMapper deviceMapper, Integer type, String[] sn){
		
		JSONArray ja = new JSONArray();
		
		Queue<RcvUeInfo> seekedQueue = null;
		//Queue<RcvUeInfo> seekedFilterQueue = null;
		List<RcvUeInfo>  currentList = null;
		int nowCount;
		int in;
		int removeCnt;
		int enterCnt;
		Map<String, Object> showmap;
	
		if(type == 0)
		{
			showmap = SigletonBean.showbuffmaps;
		}
		else
		{
			showmap = SigletonBean.showmaps;
		}

		/*根据userid查找map表，如果map表中找到已经有该用户的队列queue，则数据入队列，否则创建队列，再把数据插入队列*/
		long currentUserId = SigletonBean.getUserId();
		currentList = (List<RcvUeInfo>)showmap.get(String.valueOf(currentUserId));
		
		if(null == currentList)
		{
			/* 没找到对应的链表，则申请新的链表，并插入*/
			currentList = new ArrayList<RcvUeInfo>();
			showmap.put(String.valueOf(currentUserId), currentList);
		}
		else
		{
			/* 判断currentList中的数据是否在输入的sn列表范围之内，不是则清除出去 ，是的则保留  */
			this.handleCurrentList(currentList, sn);
			
		}
		
		//seekedFilterQueue = (Queue<RcvUeInfo>)SigletonBean.maps.get(String.valueOf(currentUserId));
	    
		seekedQueue = this.getFilterUeInfo(type, sn, currentUserId);
		if(null == seekedQueue)
		{
			return ja;
		}
		
		if (0 != seekedQueue.size())
		{
			nowCount = currentList.size();
			in = (seekedQueue.size() - onetime > 0)?onetime:seekedQueue.size();
			
			if ((in + nowCount) <= onetime)
			{
				/*in个元素直接入队列*/
				for(int i = 0; i < in; i++){
					currentList.add(0, seekedQueue.poll());
					
				}
				
			}
			else
			{
				/* 计算需要移除的个数 ，新进入数据的个数 */
				if (in >= onetime)
				{
					removeCnt = nowCount;
					enterCnt  = onetime;
				}
				else
				{
					/* 新进入数据优先显示， onetime-in只是为保存之前数据的多余空间，
					  所以应该移除的数据个数应该是 nowCount - (onetime-in) */
					removeCnt = nowCount - (onetime-in);
					enterCnt = in;
				}
			
				for(int i = 0; i < removeCnt; i++){
					currentList.remove(nowCount-1-i);
				} 
				
				for(int i = 0; i < enterCnt; i++){
					currentList.add(0, seekedQueue.poll());
				}
			}
			
			
		}
		
		for (RcvUeInfo ueInfo : currentList) {
			
			JSONObject ueInfoJo = this.getShowUeInfoObj(ueInfo, deviceMapper);
			ja.add(ueInfoJo);
		
		}
	
		

		 return ja;
	}
	
	
	@SuppressWarnings("unchecked")
	private Queue<RcvUeInfo> getFilterUeInfo(Integer type, String[] sn, long userId)
	{
		/* 求出seekedQueue中满足条件的记录，如果满足条件的记录多于20条，则只取20条
		 * 1、遍历 seekedQueue，逐条记录比对；
		 * 2、如果记录满足条件则入filter队列，如果不满足条件则记录被抛弃
		 * 3、直到取满20条记录或者队列里的数据遍历完。
		 * 还有一点需要考虑，就是过滤条件第一次起动过滤时，原来显示列表中的记录是否需要清空？
		 * */
		Queue<RcvUeInfo> seekedFilterQueue = new LinkedBlockingQueue<RcvUeInfo>();
		Queue<RcvUeInfo> seekedQueue = null;
		RcvUeInfo rcvUeInfo = null;
	    int typeFilter  =  (type == 0)? 1:0;
	    int filterTotalCnt = 0;
	    int totalCnt = 0;
	    int acturalTotalCnt = 0;
		
		seekedQueue = (Queue<RcvUeInfo>)(SigletonBean.maps.get(String.valueOf(userId)));
		if(seekedQueue == null)
		{
			return null;
		}
		
		/* 如果队列中多于 500个,则只保留后500个。
		totalCnt =seekedQueue.size();
		if(totalCnt > SigletonBean.maxCount){
			for(int j =0; j < (totalCnt - SigletonBean.maxCount); j++)
			{
				seekedQueue.poll();
			}
			
		}*/
		
		acturalTotalCnt = seekedQueue.size();
		for(int i=0; i < acturalTotalCnt; i++)
		{
			rcvUeInfo = seekedQueue.poll();
			
			if(rcvUeInfo == null)
			{
				logger.info(" rcvUeInfo  is null and acturalTotalCnt = "+ acturalTotalCnt);
				seekedQueue.clear();
				return seekedFilterQueue;
			}
			if(rcvUeInfo.getBoolsys() == typeFilter)
			{
				if(this.isFilterImsi(rcvUeInfo.getSerialNum(), sn) == true)
				{
					filterTotalCnt ++;
					seekedFilterQueue.add(rcvUeInfo);
					if(filterTotalCnt == 20)
					{
					  break;
					}
				}
			}
		}
		
		return seekedFilterQueue;
	}
  
	private boolean isFilterImsi(String reportDevSn, String[] sn)
	{
		if (sn == null)
		{
			return true;
		}
		
		for(int i = 0; i < sn.length; i ++)
		{
			if(reportDevSn.equals(sn[i]) == true)
			{
				
				return true;
			}
		}
		
		return false;
	}
	/*//可疑人员分析
	@RequestMapping("/analysisData")
	public String analysisData( Map<String, Object> model ){

		comInfoUtil.getTopHeaderInfo( model );
		comInfoUtil.getSystemInfo( model );
		long roleId = SigletonBean.getRoleId();
		resourceServ.getResourceInfoByRoleId(model, roleId);
		model.put("user", userService.getUserDetailInfo());
		return "business/analysisData";
	}
	@RequestMapping("/queryAnalysisiData")
	@ResponseBody
	public JSONObject queryAnalysisiData(
			@RequestParam(value = "draw", required = false ) Integer draw,
			@RequestParam(value = "length", required = false ) Integer length
			*//*@RequestParam(value = "start", required = false ) Integer start,
			@RequestParam(value = "startDate", required = false ) String startDate,
			@RequestParam(value = "endDate", required = false ) String endDate,
			@RequestParam(value = "operator", required = false ) Integer operator,
			@RequestParam(value = "homeOwnership", required = false ) String homeOwnership,
			@RequestParam(value = "siteSN", required = false ) String siteSN,
			@RequestParam(value = "deviceSN", required = false ) String deviceSN,
			@RequestParam(value = "imsi", required = false ) String imsi*//*
	) {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

		long wholeQuerySTime = System.currentTimeMillis();

		*//*if( startDate != null && endDate != null ) {

			Long recordsTotal = histroyDataService.getShardingUeInfoCount( startDate.replace("/", "-"),
					endDate.replace("/", "-"), operator, homeOwnership, siteSN, deviceSN, imsi );
			if( recordsTotal > 0) {

				List<UeInfo> ueInfos = histroyDataService.queryShardingUeInfo( length, start ,
						startDate.replace("/", "-"), endDate.replace("/", "-"),
						operator, homeOwnership, siteSN, deviceSN, imsi );

				for (UeInfo ueInfo : ueInfos) {
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
					if (ueInfo.getDevice() != null)
					{
						ueInfoJo.put("deviceName", ueInfo.getDevice().getName());
					}
					else
					{
						ueInfoJo.put("deviceName", "未知设备");
					}
					ja.add(ueInfoJo);
				}

			}

			jo.put("draw", draw);
			jo.put("recordsTotal", recordsTotal);
			jo.put("recordsFiltered", recordsTotal);
			jo.put("data", ja);

		} else {
			jo.put("draw", draw);
			jo.put("recordsTotal", 0);
			jo.put("recordsFiltered", 0);
			jo.put("data", ja);
		}*//*

		long wholeQueryETime = System.currentTimeMillis();
		this.logger.info("总查询结束，共耗时" + (wholeQueryETime - wholeQuerySTime) + "ms");

		return jo;
	}*/
	@RequestMapping("/queryHistoryData")
	@ResponseBody
	public JSONObject queryHistoryData(
			@RequestParam(value = "draw", required = false ) Integer draw,
			@RequestParam(value = "length", required = false ) Integer length,
			@RequestParam(value = "start", required = false ) Integer start,
			@RequestParam(value = "startDate", required = false ) String startDate,
			@RequestParam(value = "endDate", required = false ) String endDate,
			@RequestParam(value = "operator", required = false ) Integer operator,
			@RequestParam(value = "homeOwnership", required = false ) String homeOwnership,
			@RequestParam(value = "siteSN", required = false ) String siteSN,
			@RequestParam(value = "deviceSN", required = false ) String deviceSN,
			@RequestParam(value = "imsi", required = false ) String imsi,HttpServletRequest request
	) {

		String formData = request.getParameter("formData");
		Map<String, String> params = (Map<String, String>)JSONObject.parse(formData);
		JSONObject jsonObject  = new JSONObject();

		//分页处理
		int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
		int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
		String sEcho = request.getParameter("sEcho");
		int startNum = iDisplayStart / iDisplayLength + 1;

				DataTablePager page = new DataTablePager();


				Map map = histroyDataService.queryShardingUeInfo(
				params.get("startDate").replace("/", "-"), params.get("endDate").replace("/", "-"),
				Integer.parseInt(params.get("operator")), params.get("homeOwnership"), params.get("siteSN"), params.get("deviceSN"), params.get("imsi") );

				PageHelper.startPage(startNum, iDisplayLength);
				List<UeInfo> ueInfos=histroyDataService.queryShardingUeInfomap(map);


				PageInfo pg = new PageInfo(ueInfos);
				page.setDataResult(ueInfos);
				page.setiTotalRecords(pg.getTotal());
				page.setiTotalDisplayRecords(pg.getTotal());
				page.setiDisplayLength(iDisplayLength);
				page.setsEcho(sEcho);
//
//		if( params.get("startDate") != null && params.get("endDate") != null ) {
//
//			Long recordsTotal = histroyDataService.getShardingUeInfoCount(params.get("startDate").replace("/", "-"),
//					params.get("endDate").replace("/", "-"), Integer.parseInt(params.get("operator")), params.get("homeOwnership"), params.get("siteSN"), params.get("deviceSN"), params.get("imsi"));
//			if (recordsTotal > 0) {
//
//				List<UeInfo> ueInfos = histroyDataService.queryShardingUeInfo(
//						params.get("startDate").replace("/", "-"), params.get("endDate").replace("/", "-"),
//						Integer.parseInt(params.get("operator")), params.get("homeOwnership"), params.get("siteSN"), params.get("deviceSN"), params.get("imsi") );
//
//
//				List<UeInfo> records  =ueInfos;
//				PageInfo pg = new PageInfo(records);
//
//				page.setDataResult(records);
//				page.setiTotalRecords(pg.getTotal());
//				page.setiTotalDisplayRecords(pg.getTotal());
//				page.setiDisplayLength(iDisplayLength);
//				page.setsEcho(sEcho);
//
//
//
//			}else{
//				List<UeInfo> records  = new ArrayList<UeInfo>();
//				PageInfo pg = new PageInfo(records);
//
//				page.setDataResult(records);
//				page.setiTotalRecords(pg.getTotal());
//				page.setiTotalDisplayRecords(pg.getTotal());
//				page.setiDisplayLength(iDisplayLength);
//				page.setsEcho(sEcho);
//			}
//
//
//		}else{
//			List<UeInfo> records  = new ArrayList<UeInfo>();
//			PageInfo pg = new PageInfo(records);
//
//			page.setDataResult(records);
//			page.setiTotalRecords(pg.getTotal());
//			page.setiTotalDisplayRecords(pg.getTotal());
//			page.setiDisplayLength(iDisplayLength);
//			page.setsEcho(sEcho);
//		}



		return (JSONObject)JSONObject.toJSON(page);



	}









}
