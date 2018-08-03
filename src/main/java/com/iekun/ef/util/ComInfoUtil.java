package com.iekun.ef.util;


import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.model.Message;
import com.iekun.ef.service.MessageService;
import com.iekun.ef.service.SystemParaService;
import com.iekun.ef.service.TaskService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feilong.cai  on 2016/11/26.
 */

@Component
public class ComInfoUtil {

    public static org.slf4j.Logger logger = LoggerFactory.getLogger(  ComInfoUtil.class );

    @Autowired
    private TaskService taskService;

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private SystemParaService sysParaServ;

    public void getTopHeaderInfo( Map<String, Object> model){
        //站内通知消息
        getMessageInfo(model);
        //告警消息
        getAlarmInfo(model);
        //任务消息
        getTaskInfo(model);
    }

    private void getMessageInfo(  Map<String, Object> model ) {
        Map<String, Object>  messages= new HashMap();
        List messageArray = new ArrayList();

        long userId = SigletonBean.getUserId();
        Integer totalCnt = messageService.getMessageCountByUserIsRead(userId, 0);
        List<Message> messageList= messageService.getMessageByUserIsRead( userId, 0, totalCnt, 0 );
        for ( Message message: messageList ) {
            Map<String, Object>  messageMap = new HashMap();

            messageMap.put("info", message.getContent());
            messageMap.put("url", message.getDestUrl());

            messageArray.add( messageMap );
        }

        messages.put("count",  totalCnt);
        messages.put("messages", messageArray );

        model.put("messages", messages);
    }

    private  void getAlarmInfo(  Map<String, Object> model ) {
        Map<String, Object>  alarms= new HashMap();
        List alarmList = new ArrayList();
        alarms.put("count", alarmList.size() );
        alarms.put("alarms", alarmList);


        model.put("alarms", alarms);
    }

    private void getTaskInfo(  Map<String, Object> model ) {

        Map<String, Object>  tasks= new HashMap();
        List taskList = new ArrayList();

        List<com.iekun.ef.model.Task> originalTasks =  taskService.getProcessTasksByUserId(SigletonBean.getUserId());
        for ( com.iekun.ef.model.Task task : originalTasks) {

            Map<String, Object>  taskMap = new HashMap();


            taskMap.put("id" , task.getId());
            taskMap.put("progress" , task.getNowValue() );
            taskMap.put("info" , task.getName() );
            switch ( task.getType() ) {
                case 0:  //上号统计
                    taskMap.put("url" , "/analysis/imsiStatistics" );
                    break;
                case 1: //嫌疑人运动轨迹
                    taskMap.put("url" , "/analysis/suspectTrail" );
                    break;
                case 2: //数据碰撞分析
                    taskMap.put("url" , "/analysis/dataCollide" );
                    break;
                case 3: //常驻人口外来人口分析
                    taskMap.put("url" , "/analysis/residentPeople" );
                    break;
                case 4: //IMSI伴随分析
                    taskMap.put("url" , "/analysis/imsiFollow" );
                    break;
                default:
                    taskMap.put("url" , "###" );
            }

            taskList.add( taskMap );

        }

        tasks.put("count", taskList.size());
        tasks.put("tasks", taskList);

        model.put("tasks", tasks);
    }

    public void getSystemInfo(  Map<String, Object> model ){

        Map<String, Object> systemInfo = new HashMap();
        systemInfo.put("version",SystemInfo.getVersion());
        String companyYear = sysParaServ.getSysPara("companyYear");
        if(companyYear == null)
        {
        	companyYear = "2014";
        }
        systemInfo.put("companyYear",companyYear + "-" + TimeUtils.yearFormatterStr.format( new Date()));
        String companyName = sysParaServ.getSysPara("companyName");
        if (companyName == null)
        {
        	companyName = "上海翊坤";
        }
        systemInfo.put("companyName",companyName);
        String companyUrl = sysParaServ.getSysPara("companyURL");
        if (companyUrl == null)
        {
        	companyUrl = "www.iekun.com";
        }
        systemInfo.put("companyURL", companyUrl);
        String SystemName = sysParaServ.getSysPara("systemName");
        if (SystemName == null)
        {
        	SystemName = "移动终端预警系统";
        }
        systemInfo.put("projectName", SystemName);
        String systemIcon = sysParaServ.getSysPara("systemIcon");
        if (systemIcon == null)
        {
        	systemIcon = "/img/logo.png";
        }
        systemInfo.put("projectIcon",/*"/img/logo.png"*/ systemIcon);

        model.put("systemInfo", systemInfo);
    }


}
