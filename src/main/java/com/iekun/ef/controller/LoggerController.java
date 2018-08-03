package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;

import com.iekun.ef.util.ComInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by jiangqi.yang on 2016/10/26.
 */

@Controller
public class LoggerController {

    public static Logger logger = LoggerFactory.getLogger( LoggerController.class );

    @Autowired
    private ResourceService resourceServ;
	
	@Autowired
    private UserService userService;

    @Autowired
    private ComInfoUtil comInfoUtil;
    
    @Autowired
    private LoggerService loggerServ;
	
    @RequestMapping("/system/logger")
    public String logger( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        //// TODO: 2016/11/12
    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());
        return "system/logger";
    }

    @RequestMapping("/system/queryLoggerData")
    @ResponseBody
    public JSONObject queryLoggerData(
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "start", required = false ) Integer start,
            @RequestParam(value = "startDate", required = false ) String startDate,
            @RequestParam(value = "endDate", required = false ) String endDate,
            @RequestParam(value = "type", required = false ) String type,
            @RequestParam(value = "username", required = false ) String username,
            @RequestParam(value = "deviceSn", required = false ) String deviceSn
    ) {
        JSONObject jsonObject  = new JSONObject();
        //// TODO: 2016/11/12
        /*jsonObject = com.iekun.ef.test.ui.UITestLoggerData.queryLoggerData( draw, length,
                start, startDate, endDate, type, username );*/
        jsonObject = loggerServ.queryLoggerData( draw, length, start, startDate, endDate, type, username, deviceSn);
        return jsonObject;
    }


}
