package com.iekun.ef.util;

public class M16Tools {
    /**
     * 字符串转换为整形
     * @param str
     * @return
     */
    public static Integer stringToNumber(String str) {
        if(str != null) {
            try {
                return Integer.valueOf(str.trim());
            } catch (Exception e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
