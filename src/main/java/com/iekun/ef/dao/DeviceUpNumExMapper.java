package com.iekun.ef.dao;

import com.iekun.ef.model.DeviceUpNumExModel;

import java.util.List;

public interface DeviceUpNumExMapper {

    List<DeviceUpNumExModel> getHistoryConfigs(String deviceSn);
    int insertConfigs(List<DeviceUpNumExModel> configs);
    int updateConfigs(List<DeviceUpNumExModel> configs);
    int deleteConfigs(List<DeviceUpNumExModel> configs);
    int deleteConfig(DeviceUpNumExModel config);
    int updateConfigByPrimaryKey(DeviceUpNumExModel config);

}
