package com.iekun.ef.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class StringUtils
{
  public static boolean hasChinese(String[] strs)
  {
    for (String str : strs) {
      if (str.length() != str.getBytes().length) {
        return true;
      }
    }
    return false;
  }
  
  public static int getMaxStringLength(String[] strs)
  {
    int maxLength = 0;
    for (String str : strs) {
      if (str.length() > maxLength) {
        maxLength = str.length();
      }
    }
    return maxLength;
  }
  
  public static String getArrayStr(Object[] objs)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    if (null != objs) {
      for (int i = 0; i < objs.length; i++) {
        sb.append(objs[i] + " ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
  
  public static String getIntArrayStr(int[] objs)
  {
    StringBuffer sb = new StringBuffer();
    if (null != objs) {
      for (int i = 0; i < objs.length; i++) {
        if (sb.length() == 0) {
          sb.append(objs[i]);
        } else {
          sb.append(", " + objs[i]);
        }
      }
    }
    return sb.toString();
  }
  
  public static String getByteArrayStr(byte[] objs)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    if (null != objs) {
      for (int i = 0; i < objs.length; i++) {
        sb.append(" " + objs[i]);
      }
    }
    return sb.toString();
  }
  
  public static String getFloatArrayStr(byte[] objs)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    if (null != objs) {
      for (int i = 0; i < objs.length; i++) {
        sb.append(objs[i] + " ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
  
  public static String getSmsText(byte[] text)
  {
    int[] srcBytes = { 200, 50, 155, 253, 6, 93, 223, 114, 54, 57, 4 };
    String[] srcStrBytes = { "C8", "32", "9B", "FD", "06", "5D", "DF", "72", "36", "39", "04" };
    
    List<String> dstBinaryStrList = new ArrayList<String>();
    
    int leftLength = 1;
    String downStr = "";
    String keepStr = "";
    String dstStr = "";
    for (int i = 0; i < srcBytes.length; i++)
    {
      String tempBits = Integer.toBinaryString(srcBytes[i]);
      int addZeroNun = 8 - tempBits.length();
      for (int j = 0; j < addZeroNun; j++) {
        tempBits = "0" + tempBits;
      }
      System.out.println("src: " + srcStrBytes[i] + " " + tempBits);
      
      keepStr = tempBits.substring(leftLength);
      
      dstStr = keepStr + downStr;
      
      addZeroNun = 8 - dstStr.length();
      for (int j = 0; j < addZeroNun; j++) {
        dstStr = "0" + dstStr;
      }
      dstBinaryStrList.add(dstStr);
      
      downStr = tempBits.substring(0, leftLength);
      
      System.out.println("dst: " + srcStrBytes[i] + " " + dstStr + " " + BinstrToChar(dstStr) + " " + downStr);
      if (leftLength == 7)
      {
        dstStr = downStr;
        
        addZeroNun = 8 - dstStr.length();
        for (int j = 0; j < addZeroNun; j++) {
          dstStr = "0" + dstStr;
        }
        System.out.println("dst: " + srcStrBytes[i] + " " + dstStr + " " + BinstrToChar(dstStr) + " " + downStr);
        dstBinaryStrList.add(dstStr);
        
        leftLength = 1;
        
        downStr = "";
      }
      else
      {
        leftLength += 1;
      }
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < dstBinaryStrList.size(); i++) {
      sb.append(BinstrToChar((String)dstBinaryStrList.get(i)));
    }
    System.out.println(sb.toString());
    
    return sb.toString();
  }
  
  private static char BinstrToChar(String binStr)
  {
    int[] temp = BinstrToIntArray(binStr);
    int sum = 0;
    for (int i = 0; i < temp.length; i++) {
      sum += (temp[(temp.length - 1 - i)] << i);
    }
    return (char)sum;
  }
  
  private static int[] BinstrToIntArray(String binStr)
  {
    char[] temp = binStr.toCharArray();
    int[] result = new int[temp.length];
    for (int i = 0; i < temp.length; i++)
    {
      temp[i] -= '0';
      System.out.println(temp[i] + " " + result[i]);
    }
    return result;
  }
}
