package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangqi.yang on 2016/11/5.
 */
public class UIHistoryData {

    private static Integer MAX_RECORD = 50;

    public static JSONObject  queryHistoryData(
            Integer draw,
            Integer length,
            Integer start,
            String startDate, String endDate,
            Integer operator, String homeOwnership,
            String siteSN,  String deviceSN,
            String imsi
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

                int indication = 0 ;
                if( (i % 10) == 0 ) {
                    indication = 1;
                }

                String imsiVal = String.format("460000000000%02d", i );

                recordObject.put("id", i);
                recordObject.put("imsi", imsiVal);
                recordObject.put("imei", "" );
                recordObject.put("stmsi", "");
                recordObject.put("mac",  "");
                recordObject.put("latype", "" );
                recordObject.put("indication", indication );
                recordObject.put("realtime", "");
                recordObject.put("captureTime", dateformat.format( new Date()) );
                recordObject.put("cityName", "上海");
                recordObject.put("band", "band1");
                recordObject.put("operator", "中国移动");
                recordObject.put("numberSection", "138***");
                recordObject.put("siteSN", "S0000001");
                recordObject.put("siteName", "闵行站点");
                recordObject.put("deviceSN", "D000001");
                recordObject.put("deviceName", "元江路设备");

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
