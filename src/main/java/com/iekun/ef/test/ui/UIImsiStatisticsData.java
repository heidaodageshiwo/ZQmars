package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by jiangqi.yang  on 2016/11/24.
 */
public class UIImsiStatisticsData {

    public static JSONObject getIMSIStatisticsTasks() {

        JSONObject jsonObject = new JSONObject();
        JSONArray dataObject = new JSONArray();

        JSONObject task1 = new JSONObject();
        JSONArray  task1Items = new JSONArray();
        task1.put("id", 1);
        task1.put("name", "上海1统计");
        task1.put("taskProcess", 50);
        task1.put("typeText", "站点");
        task1.put("type", 1);
        task1.put("unitText", "小时");
        task1.put("unit", 2);
        task1.put("rangeTime", "2016-10-22 01:30:22 - 2016-10-23 12:30:22");
        task1.put("createTime", "2016-10-23 14:30:22");
        JSONObject item1 = new JSONObject();
        item1.put("id", 11);
        item1.put("sn", "S000006");
        item1.put("name", "徐汇站点");
        JSONObject item2 = new JSONObject();
        item2.put("id", 11);
        item2.put("sn", "S000007");
        item2.put("name", "闵行站点");
        task1Items.add(item1);
        task1Items.add(item2);
        task1.put("items", task1Items);


        JSONObject task2 = new JSONObject();
        JSONArray  task2Items = new JSONArray();
        task2.put("id", 2);
        task2.put("name", "上海2统计");
        task2.put("taskProcess", 100);
        task2.put("type", "1");
        task2.put("typeText", "站点");
        task2.put("unitText", "小时");
        task2.put("unit", "2");
        task2.put("rangeTime", "2016-10-22 01:30:22 - 2016-10-23 12:30:22");
        task2.put("createTime", "2016-10-23 14:30:22");
        JSONObject item4 = new JSONObject();
        item4.put("id", 15);
        item4.put("sn", "S0000016");
        item4.put("name", "浦东站点");
        JSONObject item5 = new JSONObject();
        item5.put("id", 16);
        item5.put("sn", "S0000017");
        item5.put("name", "杨浦站点");
        task2Items.add(item4);
        task2Items.add(item5);
        task2.put("items", task2Items);

        dataObject.add(task1);
        dataObject.add(task2);
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    public static JSONObject getImsiStatisticsReportData(){
        JSONObject jsonObject = new JSONObject();
        JSONObject taskObject = new JSONObject();
        JSONObject dataObject = new JSONObject();

        JSONArray colorsArray = new JSONArray();
        JSONArray yKeysArray = new JSONArray();
        JSONArray labelsArray = new JSONArray();
        JSONArray statisticDataArray = new JSONArray();

        colorsArray.add("#00a65a");
        colorsArray.add("#f56954");

        yKeysArray.add("site1");
        yKeysArray.add("site2");

        labelsArray.add("浦东站点");
        labelsArray.add("杨浦站点");

        JSONObject data1 = new JSONObject();
        data1.put("time", "2016-10-25 12:20:00");
        data1.put("site1", 100);
        data1.put("site2", 90);

        JSONObject data2 = new JSONObject();
        data2.put("time", "2016-10-25 13:20:00");
        data2.put("site1", 82);
        data2.put("site2", 15);

        statisticDataArray.add(data1);
        statisticDataArray.add(data2);

        dataObject.put("colors", colorsArray);
        dataObject.put("xkey", "time");
        dataObject.put("ykeys", yKeysArray);
        dataObject.put("labels", labelsArray );
        dataObject.put("data", statisticDataArray );

        taskObject.put("taskId", 2);
        taskObject.put("taskName", "上海2统计");
        taskObject.put("data", dataObject);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", taskObject);

        return jsonObject;
    }
}
