package com.haron.pro.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OpLog {
    private Integer id;

    private String openId;

    private String ipAddress;

    private String api;

    private String param;

    private String result;

    private Date createTime;

}