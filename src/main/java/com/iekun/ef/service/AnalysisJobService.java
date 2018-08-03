package com.iekun.ef.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvWriter;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.AnalysisJobMapper;
import com.iekun.ef.model.*;
import com.iekun.ef.push.NoticePush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AnalysisJobService {

    @Autowired
    AnalysisJobMapper analysisJobMapper;

    @Autowired
    NoticePush noticePush;

    @Value("${download.filePath}")
    private String filePath;

    @Value("${createFile.queryCount}")
    private int queryCount;

    @Value("${createFile.maxTxtCount}")
    private int maxTxtCount;

    private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");

    /**
     * 数据碰撞分析创建任务
     * */
    public JSONObject creatorJob(DataAnalysis collisionAnalysis){
        JSONObject jsonObject = new JSONObject();
        boolean status = false;
        try{
            analysisJobMapper.creatorJob(collisionAnalysis);
            status = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        if (status == true) {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }
        return jsonObject;
    }

    /**
     * 数据碰撞分析查询任务
     * */
    public List<DataAnalysis> queryDataCollide(Long userId ) {

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("type", "collisionAnalysis");
        params.put("creatorId",  userId );

        List<DataAnalysis>  collisionAnalyses = analysisJobMapper.queryData(params);
        List<DataAnalysis>  dataCollideTasks = new LinkedList<>();
        for ( DataAnalysis collisionAnalyse: collisionAnalyses ) {

            JSONObject jsonObject = JSONObject.parseObject(  collisionAnalyse.getParameter() ) ;
            JSONArray jsonItems = jsonObject.getJSONArray("items");

            DataAnalysis collisionAnalysis = new DataAnalysis();
            collisionAnalysis.setSpaceTimeItems(jsonItems);
            collisionAnalysis.setId(collisionAnalyse.getId());
            collisionAnalysis.setName(collisionAnalyse.getName());
            collisionAnalysis.setCreateTime(collisionAnalyse.getCreateTime());
            collisionAnalysis.setRemark(collisionAnalyse.getRemark());
            collisionAnalysis.setProgress(collisionAnalyse.getProgress());
            collisionAnalysis.setStatus(collisionAnalyse.getStatus());
            dataCollideTasks.add( collisionAnalysis );

        }

        return dataCollideTasks;
    }

    /**
     * 数据分析删除任务
     * */
    public JSONObject updateDeleteFlag(String taskId){
        JSONObject jsonObject = new JSONObject();
        boolean status = false;

        try{
            analysisJobMapper.updateDeleteFlag(taskId);
            status = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        if (status == true) {
            jsonObject.put("status", true);
            jsonObject.put("message", "删除任务成功");
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "删除任务失败");
        }
        return jsonObject;
    }

    public DataAnalysis getDataCollideTaskById(String taskId ) {

        DataAnalysis collisionAnalysis = analysisJobMapper.getDataCollideTaskById(taskId);
        JSONObject jsonObject = JSONObject.parseObject(  collisionAnalysis.getParameter() ) ;
        //时空条件
        JSONArray  jsonItems = jsonObject.getJSONArray("items");
        collisionAnalysis.setSpaceTimeItems(jsonItems);
        return collisionAnalysis;

    }

    /**
     *  数据碰撞分析查询详情
     */
    public List<Map> queryRowDataCollide(String id) {

        List<TargetRule> targetGroup = null;
        Map param = new HashMap();
        param.put("tableName","t_analysis_job_"+id);
        param.put("targetTable","t_analysis_job_t_"+id);
        try{
            List<Map> list = analysisJobMapper.queryRowDataCollideByImsi(param);
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 数据碰撞分析imsi号码对应的站点信息
     * */
    public List<UeInfo> showThisRowData(Map params){
        JSONObject jsonObject= new JSONObject();
        Map param = new HashMap();

        try{
            param.put("tableName","t_analysis_job_"+params.get("taskId"));
            param.put("imsi",params.get("imsi"));
            List<UeInfo> list = analysisJobMapper.showThisRowData(param);
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject createFile(String taskId,String taskName){
        JSONObject jsonObject = new JSONObject();
        Boolean status = false;
        //获取文件名称
        String uuid = UUID.randomUUID().toString().trim().replace("-", "");
        //String date = sdf.format(new Date());
        String fileName = uuid + ".csv";
        File file = new File(filePath.trim() + "/" + fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
        CsvWriter csvWriter = new CsvWriter(filePath.trim() + "/" + fileName, ',', Charset.forName("GBK"));
        try {
            // 写表头
            String[] csvHeaders = { "imsi号码", "上号站点", "上号时间" };
            csvWriter.writeRecord(csvHeaders);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Map map = new HashMap();
            map.put("tableName","t_analysis_job_" + taskId);
            int count = analysisJobMapper.queryCountByTaskId(map);
            int item;
            if(count < maxTxtCount){
                item = count;
            }else{
                item = maxTxtCount;
            }
            int limit = 0;
            int startIdx = 0;
            int selected = 0;
            while(true) {
                if(item - selected >= queryCount) {
                    limit = queryCount;
                    Map maps = new HashMap();
                    maps.put("tableName","t_analysis_job_" + taskId);
                    maps.put("start",startIdx);
                    maps.put("length",limit);
                    List<UeInfo> records = analysisJobMapper.queryDataToCreateFile(maps);
                    writeCsvFile(fileName, records,csvWriter);
                    startIdx += queryCount;
                    selected += queryCount;
                } else {
                    limit = item - selected;
                    if(limit > 0){
                        Map maps = new HashMap();
                        maps.put("tableName","t_analysis_job_" + taskId);
                        maps.put("start",startIdx);
                        maps.put("length",limit);
                        List<UeInfo> records = analysisJobMapper.queryDataToCreateFile(maps);
                        writeCsvFile(fileName, records,csvWriter);
                    }
                    break;
                }
            }
            status = true;
        } catch (Exception e) {
            jsonObject.put("status",false);
            jsonObject.put("message","创建csv异常");
            e.printStackTrace();
        }
        if(true == status){
            jsonObject.put("status", true);
            jsonObject.put("result",fileName);
            jsonObject.put("message", "创建csv文件完成");
        }
        return jsonObject;
    }

    /**
     * 数据碰撞分析创建要下载的csv文件
     */
    public void writeCsvFile(String fileName,List<UeInfo> ueInfos,CsvWriter csvWriter) {
        String csvFilePath = filePath.trim() + "/" + fileName;
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilePath,true),"GBK"),1024);
            csvWriter = new CsvWriter(out,',');
            for(UeInfo ueInfo : ueInfos){
                //"imsi号码", "上号站点", "上号时间"
                String[] csvContent = {"=\"" + ueInfo.getImsi() + "\"",ueInfo.getSiteName(),"=\"" + ueInfo.getCaptureTime() + "\""};
                csvWriter.writeRecord(csvContent);
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * 执行数据导出
     * @param response
     * @param formData
     */
    public void exportFile(HttpServletResponse response, String formData) {
        File file = new  File(filePath.trim() + "/" + formData);
        String targetName = sdf.format(new Date()) + ".csv";
        try{
            FileInputStream inputStream = new FileInputStream(file);
            //写到前端
            response.reset();
            targetName = new String(targetName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + targetName + "\"");
            response.addHeader("Content-lenth", "" + file.length());
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            int temp = 0 ;
            byte[] tempbytes = new byte[1024*1024*1024];
            while ((temp = inputStream.read(tempbytes)) != -1) { // 读取内容
                os.write(tempbytes,0,temp);
            }
            inputStream.close();
            os.flush();
            os.close();
            response.flushBuffer();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * IMSI伴随分析任务查询
     * */
    public List<DataAnalysis> queryImsiFollowTask() {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("type", "alongAnalysis");
        params.put("creatorId",  SigletonBean.getUserId() );
        List<DataAnalysis>  dataAnalyses = analysisJobMapper.queryData(params);
        return dataAnalyses;
    }

    /**
     * imsi伴随分析查询详情
     */
    public List<Map> imsiFollowDetails(String id) {

        List<TargetRule> targetGroup = null;
        Map param = new HashMap();
        param.put("targetTable","t_analysis_job_t_"+id);
        try{
            List<Map> list = analysisJobMapper.imsiFollowDetails(param);
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }


}
