package com.iekun.ef.dao;

import com.iekun.ef.model.SelfAreaCode;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceExample;
import com.iekun.ef.model.DeviceSiteDetailInfo;
import com.iekun.ef.model.DeviceUser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DeviceMapper {
    int countByExample(DeviceExample example);

    int deleteByExample(DeviceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Device record);

    int insertSelective(Device record);

    List<Device> selectByExample(DeviceExample example);

    Device selectByPrimaryKey(Long id);
    
    Device selectByDevSN(String SN);

    int updateByExampleSelective(@Param("record") Device record, @Param("example") DeviceExample example);

    int updateByExample(@Param("record") Device record, @Param("example") DeviceExample example);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

	List<Device> selectDeviceList();
	
	List<Device> selectDeviceDetails(Map<String, Object> params);
	
	List<Device> selectDeviceDetailsBySiteId(Long siteId);

	void updateDeviceById(Device device);

	void deleteDeviceById(Device device);

	Device getDeviceSiteDetails(String deviceSn);

	List<DeviceUser> selectDeviceUsers();

	List<SelfAreaCode> selectAreaCodeDetails();

	List<Device> selectDevList();

	List<Device> selectUserDevices(Long userId);
	
}