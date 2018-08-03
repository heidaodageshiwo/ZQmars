/*
Navicat MySQL Data Transfer

Source Server         : iekun
Source Server Version : 50544
Source Host           : 192.168.1.200:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2017-01-17 12:13:34
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源表';

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES ('1', '1', '', '总控台', '/', '', '3', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('2', '1', null, '设备分布图', '/map', null, '3', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('3', '1', null, '概览', null, 'fa fa-dashboard', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('4', '1', null, '实时数据', '/realTimeData', null, '6', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('5', '1', null, '历史数据', '/historyData', null, '6', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('6', '1', null, '数据采集', null, 'fa fa-cloud-upload', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('7', '1', null, '上号统计', '/analysis/imsiStatistics', null, '12', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('8', '1', null, '数据碰撞分析', '/analysis/dataCollide', null, '12', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('9', '1', null, '常驻人口外来人口分析', '/analysis/residentPeople', null, '12', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('10', '1', null, 'IMSI伴随分析', '/analysis/imsiFollow', null, '12', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('11', '1', null, '嫌疑人运动轨迹', '/analysis/suspectTrail', null, '12', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('12', '1', null, '数据分析', null, 'fa  fa-random', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('13', '1', null, '当前预警', '/target/current', null, '17', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('14', '1', null, '历史预警', '/target/history', null, '17', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('15', '1', null, '黑名单管理', '/target/blacklist', null, '17', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('16', '1', null, '归属地预警管理', '/target/homeOwnership', null, '17', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('17', '1', null, '预警', null, 'fa fa-lightbulb-o', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('18', '1', null, '用户管理', '/user/index', null, '20', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('20', '1', null, '用户管理', null, 'fa fa-user', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('21', '1', null, '设备状态', '/device/status', null, '34', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('22', '1', null, '设备管理', '/device/index', null, '34', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('23', '1', null, '设备分配', '/device/group', null, '34', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('24', '1', null, '设备维护', '/device/oam', null, '34', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('25', '1', null, '版本库管理', '/device/version/library', null, '27', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('26', '1', null, '设备版本管理', '/device/version/manager', null, '27', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('27', '1', null, '版本管理', null, 'fa fa-file-text-o', '34', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('28', '1', null, '授权状态', '/device/license/index', null, '30', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('29', '1', null, '历史授权', '/device/license/history', null, '30', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('30', '1', null, '授权管理', null, 'fa fa-key', '34', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('31', '1', null, '当前告警', '/device/alarm/current', null, '33', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('32', '1', null, '历史告警', '/device/alarm/history', null, '33', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('33', '1', null, '设备告警', null, 'fa fa-warning', '34', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('34', '1', null, '设备管理', null, 'fa  fa-cubes', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('35', '1', null, '用户信息', '/user/profile', null, '38', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('36', '1', null, '通知消息', '/user/notifications', null, '38', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('38', '1', null, '用户中心', null, 'fa fa-child', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('39', '1', null, '日志管理', '/system/logger', null, '50', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('40', '1', null, '模板管理', '/system/sms/template', null, '42', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('41', '1', null, '短信历史', '/system/sms/history', null, '42', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('42', '1', null, '短信通知', null, 'fa  fa-commenting-o', '50', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('43', '1', null, '模板管理', '/system/email/template', null, '45', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('44', '1', null, '邮件历史', '/system/email/history', null, '45', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('45', '1', null, '邮件通知', null, 'fa fa-envelope', '50', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('46', '1', null, '系统配置', '/system/configuration', null, '50', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('47', '1', null, '数据库维护', '/system/databaseMaintenance', null, '50', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('48', '1', null, '系统授权', '/system/license', null, '50', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('50', '1', null, '系统维护', null, 'fa fa-gears', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('49', '1', null, '系统升级', '/system/upgrade', 'fa fa-long-arrow-up', '50', null, null, null, null, null, null, null, '0');
INSERT INTO `t_resource` VALUES ('19', '1', null, '角色管理', '/user/role', null, '20', null, null, null, null, null, null, null, '0');
