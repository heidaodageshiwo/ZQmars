package com.iekun.ef.jms.vo.send;

import com.iekun.ef.util.ConvertTools;

public class SendSrxParam {
    private String dlEarfcn;

    public String getDlEarfcn() {
        return dlEarfcn;
    }

    public void setDlEarfcn(String dlEarfcn) {
        this.dlEarfcn = dlEarfcn;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(ConvertTools.fillZero(Integer.toHexString(Integer.valueOf(this.dlEarfcn)), 4));
        sb.append("0000");
        sb.append("00000000");
        sb.append("00000000");
        sb.append("00000000");

        return sb.toString();
    }
}
