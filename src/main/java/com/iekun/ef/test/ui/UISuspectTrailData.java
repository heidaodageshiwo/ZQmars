package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by jiangqi.yang on 2016/11/23.
 */

public class UISuspectTrailData {

    public static JSONObject getSuspectTrailTasks(){
        JSONObject jsonObject = new JSONObject();
        JSONArray  dataObject = new JSONArray();

        JSONObject task1 = new JSONObject();
        task1.put("id", 1);
        task1.put("name", "嫌疑人1轨迹");
        task1.put("taskProcess", 30);
        task1.put("createTime", "2016-11-23 10:17:45");
        task1.put("rangeTime", "2016-10-23 10:17:45 - 2016-11-23 10:17:45");
        task1.put("imsi", "460123456789451");

        JSONObject task2 = new JSONObject();
        task2.put("id", 2);
        task2.put("name", "嫌疑人2轨迹");
        task2.put("taskProcess", 100);
        task2.put("createTime", "2016-11-23 10:17:45");
        task2.put("rangeTime", "2016-10-23 10:17:45 - 2016-11-23 10:17:45");
        task2.put("imsi", "460888888888888");

        dataObject.add(task1);
        dataObject.add(task2);

        jsonObject.put("status", true );
        jsonObject.put("message", "成功" );
        jsonObject.put("data", dataObject );

        return  jsonObject;
    }

    public static JSONObject getSuspectTrailReportData() {
        JSONObject jsonObject = new JSONObject();
        JSONObject  taskObject = new JSONObject();
        JSONArray  dataObject = new JSONArray();

//        for( int i=0; i< 50; i++ ) {
//            JSONObject reportData1 = new JSONObject();
//            reportData1.put("siteSN", "S0000004");
//            reportData1.put("siteName", "闵浦二桥北");
//            reportData1.put("longitude", "121.43355");
//            reportData1.put("latitude", "31.004778");
//            reportData1.put("time", "2016-10-10 14:30:00");
//
//            JSONObject reportData2 = new JSONObject();
//            reportData2.put("siteSN", "S0000001");
//            reportData2.put("siteName", "汇力得");
//            reportData2.put("longitude", "121.4047");
//            reportData2.put("latitude", "31.054104");
//            reportData2.put("time", "2016-10-10 15:30:00");
//
//
//            JSONObject reportData3 = new JSONObject();
//            reportData3.put("siteSN", "S000002");
//            reportData3.put("siteName", "中春路银都路");
//            reportData3.put("longitude", "121.384125");
//            reportData3.put("latitude", "31.091675");
//            reportData3.put("time", "2016-10-10 16:30:00");
//
//            JSONObject reportData4 = new JSONObject();
//            reportData4.put("siteSN", "S000003");
//            reportData4.put("siteName", "莘松路沪闵路");
//            reportData4.put("longitude", "121.386154");
//            reportData4.put("latitude", "31.115175");
//            reportData4.put("time", "2016-10-10 17:30:00");
//
//
//            dataObject.add(reportData1);
//            dataObject.add(reportData2);
//            dataObject.add(reportData3);
//            dataObject.add(reportData4);
//        }

        JSONObject reportData1 = new JSONObject();
        reportData1.put("siteSN", "S0000004");
        reportData1.put("siteName", "闵浦二桥北");
        reportData1.put("longitude", "121.43355");
        reportData1.put("latitude", "31.004778");
        reportData1.put("time", "2016-10-10 14:30:00");

        JSONObject reportData2 = new JSONObject();
        reportData2.put("siteSN", "S0000001");
        reportData2.put("siteName", "汇力得");
        reportData2.put("longitude", "121.4047");
        reportData2.put("latitude", "31.054104");
        reportData2.put("time", "2016-10-10 15:30:00");


        JSONObject reportData3 = new JSONObject();
        reportData3.put("siteSN", "S000002");
        reportData3.put("siteName", "中春路银都路");
        reportData3.put("longitude", "121.384125");
        reportData3.put("latitude", "31.091675");
        reportData3.put("time", "2016-10-10 16:30:00");

        JSONObject reportData4 = new JSONObject();
        reportData4.put("siteSN", "S000003");
        reportData4.put("siteName", "莘松路沪闵路");
        reportData4.put("longitude", "121.386154");
        reportData4.put("latitude", "31.115175");
        reportData4.put("time", "2016-10-10 17:30:00");


        dataObject.add(reportData1);
        dataObject.add(reportData2);
        dataObject.add(reportData3);
        dataObject.add(reportData4);

        taskObject.put("taskId", 2);
        taskObject.put("taskName", "嫌疑人2轨迹");
        taskObject.put("data", dataObject);

        jsonObject.put("status", true );
        jsonObject.put("message", "成功" );
        jsonObject.put("data", taskObject );

        return  jsonObject;
    }
}
