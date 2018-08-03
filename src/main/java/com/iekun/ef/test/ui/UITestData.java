package com.iekun.ef.test.ui;

import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  页面展示测试数据
 *
 *
 * Created by jiangqi.yang on 2016/10/25.
 */

public class UITestData {

    public static Map<String, Object> getCommInfo(  Map<String, Object> model) {

        /* 测试数据-start */
        //用户
        //Map<String, Object> userInfo = com.iekun.ef.test.ui.UITestData.getUserInfo();
        //系统信息
        Map<String, Object> systemInfo = com.iekun.ef.test.ui.UITestData.getSystemInfo();
        //消息
        Map<String, Object>  messages= new HashMap();
        List messageList = new ArrayList();

        messages.put("count", messageList.size());
        messages.put("messages", messageList );

        //告警
        Map<String, Object>  alarms= new HashMap();
        List alarmList = new ArrayList();
        Map<String, Object> alarm1 = new HashMap();
        alarm1.put("type" , 0 );
        alarm1.put("info" , "上访人员-460000000000001" );
        alarm1.put("url" , "/target/current" );
        alarmList.add( alarm1 );
        Map<String, Object> alarm2 = new HashMap();
        alarm2.put("type" , 0 );
        alarm2.put("info" , "在逃犯-460000000000000" );
        alarm2.put("url" , "/target/current" );
        alarmList.add( alarm2 );
        Map<String, Object> alarm3 = new HashMap();
        alarm3.put("type" , 1 );
        alarm3.put("info" , "设备4号-PA:get status failed" );
        alarm3.put("url" , "/device/alarm/current" );
        alarmList.add( alarm3 );
        alarms.put("count", alarmList.size() );
        alarms.put("alarms", alarmList);


        //任务
        Map<String, Object>  tasks= new HashMap();
        List taskList = new ArrayList();
        Map<String, Object>  task1 = new HashMap();
        task1.put("progress" , 50 );
        task1.put("info" , "上海1统计" );
        task1.put("url" , "/analysis/imsiStatistics" );
        taskList.add( task1 );
        Map<String, Object>  task2 = new HashMap();
        task2.put("progress" , 50 );
        task2.put("info" , "沪嘉沿线" );
        task2.put("url" , "/analysis/dataCollide" );
        taskList.add( task2 );

        tasks.put("count", taskList.size());
        tasks.put("tasks", taskList);

        //菜单栏
         /* List treeMenus =  com.iekun.ef.test.ui.UITestData.getMenus();*/

        //model.put("user", userInfo);
        model.put("systemInfo", systemInfo);
        model.put("messages", messages);
        model.put("alarms", alarms);
        model.put("tasks", tasks);
        /* model.put("treeMenus", treeMenus);*/

        return model;
    }

    /***
     * 获取用户信息
     * @return
     */
    public static Map<String, Object> getUserInfo() {

        Map<String, Object> userInfo = new HashMap();
        userInfo.put("id", 1);
        userInfo.put("userName", "杨江奇");
        userInfo.put("loginName", "admin");
        userInfo.put("role", "管理员");
        userInfo.put("roleId", 1);
        userInfo.put("avata", "/img/avatar04.png");
        userInfo.put("sex", "男");
        userInfo.put("sexVal", 0 );
        userInfo.put("email", "jiangqi.yang@iekun.com");
        userInfo.put("mobilePhone", "13811111111");
        userInfo.put("remark", "测试数据");
        userInfo.put("create_time", "2016-10-15 15:30:00");

        return userInfo;
    }

    /**
     * 获取系统信息
     * @return
     */
    public static Map<String, Object> getSystemInfo() {

        Map<String, Object> systemInfo = new HashMap();
        systemInfo.put("version","2.0.0");
        systemInfo.put("companyYear","2014-2016");
        systemInfo.put("companyName","上海翊坤信息技术有限公司");
        systemInfo.put("companyURL","http://www.iekun.com");

        systemInfo.put("projectName","移动终端预警系统");
        systemInfo.put("projectIcon","/img/logo.png");

        return systemInfo;
    }

