package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by  jiangqi.yang on 2016/12/28.
 */
public class EmailTestData {

    private static Integer MAX_RECORD = 50;

    public static JSONObject queryHistoryEmail(
            Integer draw,
            Integer length,
            Integer start,
            String startTime, String endTime,
            String sender
    ){

        JSONObject DataObject = new JSONObject();
        JSONArray recordArray = new JSONArray();

        if( ( startTime != null) && ( endTime != null ) &&
                (!startTime.isEmpty()) && (!endTime.isEmpty())   ) {

            DataObject.put("draw", draw);
            DataObject.put("recordsTotal", 50);
            DataObject.put("recordsFiltered", 50);
            DataObject.put("data", recordArray );


            SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Integer  loopLength = length;
            if( ( MAX_RECORD - start) < length ) {
                loopLength = MAX_RECORD - start;
            }


            Integer  loopMaxLen = start + length;
            if( loopMaxLen > MAX_RECORD ) {
                loopMaxLen = MAX_RECORD;
            }


            for( int i=start; i < loopMaxLen; i++ ) {

                JSONObject recordObject = new JSONObject();

                String historySender =  String.format("email%02dsender@iekun.com", i );
                String subject =  String.format("用户U0000%02d,重置密码", i );

                recordObject.put("id", i);
                recordObject.put("sender", historySender);
                recordObject.put("subject", subject);
                recordObject.put("sendTime", dateformat.format( new Date()));

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

    public static JSONObject getEmailDetail() {
        JSONObject jsonObject = new JSONObject();
        JSONObject emailObject = new JSONObject();

        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        emailObject.put("id", 10 );
        emailObject.put("subject", "密码重置" );
        emailObject.put("sender", "support@iekun.com" );
        emailObject.put("receiver", "jiangqi.yang@iekun.com" );
        emailObject.put("body",
                "<html><p>亲,</p><br>" +
                "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的密码已经重置为<strong>mmczs1234</strong>。</p>" +
                "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了安全，请登陆后进行修改。</p>" +
                "<p>谢谢,<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Efence团队</p>" +
                "<p>(本消息为系统自动发送，请不要回复本邮件)</p></html>" );
        emailObject.put("sendTime", dateformat.format( new Date()) );

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", emailObject);

        return  jsonObject;
    }

    public static JSONObject getEmailTpls(){

        JSONObject jsonObject = new JSONObject();
        JSONArray dataArray = new JSONArray();

        JSONObject emailTmp1 = new JSONObject();
        emailTmp1.put("id", 1);
        emailTmp1.put("eventType", 3);
        emailTmp1.put("eventTypeText", "密码重置");
        emailTmp1.put("active", 0);
        emailTmp1.put("subject", "密码重置");
        emailTmp1.put("content", "");
        emailTmp1.put("remark", "找回密码");
        emailTmp1.put("createTime", "2016-12-24 12:22:00");
        dataArray.add(emailTmp1);

        JSONObject emailTmp2 = new JSONObject();
        emailTmp2.put("id", 2);
        emailTmp2.put("eventType", 2);
        emailTmp2.put("eventTypeText", "设备告警");
        emailTmp2.put("active", 1);
        emailTmp2.put("subject", "当前设备状态告警");
        emailTmp2.put("content", "");
        emailTmp2.put("remark", "设备故障时候邮件提示");
        emailTmp2.put("createTime", "2016-12-24 12:22:00");
        dataArray.add(emailTmp2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataArray);

        return jsonObject;
    }

}
