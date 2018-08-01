package com.haron.pro.dao.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ChatLog {

    private Integer id;

    private String openId;

    private String nickName;

    private String userSend;

    private String userRecieve;

    private Date createTime;
}