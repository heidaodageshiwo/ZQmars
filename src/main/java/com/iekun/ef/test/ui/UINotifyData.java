package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangqi.yang on 2016/11/21.
 */
public class UINotifyData {

    private static Integer MAX_RECORD = 50;

    public static JSONObject queryHistorySms(
            Integer draw,
            Integer length,
            Integer start,
            String startDate, String endDate,
            String notifyName,  String notifyPhone
    ){

        JSONObject DataObject = new JSONObject();
        JSONArray recordArray = new JSONArray();

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

                recordObject.put("id", i);
                recordObject.put("notifyName", "林队");
                recordObject.put("notifyPhone", "13888888888");
                recordObject.put("eventType", 0 );
                recordObject.put("content", "目标人物在xxx出现");
                recordObject.put("lastSendTime", dateformat.format( new Date()));

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

    public static JSONObject getSmsTemplate() {
        JSONObject jsonObject = new JSONObject();
        String smsTemplate = "尊敬的{%user%}, 在{%time%}时间目标{%target%}出现于{%location%}位置";
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", smsTemplate );
        return jsonObject;
    }

    public static JSONObject getNotifications(
            Integer isRead,
            Integer draw,
            Integer length,
            Integer start
    ){
        JSONObject jsonObject = new JSONObject();
        JSONArray recordArray = new JSONArray();

        int nMaxTotal = 0;

        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if( 0 == isRead ) { //未读
            nMaxTotal = 30;
        } else if ( 1 == isRead ) { //已读
            nMaxTotal = 60;
        }

        jsonObject.put("draw", draw);
        jsonObject.put("recordsTotal", nMaxTotal);
        jsonObject.put("recordsFiltered", nMaxTotal);
        jsonObject.put("data", recordArray );

        Integer  loopLength = length;
        if( ( nMaxTotal - start) < length ) {
            loopLength = nMaxTotal - start;
        }

        for( int i=0; i < loopLength; i++ ) {

            JSONObject recordObject = new JSONObject();

            recordObject.put("id", i);
            recordObject.put("message", "鹅，鹅，鹅，曲项向天歌。白毛浮绿水，红掌拨清波。");
            recordObject.put("isRead", isRead );
            recordObject.put("createTime", dateformat.format( new Date()));

            recordArray.add(recordObject);

        }


        return  jsonObject;
    }

}
