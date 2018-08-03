package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendServerCnf {
    String serverAddrType;
    String serverAddr;
    String serverPort;
    String serverTryConnectionTime;
    String secondServerEnable;
    String secondServerAddrType;
    String secondServerAddr;
    String secondServerPort;
    String secondServerConnectionTime;

    public String getServerAddrType() {
        return serverAddrType;
    }

    public void setServerAddrType(String serverAddrType) {
        this.serverAddrType = serverAddrType;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerTryConnectionTime() {
        return serverTryConnectionTime;
    }

    public void setServerTryConnectionTime(String serverTryConnectionTime) {
        this.serverTryConnectionTime = serverTryConnectionTime;
    }

    public String getSecondServerEnable() {
        return secondServerEnable;
    }

    public void setSecondServerEnable(String secondServerEnable) {
        this.secondServerEnable = secondServerEnable;
    }

    public String getSecondServerAddrType() {
        return secondServerAddrType;
    }

    public void setSecondServerAddrType(String secondServerAddrType) {
        this.secondServerAddrType = secondServerAddrType;
    }

    public String getSecondServerAddr() {
        return secondServerAddr;
    }

    public void setSecondServerAddr(String secondServerAddr) {
        this.secondServerAddr = secondServerAddr;
    }

    public String getSecondServerPort() {
        return secondServerPort;
    }

    public void setSecondServerPort(String secondServerPort) {
        this.secondServerPort = secondServerPort;
    }

    public String getSecondServerConnectionTime() {
        return secondServerConnectionTime;
    }

    public void setSecondServerConnectionTime(String secondServerConnectionTime) {
        this.secondServerConnectionTime = secondServerConnectionTime;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        //
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.serverAddrType)), 2));
        sb.append("00");
        sb.append(ConvertTools.fillZeroToEnd(ConvertTools.bytesToHexString(this.serverAddr.getBytes()), 64));
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.serverPort)), 4));
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.serverTryConnectionTime)), 4));
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.secondServerEnable)), 2));
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.secondServerAddrType)), 2));
        sb.append(ConvertTools.fillZeroToEnd(ConvertTools.bytesToHexString(this.secondServerAddr.getBytes()), 64));
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.secondServerPort)), 4));
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.secondServerConnectionTime)), 4));
        sb.append("0000000000000000");
        return sb.toString();
    }


}
