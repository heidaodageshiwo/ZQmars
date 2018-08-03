package com.iekun.ef.jms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iekun.ef.jms.vo.send.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.MyMessageCreator;

import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.SysLogMapper;
//import com.iekun.efence.dao.DeviceDao;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.OperCode;
import com.iekun.ef.model.SysLog;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.service.StartupListener;
import com.iekun.ef.util.CommonConsts;
//import com.iekun.efence.service.ITargetMgrService;
import com.iekun.ef.jms.vo.receive.RcvDevVersion;

import com.alibaba.fastjson.JSONObject;


@Transactional(rollbackFor = java.lang.Exception.class)
@Scope("prototype")
@Service("HandleSendMessage")
public class HandleSendMessage {

    @Autowired
    @Qualifier("jmsTemplate")
    private JmsTemplate jmsTemplate;

	/*@Autowired
	@Qualifier("iMessageToDataBaseDao")
	private IMessageToDataBaseDao iMessageToDataBaseDao;
	
	@Autowired
	@Qualifier("deviceDao")
	private DeviceDao deviceDao;
	*/
	
/*	@Autowired
	@Qualifier("iTargetMgrService")
	private ITargetMgrService iTargetMgrService;*/

    @Autowired
    LoggerService loggerServ;

    @Autowired
    StartupListener startupListener;

    private static final int maxTryTime = 30;

    private static Logger logger = LoggerFactory.getLogger(HandleSendMessage.class);

