package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendAlignGps {


    String nowSysNo;
    String offset;
    String friendOffset;
    String padding;

    public String getNowSysNo() {
        return nowSysNo;
    }

    public void setNowSysNo(String nowSysNo) {
        this.nowSysNo = nowSysNo;
    }

    public String getFriendOffset() {
        return friendOffset;
    }

    public void setFriendOffset(String friendOffset) {
        this.friendOffset = friendOffset;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("00000000");
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.offset)), 8));
        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.friendOffset)), 8));
        sb.append("00000000000000000000000000000000");
        return sb.toString();
    }


}
