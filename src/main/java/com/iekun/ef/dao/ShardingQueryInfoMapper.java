package com.iekun.ef.dao;

import com.iekun.ef.model.ShardingQueryInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by sword.yu on 2017/1/16.
 */
public interface ShardingQueryInfoMapper {

    /**
     * 创建查询信息临时表信息存储内存表
     * @return
     */
    int createQueryTab();

    /**
     * 记录查询条件对应临时表
     * @param queryInfo
     * @return
     */
    int insertQueryInfo(ShardingQueryInfo queryInfo );

    /**
     * 根据查询条件MD5值，查到临时表
     * @param queryInfo
     * @return
     */
    ShardingQueryInfo selectByQueryInfo( @Param(value="queryInfo")  String queryInfo);

    /**
     * 删除指定时间之前的记录信息
     * @param beforeTime
     * @return
     */
    int deleteQueryInfoByTime( @Param(value="beforeTime")  String beforeTime );



}
