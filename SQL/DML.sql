CREATE TABLE `op_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `open_id` varchar(64) DEFAULT '' COMMENT '用户的openId',
  `ip_address` varchar(32) DEFAULT '' COMMENT 'ip地址',
  `api` varchar(256) DEFAULT '' COMMENT '调用接口',
  `param` varchar(512) DEFAULT '' COMMENT '用户传参',
  `result` varchar(256) DEFAULT '' COMMENT '返回结果',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

CREATE TABLE `date_remind` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `open_id` varchar(64) DEFAULT '' COMMENT '用户的openid',
  `content` varchar(64) DEFAULT '' COMMENT '提醒内容',
  `url` varchar(128) DEFAULT NULL COMMENT '链接',
  `remark` varchar(64) DEFAULT '' COMMENT '备注',
  `next_remind_date` timestamp NULL DEFAULT NULL COMMENT '下次提醒时间',
  `to_be_remind` tinyint(2) DEFAULT '1' COMMENT '是否需要提醒，0否，1是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户定时提醒表';

CREATE TABLE `chat_private` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `recieve` varchar(20) NOT NULL DEFAULT '' COMMENT '接受参数',
  `content` varchar(256) NOT NULL DEFAULT '' COMMENT '返回内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='机器人聊天菜单表';

CREATE TABLE `chat_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `open_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户openid',
  `nick_name` varchar(32) DEFAULT '' COMMENT '用户昵称',
  `user_send` varchar(128) DEFAULT NULL COMMENT '用户发送消息',
  `user_recieve` varchar(128) DEFAULT NULL COMMENT '用户接受内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;