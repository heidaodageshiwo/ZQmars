package com.iekun.ef.jms.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.MyMessageCreator;
import com.iekun.ef.jms.vo.receive.RcvDevVersion;
import com.iekun.ef.jms.vo.send.SendFunctionPara;
import com.iekun.ef.jms.vo.send.SendImsi;
import com.iekun.ef.jms.vo.send.SendRfPara;
import com.iekun.ef.jms.vo.send.SendUpdateImsiLib;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.model.Device;


import com.alibaba.fastjson.JSONObject;

@Service("iHandleSendMessageService")
@Transactional(rollbackFor=java.lang.Exception.class)
@Scope("prototype")
public class HandleRcvMessage {

	@Autowired
	@Qualifier("jmsTemplate")
	private JmsTemplate jmsTemplate;
	
	//@Autowired
	//@Qualifier("iMessageToDataBaseDao")
	//private IMessageToDataBaseDao iMessageToDataBaseDao;
	
	private MessageToDataBase messageToDb = new MessageToDataBase();
	
	private static final int maxTryTime = 30;
	
	private static Logger logger = LoggerFactory.getLogger(HandleRcvMessage.class);
	
	//@Override
	public String handleSendMessage(Message message)  throws Exception{
		
		JSONObject jSONObject = new JSONObject();
		String result = "";
		
		try{
			messageToDb.addOperLog(message.getPktType(), message.getSerialNum(), 0, Integer.valueOf(message.getSessionId()));
			logger.info("发送消息类型" + message.getPktType() + "对应sessionId：" + message.getSessionId());
			jmsTemplate.send("iekunSendQueue", new MyMessageCreator(message));
			result = this.getResult(message.getSessionId()); 
			if(result == null){
				throw new Exception("命令应答超时");
			}
		}catch(Exception e){
			e.printStackTrace();
			jSONObject.put("success", false);
			jSONObject.put("info", "操作失败：" + e.getMessage());
			throw new Exception(jSONObject.toString());
		}
		
		jSONObject.put("success", true);
		jSONObject.put("info", result);
		
		return jSONObject.toString();
	}

	public void  handleTargetToSigleDevice(Message message, String serialNum)
	{
		messageToDb.addOperLog(message.getPktType(), message.getSerialNum(), 0, Integer.valueOf(message.getSessionId()));
		logger.info("发送消息类型" + message.getPktType() + "对应sessionId：" + message.getSessionId() + "device id: " + serialNum);
		jmsTemplate.send("iekunSendQueue", new MyMessageCreator(message));	
	}
	
	//@Override
	public String getNowPara(Message message, String pktType) throws Exception{
		
		JSONObject jSONObject = new JSONObject();
		String result = "";
		
		try{
			JSONObject jsonObject = new JSONObject();
			try{
				jsonObject = JSONObject.parseObject(this.handleSendMessage(message));
			}catch(Exception e){
				jsonObject = JSONObject.parseObject(e.getMessage());
			}
			
			if(!jsonObject.getBoolean("success")){
				throw new Exception(jsonObject.getString("info").replace("操作失败：", ""));
			}
			
			result = jsonObject.getString("info");
			
			com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(result);
			
			switch (pktType) {
			case "0007":
				SendRfPara recvRfPara = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendRfPara.class);
				this.putRecvRfParaResult(jSONObject, recvRfPara);
				break;
			case "0011":
				SendFunctionPara recvFunctionPara = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendFunctionPara.class);
				this.putRecvFunctionParaResult(jSONObject, recvFunctionPara);
				break;
			case "0008":
				RcvDevVersion SendDevVersion = com.alibaba.fastjson.JSONObject.toJavaObject(object, RcvDevVersion.class);
				this.putSendDevVersion(jSONObject, SendDevVersion);
				break;
			default:
				jSONObject.put("success", false);
				jSONObject.put("info", "命令应答失败：未识别的消息类型");
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
			jSONObject.put("success", false);
			jSONObject.put("info", "操作失败：" + e.getMessage());
			throw new Exception(jSONObject.toString());
		}
		
		jSONObject.put("success", true);
		
