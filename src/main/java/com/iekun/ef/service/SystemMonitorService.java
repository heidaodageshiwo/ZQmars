package com.iekun.ef.service;

import com.iekun.ef.dao.TargetAlarmNewEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemMonitorService {
    @Autowired
    private TargetAlarmNewEntityMapper targetAlarmNewEntityMapper;

    public long queryAllBlackListAlarmCount() {
        return targetAlarmNewEntityMapper.queryAllBlackListAlarmCount();
    }

    public long queryAllBlackAreaAlarmCount() {
        return targetAlarmNewEntityMapper.queryAllBlackAreaAlarmCount();
    }
}
