package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.iekun.ef.jms.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.receive.RcvNowPara;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.controller.SigletonBean;

@Service("handleSendNowPara")
@Transactional(rollbackFor = java.lang.Exception.class)
@Scope("prototype")
public class HandleRcvNowPara {

    private static Logger logger = LoggerFactory.getLogger(HandleRcvNowPara.class);
    Map<String, Object> DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;

    private MessageToDataBase messageToDb = new MessageToDataBase();

    //@Override
    public void handleMessage(Message message, LoggerService loggerServ) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date nowDate = new Date();
        String noDateStr = df.format(nowDate);
        String deviceSn = message.getSerialNum();
        logger.info("step 7-2 " + message.getSubOject().toString());
        logger.info("step 7-2 pktType=" + message.getPktType() + " sessionId=" + message.getSessionId() + " serialNum="+ message.getSerialNum());
        JSONObject mapObject = JSONObject.parseObject(message.getSubOject().toString());
        RcvNowPara sendNowPara = JSONObject.toJavaObject(mapObject, RcvNowPara.class);
        DeviceStatus dvStatus = (DeviceStatus) DvStatusInfoMap.get(deviceSn);
        //dvStatus.getDeviceStatusCommon().setDeviceStatus("run");
        if ((dvStatus.getDeviceStatusCommon().getDeviceStatus()).equals("offline")) {
            dvStatus.getDeviceStatusCommon().setDeviceStatus("run");
            loggerServ.insertLog("online", deviceSn, new Long(0));
        }
        dvStatus.getDeviceStatusCommon().setStatusUpdateTime(noDateStr);
        this.resolve(sendNowPara);
        messageToDb.updateOperLog(sendNowPara.getSessionId(), 0);
    }

    /**
     * 根据不同类型解报文
     *
     * @param sendNowPara
     */
    private void resolve(RcvNowPara sendNowPara) {

        int key = sendNowPara.getSessionId();
        logger.info("获取当前参数SessionId: " + key + "nowType: " + sendNowPara.getNowType());
        String value = "";
        String data = sendNowPara.getNowPara();

        try {
            switch (sendNowPara.getNowType()) {
                case "0007":
                    value = ResolveRfPara.resolve(data);
                    break;
                case "0011":
                    value = ResolveFunctionPara.resolve(data);
                    break;
                case "00D0":
                    value = ResolveUpdateImsiLib.resolve(data);
                    break;
                case "0013":
                    value = ResolveDataSendCfgPara.resolve(data);
                    break;
                case "00E0":
                    value = ResolveAlignGpsPara.resolve(data);
                    break;
                case "00C1":
                    value = ResolvePaPara.resolve(data);
                    break;
                case "00B0":
                    value = ResolveCtrlRangePara.resolve(data);
                    break;
                //异频下行频点
                case "00C3":
                    value = ResolveSrxPara.resolve(data);
                    break;
                //服务器配置
                case "00E3":
                    value = ResolveServerCnfPara.resolve(data);
                    break;
                //2G设备参数获取
                case "004A":
                    value = Resolve2GData.resolve(data);
                //2G设备参数获取
                case "004B":
                    value = Resolve2GData.resolve(data);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            value = "解析应答错误";
        }

        SigletonBean.putToMap(String.valueOf(key), value);
        logger.info(key + " put into map success! ");
    }

}
