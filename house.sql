/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : house-search

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 01/12/2018 13:57:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'house唯一标识',
  `number` varchar(32) DEFAULT NULL COMMENT '编号',
  `title` varchar(32) NOT NULL,
  `price` int(11) unsigned NOT NULL COMMENT '价格',
  `area` int(11) unsigned NOT NULL COMMENT '面积',
  `type` varchar(16) NOT NULL COMMENT '户型',
  `floor` int(11) unsigned NOT NULL COMMENT '楼层',
  `total_floor` int(11) unsigned DEFAULT NULL COMMENT '总楼层',
  `watch_times` int(11) unsigned DEFAULT '0' COMMENT '被看次数',
  `build_year` int(4) DEFAULT NULL COMMENT '建立年限',
  `status` int(4) unsigned NOT NULL DEFAULT '0' COMMENT '房屋状态 0-正常 1-下架 2-删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近数据更新时间',
  `province` int(4) DEFAULT NULL COMMENT '省份',
  `city` int(4) DEFAULT NULL COMMENT '城市',
  `district` int(4) DEFAULT NULL COMMENT '区域',
  `street` varchar(32) DEFAULT NULL COMMENT '街道',
  `direction` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '房屋朝向',
  `subway` varchar(32) DEFAULT NULL COMMENT '地铁信息',
  `parlour` int(11) DEFAULT '0' COMMENT '客厅数量',
  `community` varchar(32) DEFAULT NULL COMMENT '所在小区',
  `tag` varchar(32) DEFAULT NULL COMMENT '标签',
  `origin` int(4) NOT NULL DEFAULT '0' COMMENT '来源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房屋信息表';

SET FOREIGN_KEY_CHECKS = 1;
