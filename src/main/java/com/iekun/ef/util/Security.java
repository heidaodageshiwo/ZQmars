package com.iekun.ef.util;

/**
 * Created by feilong.cai on 2016/5/24.
 */

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Random;

public class Security {

    private static final String secretKey = "2016&efence!@lic";
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    //MD5加密
    public String encodeByMD5(String str)  throws Exception {

        if (str == null) {
            return null;
        }
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(str.getBytes());
        return parseByte2Hex(messageDigest.digest());

    }

    //ASE加密
    public String encodeByASE( String str ) throws Exception {

        if( str == null) {
            return null;
        }

        Cipher cipher = Cipher.getInstance("AES");
        byte[] raw = secretKey.getBytes("ASCII");
        SecretKeySpec keySpec = new SecretKeySpec( raw, "AES");

        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte [] src = str.getBytes();
        byte [] enc = cipher.doFinal(src);

        return parseByte2Hex( enc );
    }

    //ASE解密
    public String decodeByASE( String  str )  throws Exception {

        if( str == null ) {
            return null;
        }

        byte[] raw = secretKey.getBytes("ASCII");
        SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] enc = parseHex2Byte(str);
        byte [] dec = cipher.doFinal(enc);

        return new String(dec);
    }

    //二进制转换成十六进制的字符串形式
    private String parseByte2Hex(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder( len *2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    //十六进制的字符串转换成二进制
    private  byte[] parseHex2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    //生成随机字串
    public static  String getRandomString( int len ) {

        String base = "abcdef0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
