/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : customer_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-06 21:47:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `departmentNo` varchar(20) NOT NULL COMMENT 'departmentNo',
  `departmentName` varchar(20) NOT NULL COMMENT '部门名称',
  `bornDate` varchar(20) default NULL COMMENT '成立日期',
  `fuzeren` varchar(20) NOT NULL COMMENT '负责人',
  `departmentDesc` varchar(8000) NOT NULL COMMENT '部门介绍',
  PRIMARY KEY  (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('BM001', '人事部', '2018-03-13', '宋明月', '<p>管理公司人事信息</p>');
INSERT INTO `t_department` VALUES ('BM002', '市场部', '2018-03-14', '李梦琪', '<p>管理市场营销！</p>');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '管理回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '我想订单啥时候发货', '我昨天遇到贵公司的产品啥时候发货哦？', 'user1', '2018-03-25 18:49:09', '最新订单爆了，估计2天后！', '2018-03-25 23:08:22');

-- ----------------------------
-- Table structure for `t_manager`
-- ----------------------------
DROP TABLE IF EXISTS `t_manager`;
CREATE TABLE `t_manager` (
  `managerUserName` varchar(20) NOT NULL COMMENT 'managerUserName',
  `password` varchar(20) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `sex` varchar(4) NOT NULL COMMENT '性别',
  `age` int(11) NOT NULL COMMENT '年龄',
  `departmentObj` varchar(20) NOT NULL COMMENT '所在部门',
  `managerMemo` varchar(500) default NULL COMMENT '管理备注',
  PRIMARY KEY  (`managerUserName`),
  KEY `departmentObj` (`departmentObj`),
  CONSTRAINT `t_manager_ibfk_1` FOREIGN KEY (`departmentObj`) REFERENCES `t_department` (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_manager
-- ----------------------------
INSERT INTO `t_manager` VALUES ('gl001', '123', 'a', 'b', '1', 'BM001', 'c');
INSERT INTO `t_manager` VALUES ('gl002', '123', '王波', '男', '37', 'BM002', '营销主管');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(5000) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '客户信息系统开题', '<p>欢迎各位新老客户使用本平台接收公司最新信息！</p>', '2018-03-25 23:10:30');

-- ----------------------------
-- Table structure for `t_usercare`
-- ----------------------------
DROP TABLE IF EXISTS `t_usercare`;
CREATE TABLE `t_usercare` (
  `careId` int(11) NOT NULL auto_increment COMMENT '关怀id',
  `careTitle` varchar(80) NOT NULL COMMENT '关怀主题',
  `careContent` varchar(8000) NOT NULL COMMENT '关怀内容',
  `userObj` varchar(30) NOT NULL COMMENT '关怀客户',
  `careTime` varchar(20) default NULL COMMENT '关怀时间',
  PRIMARY KEY  (`careId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_usercare_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_usercare
-- ----------------------------
INSERT INTO `t_usercare` VALUES ('1', '祝你生日快乐', '<p>马上就是你的生日到了，祝你生日快乐，永远年轻！</p>', 'user1', '2018-03-25 12:10:42');
INSERT INTO `t_usercare` VALUES ('2', '祝你母亲健康长寿', '<p>听说你母亲将于本月底举行70大寿盛宴，祝福你母亲寿比南山！</p>', 'user1', '2018-03-25 23:25:18');

-- ----------------------------
-- Table structure for `t_useremail`
-- ----------------------------
DROP TABLE IF EXISTS `t_useremail`;
CREATE TABLE `t_useremail` (
  `emailId` int(11) NOT NULL auto_increment COMMENT '邮件id',
  `title` varchar(80) NOT NULL COMMENT '邮件标题',
  `content` varchar(8000) NOT NULL COMMENT '邮件内容',
  `userObj` varchar(30) NOT NULL COMMENT '接受客户',
  `sendTime` varchar(20) default NULL COMMENT '发送时间',
  PRIMARY KEY  (`emailId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_useremail_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_useremail
-- ----------------------------
INSERT INTO `t_useremail` VALUES ('1', '你的会员快到期了', '<p>请尽快联系你的销售代表续费哦！</p>', 'user1', '2018-03-25 12:10:50');
INSERT INTO `t_useremail` VALUES ('2', '你的会员续费成功！', '<p>恭喜你成功开通了咱们公司的XXX产品6个月服务期，祝你在这里玩得开心！</p>', 'user1', '2018-03-25 23:26:36');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '客户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '王超', '男', '2018-02-28', 'upload/310a839a-e78e-4929-b2f2-c3374bf91e92.jpg', '13573598343', 'wangchao@163.com', '四川成都红星路13号', '2018-03-25 12:10:23');
INSERT INTO `t_userinfo` VALUES ('user2', '123', '王青', '男', '2018-03-09', 'upload/20c5c6ca-8f3d-4918-9d2a-b4d11935bae8.jpg', '13980890834', 'wangqing@163.com', '四川南充海阳路13号', '2018-03-25 23:17:44');
