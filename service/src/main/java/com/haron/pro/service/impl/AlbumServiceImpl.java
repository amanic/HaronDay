package com.haron.pro.service.impl;

import com.haron.pro.common.module.message.WxMessage;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.common.module.web.WxRequest;
import com.haron.pro.dao.entity.AnniversaryAlbum;
import com.haron.pro.dao.mapper.AnniversaryAlbumMapper;
import com.haron.pro.service.api.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by martea on 2018/8/27.
 */
@Service
public class AlbumServiceImpl implements AlbumService{

    @Autowired
    AnniversaryAlbumMapper anniversaryAlbumMapper;

    @Autowired
    JedisPool jedisPool;

    @Override
    public WxMessage insertPhoto(WxUser wxUser, WxRequest wxRequest) {
        Jedis jedis = jedisPool.getResource();
        if(null==jedis.get(wxUser.getOpenId())){
            return WxMessage.textBuilder().content("图片不错ヽ(･ω･´ﾒ),若想要上传图片到纪念日相册，请点击菜单：纪念日-->上传图片到纪念册。").toGroup(wxUser.getOpenId()).build();
        }
        jedis.del(wxUser.getOpenId());
        jedis.close();
        AnniversaryAlbum anniversaryAlbum = new AnniversaryAlbum();
        anniversaryAlbum.setOpenId(wxUser.getOpenId());
        anniversaryAlbum.setPicName(wxRequest.getBody().getPicUrl());
        anniversaryAlbumMapper.insert(anniversaryAlbum);
        return WxMessage.textBuilder().content("上传成功，图片链接为："+wxRequest.getBody().getPicUrl()+",后续将推出纪念日相册功能，敬请期待！").toGroup(wxUser.getOpenId()).build();
    }
}
