package com.haron.pro.service.impl;

import com.haron.pro.common.module.message.WxMessage;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.common.module.web.WxRequest;
import com.haron.pro.common.util.FileUtil;
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
        try {
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
            String fileName = "/usr/local/tomcat/haron/albumExample/view/"+wxUser.getOpenId()+"_album.html";
            // 判断文件是否存在
            if(FileUtil.isFileExist(fileName)){

            }else {
                String fileContent = FileUtil.fileRead("/usr/local/tomcat/haron/albumExample/view/index.html");
                fileContent = fileContent.replaceAll("￥",wxUser.getNickName());
                String albumString = FileUtil.albumString.replaceAll("￥",wxRequest.getBody().getPicUrl());
                fileContent = fileContent.replaceAll("albumReplacement",albumString);
                FileUtil.WriteStringToFile(fileName,fileContent);
            }
            return WxMessage.textBuilder().content("上传成功,可以查看纪念相册啦~").toGroup(wxUser.getOpenId()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
