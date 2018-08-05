package com.haron.pro.dao.entity;

import lombok.Data;

import java.util.Date;


@Data
public class DateRemind {

    private Integer id;

    private String openId;

    private String content;

    private String url;

    private String remark;

    private Date nextRemindDate;

    private Byte toBeRemind;

    private Date createTime;

    private Date updateTime;

}