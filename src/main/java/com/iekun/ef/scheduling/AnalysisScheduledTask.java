package com.iekun.ef.scheduling;

import com.iekun.ef.dao.TaskMapper;
import com.iekun.ef.model.Task;
import com.iekun.ef.push.NoticePush;
import com.iekun.ef.service.AnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据分析定时任务
 * Created by feilong.cai on 2016/11/25.
 */
@Component
public class AnalysisScheduledTask {

    public static Logger logger = LoggerFactory.getLogger(AnalysisScheduledTask.class);

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private NoticePush noticePush;

    //@Scheduled(fixedDelay = 1000)
    @Scheduled(fixedRate = 1000)
    public void analysisTaskProcess() {

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("isFinish",  0 );
        params.put("orderByTryCount",  0 );

        List<Task> tasks = taskMapper.getTasks(params);
        logger.debug("task count: " + tasks.size() );
        //logger.info("统计分析任务扫描执行!");
        if( 0 == tasks.size() ) {
            return ;
        }

        Task task = tasks.get(0);
        if( null != task ) {
            switch ( task.getType() ) {
                case 0:  //上号统计
                    analysisService.statisticsIMSI( task );
                    break;
                case 1: //嫌疑人运动轨迹
                    analysisService.analysisSuspectTrail( task );
                    break;
                case 2: //数据碰撞分析
                    analysisService.analysisDataCollide( task );
                    break;
                case 3: //常驻人口外来人口分析
                    analysisService.analysisResidentPeople( task );
                    break;
                case 4: //IMSI伴随分析
                    analysisService.analysisIMSIFollow( task );
                    break;
                default:
                    return;
            }

            noticePush.pushTasks();
        }
    }
}
