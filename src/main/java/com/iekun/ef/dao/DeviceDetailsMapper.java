package com.iekun.ef.dao;

import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceDetailsCount;
import com.iekun.ef.model.Site;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeviceDetailsMapper {


    List<Device> selectDeviceDetailsBySiteId(Long siteId);

    //List<DeviceDetailsCount> getDeviceDetailsCountlist(String deviceSn);
    List<DeviceDetailsCount> getDeviceDetailsCountlist(@Param("devicesn") String devicesn, @Param("table") String table);

    String getTableName(String date);

    List<Site> selectSiteListByUserId(Long userId);


}