package com.iekun.ef.model;

/**
 * Created by feilong.cai on 2016/11/25.
 */

public class Task {

    /**
     * 任务ID
     */
    private Long id;

    /**
     * 任务类型
     */
    private Integer type;

    /**
     * 任务名称
     */
    private String  name;

    /**
     * 分析任务参数
     */
    private  String parameter;

    /**
     * 分析任务结果
     */
    private  String result;

    /**
     * 分析任务结果扩展
     */
    private  String resultExt;
    
    /**
     * 导出路径
     */
    private  String exportPath;   

    /**
     * 是否完成。0-未完成，1-已完成
     */
    private  Integer isFinish;

    /**
     *  尝试次数
     */
    private  Integer tryCount;

    /**
     * 任务进度
     */
    private  float  nowValue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 任务完成时间
     */
    private String finishTime;


    /**
     * 创建者用户ID
     */
    private  Long creatorId;

    /**
     * 删除标记: 0-  正常，  1-  删除
     */
    private Integer deleteFlag;


    public Task() {
        super();
    }

    public Task(Integer type, String name, String parameter, Long creatorId, String createTime) {
        this.type = type;
        this.name = name;
        this.parameter = parameter;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.isFinish = 0;
        this.tryCount = 0;
        this.deleteFlag = 0;
    }

    public Task(Integer type, String name, String parameter, String result, String resultExt,
                Integer isFinish, float nowValue, String remark, String createTime,
                String finishTime, Long creatorId ) {
        this.type = type;
        this.name = name;
        this.parameter = parameter;
        this.result = result;
        this.resultExt = resultExt;
        this.isFinish = isFinish;
        this.nowValue = nowValue;
        this.remark = remark;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.creatorId = creatorId;
        this.tryCount = 0;
        this.deleteFlag = 0;
    }

    public Task( Long id, Integer type, String name, String parameter, String result, String resultExt,
                 Integer isFinish, float nowValue, String remark, String createTime,
                 String finishTime, Long creatorId ) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.parameter = parameter;
        this.result = result;
        this.resultExt = resultExt;
        this.isFinish = isFinish;
        this.nowValue = nowValue;
        this.remark = remark;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.creatorId = creatorId;
    }

    public Task(Long id, Integer type, String name, String parameter, String result,
                String resultExt, Integer isFinish, float nowValue, String remark,
                String createTime, String finishTime, Long creatorId, Integer deleteFlag) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.parameter = parameter;
        this.result = result;
        this.resultExt = resultExt;
        this.isFinish = isFinish;
        this.nowValue = nowValue;
        this.remark = remark;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.creatorId = creatorId;
        this.deleteFlag = deleteFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultExt() {
        return resultExt;
    }

    public void setResultExt(String resultExt) {
        this.resultExt = resultExt;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public float getNowValue() {
        return nowValue;
    }

    public void setNowValue(float nowValue) {
        this.nowValue = nowValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

	public String getExportPath() {
		return exportPath;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}
}
