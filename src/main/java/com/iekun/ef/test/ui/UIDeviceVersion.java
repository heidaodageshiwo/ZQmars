package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangqi.yang  on 2016/12/5.
 */
public class UIDeviceVersion {

    private static Integer MAX_RECORD = 50;

    public static JSONObject getLibraries(){

        JSONObject jsonObject  = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        JSONObject version1  = new JSONObject();
        version1.put("id", "1");
        version1.put("version", "2.0.3");
        version1.put("remark", "版本重要升级.1.GPS同步修改;2.RRC修改");
        version1.put("fpgaVersion", "2.0.3");
        version1.put("bbuVersion", "2.0.3");
        version1.put("swVersion", "2.0.3");
        version1.put("uploadUser", "杨工");
        version1.put("uploadTime", "2016-12-04 12:00:00");

        JSONObject version2  = new JSONObject();
        version2.put("id", "2");
        version2.put("version", "1.0.3");
        version2.put("remark", "软件修改");
        version2.put("fpgaVersion", "1.0.3");
        version2.put("bbuVersion", "1.0.3");
        version2.put("swVersion", "1.0.3");
        version2.put("uploadUser", "杨工");
        version2.put("uploadTime", "2016-10-05 12:00:00");

        JSONObject version3  = new JSONObject();
        version3.put("id", "3");
        version3.put("version", "4.0.3");
        version3.put("remark", "重大修改");
        version3.put("fpgaVersion", "4.0.3");
        version3.put("bbuVersion", "4.0.3");
        version3.put("swVersion", "4.0.3");
        version3.put("uploadUser", "杨工");
        version3.put("uploadTime", "2016-12-05 12:00:00");


        jsonArray.add(version1);
        jsonArray.add(version2);
        jsonArray.add(version3);

        jsonObject.put("data", jsonArray);
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");

        return jsonObject;
    }

    public static JSONObject getDevVerList(){

        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONObject devVer1  = new JSONObject();
        devVer1.put("siteName", "汇力得");
        devVer1.put("deviceSN", "D0000000001");
        devVer1.put("deviceName", "DEVICE1");
        devVer1.put("version", "1.0.0");
        devVer1.put("fpgaVersion", "1.0.0");
        devVer1.put("bbuVersion", "1.0.0");
        devVer1.put("swVersion", "1.0.1");

        JSONObject devVer2  = new JSONObject();
        devVer2.put("siteName", "汇力得");
        devVer2.put("deviceSN", "D0000000022");
        devVer2.put("deviceName", "测试设备2");
        devVer2.put("version", "1.2.3");
        devVer2.put("fpgaVersion", "1.2.1");
        devVer2.put("bbuVersion", "1.2.0");
        devVer2.put("swVersion", "1.2.0");


        JSONObject devVer3  = new JSONObject();
        devVer3.put("siteName", "闵浦二桥北");
        devVer3.put("deviceSN", "1313131313131313");
        devVer3.put("deviceName", "测试设备5");
        devVer3.put("version", "3.2.0");
        devVer3.put("fpgaVersion", "3.0.1");
        devVer3.put("bbuVersion", ".3.0.0");
        devVer3.put("swVersion", "3.0.2");


        jsonArray.add(devVer1);
        jsonArray.add(devVer2);
        jsonArray.add(devVer3);

        jsonObject.put("data", jsonArray);
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");

        return jsonObject;
    }

    public static JSONObject  queryHistoryData(
            Integer draw,
            Integer length,
            Integer start,
            String startDate, String endDate,
            String deviceSN, String version,
            String status
    ){

        JSONObject DataObject = new JSONObject();
        JSONArray  recordArray = new JSONArray();

        if( ( startDate != null) && ( endDate != null ) &&
                (!startDate.isEmpty()) && (!endDate.isEmpty())   ) {

            DataObject.put("draw", draw);
            DataObject.put("recordsTotal", 50);
            DataObject.put("recordsFiltered", 50);
            DataObject.put("data", recordArray );

            SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Integer  loopLength = length;
            if( ( MAX_RECORD - start) < length ) {
                loopLength = MAX_RECORD - start;
            }

            for( int i=0; i < loopLength; i++ ) {

                JSONObject recordObject = new JSONObject();

                int statusInd = 0 ;
                if( (i % 10) == 0 ) {
                    statusInd = 1;
                }

                String oldVersion = String.format("1.0.%02d", i );
                String newVersion = String.format("2.0.%02d", i );

                recordObject.put("id", i);
                recordObject.put("siteName", "汇力得");
                recordObject.put("deviceSN", "D0000000003");
                recordObject.put("deviceName", "设备1");
                recordObject.put("oldVersion", oldVersion);
                recordObject.put("newVersion", newVersion);
                recordObject.put("upgradeTime", dateformat.format( new Date()));
                recordObject.put("successTime", dateformat.format( new Date()));
                recordObject.put("status", statusInd );

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
