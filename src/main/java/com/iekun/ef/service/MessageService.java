package com.iekun.ef.service;

import com.iekun.ef.dao.MessageMapper;
import com.iekun.ef.model.Message;
import com.iekun.ef.push.NoticePush;
import com.iekun.ef.util.TimeUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sword.yu on 2016/12/17.
 */

@Service
public class MessageService {

    private static Logger logger = LoggerFactory.getLogger( MessageService.class);

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private NoticePush  noticePush;

    public int createMessage(Long userId, Integer triggerType, String content, String url ) {

        Message message = new Message();

        message.setUserId( userId );
        message.setTriggerType( triggerType );
        message.setContent( content );
        message.setIsRead( false );
        message.setDestUrl( url );
        message.setCreateTime(TimeUtils.timeFormatterStr.format(new Date()));
        messageMapper.insert( message );

        Integer total = getMessageCountByUserIsRead( userId, 0 );
        Map<String, Object> params = new HashedMap();
        params.put("userId", userId );
        params.put("isRead", 0 );
        params.put("start", 0 );
        params.put("length", 30 );
        List<Message>  list =  messageMapper.selectMessageByUserAndIsRead( params );
        noticePush.pushMessages(userId ,total, list );

        return 0;
    }

    public int createMessage(Long userId, Integer triggerType, String content  ) {

        Message message = new Message();

        message.setUserId( userId );
        message.setTriggerType( triggerType );
        message.setContent( content );
        message.setIsRead( false );
        message.setDestUrl( "/user/notifications" );
        message.setCreateTime(TimeUtils.timeFormatterStr.format(new Date()));
        messageMapper.insert( message );

        Integer total = getMessageCountByUserIsRead( userId, 0 );
        Map<String, Object> params = new HashedMap();
        params.put("userId", userId );
        params.put("isRead", 0 );
        params.put("start", 0 );
        params.put("length", 30 );
        List<Message>  list =  messageMapper.selectMessageByUserAndIsRead(params );
        noticePush.pushMessages(userId ,total, list );

        return 0;
    }

    public Integer getMessageCountByUserIsRead( Long userId, Integer isRead ){
        return messageMapper.countMessageByUserAndIsRead( userId, isRead);
    }

    public List<Message> getMessageByUserIsRead( Long userId, Integer start, Integer length, Integer isRead ){

        Map<String, Object> params = new HashedMap();
        params.put("userId", userId );
        params.put("isRead", isRead );
        params.put("start", start );
        params.put("length", length );
        List<Message>  list =  messageMapper.selectMessageByUserAndIsRead( params );

        return list;
    }

    public void markReadMessage( Long id ) {
        messageMapper.markReadById( id );
    }


}
