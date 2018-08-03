package com.iekun.ef.dao;

import com.iekun.ef.model.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by feilong.cai on 2016/11/25.
 */

public interface TaskMapper {

    Long getTaskCount( Map<String, Object> params );

    List<Task> getTasksByIsFinish( Integer isFinish );

    List<Task> getTasks( Map<String, Object> params );

    Task getTaskById( Long id );

    int updateTaskById( Task task );

    int deleteByID(Long id);

    int insertTask( Task task );

    int updateTryCount(@Param(value="id") Long id,  @Param(value="tryCount") Integer tryCount );

}
