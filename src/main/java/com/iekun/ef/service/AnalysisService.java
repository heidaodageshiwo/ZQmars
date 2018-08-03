package com.iekun.ef.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.*;
import com.iekun.ef.model.AreaCode;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.Task;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.push.NoticePush;
import com.iekun.ef.util.TimeUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by feilong.cai on 2016/11/25.
 */

@Service
public class AnalysisService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    private float progressNowValue = 0;
    private float oldProgressValue = 0;
    private Long currentTaskId = -1L;

    @Value(" ${tasksave.filePath}")
    private String filePath;

    @Value(" ${tasksave.basePath}")
    private String basePath;
    
    @Autowired
    private UeInfoMapper ueInfoMapper;

    @Autowired
    private TaskMapper  taskMapper;

    @Autowired
    private AreaCodeMapper areaCodeMapper;

    @Autowired
    private SiteMapper  siteMapper;

    @Autowired
    private NoticePush  noticePush;
    
    @Autowired
    private DeviceService devService;

    @Autowired
    private AnalysisShardingDao  analysisShardingDao;
    @Autowired
    private SystemParaService sysParaServ;
    
    private Map<String, Object> spaceTimeSiteNameMaps = new HashMap<String,Object>(); 

    public float getProgressNowValue() {
        return progressNowValue;
    }

    public Long getCurrentTaskId() {
        return currentTaskId;
    }

    private void pushTaskProgress( float stepValue ){

        try {
			this.progressNowValue += stepValue;

			if(  0 == this.progressNowValue ){
			    noticePush.pushTaskProcess( currentTaskId, progressNowValue );
			} else if( this.progressNowValue > 99.9  ) {
			    noticePush.pushTaskProcess( currentTaskId, 100/*progressNowValue*/ );
			} else if( (this.progressNowValue - this.oldProgressValue ) > 5 ) {
			    this.oldProgressValue = this.progressNowValue;
			    noticePush.pushTaskProcess( currentTaskId, progressNowValue );
                Thread.sleep(2000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }


    //=======================================================================
    //统计分析

    public int statisticsIMSI(Task task ) {

        this.currentTaskId = task.getId();
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        pushTaskProgress(0);

        JSONObject taskObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
//        JSONArray colorsArray = new JSONArray();
        JSONArray yKeysArray = new JSONArray();
        JSONArray labelsArray = new JSONArray();
        JSONArray statisticDataArray = new JSONArray();

        long sumUeInfoTotal = 0;

        try {

            JSONObject paramJson = JSONObject.parseObject( task.getParameter() );
            String startTime = paramJson.getString("startDate");
            String endTime =  paramJson.getString("endDate");
            Integer unit = paramJson.getInteger("unit");
            Integer type = paramJson.getInteger("type");
            JSONArray itemsJson = paramJson.getJSONArray ("items");


            JSONObject initJson = new JSONObject();
            float stepProcess = 10/itemsJson.size();
            for( int i=0; i < itemsJson.size() ; i++ ) {

                JSONObject itemJson = itemsJson.getJSONObject( i );

                initJson.put( itemJson.getString("value"), 0 );
                labelsArray.add( itemJson.getString("name"));
                yKeysArray.add( itemJson.getString("value"));

                pushTaskProgress( stepProcess );

            }

            Long userId = task.getCreatorId();
            List<Site> siteList= devService.getSiteListInfoByUserId(userId);
            
            List<JSONObject> dataList  =  getInitialData( startTime, endTime, unit, initJson);
            Integer dataListLen = dataList.size();
            float progressStep = (float)(80/(float)dataListLen);
            for ( JSONObject itemData: dataList ) {

                Map<String,Object> params = new HashMap<String, Object>();
                params.put("statisticsTime", itemData.getString("time") );

                for( int i=0; i < itemsJson.size() ; i++ ) {

                    JSONObject itemJson = itemsJson.getJSONObject( i );

                    long ueInfoTotal = 0;

                    switch ( type ) {
                        case 1: //站点
                            params.put("siteSN", itemJson.getString("value"));
                            params.put("sites", null);
                            break;
                        case 2: //设备
                            params.put("deviceSN", itemJson.getString("value"));
                            params.put("sites", null);
                            break;
                        case 3: //运营商
                            params.put("operator", itemJson.getString("name")); //根据名称查找
                            params.put("sites", siteList);
                            break;
                        default:
                            logger.error("statistics task parameter error!");
                            return 0;
                    }

                    switch ( unit ) {
                        case 1: //天
                            ueInfoTotal = analysisShardingDao.getUeInfoCountByDay(params);
                            break;
                        case 2: //小时
                            ueInfoTotal = analysisShardingDao.getUeInfoCountByHour(params);
                            break;
                        case 3: //分钟
                            ueInfoTotal = analysisShardingDao.getUeInfoCountByMin(params);
                            break;
                        default:
                            logger.error("statistics task parameter error!");
                            return 0;
                    }

                    itemData.put(  itemJson.getString("value") , ueInfoTotal);

                    sumUeInfoTotal += ueInfoTotal;
                }


                statisticDataArray.add(itemData );

                pushTaskProgress( progressStep );

            }

            dataObject.put("xkey", "time");
            dataObject.put("ykeys", yKeysArray);
            dataObject.put("labels", labelsArray );
            dataObject.put("data", statisticDataArray );

            taskObject.put("taskId", task.getId());
            taskObject.put("taskName", task.getName());
            taskObject.put("sumTotal", sumUeInfoTotal);
            taskObject.put("data", dataObject);

            //创建文件路径
            String txtPath = filePath;
            txtPath = txtPath.trim() + "/"
                    + task.getName().trim() + sdf.format( new Date())+ ".txt" ;
            //创建文件目录和文件
           // String newp = new String(txtPath.getBytes("gbk"),"UTF-8");
            File file = new File(txtPath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }

            //写入txt文件
            FileOutputStream fos = new FileOutputStream(txtPath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            String taskReport = taskObject.toJSONString();
            osw.write( taskReport );
            osw.flush();
            osw.close();
            fos.close();

            pushTaskProgress( 5 );

            task.setIsFinish(1);
            task.setFinishTime(TimeUtils.timeFormatterStr.format(new Date()));
            task.setResultExt(txtPath);
            taskMapper.updateTaskById( task );
            pushTaskProgress( 5 );

        } catch ( Exception e ) {
            e.printStackTrace();
            taskMapper.updateTryCount( task.getId(), task.getTryCount() + 1);
        }

        this.currentTaskId = -1L;
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        return 0;
    }

    private List<JSONObject> getInitialData( String startTime, String endTime,  Integer unit, JSONObject initJson ){

        List<JSONObject>  initialStatisticData = new LinkedList<>();
        DateFormat df = null;
        Date startDate = null;
        Date endDate = null;
        try {

            switch ( unit ) {
                case 1: //天
                    df = new SimpleDateFormat("yyyy-MM-dd");
                    break;
                case 2: //小时
                    df = new SimpleDateFormat("yyyy-MM-dd HH");
                    break;
                case 3: //分钟
                    df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    break;
                default:
                    logger.error("statistics task parameter error!");
                    return null;
            }
        if(unit.equals(Integer.parseInt("1"))){

            startDate = df.parse( startTime );



            DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date dd = dfs.parse(endTime);

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(dd);

            calendar.add(Calendar.DAY_OF_MONTH, 1);//加一天
            String endTime1=df.format(calendar.getTime());

            endDate   = df.parse( endTime1 );


            Date lpDate = startDate;
            Calendar cal = Calendar.getInstance();
            cal.setTime( startDate );
            do{
                JSONObject initItemData  = (JSONObject) initJson.clone();
                initItemData.put("time", df.format( lpDate));
                initialStatisticData.add(initItemData);
                if( 1 ==  unit ) {
                    //加一天
                    cal.add(Calendar.DATE, 1);
                } else if( 2 == unit ) {
                    //加一个小时
                    cal.add(Calendar.HOUR, 1);
                } else  {
                    //加一分钟
                    cal.add(Calendar.MINUTE, 1);
                }
                lpDate = cal.getTime();

            } while (  lpDate.before( endDate ) );



        }else{
            startDate = df.parse( startTime );
            endDate   = df.parse( endTime );
            Date lpDate = startDate;
            Calendar cal = Calendar.getInstance();
            cal.setTime( startDate );
            do{
                JSONObject initItemData  = (JSONObject) initJson.clone();
                initItemData.put("time", df.format( lpDate));
                initialStatisticData.add(initItemData);
                if( 1 ==  unit ) {
                    //加一天
                    cal.add(Calendar.DATE, 1);
                } else if( 2 == unit ) {
                    //加一个小时
                    cal.add(Calendar.HOUR, 1);
                } else  {
                    //加一分钟
                    cal.add(Calendar.MINUTE, 1);
                }
                lpDate = cal.getTime();

            } while (  lpDate.before( endDate ) );
        }





        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }

        return initialStatisticData;
    }

    //=======================================================================
    //轨迹分析

    public int analysisSuspectTrail( Task task ) {

        this.currentTaskId = task.getId();
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        JSONObject taskObject = new JSONObject();
        JSONArray  dataObject = new JSONArray();

        pushTaskProgress( 0 );

        try {

            JSONObject paramJson = JSONObject.parseObject( task.getParameter() );
            String startTime = paramJson.getString("startDate");
            String endTime =  paramJson.getString("endDate");
            String targetIMSI  = paramJson.getString("targetIMSI");
            JSONArray otherIMSIJa =  paramJson.getJSONArray("otherIMSI");

            ArrayList IMSIArray = new ArrayList();
            IMSIArray.add(targetIMSI);
            if (otherIMSIJa != null) {
                for (int i=0;i< otherIMSIJa.size();i++){
                    IMSIArray.add(otherIMSIJa.getString(i));
                }
            }

            int imsiLoop = IMSIArray.size();
            float imsiProgressStepVal = (float)(95/(float)imsiLoop);
            for( int i=0; i < imsiLoop;i++ ) {

                String suspectTrailIMSI = (String)IMSIArray.get(i);

                JSONObject someoneIMSIDataJO = new JSONObject();
                JSONArray  someoneIMSIDataJa = new JSONArray();
                someoneIMSIDataJO.put("imsi", suspectTrailIMSI);
                someoneIMSIDataJO.put("data", someoneIMSIDataJa);
                /*Long userId = SigletonBean.getUserId();*/
                Long userId = task.getCreatorId();
                List<Site> siteListByUserId = devService.getSiteListInfoByUserId(userId);
                
                List<Map<String, String>> targetSuspectTrailList = analysisShardingDao.getUeInfoBySuspect(
                    startTime, endTime, suspectTrailIMSI, siteListByUserId );

                Date  lastCaptureTime = null;
                Integer dataListLen = targetSuspectTrailList.size();
                float progressStep = (float)(imsiProgressStepVal/(float)dataListLen);

                for ( Map<String, String> targetSuspectTrail : targetSuspectTrailList ) {

                    String captureTimeStr = targetSuspectTrail.get("time");
                    Date captureTimeDate = TimeUtils.timeFormatter.parse(captureTimeStr);

                    if( null == lastCaptureTime) {
                        JSONObject jsonObject  = new JSONObject();
                        jsonObject.put("siteSN", targetSuspectTrail.get("siteSN"));
                        jsonObject.put("siteName", targetSuspectTrail.get("siteName"));
                        jsonObject.put("longitude", targetSuspectTrail.get("longitude"));
                        jsonObject.put("latitude", targetSuspectTrail.get("latitude"));
                        jsonObject.put("time", captureTimeStr);
                        someoneIMSIDataJa.add( jsonObject);
                        lastCaptureTime = captureTimeDate;
                    } else {
                        long diff = Math.abs(captureTimeDate.getTime() - lastCaptureTime.getTime());
                        long diffMinutes = (diff / (60 * 1000));
                        if( diffMinutes > 5  ) {  //时间间隔大于5分钟
                            JSONObject jsonObject  = new JSONObject();
                            jsonObject.put("siteSN", targetSuspectTrail.get("siteSN"));
                            jsonObject.put("siteName", targetSuspectTrail.get("siteName"));
                            jsonObject.put("longitude", targetSuspectTrail.get("longitude"));
                            jsonObject.put("latitude", targetSuspectTrail.get("latitude"));
                            jsonObject.put("time", captureTimeStr);
                            someoneIMSIDataJa.add( jsonObject);
                            lastCaptureTime = captureTimeDate;
                        }
                    }

                    pushTaskProgress( progressStep );
                }

                dataObject.add(someoneIMSIDataJO);
            }


            taskObject.put("taskId", task.getId());
            taskObject.put("taskName", task.getName());
            taskObject.put("data", dataObject);

            String txtPath = filePath;
            txtPath = txtPath.trim() + "/"
                    + task.getName().trim() + sdf.format( new Date())+ ".txt" ;

            File file = new File(txtPath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }

            //写入txt文件
            FileOutputStream fos = new FileOutputStream(txtPath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            String taskReport = taskObject.toJSONString();
            osw.write( taskReport );
            osw.flush();
            osw.close();
            fos.close();

            pushTaskProgress( 3 );

            task.setIsFinish(1);
            task.setFinishTime(TimeUtils.timeFormatterStr.format(new Date()));
            task.setResultExt(txtPath);
//            task.setResult( taskReport );
            taskMapper.updateTaskById( task );

            pushTaskProgress( 2 );


        } catch ( Exception e) {
            e.printStackTrace();
            taskMapper.updateTryCount( task.getId(), task.getTryCount() + 1);
        }

        this.currentTaskId = -1L;
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        return  0;
    }

    //=======================================================================
    //碰撞分析

    private String getImsiListStr(List<String> imsiList)
    {
    	
    	int totalCnt = imsiList.size();
    	if(totalCnt == 0)
    	{
    		return null;
    	}
    	
    	String imsiListStr ="'" + imsiList.get(0)+ "'" + ",";
    	
    	for(int i =1; i < totalCnt - 1; i++)
    	{
    		imsiListStr = imsiListStr + "'" + imsiList.get(i) + "'" + ",";
    	}
    	
    	imsiListStr = imsiListStr + "'" + imsiList.get(totalCnt -1) + "'" ;  
    	
    	return imsiListStr;
    	
    }
    public int analysisDataCollide( Task task ) {

        this.currentTaskId = task.getId();
        this.progressNowValue = 0;
        this.oldProgressValue = 0;
        int  totalCollideSize = 0;
        float progressStep2 = 0;

        JSONObject taskObject = new JSONObject();
        JSONArray  dataObject = new JSONArray();
        JSONArray  spaceTimeTempItems;
        List<UeInfo> ueInfoList;

        pushTaskProgress( 0 );

        try {

            JSONObject paramJson = JSONObject.parseObject( task.getParameter() );
            JSONArray  spaceTimeItems = paramJson.getJSONArray("items");

            String sumUeInfoTable="t_data_collide_sum";
            if( analysisShardingDao.existTempTable(sumUeInfoTable) > 0 ) {
                analysisShardingDao.truncateTempTable(sumUeInfoTable);
            }
            else
            {
            	analysisShardingDao.createDateCollideTempTable(sumUeInfoTable);
            }
            
            String tableName = "";
            tableName = "t_temp_imsi_1";
            if( analysisShardingDao.existTempTable(tableName) > 0 ) {
                analysisShardingDao.truncateTempTable(tableName);
            }
            
            else
            {
            	analysisShardingDao.createTempTable(tableName);
            }
           
            tableName = "t_temp_imsi_2";
            if( analysisShardingDao.existTempTable(tableName) > 0 ) {
                analysisShardingDao.truncateTempTable(tableName);
            }
            else
            {
            	analysisShardingDao.createTempTable(tableName);
            }
            
            this.getSpaceTimeSet(spaceTimeItems);
            Map<String, Object>  spaceTimeSet = spaceTimeSiteNameMaps;
            float progressStep1 = 80;
            
            if( spaceTimeSet.size() == 0 ){
                pushTaskProgress(80);
                pushTaskProgress(10);
            } else {
            	progressStep1 = (float)(80/(float)spaceTimeSet.size());
            	progressStep2 = (float)(10/(float)spaceTimeSet.size());
            }
                       
            for(Map.Entry<String, Object> entry:spaceTimeSet.entrySet())
            {
            	spaceTimeTempItems = (JSONArray)(entry.getValue());
             	
            	ueInfoList = this.getDataCollideResultList(spaceTimeTempItems, sumUeInfoTable, progressStep1);
 
				if( ueInfoList.size() == 0 ){
				     pushTaskProgress( progressStep2 );
				     continue;
				}
				
				totalCollideSize = totalCollideSize + ueInfoList.size();
				for ( UeInfo ueInfo : ueInfoList ) {

	                JSONObject recordObject = new JSONObject();

	                recordObject.put("id", ueInfo.getId() );
	               
	                if (entry.getKey().equals("all"))
	                {
	                	recordObject.put("imsi", ueInfo.getImsi() + "(" +   "all" + ")");
	                }
	                else
	                {
	                	recordObject.put("imsi", ueInfo.getImsi() + "(" +  entry.getKey() + "除外" + ")");
	                }
	                
	                recordObject.put("captureTime", ueInfo.getCaptureTime() );
	                if( ( null == ueInfo.getCityName() ) || ueInfo.getCityName().isEmpty() ) {
	                    recordObject.put("cityName", "" );
	                } else {
	                    recordObject.put("cityName", ueInfo.getCityName() );
	                }
	                if( ( null == ueInfo.getOperator() ) || ueInfo.getOperator().isEmpty() ) {
	                    recordObject.put("operator", "" );
	                } else {
	                    recordObject.put("operator", ueInfo.getOperator() );
	                }
	                recordObject.put("siteSN", ueInfo.getSiteSn());
	                recordObject.put("siteName", ueInfo.getSiteName());
	                recordObject.put("deviceSN", ueInfo.getDeviceSn());
	                recordObject.put("deviceName", ueInfo.getDeviceName());

	                dataObject.add(recordObject);
   
	            }
				
				pushTaskProgress( progressStep2 );
				
				analysisShardingDao.truncateTempTable("t_temp_imsi_1");
				analysisShardingDao.truncateTempTable("t_temp_imsi_2");
				analysisShardingDao.truncateTempTable(sumUeInfoTable);
            
            }
            
            logger.info("DataCollide Analysis UE count:" + totalCollideSize);
     
			analysisShardingDao.truncateTempTable("t_temp_imsi_1");
            analysisShardingDao.truncateTempTable("t_temp_imsi_2");

            if( analysisShardingDao.existTempTable(sumUeInfoTable) > 0 ) {
                analysisShardingDao.truncateTempTable(sumUeInfoTable);
            }

            //保存分析结果
            taskObject.put("taskId", task.getId());
            taskObject.put("taskName", task.getName());
            taskObject.put("total", totalCollideSize);
            taskObject.put("data", dataObject);

            String txtPath = filePath;
            txtPath = txtPath.trim() + "/"
                    + task.getName().trim() + sdf.format( new Date())+ ".txt" ;

            File file = new File(txtPath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }

            //写入txt文件
            FileOutputStream fos = new FileOutputStream(txtPath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            String taskReport = taskObject.toJSONString();
            osw.write( taskReport );
            osw.flush();
            osw.close();
            fos.close();

            pushTaskProgress( 5 );

            task.setIsFinish(1);
            task.setFinishTime(TimeUtils.timeFormatterStr.format(new Date()));
            task.setResultExt(txtPath);
            taskMapper.updateTaskById( task );

            pushTaskProgress( 5 );

        } catch ( Exception e ){
            e.printStackTrace();
            taskMapper.updateTryCount( task.getId(), task.getTryCount() + 1);
        }

        this.currentTaskId = -1L;
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        return 0;
    }
    
    private List<UeInfo> getDataCollideResultList(JSONArray  spaceTimeItems, String sumUeInfoTable, float progressStep1)
    {
    	
    	  String whereSql = "";
    	  String newWhereSql = "";
    	  String tableName = "t_temp_imsi_2";
    	  float progressStep3 = (float)(progressStep1/(float)spaceTimeItems.size());
          if( spaceTimeItems.size() == 0 ){
              pushTaskProgress( progressStep1 );
          } 
    	  for ( int i=0; i < spaceTimeItems.size(); i++ ) {

              JSONObject spaceTimeJObj = spaceTimeItems.getJSONObject(i);
              String startTime = spaceTimeJObj.getString("startTime").replace("/", "-");
              String endTime = spaceTimeJObj.getString("endTime").replace("/", "-");
              String siteSN  = spaceTimeJObj.getString("siteSN");

              whereSql= " WHERE SITE_SN='" + siteSN +
                      "' AND ( CAPTURE_TIME BETWEEN \"" + startTime + "\" AND " +
                      "\""+ endTime + "\" )";
              newWhereSql = whereSql;
              if( 0 == i%2 ) {
                  tableName = "t_temp_imsi_1";
                  if( i != 0  ){
                      whereSql = whereSql + " AND ( IMSI IN ( SELECT T.IMSI FROM t_temp_imsi_2 T ) )";
                      newWhereSql = newWhereSql + " AND ( IMSI IN ( SELECT T.IMSI FROM t_temp_imsi_1 T ) )";
                  }
              } else {
                  tableName = "t_temp_imsi_2";
                  whereSql = whereSql + " AND ( IMSI IN ( SELECT T.IMSI FROM t_temp_imsi_1 T ) )";
                  newWhereSql = newWhereSql + " AND ( IMSI IN ( SELECT T.IMSI FROM t_temp_imsi_2 T ) )";
              }
              
              analysisShardingDao.truncateTempTable( tableName );
              analysisShardingDao.insertDataCollideInterTable( tableName, whereSql, startTime, endTime );
              analysisShardingDao.insertDataCollideSumTableSharding( sumUeInfoTable, newWhereSql, startTime, endTime );

              pushTaskProgress( progressStep3 );
          }

          List<UeInfo> ueInfoList = analysisShardingDao.getDataCollideUeInfoList( sumUeInfoTable, tableName );
              //List<UeInfo> ueInfoList = new ArrayList<UeInfo> ();
            analysisShardingDao.truncateTempTable("t_temp_imsi_1");
            analysisShardingDao.truncateTempTable("t_temp_imsi_2");
            analysisShardingDao.truncateTempTable(sumUeInfoTable);
          return ueInfoList;
    }
    
    private void getSpaceTimeSet(JSONArray  spaceTimeItems)
    {
    	//Map<String, Object> spaceTimeSiteNameMaps = new HashMap<String,Object>(); 
    	spaceTimeSiteNameMaps.clear();
    	JSONArray  spaceTimeTempItems = null;
    	JSONObject spaceTimeJObj = null;
    	String siteSN = null;
    	
    	if (spaceTimeItems.size() > 2)
    	{
    		for(int i =0; i < spaceTimeItems.size(); i++)
        	{
        		spaceTimeTempItems = new JSONArray();
        		for(int j=0; j < spaceTimeItems.size(); j++)
        		{
        			if(j != i)
        			{
        				spaceTimeTempItems.add(spaceTimeItems.get(j));
        			}
        		}
        		
        		spaceTimeJObj = spaceTimeItems.getJSONObject(i);
        		siteSN  = spaceTimeJObj.getString("siteSN");
        		
        		spaceTimeSiteNameMaps.put(siteSN, spaceTimeTempItems);
        	}
    	}
  	
    	spaceTimeSiteNameMaps.put("all", spaceTimeItems);
    	return ;
    }
    
    
    //====================================================================================
    //IMSI伴随分析
    //

    public int analysisIMSIFollow( Task task ) {

        this.currentTaskId = task.getId();
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        JSONObject taskObject = new JSONObject();
        JSONArray  dataObject = new JSONArray();

        pushTaskProgress( 0 );

        try{

            JSONObject paramJson = JSONObject.parseObject( task.getParameter() );
            String startTime = paramJson.getString("startTime");
            String endTime = paramJson.getString("endTime");
            String targetIMSI = paramJson.getString("targetIMSI");
            int  topTime = Integer.parseInt(paramJson.getString("topTime"));
            int  bottomTime = Integer.parseInt(paramJson.getString("bottomTime"));
            //目标IMSI驻留站点
            Long userId = task.getCreatorId();/*SigletonBean.getUserId();*/
            List<Site> siteListByUserId = devService.getSiteListInfoByUserId(userId);
            
            List<Map<String, String>> targetUeInfoList = analysisShardingDao.selectUeInfoAggrByMinForIMSIFollow( startTime,
                                                                 endTime, targetIMSI, siteListByUserId );

            Integer targetUeInfoTotal  =  targetUeInfoList.size();
            if( targetUeInfoTotal > 1 ) {

                String tableName = "t_follow_imsi";
                if( analysisShardingDao.existTempTable(tableName) > 0 ) {
                    analysisShardingDao.truncateTempTable(tableName);
                }
                else
                {
                	analysisShardingDao.createTempTable(tableName);
                }
                

                float progressStep = (float)(50/(float)targetUeInfoList.size());
                if( targetUeInfoList.size() == 0) {
                    pushTaskProgress( 50 );
                }
                for (  Map<String, String> targetUeInfo : targetUeInfoList  ) {

                    String targetSiteSN = targetUeInfo.get("siteSN");
                    String targetCaptureTimeGroup= targetUeInfo.get("captureTime");

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date targetCaptureTime = df.parse( targetCaptureTimeGroup );
                    Calendar cal = Calendar.getInstance();
                    cal.setTime( targetCaptureTime );
                    cal.add(Calendar.MINUTE, -topTime );
                    Date intervalLowerTime  =   cal.getTime();
                    cal.add(Calendar.MINUTE, bottomTime);
                    Date intervalUpperTime =   cal.getTime();

                    DateFormat outDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String intervalLowerTimeStr = outDF.format(intervalLowerTime);
                    String intervalUpperTimeStr = outDF.format(intervalUpperTime);

                    Map<String,Object> params = new HashMap<String, Object>();
                    params.put("tableName", tableName );
                    params.put("imsi", targetIMSI );
                    params.put("siteSN", targetSiteSN );
                    params.put("startTime", intervalLowerTimeStr );
                    params.put("endTime", intervalUpperTimeStr );
                    analysisShardingDao.insertIMSIFollowInterTable( params );

                    pushTaskProgress( progressStep );

                }

                List<Map<String, String>> followRateList =  analysisShardingDao.getIMSIFollowRateList(tableName, targetUeInfoTotal);

                if( followRateList.size() == 0) {
                    pushTaskProgress( 40 );
                } else {
                    progressStep = (float)(40/(float)followRateList.size());
                }
                for ( Map<String, String> followRate : followRateList ) {
                    JSONObject rateRecord  = new JSONObject();
                    rateRecord.put("imsi", followRate.get("imsi"));
                    rateRecord.put("rate", followRate.get("rate"));
                    dataObject.add( rateRecord);

                    pushTaskProgress( progressStep );
                }

                analysisShardingDao.truncateTempTable(tableName);

                taskObject.put("total", followRateList.size());

            } else {
                //条件过少
                taskObject.put("result", false);
                taskObject.put("message", "条件过少");
                taskObject.put("total", 0);

                pushTaskProgress( 90 );
            }

            /////////////////////////
            //保存分析结果
            taskObject.put("taskId", task.getId());
            taskObject.put("taskName", task.getName());
            taskObject.put("data", dataObject);

            String txtPath = filePath;
            txtPath = txtPath.trim() + "/"
                    + task.getName().trim() + sdf.format( new Date())+ ".txt" ;

            File file = new File(txtPath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }

            //写入txt文件
            FileOutputStream fos = new FileOutputStream(txtPath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            String taskReport = taskObject.toJSONString();
            osw.write( taskReport );
            osw.flush();
            osw.close();
            fos.close();

            pushTaskProgress( 5 );

            task.setIsFinish(1);
            task.setFinishTime(TimeUtils.timeFormatterStr.format(new Date()));
            task.setResultExt(txtPath);
            taskMapper.updateTaskById( task );

            pushTaskProgress( 5 );


        } catch (Exception e){
            e.printStackTrace();
            taskMapper.updateTryCount( task.getId(), task.getTryCount() + 1);
        }

        this.currentTaskId = -1L;
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        return 0;
    }

    //===================================================================================
    //外来和常驻人口分析
    //
    public int analysisResidentPeople( Task task ) {

        this.currentTaskId = task.getId();
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        String tableName = "t_resident_imsi_temp";

        JSONObject taskObject = new JSONObject();
        JSONObject  dataObject = new JSONObject();
        JSONArray  fixedDataObject = new JSONArray();
        JSONArray  periodDataObject = new JSONArray();

        pushTaskProgress( 0 );

        try{

            JSONObject paramJson = JSONObject.parseObject( task.getParameter() );
            String startTime = paramJson.getString("startTime");
            String endTime = paramJson.getString("endTime");
            Long  areaId = paramJson.getLong("areaId");
//            String areaName = paramJson.getString("areaName");

            List<Site> sites1 = getSitesByAreaId(areaId);
        	/*Long userId = SigletonBean.getUserId();*/
            Long userId = task.getCreatorId();
            List<Site> siteList= devService.getSiteListInfoByUserId(userId);
            List<Site> sites = getSitesFilterByUserId(siteList, sites1);
            if( (sites != null)  && (sites.size() > 0 ) ) {

                if( analysisShardingDao.existTempTable(tableName) > 0 ) {
                    analysisShardingDao.truncateTempTable(tableName);
                }
                else
                {
                	analysisShardingDao.createTempTable(tableName);
                }
                Date startDate = TimeUtils.timeFormatter.parse(startTime);
                Date endDate = TimeUtils.timeFormatter.parse(endTime);
                Long diff = endDate.getTime() - startDate.getTime();
                Long totalDay = diff/(24*60*60*1000);

                Calendar cal = Calendar.getInstance();
                cal.setTime( startDate );
                Date lpDate = startDate;

                float progressStep = (float)(50/(float)totalDay);
                while ( lpDate.before( endDate ) ) {

                    String loopTime = TimeUtils.dayFormatter.format( lpDate );
                    Map<String,Object> params = new HashMap<String, Object>();
                    params.put("tableName", tableName);
                    params.put("loopTime", loopTime);
                    params.put("sites", sites);
                    analysisShardingDao.insertResidentPeopleInterTable(params);

                    //加一天
                    cal.add(Calendar.DATE, 1);
                    lpDate = cal.getTime();

                    pushTaskProgress( progressStep );
                }

                Long totalUeInfo = analysisShardingDao.getTotalCountResidentPeople( tableName );
                Long fixedUeInfo = analysisShardingDao.getFixedCountResidentPeople( tableName, totalDay );
                Long periodUeInfo = analysisShardingDao.getPeriodCountResidentPeople( tableName, totalDay );

                if( fixedUeInfo == 0 ) {
                    pushTaskProgress( 20 );
                } else {
                    progressStep = (float)(20/(float)fixedUeInfo);
                }

                for( int i=0; i<fixedUeInfo; i += 10000 ) {
                    List<Map<String, String>> fixedRateList =  analysisShardingDao.getResidentPeopleFixedRateList(tableName,
                            totalDay, i, 10000 );

                    for ( Map<String, String> fixedRate : fixedRateList ) {
                        JSONObject rateRecord  = new JSONObject();
                        rateRecord.put("imsi", fixedRate.get("imsi"));
                        rateRecord.put("rate", fixedRate.get("rate"));
                        fixedDataObject.add( rateRecord);

                        pushTaskProgress( progressStep );
                    }

                }

                if( periodUeInfo == 0 ) {
                    pushTaskProgress( 20 );
                } else {
                    progressStep = (float)(20/(float)periodUeInfo);
                }

                for( int i=0; i<periodUeInfo; i += 10000 ) {
                    List<Map<String, String>> periodRateList =  analysisShardingDao.getResidentPeoplePeriodRateList(tableName,
                            totalDay , i, 10000 );

                    for ( Map<String, String> periodRate : periodRateList ) {
                        JSONObject rateRecord  = new JSONObject();
                        rateRecord.put("imsi", periodRate.get("imsi"));
                        rateRecord.put("rate", periodRate.get("rate"));
                        periodDataObject.add( rateRecord);

                        pushTaskProgress( progressStep );
                    }
                }

                taskObject.put("total", totalUeInfo );
                taskObject.put("fixed", fixedUeInfo );
                taskObject.put("period", periodUeInfo );


            } else {
                //没有站点
                taskObject.put("result", false);
                taskObject.put("message", "没有站点信息");
                taskObject.put("total", 0 );
                taskObject.put("fixed", 0 );
                taskObject.put("period", 0 );

            }


            /////////////////////////
            //保存分析结果
            taskObject.put("taskId", task.getId());
            taskObject.put("taskName", task.getName());
            taskObject.put("data", dataObject);
            dataObject.put("fixed", fixedDataObject );
            dataObject.put("period",periodDataObject );

            String txtPath = filePath;
            txtPath = txtPath.trim() + "/"
                    + task.getName().trim() + sdf.format( new Date())+ ".txt" ;

            File file = new File(txtPath);
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }

            //写入txt文件
            FileOutputStream fos = new FileOutputStream(txtPath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            String taskReport = taskObject.toJSONString();
            osw.write( taskReport );
            osw.flush();
            osw.close();
            fos.close();

            pushTaskProgress( 5 );

            task.setIsFinish(1);
            task.setFinishTime(TimeUtils.timeFormatterStr.format(new Date()));
            task.setResultExt(txtPath);
            taskMapper.updateTaskById( task );

            pushTaskProgress( 5 );

        } catch ( Exception e ) {
            e.printStackTrace();
            taskMapper.updateTryCount( task.getId(), task.getTryCount() + 1);
        }

        if( analysisShardingDao.existTempTable(tableName) > 0 ) {
            analysisShardingDao.truncateTempTable(tableName);
        }

        this.currentTaskId = -1L;
        this.progressNowValue = 0;
        this.oldProgressValue = 0;

        return 0;
    }
    
    private List<Site> getSitesFilterByUserId(List<Site> siteListByUserId,  List<Site> siteListByAreaId)
    {
    	List<Site> filterSiteList = new  ArrayList<Site>();
    	
    	for(int i =0; i < siteListByUserId.size(); i ++)
    	{
    		Site siteByUserId = siteListByUserId.get(i);
    		for(int j =0; j < siteListByAreaId.size(); j ++)
    		{
    			
    			if (siteByUserId.getSn().equals(siteListByAreaId.get(j).getSn()))
    			{
    				filterSiteList.add(siteByUserId);
    			}
    		}
    		
    	}
    	
    	return filterSiteList;
    }

    public List<Site> getSitesByAreaId( Long areaId ) {
        List<Site> sites = null;
        AreaCode areaCode = areaCodeMapper.selectByPrimaryKey(areaId );
        if( null != areaCode) {
            Map<String,Object> params = new HashMap<String, Object>();
            if( 0 == areaCode.getLevel() ) { //国家
                //nothing
            } else if( 1 == areaCode.getLevel()  ) { //省
                params.put("provinceId", areaId);
            } else if( 2 == areaCode.getLevel()  ) { //市
                params.put("cityId", areaId);
            }else if( 3 == areaCode.getLevel()  ) { //区
                params.put("townId", areaId );
            }
            sites = siteMapper.selectSitesByAreaId( params );
        }
        return sites;
    }


}
