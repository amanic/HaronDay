package com.haron.pro.service.model;

import com.haron.pro.common.module.message.WxMessage;
import lombok.Data;

/**
 * Created by chenhaitao on 2018/8/9.
 */
@Data
public class UnionParam {

    private String openid;
    private String StringParam;
    private WxMessage wxMessage;

    public String toString(){
        StringBuilder builder = new StringBuilder();
        if(openid!=null){
            builder.append("openId = {"+openid+"},");
        }
        if(StringParam!=null){
            builder.append("StringParam = {"+StringParam+"},");
        }
        if(wxMessage!=null){
            builder.append("wxMessage = {"+wxMessage.toString()+"},");
        }
        return builder.toString().substring(0,builder.length()-1);
    }
}
