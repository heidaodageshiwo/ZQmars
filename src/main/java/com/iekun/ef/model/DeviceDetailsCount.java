package com.iekun.ef.model;

public class DeviceDetailsCount {
    /**
    * 供应商
    */
    private String operator;
    /**
    * 统计总数
    */
    private String sum;

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getOperator() {

        return operator;
    }

    public String getSum() {
        return sum;
    }
}