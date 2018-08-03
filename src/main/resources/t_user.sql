/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2016-11-23 11:27:18
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
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('42', 'admin', '杨江奇', '0', '44fbaba14f8eca81577d4fefda66788b', 'jiangqi.yang@iekun.com', '13812345678', 'test', '0', '20161107152844', null, '1', 'd89b776a236e5c430738462b30df95b4', '0', '1');
INSERT INTO `t_user` VALUES ('43', 'yuguangtao', '于广涛', '0', '8d988a165eecf402d15570f2d82d42f4', 'yuguangtao@iekun.com', '18965659856', '管理员只能看到设备', '0', '20161123110351', null, '42', 'de326855fcff432590d3bbb46d4669b5', '0', '3');
INSERT INTO `t_user` VALUES ('44', 'lutao', '陆涛', '0', '66985de5290ac309ed6bbc23d92ae5f4', 'lutao@iekun.com', '15000558596', '操作员权限', '0', '20161123110351', null, '42', '297524c215f44b228ff1a80c15fed2df', '0', '2');
