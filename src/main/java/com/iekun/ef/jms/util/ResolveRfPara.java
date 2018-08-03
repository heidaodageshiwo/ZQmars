package com.iekun.ef.jms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.send.SendRfPara;
import com.iekun.ef.util.ConvertTools;

public class ResolveRfPara {
	
	private static Logger logger = LoggerFactory.getLogger(ResolveRfPara.class);

	public static String resolve(String data){
		
		SendRfPara recvRfPara = new SendRfPara();
		
//		StringBuffer value = new StringBuffer();
		
		byte[] nowPara = ConvertTools.hexStringToByte(data); 
		
		byte[] rfEnableByte = new byte[1];
		System.arraycopy(nowPara, 2, rfEnableByte, 0, 1);
		int rfEnable = Integer.valueOf(ConvertTools.bytesToHexString(rfEnableByte), 16);
//		value.append("射频使能：" + ResolveRfPara.convertRfEnable(rfEnable) + "\r\n");
		recvRfPara.setRfEnable(String.valueOf(rfEnable));
		logger.info("rfEnable：" + rfEnable);
		
		byte[] fastConfigEarfcnByte = new byte[1];
		System.arraycopy(nowPara, 3, fastConfigEarfcnByte, 0, 1);
		int fastConfigEarfcn = Integer.valueOf(ConvertTools.bytesToHexString(fastConfigEarfcnByte), 16);
//		value.append("快速配置频点：" + ResolveRfPara.convertFastConfigEarfcn(fastConfigEarfcn) + "\r\n");
		recvRfPara.setFastConfigEarfcn(String.valueOf(fastConfigEarfcn));
		logger.info("fastConfigEarfcn：" + fastConfigEarfcn);
		
		byte[] eutraBandByte = new byte[2];
		System.arraycopy(nowPara, 4, eutraBandByte, 0, 2);
		int eutraBand = Integer.valueOf(ConvertTools.bytesToHexString(eutraBandByte), 16);
//		value.append("频带号：" + eutraBand + "\r\n");
		recvRfPara.setEutraBand(String.valueOf(eutraBand));
		logger.info("eutraBand：" + eutraBand);
		
		byte[] dlearfcnByte = new byte[2];
		System.arraycopy(nowPara, 6, dlearfcnByte, 0, 2);
		int dlearfcn = Integer.valueOf(ConvertTools.bytesToHexString(dlearfcnByte), 16);
//		value.append("信道号EARFCN：" + dlearfcn + "\r\n");
		recvRfPara.setDlEarfcn(String.valueOf(dlearfcn));
		logger.info("dlearfcn：" + dlearfcn);
		
		byte[] ulEarfcnByte = new byte[2];
		System.arraycopy(nowPara, 8, ulEarfcnByte, 0, 2);
		int ulEarfcn = Integer.valueOf(ConvertTools.bytesToHexString(ulEarfcnByte), 16);
//		value.append("EARFCN：" + ulEarfcn + "\r\n");
		recvRfPara.setUlEarfcn(String.valueOf(ulEarfcn));
		logger.info("ulEarfcn：" + ulEarfcn);
		
		byte[] frameStrucureTypeByte = new byte[1];
		System.arraycopy(nowPara, 10, frameStrucureTypeByte, 0, 1);
		int frameStrucureType = Integer.valueOf(ConvertTools.bytesToHexString(frameStrucureTypeByte), 16);
//		value.append("FrameStrucureType：" + ResolveRfPara.convertFrameStrucureType(frameStrucureType) + "\r\n");
		recvRfPara.setFrameStrucureType(String.valueOf(frameStrucureType));
		logger.info("frameStrucureType：" + frameStrucureType);
		
		byte[] subframeAssinmentByte = new byte[1];
		System.arraycopy(nowPara, 11, subframeAssinmentByte, 0, 1);
		int subframeAssinment = Integer.valueOf(ConvertTools.bytesToHexString(subframeAssinmentByte), 16);
//		value.append("上下行时隙配置：" + subframeAssinment + "\r\n");
		recvRfPara.setSubframeAssinment(String.valueOf(subframeAssinment));
		logger.info("subframeAssinment：" + subframeAssinment);
		
		byte[] specialSubframePatternsByte = new byte[1];
		System.arraycopy(nowPara, 12, specialSubframePatternsByte, 0, 1);
		int specialSubframePatterns = Integer.valueOf(ConvertTools.bytesToHexString(specialSubframePatternsByte), 16);
//		value.append("特殊子帧配置：" + specialSubframePatterns + "\r\n");
		recvRfPara.setSpecialSubframePatterns(String.valueOf(specialSubframePatterns));
		logger.info("specialSubframePatterns：" + specialSubframePatterns);
		
		byte[] dlBandWidthByte = new byte[1];
		System.arraycopy(nowPara, 13, dlBandWidthByte, 0, 1);
		int dlBandWidth = Integer.valueOf(ConvertTools.bytesToHexString(dlBandWidthByte), 16);
//		value.append("DlBandWidth：" + dlBandWidth + "\r\n");
		recvRfPara.setDlBandWidth(String.valueOf(dlBandWidth));
		logger.info("dlBandWidth：" + dlBandWidth);
		
		byte[] ulBandWidthByte = new byte[1];
		System.arraycopy(nowPara, 14, ulBandWidthByte, 0, 1);
		int ulBandWidth = Integer.valueOf(ConvertTools.bytesToHexString(ulBandWidthByte), 16);
//		value.append("UlBandWidth：" + ulBandWidth + "\r\n");
		recvRfPara.setUlBandWidth(String.valueOf(ulBandWidth));
		logger.info("ulBandWidth：" + ulBandWidth);
		
		byte[] rFchoiceByte = new byte[1];
		System.arraycopy(nowPara, 15, rFchoiceByte, 0, 1);
		int rFchoice = Integer.valueOf(ConvertTools.bytesToHexString(rFchoiceByte), 16);
//		value.append("选择PA：" + ResolveRfPara.convertRFchoice(rFchoice) + "\r\n");
		recvRfPara.setrFchoice(String.valueOf(rFchoice));
		logger.info("rFchoice：" + rFchoice);
		
		byte[] tX1PowerAttenuationByte = new byte[2];
		System.arraycopy(nowPara, 16, tX1PowerAttenuationByte, 0, 2);
		int tX1PowerAttenuation = Integer.valueOf(ConvertTools.bytesToHexString(tX1PowerAttenuationByte), 16);
//		value.append("TX1功率衰减值：" + tX1PowerAttenuation + "\r\n");
		recvRfPara.setTx1PowerAttenuation(String.valueOf(tX1PowerAttenuation));
		logger.info("tX1PowerAttenuation：" + tX1PowerAttenuation);
		
		byte[] tX2PowerAttenuationByte = new byte[2];
		System.arraycopy(nowPara, 18, tX2PowerAttenuationByte, 0, 2);
		int tX2PowerAttenuation = Integer.valueOf(ConvertTools.bytesToHexString(tX2PowerAttenuationByte), 16);
//		value.append("TX2功率衰减值：" + tX2PowerAttenuation + "\r\n");
		recvRfPara.setTx2PowerAttenuation(String.valueOf(tX2PowerAttenuation));
		logger.info("tX2PowerAttenuation：" + tX2PowerAttenuation);
		
		return JSONObject.toJSON(recvRfPara).toString();
	}
	
