package com.iekun.ef.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.dao.TaskMapper;
import com.iekun.ef.model.*;
import com.iekun.ef.push.NoticePush;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 数据分析等任务服务类
 * Created by feilong.cai on 2016/11/25.
 */
@Service
public class TaskService {

    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Value(" ${tasksave.filePath}")
    private String filePath;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private SystemParaService sysParaServ;
    
    @Autowired
    private NoticePush noticePush;
    
	//@Value(" ${ftpserver.ip}")
	private String ftpServerIp;
	
	//@Value(" ${ftpserver.port}")
	private String ftpServerPort;
	
	@Autowired
	ExportDataService exportDataService;

    public int createTask( StatisticsTask task ) {

        try {
            taskMapper.insertTask( task );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 0;
        }

        noticePush.pushTasks( task.getCreatorId() );

        return 0;
    }

    public int createTask( SuspectTrailTask task ) {

        try {
            taskMapper.insertTask( task );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 1;
        }

        noticePush.pushTasks( task.getCreatorId() );

        return 0;
    }

    public int createTask( DataCollideTask task) {

        try {
            taskMapper.insertTask( task );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 1;
        }

        noticePush.pushTasks( task.getCreatorId() );

        return 0;
    }


    public int createTask(IMSIFollowTask task) {

        try {
            taskMapper.insertTask( task );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 1;
        }

        noticePush.pushTasks( task.getCreatorId() );

        return 0;
    }

    public int createTask( ResidentPeopleTask task) {

        try {
            taskMapper.insertTask( task );
        } catch ( Exception e ) {
            e.printStackTrace();
            return 1;
        }

        noticePush.pushTasks( task.getCreatorId() );

        return 0;
    }


