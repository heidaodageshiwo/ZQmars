package com.iekun.ef.jms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.send.SendFunctionPara;
import com.iekun.ef.util.ConvertTools;

public class ResolveFunctionPara {
	
	private static Logger logger = LoggerFactory.getLogger(ResolveFunctionPara.class);

	public static String resolve(String data){
		
//		StringBuffer value = new StringBuffer();
		
		SendFunctionPara recvFunctionPara = new SendFunctionPara();
		
		byte[] nowPara = ConvertTools.hexStringToByte(data); 
		
		byte[] paraMccByte = new byte[3];
		System.arraycopy(nowPara, 1, paraMccByte, 0, 3);
		/*由于没配置的情况下，传过来的值为000，所以应闻远要求，不取传过来的值，强制改成 460 */
		paraMccByte[0] = 4;
		paraMccByte[1] = 6;
		paraMccByte[2] = 0;
		int paraMcc = ResolveFunctionPara.getChar(paraMccByte);
//		value.append("移动国家码：" + paraMcc + "\r\n");
		recvFunctionPara.setParaMcc(String.valueOf(paraMcc));
		logger.info("paraMcc：" + paraMcc);
		
		byte[] paraMncByte = new byte[2];
		System.arraycopy(nowPara, 4, paraMncByte, 0, 2);
		int paraMnc = ResolveFunctionPara.getChar(paraMncByte);
//		value.append("移动网号：" + ResolveFunctionPara.convertParaMnc(paraMnc) + "\r\n");
		/* 小于2位，在转为字符串的时候需要补齐成2位  */
		if (paraMnc > 9)
		{
			recvFunctionPara.setParaMnc(String.valueOf(paraMnc));
		}
		else
		{
			recvFunctionPara.setParaMnc("0" + String.valueOf(paraMnc));
		}
		
		logger.info("paraMnc：" + paraMnc);
		
		byte[] boolStartByte = new byte[2];
		System.arraycopy(nowPara, 6, boolStartByte, 0, 2);
		int boolStart = Integer.valueOf(ConvertTools.bytesToHexString(boolStartByte), 16);
//		value.append("上电后是否自动建立小区：" + ResolveFunctionPara.convertBoolStart(boolStart) + "\r\n");
		recvFunctionPara.setBoolStart(String.valueOf(boolStart));
		logger.info("boolStart：" + boolStart);
		
		byte[] paraPciNoByte = new byte[2];
		System.arraycopy(nowPara, 8, paraPciNoByte, 0, 2);
		int paraPciNo = Integer.valueOf(ConvertTools.bytesToHexString(paraPciNoByte), 16);
//		value.append("物理层小区号：" + paraPciNo + "\r\n");
		recvFunctionPara.setParaPcino(String.valueOf(paraPciNo));
		logger.info("paraPciNo：" + paraPciNo);
		
		byte[] paraPeriByte = new byte[4];
		System.arraycopy(nowPara, 16, paraPeriByte, 0, 4);
		int paraPeri = Integer.valueOf(ConvertTools.bytesToHexString(paraPeriByte), 16);
//		value.append("强制上号周期：" + paraPeri + "\r\n");
		recvFunctionPara.setParaPeri(String.valueOf(paraPeri));
		logger.info("paraPeri：" + paraPeri);
		
		byte[] controlRangeByte = new byte[1];
		System.arraycopy(nowPara, 20, controlRangeByte, 0, 1);
		int controlRange = Integer.valueOf(ConvertTools.bytesToHexString(controlRangeByte), 16);
//		value.append("控制范围：" + ResolveFunctionPara.convertControlRange(controlRange) + "\r\n");
		recvFunctionPara.setControlRange(String.valueOf(controlRange));
		logger.info("controlRange：" + controlRange);
		
		byte[] iNandOUTtypeByte = new byte[1];
		System.arraycopy(nowPara, 21, iNandOUTtypeByte, 0, 1);
		int iNandOUTtype = Integer.valueOf(ConvertTools.bytesToHexString(iNandOUTtypeByte), 16);
//		value.append("UE接入后已什么样的方式出去，是否允许再次接入：" + ResolveFunctionPara.convertINandOUTtype(controlRange) + "\r\n");
		recvFunctionPara.setInandOutType(String.valueOf(iNandOUTtype));
		logger.info("iNandOUTtype：" + iNandOUTtype);
		
		byte[] debug2Byte = new byte[1];
		System.arraycopy(nowPara, 22, debug2Byte, 0, 1);
		int debug2 = Integer.valueOf(ConvertTools.bytesToHexString(debug2Byte), 16);
//		value.append("debug2：" + debug2 + "\r\n");
		recvFunctionPara.setDebug(String.valueOf(debug2));
		logger.info("debug2：" + debug2);
		
		byte[] aRFCNByte = new byte[2];
		System.arraycopy(nowPara, 24, aRFCNByte, 0, 2);
		int aRFCN = Integer.valueOf(ConvertTools.bytesToHexString(aRFCNByte), 16);
//		value.append("控制范围内UE 指向的频点：" + aRFCN + "\r\n");
		recvFunctionPara.setArfcn(String.valueOf(aRFCN));
		logger.info("aRFCN：" + aRFCN);

		byte[] aRFCNByte1 = new byte[2];
		System.arraycopy(nowPara, 26, aRFCNByte1, 0, 2);
		int aRFCN1 = Integer.valueOf(ConvertTools.bytesToHexString(aRFCNByte1), 16);
		recvFunctionPara.setArfcn1(String.valueOf(aRFCN1));

		byte[] aRFCNByte2 = new byte[2];
		System.arraycopy(nowPara, 28, aRFCNByte2, 0, 2);
		int aRFCN2 = Integer.valueOf(ConvertTools.bytesToHexString(aRFCNByte2), 16);
		recvFunctionPara.setArfcn2(String.valueOf(aRFCN2));

		byte[] aRFCNByte3 = new byte[2];
		System.arraycopy(nowPara, 30, aRFCNByte3, 0, 2);
		int aRFCN3 = Integer.valueOf(ConvertTools.bytesToHexString(aRFCNByte3), 16);
		recvFunctionPara.setArfcn3(String.valueOf(aRFCN3));
		return JSONObject.toJSON(recvFunctionPara).toString();
	}
	
