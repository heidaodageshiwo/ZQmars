package com.iekun.ef.model;

/**
 * Created by sword.yu  on 2017/1/16.
 */
public class ShardingQueryInfo {

    /**
     *  查询条件MD5字符值
     */
    private String  queryCondition;

    /**
     * 分片查询临时表名
     */
    private String  shardingTmpName;

    /**
     * 创建时间
     */
    private String  createTime;


    public ShardingQueryInfo() {
    }

    public ShardingQueryInfo(String queryCondition, String shardingTmpName, String createTime) {
        this.queryCondition = queryCondition;
        this.shardingTmpName = shardingTmpName;
        this.createTime = createTime;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getShardingTmpName() {
        return shardingTmpName;
    }

    public void setShardingTmpName(String shardingTmpName) {
        this.shardingTmpName = shardingTmpName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
