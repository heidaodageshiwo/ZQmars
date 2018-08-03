package com.iekun.ef.scheduling;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iekun.ef.dao.DeviceMapper;
import com.iekun.ef.dao.UeInfoMapper;
import com.iekun.ef.dao.UeInfoShardingDao;
import com.iekun.ef.jms.service.HandleRcvUeInfoService;
import com.iekun.ef.service.SystemMonitor;

@Component
@ConditionalOnProperty(prefix = "efence.properties.old", value = "storage-recive-ueinfo", matchIfMissing = false)
public class DetectRcvUeinfoNum {
	
	private Logger logger = LoggerFactory.getLogger(DetectRcvUeinfoNum.class);
	public static Long totalUeinfoNum = new Long(0);
	
	public static Long totalTargetUeinfoNum = new Long(0);
	
	@Autowired
	SystemMonitor systemMonitor;
	
	@Autowired
	UeInfoMapper ueInfoMapper;
	
	@Autowired
	DeviceMapper deviceMapper;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	UeInfoShardingDao ueInfoShardingDao;
	
	@Scheduled(fixedRate = 30000)
	public void decimationSendUeCnt() throws Exception{
		
		HandleRcvUeInfoService handleRcvUeInfoService = new HandleRcvUeInfoService();
		
		if (this.totalUeinfoNum.equals(systemMonitor.getUeInfoTotal()))
		{
			logger.info("enter into DetectRcvUeinfoNum !");
			handleRcvUeInfoService.remainedUeinfoIntoDbWithoutReport(ueInfoMapper, deviceMapper,  sqlSessionFactory, ueInfoShardingDao);
		}
		this.totalUeinfoNum = systemMonitor.getUeInfoTotal();
		
		handleRcvUeInfoService.checkRemainedDataWithReport(ueInfoMapper, deviceMapper,  sqlSessionFactory, ueInfoShardingDao);
		
	}
	
	
	@Scheduled(fixedRate = 30000)
	public void decimationTargetInfoIntoDb()throws Exception{
		
		HandleRcvUeInfoService handleRcvUeInfoService = new HandleRcvUeInfoService();
		
		handleRcvUeInfoService.checkRemainedTargetUeInfo(sqlSessionFactory);
		
	}

}
