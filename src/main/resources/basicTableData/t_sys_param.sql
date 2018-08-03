/*
Navicat MySQL Data Transfer

Source Server         : iekun
Source Server Version : 50544
Source Host           : 192.168.1.200:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2017-01-17 12:14:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_sys_param
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_param`;
CREATE TABLE `t_sys_param` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '系统参数ID',
  `PARA_KEY` varchar(40) NOT NULL COMMENT '系统参数KEY值',
  `VALUE` varchar(255) NOT NULL COMMENT '系统参数内容',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统参数表';

-- ----------------------------
-- Records of t_sys_param
-- ----------------------------
INSERT INTO `t_sys_param` VALUES ('1', 'smsTemplate', '尊敬的{%user%}, 在{%time%}时间目标{%target%}出现于{%location%}位置');
INSERT INTO `t_sys_param` VALUES ('2', 'systemIcon', '/img/logo.png');
INSERT INTO `t_sys_param` VALUES ('3', 'companyYear', '2014');
INSERT INTO `t_sys_param` VALUES ('4', 'companyName', '技术有限公司');
INSERT INTO `t_sys_param` VALUES ('5', 'companyURL', '');
INSERT INTO `t_sys_param` VALUES ('6', 'systemName', '移动终端预警系统');
INSERT INTO `t_sys_param` VALUES ('7', 'upgradeURL', 'http://10.1.1.64:9191');
INSERT INTO `t_sys_param` VALUES ('8', 'mapCenterLng', '117.386154');
INSERT INTO `t_sys_param` VALUES ('9', 'mapCenterLat', '36.115173');
INSERT INTO `t_sys_param` VALUES ('10', 'mapZoom', '15');
INSERT INTO `t_sys_param` VALUES ('11', 'mapDataSwitch', '1');
INSERT INTO `t_sys_param` VALUES ('12', 'devAlarmSms', '0');
