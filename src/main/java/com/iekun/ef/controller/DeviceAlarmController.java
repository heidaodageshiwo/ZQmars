package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.service.DeviceAlarmService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;

import com.iekun.ef.util.ComInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangqi.yang  on 2016/10/26.
 */

@Controller
@RequestMapping("/device/alarm")
public class DeviceAlarmController {

    public static Logger logger = LoggerFactory.getLogger(DeviceAlarmController.class);

    
    @Autowired
    private ResourceService resourceServ;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private DeviceAlarmService dvAlarmServ;

    @Autowired
    private ComInfoUtil comInfoUtil;

    @RequestMapping("/current")
    public String current( Map<String, Object> model ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/12
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "device/alarm";
    }

    @RequestMapping("/getCurrentAlarms")
    @ResponseBody
    public JSONObject getCurrentAlarms() {
        JSONObject jsonObject = new JSONObject();
        //// TODO: 2016/11/12
        jsonObject = dvAlarmServ.getCurrentAlarms();
        //jsonObject = com.iekun.ef.test.ui.UITestDeviceAlarmData.getCurrentAlarms();
        return  jsonObject;
    }


    @RequestMapping("/history")
    public String history( Map<String, Object> model ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/12
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "device/historyAlarm";
    }

    @RequestMapping("/queryHistoryAlarms")
    @ResponseBody
    public JSONObject queryHistoryAlarms(
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "start", required = false ) Integer start,
            @RequestParam(value = "startDate", required = false ) String startDate,
            @RequestParam(value = "endDate", required = false ) String endDate,
            @RequestParam(value = "deviceSN", required = false ) String deviceSN,
            @RequestParam(value = "deviceName", required = false ) String deviceName,
            @RequestParam(value = "alarmCode", required = false ) String alarmCode
    ) throws UnsupportedEncodingException {
        JSONObject jsonObject  = new JSONObject();
        String deviceNameUtf8 = null;
        //// TODO: 2016/11/12
        /*jsonObject = com.iekun.ef.test.ui.UITestDeviceAlarmData.queryHistoryData( draw, length, start, startDate,
                endDate, deviceSN, deviceName, alarmCode );*/
        
        if (deviceName == null || deviceName.isEmpty())
        {
        	deviceNameUtf8 =null;
        }
        else
        {
        	deviceNameUtf8 =  URLDecoder.decode(deviceName,"utf-8");
        	logger.info("aaa deviceName " + deviceNameUtf8);
            
        }
        jsonObject = dvAlarmServ.queryHistoryData( draw, length, start, startDate,
                endDate, deviceSN, deviceNameUtf8, alarmCode );
        return jsonObject;
    }

}
