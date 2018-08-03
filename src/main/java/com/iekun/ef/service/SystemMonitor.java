package com.iekun.ef.service;

import com.alibaba.fastjson.JSONObject;
import com.iekun.ef.dao.AnalysisShardingDao;
import com.iekun.ef.dao.TargetAlarmMapper;
import com.iekun.ef.dao.UeInfoMapper;
import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by feilong.cai on 2016/11/24.
 */


@Component
public class SystemMonitor {

    public static Logger logger = LoggerFactory.getLogger( SystemMonitor.class);

    private Date  startTime;
    private Long  ueInfoTotal ;
    private Long  blacklistAlarmTotal;
    private Long  homeOwnershipAlarmTotal;

    @Autowired
    private UeInfoMapper ueInfoMapper;

    @Autowired
    private TargetAlarmMapper targetAlarmMapper;
    
    @Autowired
    private AnalysisShardingDao  analysisShardingDao;

    @PostConstruct
    public void start() throws Exception {
        logger.info("SystemMonitor is construct!\n");
        this.startTime = new Date();
        //ueInfoTotal = ueInfoMapper.getUeInfoTotal();
        ueInfoTotal = analysisShardingDao.getUeInfoTotalCnt();
        blacklistAlarmTotal = targetAlarmMapper.getTargetAlarmTotal( 1 );
        homeOwnershipAlarmTotal = targetAlarmMapper.getTargetAlarmTotal( 2);

    }

    @PreDestroy
    public void stop() throws Exception {
        logger.info("SystemMonitor is destroy!\n");
    }

    public JSONObject getSystemRunningDuration(){
        JSONObject jsonObject = new JSONObject();

        Date nowDate =  new Date();
        long diff = nowDate.getTime() - this.startTime.getTime();
        long day = diff/(24*60*60*1000);
        long hour = (diff/(60*60*1000)-day*24);
        long min = ((diff/(60*1000))-day*24*60-hour*60);
        long sec =  (diff/1000-day*24*60*60-hour*60*60-min*60);

        logger.debug("系统已经运行:" +day+"天"+hour+"小时"+min+"分"+sec+"秒");

        jsonObject.put("day", day);
        jsonObject.put("hour", hour);
        jsonObject.put("minute", min);
        jsonObject.put("second", sec);

        return jsonObject;
    }

    public Long getUeInfoTotal(){
        //新增处理
        //zkz
        ueInfoTotal = analysisShardingDao.getUeInfoTotalCnt();
        return  this.ueInfoTotal;
    }

    public Long getBlacklistAlarmTotal(){
        return  this.blacklistAlarmTotal;
    }

    public Long getHomeOwnershipAlarmTotal() {
        return this.homeOwnershipAlarmTotal;
    }

    public void incrementUeInfoTotal() {
        this.ueInfoTotal++;
    }

    public void incrementBlacklistAlarmTotal() {
        this.blacklistAlarmTotal++;
    }

    public void incrementHomeOwnershipAlarmTotal() {
        this.homeOwnershipAlarmTotal++;
    }

    public JSONObject getHardDisk() {
        JSONObject jsonObject = new JSONObject();

        Sigar sigar = new Sigar();
        OperatingSystem sys = OperatingSystem.getInstance();

        long hardDiskTotal =  0;
        long hardDiskFree =  0;
        long hardDiskAvail =  0;
        long hardDiskUsed =  0;

        try{

            FileSystem[] fslist = sigar.getFileSystemList();
            for ( FileSystem fs:fslist ) {

                FileSystemUsage usage = sigar.getFileSystemUsage( fs.getDirName());
                switch (fs.getType()) {
                    case FileSystem.TYPE_LOCAL_DISK:
                        hardDiskTotal += usage.getTotal();
                        hardDiskFree += usage.getFree();
                        hardDiskAvail += usage.getAvail();
                        hardDiskUsed += usage.getUsed();
                        logger.info("  fs.DirName: " + fs.getDirName());
                        logger.info("  Total: " + usage.getTotal() / 1024 + "MB" );
                        logger.info("  Free:  " + usage.getFree() / 1024 + "MB");
                        logger.info("  Avail: " + usage.getAvail() / 1024 + "MB" );
                        logger.info("   Used: " + usage.getUsed() / 1024 + "MB" );

                }
            }

        } catch ( SigarException e) {
            e.printStackTrace();
        }

        logger.info("  Total: " + hardDiskTotal / 1024 + "MB" );
        logger.info("  Free:  " + hardDiskFree / 1024 + "MB");
        logger.info("  Avail: " + hardDiskAvail / 1024 + "MB" );
        logger.info("   Used: " + hardDiskUsed / 1024 + "MB" );

        jsonObject.put("used",  hardDiskUsed / 1024 );
        jsonObject.put("free",  hardDiskFree / 1024 );

        return jsonObject;
    }


}
