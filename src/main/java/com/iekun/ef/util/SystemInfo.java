package com.iekun.ef.util;

/**
 * Created by feilong.cai on 2016/12/15.
 */


public class SystemInfo {

    public static final Integer VERSION_MAJOR = 3;
    public static final Integer VERSION_MINOR = 2;
    public static final Integer VSERSION_REVISION = 1;
    public static final Integer VERSION_BUILD = 5;

    public static final String PRODUCT_ID= "9.15.9.0.3.9";
    public static final String PRODUCT_EDITION = "efence2";


    public static String getVersion(){
        String version = VERSION_MAJOR + "." + VERSION_MINOR + "." + VSERSION_REVISION + "." + VERSION_BUILD;
        return version;
    }

}
