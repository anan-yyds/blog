/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1-MySQL5.7
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:13306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 10/06/2022 12:58:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ms_admin
-- ----------------------------
DROP TABLE IF EXISTS `ms_admin`;
CREATE TABLE `ms_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_admin
-- ----------------------------
INSERT INTO `ms_admin` VALUES (1, 'admin', '$2a$10$RZECQ90DjOT/t1mhnXsl5.XSuZWc0Sa1XduPxj2rb4yaSYcje3nWW');
INSERT INTO `ms_admin` VALUES (2, 'mszlu', '$2a$10$RZECQ90DjOT/t1mhnXsl5.XSuZWc0Sa1XduPxj2rb4yaSYcje3nWW');

-- ----------------------------
-- Table structure for ms_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `ms_admin_permission`;
CREATE TABLE `ms_admin_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_admin_permission
-- ----------------------------
INSERT INTO `ms_admin_permission` VALUES (1, 1, 1);
INSERT INTO `ms_admin_permission` VALUES (2, 2, 1);

-- ----------------------------
-- Table structure for ms_article
-- ----------------------------
DROP TABLE IF EXISTS `ms_article`;
CREATE TABLE `ms_article`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_counts` int(11) NULL DEFAULT NULL COMMENT '????????????',
  `create_date` bigint(20) NULL DEFAULT NULL COMMENT '????????????',
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????',
  `view_counts` int(11) NULL DEFAULT NULL COMMENT '????????????',
  `weight` int(11) NOT NULL COMMENT '????????????',
  `author_id` bigint(20) NULL DEFAULT NULL COMMENT '??????id',
  `body_id` bigint(20) NULL DEFAULT NULL COMMENT '??????id',
  `category_id` int(11) NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1535112951170646019 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_article
-- ----------------------------
INSERT INTO `ms_article` VALUES (1535112951170646018, 0, 1654834423232, '?????????', '?????????', 3, 0, 1531658787258146817, 1535112951170646021, 3);

-- ----------------------------
-- Table structure for ms_article_body
-- ----------------------------
DROP TABLE IF EXISTS `ms_article_body`;
CREATE TABLE `ms_article_body`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `content_html` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `article_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1535112951170646022 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_article_body
-- ----------------------------
INSERT INTO `ms_article_body` VALUES (1535109283860025345, 'dasd', '<p>dasd</p>\n', 1535109283797110785);
INSERT INTO `ms_article_body` VALUES (1535112951170646021, '?????????', '<p>?????????</p>\n', 1535112951170646018);

-- ----------------------------
-- Table structure for ms_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `ms_article_tag`;
CREATE TABLE `ms_article_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE,
  INDEX `tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1535112951170646021 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for ms_category
-- ----------------------------
DROP TABLE IF EXISTS `ms_category`;
CREATE TABLE `ms_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_category
-- ----------------------------
INSERT INTO `ms_category` VALUES (1, '/static/category/front.png', '??????', '???????????????????????????');
INSERT INTO `ms_category` VALUES (2, '/static/category/back.png  ', '??????', '???????????????');
INSERT INTO `ms_category` VALUES (3, '/static/category/lift.jpg', '??????', '????????????');
INSERT INTO `ms_category` VALUES (4, '/static/category/database.png', '?????????', '??????????????????????????????');
INSERT INTO `ms_category` VALUES (5, '/static/category/language.png', '????????????', '??????????????????????????????');

-- ----------------------------
-- Table structure for ms_comment
-- ----------------------------
DROP TABLE IF EXISTS `ms_comment`;
CREATE TABLE `ms_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_date` bigint(20) NOT NULL,
  `article_id` int(11) NOT NULL,
  `author_id` bigint(20) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `to_uid` bigint(20) NOT NULL,
  `level` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1532271874772377603 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_comment
-- ----------------------------

-- ----------------------------
-- Table structure for ms_permission
-- ----------------------------
DROP TABLE IF EXISTS `ms_permission`;
CREATE TABLE `ms_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_permission
-- ----------------------------
INSERT INTO `ms_permission` VALUES (1, '??????????????????', '/admin/permission/permissionList', '??????????????????');
INSERT INTO `ms_permission` VALUES (2, '11', '11', '111');
INSERT INTO `ms_permission` VALUES (7, '1213', '123', '123');
INSERT INTO `ms_permission` VALUES (8, '????????????', '/admin/permission/add', '????????????');

-- ----------------------------
-- Table structure for ms_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `ms_sys_log`;
CREATE TABLE `ms_sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` bigint(20) NULL DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `module` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nickname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `operation` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `time` bigint(20) NULL DEFAULT NULL,
  `userid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for ms_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `ms_sys_user`;
CREATE TABLE `ms_sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????',
  `admin` bit(1) NULL DEFAULT NULL COMMENT '???????????????',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????',
  `create_date` bigint(20) NULL DEFAULT NULL COMMENT '????????????',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '????????????',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????',
  `last_login` bigint(20) NULL DEFAULT NULL COMMENT '??????????????????',
  `mobile_phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '?????????',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '?????????',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1531665620567859203 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_sys_user
-- ----------------------------
INSERT INTO `ms_sys_user` VALUES (1531658787258146817, '1820914788', b'1', '/static/user/admin.png', 1654010886379, b'0', '', 1654010886379, NULL, '????????????', '8955e2aa5dfed1c6ffb7fe632ea2e394', '', '');
INSERT INTO `ms_sys_user` VALUES (1531660034530934786, '197400553', b'1', '/static/user/user_1.png', 1654011183749, b'0', '', 1654011183749, NULL, '??????', 'a6350bf0e9e22765ba8c80c2d7da4a1b', '', '');
INSERT INTO `ms_sys_user` VALUES (1531665620567859202, '123456', b'1', '/static/user/user_2.png', 1654012515564, b'0', '', 1654012515564, NULL, '?????????', 'e677114452bc152313b0bc3cbb145038', '', '');

-- ----------------------------
-- Table structure for ms_tag
-- ----------------------------
DROP TABLE IF EXISTS `ms_tag`;
CREATE TABLE `ms_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ms_tag
-- ----------------------------
INSERT INTO `ms_tag` VALUES (5, '/static/tag/??????.png', '??????');
INSERT INTO `ms_tag` VALUES (6, '/static/tag/??????.png', '??????');
INSERT INTO `ms_tag` VALUES (7, '/static/tag/????????????.png', '????');
INSERT INTO `ms_tag` VALUES (8, '/static/tag/??????.png', '??????');

SET FOREIGN_KEY_CHECKS = 1;
