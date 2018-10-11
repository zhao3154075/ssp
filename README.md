# 随手拍红包项目
## 项目框架
  SpringBoot          
  Thymeleaf        
  MyBatis       
  RabbitMQ         
  Redis       
  MySql  
  
## 代码结构
admin--后台管理（controller，service）     
mobile--前台（controller，service）        
database--数据库操作（dao,model,query,数据配置文件也在这里）         
common--共用方法（工具类，公用配置等，urlPrefix配置在这里）          
operate-recorder--操作记录器（通过MQ与前后台关联）           
prize-recorder--红包记录器和发送（通过MQ与前后台关联）         
resource--所有静态文件（css，js，图片等）

## 数据库

### 数据库设计

#### 粉丝表 fans  

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>fansId</td><td>int</td><td>11</td><td>粉丝ID(主键)</td></tr>
<tr><td>openId</td><td>varchar</td><td>32</td><td>openId</td></tr>
<tr><td>nickName</td><td>varchar</td><td>150</td><td>昵称</td></tr>
<tr><td>realName</td><td>varchar</td><td>20</td><td>姓名</td></tr>
<tr><td>mobile</td><td>varchar</td><td>15</td><td>联系方式</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
<tr><td>headimgurl</td><td>varchar</td><td>200</td><td>头像</td></tr>
<tr><td>subscribe</td><td>int</td><td>1</td><td>是否关注</td></tr>
</table>


#### 全局参数表 global_setting

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>settingId</td><td>int</td><td>11</td><td>主键</td></tr>
<tr><td>dayLimit</td><td>int</td><td>11</td><td>每日奖励发放上限</td></tr>
<tr><td>firstAmount</td><td>int</td><td>11</td><td>初始红包金额</td></tr>
<tr><td>receiveType</td><td>int</td><td>11</td><td>领取方式（0，1，2分别对应产品文档的3种方式）</td></tr>
<tr><td>updateTime</td><td>bigint</td><td>10</td><td>最后修改时间</td></tr>
</table>


#### 后台用户登录表 login_user

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>userId</td><td>int</td><td>11</td><td>用户ID(主键)</td></tr>
<tr><td>userName</td><td>varchar</td><td>30</td><td>用户名</td></tr>
<tr><td>realName</td><td>varchar</td><td>20</td><td>真实姓名</td></tr>
<tr><td>mobile</td><td>varchar</td><td>15</td><td>联系方式</td></tr>
<tr><td>roleType</td><td>int</td><td>1</td><td>角色类型(0 系统管理，1 一级管理员，2 二级管理员)</td></tr>
  <tr><td>psw</td><td>varchar</td><td>32</td><td>密码(32位md5)</td></tr>
  <tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
  <tr><td>status</td><td>int</td><td>1</td><td>状态(0 禁用，1 启用)</td></tr>
</table>

#### 操作记录表 operate_record

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>recordId</td><td>bigint</td><td>20</td><td>主键</td></tr>
<tr><td>reportId</td><td>bigint</td><td>20</td><td>举报信息ID（外键）</td></tr>
<tr><td>userId</td><td>int</td><td>11</td><td>登录用户ID（外键）</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
<tr><td>desc</td><td>varchar</td><td>100</td><td>备注</td></tr>
</table>

#### 红包记录表 prize_record

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>recordId</td><td>bigint</td><td>20</td><td>主键</td></tr>
<tr><td>fansId</td><td>int</td><td>11</td><td>粉丝ID（外键）</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
<tr><td>status</td><td>int</td><td>1</td><td>状态（-1 发送失败，0 待发送，1 发送成功，2 发送中）</td></tr>
<tr><td>reportId</td><td>bigint</td><td>20</td><td>举报信息ID（外键）</td></tr>
  <tr><td>type</td><td>int</td><td>1</td><td>类型（0 初次发放，1 追加发放）</td></tr>
  <tr><td>amount</td><td>int</td><td>11</td><td>金额（单位分，×100=元）</td></tr>
  <tr><td>errorInfo</td><td>varchar</td><td>100</td><td>失败原因</td></tr>
  <tr><td>billno</td><td>varchar</td><td>28</td><td>红包订单号（腾讯方）</td></tr>
</table>

