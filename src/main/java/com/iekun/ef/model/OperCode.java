package com.iekun.ef.model;

public class OperCode {
    /**
    * 操作对应id
    */
    private Long id;

    /**
    * 操作码
    */
    private String operCode;

    /**
    * 操作的名字
    */
    private String operName;

    /**
    * 操作类型
    */
    private String operType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }
}