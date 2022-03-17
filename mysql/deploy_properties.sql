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
-- 前端配置，密码使用密文
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_IP', 'FRONTEND', '192.168.0.2', '1');
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_USER', '192.168.0.2', 'root', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_PASS', '192.168.0.2', 'password', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('DEPLOY_PATH', '192.168.0.2', 'deploy', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('BACKUP_PATH', '192.168.0.2', 'backup', null);
-- 后端配置，密码使用密文，LOG_PATH特指nohup的日志路径，logback路径需要自行配置
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_IP', 'SERVICE', '192.168.0.121', '3');
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_USER', '192.168.0.121', 'server', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('SERVER_PASS', '192.168.0.121', 'password', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('DEPLOY_PATH', '192.168.0.121', 'deploy', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('BACKUP_PATH', '192.168.0.121', 'backup', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('RUN_PATH', '192.168.0.121', 'running', null);
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('LOG_PATH', '192.168.0.121', 'log/nohup', null);
-- jar包重命名配置，建议指定排序，从上往下，从左往右，分为前缀和后缀
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('RENAME', 'PREFIX', 'my-prefix aaa-', '1');
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('RENAME', 'SUFFIX', '.jar -1.0.0 -service', '1');
INSERT INTO `deploy_properties` (`type`, `key`, `val`, `seq`) VALUES ('RENAME', 'SUFFIX', '-1.0 -1.1', '2');

