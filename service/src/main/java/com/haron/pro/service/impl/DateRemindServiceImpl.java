package com.haron.pro.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haron.pro.common.annotation.LogOperationTag;
import com.haron.pro.common.module.message.WxMessage;
import com.haron.pro.common.module.message.WxMessageTemplate;
import com.haron.pro.common.module.message.WxTemplateMessage;
import com.haron.pro.common.module.message.WxUserMessage;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.common.service.WxApiService;
import com.haron.pro.common.util.DateUtil;
import com.haron.pro.common.util.HttpClientUtil;
import com.haron.pro.dao.entity.ChatLog;
import com.haron.pro.dao.entity.ChatPrivate;
import com.haron.pro.dao.entity.DateRemind;
import com.haron.pro.dao.mapper.ChatLogMapper;
import com.haron.pro.dao.mapper.ChatPrivateMapper;
import com.haron.pro.dao.mapper.DateRemindMapper;
import com.haron.pro.service.api.DateRemindService;
import com.haron.pro.service.model.UnionParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;


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

    @Autowired
    JedisPool jedisPool;

    @Autowired
    DateRemindMapper dateRemindMapper;

    private static final String signKey = "_sign";

    @Override
    public String remind(DateRemind remind) {
        WxTemplateMessage wxTemplateMessage = WxTemplateMessage.templateBuilder()
                .toUser(remind.getOpenId())
                .data("theme","纪念日提醒啦","#DA70D6")
                .data("time", DateUtil.convert2String(remind.getNextRemindDate(),"yyyy-MM-dd"),"#FF1493")
                .data("content",remind.getContent(),"#4B0082")
                .data("remark",remind.getRemark(),"#708090")
                .templateId("EhB4WyjHG5Uv2rpHDb1rawckrtVcoN9KMN1jVuAaePw")
                .url("http://tczmh.club/bz/")
                .build();
        wxApiService.sendTemplateMessage(wxTemplateMessage);
        return "成功";
    }

    @Override
    @LogOperationTag
    public String chat(UnionParam unionParam) {
        String content = unionParam.getStringParam();
        String openId = unionParam.getOpenId();
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
        String result = "sso";
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

    @Override
    public WxMessage sign(WxUser wxUser) {
        Jedis jedis = jedisPool.getResource();
        if(jedis.get(wxUser.getOpenId()+signKey)==null){
            int second = (int)DateUtil.diffSecond(new Date(),DateUtil.convert2Date(DateUtil.convert2String(new Date(),"yyyy-MM-dd")+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
            jedis.setex(wxUser.getOpenId()+signKey,second,"sign");
            return WxMessage.textBuilder().content("签到啦٩(๑>◡<๑)۶，又离纪念日进了一步。").build();
        }
        return WxMessage.textBuilder().content("你今天已经签过到了╰（‵□′）╯").build();
    }

    @Override
    public WxMessage next(WxUser wxUser) {
        List<DateRemind> reminds = dateRemindMapper.selectUniqueToRemind(wxUser.getOpenId());
        if(reminds==null||reminds.size()==0){
            return WxMessage.newsBuilder().addItem("最近纪念日","您还没有需要提醒的纪念日哦","https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-674407.jpg","http://www.btkitty.com/").build();
        }
        WxMessage.NewsBuilder builder = WxMessage.newsBuilder();
        for (DateRemind remind : reminds) {
            builder.addItem("纪念日",remind.getContent(),"https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-674407.jpg","http://www.btkitty.com/");
        }
        return builder.build();
    }


//    public static void main(String[] args) {
//        int i = (int)DateUtil.diffSecond(new Date(),DateUtil.convert2Date(DateUtil.convert2String(new Date(),"yyyy-MM-dd")+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
//        System.out.println(i);
//    }

}
