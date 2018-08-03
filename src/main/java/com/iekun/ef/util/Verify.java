package com.iekun.ef.util;

public class Verify {

	public static void verifyImsi(String imsi)throws Exception{
		if(imsi.trim().length() != 15){
			throw new Exception("IMSI长度不符");
		}
		
		try{
			Long.valueOf(imsi);
		}catch(Exception e){
			throw new Exception("IMSI不是数字");
		}
	}
}
