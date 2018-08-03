package com.iekun.ef.test.ui;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by jiangqi.yang  on 2016/12/2.
 */
public class UIOam {

    public static JSONObject deviceAction(  String deviceSN, String actionName, String actionParam ) {
        JSONObject jsonObject = null;
        if( actionName.equals("getRFParam")){
            jsonObject = getRFParam();
        } else if( actionName.equals("setRFParam") ) {
            jsonObject = setRFParam();
        } else  if( actionName.equals("getFuncParam")){
            jsonObject = getFuncParam();
        } else  if( actionName.equals("setFuncParam")){
            jsonObject = setRFParam();
        } else  if( actionName.equals("getAlignGPSParam")){
            jsonObject = getAlignGPSParam();
        } else  if( actionName.equals("setAlignGPSParam")){
            jsonObject = setRFParam();
        }  else  if( actionName.equals("getPAParam")){
            jsonObject =getPAParam();
        }  else  if( actionName.equals("setPAParam")) {
            jsonObject = setRFParam();
        }  else  if( actionName.equals("getControlRangeParam")){
            jsonObject =getControlRangeParam();
        }  else  if( actionName.equals("setControlRangeParam")){
            jsonObject = setRFParam();
        }  else  if( actionName.equals("getENBIdParam")){
            jsonObject = getENBIdParam();
        }  else  if( actionName.equals("setENBIdParam")){
            jsonObject = setRFParam();
        } else  if( actionName.equals("getDataSendCfgParam")){
            jsonObject = getDataSendCfgParam();
        }  else  if( actionName.equals("setDataSendCfgParam")){
            jsonObject = setRFParam();
        }  else  if( actionName.equals("queryVersion")){
            jsonObject = queryVersion();
        } else  if( actionName.equals("queryPAStatus")){
            jsonObject = queryPAStatus();
        } else  if( actionName.equals("queryPAInfo")) {
            jsonObject = queryPAInfo();
        } else  if( actionName.equals("switchRF")){
            jsonObject = setRFParam();
        } else  if( actionName.equals("reboot")){
                jsonObject = setRFParam();
        } else  if( actionName.equals("resetFactory")){
            jsonObject = setRFParam();
        } else {
            jsonObject = new JSONObject();
            jsonObject.put("status", false);
            jsonObject.put("message", "没有对应消息");
        }

        return jsonObject;
    }

    private static JSONObject getRFParam() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("rfenable",  1 );
        paramObj.put("fastconfigearfcn", 1 );
        paramObj.put("framestrucuretype", 0);
        paramObj.put("eutraband", 39);
        paramObj.put("dlearfcn", 38251);
        paramObj.put("ulearfcn", 38251 );
        paramObj.put("subframeassinment", 2);
        paramObj.put("specialsubframepatterns", 7);
        paramObj.put("dlbandwidth", 50);
        paramObj.put("ulbandwidth", 50);
        paramObj.put("rfchoice" , 7 );
        paramObj.put("tx1powerattenuation" , 0);
        paramObj.put("tx2powerattenuation", 7000);

        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "getRFParam");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }

    private static JSONObject setRFParam(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
//        jsonObject.put("status", false);
//        jsonObject.put("message", "失败了。。。。。");
        return jsonObject;
    }

    private static JSONObject getFuncParam() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("paramcc",  "461" );
        paramObj.put("paramnc",  "01" );
        paramObj.put("parapcino",  1 );
        paramObj.put("paraperi",  2 );
        paramObj.put("controlrange",  1 );
        paramObj.put("inandouttype",  2 );
        paramObj.put("arfcn",  0 );
        paramObj.put("boolstart",  1 );

        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "getFuncParam");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }

    private static JSONObject getAlignGPSParam() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("offset",  0 );

        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "getAlignGPSParam");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }

    private static JSONObject getPAParam() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("powerattenuation",  0 );

        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "getPAParam");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }

    private static JSONObject getControlRangeParam() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("open",  1 );
        paramObj.put("lev",  -55 );

        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "getControlRangeParam");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }


    private static JSONObject getENBIdParam() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("identity",  1 );

        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "getENBIdParam");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }

    private static JSONObject getDataSendCfgParam() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("realtimesend",  0 );
        paramObj.put("interalmin",  1 );
        paramObj.put("uecountsend",  50 );


        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "getDataSendCfgParam");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }


    private static JSONObject queryVersion() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("license",  0 );
        paramObj.put("fpgaversion", "1.2.0" );
        paramObj.put("bbuversion", "1.2.0");
        paramObj.put("softwareversion", "1.2.0");


        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "queryVersion");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }


    private static JSONObject queryPAStatus() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("valid",  1 );
        paramObj.put("warnpa",  0 );
        paramObj.put("warnstandingwaveratio",  0 );
        paramObj.put("warntemp",  0 );
        paramObj.put("warnpower",  0 );
        paramObj.put("onoffpa",  1 );
        paramObj.put("inversepower",  10 );
        paramObj.put("temp",  40 );
        paramObj.put("alcvalue",  10 );
        paramObj.put("standingwaveratio",  50 );
        paramObj.put("currattpa",  5 );
        paramObj.put("forwardpower",  0 );
        paramObj.put("forwardpower2",  0 );


        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "queryPAStatus");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }

    private static JSONObject queryPAInfo() {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();
        JSONObject paramObj = new JSONObject();

        //全部参数名小写，去掉下划线
        paramObj.put("pacount",  1 );
        paramObj.put("valid",  1 );
        paramObj.put("power",  20 );
        paramObj.put("panum",  "25465212" );
        paramObj.put("band",  39 );
        paramObj.put("en485",  1 );
        paramObj.put("addr485",  "12" );
        paramObj.put("provider",  "xxxx" );
        paramObj.put("sn",  "0123456" );
        paramObj.put("factory",  "12523ddd" );

        dataObject.put("deviceSN", " D00000000098");
        dataObject.put("actionName", "queryPAInfo");
        dataObject.put("parameter", paramObj);

        jsonObject.put("status", true);
        jsonObject.put("message", "成功");
        jsonObject.put("data", dataObject );

        return jsonObject;
    }

}
