package com.iekun.ef.dao;

import com.iekun.ef.model.NewHistoryParamsEntity;
import com.iekun.ef.model.SelfAreaCode;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.UeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NewHistoryMapper {

    /**
     * 查询时间插件开始时间
     * */
    String getUeinfoToDaterangepicker();
    /**
     * 查询总数
     * */
    public int selectSCount(@Param("tableName")String tableName);

    /**
     *根据查询条件查询总数
     * */
    public int selectWCount(NewHistoryParamsEntity item);

    /**
     * 根据查询条件分页查询
     * */
    public List<UeInfo> selectHistoryRecords(Map<String, Object> params);

    /**
     * 查询要导出的数据
     * */
    public List<UeInfo> queryUeinfo(Map map);

    /**
     * 通过用户ID查询对应的站点
     * */
    List<Site> querySiteByUserId(long userId);

}
