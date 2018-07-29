package com.haron.pro.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DateRemind {
    /**
     *
     CREATE TABLE `date_remind` (
     `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
     `open_id` varchar(64) DEFAULT '' COMMENT '用户的openid',
     `content` varchar(128) DEFAULT '' COMMENT '提醒内容',
     `remark` varchar(64) DEFAULT '' COMMENT '备注',
     `next_remind_date` timestamp NULL DEFAULT NULL COMMENT '下次提醒时间',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户定时提醒表';
     */

    private Integer id;

    private String openId;

    private String content;

    private String remark;

    private Date nextRemindDate;

    private Date createTime;

    private Date updateTime;


}