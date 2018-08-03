package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.model.AnalysisData;
import com.iekun.ef.model.ExportTaskInfo;
import com.iekun.ef.service.AnalysisDataService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.util.ComInfoUtil;
import com.iekun.ef.util.CommonConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by java1 on 2017/10/30 0030.
 */
@Controller
public class AnalysisDataController {

    private static Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private ComInfoUtil comInfoUtil;

    @Autowired
    private ResourceService resourceServ;
    @Autowired
    private UserService userService;

    @Autowired
    private AnalysisDataService analysisDataService;


    //可疑人员分析
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
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "startDate", required = false ) String startDate,
            @RequestParam(value = "endDate", required = false ) String endDate
			/*@RequestParam(value = "start", required = false ) Integer start,
			@RequestParam(value = "startDate", required = false ) String startDate,
			@RequestParam(value = "endDate", required = false ) String endDate,
			@RequestParam(value = "operator", required = false ) Integer operator,
			@RequestParam(value = "homeOwnership", required = false ) String homeOwnership,
			@RequestParam(value = "siteSN", required = false ) String siteSN,
			@RequestParam(value = "deviceSN", required = false ) String deviceSN,
			@RequestParam(value = "imsi", required = false ) String imsi*/
    ) {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        List<AnalysisData> bmd=     analysisDataService.getBMD();

/*
        if (condition == null) {
            condition = "1=1";
        }
        condition = condition + " AND sj.daycount = " + "'" + daycount + "'";*/
        String pinjie="";

        if(bmd.size()>0){

            for(int i=0;i<bmd.size();i++){

            if(pinjie.equals("")){
                pinjie="'" + bmd.get(i).getImsi() + "'";

            }else{
                pinjie=pinjie+","+"'" + bmd.get(i).getImsi() + "'"  ;
            }


            }
        }else{
            pinjie=" AND 1=1";
        }
          String condition="";
        if(bmd.size()>0){
            condition=" AND  shzsb.imsi NOT IN ("+pinjie+")";
        }else{
            condition=" AND 1=1 ";
        }
        System.out.println(condition);

        String Condition2 =null;
        Condition2 = this.getCondition3(startDate, endDate);

        if (Condition2 == null) {
            Condition2 = "1=1";
        }else{
            Condition2 = Condition2;
        }
        String condition2 = Condition2;


        String Condition3 =null;
        Condition3 = this.getCondition3(startDate, endDate);
        if (Condition3 == null) {
            Condition3 = "1=1";
        }else{
            Condition3 = Condition3;
        }
        String condition3 = Condition3;


      List<AnalysisData> analysisData= analysisDataService.getTueinfoData( condition,condition2);
    int count=0;
      for(AnalysisData AnalysisData1:analysisData){
          JSONObject ueInfoJo = new JSONObject();
          if(count==0){
              ueInfoJo.put("id", ++count);
          }else{
              ueInfoJo.put("id", ++count);
          }

          ueInfoJo.put("sitename", AnalysisData1.getSitename());
          ueInfoJo.put("sitesn", AnalysisData1.getSitesn());
          ueInfoJo.put("devicesn", AnalysisData1.getDevicesn());
          ueInfoJo.put("operator", AnalysisData1.getOperator());
          ueInfoJo.put("cityname", AnalysisData1.getCityname());
          ueInfoJo.put("imsi", AnalysisData1.getImsi());
          ueInfoJo.put("datacount", AnalysisData1.getDatacount()+"次");
          ueInfoJo.put("daycount", AnalysisData1.getDaycount()+"天");
         /* ueInfoJo.put("one", "");
          ueInfoJo.put("two", "");*/

          List<AnalysisData> analysisDatabydevicesn=   analysisDataService.getTueinfoDataBydevicesn(AnalysisData1.getImsi(),condition3);

            if(analysisDatabydevicesn.size()>1){
                ueInfoJo.put("one", analysisDatabydevicesn.get(0).getSitename());
                ueInfoJo.put("two", analysisDatabydevicesn.get(1).getSitename());
            }else{
                ueInfoJo.put("one", analysisDatabydevicesn.get(0).getSitename());
                ueInfoJo.put("two", "");
            }


          ja.add(ueInfoJo);

      }
       /* jo.put("draw", draw);
        jo.put("recordsTotal", analysisData.size());
        jo.put("recordsFiltered", analysisData.size());
        jo.put("data", ja);*/
        jo.put("status", true);
        jo.put("message", "成功");
        jo.put("data", ja);
        return jo;
    }






    @RequestMapping("/queryAnalysisiDataAjaxByImsi")
    @ResponseBody
    public JSONObject queryAnalysisiDataAjaxByImsi(
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "imsi", required = false ) String imsi,
            @RequestParam(value = "startDate", required = false ) String startDate,
            @RequestParam(value = "endDate", required = false ) String endDate
			/*@RequestParam(value = "start", required = false ) Integer start,
			@RequestParam(value = "startDate", required = false ) String startDate,
			@RequestParam(value = "endDate", required = false ) String endDate,
			@RequestParam(value = "operator", required = false ) Integer operator,
			@RequestParam(value = "homeOwnership", required = false ) String homeOwnership,
			@RequestParam(value = "siteSN", required = false ) String siteSN,
			@RequestParam(value = "deviceSN", required = false ) String deviceSN,
			@RequestParam(value = "imsi", required = false ) String imsi*/
    ) {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        String Condition2 =null;
        Condition2 = this.getCondition3(startDate, endDate);

        if (Condition2 == null) {
            Condition2 = "1=1";
        }else{
            Condition2 = Condition2;
        }
        String condition2 = Condition2;

        /*List<AnalysisData> analysisData= analysisDataService.getTueinfoData();*/
        int count=0;
        List<AnalysisData> analysisDatabyimsi=   analysisDataService.queryAnalysisiDataAjaxByImsi(imsi,condition2);
        for(AnalysisData AnalysisData1:analysisDatabyimsi){
            JSONObject ueInfoJo = new JSONObject();
            if(count==0){
                ueInfoJo.put("id", ++count);
            }else{
                ueInfoJo.put("id", ++count);
            }
            ueInfoJo.put("sitename", AnalysisData1.getSitename());
            ueInfoJo.put("sitesn", AnalysisData1.getSitesn());
            ueInfoJo.put("capturetime", AnalysisData1.getCapturetime());
            ja.add(ueInfoJo);
        }
        jo.put("status", true);
        jo.put("message", "成功");
        jo.put("data", ja);
        return jo;
    }














    @RequestMapping("/queryAnalysisiDataAjaxByAll")
    @ResponseBody
    public JSONObject queryAnalysisiDataAjaxByAll(
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
			@RequestParam(value = "start", required = false ) Integer start,
            @RequestParam(value = "imsi", required = false ) String imsi,
			@RequestParam(value = "startDate", required = false ) String startDate,
			@RequestParam(value = "endDate", required = false ) String endDate,
			@RequestParam(value = "daycount", required = false ) String daycount,
			@RequestParam(value = "datacount", required = false ) String datacount,
			@RequestParam(value = "period", required = false ) String period
    ) {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String  zqstart="";
        String  zqend="";
        Date now = new Date();
       // zqend=dateFormat.format( now );
        if(period.equals("0")){
            zqstart="";
            zqend="";
        }else if(period.equals("1")){
            //近一周
            Calendar curr1 = Calendar.getInstance();
            curr1.set(Calendar.DAY_OF_MONTH,curr1.get(Calendar.DAY_OF_MONTH)-7);
            Date dat2e=curr1.getTime();
            //dateFormat.format(dat2e);
            zqstart= dateFormat.format(dat2e);
            zqend=dateFormat.format( now );
        }else if(period.equals("2")){
            //近一月
            Calendar curr2 = Calendar.getInstance();
            curr2.set(Calendar.MONTH,curr2.get(Calendar.MONTH)-1);
            Date date3=curr2.getTime();
           // dateFormat.format(date3);
           // System.out.println(dateFormat.format(date3));
            zqstart= dateFormat.format(date3);
            zqend=dateFormat.format( now );
        }else if(period.equals("3")){
            //近半年
            Calendar curr = Calendar.getInstance();
            curr.set(Calendar.MONTH,curr.get(Calendar.MONTH)-6);
            Date date1=curr.getTime();
          /*  dateFormat.format(date1);
            System.out.println(dateFormat.format(date1));*/

            zqstart= dateFormat.format(date1);
            zqend=dateFormat.format( now );
        }
        List<AnalysisData> bmd=     analysisDataService.getBMD();
        String pinjie="";
        if(bmd.size()>0){
            for(int i=0;i<bmd.size();i++){
                if(pinjie.equals("")){
                    pinjie="'" + bmd.get(i).getImsi() + "'";

                }else{
                    pinjie=pinjie+","+"'" + bmd.get(i).getImsi() + "'"  ;
                }
            }
        }else{
            pinjie=" AND 1=1";
        }
        String PJ="";
        if(bmd.size()>0){
            PJ=" AND  imsi NOT IN ("+pinjie+")";
        }else{
            PJ="";
        }
        /*String Condition =null;
        Condition = this.getCondition1(startDate, endDate, daycount, datacount, zqstart, zqend,  imsi);
        if (Condition == null) {
            Condition = "1=1"+PJ;
        }else{
            Condition = Condition+PJ;
        }
        String condition = Condition;
*/
        String Condition =null;
        Condition = this.getCondition1(startDate, endDate, daycount, datacount, zqstart, zqend,  imsi);//里面去除 时间与imsi号的查询
        if (Condition == null) {
            Condition = "1=1";
        }else{
            Condition = Condition;
        }
        String condition = Condition;


        String Condition2 =null;
        Condition2 = this.getCondition2(startDate, endDate, daycount, datacount, zqstart, zqend,  imsi);//根据周期 天数 次数 查询
        if (Condition2 == null) {
            Condition2 = "1=1"+PJ;
        }else{
            Condition2 = Condition2+PJ;
        }
        String condition2 = Condition2;



        String Condition3 =null;
        Condition3 = this.getCondition3(startDate, endDate);
        if (Condition3 == null) {
            Condition3 = "1=1";
        }else{
            Condition3 = Condition3;
        }
        String condition3 = Condition3;




        /*List<AnalysisData> analysisData= analysisDataService.getTueinfoData();*/
        int count=0;
       /* List<AnalysisData> analysisDatabyimsi=   analysisDataService.queryAnalysisiDataAjaxByAll(condition);*/
       List<AnalysisData> analysisDatabyimsi=   analysisDataService.queryAnalysisiDataAjaxByAll(condition,condition2);
        for(AnalysisData AnalysisData1:analysisDatabyimsi){
            JSONObject ueInfoJo = new JSONObject();
            if(count==0){
                ueInfoJo.put("id", ++count);
            }else{
                ueInfoJo.put("id", ++count);
            }

            ueInfoJo.put("sitename", AnalysisData1.getSitename());
            ueInfoJo.put("sitesn", AnalysisData1.getSitesn());
            ueInfoJo.put("devicesn", AnalysisData1.getDevicesn());
            ueInfoJo.put("operator", AnalysisData1.getOperator());
            ueInfoJo.put("cityname", AnalysisData1.getCityname());
            ueInfoJo.put("imsi", AnalysisData1.getImsi());
            ueInfoJo.put("datacount", AnalysisData1.getDatacount()+"次");
            ueInfoJo.put("daycount", AnalysisData1.getDaycount()+"天");
         /* ueInfoJo.put("one", "");
          ueInfoJo.put("two", "");*/



            List<AnalysisData> analysisDatabydevicesn=   analysisDataService.getTueinfoDataBydevicesn(AnalysisData1.getImsi(),condition3);
            if(analysisDatabydevicesn.size()>1){
                ueInfoJo.put("one", analysisDatabydevicesn.get(0).getSitename());
                ueInfoJo.put("two", analysisDatabydevicesn.get(1).getSitename());
            }else{
                ueInfoJo.put("one", analysisDatabydevicesn.get(0).getSitename());
                ueInfoJo.put("two", "");
            }


            ja.add(ueInfoJo);

        }
        jo.put("status", true);
        jo.put("message", "成功");
        jo.put("data", ja);
        return jo;
    }
    private String getCondition1(String startDate, String endDate,String daycount,
                                 String datacount, String zqstart,String zqend, String imsi)
    {
        String condition = null;
      /*  if (startDate != null && !(startDate.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
                startDate = startDate.replace("/", "-");
                endDate = endDate.replace("/", "-");
                condition = "sj.CAPTURE_TIME > " + "'" + startDate + "'" + " AND sj.CAPTURE_TIME < " + "'" + endDate + "'";

        }*/

        if (daycount != null && !(daycount.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
                condition = condition + " AND sj.daycount = " + "'" + daycount + "'";

        }
        if (datacount != null && !(datacount.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
                condition = condition + " AND sj.datacount = " + "'" + datacount + "'";

        }
        if (zqstart != null && !(zqstart.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
                condition = condition + " AND sj.CAPTURE_TIME > " + "'" + zqstart + "'" + " AND sj.CAPTURE_TIME < " + "'" + zqend + "'";

        }
       /* if (imsi != null && !(imsi.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
                condition = condition + " AND sj.IMSI = " + "'" + imsi + "'";

        }*/
        return condition;
    }
    private String getCondition2(String startDate, String endDate,String daycount,
                                 String datacount, String zqstart,String zqend, String imsi)
    {
        String condition = null;
       if (startDate != null && !(startDate.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
                startDate = startDate.replace("/", "-");
                endDate = endDate.replace("/", "-");
                condition = "CAPTURE_TIME > " + "'" + startDate + "'" + " AND CAPTURE_TIME < " + "'" + endDate + "'";

        }

       /* if (daycount != null && !(daycount.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
            condition = condition + " AND sj.daycount = " + "'" + daycount + "'";

        }
        if (datacount != null && !(datacount.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
            condition = condition + " AND sj.datacount = " + "'" + datacount + "'";

        }
        if (zqstart != null && !(zqstart.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
            condition = condition + " AND sj.CAPTURE_TIME > " + "'" + zqstart + "'" + " AND sj.CAPTURE_TIME < " + "'" + zqend + "'";

        }*/
        if (imsi != null && !(imsi.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
                condition = condition + " AND IMSI = " + "'" + imsi + "'";

        }
        return condition;
    }
    private String getCondition3(String startDate, String endDate)
    {
        String condition = null;
        if (startDate != null && !(startDate.isEmpty())) {
            if (condition == null) {
                condition = "1=1";
            }
            startDate = startDate.replace("/", "-");
            endDate = endDate.replace("/", "-");
            condition = "CAPTURE_TIME > " + "'" + startDate + "'" + " AND CAPTURE_TIME < " + "'" + endDate + "'";

        }
        return condition;
    }
    @RequestMapping("/queryAnalysisiDataAjaxexportData")
    @ResponseBody
    public JSONObject exportData(@RequestParam(value = "imsi", required = false ) String imsi,
                                 @RequestParam(value = "startDate", required = false ) String startDate,
                                 @RequestParam(value = "endDate", required = false ) String endDate,
                                 @RequestParam(value = "daycount", required = false ) String daycount,
                                 @RequestParam(value = "datacount", required = false ) String datacount,
                                 @RequestParam(value = "period", required = false ) String period
    ) {
        JSONObject dataObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String  zqstart="";
        String  zqend="";
        Date now = new Date();
        // zqend=dateFormat.format( now );
        if(period.equals("0")){
            zqstart="";
            zqend="";
        }else if(period.equals("1")){
            //近一周
            Calendar curr1 = Calendar.getInstance();
            curr1.set(Calendar.DAY_OF_MONTH,curr1.get(Calendar.DAY_OF_MONTH)-7);
            Date dat2e=curr1.getTime();
            zqstart= dateFormat.format(dat2e);
            zqend=dateFormat.format( now );
        }else if(period.equals("2")){
            //近一月
            Calendar curr2 = Calendar.getInstance();
            curr2.set(Calendar.MONTH,curr2.get(Calendar.MONTH)-1);
            Date date3=curr2.getTime();
            zqstart= dateFormat.format(date3);
            zqend=dateFormat.format( now );
        }else if(period.equals("3")){
            //近半年
            Calendar curr = Calendar.getInstance();
            curr.set(Calendar.MONTH,curr.get(Calendar.MONTH)-6);
            Date date1=curr.getTime();
            zqstart= dateFormat.format(date1);
            zqend=dateFormat.format( now );
        }
        List<AnalysisData> bmd=analysisDataService.getBMD();
        String pinjie="";
        if(bmd.size()>0){
            for(int i=0;i<bmd.size();i++){
                if(pinjie.equals("")){
                    pinjie="'" + bmd.get(i).getImsi() + "'";

                }else{
                    pinjie=pinjie+","+"'" + bmd.get(i).getImsi() + "'"  ;
                }
            }
        }else{
            pinjie=" AND 1=1";
        }
        String PJ="";
        if(bmd.size()>0){
            PJ=" AND  imsi NOT IN ("+pinjie+")";
        }else{
            PJ="";
        }
      /*  String Condition =null;
        Condition = this.getCondition1(startDate, endDate, daycount, datacount, zqstart, zqend,  imsi);
        if (Condition == null) {
            Condition = "1=1"+PJ;
        }else{
            Condition = Condition+PJ;
        }
        String condition = Condition;*/
        String Condition =null;
        Condition = this.getCondition1(startDate, endDate, daycount, datacount, zqstart, zqend,  imsi);//里面去除 时间与imsi号的查询
        if (Condition == null) {
            Condition = "1=1";
        }else{
            Condition = Condition;
        }
        String condition = Condition;


        String Condition2 =null;
        Condition2 = this.getCondition2(startDate, endDate, daycount, datacount, zqstart, zqend,  imsi);//根据周期 天数 次数 查询
        if (Condition2 == null) {
            Condition2 = "1=1"+PJ;
        }else{
            Condition2 = Condition2+PJ;
        }
        String condition2 = Condition2;

        String Condition3 =null;
        Condition3 = this.getCondition3(startDate, endDate);
        if (Condition3 == null) {
            Condition3 = "1=1";
        }else{
            Condition3 = Condition3;
        }
        String condition3 = Condition3;





        /*List<AnalysisData> analysisData= analysisDataService.getTueinfoData();*/
        int count=0;
        List<AnalysisData> list=new ArrayList<AnalysisData>();
        List<AnalysisData> analysisDatabyimsi=   analysisDataService.queryAnalysisiDataAjaxByAll(condition,condition2);
        for(AnalysisData AnalysisData1:analysisDatabyimsi){
          //  JSONObject ueInfoJo = new JSONObject();
            AnalysisData analysisData=new AnalysisData();
            if(count==0){
                analysisData.setId(++count+"");
            }else{
                analysisData.setId(++count+"");
            }
          //  ueInfoJo.put("sitename", AnalysisData1.getSitename());
            analysisData.setSitename(AnalysisData1.getSitename());
          //  ueInfoJo.put("sitesn", AnalysisData1.getSitesn());
            analysisData.setSitesn(AnalysisData1.getSitesn());
          //  ueInfoJo.put("devicesn", AnalysisData1.getDevicesn());
            analysisData.setDevicesn(AnalysisData1.getDevicesn());
          //  ueInfoJo.put("operator", AnalysisData1.getOperator());
            analysisData.setOperator(AnalysisData1.getOperator());
          //  ueInfoJo.put("cityname", AnalysisData1.getCityname());
            analysisData.setCityname(AnalysisData1.getCityname());
          //  ueInfoJo.put("imsi", AnalysisData1.getImsi());
            analysisData.setImsi(AnalysisData1.getImsi());
           // ueInfoJo.put("datacount", AnalysisData1.getDatacount()+"次");
            analysisData.setDatacount(AnalysisData1.getDatacount()+"次");
          //  ueInfoJo.put("daycount", AnalysisData1.getDaycount()+"天");
            analysisData.setDaycount(AnalysisData1.getDaycount()+"天");
            List<AnalysisData> analysisDatabydevicesn=   analysisDataService.getTueinfoDataBydevicesn(AnalysisData1.getImsi(),condition3);
            if(analysisDatabydevicesn.size()>1){
               // ueInfoJo.put("one", analysisDatabydevicesn.get(0).getSitename());
               // ueInfoJo.put("two", analysisDatabydevicesn.get(1).getSitename());
                analysisData.setOne( analysisDatabydevicesn.get(0).getSitename());
                analysisData.setTwo(analysisDatabydevicesn.get(1).getSitename());
            }else{
              //  ueInfoJo.put("one", analysisDatabydevicesn.get(0).getSitename());
               // ueInfoJo.put("two", "");
                analysisData.setOne( analysisDatabydevicesn.get(0).getSitename());
                analysisData.setTwo("");
            }
            list.add(analysisData);

        }

        String  exportId = null;
        exportId  = this.getExportId();
        ExportTaskInfo exportTaskInfo = new ExportTaskInfo();
        SigletonBean.exportInfoMaps.put(exportId, exportTaskInfo);
        analysisDataService.exportData(exportId,list);
        dataObject.put("exportId", exportId );
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




    @RequestMapping("/queryAnalysisiDataAjaxexportDataProgress")
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
