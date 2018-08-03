package com.iekun.ef.dao;

import com.iekun.ef.model.DeviceAlarm;
import com.iekun.ef.model.DeviceAlarmExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DeviceAlarmMapper {
    int countByExample(DeviceAlarmExample example);

    int deleteByExample(DeviceAlarmExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeviceAlarm record);

    int insertSelective(DeviceAlarm record);

    List<DeviceAlarm> selectByExample(DeviceAlarmExample example);

    DeviceAlarm selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeviceAlarm record, @Param("example") DeviceAlarmExample example);

    int updateByExample(@Param("record") DeviceAlarm record, @Param("example") DeviceAlarmExample example);

    int updateByPrimaryKeySelective(DeviceAlarm record);

    int updateByPrimaryKey(DeviceAlarm record);

	List<DeviceAlarm> selectDeviceAlarmList(Map<String, Object> params);

	long getDeviceAlarmCnt(Map<String, Object> params);

	void insetAlarmInfo(Map<String, Object> params);
}