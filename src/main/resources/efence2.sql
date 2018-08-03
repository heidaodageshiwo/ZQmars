/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2016-12-01 18:29:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_area_code
-- ----------------------------
DROP TABLE IF EXISTS `t_area_code`;
CREATE TABLE `t_area_code` (
  `ID` bigint(20) unsigned NOT NULL,
  `NAME` varchar(40) NOT NULL COMMENT '地区名称',
  `PARENTID` bigint(20) NOT NULL COMMENT '父节点',
  `SHORT_NAME` varchar(40) DEFAULT NULL COMMENT '地区简称',
  `LEVEL` tinyint(3) DEFAULT NULL COMMENT '级别',
  `CITY_CODE` varchar(10) DEFAULT NULL COMMENT '区号',
  `ZIP_CODE` varchar(10) DEFAULT NULL COMMENT '邮编',
  `MERGER_NAME` varchar(100) DEFAULT NULL COMMENT '地区全称',
  `LNG` varchar(20) DEFAULT NULL COMMENT '经度',
  `LAT` varchar(20) DEFAULT NULL COMMENT '纬度',
  `PINYIN` varchar(40) DEFAULT NULL COMMENT '拼音',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='行政区划表';

-- ----------------------------

-- ----------------------------
-- Table structure for t_area_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_area_rule`;
CREATE TABLE `t_area_rule` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '归属地预警列表ID',
  `NAME` varchar(40) DEFAULT NULL COMMENT '规则名称',
  `SOURCE_CITY_CODE` varchar(10) NOT NULL COMMENT '源归属区号',
  `SOURCE_CITY_NAME` varchar(40) NOT NULL,
  `DEST_CITY_CODE` varchar(10) DEFAULT NULL COMMENT '目的地区号',
  `POLICY` tinyint(2) unsigned DEFAULT '0' COMMENT '(保留字段)规则：0- include;1- exclude;2-',
  `POLICY_PRIORITY` tinyint(2) unsigned DEFAULT '0' COMMENT '(保留字段)',
  `CREATOR_ID` bigint(20) NOT NULL COMMENT '创建人ID',
  `CREATE_TIME` varchar(14) NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL,
  `DELETE_FLAG` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除标记: 0-  正常，  1-  删除',
  `RECEIVER` text,
  `REMARK` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1  DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='区域监测规则表';

-- ----------------------------
-- Table structure for t_device
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '子设备ID',
  `SITE_ID` bigint(20) DEFAULT NULL COMMENT '站点ID',
  `SN` varchar(40) NOT NULL COMMENT '设备序列号',
  `NAME` varchar(40) DEFAULT NULL COMMENT '设备名称',
  `TYPE` tinyint(2) NOT NULL COMMENT '设备类型：01- cdma；02- gsm；03- wcdma；04- td；05- tdd；06- fdd',
  `BAND` varchar(10) DEFAULT NULL COMMENT '设备工作频段',
  `OPERATOR` tinyint(20) DEFAULT NULL COMMENT '运营商：01- 移动 02- 电信 03- 联通',
  `MANUFACTURER` varchar(100) DEFAULT NULL COMMENT '生产厂家',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '说明',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标记: 0-  正常，  1-  删除',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='设备表';

-- ----------------------------
-- Table structure for t_device_alarm
-- ----------------------------
DROP TABLE IF EXISTS `t_device_alarm`;
CREATE TABLE `t_device_alarm` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ALARM_TYPE` varchar(10) NOT NULL COMMENT '告警类型',
  `ALARM_CODE` varchar(10) NOT NULL COMMENT '告警码',
  `ALARM_INFO` varchar(120) NOT NULL COMMENT '告警信息',
  `ALARM_TIME` varchar(14) NOT NULL COMMENT '告警时间',
  `ALARM_LEVEL` varchar(10) NOT NULL COMMENT '告警级别',
  `SITE_ID` bigint(20) NOT NULL COMMENT '站点ID',
  `DEVICE_SN` varchar(40) NOT NULL COMMENT '设备编号',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='设备告警表';

-- ----------------------------
-- Table structure for t_device_license
-- ----------------------------
DROP TABLE IF EXISTS `t_device_license`;
CREATE TABLE `t_device_license` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设备授权ID',
  `SITE_NAME` varchar(40) DEFAULT NULL COMMENT '站点名称',
  `DEVICE_SN` varchar(40) DEFAULT NULL COMMENT '设备序列号',
  `UPLOAD_USER` varchar(40) DEFAULT NULL COMMENT '上传用户的用户名',
  `UPLOAD_TIME` varchar(14) DEFAULT NULL COMMENT '上传的时间',
  `SCHEDULE_TIME` varchar(14) DEFAULT NULL COMMENT '计划下载的时间（暂时不用）',
  `SUCCESS_TIME` varchar(14) DEFAULT NULL,
  `FILE_URL` varchar(120) DEFAULT NULL COMMENT '保存路径',
  `RETRTIES` tinyint(3) unsigned DEFAULT NULL COMMENT '尝试次数（暂时不用）',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `STATUS_FLAG` tinyint(3) unsigned DEFAULT NULL COMMENT '授权状态标志:00-  未授权;01-  已授权',
  `BEXPIRE_TIME` varchar(14) DEFAULT NULL,
  `AEXPIRE_TIME` varchar(14) DEFAULT NULL,
  `CHECK_SUM` varchar(128) DEFAULT NULL,
  `RESULT_DISCRIPTION` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='设备授权表';

-- ----------------------------
-- Table structure for t_email_tpl
-- ----------------------------
DROP TABLE IF EXISTS `t_email_tpl`;
CREATE TABLE `t_email_tpl` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `EVENT_TYPE` tinyint(4) DEFAULT NULL COMMENT '触发事件类型',
  `ACTIVE` tinyint(4) DEFAULT '0' COMMENT '是否激活。0-未激活，1-已激活',
  `EMAIL_SUBJECT` varchar(255) DEFAULT NULL COMMENT '邮件主题',
  `CONTENT_TPL` text COMMENT '邮件模板内容',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `DELETE_FLAG` tinyint(4) DEFAULT '0' COMMENT '删除标记: 0-  正常，  1-  删除',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='邮件模板';

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  `TRIGGER_TYPE` tinyint(3) unsigned NOT NULL COMMENT '触发类型:0- 设备告警;1- 分析报告;2-发现目标',
  `CONTENT` text NOT NULL COMMENT '站内通知内容',
  `IS_READ` tinyint(1) NOT NULL COMMENT '是否已读',
  `DEST_URL` varchar(255) NOT NULL COMMENT '站内通知详情的跳转URL',
  `CREATE_TIME` varchar(14) DEFAULT NULL,
  `UPDATE_TIME` varchar(14) DEFAULT NULL,
  `DELETE_FLAG` tinyint(4) DEFAULT '0' COMMENT '删除标记: 0-  正常，  1-  删除',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='站内通知表';

-- ----------------------------
-- Table structure for t_oam_param
-- ----------------------------
DROP TABLE IF EXISTS `t_oam_param`;
CREATE TABLE `t_oam_param` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(40) DEFAULT NULL COMMENT '参数类型',
  `CODE` varchar(10) DEFAULT NULL COMMENT '参数代码',
  `NAME` varchar(40) DEFAULT NULL COMMENT '参数名称',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `DELETE_FLAG` tinyint(1) DEFAULT NULL COMMENT '删除标志：0-未删除；1-已删除',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '最后更新时间',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='网管参数表';

-- ----------------------------
-- Table structure for t_oper_code
-- ----------------------------
DROP TABLE IF EXISTS `t_oper_code`;
CREATE TABLE `t_oper_code` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '操作对应id',
  `OPER_CODE` varchar(80) NOT NULL COMMENT '操作码',
  `OPER_NAME` varchar(40) NOT NULL COMMENT '操作的名字',
  `OPER_TYPE` varchar(40) NOT NULL COMMENT '操作类型',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='操作码表';

-- ----------------------------
-- Table structure for t_outbox
-- ----------------------------
DROP TABLE IF EXISTS `t_outbox`;
CREATE TABLE `t_outbox` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `NOTIFY_NAME` varchar(40) NOT NULL COMMENT '通知人员名称',
  `NOTIFY_PHONE` varchar(20) DEFAULT NULL COMMENT '通知人员手机号',
  `NOTIFY_EMAIL` varchar(100) DEFAULT NULL COMMENT '通知人员的email',
  `EMAIL_SUBJECT` varchar(255) DEFAULT NULL COMMENT '邮件主题',
  `NOTIFY_TYPE` tinyint(2) NOT NULL COMMENT '通知类型:0- sms; 1- email',
  `EVENT_TYPE` tinyint(2) NOT NULL COMMENT '触发事件类型：0-  黑名单;1-  特殊区域;2-  设备告警；3-系统事件',
  `CONTENT` text NOT NULL COMMENT '内容',
  `STATUS` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态:0- 待处理;1- 成功;2- 失败;',
  `CREATE_TIME` varchar(14) NOT NULL COMMENT '创建时间',
  `LAST_SEND_TIME` varchar(14) DEFAULT NULL COMMENT '发送时间',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标记: 0-  正常，  1-  删除',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='发件箱表';

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `TYPE` tinyint(2) DEFAULT NULL COMMENT '资源类型  : 1 - menu资源；2 - 按钮资源',
  `CODE` varchar(20) DEFAULT NULL COMMENT '资源编号',
  `NAME` varchar(20) DEFAULT NULL COMMENT '资源名称',
  `URL` varchar(255) DEFAULT NULL COMMENT '资源地址',
  `ICON` varchar(40) DEFAULT NULL COMMENT '图标资源名字',
  `PARENT_ID` bigint(10) DEFAULT NULL COMMENT '上级ID',
  `ORDER_NUMBER` int(10) DEFAULT NULL COMMENT '资源展示序号',
  `DETAIL` varchar(40) DEFAULT NULL COMMENT '显示图标',
  `DELETE_FLAG` tinyint(1) DEFAULT NULL COMMENT '删除标志：0-未删除；1-已删除',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '最后更新时间',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '说明',
  `ATTRIBUTE` tinyint(2) unsigned DEFAULT '0' COMMENT '资源属性标志位:0- R;1- R/W; 2-Hide',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源表';

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `NAME` varchar(40) DEFAULT NULL COMMENT '角色名称',
  `DEFAULT_FLAG` tinyint(1) DEFAULT NULL COMMENT '是否为默认：0-否；1-是',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '备注',
  `DELETE_FLAG` tinyint(1) DEFAULT NULL COMMENT '删除标志：0-未删除；1-已删除',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '最后更新时间',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';


-- ----------------------------
-- Table structure for t_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID值',
  `ROLE_ID` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `RESOURCE_ID` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='角色资源表';

-- ----------------------------
-- Table structure for t_self_area_code
-- ----------------------------
DROP TABLE IF EXISTS `t_self_area_code`;
CREATE TABLE `t_self_area_code` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `PROVINCE_CODE` varchar(10) DEFAULT NULL COMMENT '省编码',
  `PROVINCE_NAME` varchar(40) DEFAULT NULL COMMENT '省名：去掉“省”如山东省则写为山东',
  `CITY_CODE` varchar(10) DEFAULT NULL COMMENT '市编码',
  `CITY_NAME` varchar(40) DEFAULT NULL COMMENT '城市',
  `LEVEL` tinyint(4) DEFAULT NULL COMMENT '行政区划级别：1:省； 2：市',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sharding_query_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sharding_query_info`;
CREATE TABLE `t_sharding_query_info` (
  `QUERY_CONDITION` varchar(255) NOT NULL,
  `SHARDING_TMP_NAME` varchar(255) NOT NULL,
  `CREATE_TIME` varchar(32) NOT NULL,
  PRIMARY KEY (`QUERY_CONDITION`,`SHARDING_TMP_NAME`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='分表查询信息表';

-- ----------------------------
-- Table structure for t_sharding_tab_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sharding_tab_info`;
CREATE TABLE `t_sharding_tab_info` (
  `SHARDING_TAB_NAME` varchar(255) NOT NULL COMMENT '表名',
  `SHARDING_TAB_DATE` varchar(255) NOT NULL COMMENT '表对应日期',
  PRIMARY KEY (`SHARDING_TAB_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='分片表信息';

-- ----------------------------
-- Table structure for t_site
-- ----------------------------
DROP TABLE IF EXISTS `t_site`;
CREATE TABLE `t_site` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `SN` varchar(40) DEFAULT NULL COMMENT '站点编号',
  `NAME` varchar(40) NOT NULL COMMENT '站点名称',
  `LONGITUDE` varchar(20) DEFAULT NULL COMMENT '经度',
  `LATITUDE` varchar(20) DEFAULT NULL COMMENT '纬度',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '站点详细地址',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '说明',
  `DELETE_FLAG` tinyint(1) DEFAULT '0' COMMENT '删除标记: 0-  正常，  1-  删除',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `CITY_CODE` varchar(10) DEFAULT NULL COMMENT '区号',
  `ZIP_CODE` varchar(10) DEFAULT NULL COMMENT '地区编码（邮编）',
  `PROVINCE_ID` bigint(20) DEFAULT NULL,
  `CITY_ID` bigint(20) DEFAULT NULL,
  `TOWN_ID` bigint(20) DEFAULT NULL,
  `PROVINCE_NAME` varchar(40) DEFAULT NULL,
  `CITY_NAME` varchar(40) DEFAULT NULL,
  `TOWN_NAME` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='站点表';

-- ----------------------------
-- Table structure for t_site_user
-- ----------------------------
DROP TABLE IF EXISTS `t_site_user`;
CREATE TABLE `t_site_user` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '设备、结构关联ID',
  `SITE_ID` bigint(20) unsigned NOT NULL COMMENT '设备ID',
  `USER_ID` bigint(20) unsigned NOT NULL COMMENT '拓扑图结构节点ID',
  `DELETE_FLAG` tinyint(1) NOT NULL COMMENT '删除标记: 0-  正常，  1-  删除',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='站点用户分配表';

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '操作ID',
  `TYPE` tinyint(2) DEFAULT NULL COMMENT '操作类型：00- 设备状态日志;01- 用户登录登出日志;02- 设备操作日志;03- 通知日志;04- 系统日志',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `OPER_CODE` varchar(80) DEFAULT NULL COMMENT '操作码',
  `OPER_NAME` varchar(40) DEFAULT NULL COMMENT '操作名',
  `OPER_TIME` varchar(14) DEFAULT NULL COMMENT '操作时间',
  `OPER_STATUS` tinyint(2) DEFAULT NULL COMMENT '操作结果：-1-处理中；0-成功；1-失败',
  `SITE_NAME` varchar(40) DEFAULT NULL COMMENT '站点名称',
  `DEVICE_SN` varchar(40) DEFAULT NULL COMMENT '子设备编号',
  `SESSION_ID` bigint(20) DEFAULT NULL COMMENT '会话ID',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统操作日志';

-- ----------------------------
-- Table structure for t_sys_param
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_param`;
CREATE TABLE `t_sys_param` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统参数ID',
  `PARA_KEY` varchar(40) NOT NULL COMMENT '系统参数KEY值',
  `VALUE` varchar(255) NOT NULL COMMENT '系统参数内容',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统参数表';

-- ----------------------------
-- Table structure for t_target_alarm
-- ----------------------------
DROP TABLE IF EXISTS `t_target_alarm`;
CREATE TABLE `t_target_alarm` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ue信息ID',
  `SITE_NAME` varchar(40) DEFAULT NULL COMMENT '站点名称',
  `DEVICE_SN` varchar(40) DEFAULT NULL COMMENT '设备序列号',
  `SITE_SN` varchar(40) DEFAULT NULL,
  `IMSI` varchar(16) DEFAULT NULL COMMENT 'IMSI',
  `IMEI` varchar(16) DEFAULT NULL COMMENT 'IMEI',
  `STMSI` varchar(20) DEFAULT NULL COMMENT 'TMSI',
  `MAC` varchar(20) DEFAULT NULL COMMENT 'MAC地址',
  `LATYPE` varchar(4) DEFAULT NULL COMMENT '位置更新类型',
  `INDICATION` tinyint(2) DEFAULT NULL COMMENT '是否目标手机：0 --- NORMAL；1--- 黑名单手机；2--- 特殊归属地手机',
  `REALTIME` tinyint(1) DEFAULT NULL COMMENT '是否实时上报:00-  不实时上报;01-  实时上报',
  `CAPTURE_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '基站侧记录的上号时间',
  `RARTA` bigint(20) NOT NULL,
  `CREATE_TIME` varchar(14) NOT NULL COMMENT '创建时间',
  `CITY_NAME` varchar(100) DEFAULT NULL COMMENT '归属地地名（省+市）',
  `CITY_CODE` varchar(10) DEFAULT NULL,
  `BAND` varchar(20) DEFAULT NULL COMMENT '频段',
  `OPERATOR` varchar(20) DEFAULT NULL COMMENT '运营商',
  `IS_READ` tinyint(1) DEFAULT NULL COMMENT '预警是否已读字段： 0 - 未读 ；1- 已读',
  PRIMARY KEY (`ID`,`CREATE_TIME`),
  KEY `DEVICE_SN` (`SITE_NAME`),
  KEY `SUBDEVICE_SN` (`DEVICE_SN`),
  KEY `IMSI` (`IMSI`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='终端目标告警表';

-- ----------------------------
-- Table structure for t_target_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_target_rule`;
CREATE TABLE `t_target_rule` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `NAME` varchar(40) DEFAULT NULL COMMENT '目标名称',
  `IMSI` varchar(16) DEFAULT NULL COMMENT 'IMSI',
  `IMEI` varchar(16) DEFAULT NULL COMMENT 'IMEI',
  `MAC` varchar(20) DEFAULT NULL COMMENT 'MAC地址',
  `TARGET_TYPE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '目标类型：0-黑名单；1-白名单',
  `RECEIVER` text NOT NULL COMMENT 'RECIVER 格式（json格式组织）：',
  `SMS_TIME` varchar(22) DEFAULT NULL,
  `LATEST_DEVICE_SN` varchar(40) DEFAULT NULL,
  `REMARK` varchar(200) DEFAULT NULL COMMENT '说明',
  `DELETE_FLAG` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记: 0-  正常，  1-  删除',
  `CREATE_TIME` varchar(14) NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `CREATOR_ID` bigint(20) NOT NULL COMMENT '创建人ID',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='人员监测规则表';

-- ----------------------------
-- Table structure for t_task
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TYPE` tinyint(4) NOT NULL COMMENT '任务类型',
  `NAME` varchar(255) NOT NULL COMMENT '任务名称',
  `PARAMETER` text COMMENT '任务参数',
  `RESULT` text COMMENT '任务结果',
  `RESULT_EXT` varchar(255) DEFAULT NULL,
  `EXPORT_PATH` varchar(255) DEFAULT NULL,
  `IS_FINISH` tinyint(4) DEFAULT '0' COMMENT '是否完成。0-未完成，1-已完成',
  `TRY_COUNT` int(11) DEFAULT '0' COMMENT '尝试次数',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `FINISH_TIME` varchar(14) DEFAULT NULL COMMENT '完成时间',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `DELETE_FLAG` tinyint(4) DEFAULT '0' COMMENT '删除标记: 0-  正常，  1-  删除',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务表';

-- ----------------------------
-- Table structure for t_ue_info
-- ----------------------------
DROP TABLE IF EXISTS `t_ue_info`;
CREATE TABLE `t_ue_info` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ue信息ID',
  `SITE_NAME` varchar(40) DEFAULT NULL COMMENT '站点名称',
  `DEVICE_SN` varchar(40) DEFAULT NULL COMMENT '子设备序列号',
  `SITE_SN` varchar(40) DEFAULT NULL,
  `IMSI` varchar(16) DEFAULT NULL COMMENT 'IMSI',
  `IMEI` varchar(16) DEFAULT NULL COMMENT 'IMEI',
  `STMSI` varchar(20) DEFAULT NULL COMMENT 'TMSI',
  `MAC` varchar(20) DEFAULT NULL COMMENT 'MAC地址',
  `LATYPE` varchar(4) DEFAULT NULL COMMENT '位置更新类型',
  `INDICATION` tinyint(2) DEFAULT '0' COMMENT '是否目标手机：0 --- NORMAL；1--- 黑名单手机；2--- 特殊归属地手机',
  `REALTIME` tinyint(1) DEFAULT '0' COMMENT '是否实时上报:00-  不实时上报;01-  实时上报',
  `CAPTURE_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '基站侧记录的上号时间',
  `RARTA` bigint(20) DEFAULT NULL,
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `CITY_NAME` varchar(100) DEFAULT NULL COMMENT '归属地地名',
  `CITY_CODE` varchar(10) DEFAULT NULL,
  `BAND` varchar(20) DEFAULT NULL COMMENT '运营商',
  `OPERATOR` varchar(20) DEFAULT NULL COMMENT '运营商',
  PRIMARY KEY (`ID`),
  KEY `DEVICE_SN` (`SITE_NAME`),
  KEY `SUBDEVICE_SN` (`DEVICE_SN`),
  KEY `IMSI` (`IMSI`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='终端信息采集表';

-- ----------------------------
-- Table structure for t_upgrade
-- ----------------------------
DROP TABLE IF EXISTS `t_upgrade`;
CREATE TABLE `t_upgrade` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设备版本升级ID',
  `VERSION_ID` bigint(20) DEFAULT NULL COMMENT '设备版本ID',
  `SITE_NAME` varchar(40) NOT NULL COMMENT '站点名称',
  `DEVICE_SN` varchar(40) NOT NULL COMMENT '设备序列号',
  `OLD_VERSION` varchar(100) DEFAULT NULL COMMENT '升级前设备总版本',
  `OLD_FPGA_VERSION` varchar(40) DEFAULT NULL COMMENT '升级前设备FPGA版本',
  `OLD_BBU_VERSION` varchar(40) DEFAULT NULL COMMENT '升级前设备BBU版本',
  `OLD_SW_VERSION` varchar(100) DEFAULT NULL COMMENT '升级前设备SW版本',
  `SCHEDULE_TIME` varchar(14) DEFAULT NULL COMMENT '计划升级时间点',
  `SUCCESS_TIME` varchar(14) DEFAULT NULL COMMENT '升级成功时间点',
  `RETRTIES` tinyint(2) DEFAULT NULL COMMENT '尝试次数',
  `CREATE_TIME` varchar(14) NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '更新时间',
  `STATUS_FLAG` tinyint(2) NOT NULL COMMENT '状态标志：0- OPEN；1- FREEZE；2- REOPEN；3-CLOSE',
  `RESULT_DISCRIPTION` varchar(128) DEFAULT NULL COMMENT '设备侧返回结果描述',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='设备升级记录表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `LOGIN_NAME` varchar(40) DEFAULT NULL COMMENT '登录用户名',
  `USER_NAME` varchar(40) DEFAULT NULL COMMENT '用户姓名',
  `SEX` tinyint(2) DEFAULT NULL COMMENT '性别:0-男，1-女',
  `PASSWORD` varchar(40) DEFAULT NULL COMMENT '登陆密码',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `MOBILE_PHONE` varchar(40) DEFAULT NULL COMMENT '手机',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '说明',
  `DELETE_FLAG` tinyint(1) DEFAULT NULL COMMENT '删除标志：0-未删除；1-已删除',
  `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` varchar(14) DEFAULT NULL COMMENT '最后更新时间',
  `CREATOR_ID` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `SALT` varchar(40) DEFAULT NULL COMMENT '密码SALT',
  `LOCKED` tinyint(1) DEFAULT NULL COMMENT '锁定标志：0-否；1-是',
  `ROLE_ID` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '杨江奇', '0', '44fbaba14f8eca81577d4fefda66788b', 'jiangqi.yang@iekun.com', '13812345678', 'test', '0', '20161107152844', null, '1', 'd89b776a236e5c430738462b30df95b4', '0', '1');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ROLE_ID` bigint(20) NOT NULL COMMENT '角色ID',
  `USER_ID` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='用户角色表';

-- ----------------------------
-- Table structure for t_version_lib
-- ----------------------------
DROP TABLE IF EXISTS `t_version_lib`;
CREATE TABLE `t_version_lib` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设备版本ID',
  `VERSION_TYPE` tinyint(2) DEFAULT NULL COMMENT '版本类型:01-FPGA BBU,SW;02-FPGA BBU;03-FPGA SW;04-BBU SW;05-FPGA;06-BBU;07-SW',
  `VERSION` varchar(100) DEFAULT NULL COMMENT '设备总版本号',
  `FPGA_VERSION` varchar(40) DEFAULT NULL COMMENT 'FPGA版本号',
  `BBU_VERSION` varchar(40) DEFAULT NULL COMMENT 'BBU版本号',
  `SW_VERSION` varchar(100) DEFAULT NULL COMMENT '软件版本号',
  `UPLOAD_USER` varchar(40) DEFAULT NULL COMMENT '上传人的用户名',
  `UPLOAD_TIME` varchar(14) DEFAULT NULL COMMENT '上传的时间',
  `FILE_URL` varchar(200) NOT NULL COMMENT '文件保存路径',
  `REMARK` varchar(40) DEFAULT NULL COMMENT '说明',
  `CREATE_TIME` varchar(14) NOT NULL COMMENT '创建时间',
  `DELETE_FLAG` tinyint(1) DEFAULT NULL COMMENT '删除标记: 0-  正常，  1-  删除',
  `CHECK_SUM` varchar(128) DEFAULT NULL COMMENT '检验和',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='版本信息表';

-- ----------------------------
-- Procedure structure for AUTO_CREATE_UE_INFO_PROCEDURE
-- ----------------------------
DROP PROCEDURE IF EXISTS `AUTO_CREATE_UE_INFO_PROCEDURE`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AUTO_CREATE_UE_INFO_PROCEDURE`()
BEGIN
DECLARE ueInfoTabName VARCHAR(30);

SET @ueInfoDay = DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 1 DAY),'%Y-%m-%d');
SET @ueInfoTabName = CONCAT('t_ue_info_', DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 1 DAY),'%Y_%m_%d'));

SET @STMT :=CONCAT("CREATE TABLE ", @ueInfoTabName , "(
    `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Ue信息ID',
    `SITE_NAME` varchar(40) DEFAULT NULL COMMENT '站点名称',
    `DEVICE_SN` varchar(40) DEFAULT NULL COMMENT '子设备序列号',
    `SITE_SN` varchar(40) DEFAULT NULL,
    `IMSI` varchar(16) DEFAULT NULL COMMENT 'IMSI',
    `IMEI` varchar(16) DEFAULT NULL COMMENT 'IMEI',
    `STMSI` varchar(20) DEFAULT NULL COMMENT 'TMSI',
    `MAC` varchar(20) DEFAULT NULL COMMENT 'MAC地址',
    `LATYPE` varchar(4) DEFAULT NULL COMMENT '位置更新类型',
    `INDICATION` tinyint(2) DEFAULT '0' COMMENT '是否目标手机：0 --- NORMAL；1--- 黑名单手机；2--- 特殊归属地手机',
    `REALTIME` tinyint(1) DEFAULT '0' COMMENT '是否实时上报:00-  不实时上报;01-  实时上报',
    `CAPTURE_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '基站侧记录的上号时间',
    `RARTA` bigint(20) DEFAULT NULL,
    `CREATE_TIME` varchar(14) DEFAULT NULL COMMENT '创建时间',
    `CITY_NAME` varchar(100) DEFAULT NULL COMMENT '归属地地名',
    `CITY_CODE` varchar(10) DEFAULT NULL,
    `BAND` varchar(20) DEFAULT NULL COMMENT '运营商',
    `OPERATOR` varchar(20) DEFAULT NULL COMMENT '运营商',
    PRIMARY KEY (`ID`),
    KEY `DEVICE_SN` (`SITE_NAME`),
    KEY `SUBDEVICE_SN` (`DEVICE_SN`),
    KEY `IMSI` (`IMSI`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='终端信息采集表' ");

SET @INSER_SHARDING_INFO_STMT = "INSERT INTO t_sharding_tab_info ( SHARDING_TAB_NAME, SHARDING_TAB_DATE ) VALUES( ?, ?)";

PREPARE STMT FROM @STMT;
EXECUTE STMT;

PREPARE INSER_SHARDING_INFO_STMT FROM @INSER_SHARDING_INFO_STMT;
EXECUTE INSER_SHARDING_INFO_STMT USING @ueInfoTabName, @ueInfoDay ;

END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for AUTO_CREATE_UE_INFO_EVENT
-- ----------------------------
DROP EVENT IF EXISTS `AUTO_CREATE_UE_INFO_EVENT`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `AUTO_CREATE_UE_INFO_EVENT` ON SCHEDULE EVERY 1 DAY STARTS '2016-12-31 23:00:00' ON COMPLETION PRESERVE ENABLE DO BEGIN
CALL AUTO_CREATE_UE_INFO_PROCEDURE();
END
;;
DELIMITER ;
