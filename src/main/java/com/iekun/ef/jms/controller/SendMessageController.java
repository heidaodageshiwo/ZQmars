package com.iekun.ef.jms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.service.HandleRcvMessage;
//import com.iekun.ef.jms.service.IHandleSendMessageService;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.send.SendRfPara;
//import com.opensymphony.xwork2.ActionSupport;


@Controller
public class SendMessageController { 

	@Autowired
	//@Qualifier("iHandleSendMessageService")
	private HandleRcvMessage iHandleSendMessageService;
	
	/**
	 * 可以在SendMessage中添加更多发送消息结构体，需要在实体的构造方法中自行添加实现
	 * 比如这里我只添加了频射信息recvRfPara，后续还可以自行添加更多
	 * @throws Exception
	 */
	public void sendRecvRfParaMessage()throws Exception{
		iHandleSendMessageService.handleSendMessage(new Message(head, pktType, serialNum, dataLength, sessionId, recvRfPara));
	}
	
	/***********************************************************************************************/
	private String head;
	private String pktType;
	private String serialNum;
	private int dataLength; 
	private String sessionId;
	private SendRfPara recvRfPara;

	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public SendRfPara getRecvRfPara() {
		return recvRfPara;
	}
	public void setRecvRfPara(SendRfPara recvRfPara) {
		this.recvRfPara = recvRfPara;
	}
	public String getPktType() {
		return pktType;
	}
	public void setPktType(String pktType) {
		this.pktType = pktType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
