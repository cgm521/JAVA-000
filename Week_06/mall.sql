/*
 Navicat MySQL Data Transfer

 Source Server         : mysql_main
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3307
 Source Schema         : mall_db

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 01/12/2020 00:25:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `name` varchar(255) NOT NULL COMMENT '商品名称',
                         `price` decimal(12,4) NOT NULL COMMENT '单价',
                         `marketing_price` decimal(12,4) DEFAULT NULL COMMENT '营销价格',
                         `count` bigint(20) NOT NULL COMMENT '库存数量',
                         `status` varchar(64) NOT NULL COMMENT '商品状态',
                         `create_date` datetime DEFAULT NULL,
                         `modify_date` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `uk_unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1401 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
                         `id` bigint(20) NOT NULL COMMENT '主键',
                         `user_id` bigint(20) NOT NULL COMMENT '用户id',
                         `order_sum_money` decimal(12,4) NOT NULL COMMENT '订单总价',
                         `order_money` decimal(12,4) NOT NULL COMMENT '订单金额',
                         `status` varchar(64) DEFAULT NULL COMMENT '状态',
                         `create_date` datetime DEFAULT NULL,
                         `modify_date` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for order_goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_detail`;
CREATE TABLE `order_goods_detail` (
                                      `id` bigint(20) NOT NULL,
                                      `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
                                      `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
                                      `snapshot_real_payment` decimal(12,4) DEFAULT NULL COMMENT '快照实付金额',
                                      `snapshot_price` decimal(12,4) DEFAULT NULL COMMENT '快照价格',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=armscii8 COMMENT='订单商品记录表';

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `name` varchar(64) DEFAULT NULL COMMENT '序列名称',
                            `value` bigint(20) DEFAULT NULL COMMENT '序列值',
                            `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                            `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COMMENT='序列号表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `name` varchar(255) NOT NULL COMMENT '客户名称',
                        `mobile` varchar(255) NOT NULL COMMENT '手机号',
                        `password` varchar(255) NOT NULL COMMENT '密码',
                        `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                        `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_unique_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=401 DEFAULT CHARSET=utf8 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
