package com.iekun.ef.util;

import java.util.Random;

public class CommonConsts {
	
	public static String  off = "关闭";
	
	public static String  on = "打开";
	
	public static String Band38 ="Band38";
			
	public static String Band39 ="Band39";
	
	public static String Band40 ="Band40";
	
	public static String Band41 ="Band41";
	
	public static String Band1 ="Band1";
	
	public static String Band3 ="Band3";
	
	public static String Band7 ="Band7";
			
	public static String M3 = "3M";
	
	public static String M5 = "5M";
	
	public static String M10 = "10M";
	
	public static String M15 = "15M";
	
	public static String M20 = "20M";
	
	public static String RRU = "RRU";
	
	public static String RF9361 = "9361单板";
	
	public static String BAND38_250mw = "BAND38_250mw";
	
	public static String BAND3_20w = "BAND3_20w"; 
	
	public static String BAND39_20w = "BAND39_20w"; 
	
	public static String BAND38_10w = "BAND38_10w"; 
	
	public static String IDLE = "空闲"; 
	
	public static String W30 = "30W"; 
	
	public static String W40 = "40w"; 
	
	public static String CDMA = "cdma";
	
	public static String GSM  = "gsm";
	
	public static String WCDMA = "wcdma";
	
	public static String TD    = "td";
	
	public static String TDD  = "tdd";
	
	public static String FDD  = "fdd";
	
	public static String CHINAMOBILE = "中国移动";
	
	public static String CHINATELECOM = "中国电信";
	
	public static String CHINAUNICOM  = "中国联通";
	
	public static String HEART_BEAT   = "0001";
	
	public static String UE_INFO      = "0002";
	
	public static String REQ_CNF      = "0003";
	
	public static String NOW_PARA     = "0004";
	
	public static String DEV_VERSION  = "0007";
	
	public static String DEV_STATUS   = "0009";
	
	public static String DEV_SNIFFER  = "000A";
	
	public static String AJUST_TIME   = "00AF";
	
	public static String DEV_ALARM    = "00E0";
	
	public static String PA_STATUS    = "00F0";
	
	public static String PA_INFO      = "00FF";
	
	public static String DEV_UPGRADE_NOTIFY_ACK = "00F1";
	
	public static String DEV_UPGRADE_COMP   = "00F2";
	
	public static String DEV_LICENSE_NOTIFY_ACK = "00F3";
	
	public static String DEV_LICENSE_COMP = "00F4";
	
	public static String DEV_START_NOTIFY = "00F5";
	
	
	
	
			
			
	public static String getOperatorTypeText(Integer operatorType)
    {
    	/* 运营商：01- 移动 02- 电信 03- 联通 */
    	String operatorTypeText = "未知运营商";
    	switch(operatorType)
    	{
	    	case 1:
	    		operatorTypeText = "中国移动";
	    		break;
	    	case 2:
	    		operatorTypeText = "中国电信";
	    		break;
	    	case 3:
	    		operatorTypeText = "中国联通";
	    		break;
    		default:
    			operatorTypeText = "未知运营商";
    			break;
    	}
    	
    	return operatorTypeText;
    }
	
	
	public static String getModeTypeText(Integer modeType)
    {
    	/* 设备类型：01- cdma；02- gsm；03- wcdma；04- td；05- tdd；06- fdd */
    	String modeTypeText;
    	switch(modeType)
    	{
    		case 1:
    			modeTypeText = CommonConsts.CDMA;
    			break;
    		case 2:
    			modeTypeText = CommonConsts.GSM;
    			break;
    		case 3:
    			modeTypeText = CommonConsts.WCDMA;
    			break;
    		case 4:
    			modeTypeText = CommonConsts.TD;
    			break;
    		case 5:
    			modeTypeText = CommonConsts.TDD;
    			break;
    		case 6:
    			modeTypeText = CommonConsts.FDD;
    			break;
    		default:
    			modeTypeText = "未知制式";
    			break;
    	}
    	
    	return modeTypeText;
    }
	
	public static  String getAlignCharText(int inputVal)
	{
		if (inputVal < 10)
		{
			return ("0"+ String.valueOf(inputVal));
		}
		else
		{
			return (String.valueOf(inputVal));
		}
	}
	
