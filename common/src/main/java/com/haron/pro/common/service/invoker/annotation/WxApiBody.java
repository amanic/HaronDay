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

package com.haron.pro.common.service.invoker.annotation;

import java.lang.annotation.*;

/**
 * FastBootWeixin WxApiBody
 * 是否有必要再加一个WxApiFile，传入路径参数？
 *
 * @author Guangshan
 * @date 2017/09/21 23:31
 * @since 0.1.2
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WxApiBody {

    Type type() default Type.JSON;

    enum Type {
        JSON, XML
    }

}
