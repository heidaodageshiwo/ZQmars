package com.iekun.ef.controller;

import com.iekun.ef.dao.DeviceUpNumExMapper;
import com.iekun.ef.model.DeviceUpNumExModel;
import com.iekun.ef.model.DeviceUpNumExViewModel;
import com.iekun.ef.model.ResultBean;
import com.iekun.ef.service.DeviceAlarmUpNumExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备上号异常相关服务
 */
@Controller
@RequestMapping("/device/upnumex")
public class DeviceUpNumExController {

    @Autowired
    private DeviceAlarmUpNumExService deviceAlarmUpNumExService;

    //获取历史配置
    @RequestMapping(value = "/configlist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHistoryList(@RequestParam String deviceSn){
        Map<String, Object> resultMap = new HashMap<>();
        ResultBean<List<DeviceUpNumExModel>> result = deviceAlarmUpNumExService.getHistoryConfigs(deviceSn);
        if(result.getCode() == ResultBean.SUCCESS){
            resultMap.put("success", true);
            resultMap.put("configs", result.getData());
        }else{
            resultMap.put("success", false);
        }
        return resultMap;
    }

    //统一一个修改接口
    @RequestMapping(value = "/write", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Map<String, Object> writeConfigs(@RequestBody DeviceUpNumExViewModel deviceUpNumExViewModel){
        Map<String, Object> resultMap = new HashMap<>();
        ResultBean<Integer> result = deviceAlarmUpNumExService.writeAllUpNumExConfigs(deviceUpNumExViewModel.getDeviceId(), deviceUpNumExViewModel.getAddList(),
                deviceUpNumExViewModel.getUpdateList(), deviceUpNumExViewModel.getDeleteList());
        if(result.getCode() == ResultBean.SUCCESS){
            resultMap.put("success", true);
            resultMap.put("configs", result.getData());
        }else{
            resultMap.put("success", false);
            resultMap.put("msg", result.getMsg());
            resultMap.put("errordata", result.getData());
        }
        return resultMap;
    }

    //增加一条或多条配置信息
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Map<String, Object> addConfigs(@RequestBody List<DeviceUpNumExModel> configs){
        Map<String, Object> resultMap = new HashMap<>();
        ResultBean<Integer> result = deviceAlarmUpNumExService.addUpNumExConfigs(configs);
        if(result.getCode() == ResultBean.SUCCESS){
            resultMap.put("success", true);
            resultMap.put("num", result.getData());
        }else{
            resultMap.put("success", false);
        }
        return resultMap;
    }

    //删除一条配置
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> deleteConfig(DeviceUpNumExModel config){
        //注意添加校对 id不能为空
        Map<String, Object> result = new HashMap<>();

        return result;
    }

    //修改一条配置
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyConfig(DeviceUpNumExModel config){
        //注意添加校对 id不能为空
        Map<String, Object> result = new HashMap<>();

        return result;
    }

}