    public String handleSendMessage(Message message) throws Exception {

        JSONObject jSONObject = new JSONObject();
        String result = "";

        try {
            //iMessageToDataBaseDao.addOperLog(message.getPktType(), message.getSerialNum(), 0, Integer.valueOf(message.getSessionId()));
            logger.info("step 2 pktType=" + message.getPktType() + " sessionId=" + message.getSessionId() + " serialNum="+ message.getSerialNum());
            loggerServ.insertLog(message.getPktType(), message.getSerialNum(), new Long(0)/*Long.valueOf(message.getSessionId())*/);
            //jmsTemplate.send("iekunSendQueue", new MyMessageCreator(message));
            if (null != startupListener.oamMsgProducerSession) {
                MyMessageCreator msgCreator = new MyMessageCreator(message);
                try {
                    startupListener.oamMsgProducer.send(msgCreator.createMessage(startupListener.oamMsgProducerSession));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                    startupListener.createOamProducer();
                    startupListener.oamMsgProducer.send(msgCreator.createMessage(startupListener.oamMsgProducerSession));
                }
            }
            result = this.getResult(message.getSessionId());
            //logger.info(" sessionId :" + message.getSessionId() + "result:"+ result);
            if (result == null) {
                logger.info("应答超时===========");
                throw new Exception("命令应答超时");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            jSONObject.put("success", false);
            jSONObject.put("info", "操作失败：" + e.getMessage());
            throw new Exception(jSONObject.toString());
        }

        jSONObject.put("success", true);
        jSONObject.put("dvSn", message.getSerialNum());
        jSONObject.put("info", result);


        return jSONObject.toString();
    }

    public String handleSendMessageWithoutDelay(Message message) throws Exception {

        JSONObject jSONObject = new JSONObject();
        String result = "";

        try {
            //iMessageToDataBaseDao.addOperLog(message.getPktType(), message.getSerialNum(), 0, Integer.valueOf(message.getSessionId()));
				 /*//this.insertLog(message.getPktType(), message.getSerialNum(), new Long(0), Integer.valueOf(message.getSessionId()));
*/
            logger.info("发送消息类型" + message.getPktType() + "对应sessionId：" + message.getSessionId() + "serialNum:" + message.getSerialNum());
            loggerServ.insertLog(message.getPktType(), message.getSerialNum(), new Long(0)/*Integer.valueOf(message.getSessionId())*/);
            //jmsTemplate.send("iekunSendQueue", new MyMessageCreator(message));
            if (null != startupListener.oamMsgProducerSession) {
                MyMessageCreator msgCreator = new MyMessageCreator(message);
                try {
                    startupListener.oamMsgProducer.send(msgCreator.createMessage(startupListener.oamMsgProducerSession));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                    startupListener.createOamProducer();
                    startupListener.oamMsgProducer.send(msgCreator.createMessage(startupListener.oamMsgProducerSession));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            jSONObject.put("success", false);
            jSONObject.put("info", "操作失败：" + e.getMessage());
            throw new Exception(jSONObject.toString());
        }

        jSONObject.put("success", true);
        jSONObject.put("dvSn", message.getSerialNum());
        jSONObject.put("info", "发送成功");


        return jSONObject.toString();
    }

    public void handleTargetToSigleDevice(Message message, String serialNum) {
        //iMessageToDataBaseDao.addOperLog(message.getPktType(), message.getSerialNum(), 0, Integer.valueOf(message.getSessionId()));
        //logger.info("发送消息类型" + message.getPktType() + "对应sessionId：" + message.getSessionId() + "device id: " + serialNum);
        jmsTemplate.send("iekunSendQueue", new MyMessageCreator(message));
    }

    public JSONObject getNowPara(Message message, String pktType) throws Exception {

        JSONObject jSONObject = new JSONObject();
        JSONObject dataObject = new JSONObject();

        String result = "";
        String dvSn = null;

        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = JSONObject.parseObject(this.handleSendMessage(message));
            } catch (Exception e) {
                jsonObject = JSONObject.parseObject(e.getMessage());
            }

            if (!jsonObject.getBoolean("success")) {
                throw new Exception(jsonObject.getString("info").replace("操作失败：", ""));
            }

            result = jsonObject.getString("info");
            dvSn = jsonObject.getString("dvSn");

            com.alibaba.fastjson.JSONObject object = com.alibaba.fastjson.JSONObject.parseObject(result);

            switch (pktType) {
                case "0007":
                    SendRfPara sendRfPara = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendRfPara.class);
                    this.putRecvRfParaResult(dataObject, sendRfPara, dvSn);
                    break;
                case "0011":
                    SendFunctionPara sendFunctionPara = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendFunctionPara.class);
                    this.putRecvFunctionParaResult(dataObject, sendFunctionPara, dvSn);
                    break;
                case "0008":
                    RcvDevVersion SendDevVersion = com.alibaba.fastjson.JSONObject.toJavaObject(object, RcvDevVersion.class);
                    this.putSendDevVersion(dataObject, SendDevVersion, dvSn);
                    break;
                case "00C1":
                    SendPaPara sendPaPara = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendPaPara.class);
                    this.putSendParaResult(dataObject, sendPaPara, dvSn);
                    break;
                case "0013":
                    SendDataSendTypeCfg sendDataSendTypeCfg = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendDataSendTypeCfg.class);
                    this.putSendDataSendTypeCfgResult(dataObject, sendDataSendTypeCfg, dvSn);
                    break;
                case "00E0":
                    SendAlignGps sendAlignGps = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendAlignGps.class);
                    this.putSendAlignGpsResult(dataObject, sendAlignGps, dvSn);
                    break;
                case "00B0":
                    SendCtrlRange sendCtrlRange = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendCtrlRange.class);
                    this.putSendCtrlRangeResult(dataObject, sendCtrlRange, dvSn);
                    break;
                //异频下行频点
                case "00C3":
                    SendSrxParam sendRxs = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendSrxParam.class);
                    this.putSendSrxParamResult(dataObject, sendRxs, dvSn);
                    break;
                case "00E3":
                    SendServerCnfParam sendServerCnfParam = com.alibaba.fastjson.JSONObject.toJavaObject(object, SendServerCnfParam.class);
                    this.putSendServerCnfParamResult(dataObject, sendServerCnfParam, dvSn);
                    break;
                case "X00A":
                    dataObject.put("deviceSN", dvSn);
                    dataObject.put("actionName", "getPAParam");
                    dataObject.put("parameter", result);
                    break;
                default:
                    jSONObject.put("status", false);
                    jSONObject.put("message", "命令应答失败：未识别的消息类型");
                    return jSONObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jSONObject.put("status", false);
            jSONObject.put("message", "操作失败：" + e.getMessage());
            throw new Exception(jSONObject.toString());
        }

        jSONObject.put("status", true);
        jSONObject.put("message", "成功");
        jSONObject.put("data", dataObject);

        return jSONObject;
    }

    /**
     * 查询版本
     *
     * @param jSONObject
     * @param sendDevVersion
     * @throws Exception
     */

    private void putSendDevVersion(JSONObject dataObject, RcvDevVersion rcvDevVersion, String dvSn) throws Exception {
        //jSONObject.put("type", sendDevVersion.getType());
        JSONObject paramObj = new JSONObject();
        paramObj.put("license", 0);
        paramObj.put("fpgaversion", rcvDevVersion.getFpgaVersion());
        paramObj.put("bbuversion", rcvDevVersion.getBbuVersion());
        paramObj.put("softwareversion", rcvDevVersion.getSoftWareVersion());

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "queryVersion");
        dataObject.put("parameter", paramObj);
    }

    /**
     * 形成射频结果集
     *
     * @param jSONObject
     * @param recvRfPara
     * @throws Exception
     */
    private void putRecvRfParaResult(JSONObject dataObject, SendRfPara sendRfPara, String dvSn) throws Exception {
        JSONObject paramObj = new JSONObject();
        paramObj.put("rfenable", sendRfPara.getRfEnable());
        paramObj.put("fastconfigearfcn", sendRfPara.getFastConfigEarfcn());
        paramObj.put("eutraband", sendRfPara.getEutraBand());
        paramObj.put("dlearfcn", sendRfPara.getDlEarfcn());
        paramObj.put("ulearfcn", sendRfPara.getUlEarfcn());
        paramObj.put("framestrucuretype", sendRfPara.getFrameStrucureType());
        paramObj.put("subframeassinment", sendRfPara.getSubframeAssinment());
        paramObj.put("specialsubframepatterns", sendRfPara.getSpecialSubframePatterns());
        paramObj.put("dlbandwidth", sendRfPara.getDlBandWidth());
        paramObj.put("ulbandwidth", sendRfPara.getUlBandWidth());
        paramObj.put("rfchoice", sendRfPara.getrFchoice());
        paramObj.put("tx1powerattenuation", sendRfPara.getTx1PowerAttenuation());
        paramObj.put("tx2powerattenuation", sendRfPara.getTx2PowerAttenuation());

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "getRFParam");
        dataObject.put("parameter", paramObj);

    }

    /**
     * 形成功能参数结果集
     *
     * @param jSONObject
     * @param recvFunctionPar
     * @throws Exception
     */
    private void putRecvFunctionParaResult(JSONObject dataObject, SendFunctionPara recvFunctionPar, String dvSn) throws Exception {
        JSONObject paramObj = new JSONObject();
        paramObj.put("paramcc", recvFunctionPar.getParaMcc());
        paramObj.put("paramnc", recvFunctionPar.getParaMnc());
        paramObj.put("controlrange", recvFunctionPar.getControlRange());
        paramObj.put("inandouttype", recvFunctionPar.getInandOutType());
        paramObj.put("boolstart", recvFunctionPar.getBoolStart());
        paramObj.put("parapcino", recvFunctionPar.getParaPcino());
        paramObj.put("paraperi", recvFunctionPar.getParaPeri());
        paramObj.put("arfcn", recvFunctionPar.getArfcn());
        paramObj.put("circletime", recvFunctionPar.getDebug());
        paramObj.put("arfcn1", recvFunctionPar.getArfcn1());
        paramObj.put("arfcn2", recvFunctionPar.getArfcn2());
        paramObj.put("arfcn3", recvFunctionPar.getArfcn3());
        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "getFuncParam");
        dataObject.put("parameter", paramObj);

    }

    /**
     * 形成PA参数结果集
     *
     * @param jSONObject
     * @param sendPaPara
     * @throws Exception
     */

    private void putSendParaResult(JSONObject dataObject, SendPaPara sendPaPara, String dvSn) throws Exception {
        JSONObject paramObj = new JSONObject();

        paramObj.put("powerattenuation", sendPaPara.getPowerAttenuation());
        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "getPAParam");
        dataObject.put("parameter", paramObj);

    }

    /**
     * 形成数据发送方式结果集
     *
     * @param jSONObject
     * @param sendDataSendTypeCfg
     * @throws Exception
     */

    private void putSendDataSendTypeCfgResult(JSONObject dataObject, SendDataSendTypeCfg sendDataSendTypeCfg, String dvSn) throws Exception {
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("realtimesend", sendDataSendTypeCfg.getRealtimesend());
        paramObj.put("interalmin", sendDataSendTypeCfg.getInteralmin());
        paramObj.put("uecountsend", sendDataSendTypeCfg.getUecountsend());

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "getDataSendCfgParam");
        dataObject.put("parameter", paramObj);

    }

    /**
     * 形成帧偏移结果集
     *
     * @param jSONObject
     * @param sendAlignGps
     * @throws Exception
     */

    private void putSendAlignGpsResult(JSONObject dataObject, SendAlignGps sendAlignGps, String dvSn) throws Exception {
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("nowsysno", sendAlignGps.getNowSysNo());
        paramObj.put("offset", sendAlignGps.getOffset());
        paramObj.put("friendoffset", sendAlignGps.getFriendOffset());

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "getAlignGPSParam");
        dataObject.put("parameter", paramObj);

    }

    /**
     * 形成控制范围结果集
     *
     * @param jSONObject
     * @param sendCtrlRange
     * @throws Exception
     */

    private void putSendCtrlRangeResult(JSONObject dataObject, SendCtrlRange sendCtrlRange, String dvSn) throws Exception {
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("open", sendCtrlRange.getOpen());
        paramObj.put("lev", sendCtrlRange.getLev());

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "getControlRangeParam");
        dataObject.put("parameter", paramObj);

    }

    /**
     * 异频下行频点
     * @param dataObject
     * @param sendCtrlRange
     * @param dvSn
     * @throws Exception
     */
    private void putSendSrxParamResult(JSONObject dataObject, SendSrxParam sendSrxParam, String dvSn) throws Exception {
        JSONObject paramObj = new JSONObject();
        //全部参数名小写，去掉下划线
        paramObj.put("DlEarfcn", sendSrxParam.getDlEarfcn());
        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "getSrxParam");
        dataObject.put("parameter", paramObj);
    }

    /**
     * 服务器配置
     * @param dataObject
     * @param sendCtrlRange
     * @param dvSn
     * @throws Exception
     */
    private void putSendServerCnfParamResult(JSONObject dataObject, SendServerCnfParam sendServerCnfParam, String dvSn) throws Exception {
        JSONObject paramObj = new JSONObject();
        //全部参数名小写，去掉下划线
        paramObj.put("serverAddrType", sendServerCnfParam.getServerAddrType());
        paramObj.put("serverAddr", sendServerCnfParam.getServerAddr());
        paramObj.put("serverPort", sendServerCnfParam.getServerPort());
        paramObj.put("serverTryConnectionTime", sendServerCnfParam.getServerTryConnectionTime());
        paramObj.put("secondServerEnable", sendServerCnfParam.getSecondServerEnable());
        paramObj.put("secondServerAddrType", sendServerCnfParam.getSecondServerAddrType());
        paramObj.put("secondServerAddr", sendServerCnfParam.getSecondServerAddr());
        paramObj.put("secondServerPort", sendServerCnfParam.getSecondServerPort());
        paramObj.put("secondServerConnectionTime", sendServerCnfParam.getSecondServerConnectionTime());

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "getServerCnfParam");
        dataObject.put("parameter", paramObj);
    }

    /**
     * 根据sessionId取命令结果
     *
     * @param sessionId
     * @return
     * @throws Exception
     */
    private String getResult(String sessionId) throws Exception {

        String result = "";
        boolean get = false;
        boolean timeOut = false;
        int tryTime = 0;
        Map<String, Object> sendCnfMaps = SigletonBean.sendCnf;

        while (!get && !timeOut) {

            //logger.info("getResult sessionId :" + Integer.parseInt(sessionId));
//		    Iterator iter = sendCnfMaps.keySet().iterator();
//			while (iter.hasNext())
//			{
//				Object key = iter.next();
//				Object val = sendCnfMaps.get(key);
//				logger.info("key sessionId:  " +  key.toString() + "val :" + val.toString());
//			}

            result = (String) SigletonBean.getValue(sessionId);
            if (result == null) {
                tryTime++;
                Thread.sleep(1000);
            } else {
                get = true;
                //SigletonBean.removeFromMap(Integer.valueOf(sessionId));
                SigletonBean.removeFromMap(sessionId);
                break;
            }

            if (tryTime >= maxTryTime) {
                break;
            }
        }

        logger.info("====getResult=" + result);
        return result;
    }

    public String downloadLog(Message message) throws Exception {

        JSONObject jSONObject = new JSONObject();
        String result = "";
        try {
            logger.info("step 2 pktType=" + message.getPktType() + " sessionId=" + message.getSessionId() + " serialNum="+ message.getSerialNum());
            if (null != startupListener.oamMsgProducerSession) {
                MyMessageCreator msgCreator = new MyMessageCreator(message);
                try {
                    startupListener.oamMsgProducer.send(msgCreator.createMessage(startupListener.oamMsgProducerSession));
                } catch (Exception e) {
                    startupListener.createOamProducer();
                    startupListener.oamMsgProducer.send(msgCreator.createMessage(startupListener.oamMsgProducerSession));
                }
            }
            result = this.getResult(message.getSessionId());
            if (result == null) {
                logger.info("应答超时===========");
                throw new Exception("命令应答超时");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            jSONObject.put("success", false);
            jSONObject.put("info", "操作失败：" + e.getMessage());
            throw new Exception(jSONObject.toString());
        }

        jSONObject.put("success", true);
        jSONObject.put("dvSn", message.getSerialNum());
        jSONObject.put("info", result);


        return jSONObject.toString();
    }

}
