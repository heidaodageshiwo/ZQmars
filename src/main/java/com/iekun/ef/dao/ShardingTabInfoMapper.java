package com.iekun.ef.dao;

import com.iekun.ef.model.ShardingTabInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sword.yu on 2017/1/16.
 */
public interface ShardingTabInfoMapper {

    /**
     * 添加分片表信息
     * @param tabInfo
     * @return
     */
    int insertTabInfo( ShardingTabInfo tabInfo );

    /**
     * 查询全部分片表信息
     * @return
     */
    List<ShardingTabInfo> selectAll();

    /**
     * 根据时间范围获取分片表信息
     * @param startDate  开始日期
     * @param endDate    结束日期
     * @return
     */
    List<ShardingTabInfo> selectByDate( @Param(value="startDate") String startDate,
                                        @Param(value="endDate") String endDate );

    /**
     * 查询某一天的分片表信息
     * @param oneDay  日期
     * @return
     */
    ShardingTabInfo selectByOneDay( @Param(value="oneDay") String oneDay );

}

