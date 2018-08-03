package com.iekun.ef.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.model.*;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.ShardingTabInfoMapper;
import com.iekun.ef.dao.SysLogMapper;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.util.CommonConsts;
import com.iekun.ef.util.TimeUtils;
import com.iekun.ef.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.iekun.ef.util.PropertyUtil;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ExportDataService {

	private static Logger logger = LoggerFactory.getLogger(ExportDataService.class);
	
	@Value(" ${export.baseTablePath}")
	private String baseTablePathStr;
	
	@Value(" ${export.ueInfoTablePath}")
	private String ueInfoTablePathStr;
	
	
	@Value(" ${export.targetInfoTablePath}")
	private String targetInfoTablePathStr;
	
	@Value(" ${storage.tempPath}")
	private String strorageTempPath;
	
	@Value(" ${storage.basePath}")
	private String StringBasePath; 
	
	//@Value(" ${ftpserver.ip}")
	private String ftpServerIp;
	
	//@Value(" ${ftpserver.port}")
	private String ftpServerPort;
	
	@Value("${export.logTablePath}")
	private String logTablePath;
	
    @Autowired
    private SystemParaService sysParaServ;
    
	@Autowired
	private UeInfoMapper ueInfoMapper;
	
	@Autowired
	private TargetAlarmMapper targetAlarmMapper;
	
	@Autowired 
	private ShardingTabInfoMapper shardingTabInfoMapper;
	
	@Autowired
	private SysLogMapper sysLogMapper;

	@Autowired
	private TargetAlarmService  targetAlarmServ;
	@Autowired
	private UserService userServ;
	
	@Autowired
	private DeviceService deviceService;

	@Autowired
	private DeviceDetailsService deviceDetailsService;

	
	public static final int maxCountInsingleFile = 1000000;
	
	public  String exportBaseData(String userData, String siteData, String blacklistData, String homeOwnershipData)
	{
		String tableStr = null;
		String destUrl = null;
		if ((userData == null) && (siteData == null)&&(blacklistData == null) && (homeOwnershipData == null))
		{
			return null;			
		}
		
		if (userData != null)
		{
			tableStr = "t_user";
		}
		
		if (siteData != null)
		{
			if(tableStr == null)
			{
				tableStr = "t_site" + " " + "t_device" ;
			}
			else
			{
				tableStr = tableStr + " "+ "t_site" + " " + "t_device";
			}
			
		}
		
		if (blacklistData != null)
		{
			
			if(tableStr == null)
			{
				tableStr = "t_target_rule";
			}
			else
			{
				tableStr = tableStr + " "+ "t_target_rule";
			}
			
		}
		
		if (homeOwnershipData != null)
		{
			if(tableStr == null)
			{
				tableStr = "t_area_rule";
			}
			else
			{
				tableStr = tableStr + " "+ "t_area_rule";
			}
	
		}
		
		String filePath = baseTablePathStr.trim() +  "/" + TimeUtils.timeFormatterStr.format( new Date() );
		String destPath = filePath + "/" +"baseData.txt";
		File file=new File(filePath);

		boolean pathMake = file.mkdirs();
		try {
			Runtime.getRuntime().exec("chmod 777 " + filePath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("is execute allow : " + file.canExecute());  
        System.out.println("is read allow : " + file.canRead());  
        System.out.println("is write allow : " + file.canWrite());  
		if (pathMake == false)
		{
			logger.info(" create path fail!!");
		}
		
	    this.exportBaseTable2File(tableStr, destPath);
	    
	    try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String chmodCmdTxt = "chmod 777 " + filePath + "/*";
		File destFile=new File(destPath);
		try {
			
			Runtime.getRuntime().exec(chmodCmdTxt);
			logger.info("cmd chmod:" + chmodCmdTxt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		destFile.setExecutable(true, false);
		destFile.setReadable(true, false);
		destFile.setWritable(true, false);
		
		System.out.println("destFile is execute allow : " + destFile.canExecute());  
        System.out.println("destFile is read allow : " + destFile.canRead());  
        System.out.println("destFile is write allow : " + destFile.canWrite()); 
        
        String destFilePath 	= filePath + "/" +"exportTB.zip";
		String sourceFilePath 	= filePath + "/" +"*.txt";
		
		this.createZipfile(destFilePath, sourceFilePath, filePath);
		
		ftpServerPort = (sysParaServ.getSysPara("ftpPort")).trim();
		ftpServerIp = (sysParaServ.getSysPara("ftpIp")).trim();
		
		if(ftpServerPort.equals("21"))
		{
			destUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,ftpServerIp);
		}
		else
		{
			destUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,(ftpServerIp + ":" + ftpServerPort));
		}
	    
		logger.info("========returnUrl = "+ destUrl);
		return destUrl;
	}
	
	public boolean importBaseData(String fileName, SqlSessionFactory sqlSessionFactory)
	{
		boolean returnVal = false;
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE); 
		Connection conn = sqlSession.getConnection();
		
		try {
				/*Statement stmt = conn.createStatement();

				returnVal = stmt.execute("source" + fileName);*/
			 
			returnVal = this.execute(conn, StringBasePath.trim() + strorageTempPath.trim() + "/" + fileName);
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnVal;
	}



    private String getCondition(String startDate, String endDate,	Integer operator, 
			String homeOwnership, String siteSN, String deviceSN, String imsi)
    {
    	String condition = null;
    	if (startDate != null && !(startDate.isEmpty()))
        {
    		startDate = startDate.replace("/", "-");
    		endDate = endDate.replace("/", "-");
    		condition = "CAPTURE_TIME > " + "'" + startDate + "'" + " AND CAPTURE_TIME < " + "'"+ endDate + "'";
        }	
        
        if (operator != null && operator!=0)
        {
        	condition = condition + " AND OPERATOR = " + "'" + CommonConsts.getOperatorTypeText(operator) + "'";
        }
        
        if (homeOwnership != null && !(homeOwnership.isEmpty()))
        {
        	condition = condition + " AND CITY_CODE = " + "'" + homeOwnership + "'";
        }
        
        if (siteSN != null && !(siteSN.isEmpty()))
        {
        	 condition = condition + " AND SITE_SN = " + "'" + siteSN + "'";
        }
       
        if (deviceSN != null && !(deviceSN.isEmpty()))
        {
        	condition = condition + " AND DEVICE_SN = " + "'" + deviceSN + "'";
        }
        
        if (imsi != null && !(imsi.isEmpty()))
        {
        	condition = condition + " AND IMSI = " + "'" + imsi + "'";
        }
      
    	return condition;
    }
    
	public  void exportUeInfoData(Integer fileType, String exportId,String startDate, String endDate,	Integer operator, 
						String homeOwnership, String siteSN, String deviceSN, String imsi, SqlSessionFactory sqlSessionFactory)
	{
		String filePath = null;
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE); 
		Connection conn = sqlSession.getConnection();
		long ueInfoCnt = 0;
		int reportTimes = 0;
		String destFilePath = null;
		String sourceFilePath = null;
		String cmdTxt;
		String Condition =null;
		String returnUrl = null;
		
		String fileName = null;
		int progessLength;
		long rows;
		
		ueInfoCnt = this.getUeInfoTotalCnt(startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi);
		if (this.maxCountInsingleFile > ueInfoCnt)
		{
			reportTimes = 1;
		}
		else
		{
			reportTimes = (int)((this.maxCountInsingleFile - (ueInfoCnt%this.maxCountInsingleFile) + ueInfoCnt)/this.maxCountInsingleFile);
		}
		
		progessLength = 0;
		ExportTaskInfo exportTaskInfo = (ExportTaskInfo)SigletonBean.exportInfoMaps.get(exportId);
		exportTaskInfo.setProgress(progessLength);
		
		filePath = ueInfoTablePathStr.trim() + "/" + TimeUtils.timeFormatterStr.format( new Date());
		File file=new File(filePath);

		boolean pathMake = file.mkdirs();
		if (pathMake == false)
		{
			logger.info(" create path fail!!");
		}
		
		this.execCmd("chmod 777 " + filePath);
		System.out.println("is execute allow : " + file.canExecute());  
        System.out.println("is read allow : " + file.canRead());  
        System.out.println("is write allow : " + file.canWrite());  
				
		Condition = this.getCondition(startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi);
		if (Condition == null)
		{
			Condition = "1=1";
		}
		this.needDelay(1000);
		try {
			
			Statement stmt = conn.createStatement();
			for(int i=0; i < reportTimes; i++)
			{
				fileName = "'" + filePath + "/"/*File.separator*/ + "ue_info"+ "_00" + String.valueOf(i) + this.getFileSuffix(fileType) + "'";
				if ((ueInfoCnt-(i+1)*this.maxCountInsingleFile) < 0)
				{   
					
					rows = ueInfoCnt - (this.maxCountInsingleFile)*i;
				
				}
				else
				{
					rows = this.maxCountInsingleFile;
				}
				
				//String execSql = "select * from t_ue_info where "+ Condition +" limit " + (this.maxCountInsingleFile*i) + "," + rows + " into outfile " + fileName;
			
				String execSql =  "SELECT * FROM ( SELECT 'ID' AS ID,'SITE_NAME' AS SITE_NAME,'DEVICE_SN' AS DEVICE_SN, 'SITE_SN' AS SITE_SN,'IMSI' AS IMSI,'IMEI' AS IMEI   ,'STMSI' AS STMSI,'MAC' AS MAC, 'LATYPE' AS LATYPE,'INDICATION' AS INDICATION, 'REALTIME' AS REALTIME  ,'CAPTURE_TIME' AS CAPTURE_TIME, 'RARTA' AS RARTA, 'CREATE_TIME' AS CREATE_TIME,'CITY_NAME' AS CITY_NAME,'CITY_CODE' AS CITY_CODE, 'BAND' AS BAND,'OPERATOR' AS OPERATOR UNION ALL  SELECT * FROM t_ue_info where "+ Condition +" limit " + (this.maxCountInsingleFile*i) + "," + rows + " )a INTO OUTFILE " + fileName 
				 +" FIELDS TERMINATED BY ','";
				
				 logger.info("==== exportUeInfoData execSql :   " + execSql);
				boolean returnVal = stmt.execute(execSql);
				if(returnVal == false)
				{
					logger.info(" export file is error!");
					//return;
				}
				this.needDelay(3000);
				progessLength = progessLength + (int)(80/reportTimes);
				exportTaskInfo.setProgress(progessLength);
				logger.info(" progessLength = " + progessLength);
			}
			
			destFilePath 	= filePath + "/" +"ueInfo.zip";
			sourceFilePath 	= filePath + "/" +"*.*";

			//cmdTxt = "winrar a " + " " + destFilePath + " " + sourceFilePath; 
			cmdTxt = "zip -mjxf" + " " + destFilePath + " " + sourceFilePath;
			this.execCmd(cmdTxt);
			
			String chmodCmdTxt = "chmod 777 " + filePath + "/*.*";
			File zipfile=new File(destFilePath);
			
			this.execCmd(chmodCmdTxt);
			System.out.println("zipfile is execute allow : " + zipfile.canExecute());  
	        System.out.println("zipfile is read allow : " + zipfile.canRead());  
	        System.out.println("zipfile is write allow : " + zipfile.canWrite());  
	        this.needDelay(50*reportTimes);
			progessLength = progessLength + 20; 
			exportTaskInfo.setProgress(progessLength);
	     
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//returnUrl = destFilePath.replaceFirst("e:", "ftp://192.168.1.88");
		ftpServerPort = (sysParaServ.getSysPara("ftpPort")).trim();
		ftpServerIp = (sysParaServ.getSysPara("ftpIp")).trim();
		
		if(ftpServerPort.equals("21"))
		{
			returnUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,ftpServerIp);
		}
		else
		{
			returnUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,(ftpServerIp + ":" + ftpServerPort));
		}
		
		logger.info("========returnUrl = "+ returnUrl);
		exportTaskInfo.setUrl(returnUrl);
		exportTaskInfo.setProgress(100);
		return;
	}

	public  void exportUeInfoDataFromSplitTable1(Integer fileType, String exportId,String startDate, String endDate,	Integer operator,
												String homeOwnership, String siteSN, String deviceSN, String imsi, SqlSessionFactory sqlSessionFactory)
	{
		String Condition =null;int reportTimes = 0;List<String> tableNameList = null;
		tableNameList = this.getTableNameListAccordingCondition(startDate, endDate);
		Condition = this.getCondition1(startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi);
		if (Condition == null){Condition = "1=1";}
		reportTimes = tableNameList.size();
		Map<String,Object>map=new HashMap<>();
		int progessLength;
		progessLength = 0;
		String name="";
		if(fileType==0){
			 name=TimeUtils.timeFormatterStr.format( new Date())+".txt";
		}else if(fileType==1){
			 name=TimeUtils.timeFormatterStr.format( new Date())+".csv";
		}else{
			 name=TimeUtils.timeFormatterStr.format( new Date())+".xls";
		}
		ExportTaskInfo exportTaskInfo = (ExportTaskInfo)SigletonBean.exportInfoMaps.get(exportId);
		exportTaskInfo.setProgress(progessLength);
		for(int i=0; i < reportTimes; i++) {
			String table = tableNameList.get(i);
			String condition = Condition;
			List<TargetAlarm> selfCityCodeList = targetAlarmServ.gettableshuju(table, condition);
			map.put("list"+i,selfCityCodeList);
			this.needDelay(3000);
			progessLength = progessLength + (int)(80/reportTimes);
			exportTaskInfo.setProgress(progessLength);
			logger.info(" progessLength = " + progessLength);
		}
		List<TargetAlarm> list = new ArrayList<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			//System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
			list.addAll((List<TargetAlarm>)entry.getValue());
		}
	/**根据登录的用户来查询出本用户下有那些站点与设备**/
		/*Long userId = SigletonBean.getUserId();
		List<Site> siteList= devicedetailsService.getSiteListInfoByUserId(userId);
		for(Site site :siteList){

		}*/
		/*JSONObject dataObject = new JSONObject();
		JSONObject jsonObject = new JSONObject();*/

		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		jo = getSitesList();
		ja = (JSONArray) jo.get("data");
		List<DeviceDetailsModel> DeviceDetailsModelList=new ArrayList<DeviceDetailsModel>();
		for (int i = 0; i < ja.size(); i++) {
			JSONObject jc = (JSONObject) ja.get(i);
			JSONArray jcc = (JSONArray) jc.get("devices");
			if (jcc == null) {
				/*DeviceDetailsModel DeviceDetailsModel1=new DeviceDetailsModel();
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
				DeviceDetailsModelList.add(DeviceDetailsModel1);*/
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
					/*DeviceDetailsModel1.setYd((String) jcc1.get("yd"));
					DeviceDetailsModel1.setLt((String) jcc1.get("lt"));
					DeviceDetailsModel1.setDx((String) jcc1.get("dx"));
					DeviceDetailsModel1.setWz((String) jcc1.get("wz"));
					DeviceDetailsModel1.setZt((String) jcc1.get("zt"));*/
					DeviceDetailsModelList.add(DeviceDetailsModel1);

				}
			}
		}
		System.out.println(DeviceDetailsModelList);
		System.out.println(DeviceDetailsModelList);


		List<TargetAlarm> list1 = new ArrayList<>();

