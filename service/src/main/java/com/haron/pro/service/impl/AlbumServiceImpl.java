package com.haron.pro.service.impl;

import com.haron.pro.common.module.message.WxMessage;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.common.module.web.WxRequest;
import com.haron.pro.common.util.FileUtil;
import com.haron.pro.dao.entity.AnniversaryAlbum;
import com.haron.pro.dao.mapper.AnniversaryAlbumMapper;
import com.haron.pro.service.api.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * Created by martea on 2018/8/27.
 */
@Service
public class AlbumServiceImpl implements AlbumService{

    @Value("${ali.key}")
    private String AccessKey;

    @Value("${ali.secret}")
    private String AccessKeySecret;

    @Autowired
    AnniversaryAlbumMapper anniversaryAlbumMapper;

    @Autowired
    JedisPool jedisPool;

    @Override
    public WxMessage insertPhoto(WxUser wxUser, WxRequest wxRequest) {
        try {
            AccessKey = AccessKey.replace("CHT","");
            AccessKeySecret = AccessKeySecret.replace("ZYM","");
            Jedis jedis = jedisPool.getResource();
            if(null==jedis.get(wxUser.getOpenId())){
                return WxMessage.textBuilder().content("图片不错ヽ(･ω･´ﾒ),若想要上传图片到纪念日相册，请点击菜单：纪念日-->上传图片到纪念册。").toGroup(wxUser.getOpenId()).build();
            }
            jedis.del(wxUser.getOpenId());
            jedis.close();
            String filePath = FileUtil.DownLoadPages(wxRequest.getBody().getPicUrl(),"/usr/local/tomcat/haron/temp/"+wxUser.getOpenId()+"_temp.jpg");
            String id= UUID.randomUUID().toString();//生成的id942cd30b-16c8-449e-8dc5-028f38495bb5中间含有横杠，<span style="color: rgb(75, 75, 75); font-family: Verdana, Arial, Helvetica, sans-serif; line-height: 20.7999992370605px;">用来生成数据库的主键id是很实用的。</span>
            id=id.replace("-", "");//替换掉中间的那个斜杠
            FileUtil.uploadToOss(AccessKey,AccessKeySecret,filePath,id);
            String fileUrl = FileUtil.getOssUrl(AccessKey,AccessKeySecret,id);
            FileUtil.deleteFile(filePath);
            AnniversaryAlbum anniversaryAlbum = new AnniversaryAlbum();
            anniversaryAlbum.setOpenId(wxUser.getOpenId());
            anniversaryAlbum.setPicName(fileUrl);
            anniversaryAlbumMapper.insert(anniversaryAlbum);
            String fileName = "/usr/local/tomcat/haron/albumExample/view/"+wxUser.getOpenId()+"_album.html";
            // 判断文件是否存在
            if(FileUtil.isFileExist(fileName)){

            }else {
                String fileContent = FileUtil.fileRead("/usr/local/tomcat/haron/albumExample/view/index.html");
                fileContent = fileContent.replaceAll("￥",wxUser.getNickName());
                String albumString = FileUtil.albumString.replaceAll("url",fileUrl);
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
