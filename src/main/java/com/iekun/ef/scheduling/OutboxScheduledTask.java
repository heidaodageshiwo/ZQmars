package com.iekun.ef.scheduling;

import com.iekun.ef.service.DeviceService;
import com.iekun.ef.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iekun.ef.dao.AreaRuleMapper;
import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.dao.OutboxMapper;
import com.iekun.ef.dao.SysParamMapper;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.TargetRuleMapper;
import com.iekun.ef.service.SmsService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OutboxScheduledTask {

    public static Logger logger = LoggerFactory.getLogger(OutboxScheduledTask.class);

    
   @Autowired
   SmsService sendSmsService;
   
   @Autowired
   TargetRuleMapper targetRuleMapper;
   
   @Autowired
   DeviceMapper deviceMapper;
   
   @Autowired
   TargetAlarmMapper targetAlarmMapper;
   
   @Autowired	
   SysParamMapper sysParamMapper;
   
   @Autowired
   OutboxMapper outboxMapper;
   
   @Autowired
   AreaRuleMapper areaRuleMapper;

    @Autowired
    private EmailService emailService;


    @Autowired
    private DeviceService dvServ;


    //注释掉target_alarm处理
    //@Scheduled(fixedRate = 30000)
    public void inOutBox() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("OutBox In time:" + dateFormat.format(new Date()));
        //// TODO: 2016/11/21  
       
        sendSmsService.setMapper(dvServ,targetRuleMapper, areaRuleMapper, deviceMapper, targetAlarmMapper, sysParamMapper,outboxMapper);
       
        sendSmsService.decimationCreateSms();
    }

    //注释掉target_alarm处理
    //@Scheduled(fixedRate = 30000)
    public void outOutBox() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("OutBox Out time:" + dateFormat.format(new Date()));
        //// TODO: 2016/11/21 
        sendSmsService.setMapper(dvServ,targetRuleMapper, areaRuleMapper, deviceMapper, targetAlarmMapper, sysParamMapper,outboxMapper);
        
        sendSmsService.decimationSendSms();

        //发送邮件
        emailService.sendEmail();
    }

}
