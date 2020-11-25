/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : localhost:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 26/11/2020 00:10:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     varchar(64)  NOT NULL COMMENT '用户id',
    `name`        varchar(255) NOT NULL COMMENT '客户名称',
    `mobile`      varchar(255) NOT NULL COMMENT '手机号',
    `password`    varchar(255) NOT NULL COMMENT '密码',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `modify_date` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = armscii8 COMMENT ='用户表';

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`
(
    `id`              bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `goods_id`        varchar(64)    NOT NULL COMMENT '商品id',
    `name`            varchar(255)   NOT NULL COMMENT '商品名称',
    `price`           decimal(12, 4) NOT NULL COMMENT '单价',
    `marketing_price` decimal(12, 4) DEFAULT NULL COMMENT '营销价格',
    `count`           bigint(20)     NOT NULL COMMENT '库存数量',
    `status`          varchar(64)    NOT NULL COMMENT '商品状态',
    `create_date`     datetime       DEFAULT NULL,
    `modify_date`     datetime       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = armscii8 COMMENT ='商品表';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`          bigint(20)     NOT NULL COMMENT '主键',
    `order_id`    varchar(64)    NOT NULL COMMENT '订单id',
    `user_id`     bigint(20)     NOT NULL COMMENT '用户id',
    `order_money` decimal(12, 4) NOT NULL COMMENT '订单金额',
    `create_date` datetime DEFAULT NULL,
    `modify_date` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = armscii8 COMMENT ='订单表';

-- ----------------------------
-- Table structure for order_goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_detail`;
CREATE TABLE `order_goods_detail`
(
    `id`             bigint(20) NOT NULL,
    `order_id`       varchar(64)    DEFAULT NULL COMMENT '订单id',
    `goods_id`       bigint(20)     DEFAULT NULL COMMENT '商品id',
    `snapshot_price` decimal(12, 2) DEFAULT NULL COMMENT '快照价格',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = armscii8 COMMENT ='订单商品记录表';



SET FOREIGN_KEY_CHECKS = 1;
