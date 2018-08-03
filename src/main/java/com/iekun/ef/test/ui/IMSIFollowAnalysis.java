package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiangqi.yang on 2016/12/8.
 */
public class IMSIFollowAnalysis {

    public static JSONObject getTasks() {
        JSONObject jsonObject = new JSONObject();
        JSONArray  tasksArray = new JSONArray();

        JSONObject task1 = new JSONObject();
        task1.put("id", 1);
        task1.put("name", "盗窃团伙1");
        task1.put("targetIMSI", "460000000120125");
        task1.put("rangeTime", "2014-12-30 12:30:50 - 2016-12-30 23:23:00");
        task1.put("createTime", "2016-12-08 09:30:00");
        task1.put("taskProcess", 26 );


        JSONObject task2 = new JSONObject();
        task2.put("id", 2);
        task2.put("name", "盗窃团伙2");
        task2.put("targetIMSI", "460000000120156");
        task2.put("rangeTime", "2014-12-30 12:30:50 - 2016-12-30 23:23:00");
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

        Map<String, Object> imsiFollowTaskInfo = new HashMap();

        imsiFollowTaskInfo.put("taskName", "盗窃团伙2");
        imsiFollowTaskInfo.put("taskId", 2);
        imsiFollowTaskInfo.put("rangeTime",  "2014-12-30 12:30:50 -  2016-12-30 23:23:00" );
        imsiFollowTaskInfo.put("targetIMSI", "460000000120156" );
        imsiFollowTaskInfo.put("createTime", "2016-12-08 09:30:00" );

        model.put("taskReport", imsiFollowTaskInfo);
    }

    public static JSONObject getReportData() {

        JSONObject jsonObject = new JSONObject();
        JSONObject  taskObject = new JSONObject();
        JSONArray   dataObject = new JSONArray();

        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for( int i=0; i< 60; i++) {

            JSONObject reportData = new JSONObject();

            String imsiVal = String.format("460000000000%02d", i );
            float rate =   (float)99.9 - (float)i/10 ;

            reportData.put("id", i);
            reportData.put("imsi", imsiVal);
            reportData.put("rate", rate );
//            reportData.put("address", "上海市闵行区元江路中春路路口" );
//            reportData.put("captureTime", dateformat.format(new Date()) );
//            reportData.put("siteName", "站点名称" );
//            reportData.put("deviceName", "设备名称" );

            dataObject.add(reportData);
        }

        taskObject.put("taskId", 2);
        taskObject.put("taskName", "盗窃团伙2");
        taskObject.put("data", dataObject);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", taskObject);

        return jsonObject;
    }

}
