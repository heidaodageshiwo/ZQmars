/*
Navicat MySQL Data Transfer

Source Server         : iekun
Source Server Version : 50544
Source Host           : 192.168.1.200:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2017-01-17 12:13:47
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'admin', '0', '系统管理员', '0', '201611042323', null, '1');
INSERT INTO `t_role` VALUES ('2', 'operator', '0', '操作员', '0', '201611042324', null, '1');
INSERT INTO `t_role` VALUES ('3', 'manager', '0', '设备管理员', '0', '201611042324', null, '1');
