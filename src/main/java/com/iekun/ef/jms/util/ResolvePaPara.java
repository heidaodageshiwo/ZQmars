package com.iekun.ef.jms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.send.SendPaPara;
import com.iekun.ef.util.ConvertTools;

public class ResolvePaPara {
	private static Logger logger = LoggerFactory.getLogger(ResolvePaPara.class);

	public static String resolve(String data){
		
		SendPaPara sendPaPara = new SendPaPara();
		
		byte[] nowPara = ConvertTools.hexStringToByte(data); 
		
		byte[] rfSysNoByte = new byte[1];
		System.arraycopy(nowPara, 0, rfSysNoByte, 0, 1);
		int rfSysNo = Integer.valueOf(ConvertTools.bytesToHexString(rfSysNoByte), 16);
		sendPaPara.setRfSysNo(String.valueOf(rfSysNo));
		logger.info("rfSysNo：" + rfSysNo);
		
		byte[] powerAttenuationByte = new byte[2];
		System.arraycopy(nowPara, 2, powerAttenuationByte, 0, 2);
		int powerAttenuation = Integer.valueOf(ConvertTools.bytesToHexString(powerAttenuationByte), 16);
		sendPaPara.setPowerAttenuation(String.valueOf(powerAttenuation));
		logger.info("powerAttenuation：" + powerAttenuation);
			
		return JSONObject.toJSON(sendPaPara).toString();
	}
}
