package com.haron.pro.service.api;

import com.haron.pro.common.module.message.WxMessage;
import com.haron.pro.common.module.user.WxUser;
import com.haron.pro.dao.entity.DateRemind;

/**
 * Created by chenhaitao on 2018/7/29.
 */
public interface DateRemindService {

    String remind(DateRemind remind);

    String chat(String content,String openId);

    WxMessage sign(WxUser wxUser);

    WxMessage next(WxUser wxUser);


}
