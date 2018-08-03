package com.iekun.ef.model;

import java.util.List;

/**
 * 设备上号异常viewmodel类，主要用于接收前端传来参数
 */
public class DeviceUpNumExViewModel {

    private String deviceId;

    //待添加列表
    private List<DeviceUpNumExModel> addList;
    //待修改列表
    private List<DeviceUpNumExModel> updateList;
    //待删除列表
    private List<DeviceUpNumExModel> deleteList;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<DeviceUpNumExModel> getAddList() {
        return addList;
    }

    public void setAddList(List<DeviceUpNumExModel> addList) {
        this.addList = addList;
    }

    public List<DeviceUpNumExModel> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<DeviceUpNumExModel> updateList) {
        this.updateList = updateList;
    }

    public List<DeviceUpNumExModel> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<DeviceUpNumExModel> deleteList) {
        this.deleteList = deleteList;
    }
}
