/*
 * Copyright (c) 2016-2017, Guangshan (guangshan1992@qq.com) and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haron.pro.common.module.message.processor.group;


import com.haron.pro.common.module.message.WxMessageBody;
import com.haron.pro.common.module.message.parameter.WxMessageParameter;
import com.haron.pro.common.module.message.processor.AbstractWxMessageBodyProcessor;
import com.haron.pro.common.util.WxRedirectUtils;
import com.haron.pro.common.util.WxUrlUtils;

/**
 * FastBootWeixin WxGroupNewsMessageProcessor
 *
 * @author Guangshan
 * @date 2017/8/20 22:53
 * @since 0.1.2
 */
public class WxGroupNewsMessageProcessor extends AbstractWxMessageBodyProcessor<WxMessageBody.News> {

    @Override
    protected WxMessageBody.News processBody(WxMessageParameter wxMessageParameter, WxMessageBody.News body) {
        body.getArticles().stream().forEach(i -> {
            i.setPicUrl(WxUrlUtils.mediaUrl(wxMessageParameter.getRequestUrl(), i.getPicUrl()));
            i.setUrl(WxRedirectUtils.redirect(wxMessageParameter.getRequestUrl(), i.getUrl()));
        });
        return body;
    }

}
