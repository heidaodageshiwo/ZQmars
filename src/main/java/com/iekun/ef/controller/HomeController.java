package com.iekun.ef.controller;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.service.SystemMonitor;
import com.iekun.ef.service.SystemParaService;
import com.iekun.ef.util.ComInfoUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.service.ResourceService;
import com.iekun.ef.service.UserService;

import java.util.*;

/**
 * Created by jiangqi.yang  on 2016/10/21.
 */

@Controller
public class HomeController {

	@Autowired
	private ResourceService resourceServ;
	
	@Autowired
	private UserService userService;

    @Autowired
    private SystemMonitor systemMonitor;
    
    @Autowired
    private SystemParaService sysParaServ;

    @Autowired
    private ComInfoUtil comInfoUtil;

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String home( Map<String, Object> model ) {

    	long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );
        
        Map<String, Object> statistics = new HashedMap();
        Map<String, Object> device = new HashMap();
        Map<String, Object> system = new HashMap();
        Integer runCnt = 0;
        Integer offlineCnt = 0;
        Integer warningCnt = 0;
        Integer willExpireCnt = 0;
        Integer expiredCnt = 0;
        Integer totalCnt = 0;
        DeviceStatus deviceStatus;
        
        Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
        
        for(Map.Entry<String, Object> entry : dvStatusMaps.entrySet())
        {
        	deviceStatus = (DeviceStatus)(entry.getValue());
        	switch(deviceStatus.getDeviceStatusCommon().getDeviceStatus()) {

                case "run":
                    runCnt++;
                    break;
                case "offline":
                    offlineCnt++;
                    break;
                case "willexpire":
                    willExpireCnt++;
                    break;
                case "expiredfailure":
                    expiredCnt++;
                    break;
                case "warning":
                    warningCnt++;
                    break;
                default:
                    break;
            }
        }
        
        totalCnt = runCnt + offlineCnt + willExpireCnt + expiredCnt + warningCnt;
        //// TODO: 2016/11/29
        device.put("total", totalCnt );
        device.put("running", runCnt );
        device.put("offline", offlineCnt );
        device.put("willExpire", willExpireCnt );
        device.put("expiredFailure", expiredCnt );
        device.put("warning", warningCnt );
        statistics.put("device", device);

        Map<String, Object> hardDisk = new HashMap();
        JSONObject hardDiskJsonObj = systemMonitor.getHardDisk();
        hardDisk.put("used",  hardDiskJsonObj.getString("used") );
        hardDisk.put("free",  hardDiskJsonObj.getString("free") );
        system.put("hardDisk", hardDisk);
        system.put("ueTotal", systemMonitor.getUeInfoTotal());
        system.put("blacklistTotal", systemMonitor.getBlacklistAlarmTotal());
        system.put("homeOwnershipTotal",  systemMonitor.getHomeOwnershipAlarmTotal());
        Map<String, Object> runTime = new HashMap();
        JSONObject runTimeJsonObj = systemMonitor.getSystemRunningDuration();
        runTime.put("day", runTimeJsonObj.getString("day"));
        runTime.put("hour", runTimeJsonObj.getString("hour"));
        runTime.put("minute", runTimeJsonObj.getString("minute"));
        runTime.put("second", runTimeJsonObj.getString("second"));
        system.put("runTime", runTime );

        statistics.put("system", system);
        model.put("statistics", statistics);

        return "home";
    }

    @RequestMapping("/map")
    public String map( Map<String, Object> model  ){

        comInfoUtil.getTopHeaderInfo( model );
        comInfoUtil.getSystemInfo( model );

        long roleId = SigletonBean.getRoleId();
        resourceServ.getResourceInfoByRoleId(model, roleId);
        model.put("user", userService.getUserDetailInfo());


        Map<String, Object> statistics = new HashedMap();
        Map<String, Object> system = new HashMap();

        system.put("ueTotal", systemMonitor.getUeInfoTotal());
        system.put("blacklistTotal", systemMonitor.getBlacklistAlarmTotal());
        system.put("homeOwnershipTotal",  systemMonitor.getHomeOwnershipAlarmTotal());
        statistics.put("system", system);
        model.put("statistics", statistics);

        Map<String, Object> mapInfo = new HashMap();
        Map<String, Object> centerPoint = new HashMap();

        centerPoint.put("lng",  Float.parseFloat(sysParaServ.getSysPara("mapCenterLng")) );
        centerPoint.put("lat",  Float.parseFloat(sysParaServ.getSysPara("mapCenterLat")) );
        mapInfo.put("centerPoint", centerPoint );
        mapInfo.put("zoom", Integer.parseInt(sysParaServ.getSysPara("mapZoom")) );
        mapInfo.put("mapOnline", sysParaServ.getSysPara("mapDataSwitch") );
        model.put("map", mapInfo);

        return "map";
    }
}
