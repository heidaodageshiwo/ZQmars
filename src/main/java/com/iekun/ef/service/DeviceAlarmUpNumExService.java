package com.iekun.ef.service;

import com.iekun.ef.dao.DeviceUpNumExMapper;
import com.iekun.ef.model.DeviceUpNumExModel;
import com.iekun.ef.model.ResultBean;
import com.iekun.ef.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceAlarmUpNumExService {

    @Autowired
    private DeviceUpNumExMapper deviceUpNumExMapper;

    public ResultBean<List<DeviceUpNumExModel>> getHistoryConfigs(String deviceSn){
        List<DeviceUpNumExModel> configList = deviceUpNumExMapper.getHistoryConfigs(deviceSn);
        return new ResultBean<>(configList);
    }

    public ResultBean<Integer> addUpNumExConfigs(List<DeviceUpNumExModel> configs){
        int addCount = deviceUpNumExMapper.insertConfigs(configs);
        return new ResultBean<>(addCount);
    }

    public ResultBean<Integer> deleteUpNumExConfigs(DeviceUpNumExModel config){
        int addCount = deviceUpNumExMapper.deleteConfig(config);
        return new ResultBean<>(addCount);
    }

    public ResultBean<Integer> updateUpNumExConfigs(DeviceUpNumExModel config){
        int addCount = deviceUpNumExMapper.updateConfigByPrimaryKey(config);
        return new ResultBean<>(addCount);
    }

    @Transactional
    public ResultBean<Integer> writeAllUpNumExConfigs(String deviceId, List<DeviceUpNumExModel> addList, List<DeviceUpNumExModel> updateList, List<DeviceUpNumExModel> deleteList){
        List<DeviceUpNumExModel> configList = deviceUpNumExMapper.getHistoryConfigs(deviceId);
        //判断是否可更新
        int addCount = 0, updateCount = 0, deleteCount = 0;
        //插入
        if(addList != null && !addList.isEmpty()){
            //判断是否可插入
            for(DeviceUpNumExModel deviceUpNumExModel : addList){

//                List<DeviceUpNumExModel> addCheckResult = configList.stream().filter(s -> {
//                            return deviceUpNumExModel.getStartTime().compareTo(s.getStartTime()) >= 0 && deviceUpNumExModel.getStartTime().compareTo(s.getEndTime()) <= 0 ||
//                                    deviceUpNumExModel.getEndTime().compareTo(s.getStartTime()) >= 0 && deviceUpNumExModel.getEndTime().compareTo(s.getEndTime()) <= 0;
//                        }
//                ).collect(Collectors.toList());

                List<DeviceUpNumExModel> addCheckResult = configList.stream().filter(s -> {
                            return (DateUtils.compareDateTime(deviceUpNumExModel.getStartTime(), s.getStartTime()) >= 0
                                    && DateUtils.compareDateTime(deviceUpNumExModel.getStartTime(), s.getEndTime()) <= 0)
                                    || (DateUtils.compareDateTime(deviceUpNumExModel.getEndTime(), s.getStartTime()) >= 0
                                            && DateUtils.compareDateTime(deviceUpNumExModel.getEndTime(), s.getEndTime()) <= 0);
                        }
                ).collect(Collectors.toList());

                if(!addCheckResult.isEmpty()){
                    new ResultBean<>(addCheckResult, "新增配置时间与之前记录冲突");
                }
            }

            addCount = deviceUpNumExMapper.insertConfigs(addList);
        }

        //更新
        if(updateList != null && !updateList.isEmpty()){
            for(DeviceUpNumExModel deviceUpNumExModel : updateList){
                List<DeviceUpNumExModel> addCheckResult = configList.stream().filter(s -> {
                            return  s.getId() != deviceUpNumExModel.getId() &&
                                    ((DateUtils.compareDateTime(deviceUpNumExModel.getStartTime(), s.getStartTime()) >= 0
                                            && DateUtils.compareDateTime(deviceUpNumExModel.getStartTime(), s.getEndTime()) <= 0)
                                    || (DateUtils.compareDateTime(deviceUpNumExModel.getEndTime(), s.getStartTime()) >= 0
                                    && DateUtils.compareDateTime(deviceUpNumExModel.getEndTime(), s.getEndTime()) <= 0));
                        }
                ).collect(Collectors.toList());

                if(!addCheckResult.isEmpty()){
                    new ResultBean<>(addCheckResult, "修改的配置时间与之前记录冲突");
                }
            }

            updateCount = deviceUpNumExMapper.updateConfigs(updateList);
        }

        //删除
        if(deleteList != null && !deleteList.isEmpty()){
            deleteCount = deviceUpNumExMapper.deleteConfigs(deleteList);
        }

        int resultCount = addCount + updateCount + deleteCount;
        return new ResultBean<>(resultCount);
    }
}
