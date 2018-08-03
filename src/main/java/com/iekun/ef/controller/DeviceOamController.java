package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.service.OamMessageService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.test.ui.UIOam;
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
 * Created by jiangqi.yang  on 2016/10/26.
 */

@Controller
@RequestMapping("/device")
public class DeviceOamController {

    private static Logger logger = LoggerFactory.getLogger( DeviceOamController.class );

    @Autowired
    private ComInfoUtil comInfoUtil;

    @Autowired
    private ResourceService resourceServ;

    @Autowired
    private UserService userService;
    
    @Autowired
    private OamMessageService oamMsgServ;


    @RequestMapping("/oam")
    public String oam( Map<String, Object> model ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "device/oamNew";
    }

    @RequestMapping("/deviceAction")
    @ResponseBody
    public JSONObject deviceAction(
            @RequestParam(value = "deviceSN", required = true ) String deviceSN,
            @RequestParam(value = "actionName", required = true ) String actionName,
            @RequestParam(value = "actionParam", required = false ) String actionParam
    ) {
    	//JSONObject jsonObject = UIOam.deviceAction( deviceSN, actionName, actionParam );
        logger.info("step 1 deviceSN=" + deviceSN + " =actionName" + actionName + " actionParam=" + actionParam);
        JSONObject jsonObject = oamMsgServ.oamMessageHandle( deviceSN, actionName, actionParam );
        //logger.info("=============++++deviceAction oam msg :===" + jsonObject.toJSONString());
        return jsonObject;
    }

    /**
     * 下载设备日志
     * @param deviceSn
     * @return
     */
    @RequestMapping("/exportLog")
    @ResponseBody
    public JSONObject exportLog(String deviceSn){
        JSONObject jsonObject = new JSONObject();
        jsonObject = oamMsgServ.exportLog(deviceSn);
        return jsonObject;
    }
}
