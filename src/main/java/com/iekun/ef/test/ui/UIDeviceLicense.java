package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangqi.yang  on 2016/12/7.
 */
public class UIDeviceLicense {

    private static Integer MAX_RECORD = 50;


    public static JSONObject getDevLicenses() {

        JSONObject jsonObject  = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        JSONObject license1  = new JSONObject();
//        license1.put("id", "1");
        license1.put("siteName", "汇力得");
        license1.put("deviceSN", "D0000000001");
        license1.put("deviceName", "DEVICE1");
        license1.put("isLicense",  0 ); //0-没过期，1-过期
        license1.put("expireTime", "2018-10-05 12:00:00");

        JSONObject license2  = new JSONObject();
//        license2.put("id", "2");
        license2.put("siteName", "汇力得");
        license2.put("deviceSN", "D0000000022");
        license2.put("deviceName", "测试设备2");
        license2.put("isLicense",  0 ); //0-没过期，1-过期
        license2.put("expireTime", "2016-10-05 12:00:00");

        JSONObject license3  = new JSONObject();
//        license3.put("id", "3");
        license3.put("siteName", "闵浦二桥北");
        license3.put("deviceSN", "1313131313131313");
        license3.put("deviceName", "测试设备5");
        license3.put("isLicense",  1 ); //0-没过期，1-过期
        license3.put("expireTime", "2018-10-05 12:00:00");

        jsonArray.add(license1);
        jsonArray.add(license2);
        jsonArray.add(license3);

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
            String deviceSN, String status
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

                String oldExpireTime = String.format("20%02d-12-30 12:00:00", i );
                String newExpireTime = String.format("20%02d-12-30 12:00:00", i+20);


                recordObject.put("id", i);
                recordObject.put("siteName", "汇力得");
                recordObject.put("deviceSN", "D0000000003");
                recordObject.put("deviceName", "设备1");
                recordObject.put("updateTime", dateformat.format( new Date()));
                recordObject.put("bExpireTime", oldExpireTime );
                if( 0 == statusInd ) {
                    recordObject.put("aExpireTime", newExpireTime );
                } else {
                    recordObject.put("aExpireTime", "");
                }
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
