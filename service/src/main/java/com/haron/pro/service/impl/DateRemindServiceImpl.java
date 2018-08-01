package com.haron.pro.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haron.pro.common.module.message.WxMessage;
import com.haron.pro.common.module.message.WxMessageTemplate;
import com.haron.pro.common.module.message.WxUserMessage;
import com.haron.pro.common.service.WxApiService;
import com.haron.pro.common.util.HttpClientUtil;
import com.haron.pro.dao.entity.ChatLog;
import com.haron.pro.dao.entity.ChatPrivate;
import com.haron.pro.dao.entity.DateRemind;
import com.haron.pro.dao.mapper.ChatLogMapper;
import com.haron.pro.dao.mapper.ChatPrivateMapper;
import com.haron.pro.service.api.DateRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by chenhaitao on 2018/7/29.
 */
@Service
public class DateRemindServiceImpl implements DateRemindService {

    @Autowired
    WxApiService wxApiService;

    @Autowired
    WxMessageTemplate wxMessageTemplate;

    @Autowired
    ChatPrivateMapper chatPrivateMapper;

    @Autowired
    ChatLogMapper chatLogMapper;

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
    public String chat(String content,String openId) {
        ChatLog chatLog = new ChatLog();
        chatLog.setUserSend(content);
        chatLog.setOpenId(openId);
        chatLog.setNickName(wxApiService.getUserInfo(openId).getNickName());
        ChatPrivate chatPrivate = chatPrivateMapper.selectByRecieve(content);
        if(chatPrivate!=null){
            return chatPrivate.getContent();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key","b6f73bda544046988c5731de14701a35");
        jsonObject.put("info",content);
        String result = "我不知道你在说什么，我只知道皓然是最美的小姐姐！";
        try {
            String urlResult = HttpClientUtil.doPost("http://www.tuling123.com/openapi/api",jsonObject);
            JSONObject object = JSON.parseObject(urlResult);
            if(object.getInteger("code")==100000){
                result = object.getString("text");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            chatLog.setUserRecieve(result);
            chatLogMapper.insertSelective(chatLog);
            return result;
        }
    }

}
