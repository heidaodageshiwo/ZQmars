package com.iekun.ef.jms.vo;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

//import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSONObject;

import org.springframework.jms.core.MessageCreator;

import com.iekun.ef.jms.vo.Message;

public class MyMessageCreator implements MessageCreator{
	
	private Object jsonObject;
	
	public MyMessageCreator(Message message){
		jsonObject = JSONObject.toJSON(message);
		//jsonObject = JSONObject.fromObject(message);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public javax.jms.Message createMessage(Session session) throws JMSException {
		MapMessage mapMessage = session.createMapMessage();
		HashMap map = new HashMap();
		map.put("messageSend", jsonObject.toString());
		mapMessage.setObject("SBSENDXX", map);
		return mapMessage;
	}

}
