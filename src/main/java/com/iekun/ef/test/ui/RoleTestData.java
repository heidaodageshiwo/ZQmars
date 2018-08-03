package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.secure.internal.JaccSecurityListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangqi.yang  on 2017/1/31.
 */

public class RoleTestData {

    public static Map<String, Object> getResourceInfo(Map<String, Object> model) {

        List moduleList = new ArrayList();

        /*概览*/
        Map<String, Object>  module1 = new HashMap();
        List resource1List = new  ArrayList();
        Map<String, Object> homeResMap = new HashMap();
        homeResMap.put("displayName","总控台");
        homeResMap.put("value","1");
        Map<String, Object> mapResMap = new HashMap();
        mapResMap.put("displayName","设备分布图");
        mapResMap.put("value","2");
        resource1List.add(homeResMap);
        resource1List.add(mapResMap);
        module1.put("diaplayName", "概览");
        module1.put("resources", resource1List);
        moduleList.add(module1);

        /*数据采集*/
        Map<String, Object>  module2 = new HashMap();
        List resource2List = new  ArrayList();
        Map<String, Object> realTimeDataResMap = new HashMap();
        realTimeDataResMap.put("displayName","实时数据");
        realTimeDataResMap.put("value","4");
        Map<String, Object> historyDataResMap = new HashMap();
        historyDataResMap.put("displayName","历史数据");
        historyDataResMap.put("value","5");
        resource2List.add(realTimeDataResMap);
        resource2List.add(historyDataResMap);
        module2.put("diaplayName", "数据采集");
        module2.put("resources", resource2List);
        moduleList.add(module2);

        /*数据分析*/
        Map<String, Object>  module3 = new HashMap();
        List resource3List = new  ArrayList();
        Map<String, Object> imsiStatisticsResMap = new HashMap();
        imsiStatisticsResMap.put("displayName","上号统计");
        imsiStatisticsResMap.put("value","7");
        Map<String, Object> dataCollideResMap = new HashMap();
        dataCollideResMap.put("displayName","数据碰撞分析");
        dataCollideResMap.put("value","8");
        Map<String, Object> residentPeopleResMap = new HashMap();
        residentPeopleResMap.put("displayName","常驻人口外来人口分析");
        residentPeopleResMap.put("value","9");
        Map<String, Object> imsiFollowResMap = new HashMap();
        imsiFollowResMap.put("displayName","IMSI伴随分析");
        imsiFollowResMap.put("value","10");
        Map<String, Object> suspectTrailResMap = new HashMap();
        suspectTrailResMap.put("displayName","嫌疑人运动轨迹");
        suspectTrailResMap.put("value","11");
        resource3List.add(imsiStatisticsResMap);
        resource3List.add(dataCollideResMap);
        resource3List.add(residentPeopleResMap);
        resource3List.add(imsiFollowResMap);
        resource3List.add(suspectTrailResMap);
        module3.put("diaplayName", "数据分析");
        module3.put("resources", resource3List);
        moduleList.add(module3);

        /*预警*/
        Map<String, Object>  module4 = new HashMap();
        List resource4List = new  ArrayList();
        Map<String, Object> currentTargetResMap = new HashMap();
        currentTargetResMap.put("displayName","当前预警");
        currentTargetResMap.put("value","13");
        Map<String, Object> historyTargetResMap = new HashMap();
        historyTargetResMap.put("displayName","历史预警");
        historyTargetResMap.put("value","14");
        Map<String, Object> blacklistResMap = new HashMap();
        blacklistResMap.put("displayName","黑名单管理");
        blacklistResMap.put("value","15");
        Map<String, Object> homeOwnershipResMap = new HashMap();
        homeOwnershipResMap.put("displayName","归属地预警管理");
        homeOwnershipResMap.put("value","16");
        resource4List.add(currentTargetResMap);
        resource4List.add(historyTargetResMap);
        resource4List.add(blacklistResMap);
        resource4List.add(homeOwnershipResMap);
        module4.put("diaplayName", "预警");
        module4.put("resources", resource4List);
        moduleList.add(module4);

        /*用户管理*/
        Map<String, Object>  module5 = new HashMap();
        List resource5List = new  ArrayList();
        Map<String, Object> userManageResMap = new HashMap();
        userManageResMap.put("displayName","用户管理");
        userManageResMap.put("value","18");
        Map<String, Object> roleManageResMap = new HashMap();
        roleManageResMap.put("displayName","角色管理");
        roleManageResMap.put("value","19");
        resource5List.add(userManageResMap);
        resource5List.add(roleManageResMap);
        module5.put("diaplayName", "用户管理");
        module5.put("resources", resource5List);
        moduleList.add(module5);

        /*设备管理*/
        Map<String, Object>  module6 = new HashMap();
        List resource6List = new  ArrayList();
        Map<String, Object> deviceStatusResMap = new HashMap();
        deviceStatusResMap.put("displayName","设备状态");
        deviceStatusResMap.put("value","21");
        Map<String, Object> deviceManageResMap = new HashMap();
        deviceManageResMap.put("displayName","设备管理");
        deviceManageResMap.put("value","22");
        Map<String, Object> deviceGroupResMap = new HashMap();
        deviceGroupResMap.put("displayName","设备分配");
        deviceGroupResMap.put("value","23");
        Map<String, Object> deviceOamResMap = new HashMap();
        deviceOamResMap.put("displayName","设备维护");
        deviceOamResMap.put("value","24");
        Map<String, Object> versionLibResMap = new HashMap();
        versionLibResMap.put("displayName","版本库管理");
        versionLibResMap.put("value","25");
        Map<String, Object> deviceVerMngResMap = new HashMap();
        deviceVerMngResMap.put("displayName","设备版本管理");
        deviceVerMngResMap.put("value","26");
        Map<String, Object> devLicenseResMap = new HashMap();
        devLicenseResMap.put("displayName","授权状态");
        devLicenseResMap.put("value","28");
        Map<String, Object> devLicenseHistoryResMap = new HashMap();
        devLicenseHistoryResMap.put("displayName","历史授权");
        devLicenseHistoryResMap.put("value","29");
        Map<String, Object> devAlarmCurrResMap = new HashMap();
        devAlarmCurrResMap.put("displayName","当前告警");
        devAlarmCurrResMap.put("value","31");
        Map<String, Object> devAlarmHistoryResMap = new HashMap();
        devAlarmHistoryResMap.put("displayName","历史告警");
        devAlarmHistoryResMap.put("value","32");
        resource6List.add(deviceStatusResMap);
        resource6List.add(deviceManageResMap);
        resource6List.add(deviceGroupResMap);
        resource6List.add(deviceOamResMap);
        resource6List.add(versionLibResMap);
        resource6List.add(deviceVerMngResMap);
        resource6List.add(devLicenseResMap);
        resource6List.add(devLicenseHistoryResMap);
        resource6List.add(devAlarmCurrResMap);
        resource6List.add(devAlarmHistoryResMap);
        module6.put("diaplayName", "设备管理");
        module6.put("resources", resource6List);
        moduleList.add(module6);

        /*系统维护*/
        Map<String, Object>  module7 = new HashMap();
        List resource7List = new  ArrayList();
        Map<String, Object> loggerResMap = new HashMap();
        loggerResMap.put("displayName","日志管理");
        loggerResMap.put("value","39");
        Map<String, Object> smsTmplResMap = new HashMap();
        smsTmplResMap.put("displayName","短信模板管理");
        smsTmplResMap.put("value","40");
        Map<String, Object> smsHistoryResMap = new HashMap();
        smsHistoryResMap.put("displayName","短信历史");
        smsHistoryResMap.put("value","41");
        Map<String, Object> emailTmplResMap = new HashMap();
        emailTmplResMap.put("displayName","邮件模板管理");
        emailTmplResMap.put("value","43");
        Map<String, Object> emailHistoryResMap = new HashMap();
        emailHistoryResMap.put("displayName","邮件历史");
        emailHistoryResMap.put("value","44");
        Map<String, Object> sysConfResMap = new HashMap();
        sysConfResMap.put("displayName","系统配置");
        sysConfResMap.put("value","46");
        Map<String, Object> dbMaintenanceResMap = new HashMap();
        dbMaintenanceResMap.put("displayName","数据库维护");
        dbMaintenanceResMap.put("value","47");
        Map<String, Object> sysLicenseResMap = new HashMap();
        sysLicenseResMap.put("displayName","系统授权");
        sysLicenseResMap.put("value","48");
        Map<String, Object> sysUpgradeResMap = new HashMap();
        sysUpgradeResMap.put("displayName","系统升级");
        sysUpgradeResMap.put("value","49");
        resource7List.add(loggerResMap);
        resource7List.add(smsTmplResMap);
        resource7List.add(smsHistoryResMap);
        resource7List.add(emailTmplResMap);
        resource7List.add(emailHistoryResMap);
        resource7List.add(sysConfResMap);
        resource7List.add(dbMaintenanceResMap);
        resource7List.add(sysLicenseResMap);
        resource7List.add(sysUpgradeResMap);
        module7.put("diaplayName", "系统维护");
        module7.put("resources", resource7List);
        moduleList.add(module7);

        model.put("modules", moduleList);

        return model;
    }

