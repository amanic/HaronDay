package com.haron.pro.service.api;

import com.haron.pro.common.module.message.WxMessage;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.common.module.web.WxRequest;

/**
 * Created by martea on 2018/8/27.
 */
public interface AlbumService {

    WxMessage insertPhoto(WxUser wxUser, WxRequest wxRequest);

}