	public static String getLoggerTypeNameByLoggerTypeId(int type)
	{
		String loggerTypeName;
		
		switch(type)
		{
		case 0:
			loggerTypeName = "设备状态日志";
			break;
		case 1:
			loggerTypeName = "用户登录登出日志";
			break;
		case 2:
			loggerTypeName = "设备操作日志";
			break;
		case 3:
			loggerTypeName = "通知日志";
			break;
		case 4:
			loggerTypeName = "系统日志";
			break;
		case 5:
			loggerTypeName = "统计分析日志";
			break;
		case 6:
			loggerTypeName = "上报数据日志";
			break;
		case 7:
			loggerTypeName = "设备管理日志";
			break;
		case 8:
			loggerTypeName = "设备告警日志";
			break;
		case 9:
			loggerTypeName = "目标预警日志";
			break;
		case 10:
			loggerTypeName = "用户管理日志";
			break;
		case 11: 
			loggerTypeName = "设备启动原因";
			break;
		default:
			loggerTypeName = "未知类型日志";
			break;
			
		}
		
		return loggerTypeName;
	}
	
	
	public static int getTypeIdByLoggerTypeName(String loggerTypeName)
	{
		int type;
		
		switch(loggerTypeName)
		{
		case "设备状态日志":
			type = 0;
			break;
		case "用户登录登出日志": 
			type = 1;
			break;
		case "设备操作日志": 
			type = 2;
			break;
		case "通知日志": 
			type = 3;
			break;
		case "系统日志":  
			type = 4;
			break;
		case "统计分析日志": 
			type = 5;
			break;
		case "上报数据日志": 
			type = 6;
			break;
		case "设备管理日志": 
			type = 7;
			break;
		case "设备告警日志": 
			type = 8;
			break;
		case "目标预警日志": 
			type = 9;
			break;
		case "用户管理日志": 
			type = 10;
			break;
		case "设备启动原因": 
			type = 11;
			break;
		default:
			type = 12;
			break;
			
		}
		
		return type;
	}
	
	public static String getAlarmNum(String AlarmInfo)
	{
		String alarmCode = AlarmInfo.substring(0, 2);
		return alarmCode;
	}
	
	public static String getAlarmInfo(String AlarmInfo)
	{
		int index = AlarmInfo.indexOf("#"); 
		String alarmInfo = AlarmInfo.substring(index+1);
		return alarmInfo;
	}
	
	/*public static  String getStartReason(String reason)
	{
		String reasonStr = "未知原因";
		switch(reason)
		{
			case 0:
			reasonStr = " 正常启动";	
			break;
			case 1:
			reasonStr = "软复位";	
			break;
			case 2:
			reasonStr = "硬复位";	
			break;
			case 3:
			reasonStr = "软件升级";
			break;
			case 4:
			reasonStr = "许可授权";	
			break;
			default:
				break;
		}
		return reasonStr;
	}*/
	
	public static  String getSexNameBySexId(int sexId)
	{
		String sexName = "未知性别";
		switch(sexId)
		{
			case 0:
				sexName = "男";
				break;
			case 1:
				sexName = "女";
				break;
		    default:
				break;
		}
		
		return sexName;
	}
	
	public static String getSyncStatusInfo(int synStatus)
	{
		String syncStatusInfo = "未知状态";
		switch(synStatus)
		{
			case 1:
				syncStatusInfo = "";
				break;
			case 2:
				syncStatusInfo = "";
				break;
			case 3:
				syncStatusInfo = "";
				break;
			case 4:
				syncStatusInfo = "";
				break;
			case 5:
				syncStatusInfo = "";
				break;
			case 6:
				syncStatusInfo = "";
				break;
			default:
				break;
			
		}
		
		return syncStatusInfo;
	}
	
	public static String genRandomNum(int pwd_len){
		  //35是因为数组是从0开始的，26个字母+10个数字
		  final int  maxNum = 36;
		  int i;  //生成的随机数
		  int count = 0; //生成的密码的长度
		  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		 
		  StringBuffer pwd = new StringBuffer("");
		  Random r = new Random();
		  while(count < pwd_len){
		   //生成随机数，取绝对值，防止生成负数，
		  
		   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
		  
		   if (i >= 0 && i < str.length) {
		    pwd.append(str[i]);
		    count ++;
		   }
		  }
		 
		  return pwd.toString();
		 }
}