#### 举报信息表 report

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>reportId</td><td>bigint</td><td>20</td><td>举报信息ID(主键)</td></tr>
<tr><td>fansId</td><td>int</td><td>11</td><td>粉丝id（外键）</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
<tr><td>happenTime</td><td>bigint</td><td>10</td><td>发生时间</td></tr>
<tr><td>happenPlace</td><td>varchar</td><td>200</td><td>发生地点</td></tr>
  <tr><td>eventDesc</td><td>varchar</td><td>1200</td><td>事件描述</td></tr>
  <tr><td>descVoice</td><td>varchar</td><td>200</td><td>描述语音</td></tr>
  <tr><td>descImages</td><td>varchar</td><td>500</td><td>佐证材料图片</td></tr>
  <tr><td>descVideo</td><td>varchar</td><td>150</td><td>佐证材料视频</td></tr>
  <tr><td>status</td><td>int</td><td>1</td><td>状态(参考产品文档)</td></tr>
  <tr><td>reportType1</td><td>int</td><td>11</td><td>初次分类</td></tr>
  <tr><td>reportType2</td><td>int</td><td>11</td><td>追加分类</td></tr>
  <tr><td>prizeStatus1</td><td>int</td><td>1</td><td>初次发放状态（0 未发，1 已发，2 已领取）</td></tr>
  <tr><td>prizeStatus2</td><td>int</td><td>1</td><td>追加发放状态（0 未发，1 已发，2 已领取）</td></tr>
  <tr><td>remark</td><td>varchar</td><td>1200</td><td>备注</td></tr>
  <tr><td>reply</td><td>varchar</td><td>1200</td><td>小编回复</td></tr>
</table>

#### 分类表 report_type

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>typeId</td><td>int</td><td>11</td><td>主键</td></tr>
<tr><td>typeName</td><td>varchar</td><td>200</td><td>分类名称</td></tr>
<tr><td>typeDesc</td><td>varchar</td><td>2000</td><td>分类描述</td></tr>
<tr><td>amount</td><td>int</td><td>11</td><td>奖励金额</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
</table>

### 建表代码
```sql
/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : ssp

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-08-24 15:44:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `fans`
-- ----------------------------
DROP TABLE IF EXISTS `fans`;
CREATE TABLE `fans` (
  `fansId` int(11) NOT NULL AUTO_INCREMENT COMMENT '粉丝ID',
  `openId` varchar(32) NOT NULL COMMENT 'openId',
  `nickName` varchar(150) DEFAULT NULL COMMENT '昵称',
  `realName` varchar(20) NOT NULL COMMENT '姓名',
  `mobile` varchar(15) NOT NULL COMMENT '联系方式',
  `createTime` bigint(10) NOT NULL COMMENT '创建时间',
  `headimgurl` varchar(200) DEFAULT NULL COMMENT '头像',
  `subscribe` int(1) DEFAULT '0' COMMENT '是否关注',
  PRIMARY KEY (`fansId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `global_setting`
