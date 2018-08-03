package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class DownloadLog {

    private String protocol;
    private String serverIp;
    private String port;
    private String username;
    private String password;

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("00");
        //type
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.protocol)), 2));
        sb.append("0000");
        sb.append("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        sb.append("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        sb.append("0000000000000000000000000000000000000000000000000000000000000000");
        //ip
        sb.append((ConvertTools.fillZeroToEnd(ConvertTools.bytesToHexString(this.serverIp.getBytes()), 32)));
        //port
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.port)), 8));
        //username
        sb.append((ConvertTools.fillZeroToEnd(ConvertTools.bytesToHexString(this.username.getBytes()), 64)));
        //password
        sb.append((ConvertTools.fillZeroToEnd(ConvertTools.bytesToHexString(this.password.getBytes()), 64)));
        sb.append("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        return sb.toString();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
