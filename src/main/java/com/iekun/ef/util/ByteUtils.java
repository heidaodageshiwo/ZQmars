package com.iekun.ef.util;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.Formatter;

public class ByteUtils
{
  public static int unsignedByteToInt(byte b)
  {
    return b & 0xFF;
  }
  
  public static String byteToHex(byte b)
  {
    int i = b & 0xFF;
    return Integer.toHexString(i);
  }
  
  public static byte intToUnsignedByte(int intValue)
  {
    byte byteValue = 0;
    int temp = intValue % 256;
    if (intValue < 0) {
      byteValue = (byte)(temp < -128 ? 256 + temp : temp);
    } else {
      byteValue = (byte)(temp > 127 ? temp - 256 : temp);
    }
    return byteValue;
  }
  
  public static long bytes2long(byte[] buf)
  {
    byte[] tempByte = new byte[4];
    tempByte[0] = buf[3];
    tempByte[1] = buf[2];
    tempByte[2] = buf[1];
    tempByte[3] = buf[0];
    
    int firstByte = 0;
    int secondByte = 0;
    int thirdByte = 0;
    int fourthByte = 0;
    
    firstByte = 0xFF & tempByte[0];
    secondByte = 0xFF & tempByte[1];
    thirdByte = 0xFF & tempByte[2];
    fourthByte = 0xFF & tempByte[3];
    
    return (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte) & 0xFFFFFFFF;
  }
  
  public static int bytes2int(byte[] b)
  {
    int value = 0;
    for (int i = 0; i < 4; i++) {
      value += ((b[i] & 0xFF) << 8 * i);
    }
    return value;
  }
  
  public static int bytes2short(byte[] b)
  {
    int tempA = -1;
    int tempB = -1;
    if (b[0] < 0) {
      tempA = unsignedByteToInt(b[0]);
    } else {
      tempA = b[0];
    }
    if (b[1] < 0) {
      tempB = unsignedByteToInt(b[1]);
    } else {
      tempB = b[1];
    }
    if ((tempA > 0) || (tempB > 0)) {
      return (tempB << 8) + tempA;
    }
    return b[1] << 8 | b[0] & 0xFF;
  }
  
  public static String byte2Hex(byte[] buf)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("{");
    for (byte b : buf)
    {
      if (b == 0)
      {
        sb.append("00");
      }
      else if (b == -1)
      {
        sb.append("FF");
      }
      else
      {
        String str = Integer.toHexString(b).toUpperCase();
        if (str.length() == 8) {
          str = str.substring(6, 8);
        } else if (str.length() < 2) {
          str = "0" + str;
        }
        sb.append(str);
      }
      sb.append(" ");
    }
    sb.append("}");
    return sb.toString();
  }
  
  public static byte[] int2bytes(int res)
  {
    byte[] targets = new byte[4];
    targets[0] = ((byte)(res & 0xFF));
    targets[1] = ((byte)(res >> 8 & 0xFF));
    targets[2] = ((byte)(res >> 16 & 0xFF));
    targets[3] = ((byte)(res >>> 24));
    return targets;
  }
  
  public static byte[] long2bytes(long s)
  {
    byte[] targets = new byte[8];
    for (int i = 0; i < 8; i++)
    {
      int offset = (targets.length - 1 - i) * 8;
      targets[i] = ((byte)(int)(s >>> offset & 0xFF));
    }
    return targets;
  }
  
  public static byte[] short2bytes(short num)
  {
    byte[] b = new byte[2];
    for (int i = 0; i < 2; i++) {
      b[i] = ((byte)(num >>> i * 8));
    }
    return b;
  }
  
  public static byte[] float2bytes(float v)
  {
    ByteBuffer bb = ByteBuffer.allocate(4);
    byte[] ret = new byte[4];
    FloatBuffer fb = bb.asFloatBuffer();
    fb.put(v);
    bb.get(ret);
    return ret;
  }
  
  public static float bytes2float(byte[] v)
  {
    int accum = 0;
    for (int shiftBy = 0; shiftBy < 4; shiftBy++) {
      accum |= (v[shiftBy] & 0xFF) << shiftBy * 8;
    }
    float result = Float.intBitsToFloat(accum);
    
    DecimalFormat df = new DecimalFormat("0.00");
    
    return Float.parseFloat(df.format(result));
  }
  
  public static byte[] floatToByte(float v)
  {
    ByteBuffer bb = ByteBuffer.allocate(4);
    byte[] ret = new byte[4];
    FloatBuffer fb = bb.asFloatBuffer();
    fb.put(v);
    bb.get(ret);
    return ret;
  }
  
  public static String number2HexString(Number inputNubmer)
  {
    StringBuffer buffer = new StringBuffer();
    Formatter format = new Formatter(buffer);
    format.format("0x%X", new Object[] { inputNubmer });
    return buffer.toString();
  }
  
  static byte ch2bcd(char ch)
  {
    if ((ch >= '0') && (ch <= '9')) {
      return (byte)(ch - '0');
    }
    return 0;
  }
  
  public static byte[] str2bcd(String str1)
  {
    char[] str = str1.toCharArray();
    byte[] bcd = new byte[8];
    int i = 0;
    int resultI = 0;
    int len = str.length;
    while (i < len)
    {
      byte v = 0;
      if (i + 1 < len) {
        v = ch2bcd(str[(i + 1)]);
      } else {
        v = 15;
      }
      v = (byte)(v << 4);
      v = (byte)(v | ch2bcd(str[i]) & 0xF);
      
      bcd[resultI] = v;
      
      i += 2;
      resultI++;
    }
    for (int j = 8; j > resultI; j--) {
      bcd[(j - 1)] = 15;
    }
    return bcd;
  }
  
  public static String bcd2str(byte[] bcd)
  {
    int i = 0;
    StringBuffer sb = new StringBuffer(bcd.length);
    while (i < 8)
    {
      byte d1 = (byte)(bcd[i] & 0xF);
      byte d2 = (byte)((bcd[i] & 0xF0) >> 4);
      if (d1 == 15) {
        break;
      }
      sb.append(d1);
      if (d2 == 15) {
        break;
      }
      sb.append(d2);
      i++;
    }
    return sb.toString();
  }
  
