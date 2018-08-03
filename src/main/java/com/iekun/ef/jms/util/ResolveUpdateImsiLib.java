package com.iekun.ef.jms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iekun.ef.util.ConvertTools;

public class ResolveUpdateImsiLib {

	private static int detailLength = 12;
	private static Logger logger = LoggerFactory.getLogger(ResolveUpdateImsiLib.class);
	
	public static String resolve(String data){
		
		StringBuffer value = new StringBuffer();
		
		byte[] nowPara = ConvertTools.hexStringToByte(data); 
		
		byte[] countInLibByte = new byte[4];
		System.arraycopy(nowPara, 4, countInLibByte, 0, 4);
		int countInLib = Integer.valueOf(ConvertTools.bytesToHexString(countInLibByte), 16);
		value.append("Imsi个数：" + countInLib + "\r\n");
		logger.info("countInLib：" + countInLib);
		
		byte[] recvImsiByte = new byte[countInLib*detailLength];
		System.arraycopy(nowPara, 8, recvImsiByte, 0, countInLib*detailLength);

		byte[] ibImsiByte = new byte[8];
		byte[] propertyByte = new byte[1];
		String ibImsi = "";
		int property = 0;
		
		for(int i = 0; i < countInLib; i++){
			System.arraycopy(recvImsiByte, (i * detailLength), ibImsiByte, 0, 8);
			ibImsi = ConvertTools.byteImsi2String(ibImsiByte);
			value.append("Imsi：" + ibImsi + "为");
			logger.info("ibImsi：" + ibImsi);
			
			System.arraycopy(recvImsiByte, (8 + i * detailLength), propertyByte, 0, 1);
			property = Integer.valueOf(ConvertTools.bytesToHexString(propertyByte), 16);
			value.append(ResolveUpdateImsiLib.convertProperty(property) + "\r\n");
			logger.info("property：" + property);
		}
		
		return value.toString();
	}

	public static String convertProperty(int property){
		String result = "";
		switch (property) {
		case 1:
			result = "黑名单";
			break;
		case 2:
			result = "白名单";
			break;
		default:
			break;
		}
		
		return result;
	}
}