    public static JSONObject getRoles(){

        JSONObject jsonObj = new JSONObject();
        JSONArray ja = new JSONArray();

        JSONObject resources1 = new JSONObject();
        resources1.put("name", "总控台");
        resources1.put("value", "1");
        JSONObject resources2 = new JSONObject();
        resources2.put("name", "设备分布图");
        resources2.put("value", "2");
        JSONObject resources3 = new JSONObject();
        resources3.put("name", "实时数据");
        resources3.put("value", "4");
        JSONObject resources4 = new JSONObject();
        resources4.put("name", "设备版本管理");
        resources4.put("value", "26");

        JSONObject roleJo1 = new JSONObject();
        roleJo1.put("id", 1);
        roleJo1.put("name", "admin" );
        roleJo1.put("text", "系统管理员");
        roleJo1.put("remark", "系统管理员");
        roleJo1.put("defaultFlag", false );
        roleJo1.put("createTime", "2016-11-04 23:23:00");
        JSONArray jaResources1 = new JSONArray();
        jaResources1.add(resources1);
        jaResources1.add(resources2);
        jaResources1.add(resources3);
        jaResources1.add(resources4);
        roleJo1.put("resources", jaResources1 );

        JSONObject roleJo2 = new JSONObject();
        roleJo2.put("id", 2);
        roleJo2.put("name", "operator");
        roleJo2.put("text", "操作员");
        roleJo2.put("remark", "操作员");
        roleJo2.put("defaultFlag", true );
        roleJo2.put("createTime", "2016-11-04 23:23:00");
        JSONArray jaResources2 = new JSONArray();
        jaResources2.add(resources1);
        jaResources2.add(resources2);
        jaResources2.add(resources3);
        roleJo2.put("resources", jaResources2 );

        ja.add(roleJo1);
        ja.add(roleJo2);

        jsonObj.put("status", true);
        jsonObj.put("message", "成功");
        jsonObj.put("data", ja);

        return jsonObj;
    }
}