//  public static void main(String[] args)
//  {
//    byte[] bcd = str2bcd("123451234589635");
//    for (int i = 0; i < bcd.length; i++) {
//      System.out.print(bcd[i] + " ");
//    }
//    System.out.println("");
//    System.out.println(bcd2str(bcd));
//
//    byte[] floatBytes = float2bytes(41.5F);
//    for (byte temp : floatBytes) {
//      System.out.print(temp + " ");
//    }
//    System.out.println("");
//    byte[] floatBytes2 = { 0, 0, 38, 66 };
//    System.out.println(bytes2float(floatBytes2));
//
//    int[] aRGB = { 86, 120, 154, 188 };
//    int color_val = 0;
//
//    color_val = setIntToBit(aRGB[0], color_val, 0, 8);
//    System.out.println("color_val : " + color_val);
//    color_val = setIntToBit(aRGB[1], color_val, 8, 8);
//    color_val = setIntToBit(aRGB[2], color_val, 16, 8);
//    color_val = setIntToBit(aRGB[3], color_val, 24, 8);
//
//    System.out.println("putIntToBit : " + color_val);
//    System.out.println(Integer.toBinaryString(color_val));
//    System.out.println("getIntFromBit : " + getIntFromBit(color_val, 24, 8));
//
//    int color_val2 = 0;
//    color_val2 |= aRGB[3];
//    color_val2 |= aRGB[2] << 8;
//    color_val2 |= aRGB[1] << 16;
//    color_val2 |= aRGB[0] << 24;
//    System.out.println("putIntToBitReg : " + color_val2);
//
//    int msg_type_8 = 8;
//    int _32BitBuff = 0;
//    _32BitBuff = setIntToBit(msg_type_8, _32BitBuff, 0, 8);
//    System.out.println("_32BitBuff : " + _32BitBuff);
//
//    int frmInt = 469766145;
//    System.out.println("type : " + getIntFromBit(frmInt, 0, 8));
//
//    System.out.println(Byte.parseByte("127"));
//    System.out.println(intToUnsignedByte(180));
//
//    byte[] shortBytes = { -96, 0 };
//    System.out.println(bytes2short(shortBytes));
//  }
  
  public static int setIntToBit(int sourceValue, int targetValue, int bitStartIndex, int bitLength)
  {
    int temp = sourceValue << 32 - bitLength;
    temp >>>= bitStartIndex;
    
    return targetValue | temp;
  }
  
  public static int getIntFromBit(int sourceValue, int bitStartIndex, int bitLength)
  {
    int returnValue = sourceValue << bitStartIndex;
    
    returnValue >>>= 32 - bitLength;
    
    return returnValue;
  }
}