-- ----------------------------
DROP TABLE IF EXISTS `global_setting`;
CREATE TABLE `global_setting` (
  `settingId` int(11) NOT NULL AUTO_INCREMENT,
  `dayLimit` int(11) NOT NULL COMMENT '每日奖励发放上限',
  `firstAmount` int(11) NOT NULL COMMENT '初始红包金额',
  `receiveType` int(11) NOT NULL COMMENT '领取方式',
  `updateTime` bigint(10) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`settingId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `login_user`
-- ----------------------------
DROP TABLE IF EXISTS `login_user`;
CREATE TABLE `login_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `userName` varchar(30) NOT NULL COMMENT '用户名',
  `realName` varchar(20) NOT NULL COMMENT '真实姓名',
  `mobile` varchar(15) NOT NULL COMMENT '联系方式',
  `roleType` int(1) NOT NULL COMMENT '角色类型',
  `psw` varchar(32) NOT NULL COMMENT '密码',
  `createTime` bigint(10) NOT NULL COMMENT '创建时间',
  `status` int(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for `operate_record`
-- ----------------------------
DROP TABLE IF EXISTS `operate_record`;
CREATE TABLE `operate_record` (
  `recordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `reportId` bigint(20) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `createTime` bigint(10) NOT NULL COMMENT '创建时间',
  `desc` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`recordId`),
  KEY `reportId` (`reportId`),
  KEY `userId` (`userId`),
  CONSTRAINT `operate_record_ibfk_1` FOREIGN KEY (`reportId`) REFERENCES `report` (`reportId`),
  CONSTRAINT `operate_record_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `login_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of operate_record
-- ----------------------------

-- ----------------------------
-- Table structure for `prize_record`
-- ----------------------------
DROP TABLE IF EXISTS `prize_record`;
CREATE TABLE `prize_record` (
  `recordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fansId` int(11) NOT NULL COMMENT '粉丝id',
  `createTime` bigint(10) NOT NULL COMMENT '创建时间',
  `status` int(1) NOT NULL COMMENT '状态',
  `reportId` bigint(20) NOT NULL COMMENT '举报信息id（外键）',
  `type` int(1) NOT NULL COMMENT '类型（初次还是追加）',
  `amount` int(11) NOT NULL COMMENT '金额',
  `errorInfo` varchar(100) DEFAULT NULL COMMENT '错误信息',
  `billno` varchar(28) NOT NULL COMMENT '红包订单号',
  PRIMARY KEY (`recordId`),
  KEY `reportId` (`reportId`),
  KEY `prize_record_ibfk_1` (`fansId`),
  CONSTRAINT `prize_record_ibfk_1` FOREIGN KEY (`fansId`) REFERENCES `fans` (`fansId`),
  CONSTRAINT `prize_record_ibfk_2` FOREIGN KEY (`reportId`) REFERENCES `report` (`reportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of prize_record
-- ----------------------------

-- ----------------------------
-- Table structure for `report`
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `reportId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '举报id',
  `fansId` int(11) NOT NULL COMMENT '粉丝id（外键）',
  `createTime` bigint(10) NOT NULL COMMENT '创建时间',
  `happenTime` bigint(10) NOT NULL COMMENT '发生时间',
  `happenPlace` varchar(200) NOT NULL COMMENT '发生地点',
  `eventDesc` varchar(1200) DEFAULT NULL COMMENT '事件描述',
  `descVoice` varchar(200) DEFAULT NULL COMMENT '描述语音',
  `descImages` varchar(500) DEFAULT NULL COMMENT '佐证材料图片',
  `descVideo` varchar(150) DEFAULT NULL COMMENT '佐证材料视频',
  `status` int(2) NOT NULL COMMENT '状态',
  `reportType1` int(11) DEFAULT NULL COMMENT '分类1',
  `reportType2` int(11) DEFAULT NULL COMMENT '分类2',
  `prizeStatus1` int(1) NOT NULL COMMENT '初次奖励状态',
  `prizeStatus2` int(1) NOT NULL COMMENT '追加奖励状态',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `reply` varchar(1200) DEFAULT NULL COMMENT '小编回复',
  PRIMARY KEY (`reportId`),
  KEY `report_ibfk_1` (`fansId`),
  KEY `report_ibfk_2` (`reportType1`),
  KEY `report_ibfk_3` (`reportType2`),
  CONSTRAINT `report_ibfk_1` FOREIGN KEY (`fansId`) REFERENCES `fans` (`fansId`) ON DELETE NO ACTION,
  CONSTRAINT `report_ibfk_2` FOREIGN KEY (`reportType1`) REFERENCES `report_type` (`typeId`) ON DELETE SET NULL,
  CONSTRAINT `report_ibfk_3` FOREIGN KEY (`reportType2`) REFERENCES `report_type` (`typeId`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of report
-- ----------------------------

-- ----------------------------
-- Table structure for `report_type`
-- ----------------------------
DROP TABLE IF EXISTS `report_type`;
CREATE TABLE `report_type` (
  `typeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `typeName` varchar(200) NOT NULL COMMENT '分类名称',
  `typeDesc` varchar(2000) DEFAULT NULL COMMENT '分类描述',
  `amount` int(11) NOT NULL COMMENT '奖励金额',
  `createTime` bigint(10) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of report_type
-- ----------------------------

-- ----------------------------
-- Table structure for `u_permission`
-- ----------------------------
DROP TABLE IF EXISTS `u_permission`;
CREATE TABLE `u_permission` (
  `permissionId` int(11) NOT NULL AUTO_INCREMENT,
  `identification` varchar(30) DEFAULT NULL COMMENT '权限标识',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_permission
-- ----------------------------
INSERT INTO `u_permission` VALUES ('1', '用户权限', 'usermanager');

-- ----------------------------
-- Table structure for `u_role`
-- ----------------------------
DROP TABLE IF EXISTS `u_role`;
CREATE TABLE `u_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `identification` varchar(30) DEFAULT NULL COMMENT '角色标识',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role
-- ----------------------------

-- ----------------------------
-- Table structure for `u_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `u_role_permission`;
CREATE TABLE `u_role_permission` (
  `rpId` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL COMMENT '角色ID',
  `permissionId` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`rpId`),
  KEY `permissionId_wj` (`permissionId`),
  KEY `roleId_wj` (`roleId`),
  CONSTRAINT `permissionId_wj` FOREIGN KEY (`permissionId`) REFERENCES `u_permission` (`permissionId`) ON DELETE CASCADE,
  CONSTRAINT `roleId_wj` FOREIGN KEY (`roleId`) REFERENCES `u_role` (`roleId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `u_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `u_user_role`;
CREATE TABLE `u_user_role` (
  `urId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '用户ID',
  `roleId` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`urId`),
  KEY `roleId_wjx` (`roleId`),
  CONSTRAINT `roleId_wjx` FOREIGN KEY (`roleId`) REFERENCES `u_role` (`roleId`) ON DELETE CASCADE,
  CONSTRAINT `userId_wj` FOREIGN KEY (`urId`) REFERENCES `login_user` (`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_user_role
-- ----------------------------


```
