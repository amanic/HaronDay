package com.haron.pro.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.haron.pro.common.module.message.WxMessage;
import com.haron.pro.common.module.message.WxMessageTemplate;
import com.haron.pro.common.module.message.WxUserMessage;
import com.haron.pro.common.service.WxApiService;
import com.haron.pro.common.util.HttpClientUtil;
import com.haron.pro.dao.entity.DateRemind;
import com.haron.pro.service.api.DateRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by chenhaitao on 2018/7/29.
 */
@Service
public class DateRemindServiceImpl implements DateRemindService {

    @Autowired
    WxApiService wxApiService;

    @Autowired
    WxMessageTemplate wxMessageTemplate;

    @Override
    public String remind(DateRemind remind) {
        WxUserMessage wxMessage = WxMessage.News.builder()
                .addItem("测试图文消息", "测试", "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-661501.jpg", "http://tczmh.club/bz/index.html")
                .addItem("测试图文消息", "测试", "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-679271.png", "https://github.com/amanic")
                .addItem("测试图文消息", "测试", "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-9394.jpg", "https://github.com/LauItachi/WeChatTest")
                .build();

        wxMessageTemplate.sendMessage(remind.getOpenId(), wxMessage);

        return null;
    }

    @Override
    public String chat(String content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key","b6f73bda544046988c5731de14701a35");
        jsonObject.put("info",content);
        String result = null;
        try {
            result = HttpClientUtil.doPost("http://www.tuling123.com/openapi/api",jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
