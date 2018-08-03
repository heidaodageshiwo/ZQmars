package com.iekun.ef.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示设备状态控制器
 */
public class DeviceListController {
    @RequestMapping("/getpage")
    public String getDeviceListPage() {
        return "device/devicelist";
    }
}
