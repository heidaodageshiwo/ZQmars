package com.iekun.ef.util;

import java.util.regex.Pattern;

public class ConvertTools {
    
    private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/*
	 * 把16进制字符串转换成字节数组 @param hex @return
	 */
	public static final byte[] hexStringToByte(String hex) {
	    char[] data = hex.toCharArray();
        int l = data.length;

        byte[] out = new byte[l >> 1];
        int i = 0;
        for (int j = 0; j < l;) {
            int f = Character.digit(data[(j++)], 16) << 4;
            f |= Character.digit(data[(j++)], 16);
            out[i] = (byte) (f & 0xFF);
            ++i;
        }

        return out;
	}

	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
	    char[] buf = new char[bArray.length * 2];
        int j;
        int i = j = 0;
        for (; i < bArray.length; ++i) {
            int k = bArray[i];
            buf[(j++)] = hex[(k >>> 4 & 0xF)];
            buf[(j++)] = hex[(k & 0xF)];
        }
        return new String(buf);
	}


	/**
	 * @函数功能: BCD码转为10进制串(阿拉伯数据)
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static final String bcd2Str(byte[] bytes) {
		StringBuilder temp = new StringBuilder(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		// return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
		// .toString().substring(1) : temp.toString();
		return temp.toString();
	}

	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static final byte[] str2Bcd(String asc) {
		String asc1 = asc;
		int len = asc1.length();
		int mod = len % 2;

		if (mod != 0) {
			asc1 = "0" + asc1;
			len = asc1.length();
		}

		byte abt[] = null;
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc1.getBytes();
		int j, k;

		for (int p = 0; p < asc1.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
	
	public static String fillZero(String src, int totalLength){
		String result = src;
		
		if(src.length() >= totalLength){
			return src.substring(0, totalLength);
		}
		
		for(int i = 0; i < (totalLength - src.length()); i++){
			result = "0" + result;
		}
		return result;
	}

	public static String getNeedStr42G(String src) {
		String desc = "";
		int length = src.length();
		int max = length / 2;
		for (int j = 1; j <= max; ++j) {
			int begin = length - j * 2;
			desc += src.substring(begin, begin + 2);
		}
		return desc;
	}
	public static String getNeedStr42GTrun(String src) {
		String desc = "";
		int length = src.length();
		int max = length / 2;
		for (int j = 1; j <= max; ++j) {
			int begin = length - j * 2;
			desc += src.substring(begin, begin + 2);
		}
		return desc;
	}

	public static String fillZeroToEnd(String src, int totalLength){
		String result = src;
		//src.getBytes("");
		if(src.length() >= totalLength){
			return src.substring(0, totalLength);
		}
		
		for(int i = 0; i < (totalLength - src.length()); i++){
			result =  result + "0";
		}
		return result;
	}

	public static byte[] getLengthByte(int length){
		String hexLength = Integer.toHexString(length);
		String result = hexLength;
		for(int i = 0; i < (8 - hexLength.length()); i++){
			result = "0" + result;
		}
		return ConvertTools.hexStringToByte(result);
	}
	
	public static String getLengthHexString(int length){
		String hexLength = Integer.toHexString(length);
		String result = hexLength;
		for(int i = 0; i < (8 - hexLength.length()); i++){
			result = "0" + result;
		}
		return result;
	}
	
	/**
	 * ibImsi BCD码，最后不满2位的第一位补0
	 * @param imsi
	 * @return
	 */
	public static String byteImsi2String(byte[] imsi){
		
		byte[] tempByte = new byte[8];
		
		System.arraycopy(imsi, 0, tempByte, 0, 8);
		
		String temp = ConvertTools.bcd2Str(tempByte);
		
//		temp = temp.substring(0, temp.length() - 2) + temp.substring(temp.length() - 1);
		temp = temp.substring(1);
		
		return temp;
	}
	
	/**
	 * ibImsi BCD码，最后不满2位的第一位补0
	 * @param imsi
	 * @return
	 */
	public static byte[] StringImsi2Byte(String imsi){
		
		String temp = imsi;
		
		if(temp.length() >= 16){
			temp = temp.substring(0, 16);
		}
		
//		String first = temp.substring(0, temp.length() - 1);
//		String last = temp.substring(temp.length() - 1);
//		
//		int i = temp.length() / 2;
//		int j = temp.length() % 2;
//		
//		if(j != 0){
//			last = "0" + last;
//		}
//		
//		for(int k = 0; k < (8 - i - j); k++){
//			last += "00";
//		}
//		
//		temp = first + last;
		
		for(int i = 0; i < (16 - temp.length());i++){
			temp = "0" + temp;
		}
		
		return ConvertTools.hexStringToByte(temp);
	}
	
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
}