    public int delTask( Long taskId ) {

        try {
            if( taskId == analysisService.getCurrentTaskId() ) {
                return 1;
            }

            Task task = taskMapper.getTaskById( taskId );

            //取更新状态DELETE_FLAG = 1
            taskMapper.deleteByID( taskId );
            //0:未完成 1：已完成
            if( 0 == task.getIsFinish()  ) {
                noticePush.pushTasks( task.getCreatorId() );
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    /**
     * 检验能否下载zip文件
     * */
    public JSONObject queryTxt(long taskId){

        JSONObject jsonObject = new JSONObject();
        Task task = new Task();
        try{
            task = taskMapper.getTaskById( taskId );
            if(task.getResultExt() != null){
                String txtName = new File(task.getResultExt()).getName();//运营商2017-11-07.txt
                String zipName = txtName.substring(0,txtName.lastIndexOf(".")) + ".zip";
                File zipFile = new File(filePath + "/" + zipName);
                //判断zip文件是否存在
                if(zipFile.exists()){
                    jsonObject.put("status", true);
                    jsonObject.put("message", "正在下载，请稍后...");
                }else{
                       //不存在则创建zip文件
                       String result = createZipFile(zipName,task.getResultExt());
                   if(result.equals("true")){
                       jsonObject.put("status", true);
                       jsonObject.put("message", "正在下载，请稍后...");
                   }else{
                       jsonObject.put("status", false);
                       jsonObject.put("message", "创建zip文件失败，请尝试重新导出!");
                   }
                }
            }else{
                jsonObject.put("status", false);
                jsonObject.put("message", "系统找不到指定的文件!");
            }
        }catch(Exception e){
            jsonObject.put("status", false);
            jsonObject.put("message", "系统找不到指定的文件!");
            e.printStackTrace();
        }
        return jsonObject;

    }

    /**
     * 下载zip文件
     * */
    public void exportZip(String taskId, HttpServletResponse response){

        JSONObject jsonObject = new JSONObject();

        try{
            Task task = taskMapper.getTaskById(Long.parseLong(taskId));
            String resultPath = task.getResultExt(); //E://AllTxt/运营商2017-11-07.txt
            String zipName = resultPath.substring(0, resultPath.lastIndexOf(".")) + ".zip";
            File zipFile = new File(zipName);

            //读写zip文件
            InputStream fis = new BufferedInputStream(new FileInputStream(zipFile));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            response.reset();
            String targetName = zipName.substring(zipName.lastIndexOf("/") + 1);
            targetName = new String(targetName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + targetName + "\"");
            response.addHeader("Content-lenth", "" + buffer.length);
            response.setContentType("application/octet-stream;charset=UTF-8");

            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(buffer);
            os.flush();
            os.close();
            response.flushBuffer();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 创建zip文件
     * */
    public String createZipFile(String zipName, String targetPath)throws Exception{
        boolean status = false;

        File file = new File(targetPath) ; // 定义要压缩的文件
        File zipFile = new File(filePath.trim() + "/" + zipName) ;//定义输出位置和文件名
        InputStream input = new FileInputStream(file) ; // 定义文件的输入流
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile)) ; // 声明压缩流对象
        zipOut.putNextEntry(new ZipEntry(file.getName())) ; // 设置ZipEntry对象

        try {
            int temp = 0 ;
            byte[] tempbytes = new byte[1024*1024];
            while ((temp = input.read(tempbytes)) != -1) { // 读取内容
                zipOut.write(tempbytes, 0, temp); // 压缩输出
            }
            input.close(); // 关闭输入流
            zipOut.close(); // 关输出流
            status = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        if(status){
            return "true";
        }else{
            return "false";
        }

    }

    public String exportTask( Long taskId ){
    	
    	String exportFilePath = null;
    	String resultExtPath = null;
    	String filePath = null;
    	String destUrl = null;
    	String formatExtPath = null;
    	Task task = taskMapper.getTaskById( taskId );
 
    	exportFilePath = task.getExportPath();
    	if(exportFilePath == null)
    	{
    		resultExtPath = task.getResultExt();
    		int index = resultExtPath.lastIndexOf('/');
    		filePath  = resultExtPath.substring(0, index);
       		try {
    			Runtime.getRuntime().exec("chmod -R 777 " + filePath);
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}

    		exportFilePath = filePath + "/" +taskId.toString() + ".zip";
    		
    		formatExtPath = this.srcFileToExportfileFormat(task, taskId, filePath);
    		if (formatExtPath == null)
    		{
    			return null;
    		}
     		exportDataService.createZipfile(exportFilePath, formatExtPath, filePath);
     		//exportDataService.createZipfile(exportFilePath, resultExtPath, filePath);
    		
    		task.setExportPath(exportFilePath);
    		taskMapper.updateTaskById(task);
       	}
    	
    	ftpServerPort = sysParaServ.getSysPara("ftpPort");
		ftpServerIp = sysParaServ.getSysPara("ftpIp");
		
    	if((ftpServerPort.trim()).equals("21"))
		{
			//destUrl = "ftp://" + exportFilePath.replaceFirst("/var/ftp" ,ftpServerIp.trim());/var/ftp/pub/taskReport/2017-11-06/217-AAA.txt
            destUrl = "ftp://" + task.getResultExt().replaceFirst("/var/ftp" ,ftpServerIp.trim());
		}
		else
		{
			destUrl = "ftp://" + exportFilePath.replaceFirst("/var/ftp" ,(ftpServerIp.trim() + ":" + ftpServerPort.trim()));
		}
    	logger.info("exportTask:" + destUrl);

    	return destUrl;

    }


    public String srcFileToExportfileFormat(Task task, Long taskId, String srcFilePath)
    {
    	String exportFile = null;
    	JSONObject reportDataJSObj = null;
    	JSONObject conditionJSObj  = null;
    	String taskReport = null;
    	JSONObject taskObject = null;
    	JSONArray ja = null;
    	JSONArray dataJa = null;
    	JSONArray innerDataJa = null;
    	JSONObject dataObj = null;
    	JSONObject innerDataObj = null;
    	JSONObject itemObj = null;
    	//String txtPath = null;
     	switch(task.getType())
    	{
    		case 0: /* 上号统计 */
    		taskReport = "上号统计:  "+  System.getProperty("line.separator");	
     		taskObject = JSONObject.parseObject( task.getParameter());
     		taskReport = taskReport + taskObject.get("startDate").toString() + "~" + taskObject.get("endDate").toString()+  System.getProperty("line.separator");
    		JSONArray itemArray = taskObject.getJSONArray("items");
    		
    		taskReport = taskReport + "统计项:  " +  System.getProperty("line.separator");
    		for(int i= 0; i < itemArray.size(); i++)
    		{
    			itemObj =  (JSONObject)(itemArray.get(i));
       			taskReport = taskReport + itemObj.get("name").toString() +  System.getProperty("line.separator");
    		}
    		logger.info("taskId: " + taskId);
    		reportDataJSObj = this.getImsiStatisticsReportData( taskId );
    		if (reportDataJSObj == null)
    		{
    			return null;
    		}
    		logger.info("reportDataJSObj" + reportDataJSObj.toString());
     		taskReport = taskReport + "上号总数:  " + ((JSONObject)reportDataJSObj.get("data")).get("sumTotal")+ System.getProperty("line.separator");
     		logger.info("taskReport: " + taskReport);
    		dataObj =  (JSONObject)((JSONObject)reportDataJSObj.get("data")).get("data");
    		dataJa =  (JSONArray)dataObj.get("data");
    		if (dataJa.size() != 0)
    		{
    			for(int j =0; j < dataJa.size(); j ++)
    			{
    				itemObj = (JSONObject)(dataJa.get(j));
    				taskReport = taskReport + itemObj.toString() + System.getProperty("line.separator");
    			}
    			
    		}
    		//taskReport = taskReport + dataObj.get("data").toString();//.toJSONString(); 
    		break;
    		
    		case 1: /* 轨迹分析 */
    		String taskImsi =  "轨迹分析:  "+ System.getProperty("line.separator");	
    		reportDataJSObj = this.getSuspectTrailReportData( taskId ); 
    		if (reportDataJSObj == null)
    		{
    			return null;
    		}
    		taskObject = JSONObject.parseObject( task.getParameter());
    		taskImsi =  taskImsi + "目标imsi: ";
    		taskImsi += taskObject.get("targetIMSI").toString()+ "  "+ System.getProperty("line.separator");
    		ja = (JSONArray)taskObject.get("otherIMSI");
    		if (ja.size() != 0)
    		{
    			for(int i =0; i < ja.size(); i ++)
    			{
    				taskImsi = taskImsi+ "          " +ja.get(i).toString()+ "  "+ System.getProperty("line.separator");
    			}
    			
    		}
    		taskReport =  taskImsi + taskObject.get("startDate").toString()+ "~" + taskObject.get("endDate").toString()+ System.getProperty("line.separator");
    		dataObj =  (JSONObject)reportDataJSObj.get("data");
    		dataJa =  (JSONArray)dataObj.get("data");
    		if (dataJa.size() != 0)
    		{
    			for(int j =0; j < dataJa.size(); j ++)
    			{
    				itemObj = (JSONObject)(dataJa.get(j));
    				taskReport = taskReport + " imsi: " + itemObj.get("imsi").toString() + System.getProperty("line.separator");
    				innerDataJa = (JSONArray)itemObj.get("data");
    				if(innerDataJa.size() != 0)
    				{
    					for(int k =0; k < innerDataJa.size(); k ++)
    	    			{
    						JSONObject dataTraceJa = (JSONObject)(innerDataJa.get(k));
    						taskReport = taskReport+ "  " + dataTraceJa.get("siteName").toString()+ "            " + dataTraceJa.get("time").toString()  + "  " + System.getProperty("line.separator");
    	    			}
    				}
    				
    			}
    			
    		}
    		//taskReport = taskReport + dataObj.get("data").toString();
    		break;
    		
    		case 2: /* 数据碰撞  */
    		String siteTimeCondition = " 数据碰撞:  " + System.getProperty("line.separator");
    		reportDataJSObj = this.getDataCollideReportData( taskId );
    		if (reportDataJSObj == null)
    		{
    			return null;
    		}
    		JSONObject itemJa;
    		taskObject = JSONObject.parseObject( task.getParameter());
    		siteTimeCondition = siteTimeCondition + " 时空条件 :" + System.getProperty("line.separator");
    		ja = (JSONArray)taskObject.get("items");
    		if (ja.size() != 0)
    		{
    			for(int i =0; i < ja.size(); i ++)
    			{
    				itemJa = (JSONObject)(ja.get(i));
    				siteTimeCondition = siteTimeCondition+ "  " +itemJa.get("siteName") + ":" + itemJa.get("rangeTime")+ System.getProperty("line.separator");
    			}
    			
    		}
    		
    		//dataObj =  (JSONObject)reportDataJSObj.get("data");
    		taskReport = siteTimeCondition + "总数:  " + reportDataJSObj.get("total") + System.getProperty("line.separator");
    		dataJa = (JSONArray)reportDataJSObj.get("data");
    		if (dataJa.size() != 0)
    		{
    			for(int j =0; j < dataJa.size(); j ++)
    			{
    				itemJa = (JSONObject)(dataJa.get(j));
    				taskReport = taskReport + itemJa.toString() + System.getProperty("line.separator");
    			}
    			
    		}
    	    //taskReport =  taskReport + reportDataJSObj.get("data").toString();
    		break;
    		
    		case 3: /* 常驻人口分析 */
    		String areaTimeCondition = "常驻人口分析:  "+ System.getProperty("line.separator");  
    		reportDataJSObj = this.getResidentPeopleReportData( taskId );
    		if (reportDataJSObj == null)
    		{
    			return null;
    		}
    		taskObject = JSONObject.parseObject( task.getParameter());
    		areaTimeCondition += areaTimeCondition + " 地域时间条件 :" + System.getProperty("line.separator");
    		areaTimeCondition += (taskObject.get("areaName").toString() + "  " + taskObject.get("startTime").toString()+ "~" + taskObject.get("endTime").toString()) + System.getProperty("line.separator");
    		dataObj =  (JSONObject)reportDataJSObj.get("data");
    		logger.info("常驻人口分析:" + dataObj.toString());
    		taskReport =  taskReport + areaTimeCondition + "总数:  " + ((JSONObject)reportDataJSObj.get("data")).get("total") + System.getProperty("line.separator"); 
    		
    		dataJa = (JSONArray)dataObj.get("period");
    		if (dataJa.size() != 0)
    		{
    			for(int j =0; j < dataJa.size(); j ++)
    			{
    				itemObj = (JSONObject)(dataJa.get(j));
    				taskReport = taskReport + itemObj.toString() + System.getProperty("line.separator");
    			}
    			
    		}
    		//taskReport = "暂驻人数:  " + dataObj.get("period");
    		
    		dataJa = (JSONArray)dataObj.get("fixed");
    		if (dataJa.size() != 0)
    		{
    			for(int j =0; j < dataJa.size(); j ++)
    			{
    				itemObj = (JSONObject)(dataJa.get(j));
    				taskReport = taskReport + itemObj.toString() + System.getProperty("line.separator");
    			}
    			
    		}
    		//taskReport = "常驻人数:  " + dataObj.get("fixed");
    		//taskReport = taskReport + dataObj.toString();
    		break;
    		
    		case 4:/* imsi伴随 */
    		String targetImsiTimeCondition = "imsi伴随:  "+ System.getProperty("line.separator");
    		reportDataJSObj = this.getIMSIFollowReportData( taskId );
    		if (reportDataJSObj == null)
    		{
    			return null;
    		}
    		taskObject = JSONObject.parseObject( task.getParameter());
    		targetImsiTimeCondition = targetImsiTimeCondition + " 目标imsi与时间条件 :" + System.getProperty("line.separator");
    		targetImsiTimeCondition += (taskObject.get("targetIMSI").toString() + "  " + taskObject.get("startTime").toString()+ "~" + taskObject.get("endTime").toString()) + System.getProperty("line.separator");
    		JSONArray dataArrayObj =  (JSONArray)reportDataJSObj.get("data");
    		logger.info("imsi伴随:" + dataArrayObj.toString());
    		taskReport =  targetImsiTimeCondition + "总数:  " + reportDataJSObj.get("total") + System.getProperty("line.separator");
     		taskReport =  taskReport + dataArrayObj.toString(); 
    		break;
    		
    		default:
    		break;
    	}
    	
     	Task getTask  = taskMapper.getTaskById(taskId);
     	
    	exportFile = srcFilePath.trim() + "/" + getTask.getName() /*taskId*/ + ".txt" ;
		File file = new File(exportFile);
        if(!file.getParentFile().exists()){
             file.getParentFile().mkdirs();
         }
        try {
			if(!file.exists()){
			     file.createNewFile();
			 }
			 //写入txt文件
			FileOutputStream fos = new FileOutputStream(exportFile);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write( taskReport );
			osw.flush();
			osw.close();
			fos.close();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return exportFile;
    }
    
    
    public List<Task> getTasksByUserId( Long userId ) {

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("creatorId",  userId );

        List<Task>  tasks = taskMapper.getTasks(params);
        for ( Task task: tasks ) {
            float taskProcess = 0;
            if( 1 == task.getIsFinish() ) {
                taskProcess = 100;
            } else {
                if( analysisService.getCurrentTaskId().equals(task.getId()) ){
                    taskProcess = analysisService.getProgressNowValue();
                }
            }
            task.setNowValue( taskProcess );
        }

        return tasks;
    }

    public List<Task> getProcessTasksByUserId( Long userId ) {

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("creatorId",  userId );
        params.put("isFinish",  0 );

        //获取当前用户下未完成任务列表
        List<Task>  tasks = taskMapper.getTasks(params);
        for ( Task task: tasks ) {
            float taskProcess = 0;
            if( analysisService.getCurrentTaskId().equals(task.getId()) ) {
                   taskProcess = analysisService.getProgressNowValue();
            }

            task.setNowValue( taskProcess );
        }

        return tasks;
    }


    public List<StatisticsTask> getIMSIStatisticsTasksByUserId( Long userId ) {

        List<StatisticsTask>  statisticsTasks = new LinkedList<>();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("type",  StatisticsTask.taskType );
        params.put("creatorId",  userId );

        List<Task>  tasks = taskMapper.getTasks(params);
        for ( Task task: tasks ) {

            JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;
            JSONArray itemsObject = jsonObject.getJSONArray("items");

            float taskProcess = 0;
            if( 1 == task.getIsFinish() ) {
                taskProcess = 100;
            } else {
                if( analysisService.getCurrentTaskId().equals(task.getId())){
                    taskProcess = analysisService.getProgressNowValue();
                }
            }

            StatisticsTask statisticsTask = new StatisticsTask( task.getId(), task.getName(), task.getParameter(),
                    task.getResult(), task.getResultExt(), task.getIsFinish(), taskProcess, task.getRemark(),
                    task.getCreateTime(), task.getFinishTime(), task.getCreatorId(), task.getDeleteFlag(),
                    jsonObject.getIntValue("type"), jsonObject.getIntValue("unit"), itemsObject,
                    jsonObject.getString("startDate"), jsonObject.getString("endDate"));

            statisticsTasks.add( statisticsTask );

        }

        return statisticsTasks;
    }


    public StatisticsTask getStatisticsTaskById( Long taskId ) {

        Task task  = taskMapper.getTaskById(taskId);

        JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;
        JSONArray itemsObject = jsonObject.getJSONArray("items");

        StatisticsTask statisticsTask = new StatisticsTask( task.getId(), task.getName(), task.getParameter(),
                task.getResult(), task.getResultExt(), task.getIsFinish(), 0, task.getRemark(),
                task.getCreateTime(), task.getFinishTime(), task.getCreatorId(), task.getDeleteFlag(),
                jsonObject.getIntValue("type"), jsonObject.getIntValue("unit"), itemsObject,
                jsonObject.getString("startDate"), jsonObject.getString("endDate"));

        return statisticsTask;

    }

    public JSONObject getImsiStatisticsReportData( Long taskId ) {

        JSONObject jsonObject = new JSONObject();
        StatisticsTask statisticsTask = getStatisticsTaskById( taskId );

//        String reportData = statisticsTask.getResult();
//        JSONObject taskObject = JSONObject.parseObject( reportData );
//        jsonObject.put("data",taskObject);

        String reportPath =  statisticsTask.getResultExt();

        try {
            File file=new File(reportPath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "UTF8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String reportTxt = "";
                while((lineTxt = bufferedReader.readLine()) != null){
                    reportTxt = reportTxt + lineTxt;
                }
                read.close();

                JSONObject taskObject = JSONObject.parseObject( reportTxt );
                jsonObject.put("data",taskObject);

            }else{
                jsonObject.put("status", false);
                jsonObject.put("message", "报告数据不存在");
                logger.error("找不到指定的文件");
            }
        } catch (Exception e) {
            jsonObject.put("status", false);
            jsonObject.put("message", "报告数据读取错误");
            logger.error("读取文件内容出错");
            e.printStackTrace();
        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");

        return  jsonObject;
    }


    public List<SuspectTrailTask> getSuspectTrailTasksByUserId ( Long userId ) {
        List<SuspectTrailTask>  suspectTrailTasks = new LinkedList<>();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("type",  SuspectTrailTask.taskType );
        params.put("creatorId",  userId );

        List<Task>  tasks = taskMapper.getTasks(params);
        for ( Task task: tasks ) {

            JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;

            float taskProcess = 0;
            if( 1 == task.getIsFinish() ) {
                taskProcess = 100;
            } else {
                if( analysisService.getCurrentTaskId().equals(task.getId()) ){
                    taskProcess = analysisService.getProgressNowValue();
                }
            }

            JSONArray otherIMSIJa =  jsonObject.getJSONArray("otherIMSI");
            ArrayList otherIMSIArray = new ArrayList();
            if (otherIMSIJa != null) {
                for (int i=0;i< otherIMSIJa.size();i++){
                    otherIMSIArray.add(otherIMSIJa.getString(i));
                }
            }

            SuspectTrailTask suspectTrailTask = new SuspectTrailTask(  task.getId(), task.getName(), task.getParameter(),
                    task.getResult(), task.getResultExt(), task.getIsFinish(), taskProcess, task.getRemark(),
                    task.getCreateTime(), task.getFinishTime(), task.getCreatorId(), task.getDeleteFlag(),
                    jsonObject.getString("targetIMSI"), otherIMSIArray ,jsonObject.getString("startDate"),
                    jsonObject.getString("endDate") );

            suspectTrailTasks.add( suspectTrailTask );

        }

        return suspectTrailTasks;
    }


    public SuspectTrailTask getSuspectTrailTaskById( Long taskId ) {

        Task task  = taskMapper.getTaskById(taskId);

        JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;

        JSONArray otherIMSIJa =  jsonObject.getJSONArray("otherIMSI");
        ArrayList otherIMSIArray = new ArrayList();
        if (otherIMSIJa != null) {
            for (int i=0;i< otherIMSIJa.size();i++){
                otherIMSIArray.add(otherIMSIJa.getString(i));
            }
        }

        SuspectTrailTask suspectTrailTask = new SuspectTrailTask(  task.getId(), task.getName(), task.getParameter(),
                task.getResult(), task.getResultExt(), task.getIsFinish(), 0, task.getRemark(),
                task.getCreateTime(), task.getFinishTime(), task.getCreatorId(), task.getDeleteFlag(),
                jsonObject.getString("targetIMSI"), otherIMSIArray, jsonObject.getString("startDate"),
                jsonObject.getString("endDate") );

        return suspectTrailTask;

    }

    public  JSONObject getSuspectTrailReportData( Long taskId ){

        JSONObject jsonObject = new JSONObject();
        SuspectTrailTask suspectTrailTask = getSuspectTrailTaskById( taskId );

        String reportPath =  suspectTrailTask.getResultExt();

        try {
            File file=new File(reportPath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "UTF8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String reportTxt = "";
                while((lineTxt = bufferedReader.readLine()) != null){
                    reportTxt = reportTxt + lineTxt;
                }
                read.close();

                JSONObject taskObject = JSONObject.parseObject( reportTxt );
                jsonObject.put("data",taskObject);

                jsonObject.put("status", true);
                jsonObject.put("message", "成功");

            }else{
                jsonObject.put("status", false);
                jsonObject.put("message", "报告数据不存在");
                logger.error("找不到指定的文件");
            }
        } catch (Exception e) {
            jsonObject.put("status", false);
            jsonObject.put("message", "报告数据读取错误");
            logger.error("读取文件内容出错");
            e.printStackTrace();
        }

        return  jsonObject;
    }

    //============================================================================
    // 碰撞任务
    //
    public List<DataCollideTask> getDataCollideTasksByUserId( Long userId ) {

        List<DataCollideTask>  dataCollideTasks = new LinkedList<>();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("type",  DataCollideTask.taskType );
        params.put("creatorId",  userId );

        List<Task>  tasks = taskMapper.getTasks(params);
        for ( Task task: tasks ) {

            JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;
            JSONArray  jsonItems = jsonObject.getJSONArray("items");

            float taskProcess = 0;
            if( 1 == task.getIsFinish() ) {
                taskProcess = 100;
            } else {
                if( analysisService.getCurrentTaskId().equals(task.getId())){
                    taskProcess = analysisService.getProgressNowValue();
                }
            }

            DataCollideTask  dataCollideTask  = new DataCollideTask( task.getId(), task.getName(),
                    task.getParameter(), task.getResult(), task.getResultExt(), task.getIsFinish(),
                    taskProcess, task.getRemark(), task.getCreateTime(), task.getFinishTime(),
                    task.getCreatorId(), task.getDeleteFlag(), jsonItems );

            dataCollideTasks.add( dataCollideTask );

        }

        return dataCollideTasks;
    }

    public DataCollideTask getDataCollideTaskById( Long taskId ) {

        Task task  = taskMapper.getTaskById(taskId);

        JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;
        //时空条件
        JSONArray  jsonItems = jsonObject.getJSONArray("items");

        DataCollideTask dataCollideTask = new DataCollideTask(   task.getId(), task.getName(),
                task.getParameter(), task.getResult(), task.getResultExt(), task.getIsFinish(),
                0, task.getRemark(), task.getCreateTime(), task.getFinishTime(),
                task.getCreatorId(), task.getDeleteFlag(), jsonItems);

        return dataCollideTask;

    }

    /**
     * 读取文件内容
     * */
    public  JSONObject getDataCollideReportData( Long taskId ){

        JSONObject taskObject = null;
        //获取数据碰撞分析对象
        DataCollideTask dataCollideTask = getDataCollideTaskById( taskId );
        //存放文件路径
        String reportPath =  dataCollideTask.getResultExt();

        try {
            //将文件读取到taskObject
            File file=new File(reportPath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "UTF8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String reportTxt = "";
                while((lineTxt = bufferedReader.readLine()) != null){
                    reportTxt = reportTxt + lineTxt;
                }
                read.close();

                taskObject = JSONObject.parseObject( reportTxt );

            }

        } catch (Exception e) {

            logger.error("读取文件内容出错");
            e.printStackTrace();
        }


        return  taskObject;
    }


    //============================================================
    //伴随
    //
    public List<IMSIFollowTask> getIMSIFollowTasksByUserId( Long userId ) {

        List<IMSIFollowTask>  imsiFollowTasks = new LinkedList<>();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("type",  IMSIFollowTask.taskType );
        params.put("creatorId",  userId );

        List<Task>  tasks = taskMapper.getTasks(params);
        for ( Task task: tasks ) {

            JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;

            float taskProcess = 0;
            if( 1 == task.getIsFinish() ) {
                taskProcess = 100;
            } else {
                if( analysisService.getCurrentTaskId().equals(task.getId()) ){
                    taskProcess = analysisService.getProgressNowValue();
                }
            }

            IMSIFollowTask  imsiFollowTask  = new IMSIFollowTask( task.getId(), task.getName(),
                    task.getParameter(), task.getResult(), task.getResultExt(), task.getIsFinish(),
                    taskProcess, task.getRemark(), task.getCreateTime(), task.getFinishTime(),
                    task.getCreatorId(), task.getDeleteFlag(), jsonObject.getString("targetIMSI"),
                    jsonObject.getString("startTime"), jsonObject.getString("endTime"));

            imsiFollowTasks.add( imsiFollowTask );

        }

        return imsiFollowTasks;
    }

    public IMSIFollowTask getIMSIFollowTaskTaskById( Long taskId ) {

        Task task  = taskMapper.getTaskById(taskId);
        JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;

        IMSIFollowTask  imsiFollowTask  = new IMSIFollowTask( task.getId(), task.getName(),
                task.getParameter(), task.getResult(), task.getResultExt(), task.getIsFinish(),
                0, task.getRemark(), task.getCreateTime(), task.getFinishTime(),
                task.getCreatorId(), task.getDeleteFlag(), jsonObject.getString("targetIMSI"),
                jsonObject.getString("startTime"), jsonObject.getString("endTime"));

        return imsiFollowTask;

    }

    public  JSONObject getIMSIFollowReportData( Long taskId ){

        JSONObject taskObject = null;
        IMSIFollowTask imsiFollowTask = getIMSIFollowTaskTaskById( taskId );

        String reportPath =  imsiFollowTask.getResultExt();

        try {
            File file=new File(reportPath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "UTF8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String reportTxt = "";
                while((lineTxt = bufferedReader.readLine()) != null){
                    reportTxt = reportTxt + lineTxt;
                }
                read.close();

                taskObject = JSONObject.parseObject( reportTxt );

            }

        } catch (Exception e) {

            logger.error("读取文件内容出错");
            e.printStackTrace();
        }


        return  taskObject;
    }

    //=========================================================
    //常驻人口外来人口
    //
    public List<Site> getSitesByAreaId( Long areaId ) {
        return  analysisService.getSitesByAreaId( areaId);
    }

    public List<ResidentPeopleTask> getResidentPeopleTasksByUserId( Long userId ) {

        List<ResidentPeopleTask>  residentPeopleTaskTasks = new LinkedList<>();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("type",  ResidentPeopleTask.taskType );
        params.put("creatorId",  userId );

        List<Task>  tasks = taskMapper.getTasks(params);
        for ( Task task: tasks ) {

            JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;

            float taskProcess = 0;
            if( 1 == task.getIsFinish() ) {
                taskProcess = 100;
            } else {
                if( analysisService.getCurrentTaskId().equals(task.getId()) ){
                    taskProcess = analysisService.getProgressNowValue();
                }
            }

            ResidentPeopleTask residentPeopleTask = new ResidentPeopleTask( task.getId(), task.getName(),
                    task.getParameter(), task.getResult(), task.getResultExt(), task.getIsFinish(),
                    taskProcess, task.getRemark(), task.getCreateTime(), task.getFinishTime(),
                    task.getCreatorId(), task.getDeleteFlag(), jsonObject.getLong("areaId"),
                    jsonObject.getString("areaName"), jsonObject.getString("startTime"),
                    jsonObject.getString("endTime"));

            residentPeopleTaskTasks.add( residentPeopleTask );

        }

        return residentPeopleTaskTasks;
    }

    public ResidentPeopleTask getResidentPeopleTaskTaskById( Long taskId ) {

        Task task  = taskMapper.getTaskById(taskId);
        JSONObject jsonObject = JSONObject.parseObject(  task.getParameter() ) ;

        ResidentPeopleTask residentPeopleTask = new ResidentPeopleTask( task.getId(), task.getName(),
                task.getParameter(), task.getResult(), task.getResultExt(), task.getIsFinish(),
                0, task.getRemark(), task.getCreateTime(), task.getFinishTime(),
                task.getCreatorId(), task.getDeleteFlag(), jsonObject.getLong("areaId"),
                jsonObject.getString("areaName"), jsonObject.getString("startTime"),
                jsonObject.getString("endTime"));

        return residentPeopleTask;

    }

    public  JSONObject getResidentPeopleReportData( Long taskId ){

        JSONObject taskObject = null;
        ResidentPeopleTask residentPeopleTask = getResidentPeopleTaskTaskById( taskId );

        String reportPath =  residentPeopleTask.getResultExt();
        logger.info(" ======reportPath:===== " + reportPath );
        try {
            File file=new File(reportPath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "UTF8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String reportTxt = "";
                while((lineTxt = bufferedReader.readLine()) != null){
                    reportTxt = reportTxt + lineTxt;
                }
                read.close();

                taskObject = JSONObject.parseObject( reportTxt );

            }

        } catch (Exception e) {

            logger.error("读取文件内容出错");
            e.printStackTrace();
        }


        return  taskObject;
    }
    
    public boolean checkTaskByTaskName(String taskName,int taskType)
    {
    	 Map<String,Object> params = new HashMap<String, Object>();
         params.put("name",  taskName );
         params.put("type",  taskType );

         List<Task>  tasks = taskMapper.getTasks(params);
    	 if (tasks.size() == 0)
    	 {
    		 return true;
    	 }
    	 
    	 return false;
    }

}
