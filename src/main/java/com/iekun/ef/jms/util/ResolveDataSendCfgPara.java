package com.iekun.ef.jms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.send.SendDataSendTypeCfg;
import com.iekun.ef.util.ConvertTools;

public class ResolveDataSendCfgPara {
	private static Logger logger = LoggerFactory.getLogger(ResolveDataSendCfgPara.class);

	public static String resolve(String data){
		
		SendDataSendTypeCfg RecvDataSendTypeCfg = new SendDataSendTypeCfg();
		
		byte[] nowPara = ConvertTools.hexStringToByte(data); 
		
		byte[] realTimeSendByte = new byte[1];
		System.arraycopy(nowPara, 0, realTimeSendByte, 0, 1);
		int realtimesend = Integer.valueOf(ConvertTools.bytesToHexString(realTimeSendByte), 16);
		RecvDataSendTypeCfg.setRealtimesend(String.valueOf(realtimesend));
		logger.info("realtimesend：" + realtimesend);
		
		byte[] internalMinByte = new byte[1];
		System.arraycopy(nowPara, 1, internalMinByte, 0, 1);
		int internalMin = Integer.valueOf(ConvertTools.bytesToHexString(internalMinByte), 16);
		RecvDataSendTypeCfg.setInteralmin(String.valueOf(internalMin));
		logger.info("internalMin：" + internalMin);
		
		byte[] ueCntSendByte = new byte[2];
		System.arraycopy(nowPara, 2, ueCntSendByte, 0, 2);
		int ueCntSend = Integer.valueOf(ConvertTools.bytesToHexString(ueCntSendByte), 16);
		RecvDataSendTypeCfg.setUecountsend(String.valueOf(ueCntSend));
		logger.info("ueCntSend：" + ueCntSend);
		
		
		return JSONObject.toJSON(RecvDataSendTypeCfg).toString();
	}
}