for(int i=0;i<DeviceDetailsModelList.size();i++){
	for(int j=0;j<list.size();j++) {
				if(DeviceDetailsModelList.get(i).getSitesn().equals(list.get(j).getSiteSn())
						&&DeviceDetailsModelList.get(i).getSitename().equals(list.get(j).getSiteName())
						&&DeviceDetailsModelList.get(i).getDevicesn().equals(list.get(j).getDeviceSn())){
					list1.add(list.get(j));
				}
		}
	}


		System.out.println(list1);
		System.out.println(list1);







//范德萨发的//////////////////////////////////////////////////////////////////////////////////////


		PoiWriteExcel a =new PoiWriteExcel();
		if(fileType==0) {
			a.PoiWriteTxt(list1, name);
			String wjcflj=PropertyUtil.getProperty("file");
			this.execCmd("chmod 777 " + wjcflj);
			File file = new File(wjcflj+"/"+name);
			File zipFile = new File(wjcflj+"/"+name.substring(0,14)+".zip");
			InputStream input = null;
			try {
			input = new FileInputStream(file);
			ZipOutputStream zipOut = null;
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			zipOut.putNextEntry(new ZipEntry(file.getName()));
			int temp = 0;
			while((temp = input.read()) != -1) {
				zipOut.write(temp);
			}
			input.close();
			zipOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			a.PoiWriteExcel1("sheet", list1, name);
		}
		this.needDelay(50*reportTimes);
		progessLength = progessLength + 20;
		exportTaskInfo.setProgress(progessLength);
		String returnUrl = null;
		exportTaskInfo.setProgress(progessLength);
		String wjcflj= PropertyUtil.getProperty("filedown");
		if(fileType==0) {
			returnUrl=wjcflj+"/"+name.substring(0,14)+".zip";
		}else{
			returnUrl=wjcflj+"/"+name;
		}
		exportTaskInfo.setUrl(returnUrl);
		exportTaskInfo.setProgress(100);
		return ;
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
		//	String devicesn=device.getSn();
		//	String shsj= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//	String table = devicedetailsService.getTableName("2017-08-09"/*shsj.substring(0,4)+"-"+shsj.substring(4,6)+"-"+shsj.substring(6,8)*/);
			//List<DeviceDetailsCount> DeviceDetailsCountlist= devicedetailsService.getDeviceDetailsCountlist(devicesn,table);
			//if(DeviceDetailsCountlist.size()==0){
		/*	deviceJo.put("yd","0");
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
			}*/
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
	public  void exportUeInfoDataFromSplitTable(Integer fileType, String exportId,String startDate, String endDate,	Integer operator, 
			String homeOwnership, String siteSN, String deviceSN, String imsi, SqlSessionFactory sqlSessionFactory)
	{
		String filePath = null;
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE); 
		Connection conn = sqlSession.getConnection();
		long ueInfoCnt = 0;
		int reportTimes = 0;
		String destFilePath = null;
		String sourceFilePath = null;
		String cmdTxt;
		String Condition =null;
		String returnUrl = null;
		
		List<String> tableNameList = null;//new  ArrayList<String>();
		
		String fileName = null;
		int progessLength;
		long rows;
		
		tableNameList = this.getTableNameListAccordingCondition(startDate, endDate);
		
		reportTimes = tableNameList.size();
		
		progessLength = 0;
		ExportTaskInfo exportTaskInfo = (ExportTaskInfo)SigletonBean.exportInfoMaps.get(exportId);
		exportTaskInfo.setProgress(progessLength);
		
		filePath = ueInfoTablePathStr.trim() + "/" + TimeUtils.timeFormatterStr.format( new Date());
		File file=new File(filePath);
		
		boolean pathMake = file.mkdirs();
		
		this.execCmd("chmod 777 " + filePath);
		System.out.println("is execute allow : " + file.canExecute());  
		System.out.println("is read allow : " + file.canRead());  
		System.out.println("is write allow : " + file.canWrite());  
		if (pathMake == false)
		{
			logger.info(" create path fail!!");
		}
		
		Condition = this.getCondition(startDate, endDate, operator, homeOwnership, siteSN, deviceSN, imsi);
		if (Condition == null)
		{
			Condition = "1=1";
		}
		
		String listSiteStr = null;
		Long userId = SigletonBean.getUserId();
	    List<Site> sites= deviceService.getSiteListInfoByUserId(userId);
	    if (sites.size() != 0)
	    {
	    	 listSiteStr =  Utils.getSitelistStr(sites);
	    }
	    else
	    {
	    	listSiteStr = "( '1223311hhddf2123323' )";
	    }
	    
		this.needDelay(1000);
		try {
			
			Statement stmt = conn.createStatement();
			for(int i=0; i < reportTimes; i++)
			{
				fileName = "'" + filePath + "/"  + "ue_info"+ "_00" + String.valueOf(i) + this.getFileSuffix(fileType) + "'";
				
				/*String execSql1 =  "SELECT * FROM ( SELECT 'ID' AS ID,'SITE_NAME' AS SITE_NAME,'DEVICE_SN' AS DEVICE_SN, 'SITE_SN' AS SITE_SN,'IMSI' AS IMSI,'IMEI' AS IMEI   ,'STMSI' AS STMSI,'MAC' AS MAC, 'LATYPE' AS LATYPE,'INDICATION' AS INDICATION, 'REALTIME' AS REALTIME  ,'CAPTURE_TIME' AS CAPTURE_TIME, 'RARTA' AS RARTA, 'CREATE_TIME' AS CREATE_TIME,'CITY_NAME' AS CITY_NAME,'CITY_CODE' AS CITY_CODE, 'BAND' AS BAND,'OPERATOR' AS OPERATOR UNION ALL  SELECT * FROM " + tableNameList.get(i) + " where "+ Condition + "and SITE_SN in " + listSiteStr + " ORDER BY CAPTURE_TIME DESC)a INTO OUTFILE " + fileName 
						 +" FIELDS TERMINATED BY ','";*/
				
				String execSql =  "SELECT * FROM ( SELECT 'ID' AS ID,'SITE_NAME' AS SITE_NAME,'DEVICE_SN' AS DEVICE_SN, 'SITE_SN' AS SITE_SN,'IMSI' AS IMSI,'IMEI' AS IMEI,'STMSI' AS STMSI,'MAC' AS MAC, 'LATYPE' AS LATYPE,'INDICATION' AS INDICATION, 'REALTIME' AS REALTIME  ,'CAPTURE_TIME' AS CAPTURE_TIME, 'RARTA' AS RARTA, 'CREATE_TIME' AS CREATE_TIME,'CITY_NAME' AS CITY_NAME,'CITY_CODE' AS CITY_CODE, 'BAND' AS BAND,'OPERATOR' AS OPERATOR UNION ALL SELECT * FROM (SELECT * FROM " + tableNameList.get(i) + " where "+ Condition  + " ORDER BY CAPTURE_TIME DESC) AS TempTable where SITE_SN in " + listSiteStr + ")a INTO OUTFILE " + fileName 
				 +" FIELDS TERMINATED BY ','";
				
				//String execSql2 = "select * from " + tableNameList.get(i) + " where "+ Condition + " into outfile " + fileName;
				logger.info("==== exportUeInfoData execSql :   " + execSql);
				boolean returnVal = stmt.execute(execSql);
				if(returnVal == false)
				{
					logger.info(" export file is error!");
					//return;
				}
				this.needDelay(3000);
				progessLength = progessLength + (int)(80/reportTimes);
				exportTaskInfo.setProgress(progessLength);
				logger.info(" progessLength = " + progessLength);
			}
			
			destFilePath 	= filePath + "/" +"ueInfo.zip";
			sourceFilePath 	= filePath + "/" +"*.*";
			
			//cmdTxt = "winrar a " + " " + destFilePath + " " + sourceFilePath; 
			cmdTxt = "zip -mjxf" + " " + destFilePath + " " + sourceFilePath;
			this.execCmd(cmdTxt);
			
			
			String chmodCmdTxt = "chmod 777 " + filePath + "/*";
			this.execCmd(chmodCmdTxt);
			
			File zipfile=new File(destFilePath);
						
			System.out.println("zipfile is execute allow : " + zipfile.canExecute());  
			System.out.println("zipfile is read allow : " + zipfile.canRead());  
			System.out.println("zipfile is write allow : " + zipfile.canWrite());  
			this.needDelay(50*reportTimes);
			progessLength = progessLength + 20; 
			exportTaskInfo.setProgress(progessLength);
			
			
		} catch (SQLException e) {
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
		ftpServerPort = (sysParaServ.getSysPara("ftpPort")).trim();
		ftpServerIp = (sysParaServ.getSysPara("ftpIp")).trim();
		
		if((ftpServerPort.trim()).equals("21"))
		{
			returnUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,ftpServerIp.trim());
		}
		else
		{
			returnUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,(ftpServerIp.trim() + ":" + ftpServerPort.trim()));
		}
		
		logger.info("========returnUrl = "+ returnUrl);
		exportTaskInfo.setUrl(returnUrl);
		exportTaskInfo.setProgress(100);
		
		return;
		
	}

	
	
	private List<String> getTableNameListAccordingCondition(String startDate, String endDate)
	{
		List<String> tableNameList = new ArrayList<String>();
			
		String startDateTime = startDate.isEmpty()?"": startDate.substring(0, 10) ;
	    String endDateTime   = endDate.isEmpty()?"": endDate.substring(0, 10);
	    
	    startDateTime = startDateTime.replace("/", "-");
	    endDateTime = endDateTime.replace("/", "-");
	    List<ShardingTabInfo> shardingTabInfoList =  shardingTabInfoMapper.selectByDate( startDateTime, endDateTime );
	    for(int i = 0; i < shardingTabInfoList.size(); i ++)
	    {
	    	tableNameList.add(shardingTabInfoList.get(i).getTableName());
	    }
	    
	    return tableNameList;
		
		
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
	
	public boolean execCmd(String shellString)
	{
		try{
			if (shellString == null || shellString.length() == 0)
			{
				return true;
			}
			String[] cmd = new String[]{"sh", "-c", shellString};
			Process process = Runtime.getRuntime().exec(cmd);
			
			int exitValue = process.waitFor();
			if(0 != exitValue)
			{
				logger.error("命令{}执行失败", shellString);
			}
			logger.info("命令{}执行成功", shellString);
			
			return exitValue==0;
			
		}catch(Throwable e)
		{
			logger.error("命令{}执行出现异常", new String[]{shellString, e.getMessage()});
			return false;
		}
	}
	
	public void createZipfile(String destFilePath, String sourceFilePath, String filePath)
	{
		String cmdTxt = "zip -jxf" + " " + destFilePath + " " + sourceFilePath;
		//String cmdTxt = "zip -mjxf" + " " + destFilePath + " " + sourceFilePath; //增加命令参数m 打包后会删除原来的文件。
		//String cmdTxt = "winrar a " + " " + destFilePath + " " + sourceFilePath; //windows下运行的压缩命令
			
		this.execCmd(cmdTxt);
		
		String chmodCmdTxt = "chmod 777 " + filePath + "/*";
		File zipfile=new File(destFilePath);
		
		this.execCmd(chmodCmdTxt);
		System.out.println("zipfile is execute allow : " + zipfile.canExecute());  
        System.out.println("zipfile is read allow : " + zipfile.canRead());  
        System.out.println("zipfile is write allow : " + zipfile.canWrite()); 
        
        return;
        
	}
	
	
	private long getUeInfoTotalCnt(String startDate, String endDate,	Integer operator, 
			String homeOwnership, String siteSN, String deviceSN, String imsi)
	{
		long totalCnt = 0;
		Map<String,Object> params=new HashMap<String, Object>();
		if (startDate == null || startDate.isEmpty())
        {
        	params.put("startDate", null);
            params.put("endDate", null);
        }
        else
        {
        	params.put("startDate", startDate.replace("/", "-"));
            params.put("endDate", endDate.replace("/", "-"));
        }	
        
        if (operator == null || operator==0)
        {
        	params.put("operator", null);
        }
        else
        {
           	params.put("operator", CommonConsts.getOperatorTypeText(operator));
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
        
		totalCnt = (long)ueInfoMapper.getUeInfoCnt(params);
		
		return totalCnt;
		
	}

	private String getTargetCondition(String startDate, String endDate,	String ruleName, 
			String homeOwnership, String siteSN, String deviceSN, String imsi)
    {
    	String condition = null;
    	if (startDate != null && !(startDate.isEmpty()))
        {
    		startDate = startDate.replace("/", "-");
    		endDate = endDate.replace("/", "-");
    		condition = "CAPTURE_TIME > " + "'" + startDate + "'" + " AND CAPTURE_TIME < " + "'"+ endDate + "'";
        }	
    	
        if (ruleName != null && !(ruleName.isEmpty()))
        {
        	condition = condition + " AND RULE_NAME = " + "'" + ruleName + "'";
        }
        
        if (homeOwnership != null && !(homeOwnership.isEmpty()))
        {
        	condition = condition + " AND CITY_NAME = " + "'" + homeOwnership + "'";
        }
        
        if (siteSN != null && !(siteSN.isEmpty()))
        {
        	 siteSN = null;
        	 condition = condition + " AND SITE_SN = " + "'" + siteSN + "'";
        }
       
        if (deviceSN != null && !(deviceSN.isEmpty()))
        {
        	deviceSN = null;
        	condition = condition + " AND DEVICE_SN = " + "'" + deviceSN + "'";
        }
        
        if (imsi != null && !(imsi.isEmpty()))
        {
        	condition = condition + " AND IMSI = " + "'" + imsi + "'";
        }
                
    	return condition;
    }
	
	private long getTargetTotalCnt(String startDate, String endDate, String ruleName, 
			String homeOwnership, String siteSN, String deviceSN, String imsi)
	{
		long totalCnt = 0;
		Map<String,Object> params=new HashMap<String, Object>();
		if (startDate == null || startDate.isEmpty())
        {
        	params.put("startDate", null);
            params.put("endDate", null);
        }
        else
        {
        	params.put("startDate", startDate.replace("/", "-"));
            params.put("endDate", endDate.replace("/", "-"));
        }	
        
        if (ruleName == null || ruleName.isEmpty())
        {
        	params.put("ruleName", null);
        }
        else
        {
        	params.put("ruleName", ruleName);
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
        	params.put("siteSn", siteSN);
        }
        else
        {
        	 params.put("siteSn", siteSN);
        }
       
        if (deviceSN == null || deviceSN.isEmpty())
        {
        	deviceSN = null;
        	params.put("deviceSn", deviceSN);
        	
        }
        else
        {
        	params.put("deviceSn", deviceSN);
        }
        if (deviceSN == null && siteSN == null)
        {
        	   Long userId = SigletonBean.getUserId();
               List<Site> sites= deviceService.getSiteListInfoByUserId(userId);
               params.put("sites", sites);
        }
        else
        {
        	params.put("sites", null);
        }
        
        if (imsi == null || imsi.isEmpty())
        {
        	params.put("imsi", null);
        }
        else
        {
        	params.put("imsi", imsi);
        }
        
		totalCnt = (long)targetAlarmMapper.getTotalAlarmCount(params);
		
		return totalCnt;
		
	}
	
	private String constructTargetDataSql(String startDate, String endDate,
			String ruleName, String homeOwnership, String siteSn,  String deviceSn, String imsi, String fileName, int page, long rows)
	{
		String execSql = null;
		long start = 0;
		String inCondition;
		
		String condition = this.getTargetCondition(startDate, endDate, ruleName, homeOwnership, siteSn, deviceSn, imsi);
		
		String execSql1 = " select TTA.ID, TTR.NAME as RULE_NAME, TTR.REMARK,TTA.SITE_NAME, TTA.DEVICE_SN, TD.NAME as DEVICE_NAME, TTA.IMSI,TTA.IMEI,TTA.STMSI,TTA.MAC,TTA.LATYPE,TTA.INDICATION,TTA.REALTIME,TTA.CAPTURE_TIME,	TTA.RARTA,TTA.CREATE_TIME,TTA.CITY_NAME,TTA.CITY_CODE,TTA.BAND,TTA.OPERATOR,TTA.IS_READ, TTA.SITE_SN from t_target_alarm TTA left join t_device TD on TD.SN = TTA.DEVICE_SN left join t_target_rule TTR on TTR.IMSI = TTA.IMSI where TTA.INDICATION = 1 AND " + condition;
		
		String execSql2 = " select TTA.ID, TAR.NAME as RULE_NAME, TAR.REMARK,TTA.SITE_NAME,	TTA.DEVICE_SN, TD.NAME as DEVICE_NAME, TTA.IMSI,TTA.IMEI,TTA.STMSI,	TTA.MAC,TTA.LATYPE,TTA.INDICATION,TTA.REALTIME,	TTA.CAPTURE_TIME,TTA.RARTA,TTA.CREATE_TIME,	TTA.CITY_NAME,TTA.CITY_CODE,TTA.BAND,TTA.OPERATOR,TTA.IS_READ, TTA.SITE_SN from t_target_alarm TTA left join t_device TD on TD.SN = TTA.DEVICE_SN left join t_area_rule TAR on TAR.SOURCE_CITY_NAME = TTA.CITY_NAME where TTA.INDICATION = 2 AND " + condition;
		
		if(page == 0)
	   	{
	   		 start =0;
	   	}
	   	else
	   	{
	   		 start = ((page-1)*this.maxCountInsingleFile);
	   	}

	   if (deviceSn == null || deviceSn.isEmpty())
       {
         	
         	deviceSn = null ;
       } 
	   
	   if (siteSn == null || siteSn.isEmpty())
       {
         	
		   siteSn = null ;
       }
	   
	   if (deviceSn == null && siteSn == null)
        {
        	String listSiteStr = null;
    		Long userId = SigletonBean.getUserId();
    	    List<Site> sites= deviceService.getSiteListInfoByUserId(userId);
    	    if (sites.size() != 0)
    	    {
    	    	 listSiteStr =  Utils.getSitelistStr(sites);
    	    }
    	    else
    	    {
    	    	listSiteStr = "( '1223311hhddf2123323' )";
    	    }
    	    
    	    inCondition = "SITE_SN in "+ listSiteStr;
    	    
    	    execSql = "SELECT * FROM ( SELECT 'ID' AS ID, 'RULE_NAME' AS RULE_NAME, 'REMARK' AS REMARK, 'SITE_NAME' AS SITE_NAME,'DEVICE_SN' AS DEVICE_SN, 'DEVICE_NAME' AS DEVICE_NAME,'IMSI' AS IMSI,'IMEI' AS IMEI,'STMSI' AS STMSI,'MAC' AS MAC, 'LATYPE' AS LATYPE,'INDICATION' AS INDICATION, 'REALTIME' AS REALTIME  ,'CAPTURE_TIME' AS CAPTURE_TIME, 'RARTA' AS RARTA, 'CREATE_TIME' AS CREATE_TIME,'CITY_NAME' AS CITY_NAME,'CITY_CODE' AS CITY_CODE, 'BAND' AS BAND,'OPERATOR' AS OPERATOR, 'IS_READ' AS IS_READ, 'SITE_SN' AS SITE_SN UNION ALL SELECT * from ( SELECT * from ( " + execSql1 + "UNION" + execSql2 + ") as allTable where "+ inCondition + ") as finalTable " + " limit " + start + ", " + rows + ")a into outfile " + fileName +" FIELDS TERMINATED BY ','";
    		
         }
		 else
		 {
			execSql = "SELECT * FROM ( SELECT 'ID' AS ID, 'RULE_NAME' AS RULE_NAME, 'REMARK' AS REMARK, 'SITE_NAME' AS SITE_NAME,'DEVICE_SN' AS DEVICE_SN, 'DEVICE_NAME' AS DEVICE_NAME,'IMSI' AS IMSI,'IMEI' AS IMEI,'STMSI' AS STMSI,'MAC' AS MAC, 'LATYPE' AS LATYPE,'INDICATION' AS INDICATION, 'REALTIME' AS REALTIME  ,'CAPTURE_TIME' AS CAPTURE_TIME, 'RARTA' AS RARTA, 'CREATE_TIME' AS CREATE_TIME,'CITY_NAME' AS CITY_NAME,'CITY_CODE' AS CITY_CODE, 'BAND' AS BAND,'OPERATOR' AS OPERATOR, 'IS_READ' AS IS_READ, 'SITE_SN' AS SITE_SN UNION ALL SELECT * from ( " + execSql1 + "UNION" + execSql2 + ") as allTable where 1=1" + " limit " + start + ", " + rows + ")a into outfile " + fileName +" FIELDS TERMINATED BY ','";
				
		 }
		 
		//execSql = "Select * from ( " + execSql1 + "UNION" + execSql2 + ") as allTable where 1=1" + " limit " + start + ", " + rows + " into outfile " + fileName;
		
		
		return execSql; 
		
	}
	
	private long getLogTotalCnt(String startDate, String endDate, String userName, String logType, String deviceSn)
	{
		long totalCnt = 0;
		Map<String,Object> params=new HashMap<String, Object>();
		if (startDate == null || startDate.isEmpty())
        {
        	params.put("startDate", null);
            params.put("endDate", null);
        }
        else
        {
        	startDate = startDate.replace("/", "-");
        	endDate = endDate.replace("/", "-");
            startDate = TimeUtils.convertDateSepToNoSep(startDate);
	   		endDate = TimeUtils.convertDateSepToNoSep(endDate);
	   		params.put("startDate", startDate);
            params.put("endDate", endDate);
        }	
        
        if (userName == null || userName.isEmpty())
        {
        	params.put("userName", null);
        }
        else
        {
        	params.put("userName", userName);
        }
     
        
        if (logType == null || Integer.valueOf(logType) == 99)
        {
        	params.put("type", null);
        }
        else
        {
           	params.put("type", logType);
        }
        
        if (deviceSn == null || deviceSn.isEmpty())
        {
        	params.put("deviceSn", null);
        }
        else
        {
        	params.put("deviceSn", deviceSn);
        }
        
        if(SigletonBean.getRoleId() != 1)
		{
        	/*日志的查询和导出，系统管理员权限的用户能看到所有日志，其他角色用户只能看到自己的日志*/
        	params.put("owerId", SigletonBean.getUserId());
		}
        else
        {
        	params.put("owerId", null);
        }
        
		totalCnt = (long)sysLogMapper.getLoggerCnt(params);
		
		return totalCnt;
		
	}
	
	private String getLogCondition(String startDate, String endDate,
			String userName, String deviceSn, String logType, String fileName, int page, long rows)
	{
		String condition = null;
		long start = 0;
    	if (startDate != null && !(startDate.isEmpty()))
        {
    		startDate = startDate.replace("/", "-");
    		startDate = TimeUtils.convertDateSepToNoSep(startDate);
    		
    		endDate = endDate.replace("/", "-");
    		endDate = TimeUtils.convertDateSepToNoSep(endDate);
    		
    		condition = " AND OPER_TIME > " + "'" + startDate + "'" + " AND OPER_TIME < " + "'"+ endDate + "'";
        }
    	
    	 if (logType != null && Integer.valueOf(logType) != 99)
         {
    		 condition = condition + " AND TYPE = " + Integer.parseInt(logType);
         }
    	 
    	 if (deviceSn != null && !(deviceSn.isEmpty()))
         {
    		 condition = condition + " AND DEVICE_SN = " + deviceSn;
         }
    	 
    	 if(page == 0)
    	 {
    		 start =0;
    	 }
    	 else
    	 {
    		 start = ((page-1)*this.maxCountInsingleFile);
    	 }
         	
		condition = condition +  " limit " + start + ", " + rows;
    	
		condition = condition + " into outfile " + fileName;
    	
		return condition;
	}
	
	
	private String constructLogSql(String startDate, String endDate,
			String userName, String deviceSn, String logType, String fileName, int page, long rows)
	{
		String execSql = null;
		String condition = this.getLogCondition( startDate,  endDate, userName, deviceSn, logType, fileName, page, rows); 
		String execSql1 = "select	TLG.ID, TLG.TYPE, TLG.OPER_NAME, TLG.OPER_TIME,	TU.USER_NAME, TLG.SITE_NAME, TLG.DEVICE_SN from t_sys_log TLG  left join t_user TU on TU.ID = TLG.USER_ID  where 1=1 ";
		
		if(SigletonBean.getRoleId() != 1)
		{
			execSql1 = execSql1 + "and TLG.USER_ID =" +  SigletonBean.getUserId();
		}
		
		if (userName != null && !(userName.isEmpty()))
		{
			execSql1 = execSql1 + "AND TU.LOGIN_NAME = " + userName;
		}
		
		execSql = execSql1 + condition;
		return execSql;
	}
	
	public  void exportTargetData(Integer fileType, String exportId, String startDate, String endDate,
			String ruleName, String homeOwnership, String siteSn,  String deviceSn, String imsi, SqlSessionFactory sqlSessionFactory)
	{
		String filePath = null;
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE); 
		Connection conn = sqlSession.getConnection();
		long targetUeInfoCnt = 0;
		int reportTimes = 0;
		String destFilePath = null;
		String sourceFilePath = null;
		String cmdTxt;
		String returnUrl = null;
		
		String fileName = null;
		int  progessLength = 0;
		long rows = 0;
		ExportTaskInfo exportTaskInfo = (ExportTaskInfo)SigletonBean.exportInfoMaps.get(exportId);
		exportTaskInfo.setProgress(progessLength);
		targetUeInfoCnt = this.getTargetTotalCnt(startDate, endDate, ruleName, homeOwnership, siteSn, deviceSn, imsi);
		if (this.maxCountInsingleFile > targetUeInfoCnt)
		{
			reportTimes = 1;
		}
		else
		{
			reportTimes = (int)((this.maxCountInsingleFile - (targetUeInfoCnt%this.maxCountInsingleFile) + targetUeInfoCnt)/this.maxCountInsingleFile);
		}
		
		progessLength = 0;
		exportTaskInfo.setProgress(progessLength);
		
		filePath = targetInfoTablePathStr.trim() + "/" + TimeUtils.timeFormatterStr.format( new Date() );
		File file=new File(filePath);

		boolean pathMake = file.mkdirs();
		if (pathMake == false)
		{
			logger.info(" create path fail!!");
		}
			
		this.execCmd("chmod 777 " + filePath);
		
		System.out.println("is execute allow : " + file.canExecute());  
        System.out.println("is read allow : " + file.canRead());  
        System.out.println("is write allow : " + file.canWrite());  
		
		
		this.needDelay(1000);
		try {
		
		Statement stmt = conn.createStatement();
		for(int i=0; i < reportTimes; i++)
		{
			fileName = "'" + filePath + "/"/*File.separator*/ + "targetUeInfo"+ "_00" + String.valueOf(i) + this.getFileSuffix(fileType) + "'";
			if ((targetUeInfoCnt-(i+1)*this.maxCountInsingleFile) < 0)
			{   
				
				rows = targetUeInfoCnt - (this.maxCountInsingleFile)*i;
			
			}
			else
			{
				rows = this.maxCountInsingleFile;
			}
			
			String execSql = this.constructTargetDataSql(startDate, endDate, ruleName, homeOwnership, siteSn, deviceSn, imsi, fileName, i, rows);//"select * from t_ue_info where "+ Condition +" into outfile " + fileName;
			logger.info("====exportTargetData execSql :   " + execSql);
			boolean returnVal = stmt.execute(execSql);
			if(returnVal == false)
			{
				logger.info(" export file is error!");
				//return ;  //返回false也是成功，需要再看看
			}
			this.needDelay(3000);
			
			progessLength = progessLength + (int)(80/reportTimes);
			exportTaskInfo.setProgress(progessLength);
			//noticePush.pushExportProcess( progessLength );
		}
		
		destFilePath 	= filePath + "/"/*File.separator*/ +"targetUeInfo.rar";
		sourceFilePath 	= filePath + "/"/*File.separator*/ +"*";
		//cmdTxt = "winrar a " + " " + destFilePath + " " + sourceFilePath; 
		cmdTxt = "zip -mjxf "  + destFilePath + " " + sourceFilePath;
		
		this.execCmd(cmdTxt);
		this.execCmd("chmod 777 " + filePath + "/*.*");
		File zipfile=new File(destFilePath);
		System.out.println("zipfile is execute allow : " + zipfile.canExecute());  
        System.out.println("zipfile is read allow : " + zipfile.canRead());  
        System.out.println("zipfile is write allow : " + zipfile.canWrite());  
        
		progessLength = progessLength + 19; 
		exportTaskInfo.setProgress(progessLength);
		
		} catch (SQLException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ftpServerPort = (sysParaServ.getSysPara("ftpPort")).trim();
		ftpServerIp = (sysParaServ.getSysPara("ftpIp")).trim();
		
		if(ftpServerPort.equals("21"))
		{
			returnUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,ftpServerIp);
		}
		else
		{
			returnUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,(ftpServerIp + ":" + ftpServerPort));
		}
		
		
		logger.info("========returnUrl = "+ returnUrl);
		exportTaskInfo.setUrl(returnUrl);
		exportTaskInfo.setProgress(100);
		return;
	}
	
	public void exportLogData(Integer fileType, String exportId, String startDate, String endDate, String userName, String logType, String deviceSn, SqlSessionFactory sqlSessionFactory)
	{
		String filePath = null;
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE); 
		Connection conn = sqlSession.getConnection();
		long LogCnt = 0;
		int reportTimes = 0;
		String destFilePath = null;
		String sourceFilePath = null;
		String cmdTxt;
		String returnUrl = null;
		long  rows;
		
		String fileName = null;
		int  progessLength = 0;
		ExportTaskInfo exportTaskInfo = (ExportTaskInfo)SigletonBean.exportInfoMaps.get(exportId);
		exportTaskInfo.setProgress(progessLength);
		LogCnt = this.getLogTotalCnt(startDate, endDate, userName, logType, deviceSn);
		if (this.maxCountInsingleFile > LogCnt)
		{
			reportTimes = 1;
		}
		else
		{
			reportTimes = (int)((this.maxCountInsingleFile - (LogCnt%this.maxCountInsingleFile) + LogCnt)/this.maxCountInsingleFile);
		}
		
		progessLength = 0;
		exportTaskInfo.setProgress(progessLength);
		
		filePath = logTablePath.trim() + "/" + TimeUtils.timeFormatterStr.format( new Date() );
		File file=new File(filePath);

		boolean pathMake = file.mkdirs();
		if (pathMake == false)
		{
			logger.info(" create path fail!!");
		}
		
		this.execCmd("chmod 777 " + filePath);
		System.out.println("is execute allow : " + file.canExecute());  
        System.out.println("is read allow : " + file.canRead());  
        System.out.println("is write allow : " + file.canWrite());  
		try {
		
		Statement stmt = conn.createStatement();
		for(int i=0; i < reportTimes; i++)
		{
			fileName = "'" + filePath + "/"/*File.separator*/ + "log"+ "_00" + String.valueOf(i) + this.getFileSuffix(fileType) + "'";
			if ((LogCnt-(i+1)*this.maxCountInsingleFile) < 0)
			{   
				
				rows = LogCnt - (this.maxCountInsingleFile)*i;
			
			}
			else
			{
				rows = this.maxCountInsingleFile;
			}
			
			String execSql = this.constructLogSql(startDate, endDate, userName, deviceSn, logType,  fileName, i, rows);//"select * from t_ue_info where "+ Condition +" into outfile " + fileName;
			logger.info("====exportLogData execSql :   " + execSql);
			boolean returnVal = stmt.execute(execSql);
			if(returnVal == false)
			{
				logger.info(" export file is error!");
				//return ;  //返回false也是成功，需要再看看
			}
			this.needDelay(3000);
			
			progessLength = progessLength + (int)(80/reportTimes);
			exportTaskInfo.setProgress(progessLength);
			//noticePush.pushExportProcess( progessLength );
		}
		
		destFilePath 	= filePath + "/"/*File.separator*/ +"log.rar";
		sourceFilePath 	= filePath + "/"/*File.separator*/ +"*";
		//cmdTxt = "winrar a " + " " + destFilePath + " " + sourceFilePath; 
		cmdTxt = "zip -mjxf "  + destFilePath + " " + sourceFilePath;
		
		this.execCmd(cmdTxt);
		String chmodCmdTxt = "chmod 777 " + filePath + "/*.*";
		this.execCmd(chmodCmdTxt);
		File zipfile=new File(destFilePath);
		
		System.out.println("zipfile is execute allow : " + zipfile.canExecute());  
        System.out.println("zipfile is read allow : " + zipfile.canRead());  
        System.out.println("zipfile is write allow : " + zipfile.canWrite());  
        
		progessLength = progessLength + 19; 
		exportTaskInfo.setProgress(progessLength);
		
		} catch (SQLException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ftpServerPort = (sysParaServ.getSysPara("ftpPort")).trim();
		ftpServerIp = (sysParaServ.getSysPara("ftpIp")).trim();
		
		if(ftpServerPort.equals("21"))
		{
			returnUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,ftpServerIp);
		}
		else
		{
			returnUrl = "ftp://" + destFilePath.replaceFirst("/var/ftp" ,(ftpServerIp + ":" + ftpServerPort));
		}
		logger.info("========returnUrl = "+ returnUrl);
		exportTaskInfo.setUrl(returnUrl);
		exportTaskInfo.setProgress(100);
		return;
	
	}
		
	private void exportBaseTable2File(String tableStr, String baseTableStr){
		StringBuffer sb = new StringBuffer();
	    sb.append("mysqldump ");
	    sb.append(" -uroot ");
	    sb.append(" -p4gefence");
	    sb.append(" --default-character-set=utf8");
	    sb.append(" efence2 ");
	    sb.append(tableStr);
	    sb.append(" > ");
	    sb.append(baseTableStr);
	  
	    String javaExecute = sb.toString();// 在CMD中执行的字符串
	  
	    this.execCmd(javaExecute);
	}
	
	   /** 
     * 读取 SQL 文件，获取 SQL 语句 
     * @param sqlFile SQL 脚本文件 
     * @return List<sql> 返回所有 SQL 语句的 List 
     * @throws Exception 
     */  
	private List<String> loadSql(String sqlFile) throws Exception {  
        List<String> sqlList = new ArrayList<String>();  
  
        try {  
            InputStream sqlFileIn = new FileInputStream(sqlFile);  
  
            StringBuffer sqlSb = new StringBuffer();  
            byte[] buff = new byte[1024];  
            int byteRead = 0;  
            while ((byteRead = sqlFileIn.read(buff)) != -1) {  
                sqlSb.append(new String(buff, 0, byteRead));  
            }  
  
            // Windows 下换行是 \r\n, Linux 下是 \n  
            String[] sqlArr = sqlSb.toString().split("(;\\s*\\r\\n)|(;\\s*\\n)");  
            for (int i = 0; i < sqlArr.length; i++) {  
                String sql = sqlArr[i].replaceAll("--.*", "").trim();  
                if (!sql.equals("")) {  
                    sqlList.add(sql);  
                }   
            }  
            return sqlList;  
        } catch (Exception ex) {  
            throw new Exception(ex.getMessage());  
        }  
    }   
    
	 private boolean execute(Connection conn, String sqlFile) throws Exception {  
	        int i =0;
		 	Statement stmt = null;  
	        List<String> sqlList = loadSql(sqlFile);  
	        stmt = conn.createStatement();  
	        for (String sql : sqlList) {  
	            stmt.addBatch(sql);  
	        }  
	        int[] rows = stmt.executeBatch();  
	        System.out.println("Row count:" + Arrays.toString(rows));
	        for(i=0; i < rows.length; i++)
	        {
	        	if (rows[i] > 0)
	        	{
	        		return true;
	        	}
	        }
	        
	        return false;
	    } 
	 
	 private String getFileSuffix(Integer fileType)
	 {
		String  fileSuffix = ".txt";
		switch(fileType)
		{
			case 0:
				fileSuffix = ".txt";
				break;
			case 1:
				fileSuffix = ".csv";
				break;
			default:
				break;
		}
		return fileSuffix;
		
	 }
	private String getCondition1(String startDate, String endDate,	Integer operator,
								String homeOwnership, String siteSN, String deviceSN, String imsi)
	{
		String condition = null;
		if (startDate != null && !(startDate.isEmpty()))
		{
			startDate = startDate.replace("/", "-");
			endDate = endDate.replace("/", "-");
			condition = "c.CAPTURE_TIME > " + "'" + startDate + "'" + " AND c.CAPTURE_TIME < " + "'"+ endDate + "'";
		}

		if (operator != null && operator!=0)
		{
			condition = condition + " AND c.OPERATOR = " + "'" + CommonConsts.getOperatorTypeText(operator) + "'";
		}

		if (homeOwnership != null && !(homeOwnership.isEmpty()))
		{
			condition = condition + " AND c.CITY_CODE = " + "'" + homeOwnership + "'";
		}

		if (siteSN != null && !(siteSN.isEmpty()))
		{
			condition = condition + " AND c.SITE_SN = " + "'" + siteSN + "'";
		}

		if (deviceSN != null && !(deviceSN.isEmpty()))
		{
			condition = condition + " AND c.DEVICE_SN = " + "'" + deviceSN + "'";
		}

		if (imsi != null && !(imsi.isEmpty()))
		{
			condition = condition + " AND c.IMSI = " + "'" + imsi + "'";
		}

		return condition;
	}
}
