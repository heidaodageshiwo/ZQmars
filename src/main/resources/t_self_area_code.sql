/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2016-12-01 12:08:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_self_area_code
-- ----------------------------
DROP TABLE IF EXISTS `t_self_area_code`;
CREATE TABLE `t_self_area_code` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `PROVINCE_CODE` varchar(10) DEFAULT NULL COMMENT '省编码',
  `PRIVINCE_NAME` varchar(40) DEFAULT NULL COMMENT '省名：去掉“省”如山东省则写为山东',
  `CITY_CODE` varchar(10) DEFAULT NULL COMMENT '市编码',
  `CITY_NAME` varchar(40) DEFAULT NULL COMMENT '城市',
  `LEVEL` tinyint(4) DEFAULT NULL COMMENT '行政区划级别：1:省； 2：市',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
