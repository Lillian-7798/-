/*
 Navicat Premium Data Transfer

 Source Server         : demo
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : database

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 30/08/2023 14:26:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for function_index
-- ----------------------------
DROP TABLE IF EXISTS `function_index`;
CREATE TABLE `function_index`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '函数id',
  `flightAltitude` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `flightSpeed` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `flightRange` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `flightEndurance` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `totalWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `totalCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `length` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `wing` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `AR` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `materialType` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `bodyWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `bodyCost` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0,
  `energyEndurance` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `energyAbility` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `energyWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `energyCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `propulsionAbility` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `propulsionWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `propulsionCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `taskWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `taskCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `groundCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of function_index
-- ----------------------------
INSERT INTO `function_index` VALUES (1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0);
INSERT INTO `function_index` VALUES (2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1);
INSERT INTO `function_index` VALUES (3, 0, 2, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
INSERT INTO `function_index` VALUES (4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- ----------------------------
-- Table structure for function_para
-- ----------------------------
DROP TABLE IF EXISTS `function_para`;
CREATE TABLE `function_para`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `para_1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `para_2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `para_3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `para_4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `para_5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `para_6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of function_para
-- ----------------------------
INSERT INTO `function_para` VALUES (1, 'totalWeight', 'bodyWeight', 'energyWeight', 'propulsionWeight', 'taskWeight', NULL);
INSERT INTO `function_para` VALUES (2, 'totalCost', 'bodyCost', 'energyCost', 'propulsionCost', 'taskCost', 'groundCost');
INSERT INTO `function_para` VALUES (3, 'flightSpeed', 'length', 'wing', 'AR', 'propulsionAbility', 'totalWeight');
INSERT INTO `function_para` VALUES (4, 'bodyWeight', 'flightSpeed', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for rule_index
-- ----------------------------
DROP TABLE IF EXISTS `rule_index`;
CREATE TABLE `rule_index`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '函数id',
  `flightAltitude` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `flightSpeed` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `flightRange` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `flightEndurance` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `totalWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `totalCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `length` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `wing` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `AR` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `materialType` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `bodyWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `energyEndurance` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `energyAbility` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `energyWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `energyCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `propulsionAbility` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `propulsionWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `propulsionCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `taskWeight` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `taskCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `groundCost` int(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0,
  `weight` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rule_index
-- ----------------------------
INSERT INTO `rule_index` VALUES (1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL);
INSERT INTO `rule_index` VALUES (2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL);
INSERT INTO `rule_index` VALUES (3, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
