package com.iekun.ef.model;

/**
 * Created by sword.yu on 2016/12/21.
 */
public class IMSIFollowTask extends Task {

    public final static Integer taskType = 4;

    private String targetIMSI;

    private String startTime;

    private String endTime;
    
    private String bottomTime;
    
    private String topTime;

    public IMSIFollowTask(  String name, String parameter, Long creatorId,
                              String createTime, String targetIMSI, String startTime, String endTime) {
        super( taskType, name, parameter, creatorId, createTime);
        this.targetIMSI = targetIMSI;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public IMSIFollowTask(Long id, String name, String parameter, String result,
                            String resultExt, Integer isFinish, float nowValue, String remark,
                            String createTime, String finishTime, Long creatorId,
                            Integer deleteFlag, String targetIMSI, String startTime, String endTime) {
        super(id, taskType, name, parameter, result, resultExt, isFinish, nowValue,
                remark, createTime, finishTime, creatorId, deleteFlag);
        this.targetIMSI = targetIMSI;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTargetIMSI() {
        return targetIMSI;
    }

    public void setTargetIMSI(String targetIMSI) {
        this.targetIMSI = targetIMSI;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

	public String getBottomTime() {
		return bottomTime;
	}

	public void setBottomTime(String bottomTime) {
		this.bottomTime = bottomTime;
	}

	public String getTopTime() {
		return topTime;
	}

	public void setTopTime(String topTime) {
		this.topTime = topTime;
	}
}
