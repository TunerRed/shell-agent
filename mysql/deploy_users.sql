SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for deploy_users
-- ----------------------------
DROP TABLE IF EXISTS `deploy_users`;
CREATE TABLE `deploy_users` (
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '使用密文',
  `grant_server_seq` varchar(100) DEFAULT NULL COMMENT '授权服务器的seq值',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `root` enum('0','1') NOT NULL DEFAULT '0' COMMENT '是否拥有操作数据库的权限',
  `label1` varchar(200) DEFAULT NULL,
  `label2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deploy_users
-- ----------------------------
-- 登录账户，密码使用密文，grant_server_seq数字为SERVER_IP的seq值，服务器权限控制，逗号隔开，需要保证所有的SERVER_IP对应的seq值都不同
INSERT INTO `deploy_users` (`username`, `password`, `grant_server_seq`, `phone`, `root`, `label1`, `label2`) VALUES ('111', 'TWpJeQ==', '1,3', '111222333', '1', NULL, NULL);