	public static String convertRfEnable(int rfEnable){
		String result = "";
		switch (rfEnable) {
		case 0:
			result = "关闭";
			break;
		case 1:
			result = "开启";
			break;
		default:
			break;
		}
		
		return result;
	}
	
	public static String convertFastConfigEarfcn(int fastConfigEarfcn){
		String result = "";
		switch (fastConfigEarfcn) {
		case 0:
			result = "正常配置AD9361";
			break;
		case 1:
			result = "快速配置频点";
			break;
		default:
			break;
		}
		
		return result;
	}
	
	public static String convertFrameStrucureType(int frameStrucureType){
		String result = "";
		switch (frameStrucureType) {
		case 0:
			result = "TDD";
			break;
		case 1:
			result = "FDD";
			break;
		default:
			break;
		}
		
		return result;
	}
	
	public static String convertRFchoice(int rFchoice){
		String result = "";
		switch (rFchoice) {
		case 0:
			result = "RRU";
			break;
		case 1:
			result = "9361单板";
			break;
		case 2:
			result = "BAND38_250mw";
			break;
		case 3:
			result = "BAND3_20w";
			break;
		case 4:
			result = "BAND39_20w";
			break;
		case 5:
			result = "BAND38_10w";
			break;
		case 6:
			result = "BAND1_20w";
			break;
		default:
			break;
		}
		
		return result;
	}
}
