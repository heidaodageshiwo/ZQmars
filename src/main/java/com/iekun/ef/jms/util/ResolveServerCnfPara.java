package com.iekun.ef.jms.util;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.send.SendAlignGps;
import com.iekun.ef.jms.vo.send.SendServerCnf;
import com.iekun.ef.util.ConvertTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolveServerCnfPara {

	
	private static Logger logger = LoggerFactory.getLogger(ResolveServerCnfPara.class);

	public static String resolve(String data){

		SendServerCnf sendServerCnf = new SendServerCnf();
		
		byte[] nowPara = ConvertTools.hexStringToByte(data); 
		
		byte[] serverAddrTypeByte = new byte[1];
		System.arraycopy(nowPara, 0, serverAddrTypeByte, 0, 1);
		int serverAddrType = Integer.valueOf(ConvertTools.bytesToHexString(serverAddrTypeByte), 16);
		sendServerCnf.setServerAddrType(String.valueOf(serverAddrType));

		byte[] serverAddrByte = new byte[32];
		System.arraycopy(nowPara, 2, serverAddrByte, 0, 32);
		int length = -1;
		for(int j = 0; j < 32; ++j) {
			if(serverAddrByte[j] == 0) {
				length = j;
				break;
			}
		}
		String serverAddr = new String(serverAddrByte, 0, length);
		sendServerCnf.setServerAddr(serverAddr);

		byte[] serverPortByte = new byte[2];
		System.arraycopy(nowPara, 34, serverPortByte, 0, 2);
		int serverPort = Integer.valueOf(ConvertTools.bytesToHexString(serverPortByte), 16);
		sendServerCnf.setServerPort(String.valueOf(serverPort));

		byte[] serverTryConnectionTimeByte = new byte[2];
		System.arraycopy(nowPara, 36, serverTryConnectionTimeByte, 0, 2);
		int serverTryConnectionTime = Integer.valueOf(ConvertTools.bytesToHexString(serverTryConnectionTimeByte), 16);
		sendServerCnf.setServerTryConnectionTime(String.valueOf(serverTryConnectionTime));

		byte[] secondServerEnableByte = new byte[1];
		System.arraycopy(nowPara, 38, secondServerEnableByte, 0, 1);
		int secondServerEnable = Integer.valueOf(ConvertTools.bytesToHexString(secondServerEnableByte), 16);
		sendServerCnf.setSecondServerEnable(String.valueOf(secondServerEnable));

		byte[] secondServerAddrTypeByte = new byte[1];
		System.arraycopy(nowPara, 39, secondServerAddrTypeByte, 0, 1);
		int secondServerAddrType = Integer.valueOf(ConvertTools.bytesToHexString(secondServerAddrTypeByte), 16);
		sendServerCnf.setSecondServerAddrType(String.valueOf(secondServerAddrType));

		byte[] secondServerAddrByte = new byte[32];
		System.arraycopy(nowPara, 40, secondServerAddrByte, 0, 32);
		length = -1;
		for(int j = 0; j < 32; ++j) {
			if(secondServerAddrByte[j] == 0) {
				length = j;
				break;
			}
		}
		String secondServerAddr = new String(secondServerAddrByte, 0, length);
		sendServerCnf.setSecondServerAddr(secondServerAddr);

		byte[] secondServerPortByte = new byte[2];
		System.arraycopy(nowPara, 72, secondServerPortByte, 0, 2);
		int secondServerPort = Integer.valueOf(ConvertTools.bytesToHexString(secondServerPortByte), 16);
		sendServerCnf.setSecondServerPort(String.valueOf(secondServerPort));

		byte[] secondServerConnectionTimeByte = new byte[2];
		System.arraycopy(nowPara, 74, secondServerConnectionTimeByte, 0, 2);
		int secondServerConnectionTime = Integer.valueOf(ConvertTools.bytesToHexString(secondServerConnectionTimeByte), 16);
		sendServerCnf.setSecondServerConnectionTime(String.valueOf(secondServerConnectionTime));

		return JSONObject.toJSON(sendServerCnf).toString();
	}
	
}
