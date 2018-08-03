package com.iekun.ef.dao;

import com.iekun.ef.model.Message;
import com.iekun.ef.model.MessageExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MessageMapper {
    int countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Long id);

    int insertSelective(Message record);

    List<Message> selectByExampleWithBLOBs(MessageExample example);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExampleWithBLOBs(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

    int insert(Message record);

    int countMessageByUserAndIsRead( @Param(value="userId")  Long userId, @Param(value="isRead")  Integer isRead );

    List<Message> selectMessageByUserAndIsRead( Map<String, Object> params );
    int deleteById( Long id );

    int markReadById( Long id );


}