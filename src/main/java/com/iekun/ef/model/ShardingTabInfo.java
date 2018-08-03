package com.iekun.ef.model;

/**
 * Created by sword.yu on 2017/1/16.
 */
public class ShardingTabInfo {

    /**
     * 分片数据表名
     */
    private String tableName;

    /**
     * 创建分表日期
     */
    private String createDate;

    public ShardingTabInfo() {
    }

    public ShardingTabInfo(String tableName, String createDate) {
        this.tableName = tableName;
        this.createDate = createDate;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
