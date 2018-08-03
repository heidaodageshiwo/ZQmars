package com.iekun.ef.model;

public class SysParam {
    /**
    * 系统参数ID
    */
    private Long id;

    /**
    * 系统参数KEY值
    */
    private String key;

    /**
    * 系统参数内容
    */
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}