		return jSONObject.toString();
	}
	
	/**
	 * 查询版本
	 * @param jSONObject
	 * @param sendDevVersion
	 * @throws Exception
	 */
	private void putSendDevVersion(JSONObject jSONObject, RcvDevVersion sendDevVersion)throws Exception{
		jSONObject.put("type", sendDevVersion.getLicense());
		jSONObject.put("fpgaVersion", sendDevVersion.getFpgaVersion());
		jSONObject.put("bbuVersion", sendDevVersion.getBbuVersion());
		jSONObject.put("softwareVersion", sendDevVersion.getSoftWareVersion());
	}
	
	/**
	 * 形成射频结果集
	 * @param jSONObject
	 * @param recvRfPara
	 * @throws Exception
	 */
	private void putRecvRfParaResult(JSONObject jSONObject, SendRfPara recvRfPara)throws Exception{
		jSONObject.put("rfEnable", recvRfPara.getRfEnable());
		jSONObject.put("fastConfigEarfcn", recvRfPara.getFastConfigEarfcn());
		jSONObject.put("eutraBand", recvRfPara.getEutraBand());
		jSONObject.put("dlEarfcn", recvRfPara.getDlEarfcn());
		jSONObject.put("ulEarfcn", recvRfPara.getUlEarfcn());
		jSONObject.put("frameStrucureType", recvRfPara.getFrameStrucureType());
		jSONObject.put("subframeAssinment", recvRfPara.getSubframeAssinment());
		jSONObject.put("specialSubframePatterns", recvRfPara.getSpecialSubframePatterns());
		jSONObject.put("dlBandWidth", recvRfPara.getDlBandWidth());
		jSONObject.put("ulBandWidth", recvRfPara.getUlBandWidth());
		jSONObject.put("rFchoice", recvRfPara.getrFchoice());
		jSONObject.put("tx1PowerAttenuation", recvRfPara.getTx1PowerAttenuation());
		jSONObject.put("tx2PowerAttenuation", recvRfPara.getTx2PowerAttenuation());
	}
	
	/**
	 * 形成功能参数结果集
	 * @param jSONObject
	 * @param recvFunctionPar
	 * @throws Exception
	 */
	private void putRecvFunctionParaResult(JSONObject jSONObject, SendFunctionPara recvFunctionPar)throws Exception{
		jSONObject.put("paraMcc", recvFunctionPar.getParaMcc());
		jSONObject.put("debug", recvFunctionPar.getDebug());
		jSONObject.put("paraMnc", recvFunctionPar.getParaMnc());
		jSONObject.put("controlRange", recvFunctionPar.getControlRange());
		jSONObject.put("inandOutType", recvFunctionPar.getInandOutType());
		jSONObject.put("boolStart", recvFunctionPar.getBoolStart());
		jSONObject.put("paraPcino", recvFunctionPar.getParaPcino());
		jSONObject.put("paraPeri", recvFunctionPar.getParaPeri());
		jSONObject.put("arfcn", recvFunctionPar.getArfcn());
		jSONObject.put("arfcn1", recvFunctionPar.getArfcn1());
		jSONObject.put("arfcn2", recvFunctionPar.getArfcn2());
		jSONObject.put("arfcn3", recvFunctionPar.getArfcn3());
	}

	/**
	 * 根据sessionId取命令结果
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	private String getResult(String sessionId)throws Exception{
		
		String result = "";
		boolean get = false;
		boolean timeOut = false;
		int tryTime = 0;
		
		while(!get && !timeOut){
			
			result = (String) SigletonBean.getValue(sessionId);
			
			if(result == null){
				tryTime++;
				Thread.sleep(1000);
			}else{
				get = true;
				SigletonBean.removeFromMap(sessionId);
				break;
			}
			
			if(tryTime >= maxTryTime){
				break;
			}
		}
		
		return result;
	}

	public String sendAllTarget(String sessionId) throws Exception {
		JSONObject jSONObject = new JSONObject();
		
		try{
			List<Device> deviceList = this.getDevicesList();
			
			String allTarget = this.getAllTarget();
			
			for(Device device : deviceList){
				jmsTemplate.send("iekunSendQueue", new MyMessageCreator(new Message("FFFF", "00D0", device.getSn(), 20, sessionId, allTarget)));
			}
		}catch(Exception e){
			jSONObject.put("success", false);
			jSONObject.put("info", "操作失败：" + e.getMessage());
			throw new Exception(jSONObject.toString());
		}
		
		jSONObject.put("success", true);
		jSONObject.put("info", "命令发送成功");
		
		return jSONObject.toString();
	}
	
	private String getAllTarget()throws Exception{
		
		List<SendImsi> list = this.getAllTargetServ();
		
		SendUpdateImsiLib recvUpdateImsiLib=new SendUpdateImsiLib();
		recvUpdateImsiLib.setNumInLib(list.size());
		
		recvUpdateImsiLib.setListRecvImsi(list);
		
		return recvUpdateImsiLib.toString();
	}
	
	private List<Device> getDevicesList()
	{
		List<Device> deviceList = null;
		
		return deviceList;
		
	}
	
	private List<SendImsi> getAllTargetServ()
	{
		List<SendImsi> list = null;
		return list;
	}
		
	
	
}
