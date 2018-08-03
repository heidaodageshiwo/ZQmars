package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangqi.yang on 2016/12/13.
 */

public class ResidentPeopleAnalysis {


    private static Integer MAX_RECORD = 500;

    public static JSONObject getTasks() {
        JSONObject jsonObject = new JSONObject();
        JSONArray tasksArray = new JSONArray();

        JSONObject task1 = new JSONObject();
        task1.put("id", 1);
        task1.put("name", "闵行区分析");
        task1.put("areaName", "中国,上海,上海市,闵行区");
        task1.put("rangeTime", "2015-01-01 08:05:00 - 2016-02-01 08:05:00");
        task1.put("createTime", "2016-12-08 09:30:00");
        task1.put("taskProcess", 35 );

        JSONObject task2 = new JSONObject();
        task2.put("id", 2);
        task2.put("name", "徐汇区分析");
        task2.put("areaName", "中国,上海,上海市,徐汇区");
        task2.put("rangeTime", "2014-01-01 08:05:00 - 2015-02-01 08:05:00");
        task2.put("createTime", "2016-12-08 09:30:00");
        task2.put("taskProcess", 100 );

        tasksArray.add(task1);
        tasksArray.add(task2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", tasksArray);

        return jsonObject;

    }

    public static void  getTaskInfo( Map<String, Object> model ) {

        Map<String, Object> residentPeopleTaskInfo = new HashMap();

        residentPeopleTaskInfo.put("taskName", "徐汇区分析");
        residentPeopleTaskInfo.put("taskId", 2);
        residentPeopleTaskInfo.put("areaName", "中国,上海,上海市,徐汇区");
        residentPeopleTaskInfo.put("rangeTime",  "2014-01-01 08:05:00 - 2015-02-01 08:05:00" );
        residentPeopleTaskInfo.put("createTime", "2016-12-08 09:30:00" );

        model.put("taskReport", residentPeopleTaskInfo);
    }


    public static JSONObject getReportData(
            String  dataType,
            Integer draw,
            Integer length,
            Integer start
    ){


        JSONObject DataObject = new JSONObject();

        if( dataType.equals("total")) {

            JSONObject  totalData = new JSONObject();

            totalData.put("total",MAX_RECORD );
            totalData.put("fixed", 365 );
            totalData.put("period", 135 );

            DataObject.put("status", true);
            DataObject.put("message", "成功");
            DataObject.put("data", totalData);

        } else if( dataType.equals("fixed")){

            JSONArray  recordArray = new JSONArray();

            DataObject.put("draw", draw);
            DataObject.put("recordsTotal", 365);
            DataObject.put("recordsFiltered", 365);
            DataObject.put("data", recordArray );

            Integer  loopLength = length;
            if( ( 365 - start) < length ) {
                loopLength = 365 - start;
            }

            for( int i=0; i < loopLength; i++ ) {

                String imsiVal = String.format("460047500000%02d", i );

                JSONObject recordObject = new JSONObject();
                recordObject.put("id", i);
                recordObject.put("imsi", imsiVal);

                recordArray.add(recordObject);
            }


        } else if( dataType.equals("period")){

            JSONArray  recordArray = new JSONArray();

            DataObject.put("draw", draw);
            DataObject.put("recordsTotal", 135);
            DataObject.put("recordsFiltered", 135);
            DataObject.put("data", recordArray );

            Integer  loopLength = length;
            if( ( 135 - start) < length ) {
                loopLength = 135 - start;
            }

            for( int i=0; i < loopLength; i++ ) {

                String imsiVal = String.format("460002500000%02d", i );

                JSONObject recordObject = new JSONObject();
                recordObject.put("id", i);
                recordObject.put("imsi", imsiVal);

                recordArray.add(recordObject);
            }


        } else {

            JSONArray  recordArray = new JSONArray();
            DataObject.put("draw", draw);
            DataObject.put("recordsTotal", 0);
            DataObject.put("recordsFiltered", 0);
            DataObject.put("data", recordArray );

        }


        return DataObject;
    }


}
