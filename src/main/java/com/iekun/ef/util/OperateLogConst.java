package com.iekun.ef.util;

import java.util.HashMap;
import java.util.Map;

public class OperateLogConst
{
  public static final Map<String, String> OperateLogConstMapCode2Name = new HashMap<String, String>();
  public static final String MODEL_CODE_LOGIN = "0001";
  public static final String MODEL_CODE_IMSISEARCH = "0101";
  public static final String MODEL_CODE_IMSIRECORD = "0111";
  public static final String MODEL_CODE_IMSIRECORD_EXPORT = "0112";
  public static final String MODEL_CODE_IMSIRECORD_MAP = "0113";
  public static final String MODEL_CODE_TARGETMGR = "0201";
  public static final String MODEL_CODE_TARGETMGR_ADD = "0202";
  public static final String MODEL_CODE_TARGETMGR_MOD = "0203";
  public static final String MODEL_CODE_TARGETMGR_DEL = "0204";
  public static final String MODEL_CODE_HITSEARCH = "0211";
  public static final String MODEL_CODE_ALARMTELMGR = "0221";
  public static final String MODEL_CODE_ALARMTELMGR_ADD = "0222";
  public static final String MODEL_CODE_ALARMTELMGR_MOD = "0223";
  public static final String MODEL_CODE_ALARMTELMGR_DEL = "0224";
  public static final String MODEL_CODE_FLOWSTAT = "0301";
  public static final String MODEL_CODE_TRACKQUERY = "0311";
  public static final String MODEL_CODE_DEVICEMGR = "0401";
  public static final String MODEL_CODE_DEVICEMGR_ADD = "0402";
  public static final String MODEL_CODE_DEVICEMGR_MOD = "0403";
  public static final String MODEL_CODE_DEVICEMGR_DEL = "0404";
  public static final String MODEL_CODE_EVENTMGR = "0411";
  public static final String MODEL_CODE_CMDTOOLS = "0421";
  public static final String MODEL_CODE_ACCOUNTMGR = "0431";
  public static final String MODEL_CODE_ACCOUNTMGR_ADD = "0432";
  public static final String MODEL_CODE_ACCOUNTMGR_MOD = "0433";
  public static final String MODEL_CODE_ACCOUNTMGR_DEL = "0434";
  public static final String MODEL_CODE_DEVICECTRL_PRASET = "0901";
  public static final String MODEL_CODE_DEVICECTRL_FLOWSTAT = "0902";
  public static final String MODEL_CODE_DEVICECTRL_RESET = "0903";
  public static final String MODEL_CODE_DEVICECTRL_SHUTDOWN = "0904";
  
  static
  {
    OperateLogConstMapCode2Name.put("0001", "系统登录");
    
    OperateLogConstMapCode2Name.put("0101", "实时监控");
    OperateLogConstMapCode2Name.put("0111", "实时查询");
    OperateLogConstMapCode2Name.put("0112", "实时查询:数据导出");
    OperateLogConstMapCode2Name.put("0113", "历史轨迹查询");
    
    OperateLogConstMapCode2Name.put("0201", "目标管理:查询");
    OperateLogConstMapCode2Name.put("0202", "目标管理：增加");
    OperateLogConstMapCode2Name.put("0203", "目标管理：修改");
    OperateLogConstMapCode2Name.put("0204", "目标管理：删除");
    
    OperateLogConstMapCode2Name.put("0211", "命中数据查询");
    OperateLogConstMapCode2Name.put("0221", "告警设置");
    OperateLogConstMapCode2Name.put("0222", "告警设置：增加");
    OperateLogConstMapCode2Name.put("0223", "告警设置：修改");
    OperateLogConstMapCode2Name.put("0224", "告警设置：删除");
    
    OperateLogConstMapCode2Name.put("0301", "流量统计");
    OperateLogConstMapCode2Name.put("0311", "历史用户分析");
    
    OperateLogConstMapCode2Name.put("0401", "设备管理");
    OperateLogConstMapCode2Name.put("0402", "设备管理：增加");
    OperateLogConstMapCode2Name.put("0403", "设备管理：修改");
    OperateLogConstMapCode2Name.put("0404", "设备管理：删除");
    OperateLogConstMapCode2Name.put("0411", "日志管理:系统日志");
    OperateLogConstMapCode2Name.put("0421", "命令行");
    OperateLogConstMapCode2Name.put("0431", "账号管理");
    OperateLogConstMapCode2Name.put("0432", "账号管理：增加");
    OperateLogConstMapCode2Name.put("0433", "账号管理：修改");
    OperateLogConstMapCode2Name.put("0434", "账号管理：删除");
    
    OperateLogConstMapCode2Name.put("0901", "设备控制：参数配置");
    OperateLogConstMapCode2Name.put("0902", "设备控制：流量统计");
    OperateLogConstMapCode2Name.put("0903", "设备控制：设备重启");
    OperateLogConstMapCode2Name.put("0904", "设备控制：关闭设备");
  }
}
