package com.iekun.ef.service;

import java.util.HashMap;

import com.iekun.ef.jms.service.*;
import com.iekun.ef.jms.vo.send.DownloadLog;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.dao.DeviceAlarmMapper;
import com.iekun.ef.dao.DeviceLicenseMapper;
import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.dao.OperCodeMapper;
import com.iekun.ef.dao.SysLogMapper;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.dao.UeInfoShardingDao;
import com.iekun.ef.dao.UpgradeMapper;

import javax.jms.*;

@Service
public class StartupListener implements
        ApplicationListener<ContextRefreshedEvent> {

//	@Autowired
//	@Qualifier("jmsTemplate")
//	private JmsTemplate jmsTemplate;

    @Value(" ${activemq.brokerip}")
    private String activemqBrokerIp;

    @Value(" ${efence.properties.old.storage-recive-ueinfo}")
    private boolean storageReciveUeInfo;

    @Autowired
    private SystemMonitor systemMonitor;

    @Autowired
    private UeInfoMapper ueInfoMapper;

    @Autowired
    private TargetAlarmMapper targetAlarmMapper;

    @Autowired
    private DeviceService dvService;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private OperCodeMapper operCodeMapper;

    @Autowired
    LoggerService loggerServ;

    @Autowired
    UpgradeMapper upgradeMapper;

    @Autowired
    DeviceLicenseMapper dvLicenseMapper;

    @Autowired
    MessageToDataBase messageToDataBase;

    @Autowired
    private DeviceAlarmMapper dvAlarmMapper;

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
    private UeInfoShardingDao ueInfoShardingDao;

    @Autowired
    private MessageService msgServ;

    @Autowired
    private HandleDownloadLog handleDownloadLog;

    private MessageConsumer useInfoConsumer;
    private MessageConsumer useInfoConsumer1;

    private MessageConsumer hearbeatConsumer;

    private MessageConsumer oamMsgConsumer;

    public MessageProducer oamMsgProducer;

    public Connection oamMsgProducerConn = null;

    public Connection useInfoConsumerConn = null;
    public Connection useInfoConsumerConn1 = null;

    public Connection hearbeatConsumerConn = null;

    public Connection oamMsgConsumerConn = null;

    public Session oamMsgProducerSession = null;

    private static Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() == null) {
            this.createUeInfoLister();
            //this.createUeInfoLister1();
            this.createHeartBeatLister();
            this.createOamMsgLister();
            this.createOamProducer();
        }

    }

    public void createOamProducer() {
        new Thread() {
            public void run() {
                boolean createConn = false;
                while (createConn == false) {
                    try {
                        if (oamMsgProducerConn != null) {
                            oamMsgProducerConn.close();
                        }
                        logger.info("activemqBrokerIp :" + activemqBrokerIp);
                        ConnectionFactory factory = new org.apache.activemq.ActiveMQConnectionFactory("admin", "admin", activemqBrokerIp.trim());
                        Connection connection = factory.createConnection();
                        connection.start();
                        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
                        oamMsgProducerSession = session;
                        Destination destination = session.createQueue("iekunSendQueue");
                        oamMsgProducer = session.createProducer(destination);
                        logger.info("create conn to oam producer activemq success! ");

                        oamMsgProducerConn = connection;
                        createConn = true;
                    } catch (JMSException e) {

                        try {
                            Thread.currentThread().sleep(2000);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        logger.info("create conn to oam producer activemq fail! ");

                    }
                }

            }

        }.start();

    }


    public void createUeInfoLister() {
        //根据配置判断是否启动数据接收线程
        if (!this.storageReciveUeInfo) {
            logger.info("efence.properties.old.storage-recive-ueinfo=false");
            return;
        } else {
            logger.info("efence.properties.old.storage-recive-ueinfo=false");
        }

        new Thread() {
            public void run() {
                this.contructUeInfoConn();
                for (; ; ) {
                    try {
                        Message message = (Message) useInfoConsumer.receive(1000);
                        //logger.info("entern createUeInfoLister ! priority: "+ Thread.currentThread().getPriority());
                        Thread.currentThread().setPriority(10);
                        if (null != message) {
                            message.acknowledge();
                            this.parseUeinfoMessage(message);
                            //System.out.println("recv message:  " );

                        } else {
                            //System.out.println("no message " );
                            continue;
                        }
                    } catch (Exception e) {
                        logger.info("recv ueinfo error");
                        this.contructUeInfoConn();

                    }

                }


            }

            private void contructUeInfoConn() {
                boolean createConn = false;
                while (createConn == false) {
                    try {

                        if (useInfoConsumerConn != null) {
                            useInfoConsumerConn.close();
                        }
                        logger.info("activemqBrokerIp :" + activemqBrokerIp);
                        ConnectionFactory factory = new org.apache.activemq.ActiveMQConnectionFactory("admin", "admin", activemqBrokerIp.trim());
                        Connection connection = factory.createConnection();
                        connection.start();
                        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
                        Destination destination = session.createQueue("iekunUeInfoReceiveQueue");
                        useInfoConsumer = session.createConsumer(destination);
                        logger.info("create conn to UeInfo activemq success! ");
                        useInfoConsumerConn = connection;
                        createConn = true;
                    } catch (Exception e) {

                        try {
                            Thread.currentThread().sleep(2000);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        logger.info("create conn to UeInfo activemq fail! ");

                    }
                }

            }

            private void parseUeinfoMessage(Message message) {
                MapMessage MapMessage = (MapMessage) message;
                HandleRcvUeInfoService handleSendUeInfo = new HandleRcvUeInfoService();
                HashMap map;
                try {
                    map = (HashMap) MapMessage.getObject("SBXX");
                    JSONObject mapObject = JSONObject.parseObject(map.get("messageReceive").toString());
                    com.iekun.ef.jms.vo.Message receiveMessage = (com.iekun.ef.jms.vo.Message) JSONObject.toJavaObject(mapObject, com.iekun.ef.jms.vo.Message.class);
                    boolean dvIsReg = dvService.deviceIsExist(receiveMessage.getSerialNum());
                    if (dvIsReg == true) {
                        handleSendUeInfo.handleMessage(receiveMessage, ueInfoMapper, targetAlarmMapper, deviceMapper, systemMonitor, sqlSessionFactory, loggerServ, ueInfoShardingDao);
                    }
                } catch (JMSException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        }.start();

    }

    public void createUeInfoLister1() {
        new Thread() {
            public void run() {
                this.contructUeInfoConn1();
                for (; ; ) {
                    try {
                        Message message = (Message) useInfoConsumer1.receive(1000);

                        if (null != message) {
                            message.acknowledge();
                            this.parseUeinfoMessage1(message);
                            //System.out.println("recv message:  " );

                        } else {
                            //System.out.println("no message " );
                            continue;
                        }
                    } catch (Exception e) {
                        logger.info("recv ueinfo error");
                        this.contructUeInfoConn1();

                    }

                }


            }

            private void contructUeInfoConn1() {
                boolean createConn = false;
                while (createConn == false) {
                    try {

                        if (useInfoConsumerConn1 != null) {
                            useInfoConsumerConn1.close();
                        }
                        logger.info("activemqBrokerIp :" + activemqBrokerIp);
                        ConnectionFactory factory = new org.apache.activemq.ActiveMQConnectionFactory("admin", "admin", activemqBrokerIp.trim());
                        Connection connection = factory.createConnection();
                        connection.start();
                        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
                        Destination destination = session.createQueue("iekunUeInfoReceiveQueue");
                        useInfoConsumer1 = session.createConsumer(destination);
                        logger.info("create conn to UeInfo activemq success! ");
                        useInfoConsumerConn1 = connection;
                        createConn = true;
                    } catch (Exception e) {

                        try {
                            Thread.currentThread().sleep(2000);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        logger.info("create conn to UeInfo activemq fail! ");

                    }
                }

            }

            private void parseUeinfoMessage1(Message message) {
                MapMessage MapMessage = (MapMessage) message;
                HandleRcvUeInfoService handleSendUeInfo = new HandleRcvUeInfoService();
                HashMap map;
                try {
                    map = (HashMap) MapMessage.getObject("SBXX");
                    JSONObject mapObject = JSONObject.parseObject(map.get("messageReceive").toString());
                    com.iekun.ef.jms.vo.Message receiveMessage = (com.iekun.ef.jms.vo.Message) JSONObject.toJavaObject(mapObject, com.iekun.ef.jms.vo.Message.class);
                    boolean dvIsReg = dvService.deviceIsExist(receiveMessage.getSerialNum());
                    if (dvIsReg == true) {
                        handleSendUeInfo.handleMessage(receiveMessage, ueInfoMapper, targetAlarmMapper, deviceMapper, systemMonitor, sqlSessionFactory, loggerServ, ueInfoShardingDao);
                    }
                } catch (JMSException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        }.start();
    }

    public void createHeartBeatLister() {
        new Thread() {
            public void run() {
                this.contructHeartBeatConn();
                for (; ; ) {
                    try {
                        Message message = (Message) hearbeatConsumer.receive(1000);
                        if (null != message) {
                            message.acknowledge();
                            this.parseHeartBeatMessage(message);
                            //System.out.println("recv message:  " );

                        } else {
                            //System.out.println("no message " );
                            continue;
                        }
                    } catch (Exception e) {
                        logger.info("recv heartbeat error");
                        this.contructHeartBeatConn();

                    }

                }

            }

            private void contructHeartBeatConn() {
                boolean createConn = false;
                while (createConn == false) {
                    try {
                        if (hearbeatConsumerConn != null) {
                            hearbeatConsumerConn.close();
                        }
                        logger.info("activemqBrokerIp :" + activemqBrokerIp);
                        ConnectionFactory factory = new org.apache.activemq.ActiveMQConnectionFactory("admin", "admin", activemqBrokerIp.trim());
                        Connection connection = factory.createConnection();
                        connection.start();
                        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
                        Destination destination = session.createQueue("iekunHeartBeatQueue");
                        hearbeatConsumer = session.createConsumer(destination);
                        logger.info("create conn to HeartBeat activemq success! ");
                        hearbeatConsumerConn = connection;
                        createConn = true;
                    } catch (Exception e) {

                        try {
                            Thread.currentThread().sleep(2000);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        logger.info("create conn to HeartBeat activemq fail! ");

                    }
                }

            }

            private void parseHeartBeatMessage(Message message) {
                MapMessage MapMessage = (MapMessage) message;
                HandleRcvHeartBeat handleRcvHeartBeat = new HandleRcvHeartBeat();
                HashMap map;
                try {
                    map = (HashMap) MapMessage.getObject("SBXX");
                    JSONObject mapObject = JSONObject.parseObject(map.get("messageReceive").toString());
                    com.iekun.ef.jms.vo.Message receiveMessage = (com.iekun.ef.jms.vo.Message) JSONObject.toJavaObject(mapObject, com.iekun.ef.jms.vo.Message.class);
                    boolean dvIsReg = dvService.deviceIsExist(receiveMessage.getSerialNum());
                    if (dvIsReg == true) {
                        handleRcvHeartBeat.handleMessage(receiveMessage, loggerServ);
                    }
                } catch (JMSException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        }.start();

    }

    public void createOamMsgLister() {

        new Thread() {
            public void run() {
                this.contructOamConn();
                for (; ; ) {
                    try {
                        Message message = (Message) oamMsgConsumer.receive(1000);
                        if (null != message) {
                            message.acknowledge();
                            this.parseOamMessage(message);
                            //System.out.println("recv message:  " );
                        } else {
                            //System.out.println("no message " );
                            continue;
                        }
                    } catch (Exception e) {
                        logger.info("recv oam error");
                        this.contructOamConn();
                    }
                }
            }

            private void contructOamConn() {
                boolean createConn = false;
                while (createConn == false) {
                    try {
                        if (oamMsgConsumerConn != null) {
                            oamMsgConsumerConn.close();
                        }

                        ConnectionFactory factory = new org.apache.activemq.ActiveMQConnectionFactory("admin", "admin", activemqBrokerIp.trim());
                        Connection connection = factory.createConnection();
                        connection.start();
                        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
                        Destination destination = session.createQueue("iekunReceiveQueue");
                        oamMsgConsumer = session.createConsumer(destination);
                        logger.info("create conn to OamMsg activemq success! ");
                        oamMsgConsumerConn = connection;
                        createConn = true;
                    } catch (Exception e) {
                        try {
                            Thread.currentThread().sleep(2000);
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        logger.info("create conn to OamMsg activemq fail! ");
                    }
                }

            }

            private void parseOamMessage(Message message) {
                MapMessage MapMessage = (MapMessage) message;
                HandleRcvUeInfoService handleSendUeInfo = new HandleRcvUeInfoService();
                HashMap map;
                try {
                    map = (HashMap) MapMessage.getObject("SBXX");
                    JSONObject mapObject = JSONObject.parseObject(map.get("messageReceive").toString());
                    com.iekun.ef.jms.vo.Message receiveMessage = (com.iekun.ef.jms.vo.Message) JSONObject.toJavaObject(mapObject, com.iekun.ef.jms.vo.Message.class);
                    boolean dvIsReg = dvService.deviceIsExist(receiveMessage.getSerialNum());
                    //logger.info("recv oam message ack: "+ receiveMessage.getPktType());
                    if (dvIsReg == true) {
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
                                logger.info("messageReceive=" + map.get("messageReceive").toString());
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
                            case "0020":
                                //HandleDownloadLog handleDownloadLog = new HandleDownloadLog();
                                handleDownloadLog.handleDownloadMessage(receiveMessage);
                                break;
                            default:
                                break;
                        }
                    }
                } catch (JMSException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        }.start();

    }

}
