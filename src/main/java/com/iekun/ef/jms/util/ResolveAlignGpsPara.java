package com.iekun.ef.jms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.send.SendAlignGps;
import com.iekun.ef.jms.vo.send.SendRfPara;
import com.iekun.ef.util.ConvertTools;

public class ResolveAlignGpsPara {

	
	private static Logger logger = LoggerFactory.getLogger(ResolveAlignGpsPara.class);

	public static String resolve(String data){
		
		SendAlignGps RecvAlignGps = new SendAlignGps();
		
		byte[] nowPara = ConvertTools.hexStringToByte(data); 
		
		byte[] nowSysNoByte = new byte[4];
		System.arraycopy(nowPara, 0, nowSysNoByte, 0, 4);
		int nowSysNo = Integer.valueOf(ConvertTools.bytesToHexString(nowSysNoByte), 16);
		RecvAlignGps.setNowSysNo(String.valueOf(nowSysNo));
		logger.info("nowSysNo：" + nowSysNo);

		byte[] offsetByte = new byte[4];
		System.arraycopy(nowPara, 4, offsetByte, 0, 4);
		int offset = Integer.valueOf(ConvertTools.bytesToHexString(offsetByte), 16);
		RecvAlignGps.setOffset(String.valueOf(offset));
		logger.info("offset：" + offset);

		byte[] friendoffsetByte = new byte[4];
		System.arraycopy(nowPara, 8, friendoffsetByte, 0, 4);
		int friendoffset = Integer.valueOf(ConvertTools.bytesToHexString(friendoffsetByte), 16);
		RecvAlignGps.setFriendOffset(String.valueOf(friendoffset));
		logger.info("offset：" + friendoffset);

		return JSONObject.toJSON(RecvAlignGps).toString();
	}
	
}
