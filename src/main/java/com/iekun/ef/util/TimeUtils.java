package com.iekun.ef.util;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils
{
  public static SimpleDateFormat dayFormatter = new SimpleDateFormat("yyyy-MM-dd");
  public static SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static SimpleDateFormat timeFormatterStr = new SimpleDateFormat("yyyyMMddHHmmss");
  public static SimpleDateFormat yearFormatterStr = new SimpleDateFormat("yyyy");
  public static SimpleDateFormat daySimpleFormatter = new SimpleDateFormat("yyyyMMdd");
  
  
  public static String formatDate(java.util.Date date, SimpleDateFormat formatter)
  {
    return formatter.format(date);
  }
  
  public static String formatDate(java.util.Date date, String formatterStr)
  {
    SimpleDateFormat formatter = new SimpleDateFormat(formatterStr);
    return formatter.format(date);
  }
  
  public static java.util.Date getDate(int datas)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.add(5, datas);
    String begin = new java.sql.Date(calendar.getTime().getTime()).toString();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date beginDate = null;
    try
    {
      beginDate = sdf.parse(begin);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return beginDate;
  }
  
  public static java.util.Date getDate(java.util.Date beginDate, int datas)
  {
    Calendar beginCal = Calendar.getInstance();
    beginCal.setTime(beginDate);
    GregorianCalendar calendar = new GregorianCalendar(beginCal.get(1), beginCal.get(2), beginCal.get(5));
    

    calendar.add(5, datas);
    String begin = new java.sql.Date(calendar.getTime().getTime()).toString();
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date endDate = null;
    try
    {
      endDate = sdf.parse(begin);
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return endDate;
  }
  
  public static long getDateIntervalSeconds(String beginTime, String endTime)
  {
    String startDayStr = beginTime.substring(0, 10);
    String endDayStr = endTime.substring(0, 10);
    try
    {
      java.util.Date beginDay = dayFormatter.parse(startDayStr);
      java.util.Date endDay = dayFormatter.parse(endDayStr);
      
      return (endDay.getTime() - beginDay.getTime()) / 1000L;
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return 0L;
  }
  
  public static String[] getDateIntervalDayStr(String beginTime, String endTime)
  {
    if ((null == beginTime) || (beginTime.trim().length() < 10) || (null == endTime) || (endTime.trim().length() < 10)) {
      return null;
    }
    String startDayStr = beginTime.substring(0, 10);
    String endDayStr = endTime.substring(0, 10);
    try
    {
      java.util.Date beginDay = dayFormatter.parse(startDayStr);
      java.util.Date endDay = dayFormatter.parse(endDayStr);
      
      long seconds = (endDay.getTime() - beginDay.getTime()) / 1000L;
      
      int days = (int)(seconds / 60L / 60L / 24L);
      
      String[] dayStrs = new String[days + 1];
      
      int index = dayStrs.length - 1;
      while ((beginDay.before(getDate(endDay, 1))) && (index >= 0))
      {
        dayStrs[(index--)] = dayFormatter.format(beginDay);
        beginDay = getDate(beginDay, 1);
      }
      return dayStrs;
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public static String convertDateNoSepToSep( String dateStr ) {
    String formatDateStr = "";

    try
    {
      java.util.Date formatDate = timeFormatterStr.parse(dateStr);
      formatDateStr = timeFormatter.format( formatDate );
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }

    return formatDateStr;
  }

  public static String convertDateSepToNoSep( String dateStr ) {
    String formatDateStr = "";
    try
    {
      java.util.Date formatDate = timeFormatter.parse(dateStr);
      formatDateStr = timeFormatterStr.format( formatDate );
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return formatDateStr;
  }

  
//  public static void main(String[] args)
//  {
//    long seconds = getDateIntervalSeconds("2013-09-25", "2013-09-27");
//    System.out.println(seconds / 60L / 60L / 24L);
//
//    String[] dayStrs = getDateIntervalDayStr("2013-9-25 03:05:11", "2013-9-27 10:55:19");
//    if (null != dayStrs)
//    {
//      for (int i = 0; i < dayStrs.length; i++) {
//        System.out.print(dayStrs[i] + " ");
//      }
//      System.out.println("");
//    }
//  }
//
  public static String getFormatTimeStr(String timeStr)
  {
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");  
	    Date date;
	    if (timeStr == null)
	    {
	    	return null;
	    }
		try {
			date = (Date) sdf1.parse(timeStr.trim());
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			return sdf2.format(date);  
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	  
  }
  
  public static String getBackWardsAccordingBaseTime( int cycleTimeLength)
  {
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	Calendar c = new GregorianCalendar();
	Date date = new Date();
	c.setTime(date);//设置参数时间
	c.add(Calendar.SECOND,-cycleTimeLength);//把日期往后增加jobExcuteCycleTime 秒.整数往后推,负数往前移动
	date=c.getTime(); //这个时间就是日期往后推了的结果
	String backTime = df.format(date);
	return backTime;
  }
  
  public static Date getDateBefore(Date d, int day) {  
      Calendar now = new GregorianCalendar();  
      now.setTime(d);  
      now.set(Calendar.DATE,  -day);  
      return now.getTime();  
  }  
  public static String getSpecifiedDayBefore(String specifiedDay){  
      //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");  
      Calendar c = Calendar.getInstance();  
      Date date=null;  
      try {  
          date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);  
      } catch (ParseException e) {  
          e.printStackTrace();  
      }  
      c.setTime(date);  
      int day=c.get(Calendar.DATE);  
      c.set(Calendar.DATE,day-1);  

      String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  
      return dayBefore;  
  }  
    
  public static String getSpecifiedDayAfter(String specifiedDay){  
      Calendar c = Calendar.getInstance();  
      Date date=null;  
      try {  
          date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);  
      } catch (ParseException e) {  
          e.printStackTrace();  
      }  
      c.setTime(date);  
      int day=c.get(Calendar.DATE);  
      c.set(Calendar.DATE,day+1);  

      String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  
      return dayAfter;  
  }  
  
  public static String getSpecifiedDayAfterSpecifiedDays(String specifiedDay, int numberOfDays){  
      Calendar c = Calendar.getInstance();  
      Date date=null;  
      try {  
          date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);  
      } catch (ParseException e) {  
          e.printStackTrace();  
      }  
      c.setTime(date);  
      int day=c.get(Calendar.DATE);  
      c.set(Calendar.DATE,day+numberOfDays);  

      String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  
      return dayAfter;  
  }  
  
  
  public static String getDayString(int year, int month, int day)
  {
	  String monthStr = null;
	  String dayStr   = null;
	  if(month < 10)
	  {
		  monthStr = "0" + String.valueOf(month);
	  }
	  else
	  {
		  monthStr = String.valueOf(month);
	  }
	  
	  if (day < 10)
	  {
		  dayStr =  "0" + String.valueOf(day);
	  }
	  else
	  {
		  dayStr = String.valueOf(day);
	  }
	  return String.valueOf(year) + "-" + monthStr + "-" + dayStr;
  }
  
}
