package com.iekun.ef.model;

import java.util.Map;

/**
 * 设备数节点
 * @author zxbing
 * @version 2017/12/3
 */
public class DeviceTreeNode {

    private static final long serialVersionUID = -766759489880382042L;

    private String id;
    private String pId;
    private String name;
    private int level; //层级
    private boolean open;  //是否默认打开
    private boolean nocheck; //是否是否隐藏 checkbox / radio
    private boolean checked; //是否默认选上
    private Map<String, Object> font; //自定义字体
    private String icon; //自定义图表 若为父节点，则需分别指定iconOpen, iconClose
    private String iconOpen;
    private String iconClose;
    private String iconSkin;

    private String sn; //设备sn号
    private String status; //设备状态

    public DeviceTreeNode(String id, String pId, String name, int level, boolean open){
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.level = level;
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Map<String, Object> getFont() {
        return font;
    }

    public void setFont(Map<String, Object> font) {
        this.font = font;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getIconClose() {
        return iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
