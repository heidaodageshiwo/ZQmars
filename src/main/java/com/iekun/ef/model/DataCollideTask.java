package com.iekun.ef.model;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by sword.yu on 2016/12/21.
 */
public class DataCollideTask extends Task  {

    public final static Integer taskType = 2;

    private JSONArray  spaceTimeItems;

    public DataCollideTask(  String name, String parameter, Long creatorId,
                            String createTime, JSONArray spaceTimeItems ) {
        super( taskType, name, parameter, creatorId, createTime);
        this.spaceTimeItems = spaceTimeItems;
    }

    public DataCollideTask(Long id, String name, String parameter, String result,
                          String resultExt, Integer isFinish, float nowValue, String remark,
                          String createTime, String finishTime, Long creatorId,
                          Integer deleteFlag, JSONArray spaceTimeItems  ) {
        super(id, taskType, name, parameter, result, resultExt, isFinish, nowValue,
                remark, createTime, finishTime, creatorId, deleteFlag);
        this.spaceTimeItems = spaceTimeItems;
    }

    public JSONArray getSpaceTimeItems() {
        return spaceTimeItems;
    }

    public void setSpaceTimeItems(JSONArray spaceTimeItems) {
        this.spaceTimeItems = spaceTimeItems;
    }
}
