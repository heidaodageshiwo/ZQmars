package com.iekun.ef.jms.util;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.util.ConvertTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Resolve2GData {
    private static Logger logger = LoggerFactory.getLogger(ResolveServerCnfPara.class);

    public static String resolve(String data) {
        byte[] nowPara = ConvertTools.hexStringToByte(data);
        Map<String, String> result = new HashMap<>();
        int curIdx = 0;
        while(curIdx < nowPara.length) {
            byte[] lengthBytes = new byte[2];
            System.arraycopy(nowPara, curIdx, lengthBytes, 0, 2);
            String lengthStr = ConvertTools.getNeedStr42GTrun(ConvertTools.bytesToHexString(lengthBytes));
            int length = Integer.valueOf(lengthStr);
            if(length <= 0 || length > nowPara.length - curIdx)
                return "";

            //
            byte[] items = new byte[length];
            System.arraycopy(nowPara, curIdx, items, 0, length);
            getItemInfo(length, items, result);
            curIdx += length;
        }
        return JSONObject.toJSON(result).toString();
    }

    private static void getItemInfo(int length, byte[] item, Map<String, String> result) {
        byte[] cmd = new byte[2];
        System.arraycopy(item, 2, cmd, 0, 2);
        byte[] data = new byte[length - 4];
        System.arraycopy(item, 4, data, 0, length - 4);
        String cmdStr = ConvertTools.bytesToHexString(cmd);
        cmdStr = ConvertTools.getNeedStr42GTrun(cmdStr);
        switch (cmdStr) {
            case "0041":
                //FTP 端口号
                String portStr = ConvertTools.bytesToHexString(data);
                result.put("ftpPort", String.valueOf(Integer.valueOf(ConvertTools.getNeedStr42GTrun(portStr), 16)));
                break;
            case "004D":
                //
                String cityCode = ConvertTools.bytesToHexString(data);
                result.put("cityCode", String.valueOf(Integer.valueOf(ConvertTools.getNeedStr42GTrun(cityCode), 16)));
                break;
        }
    }
}
