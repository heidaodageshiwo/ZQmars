package com.iekun.ef.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.iekun.ef.service.EmailService;
import com.iekun.ef.service.LoggerService;
import com.iekun.ef.service.MessageService;
import com.iekun.ef.service.SmsService;
import com.iekun.ef.service.SystemMonitor;
import com.iekun.ef.service.SystemParaService;
import com.iekun.ef.service.UserService;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.jms.service.HandleRcvAjustTime;
import com.iekun.ef.jms.service.HandleRcvAlarm;
import com.iekun.ef.jms.service.HandleRcvDevVersion;
import com.iekun.ef.jms.service.HandleRcvHeartBeat;
import com.iekun.ef.jms.service.HandleRcvInfoPa;
import com.iekun.ef.jms.service.HandleRcvLicenseComplete;
import com.iekun.ef.jms.service.HandleRcvLicenseNotifyAck;
import com.iekun.ef.jms.service.HandleRcvNowPara;
import com.iekun.ef.jms.service.HandleRcvReqCnf;
import com.iekun.ef.jms.service.HandleRcvStartNotify;
import com.iekun.ef.jms.service.HandleRcvUeInfoService;
import com.iekun.ef.jms.service.HandleRcvUpgradeComplete;
import com.iekun.ef.jms.service.HandleRcvUpgradeNotifyAck;
import com.iekun.ef.jms.service.HandleRcvStatusDev;
import com.iekun.ef.jms.service.HandleRcvStatusPa;
import com.iekun.ef.jms.service.HandleRcvStatusSniffer;
import com.iekun.ef.jms.service.IHandleMessageService;
import com.iekun.ef.jms.service.OamMessageService;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.DeviceAlarmMapper;
import com.iekun.ef.dao.DeviceLicenseMapper;
import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.dao.OperCodeMapper;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.dao.UeInfoShardingDao;
import com.iekun.ef.dao.UpgradeMapper;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceStatus;

//@Component
public class ConsumerMessageListener {

    private static Logger logger = LoggerFactory.getLogger(ConsumerMessageListener.class);

    @Autowired
    private DeviceMapper deviceDao;

    @Autowired
    private UeInfoMapper ueInfoMapper;

    @Autowired
    private TargetAlarmMapper targetAlarmMapper;

    @Autowired
    private SystemMonitor systemMonitor;

    @Autowired
    private OperCodeMapper operCodeMapper;

    @Autowired
    private DeviceAlarmMapper dvAlarmMapper;

    @Autowired
    private UeInfoShardingDao ueInfoShardingDao;

    @Autowired
    private OamMessageService oamMsgServ;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SystemParaService SysParaServ;

    @Autowired
    private MessageService msgServ;

    @Autowired
    LoggerService loggerServ;

    @Autowired
    UpgradeMapper upgradeMapper;

