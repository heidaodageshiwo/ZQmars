package com.iekun.ef.dao;

import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.model.TargetAlarmExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TargetAlarmMapper {
    int countByExample(TargetAlarmExample example);

    int deleteByExample(TargetAlarmExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TargetAlarm record);

    int insertSelective(TargetAlarm record);

    List<TargetAlarm> selectByExample(TargetAlarmExample example);

    TargetAlarm selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TargetAlarm record, @Param("example") TargetAlarmExample example);

    int updateByExample(@Param("record") TargetAlarm record, @Param("example") TargetAlarmExample example);

    int updateByPrimaryKeySelective(TargetAlarm record);

    int updateByPrimaryKey(TargetAlarm record);

	void setIsRead(Long targetId);

	List<TargetAlarm> getCurrentTargetList(Map<String,Object> params);
	
	List<TargetAlarm> selectAllUnReadAlarm();

	List<TargetAlarm> selectAlarmInfoList(Map<String, Object> params);

	long getTotalAlarmCount(Map<String, Object> params);

    long getTargetAlarmTotal( Integer indication );
	
	List<TargetAlarm> getTargetAlarms(Map<String, String> params);
	
	List<TargetAlarm> getAreaTargetAlarms(Map<String, String> params);

}