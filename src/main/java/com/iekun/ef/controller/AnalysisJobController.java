package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iekun.ef.dao.DataTablePager;
import com.iekun.ef.model.DataAnalysis;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.TreeNode;
import com.iekun.ef.model.UeInfo;
import com.iekun.ef.service.AnalysisJobService;
import com.iekun.ef.service.NewHistoryService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.M16Tools;
import com.iekun.ef.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/analysis")
@Controller
public class AnalysisJobController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceServ;

    @Autowired
    private ComInfoUtil comInfoUtil;

    @Autowired
    AnalysisJobService analysisJobService;

    @Autowired
    NewHistoryService newHistoryService;

    private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    @RequestMapping("/newDataCollide")
    public String dataCollide( Map<String, Object> model ) {
        long roleId = SigletonBean.getRoleId();
        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );
        //// TODO: 2016/10/28
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "business/newDataCollide";
    }

    @RequestMapping("/newImsiFollow")
    public String imsiFollow( Map<String, Object> model ) {
        long roleId = SigletonBean.getRoleId();
        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );
        //// TODO: 2016/10/28
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "business/newImsiFollow";
    }

    @RequestMapping("/queryZtree")
    @ResponseBody
    public  List<TreeNode> queryZtree(){
        //要返回的zTree
        List<TreeNode> list = new ArrayList<TreeNode>();
        //得到所有站点
        List<Site> siteList = newHistoryService.querySiteByUserId(SigletonBean.getUserId());
        List<Long>  province= new ArrayList<>();//省份
        List<Long> city = new ArrayList<>();//城市
        List<Long> towns = new ArrayList<>();//区域
        for(Site site: siteList){
            if(!province.contains(site.getProvince_id())){
                province.add(site.getProvince_id());
                list.add(new TreeNode(site.getProvince_id().toString(),"0",site.getProvince_name(),true,true,false));//省份
            }
            if(!city.contains(site.getCity_id())){
                city.add(site.getCity_id());
                list.add(new TreeNode(site.getCity_id().toString(),site.getProvince_id().toString(),site.getCity_name(),true,true,false));//城市
            }
            if(!towns.contains(site.getTown_id())){
                towns.add(site.getTown_id());
                list.add(new TreeNode(site.getTown_id().toString(),site.getCity_id().toString(),site.getTown_name(),true,true,false));//区域
            }
            list.add(new TreeNode(site.getSn().toString(),site.getTown_id().toString(),site.getName(),true,false,false));//站点
        }
        return list;
    }

    /**
     * 创建数据碰撞分析任务
     * */
    @RequestMapping("/creatorDataCollide")
    @ResponseBody
    public JSONObject creatorDataCollide(String taskName, String spaceTime, String mixCount, String remark) throws ParseException {
            JSONObject jsonObject = new JSONObject();
            //主键ID
            String uuid = UUID.randomUUID().toString().trim().replace("-", "");
            //类型 碰撞分析
            String type = "collisionAnalysis";
            //用户ID,姓名
            Long creatorId = SigletonBean.getUserId();
            String creatorName = SigletonBean.getUserName();
             //时空条件
            JSONArray jsonSpaceTimes = JSONArray.parseArray(spaceTime);
            JSONObject paramObject = new JSONObject();
            paramObject.put("items", jsonSpaceTimes);
            String parameter = paramObject.toJSONString();

            //filter
            JSONObject filter = new JSONObject();
            filter.put("jobId",uuid);
            filter.put("mixCount",mixCount);
            filter.put("dbUrl",url);
            filter.put("dbUser",username);
            filter.put("dbPwd",password);
            filter.put("dbDriver","com.mysql.jdbc.Driver");
            filter.put("partitionColumn","ID");

            //拿到其中一个时间条件
            JSONArray collisionLists = new JSONArray();
            List list = new ArrayList();
            //jsonSpaceTimes所有所选时空条件
            for(int x = 0;x < jsonSpaceTimes.size();x++){
                 JSONObject jsonObject1 = jsonSpaceTimes.getJSONObject(x);
                 String startTime = (String) jsonObject1.get("startTime");
                 String endTime = (String) jsonObject1.get("endTime");
                 String siteSN = (String) jsonObject1.get("siteSN");
                 Date start = sdf.parse(startTime.replace("/","-"));
                 Date end = sdf.parse(endTime.replace("/","-"));
                 int days = differentDays(start,end);
                 List<Map<String, String>> conditions = new ArrayList<>();
                 for(int day = 0;day <= days;day++){
                     Map<String, String> one = new HashMap<>();
                     if(day == 0){//第一天
                         String sta = startTime.replace("/","-");
                         String sta1 = startTime.substring(0,10).replace("/","-");//2017_10_12
                         String tableName = startTime.substring(0,10).replace("/","_");//2017_10_12
                         String last = endTime.substring(0,10).replace("/","-");
                         String last1 = endTime.replace("/","-");
                         //判断时空条件是否为同一天
                         if(sta1.equals(last)){
                             one.put("table","t_ue_info_" + tableName);
                             one.put("filter","CAPTURE_TIME >= '"+ sta +"' and CAPTURE_TIME <= '"+ last1 +"'and SITE_SN = '"+ siteSN +"'");
                             one.put("lowerBound","1");
                             one.put("upperBound","20000000");
                             one.put("fetchsize","5000");
                             one.put("numPartitions","1");
                         }else{
                             one.put("table","t_ue_info_" + tableName);
                             one.put("filter","CAPTURE_TIME >= '"+ sta +"' and CAPTURE_TIME <= '"+ sta1 +" 23:59:59'and SITE_SN = '"+ siteSN +"'");
                             one.put("lowerBound","1");
                             one.put("upperBound","20000000");
                             one.put("fetchsize","5000");
                             one.put("numPartitions","1");
                         }
                     }else if(day == days){//最后一天
                         String last = endTime.substring(0,10).replace("/","-");
                         String tableName = endTime.substring(0,10).replace("/","_");
                         String la = endTime.replace("/","-");
                         one.put("table","t_ue_info_" + tableName);
                         one.put("filter","CAPTURE_TIME >= '"+ last +" 00:00:00' and CAPTURE_TIME <= '"+ la +"'and SITE_SN = '"+ siteSN +"'");
                         one.put("lowerBound","1");
                         one.put("upperBound","20000000");
                         one.put("fetchsize","5000");
                         one.put("numPartitions","1");
                     }else{
                         Calendar calendar = new GregorianCalendar();
                         calendar.setTime(start);
                         calendar.add(Calendar.DATE,1);//把日期往后增加一天.正数往后推,负数往前推
                         start = calendar.getTime();
                         String name = sdf.format(start);//2017-12-02 00:00:00
                         String tableName = name.substring(0,10).replace("-","_");
                         String time = name.substring(0,10);
                         one.put("table","t_ue_info_" + tableName);
                         one.put("filter","CAPTURE_TIME >= '"+ time +" 00:00:00' and CAPTURE_TIME <= '"+ time +" 23:59:59'and SITE_SN = '"+ siteSN +"'");
                         one.put("lowerBound","1");
                         one.put("upperBound","20000000");
                         one.put("fetchsize","5000");
                         one.put("numPartitions","1");
                     }
                     conditions.add(one);
                 }
                 Map<String, List<Map<String, String>>> map = new HashMap<>();
                 map.put("conditions", conditions);
                 collisionLists.add(map);
            }
            filter.put("collisionLists",collisionLists);
            //创建数据碰撞分析任务对象
            DataAnalysis collisionAnalysis = new DataAnalysis();
            collisionAnalysis.setId(uuid);
            collisionAnalysis.setType(type);
            collisionAnalysis.setName(taskName);
            collisionAnalysis.setCreatorId(creatorId);
            collisionAnalysis.setCreatorName(creatorName);
            collisionAnalysis.setFilter(filter.toString());
            collisionAnalysis.setStatus("0");
            collisionAnalysis.setCreateTime(sdf.format(new Date()));
            collisionAnalysis.setRemark(remark);
            collisionAnalysis.setDeleteFlag(0);
            collisionAnalysis.setParameter(parameter);
            jsonObject =  analysisJobService.creatorJob(collisionAnalysis);
            return jsonObject;
    }

    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }

    /**
     * 查询数据碰撞分析详情
     * */
    @RequestMapping("/queryRowDataCollide")
    @ResponseBody
    public JSONObject queryBlacklist( HttpServletRequest request){
        String id = request.getParameter("formData");

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<Map> targetGroup = analysisJobService.queryRowDataCollide(id);
        PageInfo pg = new PageInfo(targetGroup);
        DataTablePager page = new DataTablePager();
        page.setDataResult(targetGroup);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        return (JSONObject)JSONObject.toJSON(page);

    }

    /**
     * 数据碰撞分析imsi号码对应的站点信息
     * */
    @RequestMapping("/showThisRowData")
    @ResponseBody
    public JSONObject showThisRowData(HttpServletRequest request){
        String RowData = request.getParameter("RowData");
        Map<String, String> params = (Map<String, String>)JSONObject.parse(RowData);
        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<UeInfo> targetGroup = analysisJobService.showThisRowData(params);
        PageInfo pg = new PageInfo(targetGroup);
        DataTablePager page = new DataTablePager();
        page.setDataResult(targetGroup);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);
        return (JSONObject)JSONObject.toJSON(page);
    }

    /**
     * 数据碰撞分析查询任务
     * */
    @RequestMapping("/queryDataCollide")
    @ResponseBody
    public JSONObject queryDataCollide(){
        JSONObject jsonObject = new JSONObject();
        JSONArray dataObject = new JSONArray();
        List<DataAnalysis> collisionAnalyses;
        collisionAnalyses = analysisJobService.queryDataCollide(SigletonBean.getUserId());
        for ( DataAnalysis collisionAnalyse : collisionAnalyses ) {
            JSONObject jsonObjTask = new JSONObject();
            jsonObjTask.put("id",  collisionAnalyse.getId());
            jsonObjTask.put("name", collisionAnalyse.getName());
            jsonObjTask.put("createTime", collisionAnalyse.getCreateTime() );
            jsonObjTask.put("spaceTime", collisionAnalyse.getSpaceTimeItems() );
            jsonObjTask.put("remark",collisionAnalyse.getRemark());
            jsonObjTask.put("taskProcess",collisionAnalyse.getProgress());
            jsonObjTask.put("jobStatus",collisionAnalyse.getStatus());
            dataObject.add(jsonObjTask);
        }
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);
        return jsonObject;
    }

    /**
     * 数据碰撞分析更新任务
     * */
    @RequestMapping("/deleteData")
    @ResponseBody
    public JSONObject deleteData(String taskId){
        JSONObject jsonObject = new JSONObject();
        jsonObject = analysisJobService.updateDeleteFlag(taskId);
        return jsonObject;
    }

    /**
     * 数据碰撞分析创建文件
     * */
    @RequestMapping("/createFile")
    @ResponseBody
    public JSONObject createFile(String taskId,String taskName){
        JSONObject jsonObject = new JSONObject();
        jsonObject = analysisJobService.createFile(taskId,taskName);
        return jsonObject;
    }

    /**
     * 下载文件
     * */
    @RequestMapping("/exportFile")
    public void exportFile(HttpServletRequest request, HttpServletResponse response){
        String formData = request.getParameter("formData");
        analysisJobService.exportFile(response, formData);
    }

    /**
     * 创建数据碰撞分析任务
     * */
    @RequestMapping("/createImsiFollow")
    @ResponseBody
    public JSONObject createImsiFollow(String taskName, String startDate,String endDate,String up,String down,String imsi,String mixCount,String type,String siteIds,String siteNames) throws ParseException {
        JSONObject jsonObject = new JSONObject();
        //主键ID
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        //用户ID,姓名
        Long creatorId = SigletonBean.getUserId();
        String creatorName = SigletonBean.getUserName();
        //存储任务信息
        JSONObject paramObject = new JSONObject();
        paramObject.put("targetIMSI",imsi );
        paramObject.put("startTime",startDate);
        paramObject.put("endTime",endDate);
        paramObject.put("topTime",up);
        paramObject.put("bottomTime",down);
        JSONArray jsonArray = JSONArray.parseArray(siteIds);
        paramObject.put("siteIds",jsonArray);
        paramObject.put("up",up);
        paramObject.put("down",down);
        paramObject.put("siteNames",siteNames);
        String parameter =  paramObject.toJSONString();

        //filter
        JSONObject filter = new JSONObject();
        filter.put("jobId",uuid);
        filter.put("imsi",imsi);
        filter.put("up",Integer.parseInt(up));
        filter.put("down",Integer.parseInt(down));
        filter.put("dbUrl",url);
        filter.put("dbUser",username);
        filter.put("dbPwd",password);
        filter.put("dbDriver","com.mysql.jdbc.Driver");
        filter.put("mixCount",Integer.parseInt(mixCount));
        filter.put("sites",jsonArray);

        List list = new ArrayList();
        Date start = sdf.parse(startDate.replace("/","-"));//2017-10-10 00:00:00
        Date end = sdf.parse(endDate.replace("/","-"));//2017-10-12 23:59:59
        int days = differentDays(start,end);
        for(int day = 0;day <= days;day++){
            //Map<String, Object> one = new HashMap<>();
            JSONObject one = new JSONObject();
            if(day == 0){//第一天
                String sta = startDate.replace("/","-");
                String sta1 = startDate.substring(0,10).replace("/","-");//2017_10_12
                String tableName = startDate.substring(0,10).replace("/","_");//2017_10_12
                String last = endDate.substring(0,10).replace("/","-");
                String last1 = endDate.replace("/","-");
                //判断时空条件是否为同一天
                if(sta1.equals(last)){
                    one.put("tableName","t_ue_info_" + tableName);
                    one.put("filter","CAPTURE_TIME >= '"+ sta +"' and CAPTURE_TIME <= '"+ last1 +"'");
                    //one.put("sites",jsonArray);
                }else{
                    one.put("tableName","t_ue_info_" + tableName);
                    one.put("filter","CAPTURE_TIME >= '"+ sta +"' and CAPTURE_TIME <= '"+ sta1 +" 23:59:59'");
                   // one.put("sites",jsonArray);
                }
            }else if(day == days){//最后一天
                String last = endDate.substring(0,10).replace("/","-");
                String tableName = endDate.substring(0,10).replace("/","_");
                String la = endDate.replace("/","-");
                one.put("tableName","t_ue_info_" + tableName);
                one.put("filter","CAPTURE_TIME >= '"+ last +" 00:00:00' and CAPTURE_TIME <= '"+ la +"'");
               // one.put("sites",jsonArray);
            }else{
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(start);
                calendar.add(Calendar.DATE,1);//把日期往后增加一天.正数往后推,负数往前推
                start = calendar.getTime();
                String name = sdf.format(start);//2017-12-02 00:00:00
                String tableName = name.substring(0,10).replace("-","_");
                String time = name.substring(0,10);
                one.put("tableName","t_ue_info_" + tableName);
                one.put("filter","CAPTURE_TIME >= '"+ time +" 00:00:00' and CAPTURE_TIME <= '"+ time +" 23:59:59'");
                //one.put("sites",jsonArray);
            }
            list.add(one);
        }
        filter.put("wheres",list);
        //创建数据碰撞分析任务对象
        DataAnalysis collisionAnalysis = new DataAnalysis();
        collisionAnalysis.setId(uuid);
        collisionAnalysis.setType(type);
        collisionAnalysis.setName(taskName);
        collisionAnalysis.setCreatorId(creatorId);
        collisionAnalysis.setCreatorName(creatorName);
        collisionAnalysis.setFilter(filter.toString());
        collisionAnalysis.setStatus("0");
        collisionAnalysis.setCreateTime(sdf.format(new Date()));
        //collisionAnalysis.setRemark(remark);
        collisionAnalysis.setDeleteFlag(0);
        collisionAnalysis.setParameter(parameter);
        jsonObject =  analysisJobService.creatorJob(collisionAnalysis);
        return jsonObject;
    }

    @RequestMapping("/queryImsiFollowTask")
    @ResponseBody
    public JSONObject queryImsiFollowTask(String type){
        JSONObject jsonObject = new JSONObject();
        JSONArray dataObject = new JSONArray();
        List<DataAnalysis> dataAnalysisList;
        dataAnalysisList = analysisJobService.queryImsiFollowTask();
        for ( DataAnalysis task : dataAnalysisList ) {
            JSONObject jsonObjTask = new JSONObject();
            JSONObject parameter = JSONObject.parseObject(  task.getParameter() ) ;
            jsonObjTask.put("id",  task.getId());
            jsonObjTask.put("name", task.getName());
            jsonObjTask.put("createTime", task.getCreateTime());
            jsonObjTask.put("taskProcess", task.getProgress() );
            jsonObjTask.put("status",task.getStatus());
            jsonObjTask.put("imsi", parameter.get("targetIMSI"));
            jsonObjTask.put("rangeTime",  parameter.get("startTime") + " - " + parameter.get("endTime"));
            jsonObjTask.put("startTime",parameter.get("startTime"));
            jsonObjTask.put("endTime",parameter.get("endTime"));
            jsonObjTask.put("siteIds",parameter.get("siteIds"));
            jsonObjTask.put("up",parameter.get("topTime"));
            jsonObjTask.put("down",parameter.get("bottomTime"));
            jsonObjTask.put("siteNames",parameter.get("siteNames"));
            dataObject.add(jsonObjTask);
        }
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);
        return jsonObject;
    }

    /**
     * imsi伴随分析查询详情
     * */
    @RequestMapping("/imsiFollowDetails")
    @ResponseBody
    public JSONObject imsiFollowDetails(HttpServletRequest request){
        String id = request.getParameter("formData");

        //分页处理
        int iDisplayStart = M16Tools.stringToNumber(request.getParameter("iDisplayStart"));
        int iDisplayLength = M16Tools.stringToNumber(request.getParameter("iDisplayLength"));
        String sEcho = request.getParameter("sEcho");
        int startNum = iDisplayStart / iDisplayLength + 1;

        PageHelper.startPage(startNum, iDisplayLength);
        List<Map> list = analysisJobService.imsiFollowDetails(id);
        PageInfo pg = new PageInfo(list);
        DataTablePager page = new DataTablePager();
        page.setDataResult(list);
        page.setiTotalRecords(pg.getTotal());
        page.setiTotalDisplayRecords(pg.getTotal());
        page.setiDisplayLength(iDisplayLength);
        page.setsEcho(sEcho);

        return (JSONObject)JSONObject.toJSON(page);
    }

}
