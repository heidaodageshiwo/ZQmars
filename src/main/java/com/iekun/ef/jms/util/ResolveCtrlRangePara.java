package com.iekun.ef.jms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.send.SendCtrlRange;
import com.iekun.ef.util.ConvertTools;

public class ResolveCtrlRangePara {

	private static Logger logger = LoggerFactory.getLogger(ResolveAlignGpsPara.class);

	public static String resolve(String data){
		
		SendCtrlRange sendCtrlRange = new SendCtrlRange();
		
		byte[] nowPara = ConvertTools.hexStringToByte(data); 
		
		byte[] openByte = new byte[1];
		System.arraycopy(nowPara, 0, openByte, 0, 1);
		int open = Integer.valueOf(ConvertTools.bytesToHexString(openByte), 16);
		sendCtrlRange.setOpen(String.valueOf(open));
		logger.info("open：" + open);
		
		byte[] levByte = new byte[1];
		System.arraycopy(nowPara, 1, levByte, 0, 1);
		//int  lev = Integer.valueOf(ConvertTools.bytesToHexString(levByte), 16);
		Byte levTemp = new Byte(levByte[0]);
		String s = levTemp.toString();
		sendCtrlRange.setLev(s);
		logger.info("lev：" + s);
				
		return JSONObject.toJSON(sendCtrlRange).toString();
	}
	
	



}
