/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2017-01-03 22:25:57
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=MyISAM AUTO_INCREMENT=122 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='操作码表';

-- ----------------------------
-- Records of t_oper_code
-- ----------------------------
INSERT INTO `t_oper_code` VALUES ('1', '0012', 'RF开关命令下发', '设备操作日志');
INSERT INTO `t_oper_code` VALUES ('2', '0008', '版本查询下发', '设备操作日志');
INSERT INTO `t_oper_code` VALUES ('3', '000D', '心跳包确认', '设备操作日志');
INSERT INTO `t_oper_code` VALUES ('4', '0010', '设备重启', '设备操作日志');
INSERT INTO `t_oper_code` VALUES ('5', '000C', '请求获取当前设备参数', '设备操作日志');
INSERT INTO `t_oper_code` VALUES ('6', '0011', '功能参数下发', '设备操作日志');
INSERT INTO `t_oper_code` VALUES ('7', '00D0', '黑名单下发', '设备操作日志');
INSERT INTO `t_oper_code` VALUES ('8', '0007', 'RF参数配置下发', '设备操作日志');
INSERT INTO `t_oper_code` VALUES ('13', 'com.iekun.ef.controller.UserController.login', '用户登录', '用户登录登出日志');
INSERT INTO `t_oper_code` VALUES ('14', 'com.iekun.ef.controller.UserController.loginPost', '用户登录校验', '用户登录登出日志');
INSERT INTO `t_oper_code` VALUES ('15', 'com.iekun.ef.controller.UserController.forgetPassword', '忘记密码', '用户登录登出日志');
INSERT INTO `t_oper_code` VALUES ('16', 'com.iekun.ef.controller.UserController.findPwdPost', '找回密码', '用户登录登出日志');
INSERT INTO `t_oper_code` VALUES ('17', 'com.iekun.ef.controller.UserController.updateSelfInfo', '更新个人信息', '用户管理日志');
INSERT INTO `t_oper_code` VALUES ('18', 'com.iekun.ef.controller.UserController.notifications', '通知', '通知日志');
INSERT INTO `t_oper_code` VALUES ('19', 'com.iekun.ef.controller.UserController.getNotifications', '获取通知', '通知日志');
INSERT INTO `t_oper_code` VALUES ('20', 'com.iekun.ef.controller.UserController.markNotificationRead', '置通知为已读', '通知日志');
INSERT INTO `t_oper_code` VALUES ('21', 'com.iekun.ef.controller.AnalysisController.imsiStatistics', '上号统计', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('22', 'com.iekun.ef.controller.AnalysisController.dataCollide', '数据碰撞', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('23', 'com.iekun.ef.controller.AnalysisController.residentPeople', '常驻人口分析', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('24', 'com.iekun.ef.controller.AnalysisController.imsiFollow', 'imsi伴随', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('25', 'com.iekun.ef.controller.AnalysisController.suspectTrail', '嫌疑人轨迹跟踪', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('26', 'com.iekun.ef.controller.AnalysisController.getIMSIStatisticsTasks', '获取上号统计任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('27', 'com.iekun.ef.controller.AnalysisController.createImsiStatisticsTask', '创建上号统计任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('28', 'com.iekun.ef.controller.AnalysisController.imsiStatisticsReport', '生成上号统计报告', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('29', 'com.iekun.ef.controller.AnalysisController.getImsiStatisticsReportData', '获取上号统计报告数据', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('30', 'com.iekun.ef.controller.AnalysisController.getSuspectTrailTasks', '获取嫌疑人分析任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('31', 'com.iekun.ef.controller.AnalysisController.createSuspectTrailTask', '创建嫌疑人分析任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('32', 'com.iekun.ef.controller.AnalysisController.suspectTrailReport', '生成嫌疑人分析报告', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('33', 'com.iekun.ef.controller.AnalysisController.getSuspectTrailReportData', '获取嫌疑人分析报告数据', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('34', 'com.iekun.ef.controller.AnalysisController.getDataCollideTasks', '获取数据碰撞分析任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('35', 'com.iekun.ef.controller.AnalysisController.createDataCollideTask', '创建数据碰撞分析任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('36', 'com.iekun.ef.controller.AnalysisController.dataCollideReport', '生成碰撞报告', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('37', 'com.iekun.ef.controller.AnalysisController.getDataCollideReportData', '获取数据碰撞报告', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('38', 'com.iekun.ef.controller.AnalysisController.getIMSIFollowTasks', '获取imsi伴随任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('39', 'com.iekun.ef.controller.AnalysisController.createIMSIFollowTask', '创建imsi伴随任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('40', 'com.iekun.ef.controller.AnalysisController.imsiFollowReport', '生成imsi伴随报告', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('41', 'com.iekun.ef.controller.AnalysisController.getImsiFollowReportData', '获取imsi伴随报告', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('42', 'com.iekun.ef.controller.AnalysisController.getResidentPeopleTasks', '获取常驻人口分析任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('43', 'com.iekun.ef.controller.AnalysisController.createResidentPeopleTask', '创建常住人口分析任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('44', 'com.iekun.ef.controller.AnalysisController.residentPeopleReport', '生成常住人口报告', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('45', 'com.iekun.ef.controller.AnalysisController.getResidentPeopleReportData', '获取常驻人口报告', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('46', 'com.iekun.ef.controller.AnalysisController.deleteTask', '删除统计分析任务', '统计分析日志');
INSERT INTO `t_oper_code` VALUES ('47', 'com.iekun.ef.controller.DataController.realTimeData', '实时数据', '上报数据日志');
INSERT INTO `t_oper_code` VALUES ('48', 'com.iekun.ef.controller.DataController.historyData', '历史数据', '上报数据日志');
INSERT INTO `t_oper_code` VALUES ('49', 'com.iekun.ef.controller.DataController.queryHistoryData', '历史数据查询', '上报数据日志');
INSERT INTO `t_oper_code` VALUES ('50', 'com.iekun.ef.controller.DeviceAlarmController.current', '切换到设备当前告警页面', '设备告警日志');
INSERT INTO `t_oper_code` VALUES ('51', 'com.iekun.ef.controller.DeviceAlarmController.getCurrentAlarms', '获取当前告警', '设备告警日志');
INSERT INTO `t_oper_code` VALUES ('52', 'com.iekun.ef.controller.DeviceAlarmController.history', '切换到设备历史告警页面', '设备告警日志');
INSERT INTO `t_oper_code` VALUES ('53', 'com.iekun.ef.controller.DeviceAlarmController.queryHistoryAlarms', '历史告警查询', '设备告警日志');
INSERT INTO `t_oper_code` VALUES ('54', 'com.iekun.ef.controller.DeviceController.status', '', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('55', 'com.iekun.ef.controller.DeviceController.index', '', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('56', 'com.iekun.ef.controller.DeviceController.group', '', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('57', 'com.iekun.ef.controller.DeviceController.getSites', '获取站点', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('58', 'com.iekun.ef.controller.DeviceController.getDevicesBySiteId', '获取指定站点下的设备', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('60', 'com.iekun.ef.controller.DeviceController.createSite', '创建站点', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('61', 'com.iekun.ef.controller.DeviceController.updateSite', '更新站点', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('62', 'com.iekun.ef.controller.DeviceController.delSite', '删除站点', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('63', 'com.iekun.ef.controller.DeviceController.getDevices', '获取设备', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('64', 'com.iekun.ef.controller.DeviceController.createDevice', '创建设备', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('65', 'com.iekun.ef.controller.DeviceController.checkSitSn', '检查sitesn合法性', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('66', 'com.iekun.ef.controller.DeviceController.checkDeviceSn', '检查devicesn合法性', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('67', 'com.iekun.ef.controller.DeviceController.updateDevice', '更新设备', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('68', 'com.iekun.ef.controller.DeviceController.delDevice', '删除设备', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('69', 'com.iekun.ef.controller.DeviceController.getDevicesTree', '获取设备树', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('70', 'com.iekun.ef.controller.DeviceController.assignDevice', '分配设备', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('71', 'com.iekun.ef.controller.DeviceController.getDeviceStatusTree', '获取设备状态树', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('72', 'com.iekun.ef.controller.DeviceController.getDeviceStatusInfo', '获取设备状态信息', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('73', 'com.iekun.ef.controller.DeviceController.getSiteStatusInfo', '获取站点状态信息', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('74', 'com.iekun.ef.controller.DeviceLicenseController.getDeviceLicense', '获取设备授权', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('75', 'com.iekun.ef.controller.DeviceLicenseController.updateFile', '更新设备授权', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('76', 'com.iekun.ef.controller.DeviceLicenseController.queryHistory', '查询设备授权历史', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('77', 'com.iekun.ef.controller.DeviceOamController.deviceAction', '设备oam消息下发', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('78', 'com.iekun.ef.controller.DeviceVersionController.getLibraries', '获取设备版本', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('79', 'com.iekun.ef.controller.DeviceVersionController.createLibrary', '创建设备版本', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('80', 'com.iekun.ef.controller.DeviceVersionController.delLibrary', '删除设备版本', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('81', 'com.iekun.ef.controller.DeviceVersionController.upgradeDevice', '更新设备版本', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('82', 'com.iekun.ef.controller.DeviceVersionController.getDevVerList', '获取所有设备的当前版本', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('83', 'com.iekun.ef.controller.DeviceVersionController.queryHistory', '查询设备历史升级记录', '设备管理日志');
INSERT INTO `t_oper_code` VALUES ('84', 'com.iekun.ef.controller.LoggerController.queryLoggerData', '历史日志查询', '系统日志');
INSERT INTO `t_oper_code` VALUES ('85', 'com.iekun.ef.controller.NoticeController.getSmsTemplate', '获取短信模板', '通知日志');
INSERT INTO `t_oper_code` VALUES ('86', 'com.iekun.ef.controller.NoticeController.setSmsTemplate', '更新短信模板', '通知日志');
INSERT INTO `t_oper_code` VALUES ('87', 'com.iekun.ef.controller.NoticeController.queryHistorySms', '查询短信历史', '通知日志');
INSERT INTO `t_oper_code` VALUES ('88', 'com.iekun.ef.controller.NoticeController.getEmailTpls', '获取邮件模板', '通知日志');
INSERT INTO `t_oper_code` VALUES ('89', 'com.iekun.ef.controller.NoticeController.delEmailTps', '删除邮件模板', '通知日志');
INSERT INTO `t_oper_code` VALUES ('90', 'com.iekun.ef.controller.NoticeController.createEmailTps', '创建邮件模板', '通知日志');
INSERT INTO `t_oper_code` VALUES ('91', 'com.iekun.ef.controller.NoticeController.updateEmailTps', '更新邮件模板', '通知日志');
INSERT INTO `t_oper_code` VALUES ('92', 'com.iekun.ef.controller.NoticeController.setEmailTpsActive', '激活发送邮件', '通知日志');
INSERT INTO `t_oper_code` VALUES ('93', 'com.iekun.ef.controller.NoticeController.queryHistoryEmail', '查询邮件发送历史', '通知日志');
INSERT INTO `t_oper_code` VALUES ('94', 'com.iekun.ef.controller.NoticeController.getEmailDetail', '获取邮件详情', '通知日志');
INSERT INTO `t_oper_code` VALUES ('95', 'com.iekun.ef.controller.SystemController.updateLicense', 'efence系统授权文件更新', '系统日志');
INSERT INTO `t_oper_code` VALUES ('96', 'com.iekun.ef.controller.SystemController.exportBaseData', '导出基础表数据', '系统日志');
INSERT INTO `t_oper_code` VALUES ('97', 'com.iekun.ef.controller.SystemController.importBaseData', '导入基础表数据', '系统日志');
INSERT INTO `t_oper_code` VALUES ('98', 'com.iekun.ef.controller.SystemController.getConfig', '获取当前系统参数', '系统日志');
INSERT INTO `t_oper_code` VALUES ('99', 'com.iekun.ef.controller.SystemController.setConfig', '设置当前系统参数', '系统日志');
INSERT INTO `t_oper_code` VALUES ('100', 'com.iekun.ef.controller.TargetAlarmController.getCurrentTargets', '获取当前预警', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('101', 'com.iekun.ef.controller.TargetAlarmController.confirmTargetAlarm', '预警确认', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('102', 'com.iekun.ef.controller.TargetAlarmController.queryHistoryData', '查询预警历史数据', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('103', 'com.iekun.ef.controller.TargetAlarmController.getBlacklist', '获取黑名单列表', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('104', 'com.iekun.ef.controller.TargetAlarmController.addBlacklist', '增加黑名单列表', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('105', 'com.iekun.ef.controller.TargetAlarmController.delBlacklist', '删除黑名单列表', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('106', 'com.iekun.ef.controller.TargetAlarmController.updateReceivers', '更新接收人', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('107', 'com.iekun.ef.controller.TargetAlarmController.getHomeOwnership', '获取特殊归属地', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('108', 'com.iekun.ef.controller.TargetAlarmController.addHomeOwnership', '增加特殊归属地', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('109', 'com.iekun.ef.controller.TargetAlarmController.delHomeOwnership', '删除特殊归属地', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('110', 'com.iekun.ef.controller.TargetAlarmController.getAlarmRuleNames', '增加告警规则', '目标预警日志');
INSERT INTO `t_oper_code` VALUES ('111', 'com.iekun.ef.controller.UploadController.uploadFile', '上传文件', '系统日志');
INSERT INTO `t_oper_code` VALUES ('112', 'com.iekun.ef.controller.UploadController.upload', '上载logo图片', '系统日志');
INSERT INTO `t_oper_code` VALUES ('113', 'com.iekun.ef.controller.UserManagerController.getUsers', '获取当前系统内所有用户', '用户管理日志');
INSERT INTO `t_oper_code` VALUES ('114', 'com.iekun.ef.controller.UserManagerController.getRoles', '获取当前系统内所有角色', '用户管理日志');
INSERT INTO `t_oper_code` VALUES ('115', 'com.iekun.ef.controller.UserManagerController.setAccountActive', '激活账号', '用户管理日志');
INSERT INTO `t_oper_code` VALUES ('116', 'com.iekun.ef.controller.UserManagerController.delUser', '删除用户', '用户管理日志');
INSERT INTO `t_oper_code` VALUES ('117', 'com.iekun.ef.controller.UserManagerController.updatePassword', '更新用户口令', '用户管理日志');
INSERT INTO `t_oper_code` VALUES ('118', 'com.iekun.ef.controller.UserManagerController.createUser', '创建用户', '用户管理日志');
INSERT INTO `t_oper_code` VALUES ('119', 'com.iekun.ef.controller.UserManagerController.updateUser', '更新用户', '用户管理日志');
INSERT INTO `t_oper_code` VALUES ('120', '', '', '');
INSERT INTO `t_oper_code` VALUES ('121', '', '', '');
