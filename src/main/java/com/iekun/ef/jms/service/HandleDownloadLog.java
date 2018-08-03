package com.iekun.ef.jms.service;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;

import com.iekun.ef.dao.SysLogMapper;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.util.ConvertTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class HandleDownloadLog {

    private static final Logger logger = LoggerFactory.getLogger(HandleDownloadLog.class);

    @Autowired
    private SysLogMapper sysLogMapper;

    public void handleDownloadMessage(Message message){

        JSONObject jsonObject = JSONObject.parseObject(message.getSubOject().toString());
        byte[] nowPara = ConvertTools.hexStringToByte(jsonObject.get("param").toString());
        int resultParam = Integer.valueOf(ConvertTools.bytesToHexString(nowPara), 16);
        logger.info("activemq return param sessionId=" + message.getSessionId() + " param=" + resultParam);
        //resultParam 1:正在上传日志 2:上传成功 3:上传失败
        if(resultParam > 0){
            switch (resultParam){
                case 1:
                        SigletonBean.putToMap(message.getSessionId(), String.valueOf(resultParam));
                        Map map = new HashMap<String,Object>();
                        long timestamp = new Date().getTime();
                        map.put("deviceSn",message.getSerialNum());
                        map.put("status",resultParam);
                        map.put("timeStamp",timestamp);
                        try{
                            sysLogMapper.insertDownloadLog(map);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    break;
                case 2:
                        Map updateMap = new HashMap();
                        updateMap.put("deviceSn",message.getSerialNum());
                        updateMap.put("status",resultParam);
                        try{
                            sysLogMapper.updateDownloadLog(updateMap);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    break;
                case 3:
                    Map exceptionMap = new HashMap();
                    exceptionMap.put("deviceSn",message.getSerialNum());
                    exceptionMap.put("status",resultParam);
                    try{
                        sysLogMapper.updateDownloadLog(exceptionMap);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

    }

}
