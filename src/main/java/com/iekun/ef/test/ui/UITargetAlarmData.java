package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangqi.yang  on 2016/11/8.
 */

public class UITargetAlarmData {

    private static Integer MAX_RECORD = 50;

    public static JSONObject getCurrentTargets() {
        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray  = new JSONArray();

        JSONObject targetRuleObject = new JSONObject();
        targetRuleObject.put("id" , 1);
        targetRuleObject.put("ruleName" , "在逃犯");
        targetRuleObject.put("indication" , 1);
        targetRuleObject.put("imsi" , "460000000000000");
        targetRuleObject.put("cityName" , "上海市");
        targetRuleObject.put("operator" , "中国移动");
        targetRuleObject.put("phoneSection" , "139**");
        targetRuleObject.put("siteName" , "闵行站点");
        targetRuleObject.put("deviceName" , "元江4G设备");
        targetRuleObject.put("captureTime" , "2016-11-08 09:45:15");
        targetRuleObject.put("remark" , "危险人员，携带管制工具");
        targetRuleObject.put("isRead" , 0 );

        JSONObject areaRuleObject = new JSONObject();
        areaRuleObject.put("id" , 2);
        areaRuleObject.put("ruleName" , "上访人员");
        areaRuleObject.put("indication" , 2);
        areaRuleObject.put("imsi" , "460000000000001");
        areaRuleObject.put("cityName" , "济南市");
        areaRuleObject.put("operator" , "中国移动");
        areaRuleObject.put("phoneSection" , "135**");
        areaRuleObject.put("siteName" , "闵行站点");
        areaRuleObject.put("deviceName" , "元江4G设备");
        areaRuleObject.put("captureTime" , "2016-11-08 09:45:15");
        areaRuleObject.put("remark" , "");
        areaRuleObject.put("isRead" , 0 );


        jsonArray.add(targetRuleObject);
        jsonArray.add(areaRuleObject);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return jsonObject;
    }


    public static JSONObject  queryHistoryData(
            Integer draw,
            Integer length,
            Integer start,
            String startDate, String endDate,
            String  ruleName, String homeOwnership,
            String siteName,  String deviceName,
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

                int indication = 1 ;
                String ruleNameVal = "在逃犯";
                String cityName="上海市";
                if( (i % 10) == 0 ) {
                    indication = 2;
                    ruleNameVal = "上访人员";
                    cityName="济南市";
                }

                String imsiVal = String.format("460000000000%02d", i );

                recordObject.put("id" , i );
                recordObject.put("ruleName" , ruleNameVal );
                recordObject.put("indication" , indication );
                recordObject.put("imsi" , imsiVal);
                recordObject.put("cityName" , cityName);
                recordObject.put("operator" , "中国移动");
                recordObject.put("phoneSection" , "139**");
                recordObject.put("siteName" , "闵行站点");
                recordObject.put("deviceName" , "元江4G设备");
                recordObject.put("captureTime" , dateformat.format( new Date()) );
                recordObject.put("remark" , "危险人员，携带管制工具");
                recordObject.put("isRead" , 0 );

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

    public  static JSONObject getBlacklist() {
        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray  = new JSONArray();

        JSONObject targetRuleObject1 = new JSONObject();
        JSONArray  target1Receivers  = new JSONArray();

        JSONObject receiver1 = new JSONObject();
        receiver1.put("name", "张队");
        receiver1.put("phone", "13888888888");
        receiver1.put("email", "zhang@iekun.com");

        JSONObject receiver2 = new JSONObject();
        receiver2.put("name", "李队");
        receiver2.put("phone", "13811111111");
        receiver2.put("email", "li@iekun.com");


        targetRuleObject1.put("id" , 1);
        targetRuleObject1.put("name" , "在逃犯");
        targetRuleObject1.put("imsi" , "460000000000000");
        targetRuleObject1.put("createTime" , "2016-11-08 09:45:15");
        targetRuleObject1.put("remark" , "危险人员，携带管制工具");
        targetRuleObject1.put("receivers" , target1Receivers );
        target1Receivers.add(receiver1);
        target1Receivers.add(receiver2);


        JSONObject targetRuleObject2 = new JSONObject();
        targetRuleObject2.put("id" , 2);
        targetRuleObject2.put("name" , "盗窃");
        targetRuleObject2.put("imsi" , "460000000000123");
        targetRuleObject2.put("createTime" , "2016-11-08 09:45:15");
        targetRuleObject2.put("remark" , "");
        targetRuleObject2.put("receivers" , target1Receivers );


        jsonArray.add(targetRuleObject1);
        jsonArray.add(targetRuleObject2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return jsonObject;
    }

    public  static JSONObject getHomeOwnership() {
        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray  = new JSONArray();

        JSONObject areaRuleObject1 = new JSONObject();
        JSONArray  areaReceivers  = new JSONArray();

        JSONObject receiver1 = new JSONObject();
        receiver1.put("name", "张队");
        receiver1.put("phone", "13888888888");
        receiver1.put("email", "zhang@iekun.com");

        JSONObject receiver2 = new JSONObject();
        receiver2.put("name", "李队");
        receiver2.put("phone", "13811111111");
        receiver2.put("email", "li@iekun.com");


        areaRuleObject1.put("id" , 1);
        areaRuleObject1.put("name" , "上海地区来人预警");
        areaRuleObject1.put("cityName" , "上海市");
        areaRuleObject1.put("cityCode" , "021");
        areaRuleObject1.put("createTime" , "2016-11-08 09:45:15");
        areaRuleObject1.put("remark" , "测试");
        areaRuleObject1.put("receivers" , areaReceivers );
        areaReceivers.add(receiver1);
        areaReceivers.add(receiver2);


        JSONObject areaRuleObject2 = new JSONObject();
        areaRuleObject2.put("id" , 2);
        areaRuleObject2.put("name" , "济南地区来人预警");
        areaRuleObject2.put("cityName" , "济南市");
        areaRuleObject2.put("cityCode" , "0531");
        areaRuleObject2.put("createTime" , "2016-11-08 09:45:15");
        areaRuleObject2.put("remark" , "");
        areaRuleObject2.put("receivers" , areaReceivers );

        jsonArray.add(areaRuleObject1);
        jsonArray.add(areaRuleObject2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return jsonObject;
    }

}
