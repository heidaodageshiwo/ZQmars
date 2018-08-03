package com.iekun.ef.dao;

import com.iekun.ef.model.Outbox;
import com.iekun.ef.model.OutboxExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OutboxMapper {
    int countByExample(OutboxExample example);

    int deleteByExample(OutboxExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Outbox record);

    int insertSelective(Outbox record);

    List<Outbox> selectByExampleWithBLOBs(OutboxExample example);

    List<Outbox> selectByExample(OutboxExample example);

    Outbox selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Outbox record, @Param("example") OutboxExample example);

    int updateByExampleWithBLOBs(@Param("record") Outbox record, @Param("example") OutboxExample example);

    int updateByExample(@Param("record") Outbox record, @Param("example") OutboxExample example);

    int updateByPrimaryKeySelective(Outbox record);

    int updateByPrimaryKeyWithBLOBs(Outbox record);

    int updateByPrimaryKey(Outbox record);

	List<Outbox> selectOutboxList(HashMap<String, Object> params);

	void updateById(HashMap<String, Object> params);

	long getOutboxCnt(Map<String, Object> params);

	List<Outbox> queryOutboxList(HashMap<String, Object> params);

    long   getEmailCount(  HashMap<String, Object> params );

    List<Outbox> getEmails( HashMap<String, Object> params );

    List<Outbox> queryHistory( HashMap<String, Object> params );

    List<Outbox> queryEmailHistory(HashMap<String, Object> params);

    Outbox  getEmail( Long id );

    int updateEmail( Outbox outbox );
    
	long getSmsOutboxCnt(Map<String, Object> params);

	List<Outbox> querySmsOutboxList(HashMap<String, Object> params);

}