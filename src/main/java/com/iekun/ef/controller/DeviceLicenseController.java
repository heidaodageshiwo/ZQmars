package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.service.DeviceLicenseService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.test.ui.UIDeviceLicense;
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
@RequestMapping("/device/license")
public class DeviceLicenseController {
    private static Logger logger = LoggerFactory.getLogger(DeviceLicenseController.class);

    @Autowired
    private ComInfoUtil comInfoUtil;

    @Autowired
    private ResourceService resourceServ;

    @Autowired
    private UserService userService;
    
    @Autowired
    private DeviceLicenseService dvLicenseServ;
    

    @RequestMapping("/index")
    public String index( Map<String, Object> model ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "device/licenseIndex";
    }

    @RequestMapping("/getDevLicenses")
    @ResponseBody
    public JSONObject getDeviceLicense(){
    	
        //JSONObject jsonObject = UIDeviceLicense.getDevLicenses();
    	JSONObject jsonObject = dvLicenseServ.getDevLicenses();
        return  jsonObject;
    }

    @RequestMapping("/updateFile")
    @ResponseBody
    public JSONObject updateFile(
            @RequestParam(value = "deviceSN", required = true ) String deviceSN,
            @RequestParam(value = "filename", required = true ) String filename,
            @RequestParam(value = "uploadFilename", required = true ) String uploadFilename
    ){
    	JSONObject jsonObject = dvLicenseServ.updateFile(deviceSN, filename,uploadFilename);
        /*
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");*/
        return jsonObject;
    }


    @RequestMapping("/history")
    public String history( Map<String, Object> model ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "device/licenseHistory";
    }

    @RequestMapping("/queryHistory")
    @ResponseBody
    public JSONObject queryHistory(
            @RequestParam(value = "draw", required = false ) Integer draw,
            @RequestParam(value = "length", required = false ) Integer length,
            @RequestParam(value = "start", required = false ) Integer start,
            @RequestParam(value = "startDate", required = false ) String startDate,
            @RequestParam(value = "endDate", required = false ) String endDate,
            @RequestParam(value = "deviceSN", required = false ) String deviceSN,
            @RequestParam(value = "status", required = false ) String status
    ){
    	
        JSONObject jsonObject = dvLicenseServ.queryHistoryData( draw,
                length, start, startDate, endDate, deviceSN, status);
    	/*JSONObject jsonObject = UIDeviceLicense.queryHistoryData( draw,
                 length, start, startDate, endDate, deviceSN, status );*/
        return jsonObject;
    }

}
