package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangqi.yang on 2016/11/12.
 */

public class UITestDeviceAlarmData {

    public static JSONObject getCurrentAlarms() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray  = new JSONArray();

        JSONObject alarm1 = new JSONObject();
        alarm1.put("id", 1);
        alarm1.put("code", "01");
        alarm1.put("info", "lmk04808:cannot lock");
        alarm1.put("time", "2016-11-10 13:14:00");
        alarm1.put("deviceSN", " D00000004");
        alarm1.put("deviceName", "设备4号");
        alarm1.put("siteSN", "S0000002");
        alarm1.put("siteName", "济南站");

        JSONObject alarm2 = new JSONObject();
        alarm2.put("id", 1);
        alarm2.put("code", "10");
        alarm2.put("info", "GPS:not syn");
        alarm2.put("time", "2016-11-12 13:14:00");
        alarm2.put("deviceSN", " D00000004");
        alarm2.put("deviceName", "设备4号");
        alarm2.put("siteSN", "S0000002");
        alarm2.put("siteName", "济南站");

        JSONObject alarm3 = new JSONObject();
        alarm3.put("id", 1);
        alarm3.put("code", "62");
        alarm3.put("info", "PA:get status failed");
        alarm3.put("time", "2016-11-13 13:14:00");
        alarm3.put("deviceSN", " D00000004");
        alarm3.put("deviceName", "设备4号");
        alarm3.put("siteSN", "S0000002");
        alarm3.put("siteName", "济南站");

        jsonArray.add(alarm1);
        jsonArray.add(alarm2);
        jsonArray.add(alarm3);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return jsonObject;
    }

    private static Integer MAX_RECORD = 50;

    public static JSONObject  queryHistoryData(
            Integer draw,
            Integer length,
            Integer start,
            String startDate, String endDate,
            String  deviceSN, String deviceName,
            String alarmCode
    ){

        JSONObject DataObject = new JSONObject();
        JSONArray  recordArray = new JSONArray();

        if( ( startDate != null) && ( endDate != null ) &&
                (!startDate.isEmpty()) && (!endDate.isEmpty())   ) {
            DataObject.put("draw", draw);
            DataObject.put("recordsTotal", 50);
            DataObject.put("recordsFiltered", 50);
            DataObject.put("data", recordArray );


            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            Integer  loopLength = length;
            if( ( MAX_RECORD - start) < length ) {
                loopLength = MAX_RECORD - start;
            }

            for( int i=0; i < loopLength; i++ ) {

                JSONObject recordObject = new JSONObject();

                recordObject.put("id", i );
                recordObject.put("code", "10");
                recordObject.put("info", "GPS:not syn");
                recordObject.put("time", dateformat.format( new Date()) );
                recordObject.put("deviceSN", " D00000004");
                recordObject.put("deviceName", "设备4号");
                recordObject.put("siteSN", "S0000002");
                recordObject.put("siteName", "济南站");

                recordArray.add(recordObject);

            }

        } else {

            DataObject.put("draw", draw);
            DataObject.put("recordsTotal", 0);
            DataObject.put("recordsFiltered", 0);
            DataObject.put("data", recordArray);

        }

        return DataObject;
    }

}