    /***
     * 返回菜单列表
     * @return
     */
    public static List getMenus() {

        List treeMenus = new ArrayList();

        /********************************************************/
        Map<String, Object>  overViewMenus = new HashMap();
        List overViewSubMenus = new ArrayList();

        Map<String, Object>  overViewTotalControl = new HashMap();
        overViewTotalControl.put("displayName", "总控台" );
        overViewTotalControl.put("icon", "" );
        overViewTotalControl.put("url", "/" );
        overViewSubMenus.add(overViewTotalControl);

        Map<String, Object>  overViewMapper = new HashMap();
        overViewMapper.put("displayName", "设备分布图" );
        overViewMapper.put("icon", "" );
        overViewMapper.put("url", "/map" );
        overViewSubMenus.add(overViewMapper);

        overViewMenus.put("displayName", "概览" );
        overViewMenus.put("icon", "fa fa-dashboard" );
        overViewMenus.put("url", "" );
        overViewMenus.put("children", overViewSubMenus );
        treeMenus.add( overViewMenus );

        /********************************************************/
        Map<String, Object>  dataMenus = new HashMap();
        List dataSubMenus = new ArrayList();

        Map<String, Object>  dataRealTimeMenu = new HashMap();
        dataRealTimeMenu.put("displayName", "实时数据" );
        dataRealTimeMenu.put("icon", "" );
        dataRealTimeMenu.put("url", "/realTimeData" );
        dataSubMenus.add( dataRealTimeMenu );

        Map<String, Object>  dataHistoryMenu = new HashMap();
        dataHistoryMenu.put("displayName", "历史数据" );
        dataHistoryMenu.put("icon", "" );
        dataHistoryMenu.put("url", "/historyData" );
        dataSubMenus.add( dataHistoryMenu );

        dataMenus.put("displayName", "数据采集" );
        dataMenus.put("icon", "fa fa-cloud-upload" );
        dataMenus.put("url", "" );
        dataMenus.put("children", dataSubMenus );
        treeMenus.add( dataMenus );

        /********************************************************/
        Map<String, Object>  analysisMenus = new HashMap();
        List analysisSubMenus = new ArrayList();

        Map<String, Object>  IMSIStatisticsMenu = new HashMap();
        IMSIStatisticsMenu.put("displayName", "上号统计" );
        IMSIStatisticsMenu.put("icon", "" );
        IMSIStatisticsMenu.put("url", "/analysis/imsiStatistics" );
        analysisSubMenus.add( IMSIStatisticsMenu );

        Map<String, Object>  dataCollideMenu = new HashMap();
        dataCollideMenu.put("displayName", "数据碰撞分析" );
        dataCollideMenu.put("icon", "" );
        dataCollideMenu.put("url", "/analysis/dataCollide" );
        analysisSubMenus.add( dataCollideMenu );

        Map<String, Object>  residentPeopleMenu = new HashMap();
        residentPeopleMenu.put("displayName", "常驻人口外来人口分析" );
        residentPeopleMenu.put("icon", "" );
        residentPeopleMenu.put("url", "/analysis/residentPeople" );
        analysisSubMenus.add( residentPeopleMenu );

        Map<String, Object>  IMSIFollowMenu = new HashMap();
        IMSIFollowMenu.put("displayName", "IMSI伴随分析" );
        IMSIFollowMenu.put("icon", "" );
        IMSIFollowMenu.put("url", "/analysis/imsiFollow" );
        analysisSubMenus.add( IMSIFollowMenu );

        Map<String, Object>  suspectTrailMenu = new HashMap();
        suspectTrailMenu.put("displayName", "嫌疑人运动轨迹" );
        suspectTrailMenu.put("icon", "" );
        suspectTrailMenu.put("url", "/analysis/suspectTrail" );
        analysisSubMenus.add( suspectTrailMenu );

        analysisMenus.put("displayName", "数据分析" );
        analysisMenus.put("icon", "fa  fa-random" );
        analysisMenus.put("url", "" );
        analysisMenus.put("children", analysisSubMenus );
        treeMenus.add( analysisMenus );

        /********************************************************/
        Map<String, Object>  warningMenus = new HashMap();
        List warningSubMenus = new ArrayList();

        Map<String, Object>  currentWarningMenu = new HashMap();
        currentWarningMenu.put("displayName", "当前预警" );
        currentWarningMenu.put("icon", "" );
        currentWarningMenu.put("url", "/target/current" );
        warningSubMenus.add( currentWarningMenu );

        Map<String, Object>  historyWarningMenu = new HashMap();
        historyWarningMenu.put("displayName", "历史预警" );
        historyWarningMenu.put("icon", "" );
        historyWarningMenu.put("url", "/target/history" );
        warningSubMenus.add( historyWarningMenu );

        Map<String, Object>  blacklistMenu = new HashMap();
        blacklistMenu.put("displayName", "黑名单管理" );
        blacklistMenu.put("icon", "" );
        blacklistMenu.put("url", "/target/blacklist" );
        warningSubMenus.add( blacklistMenu );

        Map<String, Object>  homeOwnershipListMenu = new HashMap();
        homeOwnershipListMenu.put("displayName", "归属地预警管理" );
        homeOwnershipListMenu.put("icon", "" );
        homeOwnershipListMenu.put("url", "/target/homeOwnership" );
        warningSubMenus.add( homeOwnershipListMenu );


        warningMenus.put("displayName", "预警" );
        warningMenus.put("icon", "fa fa-lightbulb-o" );
        warningMenus.put("url", "" );
        warningMenus.put("children", warningSubMenus );
        treeMenus.add( warningMenus );

        /********************************************************/
        Map<String, Object>  userMenus = new HashMap();
        List userSubMenus = new ArrayList();

        Map<String, Object>  userMenu = new HashMap();
        userMenu.put("displayName", "用户管理" );
        userMenu.put("icon", "" );
        userMenu.put("url", "/user/index" );
        userSubMenus.add( userMenu );

        Map<String, Object>  roleMenu = new HashMap();
        roleMenu.put("displayName", "角色管理" );
        roleMenu.put("icon", "" );
        roleMenu.put("url", "/user/role" );
        userSubMenus.add( roleMenu );

//        Map<String, Object>  authMenu = new HashMap();
//        authMenu.put("displayName", "权限管理" );
//        authMenu.put("icon", "" );
//        authMenu.put("url", "/user/auth" );
//        userSubMenus.add( authMenu );

        userMenus.put("displayName", "用户管理" );
        userMenus.put("icon", "fa fa-user" );
        userMenus.put("url", "" );
        userMenus.put("children", userSubMenus );
        treeMenus.add( userMenus );

        /********************************************************/
        Map<String, Object>  deviceMenus = new HashMap();
        List deviceSubMenus = new ArrayList();

        Map<String, Object>  deviceStatusMenu = new HashMap();
        deviceStatusMenu.put("displayName", "设备状态" );
        deviceStatusMenu.put("icon", "" );
        deviceStatusMenu.put("url", "/device/status" );
        deviceSubMenus.add( deviceStatusMenu );

        Map<String, Object>  deviceManageMenu = new HashMap();
        deviceManageMenu.put("displayName", "设备管理" );
        deviceManageMenu.put("icon", "" );
        deviceManageMenu.put("url", "/device/index" );
        deviceSubMenus.add( deviceManageMenu );

        Map<String, Object>  deviceGroupMenu = new HashMap();
        deviceGroupMenu.put("displayName", "设备分配" );
        deviceGroupMenu.put("icon", "" );
        deviceGroupMenu.put("url", "/device/group" );
        deviceSubMenus.add( deviceGroupMenu );

        Map<String, Object>  deviceOAMMenu = new HashMap();
        deviceOAMMenu.put("displayName", "设备维护" );
        deviceOAMMenu.put("icon", "" );
        deviceOAMMenu.put("url", "/device/oam" );
        deviceSubMenus.add( deviceOAMMenu );

        Map<String, Object>  deviceVersionMenu = new HashMap();
        List versionSubMenus = new ArrayList();

        Map<String, Object>  deviceVersionManageMenu = new HashMap();
        deviceVersionManageMenu.put("displayName", "版本库管理" );
        deviceVersionManageMenu.put("icon", "" );
        deviceVersionManageMenu.put("url", "/device/version/index" );
        versionSubMenus.add( deviceVersionManageMenu );

        Map<String, Object>  deviceVersionOamMenu = new HashMap();
        deviceVersionOamMenu.put("displayName", "设备版本管理" );
        deviceVersionOamMenu.put("icon", "" );
        deviceVersionOamMenu.put("url", "/device/version/oam" );
        versionSubMenus.add( deviceVersionOamMenu );

        deviceVersionMenu.put("displayName", "版本管理" );
        deviceVersionMenu.put("icon", "fa fa-file-text-o" );
        deviceVersionMenu.put("url", "" );
        deviceVersionMenu.put("children", versionSubMenus );
        deviceSubMenus.add( deviceVersionMenu );


        Map<String, Object>  deviceLicenseMenu = new HashMap();
        List licenseSubMenus = new ArrayList();

        Map<String, Object>  deviceLicenseManageMenu = new HashMap();
        deviceLicenseManageMenu.put("displayName", "授权状态" );
        deviceLicenseManageMenu.put("icon", "" );
        deviceLicenseManageMenu.put("url", "/device/version/index" );
        licenseSubMenus.add( deviceLicenseManageMenu );

        Map<String, Object>  deviceLicenseOamMenu = new HashMap();
        deviceLicenseOamMenu.put("displayName", "历史授权" );
        deviceLicenseOamMenu.put("icon", "" );
        deviceLicenseOamMenu.put("url", "/device/version/oam" );
        licenseSubMenus.add( deviceLicenseOamMenu );

        deviceLicenseMenu.put("displayName", "授权管理" );
        deviceLicenseMenu.put("icon", "fa fa-key" );
        deviceLicenseMenu.put("url", "" );
        deviceLicenseMenu.put("children", licenseSubMenus );
        deviceSubMenus.add( deviceLicenseMenu );


        Map<String, Object>  deviceWarningMenu = new HashMap();
        List deviceWarningSubMenus = new ArrayList();

        Map<String, Object>  deviceCurrentWarningMenu = new HashMap();
        deviceCurrentWarningMenu.put("displayName", "当前告警" );
        deviceCurrentWarningMenu.put("icon", "" );
        deviceCurrentWarningMenu.put("url", "/device/alarm/current" );
        deviceWarningSubMenus.add( deviceCurrentWarningMenu );

        Map<String, Object>  deviceHistoryWarningMenu = new HashMap();
        deviceHistoryWarningMenu.put("displayName", "历史告警" );
        deviceHistoryWarningMenu.put("icon", "" );
        deviceHistoryWarningMenu.put("url", "/device/alarm/history" );
        deviceWarningSubMenus.add( deviceHistoryWarningMenu );

        deviceWarningMenu.put("displayName", "设备告警" );
        deviceWarningMenu.put("icon", "fa fa-warning" );
        deviceWarningMenu.put("url", "" );
        deviceWarningMenu.put("children", deviceWarningSubMenus );
        deviceSubMenus.add( deviceWarningMenu );

        deviceMenus.put("displayName", "设备管理" );
        deviceMenus.put("icon", "fa  fa-cubes" );
        deviceMenus.put("url", "" );
        deviceMenus.put("children", deviceSubMenus );
        treeMenus.add( deviceMenus );

        /********************************************************/
        Map<String, Object>  userCenterMenus = new HashMap();
        List userCenterSubMenus = new ArrayList();

        Map<String, Object>  userProfileMenu = new HashMap();
        userProfileMenu.put("displayName", "用户信息" );
        userProfileMenu.put("icon", "" );
        userProfileMenu.put("url", "/user/profile" );
        userCenterSubMenus.add( userProfileMenu );

        Map<String, Object>  userNotificationMenu = new HashMap();
        userNotificationMenu.put("displayName", "通知消息" );
        userNotificationMenu.put("icon", "" );
        userNotificationMenu.put("url", "/user/notification" );
        userCenterSubMenus.add( userNotificationMenu );

        Map<String, Object>  userSpecificMenu = new HashMap();
        userSpecificMenu.put("displayName", "用户配置" );
        userSpecificMenu.put("icon", "" );
        userSpecificMenu.put("url", "/user/specific" );
        userCenterSubMenus.add( userSpecificMenu );

        userCenterMenus.put("displayName", "用户中心" );
        userCenterMenus.put("icon", "fa fa-child" );
        userCenterMenus.put("url", "" );
        userCenterMenus.put("children", userCenterSubMenus );
        treeMenus.add( userCenterMenus );


        /********************************************************/

        Map<String, Object>  systemMenus = new HashMap();
        List systemSubMenus = new ArrayList();

        Map<String, Object>  systemLogMenu = new HashMap();
        systemLogMenu.put("displayName", "日志管理" );
        systemLogMenu.put("icon", "" );
        systemLogMenu.put("url", "/system/logger" );
        systemSubMenus.add( systemLogMenu );

        Map<String, Object>  systemSmsMenu = new HashMap();
        List systemSmsSubMenus = new ArrayList();

        Map<String, Object>  systemSmsTemplateMenu = new HashMap();
        systemSmsTemplateMenu.put("displayName", "模板管理" );
        systemSmsTemplateMenu.put("icon", "" );
        systemSmsTemplateMenu.put("url", "/system/sms/template" );
        systemSmsSubMenus.add( systemSmsTemplateMenu );

        Map<String, Object>  systemSmsHistoryMenu = new HashMap();
        systemSmsHistoryMenu.put("displayName", "短信历史" );
        systemSmsHistoryMenu.put("icon", "" );
        systemSmsHistoryMenu.put("url", "/system/sms/history" );
        systemSmsSubMenus.add( systemSmsHistoryMenu );

        systemSmsMenu.put("displayName", "短信通知" );
        systemSmsMenu.put("icon", "fa  fa-commenting-o" );
        systemSmsMenu.put("url", "" );
        systemSmsMenu.put("children", systemSmsSubMenus );
        systemSubMenus.add( systemSmsMenu );


        Map<String, Object>  systemEmailMenu = new HashMap();
        List systemEmailSubMenus = new ArrayList();

        Map<String, Object>  systemEmailTempletMenu = new HashMap();
        systemEmailTempletMenu.put("displayName", "模板管理" );
        systemEmailTempletMenu.put("icon", "" );
        systemEmailTempletMenu.put("url", "/system/email/template" );
        systemEmailSubMenus.add( systemEmailTempletMenu );

        Map<String, Object>  systemEmailHistoryMenu = new HashMap();
        systemEmailHistoryMenu.put("displayName", "邮件历史" );
        systemEmailHistoryMenu.put("icon", "" );
        systemEmailHistoryMenu.put("url", "/system/email/history" );
        systemEmailSubMenus.add( systemEmailHistoryMenu );

        systemEmailMenu.put("displayName", "邮件通知" );
        systemEmailMenu.put("icon", "fa fa-envelope" );
        systemEmailMenu.put("url", "" );
        systemEmailMenu.put("children", systemEmailSubMenus );
        systemSubMenus.add( systemEmailMenu );


        Map<String, Object>  systemConfMenu = new HashMap();
        systemConfMenu.put("displayName", "系统配置" );
        systemConfMenu.put("icon", "" );
        systemConfMenu.put("url", "/system/configuration" );
        systemSubMenus.add( systemConfMenu );

        Map<String, Object>  systemDatabaseMenu = new HashMap();
        systemDatabaseMenu.put("displayName", "数据库维护" );
        systemDatabaseMenu.put("icon", "" );
        systemDatabaseMenu.put("url", "/system/databaseMaintenance" );
        systemSubMenus.add( systemDatabaseMenu );

        Map<String, Object>  systemLicenseMenu = new HashMap();
        systemLicenseMenu.put("displayName", "系统授权" );
        systemLicenseMenu.put("icon", "" );
        systemLicenseMenu.put("url", "/system/license" );
        systemSubMenus.add( systemLicenseMenu );

        systemMenus.put("displayName", "系统维护" );
        systemMenus.put("icon", "fa fa-gears" );
        systemMenus.put("url", "" );
        systemMenus.put("children", systemSubMenus );
        treeMenus.add( systemMenus );

        return treeMenus;
    }


