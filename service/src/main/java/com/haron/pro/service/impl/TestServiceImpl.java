package com.haron.pro.service.impl;

import com.haron.pro.common.util.WxWebUtils;
import com.haron.pro.dao.mapper.UserMapper;
import com.haron.pro.service.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chenhaitao on 2018/7/28.
 */
@Service
public class TestServiceImpl implements TestService{


    @Autowired
    UserMapper userMapper;

    @Override
    public String test1(Integer i) {
        return "操作者："+WxWebUtils.getWxWebUserFromSession().getOpenId()+"得到的结果是："+userMapper.selectByPrimaryKey(i).toString();
    }
}
