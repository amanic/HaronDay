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

package com.haron.pro.common.module.message;

import com.haron.pro.common.module.message.parameter.WxMessageParameter;

/**
 * FastBootWeixin WxCommonMessageProcessor
 *
 * @author Guangshan
 * @date 2017/8/20 22:53
 * @since 0.1.2
 */
public class WxUserMessageProcessor implements WxMessageProcessor<WxUserMessage> {

    @Override
    public WxUserMessage process(WxMessageParameter wxMessageParameter, WxUserMessage wxMessage) {
        // 这个重复逻辑可以使用processInternal处理
        if (wxMessage == null) {
            return wxMessage;
        }
        if (wxMessage.getToUser() == null) {
            wxMessage.setToUser(wxMessageParameter.getToUser());
        }
        if (wxMessage.getFromUser() == null) {
            wxMessage.setFromUser(wxMessageParameter.getFromUser());
        }
        if (wxMessage.getCreateTime() == null) {
            wxMessage.setCreateTime(wxMessageParameter.getCreateTime());
        }
        return wxMessage;
    }

}