    /***
     * 获取系统许可信息
     * @return
     */
    public static Map<String, Object> getLicenseInfo() {

        Map<String, Object> licenseInfo = new HashMap();

        licenseInfo.put("softwareName", "电子围栏系统2.0版本");
        licenseInfo.put("version", "2.0.0");
        licenseInfo.put("productSN", "FD84C0-5FE2D1-2E3677-4B26E3-5F7B95-FF");
        licenseInfo.put("licensedTo", "翊坤");
        licenseInfo.put("publishDate", "2016-09-09");
        licenseInfo.put("expiredDate", "2020-12-12");
        licenseInfo.put("jreInfo", "1.8.0_77 amd64");
        licenseInfo.put("jvmInfo", "Java HotSpot(TM) 64-Bit Server VM");

        return licenseInfo;
    }

    public static Map<String, Object> getStatisticsInfo(  Map<String, Object> model) {

        Map<String, Object> statistics = new HashedMap();

        Map<String, Object> device = new HashMap();
        Map<String, Object> system = new HashMap();

        device.put("total", 200 );
        device.put("running", 150 );
        device.put("offline", 45 );
        device.put("willExpire", 5 );
        device.put("expiredFailure", 0 );
        device.put("warning", 10 );

        Map<String, Object> hardDisk = new HashMap();
        hardDisk.put("used",  "200" );
        hardDisk.put("free",  "15000" );
        system.put("hardDisk", hardDisk);
        system.put("ueTotal", 130555);
        system.put("blacklistTotal", 15);
        system.put("homeOwnershipTotal", 13);
        Map<String, Object> runTime = new HashMap();
        runTime.put("day", 5);
        runTime.put("hour", 12);
        runTime.put("minute", 30);
        runTime.put("second", 30);
        system.put("runTime", runTime );

        statistics.put("system", system);
        statistics.put("device", device);
        model.put("statistics", statistics);
        return model;
    }

}
