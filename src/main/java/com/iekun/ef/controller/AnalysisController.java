package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.model.*;
import com.iekun.ef.service.TaskService;
import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.SystemParaService;
import com.iekun.ef.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by jiangqi.yang  on 2016/10/26.
 */

@Controller
@RequestMapping("/analysis")
public class AnalysisController {
    private static Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
	private ResourceService resourceServ;
	
	@Autowired
	private UserService userService;

    @Autowired
    private TaskService  taskService;

    @Autowired
    private ComInfoUtil comInfoUtil;
    
    @Autowired
    private SystemParaService sysParaServ;
	
    @RequestMapping("/imsiStatistics")
    public String imsiStatistics( Map<String, Object> model ) {

    	long roleId = SigletonBean.getRoleId();

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        
        return "business/imsiStatistics";
    }

    @RequestMapping("/dataCollide")
    public String dataCollide( Map<String, Object> model ) {
    	long roleId = SigletonBean.getRoleId();

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );
    	//// TODO: 2016/10/28
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "business/dataCollide";
    }

    @RequestMapping("/residentPeople")
    public String residentPeople( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );
        
    	//// TODO: 2016/10/28
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "business/residentPeople";
    }

    @RequestMapping("/imsiFollow")
    public String imsiFollow( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/10/28
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "business/imsiFollow";
    }

    @RequestMapping("/suspectTrail")
    public String suspectTrail( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/10/28
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "business/suspectTrail";
    }

    /**
     * 上号统计：获取任务列表
     * @return
     */
    @RequestMapping("/imsiStatistics/getTasks")
    @ResponseBody
    public JSONObject getIMSIStatisticsTasks() {

        JSONObject jsonObject = new JSONObject();
        JSONArray dataObject = new JSONArray();
        List<StatisticsTask>  tasks;
        if (SigletonBean.getRoleId() == 1)
        {
        	/*系统管理员能看到所有任务 */
        	tasks = taskService.getIMSIStatisticsTasksByUserId( null);
        }
        else
        {
        	tasks = taskService.getIMSIStatisticsTasksByUserId( SigletonBean.getUserId() );
        }
        for ( StatisticsTask task : tasks ) {

            JSONObject taskObject = new JSONObject();

            taskObject.put("id",  task.getId());
            taskObject.put("name", task.getName());
            taskObject.put("taskProcess", task.getNowValue() );
            taskObject.put("type", task.getStatisticType());
            taskObject.put("unit", task.getStatisticUnit() );
            taskObject.put("rangeTime",  task.getStartTime() + " - " + task.getEndTime() );
            taskObject.put("createTime", TimeUtils.convertDateNoSepToSep( task.getCreateTime() ) );
            taskObject.put("items", task.getStatisticItems() );

            dataObject.add(taskObject);
        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    /**
     * 上号统计：创建任务
     * @param taskName
     * @param type
     * @param unit
     * @param items
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/imsiStatistics/createTask")
    @ResponseBody
    public JSONObject createImsiStatisticsTask(
            @RequestParam(value = "taskName", required = true ) String taskName,
            @RequestParam(value = "type", required = true ) Integer type,
            @RequestParam(value = "unit", required = true ) Integer unit,
            @RequestParam(value = "items", required = true ) String items,
            @RequestParam(value = "startDate", required = true ) String startDate,
            @RequestParam(value = "endDate", required = true ) String endDate
    ) {
        JSONObject jsonObject = new JSONObject();

        if( items.isEmpty() ) {
            jsonObject.put("status", false);
            jsonObject.put("message", "统计项目不能为空");
            return jsonObject;
        }
        /*统计分析 task 类型为0。同一类型的task名不能重复*/
       /* boolean paramVaild = taskService.checkTaskByTaskName(taskName, 0);
        if (paramVaild == false)
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "系统内已创建相同名字的任务，请更换名字!");
            return jsonObject;
        }*/

        JSONArray statisticItems;
        try{
            statisticItems =  JSONArray.parseArray( items );
        } catch ( Exception e ) {
            jsonObject.put("status", false);
            jsonObject.put("message", "解析统计项目参数错误");
            return jsonObject;
        }

        JSONObject paramObject = new JSONObject();
        paramObject.put("type", type);
        paramObject.put("unit", unit);
        paramObject.put("startDate", startDate.replace("/", "-"));
        paramObject.put("endDate", endDate.replace("/", "-"));
        paramObject.put("items", statisticItems );

        String parameter =  paramObject.toJSONString();
        String createTime = sdf.format(new Date());

        StatisticsTask statisticsTask = new StatisticsTask( taskName, parameter, SigletonBean.getUserId(), createTime,
                type, unit, statisticItems, startDate, endDate );

        int result = taskService.createTask( statisticsTask );
        if( 0 == result ){
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else  {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }

        return jsonObject;
    }

    /**
     * 上号统计：任务详情
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping("/imsiStatistics/report/{taskId}")
    public String imsiStatisticsReport(
            @PathVariable("taskId") Long taskId,
            Map<String, Object> model
    ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        StatisticsTask task = taskService.getStatisticsTaskById( taskId );

        Map<String, Object> statistics = new HashMap();
        statistics.put("id", task.getId());
        statistics.put("name", task.getName());
        statistics.put("rangeTime", task.getStartTime() + " - " + task.getEndTime());
        if( 1 == task.getStatisticType() ) {
            statistics.put("type", "站点");
        } else if( 2 == task.getStatisticType() ) {
            statistics.put("type", "设备");
        } else {
            statistics.put("type", "运营商");
        }

        if( 1 == task.getStatisticUnit() ) {
            statistics.put("unit", "天");
        } else if( 2 == task.getStatisticUnit() ) {
            statistics.put("unit", "小时");
        } else {
            statistics.put("unit", "分钟");
        }

        JSONArray itemsArray =  task.getStatisticItems();
        List statisticItems = new ArrayList();
        for ( int i=0; i < itemsArray.size(); i++){
            JSONObject itemJson = (JSONObject)itemsArray.get(i);
            Map<String, Object> item = new HashMap();
            item.put("name", itemJson.getString("name") );
            statisticItems.add(item );
        }
        statistics.put("items", statisticItems );

        model.put("statistics", statistics );
        return "business/imsiStatisticsReport";
    }

    /**
     * 上号统计：获取任务报告数据
     * @param taskId
     * @return
     */
    @RequestMapping("/imsiStatistics/report/{taskId}/getReportData")
    @ResponseBody
    public JSONObject getImsiStatisticsReportData(@PathVariable("taskId") Long taskId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject = taskService.getImsiStatisticsReportData( taskId );
        return jsonObject;
    }

    /**
     * 嫌疑人运动轨迹：获取任务列表
     * @return
     */
    @RequestMapping("/suspectTrail/getTasks")
    @ResponseBody
    public JSONObject getSuspectTrailTasks() {

        JSONObject jsonObject = new JSONObject();
        JSONArray dataObject = new JSONArray();
        List<SuspectTrailTask> tasks;
        if (SigletonBean.getRoleId() == 1)
        {
        	/*系统管理员能看到所有任务 */
        	tasks = taskService.getSuspectTrailTasksByUserId( null);
        }
        else
        {
        	tasks = taskService.getSuspectTrailTasksByUserId( SigletonBean.getUserId() );
        }
        
        for ( SuspectTrailTask task : tasks ) {

            JSONObject taskObject = new JSONObject();

            taskObject.put("id",  task.getId());
            taskObject.put("name", task.getName());
            taskObject.put("imsi", task.getTargetIMSI());
            taskObject.put("otherImsi", task.getOtherIMSI());
            taskObject.put("taskProcess", task.getNowValue() );
            taskObject.put("rangeTime",  task.getStartTime() + " - " + task.getEndTime() );
            taskObject.put("createTime", TimeUtils.convertDateNoSepToSep( task.getCreateTime() ) );

            dataObject.add(taskObject);
        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return  jsonObject;
    }

    /**
     * 嫌疑人运动轨迹：创建任务
     * @param taskName
     * @param targetIMSI
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/suspectTrail/createTask")
    @ResponseBody
    public JSONObject createSuspectTrailTask(
            @RequestParam(value = "taskName", required = true ) String taskName,
            @RequestParam(value = "targetIMSI[]", required = true ) String[] targetIMSI,
            @RequestParam(value = "startDate", required = true ) String startDate,
            @RequestParam(value = "endDate", required = true ) String endDate
    ) {
        JSONObject jsonObject = new JSONObject();

        boolean paramVaild = false;
        for ( int i =0; i<targetIMSI.length ; i++) {
            paramVaild = paramVaild || (!targetIMSI[i].trim().isEmpty());
        }

        if( !paramVaild ) {
            jsonObject.put("status", false);
            jsonObject.put("message", "目标IMSI不能为空");
            return jsonObject;
        }
        
        /*嫌疑人轨迹分析 task 类型为1。同一类型的task名不能重复*/
       /* paramVaild = taskService.checkTaskByTaskName(taskName, 1);
        if (paramVaild == false)
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "系统内已创建相同名字的任务，请更换名字!");
            return jsonObject;
        }
*/
        JSONObject paramObject = new JSONObject();

        String masterIMSI = "";
        ArrayList otherIMSI = new ArrayList();
        for ( int i =0; i<targetIMSI.length ; i++) {
            if( masterIMSI.isEmpty() ) {
                if(!targetIMSI[i].trim().isEmpty()){
                    masterIMSI = targetIMSI[i];
                }
            } else  {
                if(!targetIMSI[i].trim().isEmpty()){
                    otherIMSI.add( targetIMSI[i] );
                }
            }
        }

        paramObject.put("targetIMSI", masterIMSI);
        paramObject.put("otherIMSI", otherIMSI);
        paramObject.put("startDate", startDate.replace("/", "-"));
        paramObject.put("endDate", endDate.replace("/", "-"));

        String parameter =  paramObject.toJSONString();
        String createTime = sdf.format(new Date());

        SuspectTrailTask suspectTrailTask = new SuspectTrailTask( taskName, parameter, SigletonBean.getUserId(),
                createTime, masterIMSI, otherIMSI, startDate, endDate );

        int result = taskService.createTask( suspectTrailTask );
        if( 0 == result ){
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else  {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }

        return jsonObject;
    }

    /**
     * 嫌疑人运动轨迹：任务详情
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping("/suspectTrail/report/{taskId}")
    public String suspectTrailReport(
            @PathVariable("taskId") Long taskId,
            Map<String, Object> model
    ) {
        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());


        SuspectTrailTask task = taskService.getSuspectTrailTaskById( taskId );

        Map<String, Object> suspectTrailTaskInfo = new HashMap();
        Map<String, Object> centerPoint = new HashMap();

        List imsiList = new ArrayList();
        Map<String, Object> targetImsiMap = new HashMap();
        targetImsiMap.put("imsi", task.getTargetIMSI() );
        imsiList.add(targetImsiMap);
        ArrayList otherImsiList = task.getOtherIMSI();
        for ( int i=0; i<otherImsiList.size(); i++){
            String imsi = (String)otherImsiList.get(i);
            Map<String, Object> imsiMap = new HashMap();
            imsiMap.put("imsi", imsi );
            imsiList.add(imsiMap);
        }

        suspectTrailTaskInfo.put("taskName", task.getName() );
        suspectTrailTaskInfo.put("taskId", task.getId() );
        suspectTrailTaskInfo.put("rangeTime", task.getStartTime() + " - " + task.getEndTime() );
        suspectTrailTaskInfo.put("imsiList", imsiList );

        // 系统配置
        centerPoint.put("lng",  Float.parseFloat(sysParaServ.getSysPara("mapCenterLng")) );
        centerPoint.put("lat",  Float.parseFloat(sysParaServ.getSysPara("mapCenterLat")) );
        suspectTrailTaskInfo.put("centerPoint", centerPoint );
        suspectTrailTaskInfo.put("zoom", Integer.parseInt(sysParaServ.getSysPara("mapZoom")) );
        suspectTrailTaskInfo.put("mapOnline",  sysParaServ.getSysPara("mapDataSwitch") );


        model.put("taskReport", suspectTrailTaskInfo);

        return "business/suspectTrailReport";
    }

    /**
     * 嫌疑人运动轨迹：获取任务统计报告数据
     * @param taskId
     * @return
     */
    @RequestMapping("/suspectTrail/report/{taskId}/getReportData")
    @ResponseBody
    public JSONObject getSuspectTrailReportData(@PathVariable("taskId") Long taskId){
        JSONObject jsonObject = new JSONObject();
        jsonObject = taskService.getSuspectTrailReportData( taskId);
        return jsonObject;
    }

    /**
     * 数据碰撞分析：获取任务列表
     * @return
     */
    @RequestMapping("/dataCollide/getTasks")
    @ResponseBody
    public JSONObject getDataCollideTasks() {

        JSONObject jsonObject = new JSONObject();
        JSONArray dataObject = new JSONArray();
        
        List<DataCollideTask> tasks;
        if (SigletonBean.getRoleId() == 1)
        {
        	/*系统管理员能看到所有任务 */
        	tasks = taskService.getDataCollideTasksByUserId( null );
        }
        else
        {
        	tasks = taskService.getDataCollideTasksByUserId( SigletonBean.getUserId() );
        }
        
        
        for ( DataCollideTask task : tasks ) {
            JSONObject jsonObjTask = new JSONObject();

            jsonObjTask.put("id",  task.getId());
            jsonObjTask.put("name", task.getName());
            jsonObjTask.put("createTime", TimeUtils.convertDateNoSepToSep( task.getCreateTime() ) );
            jsonObjTask.put("taskProcess", task.getNowValue() );
            jsonObjTask.put("spaceTime", task.getSpaceTimeItems() );

            dataObject.add(jsonObjTask);

        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    /**
     * 数据碰撞分析：创建任务
     * @param taskName
     * @param spaceTime
     * @return
     */
    @RequestMapping("/dataCollide/createTask")
    @ResponseBody
    public JSONObject createDataCollideTask(
            @RequestParam(value = "taskName", required = true ) String taskName,
            @RequestParam(value = "spaceTime", required = true ) String spaceTime ){

    	JSONObject jsonObject = new JSONObject();

    	/*统计分析 task 类型为2。同一类型的task名不能重复*/
       /* boolean paramVaild = taskService.checkTaskByTaskName(taskName,2);
        if (paramVaild == false)
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "该任务名已存在系统内已创建相同名字的任务，请更换名字!");
            return jsonObject;
        }*/

        try{

            JSONArray jsonSpaceTimes = JSONArray.parseArray( spaceTime );
            if( jsonSpaceTimes.size() >= 2 ) {

                JSONObject paramObject = new JSONObject();
                paramObject.put("items", jsonSpaceTimes );

                String parameter =  paramObject.toJSONString();
                String createTime = sdf.format(new Date());

                DataCollideTask dataCollideTask = new DataCollideTask( taskName, parameter,SigletonBean.getUserId(),
                        createTime, jsonSpaceTimes );

                int result = taskService.createTask( dataCollideTask );
                if( 0 == result ){
                    jsonObject.put("status", true);
                    jsonObject.put("message", "成功");
                } else  {
                    jsonObject.put("status", false);
                    jsonObject.put("message", "创建任务失败");
                }

            } else {
                jsonObject.put("status", false);
                jsonObject.put("message", "创建任务失败,时空条件过少");
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            jsonObject.put("status", false);
            jsonObject.put("message", "创建任务失败");
        }

        return  jsonObject;
    }

    /**
     * 数据碰撞分析：任务详情
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping("/dataCollide/report/{taskId}")
    public String dataCollideReport(@PathVariable("taskId") Long taskId, Map<String, Object> model) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        DataCollideTask dataCollideTask = taskService.getDataCollideTaskById( taskId );

        Map<String, Object>  dataCollideTaskInfo = new HashMap();

        dataCollideTaskInfo.put("taskName", dataCollideTask.getName());
        dataCollideTaskInfo.put("taskId", dataCollideTask.getId() );
        dataCollideTaskInfo.put("createTime", TimeUtils.convertDateNoSepToSep( dataCollideTask.getCreateTime() ) );
        List spaceTimeList = new ArrayList();
        JSONArray spaceTimeItems = dataCollideTask.getSpaceTimeItems();
        for (int i=0; i< spaceTimeItems.size(); i++) {
            Map<String, Object> spaceTime = new HashMap();
            JSONObject spaceTimeJSObj = spaceTimeItems.getJSONObject( i );
            spaceTime.put("startTime" , spaceTimeJSObj.getString("startTime") );
            spaceTime.put("endTime" , spaceTimeJSObj.getString("endTime") );
            spaceTime.put("rangeTime" , spaceTimeJSObj.getString("rangeTime") );
            spaceTime.put("siteSN" , spaceTimeJSObj.getString("siteSN") );
            spaceTime.put("siteName" , spaceTimeJSObj.getString("siteName") );
            spaceTimeList.add( spaceTime );
        }
        dataCollideTaskInfo.put("spaceTime", spaceTimeList);

        model.put("taskReport", dataCollideTaskInfo);

        return "business/dataCollideReport";
    }

    /**
     * 数据碰撞分析：获取任务统计数据
     * @param taskId
     * @param draw
     * @param length
     * @param start
     * @return
     */
    @RequestMapping("/dataCollide/report/{taskId}/getReportData")
    @ResponseBody
    public JSONObject getDataCollideReportData(
            @PathVariable("taskId") Long taskId,
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "start", required = false ) Integer start
    ){
        JSONObject dataObject = new JSONObject();
        JSONArray  recordArray = new JSONArray();

        JSONObject reportDataJSObj = taskService.getDataCollideReportData( taskId );
        if( null != reportDataJSObj ){

            JSONArray reportDataArray = reportDataJSObj.getJSONArray("data");
            int outCount = 0;
            for ( int i = start; i< reportDataArray.size() ; i++ ){
                recordArray.add(  reportDataArray.getJSONObject(i) );
                outCount++;
                if( outCount > length ) {
                    break;
                }
            }

            dataObject.put("draw", draw);
            dataObject.put("recordsTotal", reportDataJSObj.getInteger("total"));
            dataObject.put("recordsFiltered", reportDataJSObj.getInteger("total") );
            dataObject.put("data", recordArray );

        } else {
            dataObject.put("draw", draw);
            dataObject.put("recordsTotal", 0);
            dataObject.put("recordsFiltered", 0);
            dataObject.put("data", recordArray );
        }

        return dataObject;
    }

    /**
     * IMSI伴随分析：获取任务列表
     * @return
     */
    @RequestMapping("/imsiFollow/getTasks")
    @ResponseBody
    public JSONObject getIMSIFollowTasks() {

//        JSONObject jsonObject = com.iekun.ef.test.ui.IMSIFollowAnalysis.getTasks();

        JSONObject jsonObject = new JSONObject();
        JSONArray dataObject = new JSONArray();
        List<IMSIFollowTask> tasks;
        if (SigletonBean.getRoleId() == 1)
        {
        	/*系统管理员能看到所有任务 */
        	tasks = taskService.getIMSIFollowTasksByUserId( null );
        }
        else
        {
        	tasks = taskService.getIMSIFollowTasksByUserId( SigletonBean.getUserId() );
        }
        
        for ( IMSIFollowTask task : tasks ) {
            JSONObject jsonObjTask = new JSONObject();

            jsonObjTask.put("id",  task.getId());
            jsonObjTask.put("name", task.getName());
            jsonObjTask.put("createTime", TimeUtils.convertDateNoSepToSep( task.getCreateTime() ) );
            jsonObjTask.put("taskProcess", task.getNowValue() );
            jsonObjTask.put("targetIMSI", task.getTargetIMSI());
            jsonObjTask.put("rangeTime",  task.getStartTime() + " - " + task.getEndTime());

            dataObject.add(jsonObjTask);

        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);


        return jsonObject;
    }

    /**
     * IMSI伴随分析：创建任务
     * @param taskName
     * @param targetIMSI
     * @param topTime
     * @param bottomTime
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/imsiFollow/createTask")
    @ResponseBody
    public JSONObject createIMSIFollowTask(
            @RequestParam(value = "taskName", required = true ) String taskName,
            @RequestParam(value = "targetIMSI", required = true ) String targetIMSI,
            @RequestParam(value = "topTime", required = true ) String topTime,
            @RequestParam(value = "bottomTime", required = true ) String bottomTime,
            @RequestParam(value = "startDate", required = true ) String startDate,
            @RequestParam(value = "endDate", required = true ) String endDate
    ) {
        JSONObject jsonObject = new JSONObject();

        /*伴随分析 task 类型为4。同一类型的task名不能重复*/
       /* boolean paramVaild = taskService.checkTaskByTaskName(taskName, 4);
        if (paramVaild == false)
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "系统内已创建相同名字的任务，请更换名字!");
            return jsonObject;
        }*/

        
        try{

            JSONObject paramObject = new JSONObject();
            paramObject.put("targetIMSI",targetIMSI );
            paramObject.put("startTime",startDate.replace("/", "-") );
            paramObject.put("endTime",endDate.replace("/", "-") );
            paramObject.put("topTime",topTime);
            paramObject.put("bottomTime",bottomTime);

            String parameter =  paramObject.toJSONString();
            String createTime = sdf.format(new Date());

            IMSIFollowTask imsiFollowTask = new IMSIFollowTask( taskName, parameter, SigletonBean.getUserId(),
                    createTime, targetIMSI, startDate.replace("/", "-"), endDate.replace("/", "-") );

            int result = taskService.createTask( imsiFollowTask );
            if( 0 == result ){
                jsonObject.put("status", true);
                jsonObject.put("message", "成功");
            } else  {
                jsonObject.put("status", false);
                jsonObject.put("message", "失败");
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            jsonObject.put("status", false);
            jsonObject.put("message", "创建任务失败");
        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        return  jsonObject;
    }

    /**
     * IMSI伴随分析：任务报告详情
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping("/imsiFollow/report/{taskId}")
    public String imsiFollowReport(
            @PathVariable("taskId") Long taskId,
            Map<String, Object> model
    ) {
        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        IMSIFollowTask imsiFollowTask = taskService.getIMSIFollowTaskTaskById( taskId );

        Map<String, Object> imsiFollowTaskInfo = new HashMap();
        imsiFollowTaskInfo.put("taskId",  imsiFollowTask.getId() );
        imsiFollowTaskInfo.put("taskName", imsiFollowTask.getName());
        imsiFollowTaskInfo.put("createTime", TimeUtils.convertDateNoSepToSep( imsiFollowTask.getCreateTime() ) );
        imsiFollowTaskInfo.put("targetIMSI", imsiFollowTask.getTargetIMSI() );
        imsiFollowTaskInfo.put("rangeTime",  imsiFollowTask.getStartTime() + " - "  + imsiFollowTask.getEndTime() );

        model.put("taskReport", imsiFollowTaskInfo);

        return "business/imsiFollowReport";
    }

    /**
     * IMSI伴随分析：获取任务统计报告数据
     * @param taskId
     * @param draw
     * @param length
     * @param start
     * @return
     */
    @RequestMapping("/imsiFollow/report/{taskId}/getReportData")
    @ResponseBody
    public JSONObject getImsiFollowReportData(
            @PathVariable("taskId") Long taskId,
            @RequestParam(value = "draw", required = true ) Integer draw,
            @RequestParam(value = "length", required = true ) Integer length,
            @RequestParam(value = "start", required = true ) Integer start
    ){
        JSONObject dataObject = new JSONObject();
        JSONArray  recordArray = new JSONArray();

        JSONObject reportDataJSObj = taskService.getIMSIFollowReportData( taskId );
        if( null != reportDataJSObj ){

            JSONArray reportDataArray = reportDataJSObj.getJSONArray("data");
            int outCount = 0;
            for ( int i = start; i< reportDataArray.size() ; i++ ){
                recordArray.add(  reportDataArray.getJSONObject(i) );
                outCount++;
                if( outCount > length ) {
                    break;
                }
            }

            dataObject.put("draw", draw);
            dataObject.put("recordsTotal", reportDataJSObj.getInteger("total"));
            dataObject.put("recordsFiltered", reportDataJSObj.getInteger("total") );
            dataObject.put("data", recordArray );

        } else {
            dataObject.put("draw", draw);
            dataObject.put("recordsTotal", 0);
            dataObject.put("recordsFiltered", 0);
            dataObject.put("data", recordArray );
        }

        return dataObject;
    }

    /**
     * 常驻人口外来人口分析：获取任务列表
     * @return
     */
    @RequestMapping("/residentPeople/getTasks")
    @ResponseBody
    public JSONObject getResidentPeopleTasks() {
//        JSONObject jsonObject =  com.iekun.ef.test.ui.ResidentPeopleAnalysis.getTasks();
        JSONObject jsonObject = new JSONObject();
        JSONArray dataObject = new JSONArray();
        List<ResidentPeopleTask> tasks;
        if (SigletonBean.getRoleId() == 1)
        {
        	/*系统管理员能看到所有任务 */
        	tasks = taskService.getResidentPeopleTasksByUserId( null);
        }
        else
        {
        	tasks = taskService.getResidentPeopleTasksByUserId( SigletonBean.getUserId() );
        }
        
        for ( ResidentPeopleTask task : tasks ) {
            JSONObject jsonObjTask = new JSONObject();

            jsonObjTask.put("id",  task.getId());
            jsonObjTask.put("name", task.getName());
            jsonObjTask.put("createTime", TimeUtils.convertDateNoSepToSep( task.getCreateTime() ) );
            jsonObjTask.put("taskProcess", task.getNowValue() );
            jsonObjTask.put("areaName", task.getAreaName());
            jsonObjTask.put("areaId", task.getAreaId());
            jsonObjTask.put("rangeTime",  task.getStartTime() + " - " + task.getEndTime());

            dataObject.add(jsonObjTask);

        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    /**
     * 常驻人口外来人口分析：创建任务
     * @param taskName
     * @param areaId
     * @param areaName
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/residentPeople/createTask")
    @ResponseBody
    public JSONObject createResidentPeopleTask(
            @RequestParam(value = "taskName", required = true ) String taskName,
            @RequestParam(value = "areaId", required = true ) Long areaId,
            @RequestParam(value = "areaName", required = true ) String areaName,
            @RequestParam(value = "startTime", required = true ) String startTime,
            @RequestParam(value = "endTime", required = true ) String endTime
    ) {
        JSONObject jsonObject = new JSONObject();

     /*   *//*常驻人口分析 task 类型为3。同一类型的task名不能重复*//*
        boolean paramVaild = taskService.checkTaskByTaskName(taskName, 3);
        if (paramVaild == false)
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "系统内已创建相同名字的任务，请更换名字!");
            return jsonObject;
        }*/

        
        try{

            List<Site> sites = taskService.getSitesByAreaId(areaId);
            if( null == sites || sites.size()==0 ) {
                jsonObject.put("status", false);
                jsonObject.put("message", "区域没有部署站点");
                return jsonObject;
            }

//            Date startDate = TimeUtils.timeFormatter.parse(startTime);
//            Date endDate = TimeUtils.timeFormatter.parse(endTime);
//            long diff = endDate.getTime() - startDate.getTime();
//            long day = diff/(24*60*60*1000);
//            if( day < 180 ) {
//                jsonObject.put("status", false);
//                jsonObject.put("message", "分析时间太短，必须半年以上.");
//                return jsonObject;
//            }

            JSONObject paramObject = new JSONObject();
            paramObject.put("areaId",areaId );
            paramObject.put("areaName", areaName );
            paramObject.put("startTime", startTime );
            paramObject.put("endTime",endTime );

            String parameter =  paramObject.toJSONString();
            String createTime = sdf.format(new Date());

            ResidentPeopleTask residentPeopleTask = new ResidentPeopleTask( taskName, parameter,
                    SigletonBean.getUserId(), createTime, areaId, areaName, startTime, endTime);

            int result = taskService.createTask( residentPeopleTask );
            if( 0 == result ){
                jsonObject.put("status", true);
                jsonObject.put("message", "成功");
            } else  {
                jsonObject.put("status", false);
                jsonObject.put("message", "失败");
            }

        } catch ( Exception e ) {
            e.printStackTrace();
            jsonObject.put("status", false);
            jsonObject.put("message", "创建任务失败");
        }

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        return  jsonObject;
    }

    /**
     * 常驻人口外来人口分析：获取任务详情页
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping("/residentPeople/report/{taskId}")
    public String residentPeopleReport(
            @PathVariable("taskId") Long taskId,
            Map<String, Object> model
    ) {
        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

//        com.iekun.ef.test.ui.ResidentPeopleAnalysis.getTaskInfo( model );

        ResidentPeopleTask residentPeopleTask = taskService.getResidentPeopleTaskTaskById( taskId );

        Map<String, Object> residentPeopleTaskInfo = new HashMap();

        residentPeopleTaskInfo.put("taskName", residentPeopleTask.getName());
        residentPeopleTaskInfo.put("taskId", residentPeopleTask.getId());
        residentPeopleTaskInfo.put("areaName",  residentPeopleTask.getAreaName());
        residentPeopleTaskInfo.put("rangeTime",  residentPeopleTask.getStartTime() + " - "  + residentPeopleTask.getEndTime() );
        residentPeopleTaskInfo.put("createTime",TimeUtils.convertDateNoSepToSep( residentPeopleTask.getCreateTime() ) );

        model.put("taskReport", residentPeopleTaskInfo);
       
        return "business/residentPeopleReport";
    }

    /**
     * 常驻人口外来人口分析：获取任务详情数据
     * @param taskId
     * @param dataType
     * @param draw
     * @param length
     * @param start
     * @return
     */
    @RequestMapping("/residentPeople/report/{taskId}/getReportData")
    @ResponseBody
    public JSONObject getResidentPeopleReportData(
            @PathVariable("taskId") Long taskId,
            @RequestParam("dataType") String dataType,
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "start", required = false ) Integer start
    ){
        JSONObject dataObject = new JSONObject();
        JSONArray  recordArray = new JSONArray();

        JSONObject reportDataJSObj = taskService.getResidentPeopleReportData( taskId );
        if( null != reportDataJSObj ){

            JSONObject reportData = reportDataJSObj.getJSONObject("data");

            if( dataType.equals("total")) {

                JSONObject  totalData = new JSONObject();
                totalData.put("total", reportDataJSObj.getLong("total") );
                totalData.put("fixed", reportDataJSObj.getLong("fixed") );
                totalData.put("period", reportDataJSObj.getLong("period") );

                dataObject.put("status", true);
                dataObject.put("message", "成功");
                dataObject.put("data", totalData);

            } else  {
                JSONArray imsiData = null;
                long recordsTotal = 0;
                if( dataType.equals("fixed")){
                    imsiData = reportData.getJSONArray("fixed");
                    recordsTotal =  reportDataJSObj.getLong("fixed");
                } else if( dataType.equals("period")){
                    imsiData = reportData.getJSONArray("period");
                    recordsTotal =  reportDataJSObj.getLong("period");
                }

                int outCount = 0;
                for ( int i = start; i< imsiData.size() ; i++ ){
                    recordArray.add(  imsiData.getJSONObject(i) );
                    outCount++;
                    if( outCount > length ) {
                        break;
                    }
                }

                dataObject.put("draw", draw);
                dataObject.put("recordsTotal", recordsTotal);
                dataObject.put("recordsFiltered", recordsTotal );
                dataObject.put("data", recordArray );

            }

        } else {

            if( dataType.equals("total")) {

                JSONObject  totalData = new JSONObject();
                totalData.put("total", 0 );
                totalData.put("fixed", 0 );
                totalData.put("period", 0 );

                dataObject.put("status", true);
                dataObject.put("message", "成功");
                dataObject.put("data", totalData);

            }  else {
                dataObject.put("draw", draw);
                dataObject.put("recordsTotal", 0);
                dataObject.put("recordsFiltered", 0);
                dataObject.put("data", recordArray );
            }

        }

        return dataObject;

    }

    /**
     * 删除任务
     * @param taskId
     * @return
     */
    @RequestMapping("/deleteTask")
    @ResponseBody
    public JSONObject deleteTask(
            @RequestParam(value = "taskId", required = true ) Long taskId
    ) {
        JSONObject jsonObject = new JSONObject();
        Integer result = taskService.delTask(taskId );
        if( 0 == result ){
            jsonObject.put("status", true);
            jsonObject.put("message", "删除任务成功");
        } else  {
            jsonObject.put("status", false);
            jsonObject.put("message", "删除任务失败");
        }
        return jsonObject;
    }

    /**
     * 导出任务
     * @param taskId
     * @return
     */
    @RequestMapping("/exportTask")
    @ResponseBody
    public JSONObject exportTask(@RequestParam(value = "taskId", required = true ) Long taskId) {

        boolean status = false;
        JSONObject jsonObject = new JSONObject();
        try{
           jsonObject =  taskService.queryTxt(taskId );
           status = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;

    }

    /**
     * 下载zip文件
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportZip")
    public void exportZip(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String taskId = request.getParameter("formData");
        taskService.exportZip(taskId, response);
    }
}
