package com.iekun.ef.jms.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.iekun.ef.controller.DeviceController;
import com.iekun.ef.dao.SysLogMapper;
import com.iekun.ef.jms.vo.send.*;
import com.iekun.ef.util.ConvertTools;
import com.iekun.ef.util.PropertyUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.jms.vo.Message;
import com.iekun.ef.jms.vo.MyMessageCreator;
import com.iekun.ef.jms.vo.receive.RcvAjustTime;
import com.iekun.ef.jms.vo.receive.RcvLicenseNotifyAck;
import com.iekun.ef.jms.vo.receive.RcvPaInfos;
import com.iekun.ef.jms.vo.receive.RcvPaStatus;
import com.iekun.ef.jms.vo.receive.RcvReqCnf;
import com.iekun.ef.jms.vo.receive.RcvUpgradeNotifyAck;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.DeviceStatusEnviroment;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class OamMessageService {

    @Autowired
    HandleSendMessage handleSendmsg;

    @Value("${ftpserver.downloadlog.ip}")
    private String ftpServerIp;

    @Value("${ftpserver.downloadlog.port}")
    private String ftpServerPort;

    @Value("${ftpserver.downloadlog.user}")
    private String ftpUsername;

    @Value("${ftpserver.downloadlog.pwd}")
    private String ftpPassword;

    @Autowired
    private SysLogMapper sysLogMapper;

    //十分钟毫秒数
    private static final long difference = 600000;

    private static Logger logger = LoggerFactory.getLogger(OamMessageService.class);

    public JSONObject oamMessageHandle(String deviceSN, String actionName, String actionParam) {
        JSONObject jsonObject = null;
        switch (actionName) {
            case "getRFParam":
                jsonObject = getRFParam(deviceSN);
                break;
            case "setRFParam":
                jsonObject = setRFParam(deviceSN, actionParam);
                break;
            case "getFuncParam":
                jsonObject = getFuncParam(deviceSN);
                break;
            case "setFuncParam":
                jsonObject = setFuncParam(deviceSN, actionParam);
                break;
            case "getAlignGPSParam":
                jsonObject = getAlignGPSParam(deviceSN);
                break;
            case "setAlignGPSParam":
                jsonObject = setAlignGPSParam(deviceSN, actionParam);
                break;
            case "getPAParam":
                jsonObject = getPAParam(deviceSN);
                break;
            case "setPAParam":
                jsonObject = setPAParam(deviceSN, actionParam);
                break;
            case "getControlRangeParam":
                jsonObject = getControlRange(deviceSN);
                break;
            case "setControlRangeParam":
                jsonObject = setControlRange(deviceSN, actionParam);
                break;
            case "getENBIdParam":
                jsonObject = getENBIdParam(deviceSN);
                break;
            case "setENBIdParam":
                jsonObject = setENBIdParam(deviceSN, actionParam);
                break;
            case "getDataSendCfgParam":
                jsonObject = getDataSendCfgParam(deviceSN);
                break;
            case "setDataSendCfgParam":
                jsonObject = setDataSendCfgParam(deviceSN, actionParam);
                break;
            case "queryVersion":
                jsonObject = this.queryVersion(deviceSN);
                break;
            case "queryPAStatus":
                jsonObject = queryPAStatus(deviceSN);
                break;
            case "queryPAInfo":
                jsonObject = queryPAInfo(deviceSN);
                break;
            case "switchRF":
                jsonObject = setRFSwitch(deviceSN, actionParam);
                break;
            case "reboot":
                jsonObject = setRebootParam(deviceSN);
                break;
            case "resetFactory":
                jsonObject = setFactoryParam(deviceSN, actionParam);
                break;
            case "getRFSwitch":
                jsonObject = getRfSwitchStatus(deviceSN);
                break;
            //服务器地址配置
            case "getServerCnfParam":
                jsonObject = getServerCnfParam(deviceSN);
                break;
            case "setServerCnfParam":
                jsonObject = setServerCnfParam(deviceSN, actionParam);
                break;
            //
            case "getSrxParam":
                jsonObject = getSrxParam(deviceSN);
                break;
            case "setSrxParam":
                jsonObject = setSrxParam(deviceSN, actionParam);
                break;
            //查询工作模式
            case "queryWorkModeInfo":
                jsonObject = getWorkModeParam(deviceSN);
                break;
            //2g设备
            case "2gQuery":
                jsonObject = get2gParam(deviceSN);
                break;
            case "2gSet":
                jsonObject = set2gParam(deviceSN, actionParam);
                break;
            case "downloadLog":
                jsonObject = downloadLog(deviceSN);
                break;
            default:
                jsonObject = new JSONObject();
                jsonObject.put("status", false);
                jsonObject.put("message", "没有对应消息");
                break;

        }

        return jsonObject;
    }

    private synchronized String getSessionId() {
        int sessionId = SigletonBean.sessionId++;
        if (sessionId > SigletonBean.maxSessionId) {
            SigletonBean.sessionId = 0;
            sessionId = 1;
        }
        return String.valueOf(sessionId);
    }

    private JSONObject getRfSwitchStatus(String deviceSN) {

        JSONObject jSONObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();
        Map<String, Object> DvStatusInfoMap = SigletonBean.deviceStatusInfoMaps;
        DeviceStatus DvStatus = (DeviceStatus) DvStatusInfoMap.get(deviceSN);
        if (DvStatus != null) {
            DeviceStatusEnviroment dvStatusEnv = DvStatus.getDeviceStatusEnviroment();
            if (dvStatusEnv.getRfEnable() != null) {
                paramObj.put("rfenable", Integer.parseInt(dvStatusEnv.getRfEnable()));
                //全部参数名小写，去掉下划线
                dataObject.put("deviceSN", deviceSN);
                dataObject.put("actionName", "getRFSwitch");
                dataObject.put("parameter", paramObj);

                jSONObject.put("status", true);
                jSONObject.put("message", "成功");
                jSONObject.put("data", dataObject);
            } else {
                jSONObject.put("status", false);
                jSONObject.put("message", "操作失败:获取rf开关状态失败 ");
            }
        } else {
            jSONObject.put("status", false);
            jSONObject.put("message", "操作失败:获取rf开关状态失败 ");
        }

        return jSONObject;

    }

    private JSONObject getRFParam(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        obj = this.sendNowParaSend("0007");

        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", deviceSN, 20, this.getSessionId(), obj.toString()), "0007");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject setRFParam(String deviceSN, String actionParam) {

        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = this.RfParaSend(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "0007", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;

    }

    private JSONObject constructCnfJson(boolean isSuccess, String deviceSN) {
        JSONObject jsonObject = new JSONObject();
        if (isSuccess == true) {

            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        }

        if (isSuccess == false) {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败,参数错误!");
        }

        return jsonObject;
    }


    private JSONObject getFuncParam(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;

        obj = this.sendNowParaSend("0011");
        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", deviceSN, 20, this.getSessionId(), obj.toString()), "0011");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject setFuncParam(String deviceSN, String actionParam) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = this.FuncParaSend(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "0011", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    @SuppressWarnings("unused")
    private JSONObject getControlRange(String dvSn) {
        Object obj = null;
        JSONObject jsonobj = null;

        obj = this.sendNowParaSend("00B0");
        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", dvSn, 20, this.getSessionId(), obj.toString()), "00B0");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }


    private JSONObject getAlignGPSParam(String dvSn) {
        Object obj = null;
        JSONObject jsonobj = null;

        obj = this.sendNowParaSend("00E0");
        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", dvSn, 20, this.getSessionId(), obj.toString()), "00E0");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject setAlignGPSParam(String deviceSN, String actionParam) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        //obj = "";
        obj = this.AlignGpsParaSend(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00E0", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject setRFSwitch(String deviceSN, String actionParam) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = this.RfSwitchParaSend(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "0012", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject getPAParam(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        obj = this.sendNowParaSend("00C1");

        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", deviceSN, 20, this.getSessionId(), obj.toString()), "00C1");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
        /* 保留，需要和闻远确定，是否需要获取参数*/
          /*JSONObject jsonObject = new JSONObject();
          JSONObject dataObject = new JSONObject();
          JSONObject paramObj = new JSONObject();
    
          //全部参数名小写，去掉下划线
          paramObj.put("powerattenuation",  0 );
    
          dataObject.put("deviceSN", deviceSN);
          dataObject.put("actionName", "getPAParam");
          dataObject.put("parameter", paramObj);
    
          jsonObject.put("status", true);
          jsonObject.put("message", "成功");
          jsonObject.put("data", dataObject );
    
          return jsonObject;*/


    }

    private JSONObject setPAParam(String deviceSN, String actionParam) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = this.PaParaSend(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00C1", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject setControlRange(String deviceSN, String actionParam) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = this.ControlRangeSend(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00B0", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject getENBIdParam(String deviceSN) {
        /* 保留，需要和闻远确定，是否需要获取参数*/
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("identity", 1);

        dataObject.put("deviceSN", deviceSN);
        dataObject.put("actionName", "getENBIdParam");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    private JSONObject setENBIdParam(String deviceSN, String actionParam) {

        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        RcvReqCnf rcvReqCnf = null;
        String resultInfo;
        obj = this.CfgEnbIdSend(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00AE", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;

    }

    private JSONObject getDataSendCfgParam(String dvSn) {

        Object obj = null;
        JSONObject jsonobj = null;

        obj = this.sendNowParaSend("0013");
        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", dvSn, 20, this.getSessionId(), obj.toString()), "0013");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonobj;
            
            /* JSONObject jsonObject = new JSONObject();
             JSONObject dataObject = new JSONObject();
             JSONObject paramObj = new JSONObject();

             //全部参数名小写，去掉下划线
             paramObj.put("realtimesend",  0 );
             paramObj.put("interalmin",  1 );
             paramObj.put("uecountsend",  50 );


             dataObject.put("deviceSN", dvSn);
             dataObject.put("actionName", "getDataSendCfgParam");
             dataObject.put("parameter", paramObj);

             jsonObject.put("status", true);
             jsonObject.put("message", "成功");
             jsonObject.put("data", dataObject );

             return jsonObject;
*/


    }

    private JSONObject setDataSendCfgParam(String deviceSN, String actionParam) {

        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = this.DataSendCfgSend(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "0013", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;

    }

    private JSONObject queryVersion(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        obj = "";

        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "0008", deviceSN, 20, this.getSessionId(), obj.toString()), "0008");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }


    private JSONObject queryPAStatus(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        RcvPaStatus rcvPaStatus = null;
        String resultInfo;
        obj = "";

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00C0", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("info");
            //logger.info(" queryPAStatus resultInfo  :" + resultInfo);
            if (resultInfo != null) {
                JSONObject object = JSONObject.parseObject(resultInfo);
                rcvPaStatus = JSONObject.toJavaObject(object, RcvPaStatus.class);
                jsonobj = this.constructPaStatusJson(rcvPaStatus, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject constructPaStatusJson(RcvPaStatus rcvPaStatus, String dvSn) {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("valid", rcvPaStatus.getValid());
        paramObj.put("warnpa", rcvPaStatus.getWarn_pa());
        paramObj.put("warnstandingwaveratio", rcvPaStatus.getWarn_standing_wave_ratio());
        paramObj.put("warntemp", rcvPaStatus.getWarn_temp());
        paramObj.put("warnpower", rcvPaStatus.getWarn_power());
        paramObj.put("onoffpa", rcvPaStatus.getOn_off_Pa());
        paramObj.put("inversepower", rcvPaStatus.getInverse_power());
        paramObj.put("temp", rcvPaStatus.getTemp());
        paramObj.put("alcvalue", rcvPaStatus.getAlc_value());
        paramObj.put("standingwaveratio", rcvPaStatus.getStanding_wave_ratio());
        paramObj.put("currattpa", rcvPaStatus.getCurr_att_Pa());
        paramObj.put("forwardpower", rcvPaStatus.getForward_power());
        paramObj.put("forwardpower2", rcvPaStatus.getForward_power_2());


        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "queryPAStatus");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    private JSONObject queryPAInfo(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        RcvPaInfos rcvPaInfos = null;
        String resultInfo;
        obj = "";
        String sessionId = this.getSessionId();

        try {
            //logger.info("painfo query sessionId :" + sessionId);
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00CF", deviceSN, 20, sessionId, obj.toString())));
            resultInfo = jsonResult.getString("info");
            //logger.info("resultInfo  :" + resultInfo);
            if (resultInfo != null) {
                JSONObject object = JSONObject.parseObject(resultInfo);
                rcvPaInfos = JSONObject.toJavaObject(object, RcvPaInfos.class);
                jsonobj = this.constructPaStatusJson(rcvPaInfos, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    /**
     * 查询 工作模式
     *
     * @param deviceSN
     * @return
     */
    private JSONObject getWorkModeParam(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        obj = this.sendNowParaSend("00D4");

        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", deviceSN, 20, this.getSessionId(), obj.toString()), "00D4");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject constructPaStatusJson(RcvPaInfos rcvPaInfos, String dvSn) {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("pacount", rcvPaInfos.getCountPaInfo());
        paramObj.put("valid", rcvPaInfos.getPaDetailInfoList().get(0).getValid());
        paramObj.put("power", rcvPaInfos.getPaDetailInfoList().get(0).getPower());
        paramObj.put("panum", rcvPaInfos.getPaDetailInfoList().get(0).getPa_num());
        paramObj.put("band", rcvPaInfos.getPaDetailInfoList().get(0).getBand());
        paramObj.put("en485", rcvPaInfos.getPaDetailInfoList().get(0).getEn_485());
        paramObj.put("addr485", rcvPaInfos.getPaDetailInfoList().get(0).getAddr_485());
        paramObj.put("provider", rcvPaInfos.getPaDetailInfoList().get(0).getProvider());
        paramObj.put("sn", rcvPaInfos.getPaDetailInfoList().get(0).getSn());
        paramObj.put("factory", rcvPaInfos.getPaDetailInfoList().get(0).getFactory());

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "queryPAInfo");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    private JSONObject setRebootParam(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = "";

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "0010", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject setFactoryParam(String deviceSN, String actionParam) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = this.DataSendFactoryConf(actionParam);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00FE", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    public JSONObject setAdjustTimeParam(String deviceSn, RcvAjustTime rcvAjustTime) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        obj = this.AdjustTimeConf(rcvAjustTime);

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessageWithoutDelay(new Message("FFFF", "00AF", deviceSn, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSn);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }


    public JSONObject setUpgradeNotify(String deviceSN, SendUpgradeNotify notifyPara) {

        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        //RcvUpgradeNotifyAck rcvUpgradeNotiyAck = null;
        String resultInfo;
        obj = notifyPara;
        String notifySessionId = this.getSessionId();
        try {
            //logger.info(" setUpgradeNotify notifySessionId  :" + notifySessionId);
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessageWithoutDelay(new Message("FFFF", "00F1", deviceSN, 20, notifySessionId, obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject constructNotifyAckJson(RcvUpgradeNotifyAck rcvUpgradeNotiyAck, String dvSn) {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        if (rcvUpgradeNotiyAck.getCode() == 0) {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
            paramObj.put("message", rcvUpgradeNotiyAck.getMessage());

        }

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "upgradeDevices");
        dataObject.put("parameter", paramObj);

        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    public JSONObject setLicenseNotify(String deviceSN, SendLicenseNotify sendLicenseNotifyPara) {


        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        RcvLicenseNotifyAck rcvLicenseNotifyAck = null;
        String resultInfo;
        obj = sendLicenseNotifyPara;

        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00F2", deviceSN, 20, this.getSessionId(), obj.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    private JSONObject constructLicenseNotifyAckJson(RcvLicenseNotifyAck rcvLicenseNotifyAck, String dvSn) {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        if (rcvLicenseNotifyAck.getCode() == 0) {
            jsonObject.put("status", true);
            jsonObject.put("message", "成功");
        } else {
            jsonObject.put("status", false);
            jsonObject.put("message", "失败");
            paramObj.put("message", rcvLicenseNotifyAck.getMessage());

        }

        dataObject.put("deviceSN", dvSn);
        dataObject.put("actionName", "licenseDevices");
        dataObject.put("parameter", paramObj);

        jsonObject.put("data", dataObject);

        return jsonObject;
    }

    private Object AdjustTimeConf(RcvAjustTime rcvAjustTime) {

        SendAdjustTime sendAdjustTime = new SendAdjustTime();
        sendAdjustTime.setCsecond(String.valueOf(rcvAjustTime.getCuSecond()));
        sendAdjustTime.setCusecond(String.valueOf(rcvAjustTime.getCuSecond()));
        sendAdjustTime.setSadjustsecond(String.valueOf(rcvAjustTime.getsAdjustSecond()));
        sendAdjustTime.setSadjustusecond(String.valueOf(rcvAjustTime.getsAdjustuSecond()));

        return sendAdjustTime;
    }

    private Object sendNowParaSend(String pktType) {
        SendNowPara sendNowPara = new SendNowPara();
        sendNowPara.setNowType(pktType);
        return sendNowPara;
    }

    //射频参数
    private Object RfParaSend(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);
        //SendRfPara sendRfPara = JSONObject.toJavaObject(object, SendRfPara.class);

        SendRfPara sendRfPara = new SendRfPara();
        sendRfPara.setRfEnable(object.getString("rfenable"));
        sendRfPara.setFastConfigEarfcn(object.getString("fastconfigearfcn"));
        sendRfPara.setEutraBand(object.getString("eutraband"));
        sendRfPara.setDlEarfcn(object.getString("dlearfcn"));
        sendRfPara.setUlEarfcn(object.getString("ulearfcn"));
        sendRfPara.setFrameStrucureType(object.getString("framestrucuretype"));
        sendRfPara.setSubframeAssinment(object.getString("subframeassinment"));
        sendRfPara.setSpecialSubframePatterns(object.getString("specialsubframepatterns"));
        sendRfPara.setDlBandWidth(object.getString("dlbandwidth"));
        sendRfPara.setUlBandWidth(object.getString("ulbandwidth"));
        sendRfPara.setrFchoice(object.getString("rfchoice"));
        sendRfPara.setTx1PowerAttenuation(object.getString("tx1powerattenuation"));
        sendRfPara.setTx2PowerAttenuation(object.getString("tx2powerattenuation"));

        return sendRfPara;

    }

    //功能参数
    private Object FuncParaSend(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);
        //SendFunctionPara sendFuncPara = JSONObject.toJavaObject(object, SendFunctionPara.class);
        SendFunctionPara sendFuncPara = new SendFunctionPara();
        sendFuncPara.setArfcn(object.getString("arfcn"));
        sendFuncPara.setArfcn1(object.getString("arfcn1"));
        sendFuncPara.setArfcn2(object.getString("arfcn2"));
        sendFuncPara.setArfcn3(object.getString("arfcn3"));
        sendFuncPara.setBoolStart(object.getString("boolstart"));
        sendFuncPara.setControlRange(object.getString("controlrange"));
        sendFuncPara.setDebug(object.getString("circletime"));
        //sendFuncPara.setDebug(object.getString(""));
        sendFuncPara.setInandOutType(object.getString("inandouttype"));
        sendFuncPara.setParaMcc(object.getString("paramcc"));
        sendFuncPara.setParaMnc(object.getString("paramnc"));
        sendFuncPara.setParaPcino(object.getString("parapcino"));
        sendFuncPara.setParaPeri(object.getString("paraperi"));
        //sendFuncPara.setReserve(object.getString(""));
        return sendFuncPara;

    }

    //RF开关参数
    private Object RfSwitchParaSend(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);
        SendStartRf sendStartRfPara = new SendStartRf();

        sendStartRfPara.setRfEnable(object.getString("rfenable"));
        //SendStartRf sendStartRfPara = JSONObject.toJavaObject(object, SendStartRf.class);
        return sendStartRfPara;
    }

    //align GPS参数
    private Object AlignGpsParaSend(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);
        SendAlignGps sendAlignGpsPara = new SendAlignGps();

        sendAlignGpsPara.setNowSysNo(object.getString("nowsysno"));
        sendAlignGpsPara.setOffset(object.getString("offset"));
        sendAlignGpsPara.setFriendOffset(object.getString("friendoffset"));
        return sendAlignGpsPara;
    }

    // ControlRange参数
    private Object ControlRangeSend(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);
        SendCtrlRange sendCtrlRange = new SendCtrlRange();
        sendCtrlRange.setLev(object.getString("lev"));
        sendCtrlRange.setOpen(object.getString("open"));
        return sendCtrlRange;
    }

    //PA参数
    private Object PaParaSend(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);
        SendPaPara sendPaPara = new SendPaPara();
        sendPaPara.setPowerAttenuation(object.getString("powerattenuation"));
        sendPaPara.setRfSysNo("0");
        return sendPaPara;
    }

    //配置nodeb id参数
    private Object CfgEnbIdSend(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);
        SendCfgEnbId sendCfgEnbId = new SendCfgEnbId();
        sendCfgEnbId.setIdentity(object.getString("identity"));
        return sendCfgEnbId;
    }

    //配置数据发送方式参数
    private Object DataSendCfgSend(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);

        SendDataSendTypeCfg sendDataSendTypeCfg = new SendDataSendTypeCfg();
        String EnableRealTimeSend = object.getString("realtimesend");
        sendDataSendTypeCfg.setRealtimesend(EnableRealTimeSend);
        if (EnableRealTimeSend.equals("0")) {
            sendDataSendTypeCfg.setInteralmin(object.getString("interalmin"));
            sendDataSendTypeCfg.setUecountsend(object.getString("uecountsend"));
        } else {
            sendDataSendTypeCfg.setInteralmin("0");
            sendDataSendTypeCfg.setUecountsend("0");
        }

        return sendDataSendTypeCfg;

    }

    //配置恢复出厂参数
    private Object DataSendFactoryConf(String actionParam) {

        JSONObject object = JSONObject.parseObject(actionParam);
        SendFactoryConf sendFactoryConf = new SendFactoryConf();
        sendFactoryConf.setFactory("1");
        return sendFactoryConf;
    }

    /**
     * 获取服务器地址配置
     *
     * @param deviceSN
     * @return
     */
    private JSONObject getServerCnfParam(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        obj = this.sendNowParaSend("00E3");
        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", deviceSN, 20, this.getSessionId(), obj.toString()), "00E3");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonobj;
    }
    private JSONObject setServerCnfParam(String deviceSN, String actionParam) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;

        JSONObject object = JSONObject.parseObject(actionParam);
        SendServerCnf sendServerCnf = new SendServerCnf();
        sendServerCnf.setServerAddrType(object.getString("serverAddrType"));
        sendServerCnf.setServerAddr(object.getString("serverAddr"));
        sendServerCnf.setServerPort(object.getString("serverPort"));
        sendServerCnf.setServerTryConnectionTime(object.getString("serverTryConnectionTime"));
        sendServerCnf.setSecondServerEnable(object.getString("secondServerEnable"));
        sendServerCnf.setSecondServerAddrType(object.getString("secondServerAddrType"));
        sendServerCnf.setSecondServerAddr(object.getString("secondServerAddr"));
        sendServerCnf.setSecondServerPort(object.getString("secondServerPort"));
        sendServerCnf.setSecondServerConnectionTime(object.getString("secondServerConnectionTime"));
        //obj = this.DataSendFactoryConf(actionParam);
        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00E3", deviceSN, 20, this.getSessionId(), sendServerCnf.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    /**
     * 获取异频下行频点
     *
     * @param deviceSN
     * @return
     */
    private JSONObject getSrxParam(String deviceSN) {
        Object obj = null;
        JSONObject jsonobj = null;
        obj = this.sendNowParaSend("00C3");
        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "000C", deviceSN, 20, this.getSessionId(), obj.toString()), "00C3");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonobj;
    }

    private JSONObject setSrxParam(String deviceSN, String actionParam) {
        Object obj = null;
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;

        JSONObject object = JSONObject.parseObject(actionParam);
        SendSrxParam sendSrxParam = new SendSrxParam();
        sendSrxParam.setDlEarfcn(object.getString("DlEarfcn"));
        //obj = this.DataSendFactoryConf(actionParam);
        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "00C3", deviceSN, 20, this.getSessionId(), sendSrxParam.toString())));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonobj;
    }

    /**
     * 获取2g设备参数
     *
     * @param deviceSN
     * @return
     */
    private JSONObject get2gParam(String deviceSN) {
        JSONObject jsonobj = null;
        String sessionId = this.getSessionId();
        String idx = ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(sessionId)), 4);

        int sn = Integer.valueOf(deviceSN);
        String snStr = ConvertTools.fillZero(Integer.toHexString(sn), 8);
        //ConvertTools.getNeedStr42G(snStr)
        String cmd = getCmdData();
        String obj = "02" + "00000000" +  ConvertTools.getNeedStr42G(idx) + "0000" + "01" + "00" + cmd;
        String head =  ConvertTools.getNeedStr42G(ConvertTools.fillZero(Integer.toHexString(cmd.length() / 2 + 15), 8));
        obj = head + obj;
        try {
            jsonobj = handleSendmsg.getNowPara(new Message("FFFF", "X00A", deviceSN, 20, sessionId, obj), "X00A");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonobj;
    }

    /**
     * 在此处追加请求信息
     * 保证有效字段部分为0
     * @return
     */
    private String getCmdData() {
        StringBuffer buffer = new StringBuffer();
        //ftp 端口号
        buffer.append("0800410000000000");
        //城市代码
        buffer.append("08004D0000000000");
        return buffer.toString();
    }

    private JSONObject set2gParam(String deviceSN, String actionParam) {
        JSONObject jsonobj = null;
        JSONObject jsonResult = null;
        String resultInfo;
        String sessionId = this.getSessionId();
        String idx = ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(sessionId)), 4);

        int sn = Integer.valueOf(deviceSN);
        String snStr = ConvertTools.fillZero(Integer.toHexString(sn), 8);
        String cmd = getParamData(actionParam);
        String data = "02" + "00000000" +  ConvertTools.getNeedStr42G(idx) + "0000" + "02" + "00" + cmd;
        String head =  ConvertTools.getNeedStr42G(ConvertTools.fillZero(Integer.toHexString(cmd.length() / 2 + 15), 8));
        data = head + data;

        //封装报文体
        try {
            jsonResult = JSONObject.parseObject(handleSendmsg.handleSendMessage(new Message("FFFF", "X00B", deviceSN, 20, sessionId, data)));
            resultInfo = jsonResult.getString("success");
            if (resultInfo != null) {
                boolean isSuccess = Boolean.parseBoolean(resultInfo);
                jsonobj = this.constructCnfJson(isSuccess, deviceSN);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonobj;
    }

    /**
     * 封装报文体
     * 在后面追加即可
     * @param actionParam
     * @return
     */
    private String getParamData(String actionParam) {
        JSONObject params = JSONObject.parseObject(actionParam);
        StringBuffer buffer = new StringBuffer();

        //ftp 端口号
        String ftpPort = params.getString("ftpPort");
        int port = Integer.valueOf(ftpPort);
        String hexFtpFort = ConvertTools.fillZero(Integer.toHexString(port), 8);
        hexFtpFort = ConvertTools.getNeedStr42G(hexFtpFort);
        buffer.append("08004100");
        buffer.append(hexFtpFort);

        return buffer.toString();
    }

    /**
     * 上传设备日志
     * @param deviceSN
     * @return
     */
    public JSONObject downloadLog(String deviceSN){
        JSONObject jsonObject = new JSONObject();
        DownloadLog param = new DownloadLog();
        param.setProtocol("0");
        param.setServerIp(ftpServerIp);
        param.setPort(ftpServerPort);
        param.setUsername(ftpUsername);
        param.setPassword(ftpPassword);
        try{
            Map map = sysLogMapper.exportLog(deviceSN);
            if((map == null || map.size() == 0)){
                String result = handleSendmsg.downloadLog(new Message("FFFF", "0020", deviceSN, 144, this.getSessionId(), param.toString()));
                jsonObject = JSONObject.parseObject(result);
            }else{
                sysLogMapper.deleteDownloadLogTaskByDeviceSn(deviceSN);
                String result = handleSendmsg.downloadLog(new Message("FFFF", "0020", deviceSN, 144, this.getSessionId(), param.toString()));
                jsonObject = JSONObject.parseObject(result);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 下载设备日志
     * @param deviceSn
     * @return
     */
    public JSONObject exportLog(String deviceSn){
        JSONObject jsonObject = new JSONObject();
        try{
            Map map = sysLogMapper.exportLog(deviceSn);
            if((map == null || map.size() == 0)){
                jsonObject.put("info","请先获取日志");
                jsonObject.put("status",false);
            }else if("1".equals(map.get("STATUS").toString())){
                String timestamp = map.get("TIMESTAMP").toString();
                long firstTimestamp = Long.parseLong(timestamp);
                long currentTimestamp = new Date().getTime();
                //超过十分钟重新发送指令
                if((currentTimestamp - firstTimestamp) >= difference){
                    sysLogMapper.deleteDownloadLogTaskByDeviceSn(deviceSn);
                    this.downloadLog(deviceSn);
                    jsonObject.put("info","网络异常,正在重新上传日志,请稍后下载");
                    jsonObject.put("status",false);
                }else{
                    jsonObject.put("info","正在上传日志,请稍后下载");
                    jsonObject.put("status",false);
                }
            }else if("3".equals(map.get("STATUS").toString())){
                jsonObject.put("info","日志上传异常，请重新获取");
                jsonObject.put("status",false);
            }else if("2".equals(map.get("STATUS").toString())){
                //此处进行下载的逻辑
                DeviceController fds=new DeviceController();
                jsonObject = fds.deviceLogdown(deviceSn.toLowerCase() + ".zip");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return jsonObject;
    }





}
