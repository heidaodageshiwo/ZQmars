package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by jiangqi.yang on 2016/12/14.
 */
public class SystemConfigData {

    public static JSONObject getConfig(){
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObj = new JSONObject();

        Date nowTime = new Date();
        String iconUrl = "/img/logo.png?t=" + nowTime.getTime();

        dataObj.put("systemName","移动终端预警系统");
        dataObj.put("systemIcon", iconUrl);

        dataObj.put("companyYear","2014");
        dataObj.put("companyName","上海翊坤信息技术有限公司");
        dataObj.put("companyURL","http://www.iekun.com");

        dataObj.put("upgradeURL","http://localhost:9191/login");

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObj);

        return jsonObject;
    }
}