    @Autowired
    DeviceLicenseMapper dvLicenseMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /*@SuppressWarnings("rawtypes")
    @JmsListener(destination = "iekunReceiveQueue", concurrency="5-10", containerFactory = "jmsContainerQueue")*/
    public void receiveQueue(Message message) {

        MapMessage MapMessage = (MapMessage) message;
        try {

            HashMap map = (HashMap) MapMessage.getObject("SBXX");

            JSONObject mapObject = JSONObject.parseObject(map.get("messageReceive").toString());

            com.iekun.ef.jms.vo.Message receiveMessage = (com.iekun.ef.jms.vo.Message) JSONObject.toJavaObject(mapObject, com.iekun.ef.jms.vo.Message.class);
            boolean dvIsExist = this.deviceIsExist(receiveMessage.getSerialNum());

            if (dvIsExist == true) {
                switch (receiveMessage.getPktType()) {
                    case "0003":
                        HandleRcvReqCnf handleSendReqCnf = new HandleRcvReqCnf();
                        handleSendReqCnf.handleMessage(receiveMessage, loggerServ);
                        break;
                    case "0004":
                        HandleRcvNowPara handleSendNowPara = new HandleRcvNowPara();
                        handleSendNowPara.handleMessage(receiveMessage, loggerServ);
                        break;
                    case "0007":
                        HandleRcvDevVersion handleSendDevVersion = new HandleRcvDevVersion();
                        handleSendDevVersion.handleMessage(receiveMessage, loggerServ);
                        break;
                    case "0009":
                        HandleRcvStatusDev handleRecvStatusDev = new HandleRcvStatusDev();
                        handleRecvStatusDev.handleMessage(receiveMessage, loggerServ);
                        break;

                    case "000A":
                        HandleRcvStatusSniffer handleRecvStatusSniffer = new HandleRcvStatusSniffer();
                        handleRecvStatusSniffer.handleMessage(receiveMessage, loggerServ);
                        break;
                    case "00AF":
                        HandleRcvAjustTime handleRcvAjustTime = new HandleRcvAjustTime();
                        handleRcvAjustTime.handleMessage(receiveMessage, oamMsgServ);
                        break;
                    case "00E0":
                        HandleRcvAlarm handleRecvAlarm = new HandleRcvAlarm();
                        handleRecvAlarm.handleMessage(receiveMessage, dvAlarmMapper, userService, smsService, emailService, SysParaServ, msgServ);
                        break;

                    case "00F0":
                        HandleRcvStatusPa handleRecvStatusPa = new HandleRcvStatusPa();
                        handleRecvStatusPa.handleMessage(receiveMessage, loggerServ);
                        break;

                    case "00FF":
                        HandleRcvInfoPa handleRcvInfoPa = new HandleRcvInfoPa();
                        handleRcvInfoPa.handleMessage(receiveMessage, loggerServ);
                        break;
                    case "00F1":
                        HandleRcvUpgradeNotifyAck handleRcvUpgradeNotifyAck = new HandleRcvUpgradeNotifyAck();
                        handleRcvUpgradeNotifyAck.handleMessage(receiveMessage);
                        break;
                    case "00F2":
                        HandleRcvUpgradeComplete handleRcvUpgradeComplete = new HandleRcvUpgradeComplete();
                        handleRcvUpgradeComplete.handleMessage(receiveMessage, upgradeMapper);
                        break;
                    case "00F3":
                        HandleRcvLicenseNotifyAck handleRcvLicenseNotifyAck = new HandleRcvLicenseNotifyAck();
                        handleRcvLicenseNotifyAck.handleMessage(receiveMessage);
                        break;
                    case "00F4":
                        HandleRcvLicenseComplete handleRcvLicenseComplete = new HandleRcvLicenseComplete();
                        handleRcvLicenseComplete.handleMessage(receiveMessage, dvLicenseMapper);
                        break;
                    case "00F5":
                        HandleRcvStartNotify handleRcvStartNotify = new HandleRcvStartNotify();
                        handleRcvStartNotify.handleMessage(receiveMessage, operCodeMapper, loggerServ);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@SuppressWarnings("rawtypes")
    //@JmsListener(destination = "iekunUeInfoReceiveQueue", concurrency="2-5", containerFactory = "jmsListenerContainerUeInfo")
    public void receiveUeInfoQueue(Message message) {

        MapMessage MapMessage = (MapMessage) message;
        try {

            HashMap map = (HashMap) MapMessage.getObject("SBXX");

            JSONObject mapObject = JSONObject.parseObject(map.get("messageReceive").toString());

            com.iekun.ef.jms.vo.Message receiveMessage = (com.iekun.ef.jms.vo.Message) JSONObject.toJavaObject(mapObject, com.iekun.ef.jms.vo.Message.class);
            boolean dvIsExist = this.deviceIsExist(receiveMessage.getSerialNum());
            if (dvIsExist == true) {

                switch (receiveMessage.getPktType()) {
                    case "0002":
                        HandleRcvUeInfoService handleSendUeInfo = new HandleRcvUeInfoService();
                        handleSendUeInfo.handleMessage(receiveMessage, ueInfoMapper, targetAlarmMapper, deviceDao, systemMonitor, sqlSessionFactory, loggerServ, ueInfoShardingDao);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@SuppressWarnings("rawtypes")
    //@JmsListener(destination = "iekunHeartBeatQueue", concurrency="2-5", containerFactory = "jmsListenerContainerHeartBeat")
    public void receiveHeartBeatQueue(Message message) {

        MapMessage MapMessage = (MapMessage) message;
        try {

            HashMap map = (HashMap) MapMessage.getObject("SBXX");

            JSONObject mapObject = JSONObject.parseObject(map.get("messageReceive").toString());

            com.iekun.ef.jms.vo.Message receiveMessage = (com.iekun.ef.jms.vo.Message) JSONObject.toJavaObject(mapObject, com.iekun.ef.jms.vo.Message.class);
            boolean dvIsExist = this.deviceIsExist(receiveMessage.getSerialNum());
            if (dvIsExist == true) {
                switch (receiveMessage.getPktType()) {
                    case "0001":
                        HandleRcvHeartBeat handleSendHeartBeat = new HandleRcvHeartBeat();
                        handleSendHeartBeat.handleMessage(receiveMessage, loggerServ);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean deviceIsExist(String sn) {
        DeviceStatus deviceStatus = (DeviceStatus) SigletonBean.deviceStatusInfoMaps.get(sn);
        Device device;
        boolean dvIsReg = false;
        if (deviceStatus == null) {
            device = deviceDao.selectByDevSN(sn);
            if (device != null && !device.equals("")) {
                dvIsReg = true;
            } else {
                dvIsReg = false;
            }
        } else {
            dvIsReg = true;
        }
        return dvIsReg;
    }

}