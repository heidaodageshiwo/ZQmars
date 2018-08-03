package com.iekun.ef.push;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.controller.SigletonBean;
import com.iekun.ef.dao.MessageMapper;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.TaskMapper;
import com.iekun.ef.jms.vo.receive.CurrentAlarm;
import com.iekun.ef.jms.vo.receive.RcvUpgradeComplete;
import com.iekun.ef.model.Message;
import com.iekun.ef.model.Device;
import com.iekun.ef.model.DeviceSiteDetailInfo;
import com.iekun.ef.model.DeviceStatus;
import com.iekun.ef.model.PushAlarmTimeInfo;
import com.iekun.ef.model.Site;
import com.iekun.ef.model.TargetAlarm;
import com.iekun.ef.model.Task;
import com.iekun.ef.service.AnalysisService;
import com.iekun.ef.service.DeviceService;
import com.iekun.ef.service.MessageService;
import com.iekun.ef.service.SystemMonitor;
import com.iekun.ef.service.TargetAlarmService;
import com.iekun.ef.service.UserService;
import com.iekun.ef.util.SortClass;
import com.iekun.ef.util.TimeUtils;
import com.iekun.ef.service.SystemMonitorService;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Date;  

/**
 * Created by feilong.cai on 2016/10/26.
 */

@Component
public class NoticePush {

    private static Logger logger = LoggerFactory.getLogger(NoticePush.class);
    
    private static long pushTimeCnt = 0;

    @Autowired
    private SystemMonitor systemMonitor;

    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private TargetAlarmMapper targetAlarmMapper;

    @Autowired
    private AnalysisService analysisService;
    
    @Autowired
    private TargetAlarmService targetAlarmServ;

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UserService userServ;
    
    @Autowired
    private DeviceService deviceService;
       
    @Autowired
    private MessageMapper messageMapper;
    
    @Autowired
	private SessionDAO sessionDAO;

    @Autowired
    private SystemMonitorService systemMonitorService;

    @Bean
    public static NoticeWebSocketHandler noticeWebSocketHandler() {
        return new NoticeWebSocketHandler();
    }

