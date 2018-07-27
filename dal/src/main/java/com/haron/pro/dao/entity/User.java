package com.haron.pro.dao.entity;

import lombok.Data;
import java.util.Date;


@Data
public class User {

    private Integer id;

    private String name;

    private Integer sex;

    private String school;

    private String operatorName;

    private Date createTime;

    private Date updateTime;

    private String remark;
}