	private static int getChar(byte[] src){
		StringBuffer sb = new StringBuffer();
		byte[] x = new byte[1];
		String temp = "";
		
		for(int i = 0; i < src.length; i++){
			System.arraycopy(src, i, x, 0, 1);
			temp = ConvertTools.bytesToHexString(x);
			sb.append(String.valueOf(Integer.valueOf(temp)));
		}
		
		return Integer.valueOf(sb.toString());
	}

	public static String convertParaMnc(int paraMnc){
		String result = "";
		switch (paraMnc) {
		case 0:
			result = "移动";
			break;
		case 1:
			result = "联通";
			break;
		case 2:
			result = "移动";
			break;
		case 3:
			result = "电信";
			break;
		case 5:
			result = "电信";
			break;
		case 6:
			result = "联通";
			break;
		case 7:
			result = "移动";
			break;
		case 11:
			result = "电信";
			break;
		default:
			break;
		}
		
		return result;
	}
	
	public static String convertBoolStart(int boolStart){
		String result = "";
		switch (boolStart) {
		case 0:
			result = "不自动建立小区，等待连接上后，开启RF再建立小区";
			break;
		case 1:
			result = "自动建立小区";
			break;
		default:
			break;
		}
		
		return result;
	}
	
	public static String convertControlRange(int controlRange){
		String result = "";
		switch (controlRange) {
		case 1:
			result = "黑名单";
			break;
		case 2:
			result = "全部";
			break;
		default:
			break;
		}
		
		return result;
	}
	
	public static String convertINandOUTtype(int iNandOUTtype){
		String result = "";
		switch (iNandOUTtype) {
		case 1:
			result = "控制范围内，允许反复接入";
			break;
		case 2:
			result = "控制范围外，接入一次后不再接入";
			break;
		case 3:
			result = "控制范围内，接入后手机无信号";
			break;
		case 4:
			result = "接入一次后，按照周期再拉回来";
			break;
		case 5:
			result = "持续上报场强";
			break;
		default:
			break;
		}
		
		return result;
	}
}
