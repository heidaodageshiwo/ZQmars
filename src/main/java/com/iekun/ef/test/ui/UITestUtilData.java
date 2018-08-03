package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by  jiangqi.yang on 2016/11/2.
 */
public class UITestUtilData {

    public static JSONObject getProvinces() {
        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONObject province1  = new JSONObject();
        province1.put("id", "1");
        province1.put("text", "上海");

        JSONObject province2  = new JSONObject();
        province2.put("id", "2");
        province2.put("text", "山东");

        jsonArray.add(province1);
        jsonArray.add(province2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return  jsonObject;
    }

    public static JSONObject getCities( Integer provincesId ){
        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject city1  = new JSONObject();

        if( 1 == provincesId ){
            city1.put("id", "3");
            city1.put("text", "上海市");
        } else {
            city1.put("id", "4");
            city1.put("text", "济南市");
        }

       jsonArray.add(city1);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return  jsonObject;
    }

    public static JSONObject getTowns( Integer cityId ) {
        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject towns1  = new JSONObject();
        JSONObject towns2  = new JSONObject();

        if( 3 == cityId ) {
            towns1.put("id", "5");
            towns1.put("text", "徐汇区");
            towns2.put("id", "6");
            towns2.put("text", "闵行区");
        } else {
            towns1.put("id", "7");
            towns1.put("text", "历城区");
            towns2.put("id", "8");
            towns2.put("text", "历下区");
        }

        jsonArray.add(towns1);
        jsonArray.add(towns2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return  jsonObject;
    }

    public static JSONObject getCityCodes(){

        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject city1  = new JSONObject();
        JSONObject city2  = new JSONObject();

        city1.put("cityCode", "021");
        city1.put("cityName", "上海市");

        city2.put("cityCode", "0531");
        city2.put("cityName", "济南市");

        jsonArray.add(city1);
        jsonArray.add(city2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return  jsonObject;
    }

    public static JSONObject getAreas(){

        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject area1  = new JSONObject();
        JSONObject area2  = new JSONObject();
        JSONObject area3  = new JSONObject();
        JSONObject area4  = new JSONObject();

        area1.put("areaId", "100000");
        area1.put("areaName", "中国");

        area2.put("areaId", "310000");
        area2.put("areaName", "中国,上海");

        area3.put("areaId", "310104");
        area3.put("areaName", "中国,上海,上海市,徐汇区");

        area4.put("areaId", "310112");
        area4.put("areaName", "中国,上海,上海市,闵行区");

        jsonArray.add(area1);
        jsonArray.add(area2);
        jsonArray.add(area3);
        jsonArray.add(area4);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return  jsonObject;
    }
}
