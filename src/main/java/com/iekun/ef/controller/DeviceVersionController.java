package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.service.DeviceVersionService;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.test.ui.UIDeviceVersion;
import com.iekun.ef.util.ComInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangqi.yang  on 2016/10/26.
 */

@Controller
@RequestMapping("/device/version")
public class DeviceVersionController {

    private static Logger logger = LoggerFactory.getLogger( DeviceVersionController.class);

    @Autowired
    private ComInfoUtil comInfoUtil;

    @Autowired
    private ResourceService resourceServ;

    @Autowired
    private UserService userService;
    
    @Autowired
    private DeviceVersionService dvVersionServ;

    @RequestMapping("/library")
    public String library( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "device/versionLib";
    }

    @RequestMapping("/getLibraries")
    @ResponseBody
    public JSONObject getLibraries() {
        //// TODO: 2016/12/5
    	JSONObject jsonObject = new JSONObject();
    	
        jsonObject = dvVersionServ.getLibraries();
    	//jsonObject = UIDeviceVersion.getLibraries();
        
        
        return jsonObject;
    }

    @RequestMapping("/createLibrary")
    @ResponseBody
    public JSONObject createLibrary(
            @RequestParam(value = "filename", required = true ) String filename,
            @RequestParam(value = "uploadFilename", required = true ) String uploadFilename,
            @RequestParam(value = "version", required = true ) String version,
            @RequestParam(value = "remark", required = false ) String remark,
            @RequestParam(value = "fpgaVersion", required = false ) String fpgaVersion,
            @RequestParam(value = "bbuVersion", required = false ) String bbuVersion,
            @RequestParam(value = "swVersion", required = false ) String swVersion
    ){
        JSONObject jsonObject = new JSONObject();
        boolean returnVal = false;
        returnVal = dvVersionServ.createLibrary(filename, uploadFilename, version, remark, fpgaVersion, bbuVersion, swVersion);
        if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }
        
        return jsonObject;
    }

    @RequestMapping("/delLibrary")
    @ResponseBody
    public JSONObject delLibrary(
            @RequestParam(value = "id[]", required = true ) String[] ids
    ){
        JSONObject jsonObject = new JSONObject();
        boolean returnVal = false;
        for(int i=0; i<ids.length; i++)
        {
        	 try {
				returnVal = dvVersionServ.delLibrary(Long.parseLong(ids[i]));
				returnVal = true;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				returnVal = false;
				e.printStackTrace();
			} 
        }
 
        if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }
        
        return jsonObject;
    }

    @RequestMapping("/upgradeDevice")
    @ResponseBody
    public JSONObject upgradeDevice(
            @RequestParam(value="versionId", required = true ) Long versionId,
            @RequestParam(value = "sn[]", required = true ) String[] SNs
    ){
        JSONObject jsonObject = new JSONObject();
        boolean returnVal = false;
        List<String>  snList = new  ArrayList<String>();
        
        logger.info("SNs size = " + SNs.length) ;
        for(int i=0; i < SNs.length; i++)
        {
        	snList.add(SNs[i]);
        	logger.info("sn" + i + ":" + SNs[i]) ;
        }
        
        returnVal = dvVersionServ.upgradeDevices(versionId, snList);
        if(returnVal == true)
        {
        	jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        }
        else
        {
        	jsonObject.put("status", false);
            jsonObject.put("message", "失败");
        }
        
        return jsonObject;
    }

    @RequestMapping("/manager")
    public  String manager( Map<String, Object> model ) {

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        return "device/versionManager";
    }

    @RequestMapping("/getDevVerList")
    @ResponseBody
    public JSONObject getDevVerList() {
    	
        //JSONObject jsonObject = UIDeviceVersion.getDevVerList();
    	JSONObject jsonObject = dvVersionServ.getDevVerList();
        return jsonObject;
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
            @RequestParam(value = "version", required = false ) String version,
            @RequestParam(value = "status", required = false ) String status
    ) {

        //JSONObject jsonObject = UIDeviceVersion.queryHistoryData( draw, length, start, startDate,
        //        endDate, deviceSN, version, status );
    	
        JSONObject jsonObject = dvVersionServ.queryHistoryData( draw, length, start, startDate,
                endDate, deviceSN, version, status );
        return jsonObject;
    }


}
