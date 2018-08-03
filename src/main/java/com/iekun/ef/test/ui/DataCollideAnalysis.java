package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jiangqi.yang on 2016/12/8.
 */
public class DataCollideAnalysis {

    private static Integer MAX_RECORD = 50;

    public static JSONObject getTasks() {
        JSONObject jsonObject = new JSONObject();
        JSONArray tasksArray = new JSONArray();

        JSONObject task1 = new JSONObject();
        JSONArray task1SpaceTime = new JSONArray();
        task1.put("id", 1);
        task1.put("name", "沪嘉沿线");
        task1.put("createTime", "2016-12-08 09:30:00");
        task1.put("taskProcess", 35 );
        task1.put("spaceTime", task1SpaceTime );
        JSONObject task1SpaceTime1 = new JSONObject();
        task1SpaceTime1.put("rangeTime", "2016-01-01 08:05:00 - 2016-02-01 08:05:00");
        task1SpaceTime1.put("siteSN", "S0000001");
        task1SpaceTime1.put("siteName", "设备1");
        task1SpaceTime.add(task1SpaceTime1);
        JSONObject task1SpaceTime2 = new JSONObject();
        task1SpaceTime2.put("rangeTime", "2016-04-01 08:05:00 - 2016-05-01 08:05:00");
        task1SpaceTime2.put("siteSN", "S0000002");
        task1SpaceTime2.put("siteName", "设备2");
        task1SpaceTime.add(task1SpaceTime2);
        JSONObject task1SpaceTime3 = new JSONObject();
        task1SpaceTime3.put("rangeTime", "2016-07-01 08:05:00 - 2016-08-01 08:05:00");
        task1SpaceTime3.put("siteSN", "S0000003");
        task1SpaceTime3.put("siteName", "设备3");
        task1SpaceTime.add(task1SpaceTime1);
        JSONObject task1SpaceTime4 = new JSONObject();
        task1SpaceTime4.put("rangeTime", "2016-10-01 08:05:00 - 2016-12-01 08:05:00");
        task1SpaceTime4.put("siteSN", "S0000004");
        task1SpaceTime4.put("siteName", "设备4");
        task1SpaceTime.add(task1SpaceTime4);


        JSONObject task2 = new JSONObject();
        JSONArray task2SpaceTime = new JSONArray();
        task2.put("id", 2);
        task2.put("name", "沪苏沿线");
        task2.put("createTime", "2016-12-08 09:30:00");
        task2.put("taskProcess", 100 );
        task2.put("spaceTime", task2SpaceTime );
        JSONObject task2SpaceTime1 = new JSONObject();
        task2SpaceTime1.put("rangeTime", "2016-01-01 08:05:00 - 2016-02-01 08:05:00");
        task2SpaceTime1.put("siteSN", "S0000010");
        task2SpaceTime1.put("siteName", "设备10");
        task2SpaceTime.add(task2SpaceTime1);
        JSONObject task2SpaceTime2 = new JSONObject();
        task2SpaceTime2.put("rangeTime", "2016-04-01 08:05:00 - 2016-05-01 08:05:00");
        task2SpaceTime2.put("siteSN", "S0000012");
        task2SpaceTime2.put("siteName", "设备12");
        task2SpaceTime.add(task2SpaceTime2);
        JSONObject task2SpaceTime3 = new JSONObject();
        task2SpaceTime3.put("rangeTime", "2016-07-01 08:05:00 - 2016-08-01 08:05:00");
        task2SpaceTime3.put("siteSN", "S0000013");
        task2SpaceTime3.put("siteName", "设备13");
        task2SpaceTime.add(task2SpaceTime3);
        JSONObject task2SpaceTime4 = new JSONObject();
        task2SpaceTime4.put("rangeTime", "2016-10-01 08:05:00 - 2016-12-01 08:05:00");
        task2SpaceTime4.put("siteSN", "S0000014");
        task2SpaceTime4.put("siteName", "设备14");
        task2SpaceTime.add(task2SpaceTime4);

        tasksArray.add(task1);
        tasksArray.add(task2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", tasksArray);

        return jsonObject;

    }

    public static void  getTaskInfo( Map<String, Object> model ) {

        Map<String, Object>  dataCollideTaskInfo = new HashMap();

        dataCollideTaskInfo.put("taskName", "沪苏沿线");
        dataCollideTaskInfo.put("taskId", 2);
        dataCollideTaskInfo.put("createTime", "2016-12-08 09:30:00" );

        List spaceTimeList = new ArrayList();
        Map<String, Object> spaceTime1 = new HashMap();
        spaceTime1.put("startTime" , "2016-01-01 08:05:00" );
        spaceTime1.put("endTime" , "2016-02-01 08:05:00" );
        spaceTime1.put("rangeTime" , "2016-01-01 08:05:00 - 2016-02-01 08:05:00" );
        spaceTime1.put("siteSN" , "S0000010" );
        spaceTime1.put("siteName" , "设备10" );
        spaceTimeList.add( spaceTime1 );

        Map<String, Object> spaceTime2 = new HashMap();
        spaceTime2.put("startTime" , "2016-04-01 08:05:00" );
        spaceTime2.put("endTime" , "2016-05-01 08:05:00" );
        spaceTime2.put("rangeTime" , "2016-04-01 08:05:00 - 2016-05-01 08:05:00" );
        spaceTime2.put("siteSN" , "S0000012" );
        spaceTime2.put("siteName" , "设备12" );
        spaceTimeList.add( spaceTime2 );

        Map<String, Object> spaceTime3 = new HashMap();
        spaceTime3.put("startTime" , "2016-07-01 08:05:00" );
        spaceTime3.put("endTime" , "2016-08-01 08:05:00" );
        spaceTime3.put("rangeTime" , "2016-07-01 08:05:00 - 2016-08-01 08:05:00" );
        spaceTime3.put("siteSN" , "S0000013" );
        spaceTime3.put("siteName" , "设备13" );
        spaceTimeList.add( spaceTime3 );

        Map<String, Object> spaceTime4 = new HashMap();
        spaceTime4.put("startTime" , "2016-10-01 08:05:00" );
        spaceTime4.put("endTime" , "2016-12-01 08:05:00" );
        spaceTime4.put("rangeTime" , "2016-10-01 08:05:00 - 2016-12-01 08:05:00" );
        spaceTime4.put("siteSN" , "S0000014" );
        spaceTime4.put("siteName" , "设备14" );
        spaceTimeList.add( spaceTime4 );


        dataCollideTaskInfo.put("spaceTime", spaceTimeList);

        model.put("taskReport", dataCollideTaskInfo);
    }

    public static JSONObject getReportData(
            Integer draw,
            Integer length,
            Integer start
    ){

        JSONObject DataObject = new JSONObject();
        JSONArray  recordArray = new JSONArray();

        DataObject.put("draw", draw);
        DataObject.put("recordsTotal", 50);
        DataObject.put("recordsFiltered", 50);
        DataObject.put("data", recordArray );


        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Integer  loopLength = length;
        Integer imsiValAdd = 0;
        if( ( MAX_RECORD - start) < length ) {
            loopLength = MAX_RECORD - start;
            imsiValAdd = loopLength;
        }


        for( int i=0; i < loopLength; i++ ) {

            JSONObject recordObject = new JSONObject();

            if( ( (loopLength + i) % 5) == 0 ) {
                imsiValAdd ++;
            }

            String imsiVal = String.format("460000000000%02d", imsiValAdd );


            recordObject.put("id", i);
            recordObject.put("imsi", imsiVal);
            recordObject.put("captureTime", dateformat.format( new Date()) );
            recordObject.put("cityName", "上海");
            recordObject.put("operator", "中国移动");
            recordObject.put("numberSection", "138***");
            recordObject.put("siteSN", "S0000010");
            recordObject.put("siteName", "设备10");
            recordObject.put("deviceSN", "D000001");
            recordObject.put("deviceName", "设备10");

            recordArray.add(recordObject);

        }

        return DataObject;
    }

}
