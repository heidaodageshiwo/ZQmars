package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by jiangqi.yang on 2016/11/2.
 */

public class UITestDeviceData {


    public static JSONObject getSites() {

        JSONObject jsonObject  = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        JSONObject site1  = new JSONObject();

        site1.put("id", 1 );
        site1.put("sn", "S00000001" );
        site1.put("name", "闵行站点" );
        site1.put("longitude", 213.02250 );
        site1.put("latitude", 213.02251 );
        site1.put("address", "新源路1356弄" );
        site1.put("remark", "测试站点" );
        site1.put("createTime", "2016-11-2 10:07:00" );
        site1.put("province", "上海" );
        site1.put("provinceId", 1 );
        site1.put("city", "上海市" );
        site1.put("cityId", 3 );
        site1.put("town", "闵行区" );
        site1.put("townId", 6 );

        JSONArray site1Array = new JSONArray();
        JSONObject device1  = new JSONObject();
        device1.put("id", 1 );
        device1.put("sn", "D00000001" );
        device1.put("name", "设备1号" );
        device1.put("type",  5 );
        device1.put("typeText",  "TDD LTE" );
        device1.put("band",  "band38" );
        device1.put("operator", 1 );
        device1.put("operatorText", "中国移动" );
        device1.put("manufacturer", "山东闻远" );
        device1.put("remark", "测试设备" );
        device1.put("createTime", "2016-11-2 10:07:00" );
        site1Array.add(device1);
        JSONObject device2  = new JSONObject();
        device2.put("id", 2 );
        device2.put("sn", "D00000002" );
        device2.put("name", "设备2号" );
        device2.put("type",  6 );
        device2.put("typeText",  "FDD LTE" );
        device2.put("band",  "band2" );
        device2.put("operator", 2 );
        device2.put("operatorText", "中国电信" );
        device2.put("manufacturer", "山东闻远" );
        device2.put("remark", "测试设备" );
        device2.put("createTime", "2016-11-2 10:07:00" );
        site1Array.add(device2);

        site1.put("devices",  site1Array );

        jsonArray.add(site1);

        JSONObject site2  = new JSONObject();
        site2.put("id", 2 );
        site2.put("sn", "S00000002" );
        site2.put("name", "济南站" );
        site2.put("longitude", 413.02250 );
        site2.put("latitude", 513.02251 );
        site2.put("address", "济南舜泰广场8号楼" );
        site2.put("remark", "测试站点" );
        site2.put("createTime", "2016-11-2 11:07:00" );
        site2.put("province", "山东" );
        site2.put("provinceId", 2 );
        site2.put("city", "济南市" );
        site2.put("cityId", 4 );
        site2.put("town", "历下区" );
        site2.put("townId", 8 );

        JSONArray site2Array = new JSONArray();
        JSONObject device3  = new JSONObject();
        device3.put("id", 3 );
        device3.put("sn", "D00000003" );
        device3.put("name", "设备3号" );
        device3.put("type",  5 );
        device3.put("typeText",  "TDD LTE" );
        device3.put("band",  "band39" );
        device3.put("operator", 1 );
        device3.put("operatorText", "中国移动" );
        device3.put("manufacturer", "山东闻远" );
        device3.put("remark", "测试设备" );
        device3.put("createTime", "2016-11-2 10:07:00" );
        site2Array.add(device3);
        JSONObject device4  = new JSONObject();
        device4.put("id", 4 );
        device4.put("sn", "D00000004" );
        device4.put("name", "设备4号" );
        device4.put("type",  6 );
        device4.put("typeText",  "FDD LTE" );
        device4.put("band",  "band1" );
        device4.put("operator", 2 );
        device4.put("operatorText", "中国电信" );
        device4.put("manufacturer", "山东闻远" );
        device4.put("remark", "测试设备" );
        device4.put("createTime", "2016-11-2 10:07:00" );
        site2Array.add(device4);

        site2.put("devices",  site2Array );

        jsonArray.add(site2);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray );

        return jsonObject;
    }

    public static JSONObject getDevices() {

        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONObject device1  = new JSONObject();
        device1.put("id", 1 );
        device1.put("sn", "D00000001" );
        device1.put("name", "设备1号" );
        device1.put("type",  5 );
        device1.put("typeText",  "TDD LTE" );
        device1.put("band",  "band38" );
        device1.put("operator", 1 );
        device1.put("operatorText", "中国移动" );
        device1.put("manufacturer", "山东闻远" );
        device1.put("remark", "测试设备" );
        device1.put("createTime", "2016-11-2 10:07:00" );
        device1.put("siteId", 1 );
        device1.put("siteSn", "S00000001" );
        device1.put("siteName", "闵行站点" );
        device1.put("siteLongitude", 213.02250 );
        device1.put("siteLatitude", 213.02251 );
        device1.put("siteAddress", "新源路1356弄" );
        device1.put("siteRemark", "测试站点" );
        device1.put("siteCreateTime", "2016-11-2 10:07:00" );
        jsonArray.add(device1);


        JSONObject device2  = new JSONObject();
        device2.put("id", 2 );
        device2.put("sn", "D00000002" );
        device2.put("name", "设备2号" );
        device2.put("type",  6 );
        device2.put("typeText",  "FDD LTE" );
        device2.put("band",  "band2" );
        device2.put("operator", 2 );
        device2.put("operatorText", "中国电信" );
        device2.put("manufacturer", "山东闻远" );
        device2.put("remark", "测试设备" );
        device2.put("createTime", "2016-11-2 10:07:00" );
        device2.put("siteId", 1 );
        device2.put("siteSn", "S00000001" );
        device2.put("siteName", "闵行站点" );
        device2.put("siteLongitude", 213.02250 );
        device2.put("siteLatitude", 213.02251 );
        device2.put("siteAddress", "新源路1356弄" );
        device2.put("siteRemark", "测试站点" );
        device2.put("siteCreateTime", "2016-11-2 10:07:00" );
        jsonArray.add(device2);


        JSONObject device3  = new JSONObject();
        device3.put("id", 3 );
        device3.put("sn", "D00000003" );
        device3.put("name", "设备3号" );
        device3.put("type",  5 );
        device3.put("typeText",  "TDD LTE" );
        device3.put("band",  "band39" );
        device3.put("operator", 1 );
        device3.put("operatorText", "中国移动" );
        device3.put("manufacturer", "山东闻远" );
        device3.put("remark", "测试设备" );
        device3.put("createTime", "2016-11-2 10:07:00" );
        device3.put("siteId", 2 );
        device3.put("siteSn", "S00000002" );
        device3.put("siteName", "济南站" );
        device3.put("siteLongitude", 413.02250 );
        device3.put("siteLatitude", 513.02251 );
        device3.put("siteAddress", "济南舜泰广场8号楼" );
        device3.put("siteRemark", "测试站点" );
        device3.put("siteCreateTime", "2016-11-2 11:07:00" );
        jsonArray.add(device3);

        JSONObject device4  = new JSONObject();
        device4.put("id", 4 );
        device4.put("sn", "D00000004" );
        device4.put("name", "设备4号" );
        device4.put("type",  6 );
        device4.put("typeText",  "FDD LTE" );
        device4.put("band",  "band1" );
        device4.put("operator", 2 );
        device4.put("operatorText", "中国电信" );
        device4.put("manufacturer", "山东闻远" );
        device4.put("remark", "测试设备" );
        device4.put("createTime", "2016-11-2 10:07:00" );
        device4.put("siteId", 2 );
        device4.put("siteSn", "S00000002" );
        device4.put("siteName", "济南站" );
        device4.put("siteLongitude", 413.02250 );
        device4.put("siteLatitude", 513.02251 );
        device4.put("siteAddress", "济南舜泰广场8号楼" );
        device4.put("siteRemark", "测试站点" );
        device4.put("siteCreateTime", "2016-11-2 11:07:00" );
        jsonArray.add(device4);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray );

        return jsonObject;
    }

    public static  JSONObject getDevicesTree( Integer userId ){

        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONObject  selStatuObject = new JSONObject();
        selStatuObject.put("checked", true);
//        selStatuObject.put("disabled", true);
//        selStatuObject.put("expanded", true);
//        selStatuObject.put("selected", true);
        JSONObject  unselStatuObject = new JSONObject();
        unselStatuObject.put("checked", false);

        JSONObject  shanghaiObject = new JSONObject();
        JSONArray  shanghaiCitiesObject = new JSONArray();
        shanghaiObject.put("id", 1 );
        shanghaiObject.put("text", "上海" );           //显示文字
        shanghaiObject.put("state", selStatuObject );
        shanghaiObject.put("nodes", shanghaiCitiesObject );  //下级节点

        JSONObject  shanghaiCityObject = new JSONObject();
        JSONArray  shanghaiTownsObject = new JSONArray();
        shanghaiCityObject.put("id", 3 );
        shanghaiCityObject.put("text", "上海市" );
        shanghaiCityObject.put("zipCode", "00000" );
        shanghaiCityObject.put("cityCode", "00000" );
        shanghaiCityObject.put("state", selStatuObject );
        shanghaiCityObject.put("nodes", shanghaiTownsObject );
        shanghaiCitiesObject.add( shanghaiCityObject);

        JSONObject  minghanTownObject = new JSONObject();
        JSONArray   minghanSiteObject = new JSONArray();
        minghanTownObject.put("id", 6 );
        minghanTownObject.put("text", "闵行区" );
        minghanTownObject.put("zipCode", "00000" );
        minghanTownObject.put("cityCode", "00000" );
        minghanTownObject.put("state", selStatuObject );
        minghanTownObject.put("nodes", minghanSiteObject );
        shanghaiTownsObject.add(minghanTownObject);

        JSONObject site1  = new JSONObject();
        site1.put("id", 1 );
        site1.put("sn", "S00000001" );
        site1.put("name", "闵行站点" );
        site1.put("text", "闵行站点" );
        site1.put("longitude", 213.02250 );
        site1.put("latitude", 213.02251 );
        site1.put("address", "新源路1356弄" );
        site1.put("remark", "测试站点" );
        site1.put("createTime", "2016-11-2 10:07:00" );
        site1.put("province", "上海" );
        site1.put("provinceId", 1 );
        site1.put("city", "上海市" );
        site1.put("cityId", 3 );
        site1.put("town", "闵行区" );
        site1.put("townId", 6 );
        site1.put("state", selStatuObject );
        minghanSiteObject.add(site1);


        JSONObject  xuhuiTownObject = new JSONObject();
        xuhuiTownObject.put("id", 5 );
        xuhuiTownObject.put("text", "徐汇区" );
        xuhuiTownObject.put("zipCode", "00000" );
        xuhuiTownObject.put("cityCode", "00000" );
        shanghaiTownsObject.add(xuhuiTownObject);


        JSONObject  shandongObject = new JSONObject();
        JSONArray  shandongCitiesObject = new JSONArray();
        shandongObject.put("id", 2 );
        shandongObject.put("text", "山东省" );
//        shandongObject.put("state", unselStatuObject );
        shandongObject.put("nodes",shandongCitiesObject );

        JSONObject jinanCityObject = new JSONObject();
        JSONArray  jinanTownsObject = new JSONArray();
        jinanCityObject.put("id", 4 );
        jinanCityObject.put("text", "济南市" );
        jinanCityObject.put("zipCode", "00000" );
        jinanCityObject.put("cityCode", "00000" );
        jinanCityObject.put("nodes", jinanTownsObject );
        shandongCitiesObject.add( jinanCityObject);

        JSONObject  lichengTownObject = new JSONObject();

        lichengTownObject.put("id", 7 );
        lichengTownObject.put("text", "历城区" );
        lichengTownObject.put("zipCode", "00000" );
        lichengTownObject.put("cityCode", "00000" );
        jinanTownsObject.add(lichengTownObject);

        JSONObject  lixiaTownObject = new JSONObject();
        JSONArray   lixiaSiteObject = new JSONArray();
        lixiaTownObject.put("id", 8 );
        lixiaTownObject.put("text", "历下区" );
        lixiaTownObject.put("zipCode", "00000" );
        lixiaTownObject.put("cityCode", "00000" );
        lixiaTownObject.put("nodes", lixiaSiteObject );
        jinanTownsObject.add(lixiaTownObject);

        JSONObject site2  = new JSONObject();
        site2.put("id", 2 );
        site2.put("sn", "S00000002" );
        site2.put("name", "济南站" );
        site2.put("text", "济南站" );
        site2.put("longitude", 413.02250 );
        site2.put("latitude", 513.02251 );
        site2.put("address", "济南舜泰广场8号楼" );
        site2.put("remark", "测试站点" );
        site2.put("createTime", "2016-11-2 11:07:00" );
        site2.put("province", "山东" );
        site2.put("provinceId", 2 );
        site2.put("city", "济南市" );
        site2.put("cityId", 4 );
        site2.put("town", "历下区" );
        site2.put("townId", 8 );
        lixiaSiteObject.add(site2);

        jsonArray.add(shanghaiObject);
        jsonArray.add(shandongObject);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return jsonObject;
    }


    public static  JSONObject getDeviceStatusTree( String filter ) {

        String colorRun = "#090";
        String colorOffline = "#999999";
        String colorWillExpire = "#00ccff";
        String colorExpiredFailure = "#ff0000";
        String colorWarning = "#ff851b";


        JSONObject jsonObject  = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONObject  shanghaiObject = new JSONObject();
        JSONArray  shanghaiCitiesObject = new JSONArray();
        shanghaiObject.put("id", 1 );
        shanghaiObject.put("text", "上海" );           //显示文字
        shanghaiObject.put("nodes", shanghaiCitiesObject );  //下级节点

        JSONObject  shanghaiCityObject = new JSONObject();
        JSONArray  shanghaiTownsObject = new JSONArray();
        shanghaiCityObject.put("id", 3 );
        shanghaiCityObject.put("text", "上海市" );
        shanghaiCityObject.put("zipCode", "00000" );
        shanghaiCityObject.put("cityCode", "00000" );
        shanghaiCityObject.put("nodes", shanghaiTownsObject );
        shanghaiCitiesObject.add( shanghaiCityObject);

        JSONObject  minghanTownObject = new JSONObject();
        JSONArray   minghanSiteObject = new JSONArray();
        minghanTownObject.put("id", 6 );
        minghanTownObject.put("text", "闵行区" );
        minghanTownObject.put("zipCode", "00000" );
        minghanTownObject.put("cityCode", "00000" );
        minghanTownObject.put("nodes", minghanSiteObject );
        shanghaiTownsObject.add(minghanTownObject);

        JSONObject site1  = new JSONObject();
        site1.put("id", 1 );
        site1.put("sn", "S00000001" );
        site1.put("name", "闵行站点" );
        site1.put("text", "闵行站点" );
        site1.put("longitude", 213.02250 );
        site1.put("latitude", 213.02251 );
        site1.put("address", "新源路1356弄" );
        site1.put("remark", "测试站点" );
        site1.put("createTime", "2016-11-2 10:07:00" );
        site1.put("province", "上海" );
        site1.put("provinceId", 1 );
        site1.put("city", "上海市" );
        site1.put("cityId", 3 );
        site1.put("town", "闵行区" );
        site1.put("townId", 6 );
        site1.put("nodes", 6 );
        minghanSiteObject.add(site1);


        JSONArray site1Array = new JSONArray();
        if( filter.equals("all") || filter.equals("run")){
            JSONObject device1  = new JSONObject();
            device1.put("id", 1 );                        //树状视图需要字段-用于查询左边视图
            device1.put("sn", "D00000001" );
            device1.put("name", "设备1号" );
            device1.put("text", "D00000001-设备1号" );  //树状视图需要字段
            device1.put("icon", "fa fa-circle" );  //树状视图需要字段
            device1.put("color", colorRun );  //树状视图需要字段
            device1.put("type",  5 );
            device1.put("typeText",  "TDD LTE" );
            device1.put("band",  "band38" );
            device1.put("operator", 1 );
            device1.put("operatorText", "中国移动" );
            device1.put("manufacturer", "山东闻远" );
            device1.put("remark", "测试设备" );
            device1.put("createTime", "2016-11-2 10:07:00" );
            site1Array.add(device1);
        }

        if( filter.equals("all") || filter.equals("offline")) {
            JSONObject device2 = new JSONObject();
            device2.put("id", 2);
            device2.put("sn", "D00000002");
            device2.put("name", "设备2号");
            device2.put("text", "D00000002-设备2号");
            device2.put("icon", "fa fa-circle");
            device2.put("color", colorOffline);
            device2.put("type", 6);
            device2.put("typeText", "FDD LTE");
            device2.put("band", "band2");
            device2.put("operator", 2);
            device2.put("operatorText", "中国电信");
            device2.put("manufacturer", "山东闻远");
            device2.put("remark", "测试设备");
            device2.put("createTime", "2016-11-2 10:07:00");
            site1Array.add(device2);
        }

        site1.put("nodes", site1Array );


        JSONObject  shandongObject = new JSONObject();
        JSONArray  shandongCitiesObject = new JSONArray();
        shandongObject.put("id", 2 );
        shandongObject.put("text", "山东省" );
        shandongObject.put("nodes",shandongCitiesObject );

        JSONObject jinanCityObject = new JSONObject();
        JSONArray  jinanTownsObject = new JSONArray();
        jinanCityObject.put("id", 4 );
        jinanCityObject.put("text", "济南市" );
        jinanCityObject.put("zipCode", "00000" );
        jinanCityObject.put("cityCode", "00000" );
        jinanCityObject.put("nodes", jinanTownsObject );
        shandongCitiesObject.add( jinanCityObject);


        JSONObject  lixiaTownObject = new JSONObject();
        JSONArray   lixiaSiteObject = new JSONArray();
        lixiaTownObject.put("id", 8 );
        lixiaTownObject.put("text", "历下区" );
        lixiaTownObject.put("zipCode", "00000" );
        lixiaTownObject.put("cityCode", "00000" );
        lixiaTownObject.put("nodes", lixiaSiteObject );
        jinanTownsObject.add(lixiaTownObject);

        JSONObject site2  = new JSONObject();
        site2.put("id", 2 );
        site2.put("sn", "S00000002" );
        site2.put("name", "济南站" );
        site2.put("text", "济南站" );
        site2.put("longitude", 413.02250 );
        site2.put("latitude", 513.02251 );
        site2.put("address", "济南舜泰广场8号楼" );
        site2.put("remark", "测试站点" );
        site2.put("createTime", "2016-11-2 11:07:00" );
        site2.put("province", "山东" );
        site2.put("provinceId", 2 );
        site2.put("city", "济南市" );
        site2.put("cityId", 4 );
        site2.put("town", "历下区" );
        site2.put("townId", 8 );
        lixiaSiteObject.add(site2);

        JSONArray site2Array = new JSONArray();

        if( filter.equals("all") || filter.equals("expiredfailure")) {
            JSONObject device3 = new JSONObject();
            device3.put("id", 3);
            device3.put("sn", "D00000003");
            device3.put("name", "设备3号");
            device3.put("text", "D00000003-设备3号");
            device3.put("icon", "fa fa-circle");
            device3.put("color", colorExpiredFailure);
            device3.put("type", 5);
            device3.put("typeText", "TDD LTE");
            device3.put("band", "band39");
            device3.put("operator", 1);
            device3.put("operatorText", "中国移动");
            device3.put("manufacturer", "山东闻远");
            device3.put("remark", "测试设备");
            device3.put("createTime", "2016-11-2 10:07:00");
            site2Array.add(device3);
        }

        if( filter.equals("all") || filter.equals("warning")) {
            JSONObject device4 = new JSONObject();
            device4.put("id", 4);
            device4.put("sn", "D00000004");
            device4.put("name", "设备4号");
            device4.put("text", "D00000004-设备4号");
            device4.put("icon", "fa fa-circle");
            device4.put("color", colorWarning);
            device4.put("type", 6);
            device4.put("typeText", "FDD LTE");
            device4.put("band", "band1");
            device4.put("operator", 2);
            device4.put("operatorText", "中国电信");
            device4.put("manufacturer", "山东闻远");
            device4.put("remark", "测试设备");
            device4.put("createTime", "2016-11-2 10:07:00");
            site2Array.add(device4);
        }

        site2.put("nodes",  site2Array );


        jsonArray.add(shanghaiObject);
        jsonArray.add(shandongObject);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", jsonArray);

        return jsonObject;
    }


    public static JSONObject getDeviceStatusInfo( String sn ){
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONArray  normalArray = new JSONArray();
        JSONArray  boardArray = new JSONArray();
        JSONArray  licenseArray = new JSONArray();
        JSONArray  paArray = new JSONArray();
        JSONArray  snifferArray = new JSONArray();
        JSONArray  debugArray = new JSONArray();

        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //一般信息
        JSONObject updateDataJo = new JSONObject();
        updateDataJo.put("label", "更新时间" );
        updateDataJo.put("value", dateformat.format( new Date() ) );
        normalArray.add( updateDataJo);
        JSONObject lineJo = new JSONObject();
        lineJo.put("label", "状态" );
        lineJo.put("value", "在线" );
        normalArray.add( lineJo);
        JSONObject lineTimeJo = new JSONObject();
        lineTimeJo.put("label", "在线时长" );
        lineTimeJo.put("value", "12天3小时30分钟20秒" );
        normalArray.add( lineTimeJo);
        JSONObject swVersionJo = new JSONObject();
        swVersionJo.put("label", "软件版本" );
        swVersionJo.put("value", "TDD_sniffer_off_WEILAN_Single_ps1156_20161201_1136_V1595" );
        normalArray.add( swVersionJo);
        JSONObject fpgaVersionJo = new JSONObject();
        fpgaVersionJo.put("label", "FPGA版本" );
        fpgaVersionJo.put("value", "1.2.0" );
        normalArray.add( fpgaVersionJo);
        JSONObject bbuVersionJo = new JSONObject();
        bbuVersionJo.put("label", "BBU版本" );
        bbuVersionJo.put("value", "1.2.0" );
        normalArray.add( bbuVersionJo);


        //设备板子信息
        JSONObject tempJo = new JSONObject();
        tempJo.put("label", "板子温度");
        tempJo.put("value", "50 °C");
        boardArray.add(tempJo);
        JSONObject cpuJo = new JSONObject();
        cpuJo.put("label", "CPU负载");
        cpuJo.put("value", "74%");
        boardArray.add(cpuJo);
        JSONObject rfSwJo = new JSONObject();
        rfSwJo.put("label", "射频开关");
        rfSwJo.put("value", "开启");
        boardArray.add(rfSwJo);

        //许可信息
        JSONObject licenceIsJo = new JSONObject();
        licenceIsJo.put("label", "是否过期");
        licenceIsJo.put("value", "否");
        licenseArray.add(licenceIsJo);
        JSONObject licenseTimeJo = new JSONObject();
        licenseTimeJo.put("label", "过期时间");
        licenseTimeJo.put("value", "2020-12-12 12:00:00");
        licenseArray.add(licenseTimeJo);

        //PA信息
        JSONObject validPaJo = new JSONObject();
        validPaJo.put("label", "是否有效");
        validPaJo.put("value", "是");
        paArray.add(validPaJo);
        JSONObject warnPaJo = new JSONObject();
        warnPaJo.put("label", "故障告警");
        warnPaJo.put("value", "否");
        paArray.add(warnPaJo);
        JSONObject warn_standing_wave_ratioPaJo = new JSONObject();
        warn_standing_wave_ratioPaJo.put("label", "驻波比告警");
        warn_standing_wave_ratioPaJo.put("value", "否");
        paArray.add(warn_standing_wave_ratioPaJo);
        JSONObject warn_tempPaJo = new JSONObject();
        warn_tempPaJo.put("label", "过温告警");
        warn_tempPaJo.put("value", "否");
        paArray.add(warn_tempPaJo);
        JSONObject warn_powerPaJo = new JSONObject();
        warn_powerPaJo.put("label", "过功率告警");
        warn_powerPaJo.put("value", "否");
        paArray.add(warn_powerPaJo);
        JSONObject on_off_PaJo = new JSONObject();
        on_off_PaJo.put("label", "功放开关");
        on_off_PaJo.put("value", "开启");
        paArray.add(on_off_PaJo);
        JSONObject inverse_powerPaJo = new JSONObject();
        inverse_powerPaJo.put("label", "反向功率");
        inverse_powerPaJo.put("value", "-10dbm");
        paArray.add(inverse_powerPaJo);
        JSONObject tempPaJo = new JSONObject();
        tempPaJo.put("label", "功放温度");
        tempPaJo.put("value", "60°C");
        paArray.add(tempPaJo);
        JSONObject alcPaJo = new JSONObject();
        alcPaJo.put("label", "ALC");
        alcPaJo.put("value", "5dbm");
        paArray.add(alcPaJo);
        JSONObject standing_wave_ratioPaJo = new JSONObject();
        standing_wave_ratioPaJo.put("label", "驻波比");
        standing_wave_ratioPaJo.put("value", "15.2");
        paArray.add(standing_wave_ratioPaJo);
        JSONObject curr_att_PaJo = new JSONObject();
        curr_att_PaJo.put("label", "功放衰减");
        curr_att_PaJo.put("value", "3db");
        paArray.add(curr_att_PaJo);
        JSONObject forward_powerPaJo = new JSONObject();
        forward_powerPaJo.put("label", "前向功率");
        forward_powerPaJo.put("value", "2");
        paArray.add(forward_powerPaJo);
        JSONObject forward_power2PaJo = new JSONObject();
        forward_power2PaJo.put("label", "前向功率2");
        forward_power2PaJo.put("value", "1");
        paArray.add(forward_power2PaJo);

        JSONObject paCountJo = new JSONObject();
        paCountJo.put("label", "功放个数");
        paCountJo.put("value", "2");
        paArray.add(paCountJo);
//        JSONObject validPaLaJo = new JSONObject();
//        validPaLaJo.put("label", "是否有效");
//        validPaLaJo.put("value", "");
//        paArray.add(validPaLaJo);
        JSONObject powerPaJo = new JSONObject();
        powerPaJo.put("label", "额定输出功率");
        powerPaJo.put("value", "5W");
        paArray.add(powerPaJo);
        JSONObject numPaJo = new JSONObject();
        numPaJo.put("label", "功放序号");
        numPaJo.put("value", "12345678");
        paArray.add(numPaJo);
        JSONObject bandPaJo = new JSONObject();
        bandPaJo.put("label", "频带");
        bandPaJo.put("value", "band1");
        paArray.add(bandPaJo);
        JSONObject en485PaJo = new JSONObject();
        en485PaJo.put("label", "485接口");
        en485PaJo.put("value", "是");
        paArray.add(en485PaJo);
        JSONObject addr485PaJo = new JSONObject();
        addr485PaJo.put("label", "485接口的地址");
        addr485PaJo.put("value", "01010102");
        paArray.add(addr485PaJo);
        JSONObject providerPaJo = new JSONObject();
        providerPaJo.put("label", "供应商");
        providerPaJo.put("value", "ABC");
        paArray.add(providerPaJo);
        JSONObject snPaJo = new JSONObject();
        snPaJo.put("label", "序列号");
        snPaJo.put("value", "12345678");
        paArray.add(snPaJo);
        JSONObject factoryPaJo = new JSONObject();
        factoryPaJo.put("label", "出厂文件");
        factoryPaJo.put("value", "aaaa");
        paArray.add(factoryPaJo);



        //sniffer信息
        JSONObject syncStatusJo = new JSONObject();
        syncStatusJo.put("label","同步状态");
        syncStatusJo.put("value","周期同步成功");
        snifferArray.add(syncStatusJo);
        JSONObject powerJo = new JSONObject();
        powerJo.put("label","同步小区的功率");
        powerJo.put("value","-60dbm");
        snifferArray.add(powerJo);
        JSONObject pciJo = new JSONObject();
        pciJo.put("label","同步小区的PCI");
        pciJo.put("value","-45");
        snifferArray.add(pciJo);
        JSONObject frequencyJo = new JSONObject();
        frequencyJo.put("label","同步小区的频率");
        frequencyJo.put("value","1234564");
        snifferArray.add(frequencyJo);

        //debugger信息
        JSONObject ueNumJo = new JSONObject();
        ueNumJo.put("label", "numberOfConnectedUEs");
        ueNumJo.put("value", 3 );
        debugArray.add(ueNumJo);
        JSONObject isDspHearbeatRecvJo = new JSONObject();
        isDspHearbeatRecvJo.put("label", "isDspHearbeatRecv");
        isDspHearbeatRecvJo.put("value", "是" );
        debugArray.add(isDspHearbeatRecvJo);
        JSONObject rateMsg4PkMsg3Jo = new JSONObject();
        rateMsg4PkMsg3Jo.put("label", "RateMsg4PkMsg3");
        rateMsg4PkMsg3Jo.put("value", 1 );
        debugArray.add(rateMsg4PkMsg3Jo);
        JSONObject rateMsg5PkMsg4Jo = new JSONObject();
        rateMsg5PkMsg4Jo.put("label", "RateMsg5PkMsg4");
        rateMsg5PkMsg4Jo.put("value", 2 );
        debugArray.add(rateMsg5PkMsg4Jo);
        JSONObject countRACHJo = new JSONObject();
        countRACHJo.put("label", "count_RACH");
        countRACHJo.put("value", 3 );
        debugArray.add(countRACHJo);
        JSONObject countSchelMSG3Jo = new JSONObject();
        countSchelMSG3Jo.put("label", "count_schel_MSG3");
        countSchelMSG3Jo.put("value", 4 );
        debugArray.add(countSchelMSG3Jo);
        JSONObject countMSG3SuccessJo = new JSONObject();
        countMSG3SuccessJo.put("label", "count_MSG3_success");
        countMSG3SuccessJo.put("value", 5 );
        debugArray.add(countMSG3SuccessJo);
        JSONObject countMSG4 = new JSONObject();
        countMSG4.put("label", "count_MSG4");
        countMSG4.put("value", 6 );
        debugArray.add(countMSG4);
        JSONObject countMSG5Jo = new JSONObject();
        countMSG5Jo.put("label", "count_MSG5");
        countMSG5Jo.put("value", 7 );
        debugArray.add(countMSG5Jo);
        JSONObject countUEL3Jo = new JSONObject();
        countUEL3Jo.put("label", "count_UE_L3");
        countUEL3Jo.put("value", 8 );
        debugArray.add(countUEL3Jo);


        dataObject.put("normal", normalArray);
        dataObject.put("board",boardArray );
        dataObject.put("license", licenseArray);
        dataObject.put("pa",paArray );
        dataObject.put("sniffer",snifferArray );
        dataObject.put("debug", debugArray);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return  jsonObject;
    }


    public static JSONObject getSiteStatusInfo( String sn ){

        JSONObject jsonObject = new JSONObject();

        JSONObject dataObject = new JSONObject();
        JSONObject deviceObject = new JSONObject();
        JSONArray  deviceStatus = new JSONArray();
        JSONArray  deviceArray  = new JSONArray();

        SimpleDateFormat dateformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //一般信息
        JSONObject updateDataJo = new JSONObject();
        updateDataJo.put("label", "更新时间" );
        updateDataJo.put("value", dateformat.format( new Date() ) );
        deviceStatus.add( updateDataJo);
        JSONObject lineJo = new JSONObject();
        lineJo.put("label", "状态" );
        lineJo.put("value", "在线" );
        deviceStatus.add( lineJo);
        JSONObject lineTimeJo = new JSONObject();
        lineTimeJo.put("label", "在线时长" );
        lineTimeJo.put("value", "12天3小时30分钟20秒" );
        deviceStatus.add( lineTimeJo);
        JSONObject swVersionJo = new JSONObject();
        swVersionJo.put("label", "软件版本" );
        swVersionJo.put("value", "1.2.0" );
        deviceStatus.add( swVersionJo);
        JSONObject fpgaVersionJo = new JSONObject();
        fpgaVersionJo.put("label", "FPGA版本" );
        fpgaVersionJo.put("value", "1.2.0" );
        deviceStatus.add( fpgaVersionJo);
        JSONObject bbuVersionJo = new JSONObject();
        bbuVersionJo.put("label", "BBU版本" );
        bbuVersionJo.put("value", "1.2.0" );
        deviceStatus.add( bbuVersionJo);


        //设备板子信息
        JSONObject tempJo = new JSONObject();
        tempJo.put("label", "板子温度");
        tempJo.put("value", "50 °C");
        deviceStatus.add(tempJo);
        JSONObject cpuJo = new JSONObject();
        cpuJo.put("label", "CPU负载");
        cpuJo.put("value", "74%");
        deviceStatus.add(cpuJo);
        JSONObject rfSwJo = new JSONObject();
        rfSwJo.put("label", "射频开关");
        rfSwJo.put("value", "开启");
        deviceStatus.add(rfSwJo);

        //许可信息
        JSONObject licenceIsJo = new JSONObject();
        licenceIsJo.put("label", "是否过期");
        licenceIsJo.put("value", "否");
        deviceStatus.add(licenceIsJo);
        JSONObject licenseTimeJo = new JSONObject();
        licenseTimeJo.put("label", "过期时间");
        licenseTimeJo.put("value", "2020-12-12 12:00:00");
        deviceStatus.add(licenseTimeJo);

        //sniffer信息
        JSONObject syncStatusJo = new JSONObject();
        syncStatusJo.put("label","同步状态");
        syncStatusJo.put("value","周期同步成功");
        deviceStatus.add(syncStatusJo);

        deviceObject.put("id", "1");
        deviceObject.put("sn", "D0000001");
        deviceObject.put("name", "TestDevice1");
        deviceObject.put("status", deviceStatus );
        deviceArray.add(deviceObject);

        dataObject.put("id", "3");
        dataObject.put("sn", "S0000001");
        dataObject.put("name", "汇力得");
//        dataObject.put("status", );
        dataObject.put("devices", deviceArray);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return  jsonObject;
    }

}
