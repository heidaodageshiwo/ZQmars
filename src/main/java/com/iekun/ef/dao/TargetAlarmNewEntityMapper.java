package com.iekun.ef.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.iekun.ef.model.TargetAlarmNewEntity;

public interface TargetAlarmNewEntityMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("createTime") String createTime);

    int insert(TargetAlarmNewEntity record);

    TargetAlarmNewEntity selectByPrimaryKey(@Param("id") Long id, @Param("createTime") String createTime);

    List<TargetAlarmNewEntity> selectAll();

    List<TargetAlarmNewEntity> selectAllHistoryDataByUserId(Map<String, Object> params);

    List<TargetAlarmNewEntity> selectCurTargetByNameStr(Map<String, String> params);

    int updateByPrimaryKey(TargetAlarmNewEntity record);

    int updateReadedById(Map<String, Object> params);

    long queryAllBlackListAlarmCount();

    long queryAllBlackAreaAlarmCount();

    int queryCountByParams(Map<String, Object> params);

    List<TargetAlarmNewEntity> queryHistoryAlarmDataLimit(Map<String, Object> params);
}
