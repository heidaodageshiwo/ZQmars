package com.iekun.ef.jms.util;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.send.SendPaPara;
import com.iekun.ef.util.ConvertTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ResolveSrxPara {
    private static Logger logger = LoggerFactory.getLogger(ResolveSrxPara.class);

    public static String resolve(String data) {

        Map<String, String> params = new HashMap<>();
        byte[] paramBytes = ConvertTools.hexStringToByte(data);
        byte[] DlEarfcn = new byte[2];
        System.arraycopy(paramBytes, 0, DlEarfcn, 0, 2);
        int v = Integer.valueOf(ConvertTools.bytesToHexString(DlEarfcn), 16);
        params.put("dlEarfcn", String.valueOf(v));
        return JSONObject.toJSON(params).toString();
    }
}
