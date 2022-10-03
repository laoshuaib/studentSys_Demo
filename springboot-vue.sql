/*
 Navicat Premium Data Transfer

 Source Server         : classwork
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : springboot-vue

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 10/12/2021 10:00:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chooselist
-- ----------------------------
DROP TABLE IF EXISTS `chooselist`;
CREATE TABLE `chooselist`  (
  `ID` int(0) NOT NULL AUTO_INCREMENT COMMENT '选课识别ID',
  `classID` int(0) NULL DEFAULT NULL COMMENT '课程ID',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课程名字',
  `teacherid` int(0) NULL DEFAULT NULL COMMENT '老师id',
  `studentid` int(0) NULL DEFAULT NULL COMMENT '学生ID',
  `student_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学生学号',
  `student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学生名字',
  `scores` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '成绩',
  `checker` int(0) NULL DEFAULT NULL COMMENT '检查是否完成录入（1锁定，0暂存，不可为null）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chooselist
-- ----------------------------
INSERT INTO `chooselist` VALUES (34, 8, '高级软件建模', 14, 8, '202000300259', '陈泰华', '100', 1);
INSERT INTO `chooselist` VALUES (37, 21, '高数', 14, 8, '202000300259', '陈泰华', '100', 1);
INSERT INTO `chooselist` VALUES (38, 21, '高数', 14, 16, '202000300407', '廖俊霄', '-1', 1);

-- ----------------------------
-- Table structure for lessonlist
-- ----------------------------
DROP TABLE IF EXISTS `lessonlist`;
CREATE TABLE `lessonlist`  (
  `lessonID` int(0) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名称',
  `teacher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '老师',
  `teacherid` int(0) NULL DEFAULT NULL COMMENT '老师id',
  `point` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学分',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上课时间',
  `start_time` date NULL DEFAULT NULL COMMENT '选课开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '选课结束时间',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上课地点',
  `capacity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课容量',
  `margin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '课余量',
  PRIMARY KEY (`lessonID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lessonlist
-- ----------------------------
INSERT INTO `lessonlist` VALUES (8, '高级软件建模', 'xx老师', 14, '2', '周五第三节', '2021-11-28', '2021-11-29', '线上', '10', '10');
INSERT INTO `lessonlist` VALUES (21, '高数', 'xx老师', 14, '1', NULL, '2021-12-02', '2021-12-03', '105', '1', '1');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id(自动生成）',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '专业',
  `birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生日',
  `grade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '年级',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (8, NULL, NULL, NULL, '202000300259', '陈泰华', '13643021830', '2057503191@qq.com', '山东大学');
INSERT INTO `student` VALUES (15, NULL, NULL, NULL, '202100412133', '佘驭昊', NULL, NULL, NULL);
INSERT INTO `student` VALUES (16, NULL, NULL, NULL, '202000300407', '廖俊霄', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(0) NOT NULL COMMENT 'id（自动生成）',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (14, 'xx老师', '20202021');
INSERT INTO `teacher` VALUES (17, '枫原万叶', '2075046181');
INSERT INTO `teacher` VALUES (18, '陈泰华', '123');
INSERT INTO `teacher` VALUES (19, '波波', '666666');
INSERT INTO `teacher` VALUES (20, '木泽', '111111');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id（自动生成）',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名\r\n',
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '学号工号\r\n',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `identity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '身份',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (8, '陈泰华', '202000300259', 'Cth155675', '学生');
INSERT INTO `user` VALUES (14, 'xx老师', '20202021', '123456', '老师');
INSERT INTO `user` VALUES (15, '佘驭昊', '202100412133', 'SYH5211314', '学生');
INSERT INTO `user` VALUES (16, '廖俊霄', '202000300407', '123456', '学生');
INSERT INTO `user` VALUES (17, '枫原万叶', '2075046181', 'SYH5211314', '老师');
INSERT INTO `user` VALUES (18, '陈泰华', '123', '123456', '老师');
INSERT INTO `user` VALUES (19, '波波', '666666', '666666', '老师');
INSERT INTO `user` VALUES (20, '木泽', '111111', '111111', '老师');

SET FOREIGN_KEY_CHECKS = 1;
