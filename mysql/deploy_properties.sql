-- ----------------------------
-- Table structure for deploy_properties
-- ----------------------------
DROP TABLE IF EXISTS `deploy_properties`;
CREATE TABLE `deploy_properties` (
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属类型',
  `key` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '键',
  `val` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '值',
  `seq` int(11) DEFAULT NULL COMMENT '排序'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deploy_properties
-- ----------------------------
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_USER', '192.168.0.2', 'root', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_PASS', '192.168.0.2', 'password', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_PORT', '192.168.0.2', '22', null);

