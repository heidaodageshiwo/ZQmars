package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by jiangqi.yang on 2016/11/2.
 */
public class UITestUserData {

    public static JSONObject getUsers() {

        JSONObject jsonObject  = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        JSONObject user1  = new JSONObject();
        user1.put("id", "1");
        user1.put("loginName", "admin");
        user1.put("userName", "张三");
        user1.put("sex", "男");
        user1.put("sexVal", 0);
        user1.put("email", "zhangshan@iekun.com");
        user1.put("mobilePhone", "13800000000");
        user1.put("role", "系统管理员");
        user1.put("roleId", "1");
        user1.put("createTime", "2016-10-31 10:30:00");
        user1.put("remark", "测试数据");
        user1.put("locked", 0);

        JSONObject user2  = new JSONObject();
        user2.put("id", "2");
        user2.put("loginName", "lishi");
        user2.put("userName", "李四");
        user2.put("sex", "男");
        user2.put("sexVal", 0);
        user2.put("email", "lishi@iekun.com");
        user2.put("mobilePhone", "13800000001");
        user2.put("role", "设备管理员");
        user2.put("roleId", "2");
        user2.put("createTime", "2016-10-31 10:30:00");
        user2.put("remark", "测试数据");
        user2.put("locked", 0);

        JSONObject user3  = new JSONObject();
        user3.put("id", "3");
        user3.put("loginName", "zhaowu");
        user3.put("userName", "赵五");
        user3.put("sex", "男");
        user3.put("sexVal", 0);
        user3.put("email", "zhaowu@iekun.com");
        user3.put("mobilePhone", "13800000002");
        user3.put("role", "业务用户");
        user3.put("roleId", "3");
        user3.put("createTime", "2016-10-31 10:30:00");
        user3.put("remark", "测试数据");
        user3.put("locked", 1);


        jsonArray.add(user1);
        jsonArray.add(user2);
        jsonArray.add(user3);

        jsonObject.put("data", jsonArray);

        return jsonObject;
    }

    public static JSONObject getRoles() {

        JSONObject jsonObject  = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        JSONObject user1  = new JSONObject();
        user1.put("id", "1");
        user1.put("name", "admin");
        user1.put("text", "系统管理员");
        user1.put("defaultFlag", "0");
        user1.put("createTime", "2016-10-31 10:30:00");

        JSONObject user2  = new JSONObject();
        user2.put("id", "2");
        user2.put("name", "设备管理员");
        user2.put("text", "设备管理员");
        user2.put("defaultFlag", "1");
        user2.put("createTime", "2016-10-31 10:30:00");

        JSONObject user3  = new JSONObject();
        user3.put("id", "3");
        user3.put("name", "业务用户");
        user3.put("text", "业务用户");
        user3.put("defaultFlag", "0");
        user3.put("createTime", "2016-10-31 10:30:00");

        jsonArray.add(user1);
        jsonArray.add(user2);
        jsonArray.add(user3);

        jsonObject.put("data", jsonArray);

        return jsonObject;
    }
}
