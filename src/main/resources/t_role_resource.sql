/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : efence2

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2016-11-23 11:26:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID值',
  `ROLE_ID` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `RESOURCE_ID` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='角色资源表';

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES ('1', '2', '1');
INSERT INTO `t_role_resource` VALUES ('2', '2', '2');
INSERT INTO `t_role_resource` VALUES ('3', '2', '3');
INSERT INTO `t_role_resource` VALUES ('4', '2', '4');
INSERT INTO `t_role_resource` VALUES ('5', '2', '5');
INSERT INTO `t_role_resource` VALUES ('6', '2', '6');
INSERT INTO `t_role_resource` VALUES ('7', '2', '7');
INSERT INTO `t_role_resource` VALUES ('8', '2', '8');
INSERT INTO `t_role_resource` VALUES ('9', '2', '9');
INSERT INTO `t_role_resource` VALUES ('10', '2', '10');
INSERT INTO `t_role_resource` VALUES ('11', '2', '11');
INSERT INTO `t_role_resource` VALUES ('12', '2', '12');
INSERT INTO `t_role_resource` VALUES ('13', '2', '13');
INSERT INTO `t_role_resource` VALUES ('14', '2', '14');
INSERT INTO `t_role_resource` VALUES ('15', '2', '15');
INSERT INTO `t_role_resource` VALUES ('16', '2', '17');
INSERT INTO `t_role_resource` VALUES ('17', '1', '1');
INSERT INTO `t_role_resource` VALUES ('18', '1', '2');
INSERT INTO `t_role_resource` VALUES ('19', '1', '3');
INSERT INTO `t_role_resource` VALUES ('20', '1', '4');
INSERT INTO `t_role_resource` VALUES ('21', '1', '5');
INSERT INTO `t_role_resource` VALUES ('22', '1', '6');
INSERT INTO `t_role_resource` VALUES ('23', '1', '7');
INSERT INTO `t_role_resource` VALUES ('24', '1', '8');
INSERT INTO `t_role_resource` VALUES ('25', '1', '9');
INSERT INTO `t_role_resource` VALUES ('26', '1', '10');
INSERT INTO `t_role_resource` VALUES ('27', '1', '11');
INSERT INTO `t_role_resource` VALUES ('28', '1', '12');
INSERT INTO `t_role_resource` VALUES ('29', '1', '13');
INSERT INTO `t_role_resource` VALUES ('30', '1', '14');
INSERT INTO `t_role_resource` VALUES ('31', '1', '15');
INSERT INTO `t_role_resource` VALUES ('32', '1', '16');
INSERT INTO `t_role_resource` VALUES ('33', '1', '17');
INSERT INTO `t_role_resource` VALUES ('34', '1', '18');
INSERT INTO `t_role_resource` VALUES ('35', '1', '19');
INSERT INTO `t_role_resource` VALUES ('36', '1', '20');
INSERT INTO `t_role_resource` VALUES ('37', '1', '21');
INSERT INTO `t_role_resource` VALUES ('38', '1', '22');
INSERT INTO `t_role_resource` VALUES ('39', '1', '23');
INSERT INTO `t_role_resource` VALUES ('40', '1', '24');
INSERT INTO `t_role_resource` VALUES ('41', '1', '25');
INSERT INTO `t_role_resource` VALUES ('42', '1', '26');
INSERT INTO `t_role_resource` VALUES ('43', '1', '27');
INSERT INTO `t_role_resource` VALUES ('44', '1', '28');
INSERT INTO `t_role_resource` VALUES ('45', '1', '29');
INSERT INTO `t_role_resource` VALUES ('46', '1', '30');
INSERT INTO `t_role_resource` VALUES ('47', '1', '31');
INSERT INTO `t_role_resource` VALUES ('48', '1', '32');
INSERT INTO `t_role_resource` VALUES ('49', '1', '33');
INSERT INTO `t_role_resource` VALUES ('50', '1', '34');
INSERT INTO `t_role_resource` VALUES ('51', '1', '35');
INSERT INTO `t_role_resource` VALUES ('52', '1', '36');
INSERT INTO `t_role_resource` VALUES ('53', '1', '37');
INSERT INTO `t_role_resource` VALUES ('54', '1', '38');
INSERT INTO `t_role_resource` VALUES ('55', '1', '39');
INSERT INTO `t_role_resource` VALUES ('56', '1', '40');
INSERT INTO `t_role_resource` VALUES ('57', '1', '41');
INSERT INTO `t_role_resource` VALUES ('58', '1', '42');
INSERT INTO `t_role_resource` VALUES ('59', '1', '43');
INSERT INTO `t_role_resource` VALUES ('60', '1', '44');
INSERT INTO `t_role_resource` VALUES ('61', '1', '45');
INSERT INTO `t_role_resource` VALUES ('62', '1', '46');
INSERT INTO `t_role_resource` VALUES ('63', '1', '47');
INSERT INTO `t_role_resource` VALUES ('64', '1', '48');
INSERT INTO `t_role_resource` VALUES ('65', '1', '49');
INSERT INTO `t_role_resource` VALUES ('66', '3', '1');
INSERT INTO `t_role_resource` VALUES ('67', '3', '2');
INSERT INTO `t_role_resource` VALUES ('68', '3', '3');
INSERT INTO `t_role_resource` VALUES ('69', '3', '21');
INSERT INTO `t_role_resource` VALUES ('70', '3', '22');
INSERT INTO `t_role_resource` VALUES ('71', '3', '23');
INSERT INTO `t_role_resource` VALUES ('72', '3', '24');
INSERT INTO `t_role_resource` VALUES ('73', '3', '25');
INSERT INTO `t_role_resource` VALUES ('74', '3', '26');
INSERT INTO `t_role_resource` VALUES ('75', '3', '27');
INSERT INTO `t_role_resource` VALUES ('76', '3', '28');
INSERT INTO `t_role_resource` VALUES ('77', '3', '29');
INSERT INTO `t_role_resource` VALUES ('78', '3', '30');
INSERT INTO `t_role_resource` VALUES ('79', '3', '31');
INSERT INTO `t_role_resource` VALUES ('80', '3', '32');
INSERT INTO `t_role_resource` VALUES ('81', '3', '33');
INSERT INTO `t_role_resource` VALUES ('82', '3', '34');
INSERT INTO `t_role_resource` VALUES ('83', '1', '50');
