package com.haron.pro.service.impl;

import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.common.service.WxApiService;
import com.haron.pro.common.util.WxWebUtils;
import com.haron.pro.common.web.WxWebUser;
import com.haron.pro.dao.mapper.UserMapper;
import com.haron.pro.service.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;
/**
 * Created by chenhaitao on 2018/7/28.
 */
@Service
public class TestServiceImpl implements TestService{

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    JedisPool jedisPool;

    @Autowired
    WxApiService wxApiService;


    @Autowired
    UserMapper userMapper;

    @Override
    public String test1(Integer i) {
        String openId = WxWebUtils.getWxWebUserFromSession().getOpenId();
        WxUser wxUser = wxApiService.getUserInfo(openId);
        return "操作者："+wxUser.toString()+"\n得到的结果是："+userMapper.selectByPrimaryKey(i).toString();
    }

    @Override
    public String test2() {
        WxWebUser wxWebUser = WxWebUtils.getWxWebUserFromSession();
        redisTemplate.opsForValue().set("tempKey","tempValue",300, TimeUnit.SECONDS);
        String templateResult = redisTemplate.opsForValue().get("tempKey").toString();
        jedisPool.getResource().setex("jedisKey",300,"jedisValue");
        String jedisResult = jedisPool.getResource().get("jedisKey").toString();
        return "templateResult："+templateResult+"，jedisResult："+jedisResult+"";
    }
}
