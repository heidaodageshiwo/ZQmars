/*
Navicat MySQL Data Transfer

Source Server         : iekun
Source Server Version : 50544
Source Host           : 192.168.1.200:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2017-01-17 12:14:53
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=MyISAM AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('42', 'admin', '杨江奇', '1', 'dd52f0603ac748e490abdcad9d26d79c', 'chichester.lin@iekun.com', '13812345670', 'test', '0', '20161107152844', '20170104223511', '1', '7a55eec840a396887411b415a4ba0895', '0', '1');
