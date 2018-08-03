package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by jiangqi.yang  on 2016/11/12.
 */
public class UITestLoggerData {

    private static Integer MAX_RECORD = 50;

    public static JSONObject queryLoggerData(
            Integer draw,
            Integer length,
            Integer start,
            String startDate, String endDate,
            String  type, String username
    ){
        JSONObject DataObject = new JSONObject();
        JSONArray recordArray = new JSONArray();

        if( ( startDate != null) && ( endDate != null ) &&
                (!startDate.isEmpty()) && (!endDate.isEmpty())   ) {
            DataObject.put("draw", draw);
            DataObject.put("recordsTotal", 50);
            DataObject.put("recordsFiltered", 50);
            DataObject.put("data", recordArray );


            Integer  loopLength = length;
            if( ( MAX_RECORD - start) < length ) {
                loopLength = MAX_RECORD - start;
            }

            for( int i=0; i < loopLength; i++ ) {

                JSONObject recordObject = new JSONObject();

                recordObject.put("id" , i );
                recordObject.put("time" , "2016-11-12 14:22:00" );
                recordObject.put("username" , "杨工" );
                recordObject.put("operator" , "查找告警历史记录" );
                recordObject.put("typeName" , "系统日志" );
                recordObject.put("type" , "04" );

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