    public void pushTaskProcess( Long taskId,  float progressVal ) {

        JSONObject textMsgJsonObj = new JSONObject();
        JSONObject progressJsonObj = new JSONObject();
        progressJsonObj.put("taskId", taskId );
        progressJsonObj.put("progress", progressVal );
        textMsgJsonObj.put("type", "updateProgressValue");
        textMsgJsonObj.put("data", progressJsonObj );
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));

    }
    
    public void pushExportProcess( float progressVal ) {

        JSONObject textMsgJsonObj = new JSONObject();
        JSONObject progressJsonObj = new JSONObject();
        
        progressJsonObj.put("progress", progressVal );
        textMsgJsonObj.put("type", "exportProgressValue");
        textMsgJsonObj.put("data", progressJsonObj );
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));

    }


    public void pushMessages( Long userId, Integer total, List<Message>  messages ) {

        JSONObject textMsgJsonObj = new JSONObject();
        JSONObject dataJsonObj = new JSONObject();
        JSONArray  messageArrayJsonObj = new JSONArray();

        for ( Message message: messages ) {
            JSONObject messageJsonObj = new JSONObject();
            messageJsonObj.put("id", message.getId());
            messageJsonObj.put("url", message.getDestUrl());
            messageJsonObj.put("info", message.getContent());
            messageArrayJsonObj.add(messageJsonObj);
        }

        dataJsonObj.put("count",  total);
        dataJsonObj.put("messages",messageArrayJsonObj );
        textMsgJsonObj.put("type", "updateMessageNotify");
        textMsgJsonObj.put("data", dataJsonObj );
        String textMsg = textMsgJsonObj.toJSONString();

        noticeWebSocketHandler().sendMessageToUser( userId,  new TextMessage( textMsg ));
        //noticeWebSocketHandler().sendMessageToUsers(new TextMessage( textMsg )); 
    }
    
    public void pushMessage(){
    	Long userId;
    	Integer total;
    	Collection<Session> sessions = sessionDAO.getActiveSessions();
    	for(Session session : sessions){
    		if (session == null)
    		{
    			continue;
    		}
    		String userStrId = String.valueOf(session.getAttribute("userId"));
    		if (userStrId.equals("null"))
    		{
    			continue;
    		}
    		userId = Long.parseLong(userStrId);
    		total = messageService.getMessageCountByUserIsRead( userId, 0 );
        	
            Map<String, Object> params = new HashedMap();
            params.put("userId", userId );
            params.put("isRead", 0 );
            params.put("start", 0 );
            params.put("length", total );
            List<Message>  list =  messageMapper.selectMessageByUserAndIsRead( params );
            this.pushMessages(userId ,total, list );
    	}
    	
    }

    @SuppressWarnings("unchecked")
	public  void pushAlarms( ) {
    	Map<String, Object>  DvAlarmInfoMap = SigletonBean.deviceAlarmInfoMaps;
    	Device DvSiteInfo;
    	String dvSn;
    	JSONObject textMsgJsonObj = new JSONObject();
    	JSONObject dataJsonObj   = new JSONObject();
    	
    	List alarmDetailList = new ArrayList(); 
    	List alarmTextList = new ArrayList();
    	List<WebSocketSession> users = NoticeWebSocketHandler.getUsers(); 
    	
    	if (users.size() == 0)
    	{
    		return;
    	}
      
    	/* 只能通过这个方式来查询在线userid;不能用SigletonBean.getUserId() */
      for ( WebSocketSession user : users)
      {
    	Long userId = (Long)(user.getAttributes().get("WEBSOCKET_USERID"));
    	long roleId = userServ.getUserDetailInfo(userId).getRoleId();
		
		for(Map.Entry<String, Object> dvAlarm:DvAlarmInfoMap.entrySet())
    	{
			 //设备告警 
			dvSn = dvAlarm.getKey();
			if ( deviceService.deviceIsBelongToUser(userId, dvSn) == false)
	    	{
	    		continue;
	    	}
			
			DvSiteInfo =  (Device)SigletonBean.deviceSiteInfoMaps.get(dvSn);
			ArrayList<CurrentAlarm> currAlarmList = (ArrayList<CurrentAlarm>)dvAlarm.getValue();
			if (currAlarmList.isEmpty())
			{
				continue;
			}
			for ( int i=0; i < currAlarmList.size(); i++)
			{
				JSONObject deviceAlarmJo = new JSONObject();
				deviceAlarmJo.put("info", DvSiteInfo.getName() + "告警:" + currAlarmList.get(i).getDvAlarm());
				deviceAlarmJo.put("type", 1);
				deviceAlarmJo.put("url", "/device/alarm/current");
  				alarmDetailList.add(new PushAlarmTimeInfo((currAlarmList.get(i).getAlarmTime()), deviceAlarmJo.toJSONString()));
			}
         	
         }
		
		Map<String,Object> params=new HashMap<String, Object>();
   	       
      
       if (roleId != 1)
       {
           List<Site> sites= deviceService.getSiteListInfoByUserId(userId);
           if(sites.size() == 0)
           {
          	Site site = new Site();
          	site.setSn("11323rtytewv12");
          	sites.add(site);
           }
           params.put("sites", sites);
           
           	Map<String,Object> ruleParams=new HashMap<String, Object>();
           	ruleParams.put("creatorId", userId);
           	List<String> targetImsiList = targetAlarmServ.getTargetImsiListByCreatorId(ruleParams);
    	    List<String> targetAreaCodeList = targetAlarmServ.getAreaCodeListByCreatorId(ruleParams);
    	        
           	if(targetImsiList.size() == 0)
   	        {
   	        	String targetImsi = "123409321sd23gff";
   	        	targetImsiList.add(targetImsi);
   	        }
   	
           	params.put("targetImsi", targetImsiList);
    	
           	if(targetAreaCodeList.size() == 0)
   	        {
   	        	String targetAreaCode = "123409321sd23gff";
   	        	targetAreaCodeList.add(targetAreaCode);
   	        }
   	
           	params.put("targetAreaCode", targetAreaCodeList);
           	
           
       	
         }
        else
         {
       	 	params.put("targetImsi", null);
       	 	params.put("targetAreaCode", null);
       	 	params.put("sites", null);
         }
       
   		 params.put("creatorId",  userId);
		 List<TargetAlarm>  targetList =(List<TargetAlarm>) targetAlarmMapper.getCurrentTargetList(params);
	     
    	 for(int i=0; i<targetList.size(); i++)
    	 {
    		 JSONObject targetAlarmJo = new JSONObject();
    		 TargetAlarm targetAlarm = (TargetAlarm)(targetList.get(i));
    		
    		 targetAlarmJo.put("info", targetAlarm.getRuleName() +"出现在" + targetAlarm.getSiteName());
    		 
    		 targetAlarmJo.put("type", 0);
    		 targetAlarmJo.put("url", "/target/current");
    		 alarmDetailList.add(new PushAlarmTimeInfo(targetAlarm.getCaptureTime(), targetAlarmJo.toJSONString()));
    	 }
		
		SortClass sort = new SortClass();  
        Collections.sort(alarmDetailList,sort); 
        for(int j =0; j<alarmDetailList.size(); j++)
        {
       	 PushAlarmTimeInfo  pushAlarInfo = (PushAlarmTimeInfo)(alarmDetailList.get(j));
       	 String AlarmMsg 	=  pushAlarInfo.getAlarmDetailInfo();
       	 alarmTextList.add(JSONObject.parse(AlarmMsg));
       	
        }
        
        dataJsonObj.put("count", alarmDetailList.size());
        dataJsonObj.put("alarms", JSONObject.parse(alarmTextList.toString()));
        textMsgJsonObj.put("type", "updateAlarmNotify");
        textMsgJsonObj.put("data", dataJsonObj);
        
        String textMsg = textMsgJsonObj.toJSONString();
     	noticeWebSocketHandler().sendMessageToUser(userId, new TextMessage( textMsg ));
      }
      
    }

    
    public  void pushTasks( Long userId ) {

        JSONObject textMsgJsonObj = new JSONObject();
        JSONObject dataJsonObj = new JSONObject();
        JSONArray  taskArrayJsonObj = new JSONArray();

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("creatorId",  userId );
        params.put("isFinish",  0 );

        List<Task>  tasks = taskMapper.getTasks(params);
        for ( Task task: tasks ) {
            JSONObject taskJsonObj = new JSONObject();
            float taskProcess = 0;
            if( analysisService.getCurrentTaskId().equals(task.getId())) {
                taskProcess = analysisService.getProgressNowValue();
            }
            task.setNowValue( taskProcess );

            taskJsonObj.put("id", task.getId());
            taskJsonObj.put("info", task.getName());
            taskJsonObj.put("progress", taskProcess);
            switch ( task.getType() ) {
                case 0:  //上号统计
                    taskJsonObj.put("url" , "/analysis/imsiStatistics" );
                    break;
                case 1: //嫌疑人运动轨迹
                    taskJsonObj.put("url" , "/analysis/suspectTrail" );
                    break;
                case 2: //数据碰撞分析
                    taskJsonObj.put("url" , "/analysis/dataCollide" );
                    break;
                case 3: //常驻人口外来人口分析
                    taskJsonObj.put("url" , "/analysis/residentPeople" );
                    break;
                case 4: //IMSI伴随分析
                    taskJsonObj.put("url" , "/analysis/imsiFollow" );
                    break;
                default:
                    taskJsonObj.put("url" , "###" );
            }

            taskArrayJsonObj.add(taskJsonObj);
        }

        dataJsonObj.put("count",  taskArrayJsonObj.size());
        dataJsonObj.put("tasks",taskArrayJsonObj );
        textMsgJsonObj.put("type", "updateTaskNotify");
        textMsgJsonObj.put("data", dataJsonObj );
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));

    }

    public  void pushTasks() {

        List<Long> userIdList =  noticeWebSocketHandler().getWebSocketUserId();
        for (Long userId: userIdList  ) {
             this.pushTasks( userId );
        }
    }

    public void pushUeInfoTotal() {
        JSONObject textMsgJsonObj = new JSONObject();
        Long ueInfoTotal = systemMonitor.getUeInfoTotal();
        textMsgJsonObj.put("type", "updateUETotal");
        textMsgJsonObj.put("data", ueInfoTotal );
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));
    }

    /**
     * 查询黑名单预警个数
     * 修改之后
     * zkz
     */
    public void pushBlacklistTotal() {
        JSONObject textMsgJsonObj = new JSONObject();
        //Long blacklistTotal = systemMonitor.getBlacklistAlarmTotal();
        Long blacklistTotal = systemMonitorService.queryAllBlackListAlarmCount();
        textMsgJsonObj.put("type", "updateBlacklistTotal");
        textMsgJsonObj.put("data", blacklistTotal );
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));
    }

    /**
     * 查询归属地预警个数
     * 修改之后
     * zkz
     */
    public void pushHomeOwnershipTotal() {
        JSONObject textMsgJsonObj = new JSONObject();
        //Long homeOwnershipTotal = systemMonitor.getHomeOwnershipAlarmTotal();
        Long homeOwnershipTotal = systemMonitorService.queryAllBlackAreaAlarmCount();
        textMsgJsonObj.put("type", "updateHomeOwnershipTotal");
        textMsgJsonObj.put("data", homeOwnershipTotal );
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));
    }

    public void pushSystemRunningDuration() {

        JSONObject textMsgJsonObj = new JSONObject();
        JSONObject runTimeJsonObj =  systemMonitor.getSystemRunningDuration();
        textMsgJsonObj.put("type", "updateRunTime");
        textMsgJsonObj.put("data", runTimeJsonObj );
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));
    }

    public void pushHardDiskInfo() {

        JSONObject textMsgJsonObj = new JSONObject();
        JSONObject hardDiskJsonObj =  systemMonitor.getHardDisk();
        textMsgJsonObj.put("type", "updateHardDisk");
        textMsgJsonObj.put("data", hardDiskJsonObj );
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));
    }
    
    public void pushGeneralDvStatusInfo()
    {
    	JSONObject dvJo =  new JSONObject();
    	Integer runCnt = 0;
        Integer offlineCnt = 0;
        Integer waringCnt = 0;
        Integer willExpireCnt = 0;
        Integer expiredCnt = 0;
        Integer totalCnt = 0;
        DeviceStatus deviceStatus;
        
        Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
        
        for(Map.Entry<String, Object> entry:dvStatusMaps.entrySet())
        {
        	deviceStatus = (DeviceStatus)(entry.getValue());
        	switch(deviceStatus.getDeviceStatusCommon().getDeviceStatus())
        	{
        		case "run":
        			runCnt++;
        			break;
        		case "offline":
        			offlineCnt++; 
        			break;
        		case "willexpire":
        			willExpireCnt++;
        			break;
        		case "expiredfailure":
        			expiredCnt++;
        			break;
        		case "warning":
        			waringCnt++;
        			break;
        		default:
        			break;
        	}
        }
        
        totalCnt = runCnt + offlineCnt + willExpireCnt + expiredCnt + waringCnt;
       
        dvJo.put("total", totalCnt );
        dvJo.put("running", runCnt );
        dvJo.put("offline", offlineCnt );
        dvJo.put("willExpire", willExpireCnt );
        dvJo.put("expiredFailure", expiredCnt );
        dvJo.put("warning", waringCnt );
        
        JSONObject textMsgJsonObj = new JSONObject();
        textMsgJsonObj.put("type", "updateDeviceStatus");
        textMsgJsonObj.put("data", dvJo);
        
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));
    }
    
    private int getSiteStatus(List<Device> deviceList)
    {
    	Map<String, Object> dvStatusMaps = SigletonBean.deviceStatusInfoMaps;
    	Map<String, Object>  DvAlarmInfoMap = SigletonBean.deviceAlarmInfoMaps;
    	ArrayList<CurrentAlarm> currentAlarmList = null;
    	DeviceStatus deviceStatus = null;
    	String sn;
    	int siteStatusVal = 1;    	
    	for(int i=0; i<deviceList.size(); i++)
    	{
    		sn = deviceList.get(i).getSn();
    		deviceStatus = (DeviceStatus)dvStatusMaps.get(sn);
    		if (deviceStatus.getDeviceStatusCommon().getDeviceStatus().equals("offline"))
    		{
    			siteStatusVal = 3; /* 站点下有设备离线，则该站点显示离线 */
    			break;
    		}
    		
    		currentAlarmList = (ArrayList<CurrentAlarm>)DvAlarmInfoMap.get(sn);
    		if (currentAlarmList != null)  /* 设备在系统启动以来没有过报警，告警列表不会生成 */
    		{
    			if(currentAlarmList.size() != 0)
        		{
        			siteStatusVal = 2; /* 站点下有设备告警，则该站点显示告警 */
        		}
    		}
    		
    	}
    	
    	return siteStatusVal;
    }
    
    public void pushSiteStatusInfo()
    {
    	 Map<String, Object> siteDvMaps = SigletonBean.siteDeviceMaps;
    	 Map<String, Object> siteSnStMaps = SigletonBean.siteSnSiteMaps;
    	 
    	 String siteId;
    	 List<Device> deviceList = null;
    	 JSONObject textMsgJsonObj =new JSONObject();
         JSONArray ja = new JSONArray();
    	 for(Map.Entry<String, Object> siteDv: siteDvMaps.entrySet())
     	 {
    		 JSONObject siteInfoJo = new JSONObject();
    		 siteId = siteDv.getKey();
    		 //Site site = (Site)siteSnStMaps.get(siteId);
    		 deviceList = (List<Device>)siteDv.getValue();
    		 if (deviceList.size() != 0)
    		 {
    			 siteInfoJo.put("sn", deviceList.get(0).getSite().getSn());
        		 siteInfoJo.put("status", this.getSiteStatus(deviceList));
    		 }
    		 else
    		 {
    			 siteInfoJo.put("sn", this.getSiteSnBySiteId(Long.parseLong(siteId)));
    			 siteInfoJo.put("status", 2); 
    		 }
    		 
    		 ja.add(siteInfoJo);
     	 }
     	
    	 textMsgJsonObj.put("type", "updateSitesState");
         textMsgJsonObj.put("data", ja);
         
         String textMsg = textMsgJsonObj.toJSONString();
         noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));
    	
    }
    
    private String getSiteSnBySiteId(long siteId)
    {
    	String siteSn = null;
    	Site site;
    	
    	Map<String, Object> siteSnStMaps = SigletonBean.siteSnSiteMaps;
    	for(Map.Entry<String, Object> siteSnSt: siteSnStMaps.entrySet())
    	{
    		site = (Site)(siteSnSt.getValue());
    		if (site.getId().equals(siteId))
    		{
       			siteSn = site.getSn(); 
    			break;
    		}
    	}
    	
    	return siteSn;
    }
    
    public void pushServDateTime()
    {
    	JSONObject textMsgJsonObj = new JSONObject();
        textMsgJsonObj.put("type", "serverTimeNotify");
        textMsgJsonObj.put("data", TimeUtils.timeFormatter.format( new Date()));
        String textMsg = textMsgJsonObj.toJSONString();
        noticeWebSocketHandler().sendMessageToUsers( new TextMessage( textMsg ));
    }
    
    /**
     * 定时推送信息到前端
     */
    @Scheduled(fixedDelay = 5000)
    public void noticePushSchedule(){
        pushSystemRunningDuration();
          
        pushUeInfoTotal();
        pushBlacklistTotal();
        pushHomeOwnershipTotal();
        pushGeneralDvStatusInfo();
        pushAlarms();
        pushSiteStatusInfo();
        pushMessage();
    }       
    
   /* @Scheduled(fixedDelay = 1000)
    public void servTimePushSchedule(){
    	if (pushTimeCnt%604800000 ==100)
    	{
    		pushServDateTime();
    	}
    	pushTimeCnt = pushTimeCnt + 1;
     }*/
    
    @Scheduled(fixedDelay = 301031)
    public void servDiskSpaceTimePushSchedule(){
    	
    	 pushHardDiskInfo();
    }
    

}
