package com.iekun.ef.model;

import java.io.Serializable;

/**
 * 工作模式
 */
public class DeviceStatusWorkMode implements Serializable {

    private String syncMode;
    private String authType;
    private String deviceCode;
    private String slaveBand;
    private String masterBand;
    private String permission;

    public String getSyncMode() {
        return syncMode;
    }

    public void setSyncMode(String syncMode) {
        this.syncMode = syncMode;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getSlaveBand() {
        return slaveBand;
    }

    public void setSlaveBand(String slaveBand) {
        this.slaveBand = slaveBand;
    }

    public String getMasterBand() {
        return masterBand;
    }

    public void setMasterBand(String masterBand) {
        this.masterBand = masterBand;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
