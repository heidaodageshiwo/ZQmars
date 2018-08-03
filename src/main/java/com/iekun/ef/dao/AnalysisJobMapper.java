package com.iekun.ef.dao;

import com.iekun.ef.model.DataAnalysis;
import com.iekun.ef.model.UeInfo;

import java.util.List;
import java.util.Map;

public interface AnalysisJobMapper {

    /**
     * 新增任务
     * */
    void creatorJob(DataAnalysis collisionAnalysis);

    /**
     * 根据用户查询所有任务
     * */
    List<DataAnalysis> queryData(Map<String, Object> params );

    /**
     * 删除任务
     * */
    void updateDeleteFlag(String taskId);

    List<DataAnalysis> getAnalysisJob(Map<String, Object> params);

    DataAnalysis getDataCollideTaskById(String taskId);

    /**
     * 查询详情
     * */
    List<Map> queryRowDataCollide(Map map);

    /**
     * imsi号码对应的站点信息
     * */
    List<UeInfo> showThisRowData(Map map);

    /**
     * 通过IMSI查询对应站点
     */
    List<String> querySitesByImsi(Map map);

    /**
     * 创建文件查询总数
     * */
    int queryCountByTaskId(Map map);

    /**
     * 分批次查询
     * */
    List<UeInfo> queryDataToCreateFile(Map maps);

    /**
     * 数据碰撞分析查询详情
     * */
    List<Map> queryRowDataCollideByImsi(Map map);

    /**
     * imsi伴随分析查询详情
     * */
    List<Map> imsiFollowDetails(Map map);


}
