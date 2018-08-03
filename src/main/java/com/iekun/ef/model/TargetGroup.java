package com.iekun.ef.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TargetGroup {

    /**
     * 分组id
     */
    private String id;
    /**
     *分组名称
     */
    private String name;
    /**
     *创建人id
     */
    private long creator_id;
    /**
     *创建人姓名
     */
    private String creator_name;
    /**
     *分组id对应的站点
     */
    private String site;
    /**
     *预警接收人姓名电话邮箱
     */
    private String warning;
    /**
     *创建时间
     */
    private String create_time;
    /**
     *到期时间
     */
    private String overdue_time;

    /**
     *备注
     */
    private String remark;

    public TargetGroup(String id, String name, long creator_id, String creator_name, String site, String warning, String create_time, String overdue_time, String remark, int count) {
        this.id = id;
        this.name = name;
        this.creator_id = creator_id;
        this.creator_name = creator_name;
        this.site = site;
        this.warning = warning;
        this.create_time = create_time;
        this.overdue_time = overdue_time;
        this.remark = remark;
        this.count = count;
    }

    public int getCount() {

        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 每个分组人数
     * */
    private int count;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCreator_id() {
        return creator_id;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public String getSite() {
        return site;
    }

    public String getWarning() {
        return warning;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getOverdue_time() {
        return overdue_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(String id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator_id(long creator_id) {
        this.creator_id = creator_id;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setOverdue_time(String overdue_time) {
        this.overdue_time = overdue_time;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public TargetGroup(){}

    public TargetGroup(String id, String name, long creator_id, String creator_name, String site, String warning, String create_time, String overdue_time, String remark) {
        this.id = id;
        this.name = name;
        this.creator_id = creator_id;
        this.creator_name = creator_name;
        this.site = site;
        this.warning = warning;
        this.create_time = create_time;
        this.overdue_time = overdue_time;
        this.remark = remark;
    }

}
