package com.iekun.ef.dao;

import com.iekun.ef.model.SysLog;
import com.iekun.ef.model.SysLogExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SysLogMapper {
    int countByExample(SysLogExample example);

    int deleteByExample(SysLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    List<SysLog> selectByExample(SysLogExample example);

    SysLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysLog record, @Param("example") SysLogExample example);

    int updateByExample(@Param("record") SysLog record, @Param("example") SysLogExample example);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

	long getLoggerCnt(Map<String, Object> params);

	List<SysLog> selectLogList(Map<String, Object> params);

	void insertLog(Map<String, Object> params);

	void insertDownloadLog(Map<String,Object> map);

	void updateDownloadLog(Map<String,Object> updateMap);

	Map exportLog(String deviceSn);

    void deleteDownloadLogTaskByDeviceSn(String deviceSn);
}